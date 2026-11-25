package org.catrobat.catroid.devices.mindstorms;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/MindstormsConnectionImpl.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MindstormsConnectionImpl implements MindstormsConnection {
    private BluetoothConnection bluetoothConnection;
    private OutputStream legoOutputStream = null;
    private DataInputStream legoInputStream = null;
    private boolean isConnected = false;
    private short commandCounter = 1;

    public MindstormsConnectionImpl(BluetoothConnection btConnection) {
        this.bluetoothConnection = btConnection;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsConnection
    public void init() throws MindstormsException {
        try {
            this.legoInputStream = new DataInputStream(this.bluetoothConnection.getInputStream());
            this.legoOutputStream = this.bluetoothConnection.getOutputStream();
            this.isConnected = true;
        } catch (IOException e) {
            this.isConnected = false;
            throw new MindstormsException(e, "Cannot establish BtConnection");
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsConnection
    public boolean isConnected() {
        return this.isConnected;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsConnection
    public void disconnect() {
        this.isConnected = false;
        this.bluetoothConnection.disconnect();
        this.legoInputStream = null;
        this.legoOutputStream = null;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsConnection
    public synchronized byte[] sendAndReceive(MindstormsCommand command) throws MindstormsException {
        send(command);
        return receive();
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsConnection
    public void send(MindstormsCommand command) throws MindstormsException {
        try {
            int length = command.getLength();
            byte[] rawCommand = command.getRawCommand();
            byte[] bArr = new byte[command.getLength() + 2];
            bArr[0] = (byte) (length & 255);
            bArr[1] = (byte) ((65280 & length) >> 8);
            System.arraycopy(rawCommand, 0, bArr, 2, length);
            synchronized (this.legoOutputStream) {
                this.legoOutputStream.write(bArr, 0, length + 2);
                this.legoOutputStream.flush();
            }
        } catch (IOException e) {
            throw new MindstormsException(e, "Error on message send.");
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsConnection
    public short getCommandCounter() {
        return this.commandCounter;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsConnection
    public void incCommandCounter() {
        this.commandCounter = (short) (this.commandCounter + 1);
    }

    protected byte[] receive() throws MindstormsException {
        byte[] bArr = new byte[2];
        try {
            this.legoInputStream.readFully(bArr, 0, 2);
            int i = (bArr[0] & 255) | ((bArr[1] & 255) << 8);
            byte[] bArr2 = new byte[i];
            this.legoInputStream.readFully(bArr2, 0, i);
            return bArr2;
        } catch (IOException e) {
            throw new MindstormsException(e, "Read Error");
        }
    }
}
