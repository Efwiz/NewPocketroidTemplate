package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.message.ReportProtocolVersionMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class ReportProtocolVersionMessageWriter implements IMessageWriter<ReportProtocolVersionMessage> {
    public static final int COMMAND = 249;

    @Override // name.antonsmirnov.firmata.writer.IMessageWriter
    public void write(ReportProtocolVersionMessage reportProtocolVersionMessage, ISerial iSerial) throws SerialException {
        iSerial.write(249);
    }
}
