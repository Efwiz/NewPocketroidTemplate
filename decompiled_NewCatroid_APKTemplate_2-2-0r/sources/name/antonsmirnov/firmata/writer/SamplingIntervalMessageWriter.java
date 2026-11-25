package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.SamplingIntervalMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class SamplingIntervalMessageWriter extends SysexMessageWriter<SamplingIntervalMessage> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // name.antonsmirnov.firmata.writer.SysexMessageWriter
    public void writeData(SamplingIntervalMessage samplingIntervalMessage, ISerial iSerial) throws SerialException {
        writeIntervalData(samplingIntervalMessage, iSerial);
    }

    private void writeIntervalData(SamplingIntervalMessage samplingIntervalMessage, ISerial iSerial) throws SerialException {
        iSerial.write(new byte[]{(byte) BytesHelper.LSB(samplingIntervalMessage.getInterval()), (byte) BytesHelper.MSB(samplingIntervalMessage.getInterval())});
    }
}
