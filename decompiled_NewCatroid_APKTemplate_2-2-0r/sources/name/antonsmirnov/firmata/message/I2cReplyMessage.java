package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;
import java.util.Arrays;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes12.dex */
public class I2cReplyMessage extends SysexMessage {
    public static final int COMMAND = 119;
    protected static FormatHelper formatHelper = new FormatHelper();
    private int[] binaryData;
    private int register;
    private int slaveAddress;

    public I2cReplyMessage() {
        super(119, null);
    }

    public I2cReplyMessage(int i, int i2, int[] iArr) {
        this();
        setSlaveAddress(i);
        setRegister(i2);
        setBinaryData(iArr);
    }

    public int getSlaveAddress() {
        return this.slaveAddress;
    }

    public void setSlaveAddress(int i) {
        this.slaveAddress = i;
    }

    public int getRegister() {
        return this.register;
    }

    public void setRegister(int i) {
        this.register = i;
    }

    public int[] getBinaryData() {
        return this.binaryData;
    }

    public void setBinaryData(int[] iArr) {
        this.binaryData = iArr;
    }

    @Override // name.antonsmirnov.firmata.message.SysexMessage, name.antonsmirnov.firmata.message.Message
    public boolean equals(Object obj) {
        I2cReplyMessage i2cReplyMessage;
        if (super.equals(obj) && (i2cReplyMessage = (I2cReplyMessage) obj) != null && i2cReplyMessage.getSlaveAddress() == getSlaveAddress() && i2cReplyMessage.getRegister() == getRegister()) {
            return (i2cReplyMessage.getBinaryData() == null && getBinaryData() == null) || Arrays.equals(i2cReplyMessage.getBinaryData(), getBinaryData());
        }
        return false;
    }

    @Override // name.antonsmirnov.firmata.message.SysexMessage
    public String toString() {
        return MessageFormat.format("I2cReplyMessage[slaveAddress={0}, register={1}, binaryData={2}]", formatHelper.formatBinary(this.slaveAddress), Integer.valueOf(this.register), formatHelper.formatBinaryData(this.binaryData));
    }
}
