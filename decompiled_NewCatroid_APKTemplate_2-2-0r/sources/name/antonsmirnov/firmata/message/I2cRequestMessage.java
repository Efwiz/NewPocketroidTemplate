package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes12.dex */
public class I2cRequestMessage extends SysexMessage {
    public static final int COMMAND = 118;
    protected static FormatHelper formatHelper = new FormatHelper();
    private int[] binaryData;
    private MODE mode;
    private int slaveAddress;
    private boolean tenBitsMode;

    public I2cRequestMessage() {
        super(118, null);
    }

    public int getSlaveAddress() {
        return this.slaveAddress;
    }

    public void setSlaveAddress(int i) {
        this.slaveAddress = i;
    }

    public boolean isTenBitsMode() {
        return this.tenBitsMode;
    }

    public void setTenBitsMode(boolean z) {
        this.tenBitsMode = z;
    }

    public enum MODE {
        WRITE(0),
        READ_ONCE(1),
        READ_CONTINUOUSLY(2),
        STOP_READING(3);

        private int value;

        private void setValue(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

        MODE(int i) {
            setValue(i);
        }
    }

    public MODE getMode() {
        return this.mode;
    }

    public void setMode(MODE mode) {
        this.mode = mode;
    }

    public int[] getBinaryData() {
        return this.binaryData;
    }

    public void setBinaryData(int[] iArr) {
        this.binaryData = iArr;
    }

    @Override // name.antonsmirnov.firmata.message.SysexMessage
    public String toString() {
        String binary = formatHelper.formatBinary(this.slaveAddress);
        Boolean boolValueOf = Boolean.valueOf(this.tenBitsMode);
        MODE mode = this.mode;
        int[] iArr = this.binaryData;
        return MessageFormat.format("I2cRequestMessage[slaveAddress={0}, 10bitsMode={1}, mode={2}, binaryData={3}]", binary, boolValueOf, mode, iArr != null ? formatHelper.formatBinaryData(iArr) : "null");
    }
}
