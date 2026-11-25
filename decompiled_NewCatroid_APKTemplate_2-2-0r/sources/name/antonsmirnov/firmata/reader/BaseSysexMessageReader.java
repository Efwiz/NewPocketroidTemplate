package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.SysexMessage;

/* JADX INFO: loaded from: classes12.dex */
public abstract class BaseSysexMessageReader<ConcreteSysexMessage extends SysexMessage> implements IMessageReader<ConcreteSysexMessage> {
    protected boolean isReading;
    protected ConcreteSysexMessage message;
    private Byte sysexCommand;

    protected abstract ConcreteSysexMessage buildSysexMessage(byte[] bArr, int i);

    public BaseSysexMessageReader(Byte b) {
        this.sysexCommand = b;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public boolean canRead(byte[] bArr, int i, int i2) {
        Byte b;
        return (i == 1 && bArr[0] == -16) || (i == 2 && ((b = this.sysexCommand) == null || b.equals(Byte.valueOf(bArr[1])))) || (i == 3 && this.sysexCommand != null);
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void startReading() {
        this.isReading = true;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void read(byte[] bArr, int i) {
        if (bArr[i - 1] == -9) {
            this.isReading = false;
            this.message = (ConcreteSysexMessage) buildSysexMessage(bArr, i);
        }
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public ConcreteSysexMessage getMessage() {
        return this.message;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public boolean finishedReading() {
        return !this.isReading;
    }

    protected void validateSysexDataLength(int i, int i2) {
        if (((i2 - i) + 1) % 2 != 0) {
            throw new RuntimeException("Sysex command data length should be even");
        }
    }

    protected String extractStringFromBuffer(byte[] bArr, int i, int i2) {
        validateSysexDataLength(i, i2);
        return BytesHelper.DECODE_STRING(bArr, i, i2);
    }

    protected int[] extractIntArrayFromBuffer(byte[] bArr, int i, int i2) {
        validateSysexDataLength(i, i2);
        return BytesHelper.DECODE_INT_ARRAY(bArr, i, i2);
    }
}
