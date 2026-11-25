package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class StringSysexMessage extends SysexMessage {
    public static final int COMMAND = 113;

    public StringSysexMessage() {
        super(113, null);
    }

    public StringSysexMessage(String str) {
        this();
        setData(str);
    }

    @Override // name.antonsmirnov.firmata.message.SysexMessage
    public String toString() {
        return MessageFormat.format("StringSysexMessage[data=\"{0}\"]", getData());
    }
}
