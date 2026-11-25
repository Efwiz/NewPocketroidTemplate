package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.I2cReplyMessage;

/* JADX INFO: loaded from: classes12.dex */
public class I2cReplyMessageReader extends BaseSysexMessageReader<I2cReplyMessage> {
    public I2cReplyMessageReader() {
        super((byte) 119);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // name.antonsmirnov.firmata.reader.BaseSysexMessageReader
    public I2cReplyMessage buildSysexMessage(byte[] bArr, int i) {
        I2cReplyMessage i2cReplyMessage = new I2cReplyMessage();
        i2cReplyMessage.setSlaveAddress(BytesHelper.DECODE_BYTE(bArr[2], bArr[3]));
        i2cReplyMessage.setRegister(BytesHelper.DECODE_BYTE(bArr[4], bArr[5]));
        i2cReplyMessage.setBinaryData(extractIntArrayFromBuffer(bArr, 6, i - 2));
        return i2cReplyMessage;
    }

    @Override // name.antonsmirnov.firmata.reader.IMessageReader
    public void fireEvent(IFirmata.Listener listener) {
        listener.onI2cMessageReceived((I2cReplyMessage) getMessage());
    }
}
