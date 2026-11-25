package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;

/* JADX INFO: loaded from: classes12.dex */
public class ProtocolVersionMessageReader implements IMessageReader<ProtocolVersionMessage> {
    private boolean isHandling;
    private ProtocolVersionMessage message;

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public boolean canRead(byte[] bArr, int i, int i2) {
        return i == 1 && bArr[0] == -7;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void startReading() {
        this.isHandling = true;
        this.message = new ProtocolVersionMessage();
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void read(byte[] bArr, int i) {
        byte b = bArr[i - 1];
        if (i == 2) {
            this.message.setMajor(b);
        } else {
            this.message.setMinor(b);
            this.isHandling = false;
        }
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public boolean finishedReading() {
        return !this.isHandling;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public ProtocolVersionMessage getMessage() {
        return this.message;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void fireEvent(IFirmata.Listener listener) {
        listener.onProtocolVersionMessageReceived(getMessage());
    }
}
