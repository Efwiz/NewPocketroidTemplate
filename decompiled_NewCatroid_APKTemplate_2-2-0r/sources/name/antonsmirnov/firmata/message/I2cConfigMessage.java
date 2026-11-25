package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class I2cConfigMessage extends SysexMessage {
    public static final int COMMAND = 120;
    private int delay;
    private boolean on;

    public I2cConfigMessage() {
        super(120, null);
    }

    public boolean isOn() {
        return this.on;
    }

    public void setOn(boolean z) {
        this.on = z;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int i) {
        this.delay = i;
    }

    @Override // name.antonsmirnov.firmata.message.SysexMessage
    public String toString() {
        return MessageFormat.format("I2cConfigMessage[on={0}, delay={1}]", Boolean.valueOf(this.on), Integer.valueOf(this.delay));
    }
}
