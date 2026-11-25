package name.antonsmirnov.firmata.wrapper;

import java.util.HashSet;
import java.util.Set;
import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.message.ReportDigitalPortMessage;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class ReportAutostopWrapper extends IFirmata.StubListener implements IFirmata {
    private IFirmata firmata;
    private Set<Integer> digitalReporting = new HashSet();
    private Set<Integer> analogReporting = new HashSet();

    public ReportAutostopWrapper(IFirmata iFirmata) {
        this.firmata = iFirmata;
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void addListener(IFirmata.Listener listener) {
        this.firmata.addListener(listener);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void removeListener(IFirmata.Listener listener) {
        this.firmata.removeListener(listener);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public boolean containsListener(IFirmata.Listener listener) {
        return this.firmata.containsListener(listener);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void clearListeners() {
        this.firmata.clearListeners();
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void send(Message message) throws SerialException {
        this.firmata.send(message);
        if (message instanceof ReportDigitalPortMessage) {
            ReportDigitalPortMessage reportDigitalPortMessage = (ReportDigitalPortMessage) message;
            if (reportDigitalPortMessage.isEnable()) {
                this.digitalReporting.add(Integer.valueOf(reportDigitalPortMessage.getPort()));
            }
        }
        if (message instanceof ReportAnalogPinMessage) {
            ReportAnalogPinMessage reportAnalogPinMessage = (ReportAnalogPinMessage) message;
            if (reportAnalogPinMessage.isEnable()) {
                this.analogReporting.add(Integer.valueOf(reportAnalogPinMessage.getPin()));
            }
        }
    }

    @Override // name.antonsmirnov.firmata.IFirmata.StubListener, name.antonsmirnov.firmata.IFirmata.Listener
    public void onAnalogMessageReceived(AnalogMessage analogMessage) {
        if (this.analogReporting.contains(Integer.valueOf(analogMessage.getPin()))) {
            this.analogReporting.remove(Integer.valueOf(analogMessage.getPin()));
            disableAnalogReporting(analogMessage);
        }
    }

    private void disableAnalogReporting(AnalogMessage analogMessage) {
        try {
            this.firmata.send(new ReportAnalogPinMessage(analogMessage.getPin(), false));
        } catch (SerialException unused) {
        }
    }

    @Override // name.antonsmirnov.firmata.IFirmata.StubListener, name.antonsmirnov.firmata.IFirmata.Listener
    public void onDigitalMessageReceived(DigitalMessage digitalMessage) {
        if (this.digitalReporting.contains(Integer.valueOf(digitalMessage.getPort()))) {
            this.digitalReporting.remove(Integer.valueOf(digitalMessage.getPort()));
            disableDigitalReporting(digitalMessage);
        }
    }

    private void disableDigitalReporting(DigitalMessage digitalMessage) {
        try {
            this.firmata.send(new ReportDigitalPortMessage(digitalMessage.getPort(), false));
        } catch (SerialException unused) {
        }
    }

    public void clear() {
        this.digitalReporting.clear();
        this.analogReporting.clear();
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void onDataReceived(int i) {
        this.firmata.onDataReceived(i);
    }
}
