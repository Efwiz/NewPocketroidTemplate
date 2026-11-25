package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class SysexMessage extends Message {
    private int command;
    private String data;

    public SysexMessage() {
    }

    public SysexMessage(int i, String str) {
        this();
        setCommand(i);
        setData(str);
    }

    public int getCommand() {
        return this.command;
    }

    public void setCommand(int i) {
        this.command = i;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    @Override // name.antonsmirnov.firmata.message.Message
    public boolean equals(Object obj) {
        SysexMessage sysexMessage;
        if (super.equals(obj) && (sysexMessage = (SysexMessage) obj) != null && sysexMessage.getCommand() == getCommand()) {
            return (sysexMessage.getData() == null && getData() == null) || (sysexMessage.getData() != null && sysexMessage.getData().equals(getData()));
        }
        return false;
    }

    public String toString() {
        return MessageFormat.format("SysexMessage[command={0}, data=\"{1}\"]", Integer.valueOf(this.command), this.data);
    }
}
