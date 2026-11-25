package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.ServoConfigMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class ServoConfigMessageWriter extends SysexMessageWriter<ServoConfigMessage> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // name.antonsmirnov.firmata.writer.SysexMessageWriter
    public void writeData(ServoConfigMessage servoConfigMessage, ISerial iSerial) throws SerialException {
        writeServoData(servoConfigMessage, iSerial);
    }

    private void writeServoData(ServoConfigMessage servoConfigMessage, ISerial iSerial) throws SerialException {
        iSerial.write(new byte[]{(byte) servoConfigMessage.getPin(), (byte) BytesHelper.LSB(servoConfigMessage.getMinPulse()), (byte) BytesHelper.MSB(servoConfigMessage.getMinPulse()), (byte) BytesHelper.LSB(servoConfigMessage.getMaxPulse()), (byte) BytesHelper.MSB(servoConfigMessage.getMaxPulse()), (byte) BytesHelper.LSB(servoConfigMessage.getAngle()), (byte) BytesHelper.MSB(servoConfigMessage.getAngle())});
    }
}
