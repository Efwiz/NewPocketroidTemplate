package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class ReportAnalogPinMessageWriter implements IMessageWriter<ReportAnalogPinMessage> {
    public static final int COMMAND = 192;

    @Override // name.antonsmirnov.firmata.writer.IMessageWriter
    public void write(ReportAnalogPinMessage reportAnalogPinMessage, ISerial iSerial) throws SerialException {
        iSerial.write(BytesHelper.ENCODE_CHANNEL(reportAnalogPinMessage.getPin()) | 192);
        iSerial.write(reportAnalogPinMessage.isEnable() ? 1 : 0);
    }
}
