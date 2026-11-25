package name.antonsmirnov.firmata.message.factory;

import java.text.MessageFormat;
import java.util.Arrays;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.message.ReportDigitalPortMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage;

/* JADX INFO: loaded from: classes12.dex */
public abstract class BoardMessageFactory implements MessageFactory {
    public static final int MIN_PIN = 0;
    protected int[] analogInPins;
    protected int[] analogOutPins;
    protected int maxPin;
    protected int minPin;

    public int getMinPin() {
        return this.minPin;
    }

    public int getMaxPin() {
        return this.maxPin;
    }

    public int[] getAnalogInPins() {
        return this.analogInPins;
    }

    public int[] getAnalogOutPins() {
        return this.analogOutPins;
    }

    protected static int[] union(int[] iArr, int[] iArr2) {
        int[] iArr3 = new int[iArr.length + iArr2.length];
        System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
        System.arraycopy(iArr2, 0, iArr3, iArr.length, iArr2.length);
        return iArr3;
    }

    protected static int[] arrayFromTo(int i, int i2) {
        int i3 = (i2 - i) + 1;
        int[] iArr = new int[i3];
        int i4 = 0;
        while (i4 < i3) {
            iArr[i4] = i;
            i4++;
            i++;
        }
        return iArr;
    }

    public BoardMessageFactory(int i, int i2, int[] iArr, int[] iArr2) {
        this.minPin = i;
        this.maxPin = i2;
        this.analogInPins = iArr;
        this.analogOutPins = iArr2;
        Arrays.sort(iArr);
        Arrays.sort(iArr2);
    }

    protected void validatePin(int i) throws MessageValidationException {
        if (i < this.minPin || i > this.maxPin) {
            throw new MessageValidationException(MessageFormat.format("Allowed pin values are [{0}-{1}]", Integer.valueOf(this.minPin), Integer.valueOf(this.maxPin)));
        }
    }

    protected void validatePort(int i) throws MessageValidationException {
        int iCeil = (int) Math.ceil(((double) (this.maxPin + 1)) / 8.0d);
        if (i < 0 || i > iCeil) {
            throw new MessageValidationException(MessageFormat.format("Allowed port values are [{0}-{1}]", 0, Integer.valueOf(iCeil)));
        }
    }

    protected void validateAnalogIn(int i) throws MessageValidationException {
        int[] iArr = this.analogInPins;
        if (Arrays.binarySearch(iArr, i) < 0) {
            throw new MessageValidationException(MessageFormat.format("Allowed analog in pins are [{0}]", arrayToString(iArr)));
        }
    }

    public boolean isAnalogIn(int i) {
        try {
            validateAnalogIn(i);
            return true;
        } catch (MessageValidationException unused) {
            return false;
        }
    }

    protected void validateAnalogOut(int i) throws MessageValidationException {
        int[] iArr = this.analogOutPins;
        if (Arrays.binarySearch(iArr, i) < 0) {
            throw new MessageValidationException(MessageFormat.format("Allowed analog out (PWM) pins are [{0}]", arrayToString(iArr)));
        }
    }

    public boolean isAnalogOut(int i) {
        try {
            validateAnalogOut(i);
            return true;
        } catch (MessageValidationException unused) {
            return false;
        }
    }

    protected String arrayToString(int[] iArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iArr.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(iArr[i]);
        }
        return sb.toString();
    }

    protected void validateMode(int i) throws MessageValidationException {
        if (SetPinModeMessage.PIN_MODE.find(i) == null) {
            throw new MessageValidationException(MessageFormat.format("Allowed modes are [{0}]", SetPinModeMessage.PIN_MODE.values()));
        }
    }

    protected void validateDigitalValue(int i) throws MessageValidationException {
        if (i != 0 && i != 1) {
            throw new MessageValidationException("Allowed digital values are [0; 1]");
        }
    }

    protected void validateDigitalMask(int i) throws MessageValidationException {
        if (i < 0 || i > 255) {
            throw new MessageValidationException("Allowed digital mask values are [0-255]");
        }
    }

    private void validateAnalogValue(int i) throws MessageValidationException {
        if (i < 0 || i > 255) {
            throw new MessageValidationException("Allowed analog values are [0-255]");
        }
    }

    @Override // name.antonsmirnov.firmata.message.factory.MessageFactory
    public ReportDigitalPortMessage digitalRead(int i) throws MessageValidationException {
        validatePort(i);
        return new ReportDigitalPortMessage(i, true);
    }

    @Override // name.antonsmirnov.firmata.message.factory.MessageFactory
    public ReportAnalogPinMessage analogRead(int i) throws MessageValidationException {
        validatePin(i);
        validateAnalogIn(i);
        return new ReportAnalogPinMessage(i, true);
    }

    @Override // name.antonsmirnov.firmata.message.factory.MessageFactory
    public SetPinModeMessage pinMode(int i, int i2) throws MessageValidationException {
        validatePin(i);
        validateMode(i2);
        if (i2 == SetPinModeMessage.PIN_MODE.ANALOG.getMode()) {
            validateAnalogIn(i);
        }
        if (i2 == SetPinModeMessage.PIN_MODE.PWM.getMode()) {
            validateAnalogOut(i);
        }
        return new SetPinModeMessage(i, i2);
    }

    @Override // name.antonsmirnov.firmata.message.factory.MessageFactory
    public DigitalMessage digitalWrite(int i, int i2) throws MessageValidationException {
        validatePort(i);
        validateDigitalMask(i2);
        return new DigitalMessage(i, i2);
    }

    @Override // name.antonsmirnov.firmata.message.factory.MessageFactory
    public AnalogMessage analogWrite(int i, int i2) throws MessageValidationException {
        validatePin(i);
        validateAnalogOut(i);
        validateAnalogValue(i2);
        return new AnalogMessage(i, i2);
    }
}
