package org.catrobat.catroid.devices.raspberrypi;

import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.eventids.RaspiEventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/raspberrypi/RPiSocketConnection.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RPiSocketConnection {
    private static final String TAG = "AsyncRPiTaskRunner";
    private ArrayList<Integer> availableGPIOs;
    private Socket clientSocket;
    private String host;
    private int interruptReceiverPort;
    private boolean isConnected;
    private DataOutputStream outStream;
    private OutputStream outToServer;
    private BufferedReader reader;
    private Thread receiverThread;
    private String rpiVersion;

    public void connect(String host, int port) throws Exception {
        if (this.isConnected) {
            disconnect();
        }
        this.host = host;
        Socket socket = new Socket();
        this.clientSocket = socket;
        socket.connect(new InetSocketAddress(host, port), 2000);
        this.outToServer = this.clientSocket.getOutputStream();
        this.outStream = new DataOutputStream(this.outToServer);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.reader = bufferedReader;
        String line = bufferedReader.readLine();
        if (line.startsWith("quit")) {
            throw new NoConnectionException("Server refused to accept our connection!");
        }
        if (line.startsWith("hello")) {
            this.isConnected = true;
            respondVersion();
            readServerPort();
            Thread thread = new Thread(new RPiSocketReceiver());
            this.receiverThread = thread;
            thread.start();
        }
    }

    public void disconnect() throws IOException {
        if (this.isConnected) {
            try {
                processCommand("quit");
            } catch (NoConnectionException unused) {
                Log.d(TAG, "Error during quit, this should not happen!");
            }
            this.isConnected = false;
            this.clientSocket.close();
            this.receiverThread.interrupt();
        }
    }

    private void respondVersion() throws Exception {
        this.rpiVersion = processCommand("rev").split(" ")[1];
        this.availableGPIOs = RaspberryPiService.getInstance().getGpioList(this.rpiVersion);
    }

    private void readServerPort() throws Exception {
        this.interruptReceiverPort = Integer.parseInt(processCommand("serverport").split(" ")[1]);
    }

    private String processCommand(String command) throws IOException, NoConnectionException {
        if (!this.isConnected) {
            throw new NoConnectionException("No active connection!");
        }
        String str = TAG;
        Log.d(str, "Sending:  " + command);
        this.outStream.write(command.getBytes());
        String line = this.reader.readLine();
        Log.d(str, "Received: " + line);
        if (line == null || !line.startsWith(command.split(" ")[0])) {
            throw new IOException("Error with response");
        }
        return line;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callEvent(String broadcastMessage) {
        String[] strArrSplit = broadcastMessage.split(" ");
        if (strArrSplit.length != 3 || ProjectManager.getInstance().getCurrentProject() == null) {
            return;
        }
        ProjectManager.getInstance().getCurrentProject().fireToAllSprites(new EventWrapper(new RaspiEventId(strArrSplit[1], strArrSplit[2]), false));
    }

    public void setPin(int i, boolean z) throws IOException, NoGpioException, NoConnectionException {
        if (!this.isConnected) {
            throw new NoConnectionException("No active connection!");
        }
        if (!this.availableGPIOs.contains(Integer.valueOf(i))) {
            throw new NoGpioException("Pin out of range on this model!");
        }
        if (processCommand("set " + i + " " + ((int) (z ? (short) 1 : (short) 0))).split(" ").length != 3) {
            throw new IOException("setRequest: Error with response");
        }
    }

    public boolean getPin(int pin) throws IOException, NoGpioException, NoConnectionException {
        if (!this.availableGPIOs.contains(Integer.valueOf(pin))) {
            throw new NoGpioException("Pin out of range on this model!");
        }
        String[] strArrSplit = processCommand("read " + pin).split(" ");
        if (strArrSplit.length != 3) {
            throw new IOException("readRequest: Error with response");
        }
        if (strArrSplit[2].equals(SchemaSymbols.ATTVAL_TRUE_1)) {
            return true;
        }
        if (strArrSplit[2].equals("0")) {
            return false;
        }
        throw new IOException("readRequest: Error with response");
    }

    public void activatePinInterrupt(int pin) throws IOException, NoGpioException, NoConnectionException {
        if (!this.availableGPIOs.contains(Integer.valueOf(pin))) {
            throw new NoGpioException("Pin out of range on this model!");
        }
        if (processCommand("readint " + pin).split(" ").length != 3) {
            throw new IOException("readRequest: Error with response");
        }
    }

    public void setPWM(int pin, double frequencyInHz, double dutyCycleInPercent) throws IOException, NoGpioException, NoConnectionException {
        if (!this.availableGPIOs.contains(Integer.valueOf(pin))) {
            throw new NoGpioException("Pin out of range on this model!");
        }
        String str = "pwm " + pin + " " + frequencyInHz + " " + dutyCycleInPercent;
        if (!str.equals(processCommand(str))) {
            throw new IOException("pwmRequest: Error with response");
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/raspberrypi/RPiSocketConnection$RPiSocketReceiver.class
      classes14.dex
     */
    private class RPiSocketReceiver implements Runnable {
        private RPiSocketReceiver() {
        }

        @Override // java.lang.Runnable
        public void run() {
            String line;
            try {
                Socket socket = new Socket(RPiSocketConnection.this.host, RPiSocketConnection.this.interruptReceiverPort);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (!Thread.interrupted() && (line = bufferedReader.readLine()) != null) {
                    Log.d(RPiSocketConnection.TAG, "Interrupt: " + line);
                    RPiSocketConnection.this.callEvent(line);
                }
                socket.close();
                Log.d(RPiSocketConnection.TAG, "RPiSocketReceiver closed");
            } catch (IOException e) {
                Log.e(RPiSocketConnection.TAG, "Exception " + e);
            }
        }
    }

    public String getVersion() throws NoConnectionException {
        if (!this.isConnected) {
            throw new NoConnectionException("No active connection!");
        }
        return this.rpiVersion;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/raspberrypi/RPiSocketConnection$NoGpioException.class
      classes14.dex
     */
    public class NoGpioException extends Exception {
        private static final long serialVersionUID = 1;

        public NoGpioException(String msg) {
            super(msg);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/raspberrypi/RPiSocketConnection$NoConnectionException.class
      classes14.dex
     */
    public class NoConnectionException extends Exception {
        private static final long serialVersionUID = 1;

        public NoConnectionException(String msg) {
            super(msg);
        }
    }

    public boolean isConnected() {
        return this.isConnected;
    }
}
