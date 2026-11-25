package name.antonsmirnov.firmata;

import name.antonsmirnov.firmata.message.FirmwareVersionMessage;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;

/* JADX INFO: loaded from: classes12.dex */
public class InitListener extends IFirmata.StubListener {
    private FirmwareVersionMessage firmware;
    private Listener listener;
    private ProtocolVersionMessage protocol;

    public interface Listener {
        void onInitialized();
    }

    public FirmwareVersionMessage getFirmware() {
        return this.firmware;
    }

    public ProtocolVersionMessage getProtocol() {
        return this.protocol;
    }

    public InitListener(Listener listener) {
        this.listener = listener;
        clear();
    }

    public void clear() {
        this.firmware = null;
        this.protocol = null;
    }

    private void checkInitAndFire() {
        if (this.firmware == null || this.protocol == null) {
            return;
        }
        this.listener.onInitialized();
    }

    public boolean isInitialized() {
        return (this.firmware == null || this.protocol == null) ? false : true;
    }

    @Override // name.antonsmirnov.firmata.IFirmata.StubListener, name.antonsmirnov.firmata.IFirmata.Listener
    public void onFirmwareVersionMessageReceived(FirmwareVersionMessage firmwareVersionMessage) {
        this.firmware = firmwareVersionMessage;
        checkInitAndFire();
    }

    @Override // name.antonsmirnov.firmata.IFirmata.StubListener, name.antonsmirnov.firmata.IFirmata.Listener
    public void onProtocolVersionMessageReceived(ProtocolVersionMessage protocolVersionMessage) {
        this.protocol = protocolVersionMessage;
        checkInitAndFire();
    }
}
