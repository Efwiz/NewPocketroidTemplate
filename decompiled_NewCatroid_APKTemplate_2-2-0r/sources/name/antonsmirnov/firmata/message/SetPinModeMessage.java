package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes12.dex */
public class SetPinModeMessage extends Message {
    private static Map<Integer, PIN_MODE> modes = new HashMap();
    private int mode;
    private int pin;

    public enum PIN_MODE {
        INPUT(0),
        OUTPUT(1),
        ANALOG(2),
        PWM(3),
        SERVO(4);

        private int mode;

        public int getMode() {
            return this.mode;
        }

        PIN_MODE(int i) {
            this.mode = i;
            SetPinModeMessage.modes.put(Integer.valueOf(i), this);
        }

        public static PIN_MODE find(int i) {
            return (PIN_MODE) SetPinModeMessage.modes.get(Integer.valueOf(i));
        }
    }

    protected SetPinModeMessage() {
    }

    public SetPinModeMessage(int i, int i2) {
        this();
        setPin(i);
        setMode(i2);
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(int i) {
        this.pin = i;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int i) {
        this.mode = i;
    }

    @Override // name.antonsmirnov.firmata.message.Message
    public boolean equals(Object obj) {
        SetPinModeMessage setPinModeMessage;
        return super.equals(obj) && (setPinModeMessage = (SetPinModeMessage) obj) != null && setPinModeMessage.getPin() == getPin() && setPinModeMessage.getMode() == getMode();
    }

    public String toString() {
        return MessageFormat.format("SetPinModeMessage[pin={0}, mode={1}]", Integer.valueOf(this.pin), Integer.valueOf(this.mode));
    }
}
