package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class ReportDigitalPortMessage extends Message {
    private boolean enable;
    private int port;

    public ReportDigitalPortMessage() {
    }

    public ReportDigitalPortMessage(int i, boolean z) {
        this();
        setPort(i);
        setEnable(z);
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean z) {
        this.enable = z;
    }

    @Override // name.antonsmirnov.firmata.message.Message
    public boolean equals(Object obj) {
        ReportDigitalPortMessage reportDigitalPortMessage;
        return super.equals(obj) && (reportDigitalPortMessage = (ReportDigitalPortMessage) obj) != null && reportDigitalPortMessage.getPort() == getPort() && reportDigitalPortMessage.isEnable() == isEnable();
    }

    public String toString() {
        return MessageFormat.format("ReportDigitalPortMessage[port={0}, enable={1}]", Integer.valueOf(this.port), Boolean.valueOf(this.enable));
    }
}
