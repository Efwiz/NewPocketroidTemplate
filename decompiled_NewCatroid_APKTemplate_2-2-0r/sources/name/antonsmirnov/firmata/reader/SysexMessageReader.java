package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.SysexMessage;

/* JADX INFO: loaded from: classes12.dex */
public class SysexMessageReader extends BaseSysexMessageReader<SysexMessage> {
    public SysexMessageReader() {
        super(null);
    }

    @Override // name.antonsmirnov.firmata.reader.BaseSysexMessageReader
    protected SysexMessage buildSysexMessage(byte[] bArr, int i) {
        SysexMessage sysexMessage = new SysexMessage();
        sysexMessage.setCommand(bArr[1]);
        sysexMessage.setData(extractStringFromBuffer(bArr, 2, i - 2));
        return sysexMessage;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void fireEvent(IFirmata.Listener listener) {
        listener.onSysexMessageReceived(this.message);
    }
}
