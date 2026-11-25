package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class ProtocolVersionMessage extends Message {
    private int major;
    private int minor;

    public int getMajor() {
        return this.major;
    }

    public void setMajor(int i) {
        this.major = i;
    }

    public int getMinor() {
        return this.minor;
    }

    public void setMinor(int i) {
        this.minor = i;
    }

    public ProtocolVersionMessage() {
    }

    public ProtocolVersionMessage(int i, int i2) {
        this();
        setMajor(i);
        setMinor(i2);
    }

    @Override // name.antonsmirnov.firmata.message.Message
    public boolean equals(Object obj) {
        ProtocolVersionMessage protocolVersionMessage;
        return super.equals(obj) && (protocolVersionMessage = (ProtocolVersionMessage) obj) != null && protocolVersionMessage.getMajor() == getMajor() && protocolVersionMessage.getMinor() == getMinor();
    }

    public String toString() {
        return MessageFormat.format("ProtocolVersionMessage[major={0}, minor={1}]", Integer.valueOf(this.major), Integer.valueOf(this.minor));
    }
}
