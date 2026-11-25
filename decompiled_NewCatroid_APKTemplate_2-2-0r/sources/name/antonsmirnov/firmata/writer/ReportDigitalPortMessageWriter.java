package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.ReportDigitalPortMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class ReportDigitalPortMessageWriter implements IMessageWriter<ReportDigitalPortMessage> {
    public static final int COMMAND = 208;

    @Override // name.antonsmirnov.firmata.writer.IMessageWriter
    public void write(ReportDigitalPortMessage reportDigitalPortMessage, ISerial iSerial) throws SerialException {
        iSerial.write(BytesHelper.ENCODE_CHANNEL(reportDigitalPortMessage.getPort()) | 208);
        iSerial.write(reportDigitalPortMessage.isEnable() ? 1 : 0);
    }
}
