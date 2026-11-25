package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes12.dex */
public class I2cReadRequestMessage extends I2cRequestMessage {
    private Integer bytesToRead;
    private Integer slaveRegister;

    public Integer getSlaveRegister() {
        return this.slaveRegister;
    }

    public void setSlaveRegister(Integer num) {
        this.slaveRegister = num;
    }

    public Integer getBytesToRead() {
        return this.bytesToRead;
    }

    public void setBytesToRead(Integer num) {
        this.bytesToRead = num;
    }

    @Override // name.antonsmirnov.firmata.message.I2cRequestMessage
    public int[] getBinaryData() {
        validateBytesToRead();
        int i = AnonymousClass1.$SwitchMap$name$antonsmirnov$firmata$message$I2cRequestMessage$MODE[getMode().ordinal()];
        if (i == 1) {
            return getReadOnlyBinaryData();
        }
        if (i == 2) {
            return getReadContinuouslyBinaryData();
        }
        throw new RuntimeException("Mode should be ReadOnce or ReadContinuously");
    }

    /* JADX INFO: renamed from: name.antonsmirnov.firmata.message.I2cReadRequestMessage$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$name$antonsmirnov$firmata$message$I2cRequestMessage$MODE;

        static {
            int[] iArr = new int[I2cRequestMessage.MODE.values().length];
            $SwitchMap$name$antonsmirnov$firmata$message$I2cRequestMessage$MODE = iArr;
            try {
                iArr[I2cRequestMessage.MODE.READ_ONCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$name$antonsmirnov$firmata$message$I2cRequestMessage$MODE[I2cRequestMessage.MODE.READ_CONTINUOUSLY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private void validateBytesToRead() {
        if (this.bytesToRead == null) {
            throw new RuntimeException("bytesToRead field is not specified and is required");
        }
    }

    private int[] getReadOnlyBinaryData() {
        Integer num = this.slaveRegister;
        return num != null ? new int[]{num.intValue(), this.bytesToRead.intValue()} : new int[]{this.bytesToRead.intValue()};
    }

    private int[] getReadContinuouslyBinaryData() {
        validateSlaveRegister();
        validateBytesToRead();
        return new int[]{this.slaveRegister.intValue(), this.bytesToRead.intValue()};
    }

    private void validateSlaveRegister() {
        if (this.slaveRegister == null) {
            throw new RuntimeException("slaveRegister is not specified and in required in ReadContinuously mode");
        }
    }

    @Override // name.antonsmirnov.firmata.message.I2cRequestMessage, name.antonsmirnov.firmata.message.SysexMessage
    public String toString() {
        return MessageFormat.format("I2cReadRequestMessage[slaveAddress={0}, 10bitsMode={1}, mode={2}, slaveReg={3}, bytesToRead={4}]", formatHelper.formatBinary(getSlaveAddress()), Boolean.valueOf(isTenBitsMode()), getMode(), this.slaveRegister != null ? formatHelper.formatBinary(this.slaveRegister.intValue()) : "null", this.bytesToRead);
    }
}
