package name.antonsmirnov.firmata.wrapper;

import java.util.HashMap;
import java.util.Map;
import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class DigitalPortWrapper implements IFirmata {
    private IFirmata firmata;
    private Map<Integer, Integer> portValues = new HashMap();

    public DigitalPortWrapper(IFirmata iFirmata) {
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

    public Map<Integer, Integer> getPortValues() {
        return this.portValues;
    }

    public void clear() {
        this.portValues.clear();
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void send(Message message) throws SerialException {
        this.firmata.send(message);
        if (message instanceof DigitalMessage) {
            DigitalMessage digitalMessage = (DigitalMessage) message;
            this.portValues.put(Integer.valueOf(digitalMessage.getPort()), Integer.valueOf(digitalMessage.getValue()));
        }
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void onDataReceived(int i) {
        this.firmata.onDataReceived(i);
    }
}
