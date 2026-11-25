package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public interface IMessageWriter<ConcreteMessage extends Message> {
    void write(ConcreteMessage concretemessage, ISerial iSerial) throws SerialException;
}
