package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.Message;

/* JADX INFO: loaded from: classes12.dex */
public interface IMessageReader<ConcreteMessage extends Message> {
    boolean canRead(byte[] bArr, int i, int i2);

    boolean finishedReading();

    void fireEvent(IFirmata.Listener listener);

    ConcreteMessage getMessage();

    void read(byte[] bArr, int i);

    void startReading();
}
