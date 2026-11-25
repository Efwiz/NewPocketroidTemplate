package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.FirmwareVersionMessage;

/* JADX INFO: loaded from: classes12.dex */
public class FirmwareVersionMessageReader extends BaseSysexMessageReader<FirmwareVersionMessage> {
    public FirmwareVersionMessageReader() {
        super((byte) 121);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // name.antonsmirnov.firmata.reader.BaseSysexMessageReader
    public FirmwareVersionMessage buildSysexMessage(byte[] bArr, int i) {
        FirmwareVersionMessage firmwareVersionMessage = new FirmwareVersionMessage();
        firmwareVersionMessage.setMajor(bArr[2]);
        firmwareVersionMessage.setMinor(bArr[3]);
        firmwareVersionMessage.setName(extractStringFromBuffer(bArr, 4, i - 2));
        return firmwareVersionMessage;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void fireEvent(IFirmata.Listener listener) {
        listener.onFirmwareVersionMessageReceived((FirmwareVersionMessage) this.message);
    }
}
