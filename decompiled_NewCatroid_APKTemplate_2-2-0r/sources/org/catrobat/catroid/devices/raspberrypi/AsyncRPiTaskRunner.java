package org.catrobat.catroid.devices.raspberrypi;

import android.os.AsyncTask;
import android.util.Log;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/raspberrypi/AsyncRPiTaskRunner.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AsyncRPiTaskRunner {
    private static final String TAG = "AsyncRPiTaskRunner";
    private boolean connected;
    private RPiSocketConnection connection = new RPiSocketConnection();

    public boolean connect(String host, int port) {
        try {
            this.connected = new AsyncConnectTask(this.connection, host, port).execute(new Void[0]).get(2000L, TimeUnit.MILLISECONDS).booleanValue();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "RPi connecting took too long" + e);
            return false;
        }
    }

    public RPiSocketConnection getConnection() {
        return this.connection;
    }

    public void disconnect() {
        if (this.connected) {
            new AsyncDisconnectTask(this.connection).execute(new Void[0]);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/raspberrypi/AsyncRPiTaskRunner$AsyncConnectTask.class
      classes14.dex
     */
    private static class AsyncConnectTask extends AsyncTask<Void, Void, Boolean> {
        private RPiSocketConnection connection;
        private String host;
        private int port;

        AsyncConnectTask(RPiSocketConnection connection, String host, int port) {
            this.connection = connection;
            this.host = host;
            this.port = port;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void... args) {
            try {
                this.connection.connect(this.host, this.port);
                Iterator<Integer> it = RaspberryPiService.getInstance().getPinInterrupts().iterator();
                while (it.hasNext()) {
                    this.connection.activatePinInterrupt(it.next().intValue());
                }
                return true;
            } catch (ConnectException unused) {
                Log.e(AsyncRPiTaskRunner.TAG, "RPi: Could not connect!");
                return false;
            } catch (SocketTimeoutException unused2) {
                Log.e(AsyncRPiTaskRunner.TAG, "RPi: Connection timeout!");
                return false;
            } catch (UnknownHostException unused3) {
                Log.e(AsyncRPiTaskRunner.TAG, "RPi: Host not found!");
                return false;
            } catch (Exception e) {
                Log.e(AsyncRPiTaskRunner.TAG, "Exception during connect: " + e);
                return false;
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/raspberrypi/AsyncRPiTaskRunner$AsyncDisconnectTask.class
      classes14.dex
     */
    private static class AsyncDisconnectTask extends AsyncTask<Void, Void, Void> {
        private RPiSocketConnection connection;

        AsyncDisconnectTask(RPiSocketConnection connection) {
            this.connection = connection;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voids) {
            try {
                this.connection.disconnect();
                return null;
            } catch (Exception e) {
                Log.e(AsyncRPiTaskRunner.TAG, "RPi: Exception during disconnect " + e);
                return null;
            }
        }
    }
}
