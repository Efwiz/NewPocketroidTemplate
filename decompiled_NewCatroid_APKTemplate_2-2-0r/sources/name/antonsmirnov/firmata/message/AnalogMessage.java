package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class AnalogMessage extends Message {
    private int pin;
    private int value;

    public AnalogMessage() {
    }

    public AnalogMessage(int i, int i2) {
        this();
        setPin(i);
        setValue(i2);
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(int i) {
        this.pin = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    @Override // name.antonsmirnov.firmata.message.Message
    public boolean equals(Object obj) {
        AnalogMessage analogMessage;
        return super.equals(obj) && (analogMessage = (AnalogMessage) obj) != null && analogMessage.getPin() == getPin() && analogMessage.getValue() == getValue();
    }

    public String toString() {
        return MessageFormat.format("AnalogMessage[pin={0}, value={1}]", Integer.valueOf(this.pin), Integer.valueOf(this.value));
    }
}
