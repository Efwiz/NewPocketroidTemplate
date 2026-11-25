package name.antonsmirnov.firmata.message;

/* JADX INFO: loaded from: classes12.dex */
public class ReportFirmwareVersionMessage extends SysexMessage {
    public static final int COMMAND = 121;

    public ReportFirmwareVersionMessage() {
        super(121, null);
    }

    @Override // name.antonsmirnov.firmata.message.SysexMessage
    public String toString() {
        return "ReportFirmwareVersionMessage[]";
    }
}
