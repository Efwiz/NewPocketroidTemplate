package name.antonsmirnov.firmata;

import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.FirmwareVersionMessage;
import name.antonsmirnov.firmata.message.I2cReplyMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;
import name.antonsmirnov.firmata.message.StringSysexMessage;
import name.antonsmirnov.firmata.message.SysexMessage;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public interface IFirmata {

    public interface Listener {
        void onAnalogMessageReceived(AnalogMessage analogMessage);

        void onDigitalMessageReceived(DigitalMessage digitalMessage);

        void onFirmwareVersionMessageReceived(FirmwareVersionMessage firmwareVersionMessage);

        void onI2cMessageReceived(I2cReplyMessage i2cReplyMessage);

        void onProtocolVersionMessageReceived(ProtocolVersionMessage protocolVersionMessage);

        void onStringSysexMessageReceived(StringSysexMessage stringSysexMessage);

        void onSysexMessageReceived(SysexMessage sysexMessage);

        void onUnknownByteReceived(int i);
    }

    public static class StubListener implements Listener {
        @Override // name.antonsmirnov.firmata.IFirmata.Listener
        public void onAnalogMessageReceived(AnalogMessage analogMessage) {
        }

        @Override // name.antonsmirnov.firmata.IFirmata.Listener
        public void onDigitalMessageReceived(DigitalMessage digitalMessage) {
        }

        @Override // name.antonsmirnov.firmata.IFirmata.Listener
        public void onFirmwareVersionMessageReceived(FirmwareVersionMessage firmwareVersionMessage) {
        }

        @Override // name.antonsmirnov.firmata.IFirmata.Listener
        public void onI2cMessageReceived(I2cReplyMessage i2cReplyMessage) {
        }

        @Override // name.antonsmirnov.firmata.IFirmata.Listener
        public void onProtocolVersionMessageReceived(ProtocolVersionMessage protocolVersionMessage) {
        }

        @Override // name.antonsmirnov.firmata.IFirmata.Listener
        public void onStringSysexMessageReceived(StringSysexMessage stringSysexMessage) {
        }

        @Override // name.antonsmirnov.firmata.IFirmata.Listener
        public void onSysexMessageReceived(SysexMessage sysexMessage) {
        }

        @Override // name.antonsmirnov.firmata.IFirmata.Listener
        public void onUnknownByteReceived(int i) {
        }
    }

    void addListener(Listener listener);

    void clearListeners();

    boolean containsListener(Listener listener);

    void onDataReceived(int i);

    void removeListener(Listener listener);

    void send(Message message) throws SerialException;
}
