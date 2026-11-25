package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class FirmwareVersionMessage extends SysexMessage {
    private int major;
    private int minor;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f330name;

    public FirmwareVersionMessage() {
    }

    public FirmwareVersionMessage(int i, int i2, String str) {
        this();
        setName(str);
        setMajor(i);
        setMinor(i2);
    }

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

    public String getName() {
        return this.f330name;
    }

    public void setName(String str) {
        this.f330name = str;
    }

    @Override // name.antonsmirnov.firmata.message.SysexMessage, name.antonsmirnov.firmata.message.Message
    public boolean equals(Object obj) {
        FirmwareVersionMessage firmwareVersionMessage;
        if (super.equals(obj) && (firmwareVersionMessage = (FirmwareVersionMessage) obj) != null && firmwareVersionMessage.getMajor() == getMajor() && firmwareVersionMessage.getMinor() == getMinor()) {
            return (firmwareVersionMessage.getName() == null && getName() == null) || (firmwareVersionMessage.getName() != null && firmwareVersionMessage.getName().equals(getName()));
        }
        return false;
    }

    @Override // name.antonsmirnov.firmata.message.SysexMessage
    public String toString() {
        return MessageFormat.format("FirmwareVersionMessage[major={0}, minor={1}, name=''{2}'']", Integer.valueOf(this.major), Integer.valueOf(this.minor), this.f330name);
    }
}
