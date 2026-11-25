package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class DigitalMessageWriter implements IMessageWriter<DigitalMessage> {
    public static final int COMMAND = 144;

    @Override // name.antonsmirnov.firmata.writer.IMessageWriter
    public void write(DigitalMessage digitalMessage, ISerial iSerial) throws SerialException {
        iSerial.write(BytesHelper.ENCODE_CHANNEL(digitalMessage.getPort()) | 144);
        iSerial.write(BytesHelper.LSB(digitalMessage.getValue()));
        iSerial.write(BytesHelper.MSB(digitalMessage.getValue()));
    }
}
