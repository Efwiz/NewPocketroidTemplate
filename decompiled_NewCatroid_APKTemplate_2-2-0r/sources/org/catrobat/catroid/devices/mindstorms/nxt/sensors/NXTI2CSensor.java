package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import android.util.Log;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.mindstorms.nxt.Command;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandByte;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandType;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTError;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTException;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTReply;
import org.catrobat.catroid.utils.Stopwatch;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTI2CSensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class NXTI2CSensor extends NXTSensor {
    private static final byte BYTES_READ_BYTE = 3;
    private static final String TAG = "NXTI2CSensor";
    private byte address;
    private int pendingCommunicationErrorWaitTime;
    private final int requestTimeout;

    public NXTI2CSensor(byte sensorAddress, NXTSensorType sensorType, MindstormsConnection connection) {
        super(3, sensorType, NXTSensorMode.RAW, connection);
        this.requestTimeout = 500;
        this.address = sensorAddress;
        this.pendingCommunicationErrorWaitTime = 30;
    }

    public byte getI2CAddress() {
        return this.address;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor
    protected void initialize() throws MindstormsException {
        super.initialize();
        readRegister(0, 1);
    }

    protected void writeRegister(byte register, byte data, boolean reply) throws MindstormsException {
        if (!this.hasInit) {
            initialize();
        }
        write(new byte[]{this.address, register, data}, (byte) 0, reply);
    }

    protected byte[] readRegister(int register, int rxLength) throws MindstormsException {
        if (!this.hasInit) {
            initialize();
        }
        return writeAndRead(new byte[]{this.address, (byte) register}, (byte) rxLength);
    }

    private void waitForBytes(byte numberOfBytes) throws MindstormsException {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        while (tryGetNumberOfBytesAreReadyToRead() != numberOfBytes && stopwatch.getElapsedMilliseconds() < 500) {
        }
        if (stopwatch.getElapsedMilliseconds() > 500) {
            throw new NXTException("RequestTimeout while waiting on bytes Ready, waited " + stopwatch.getElapsedMilliseconds() + LanguageCodeUtil.MS);
        }
    }

    private byte tryGetNumberOfBytesAreReadyToRead() throws MindstormsException {
        try {
            return getNumberOfBytesAreReadyToRead();
        } catch (NXTException e) {
            if (e.getError() == NXTError.ErrorCode.PendingCommunication) {
                Log.e(TAG, "Pending Coummunication Error occured, wait for " + this.pendingCommunicationErrorWaitTime + "ms and try again.");
                wait(this.pendingCommunicationErrorWaitTime);
                return (byte) 0;
            }
            throw e;
        }
    }

    protected byte[] writeAndRead(byte[] data, byte rxLength) throws MindstormsException {
        write(data, rxLength, false);
        waitForBytes(rxLength);
        return read();
    }

    public void write(byte[] txData, byte rxLength, boolean reply) throws MindstormsException {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.LS_WRITE, reply);
        command.append((byte) this.port);
        command.append((byte) txData.length);
        command.append(rxLength);
        command.append(txData);
        if (reply) {
            NXTError.checkForError(new NXTReply(this.connection.sendAndReceive(command)), 5);
        } else {
            this.connection.send(command);
        }
    }

    public byte[] read() throws MindstormsException {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.LS_READ, true);
        command.append((byte) this.port);
        NXTReply nXTReply = new NXTReply(this.connection.sendAndReceive(command));
        NXTError.checkForError(nXTReply, 20);
        return nXTReply.getData(4, nXTReply.getByte(3));
    }

    public byte getNumberOfBytesAreReadyToRead() throws MindstormsException {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.LS_GET_STATUS, true);
        command.append((byte) this.port);
        NXTReply nXTReply = new NXTReply(this.connection.sendAndReceive(command));
        NXTError.checkForError(nXTReply, 4);
        return nXTReply.getByte(3);
    }

    protected void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Log.w(TAG, "Shouldn't be interrupted", e);
        }
    }
}
