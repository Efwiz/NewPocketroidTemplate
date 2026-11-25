package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class SamplingIntervalMessage extends SysexMessage {
    public static final int COMMAND = 122;
    private int interval;

    public SamplingIntervalMessage() {
        super(122, null);
    }

    public int getInterval() {
        return this.interval;
    }

    public void setInterval(int i) {
        this.interval = i;
    }

    @Override // name.antonsmirnov.firmata.message.SysexMessage
    public String toString() {
        return MessageFormat.format("SamplingIntervalMessage[interval={0}]", Integer.valueOf(this.interval));
    }
}
