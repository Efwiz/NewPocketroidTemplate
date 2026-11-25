package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.SysexMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class SysexMessageWriter<ConcreteSysexMessage extends SysexMessage> implements IMessageWriter<ConcreteSysexMessage> {
    public static final int COMMAND_END = 247;
    public static final int COMMAND_START = 240;

    @Override // name.antonsmirnov.firmata.writer.IMessageWriter
    public void write(ConcreteSysexMessage concretesysexmessage, ISerial iSerial) throws SerialException {
        iSerial.write(240);
        writeCommand(concretesysexmessage, iSerial);
        writeData(concretesysexmessage, iSerial);
        iSerial.write(247);
    }

    protected void writeCommand(ConcreteSysexMessage concretesysexmessage, ISerial iSerial) throws SerialException {
        iSerial.write(concretesysexmessage.getCommand());
    }

    protected void writeData(ConcreteSysexMessage concretesysexmessage, ISerial iSerial) throws SerialException {
        if (concretesysexmessage.getData() != null) {
            iSerial.write(BytesHelper.ENCODE_STRING(concretesysexmessage.getData()));
        }
    }
}
