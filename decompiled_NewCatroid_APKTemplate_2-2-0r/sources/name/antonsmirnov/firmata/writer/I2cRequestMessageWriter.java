package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.I2cRequestMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class I2cRequestMessageWriter<ConcreteRequestMessage extends I2cRequestMessage> extends SysexMessageWriter<ConcreteRequestMessage> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // name.antonsmirnov.firmata.writer.SysexMessageWriter
    public void writeData(ConcreteRequestMessage concreterequestmessage, ISerial iSerial) throws SerialException {
        writeI2cRequestData(concreterequestmessage, iSerial);
    }

    private void writeI2cRequestData(ConcreteRequestMessage concreterequestmessage, ISerial iSerial) throws SerialException {
        byte bLSB = (byte) BytesHelper.LSB(concreterequestmessage.getSlaveAddress());
        int bit = BytesHelper.setBit(BytesHelper.setBit(BytesHelper.setBit(0, 7, false), 6, false), 5, concreterequestmessage.isTenBitsMode()) | ((byte) (concreterequestmessage.getMode().getValue() << 3));
        if (concreterequestmessage.isTenBitsMode()) {
            bit |= BytesHelper.MSB(7 & concreterequestmessage.getSlaveAddress());
        }
        iSerial.write(new byte[]{bLSB, (byte) bit});
        int[] binaryData = concreterequestmessage.getBinaryData();
        if (binaryData != null) {
            iSerial.write(BytesHelper.ENCODE_INT_ARRAY(binaryData));
        }
    }
}
