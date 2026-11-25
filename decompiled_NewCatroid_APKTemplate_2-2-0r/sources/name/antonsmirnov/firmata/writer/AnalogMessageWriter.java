package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class AnalogMessageWriter implements IMessageWriter<AnalogMessage> {
    public static final int COMMAND = 224;

    @Override // name.antonsmirnov.firmata.writer.IMessageWriter
    public void write(AnalogMessage analogMessage, ISerial iSerial) throws SerialException {
        iSerial.write(BytesHelper.ENCODE_CHANNEL(analogMessage.getPin()) | 224);
        iSerial.write(BytesHelper.LSB(analogMessage.getValue()));
        iSerial.write(BytesHelper.MSB(analogMessage.getValue()));
    }
}
