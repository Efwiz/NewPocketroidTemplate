package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.I2cConfigMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class I2cConfigMessageWriter extends SysexMessageWriter<I2cConfigMessage> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // name.antonsmirnov.firmata.writer.SysexMessageWriter
    public void writeData(I2cConfigMessage i2cConfigMessage, ISerial iSerial) throws SerialException {
        writeI2cConfigData(i2cConfigMessage, iSerial);
    }

    private void writeI2cConfigData(I2cConfigMessage i2cConfigMessage, ISerial iSerial) throws SerialException {
        iSerial.write(new byte[]{i2cConfigMessage.isOn() ? (byte) 1 : (byte) 0, (byte) BytesHelper.LSB(i2cConfigMessage.getDelay()), (byte) BytesHelper.MSB(i2cConfigMessage.getDelay())});
    }
}
