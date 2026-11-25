package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.AnalogMessage;

/* JADX INFO: loaded from: classes12.dex */
public class AnalogMessageReader implements IMessageReader<AnalogMessage> {
    private boolean isHandling;
    private AnalogMessage message;

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public boolean canRead(byte[] bArr, int i, int i2) {
        return i2 == 224;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void startReading() {
        this.isHandling = true;
        this.message = new AnalogMessage();
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void read(byte[] bArr, int i) {
        if (i == 2) {
            this.message.setPin(BytesHelper.DECODE_CHANNEL(bArr[0]));
        } else {
            this.message.setValue(BytesHelper.DECODE_BYTE(bArr[1], bArr[2]));
            this.isHandling = false;
        }
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public boolean finishedReading() {
        return !this.isHandling;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public AnalogMessage getMessage() {
        return this.message;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void fireEvent(IFirmata.Listener listener) {
        listener.onAnalogMessageReceived(getMessage());
    }
}
