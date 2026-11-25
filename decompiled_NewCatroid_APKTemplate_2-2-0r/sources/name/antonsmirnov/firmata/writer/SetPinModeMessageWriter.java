package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.message.SetPinModeMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class SetPinModeMessageWriter implements IMessageWriter<SetPinModeMessage> {
    public static final int COMMAND = 244;

    @Override // name.antonsmirnov.firmata.writer.IMessageWriter
    public void write(SetPinModeMessage setPinModeMessage, ISerial iSerial) throws SerialException {
        iSerial.write(244);
        iSerial.write(setPinModeMessage.getPin());
        iSerial.write(setPinModeMessage.getMode());
    }
}
