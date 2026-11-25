package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class ServoConfigMessage extends SysexMessage {
    public static final int COMMAND = 112;
    private int angle;
    private int maxPulse;
    private int minPulse;
    private int pin;

    public ServoConfigMessage() {
        super(112, null);
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(int i) {
        this.pin = i;
    }

    public int getMinPulse() {
        return this.minPulse;
    }

    public void setMinPulse(int i) {
        this.minPulse = i;
    }

    public int getMaxPulse() {
        return this.maxPulse;
    }

    public void setMaxPulse(int i) {
        this.maxPulse = i;
    }

    public int getAngle() {
        return this.angle;
    }

    public void setAngle(int i) {
        this.angle = i;
    }

    @Override // name.antonsmirnov.firmata.message.SysexMessage, name.antonsmirnov.firmata.message.Message
    public boolean equals(Object obj) {
        ServoConfigMessage servoConfigMessage;
        return obj != null && obj.getClass().equals(getClass()) && (servoConfigMessage = (ServoConfigMessage) obj) != null && servoConfigMessage.getPin() == getPin();
    }

    @Override // name.antonsmirnov.firmata.message.SysexMessage
    public String toString() {
        return MessageFormat.format("ServoConfigMessage[pin={0}, minPulse={1}, maxPulse={2}, angle={3}]", Integer.valueOf(this.pin), Integer.valueOf(this.minPulse), Integer.valueOf(this.maxPulse), Integer.valueOf(this.angle));
    }
}
