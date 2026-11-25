package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class ReportAnalogPinMessage extends Message {
    private boolean enable;
    private int pin;

    public ReportAnalogPinMessage() {
    }

    public ReportAnalogPinMessage(int i, boolean z) {
        this();
        setPin(i);
        setEnable(z);
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(int i) {
        this.pin = i;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean z) {
        this.enable = z;
    }

    @Override // name.antonsmirnov.firmata.message.Message
    public boolean equals(Object obj) {
        ReportAnalogPinMessage reportAnalogPinMessage;
        return super.equals(obj) && (reportAnalogPinMessage = (ReportAnalogPinMessage) obj) != null && reportAnalogPinMessage.getPin() == getPin() && reportAnalogPinMessage.isEnable() == isEnable();
    }

    public String toString() {
        return MessageFormat.format("ReportAnalogPinMessage[pin={0}, enable={1}]", Integer.valueOf(this.pin), Boolean.valueOf(this.enable));
    }
}
