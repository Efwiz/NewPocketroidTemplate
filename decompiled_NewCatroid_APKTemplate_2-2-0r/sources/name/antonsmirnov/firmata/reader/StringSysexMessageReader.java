package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.StringSysexMessage;

/* JADX INFO: loaded from: classes12.dex */
public class StringSysexMessageReader extends BaseSysexMessageReader<StringSysexMessage> {
    public StringSysexMessageReader() {
        super((byte) 113);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // name.antonsmirnov.firmata.reader.BaseSysexMessageReader
    public StringSysexMessage buildSysexMessage(byte[] bArr, int i) {
        StringSysexMessage stringSysexMessage = new StringSysexMessage();
        stringSysexMessage.setData(extractStringFromBuffer(bArr, 2, i - 2));
        return stringSysexMessage;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void fireEvent(IFirmata.Listener listener) {
        listener.onStringSysexMessageReceived((StringSysexMessage) getMessage());
    }
}
