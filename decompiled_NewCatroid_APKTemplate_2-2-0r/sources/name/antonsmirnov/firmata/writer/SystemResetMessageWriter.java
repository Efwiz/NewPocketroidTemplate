package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.message.SystemResetMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class SystemResetMessageWriter implements IMessageWriter<SystemResetMessage> {
    public static final int COMMAND = 255;

    @Override // name.antonsmirnov.firmata.writer.IMessageWriter
    public void write(SystemResetMessage systemResetMessage, ISerial iSerial) throws SerialException {
        iSerial.write(255);
    }
}
