package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class DigitalMessage extends Message {
    private int port;
    private int value;

    public DigitalMessage() {
    }

    public DigitalMessage(int i, int i2) {
        this();
        setPort(i);
        setValue(i2);
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    @Override // name.antonsmirnov.firmata.message.Message
    public boolean equals(Object obj) {
        DigitalMessage digitalMessage;
        return super.equals(obj) && (digitalMessage = (DigitalMessage) obj) != null && digitalMessage.getPort() == getPort() && digitalMessage.getValue() == getValue();
    }

    public String toString() {
        return MessageFormat.format("DigitalMessage[port={0}, value={1}]", Integer.valueOf(this.port), Integer.valueOf(this.value));
    }
}
