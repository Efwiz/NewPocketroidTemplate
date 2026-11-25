package name.antonsmirnov.firmata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.I2cConfigMessage;
import name.antonsmirnov.firmata.message.I2cReadRequestMessage;
import name.antonsmirnov.firmata.message.I2cRequestMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.message.ReportDigitalPortMessage;
import name.antonsmirnov.firmata.message.ReportFirmwareVersionMessage;
import name.antonsmirnov.firmata.message.ReportProtocolVersionMessage;
import name.antonsmirnov.firmata.message.SamplingIntervalMessage;
import name.antonsmirnov.firmata.message.ServoConfigMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage;
import name.antonsmirnov.firmata.message.StringSysexMessage;
import name.antonsmirnov.firmata.message.SysexMessage;
import name.antonsmirnov.firmata.message.SystemResetMessage;
import name.antonsmirnov.firmata.reader.AnalogMessageReader;
import name.antonsmirnov.firmata.reader.DigitalMessageReader;
import name.antonsmirnov.firmata.reader.FirmwareVersionMessageReader;
import name.antonsmirnov.firmata.reader.I2cReplyMessageReader;
import name.antonsmirnov.firmata.reader.IMessageReader;
import name.antonsmirnov.firmata.reader.ProtocolVersionMessageReader;
import name.antonsmirnov.firmata.reader.StringSysexMessageReader;
import name.antonsmirnov.firmata.reader.SysexMessageReader;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.ISerialListener;
import name.antonsmirnov.firmata.serial.SerialException;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import name.antonsmirnov.firmata.writer.DigitalMessageWriter;
import name.antonsmirnov.firmata.writer.I2cConfigMessageWriter;
import name.antonsmirnov.firmata.writer.I2cRequestMessageWriter;
import name.antonsmirnov.firmata.writer.IMessageWriter;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;
import name.antonsmirnov.firmata.writer.ReportDigitalPortMessageWriter;
import name.antonsmirnov.firmata.writer.ReportProtocolVersionMessageWriter;
import name.antonsmirnov.firmata.writer.SamplingIntervalMessageWriter;
import name.antonsmirnov.firmata.writer.ServoConfigMessageWriter;
import name.antonsmirnov.firmata.writer.SetPinModeMessageWriter;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;
import name.antonsmirnov.firmata.writer.SystemResetMessageWriter;

/* JADX INFO: loaded from: classes12.dex */
public class Firmata implements IFirmata, ISerialListener {
    private static final int BUFFER_SIZE = 1024;
    private static List<IMessageReader> readers;
    private static Map<Class<? extends Message>, IMessageWriter> writers;
    private IMessageReader activeReader;
    private byte[] buffer;
    private int bufferLength;
    private List<IFirmata.Listener> listeners;
    private List<IMessageReader> potentialReaders;
    private ISerial serial;

    @Override // name.antonsmirnov.firmata.serial.ISerialListener
    public void onException(Throwable th) {
    }

    public ISerial getSerial() {
        return this.serial;
    }

    public void setSerial(ISerial iSerial) {
        this.serial = iSerial;
        iSerial.addListener(this);
    }

    public Firmata() {
        this.listeners = new ArrayList();
        this.buffer = new byte[1024];
        initWriters();
        initReaders();
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void addListener(IFirmata.Listener listener) {
        this.listeners.add(listener);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void removeListener(IFirmata.Listener listener) {
        this.listeners.remove(listener);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public boolean containsListener(IFirmata.Listener listener) {
        return this.listeners.contains(listener);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void clearListeners() {
        this.listeners.clear();
    }

    private void initWriters() {
        HashMap map = new HashMap();
        writers = map;
        map.put(AnalogMessage.class, new AnalogMessageWriter());
        writers.put(DigitalMessage.class, new DigitalMessageWriter());
        writers.put(ReportAnalogPinMessage.class, new ReportAnalogPinMessageWriter());
        writers.put(ReportDigitalPortMessage.class, new ReportDigitalPortMessageWriter());
        writers.put(ReportProtocolVersionMessage.class, new ReportProtocolVersionMessageWriter());
        writers.put(SetPinModeMessage.class, new SetPinModeMessageWriter());
        writers.put(SystemResetMessage.class, new SystemResetMessageWriter());
        SysexMessageWriter sysexMessageWriter = new SysexMessageWriter();
        writers.put(SysexMessage.class, sysexMessageWriter);
        writers.put(StringSysexMessage.class, sysexMessageWriter);
        writers.put(ReportFirmwareVersionMessage.class, sysexMessageWriter);
        writers.put(ServoConfigMessage.class, new ServoConfigMessageWriter());
        writers.put(SamplingIntervalMessage.class, new SamplingIntervalMessageWriter());
        writers.put(I2cRequestMessage.class, new I2cRequestMessageWriter());
        writers.put(I2cReadRequestMessage.class, new I2cRequestMessageWriter());
        writers.put(I2cConfigMessage.class, new I2cConfigMessageWriter());
    }

    private void initReaders() {
        readers = new ArrayList();
        this.potentialReaders = new ArrayList();
        readers.add(new AnalogMessageReader());
        readers.add(new DigitalMessageReader());
        readers.add(new FirmwareVersionMessageReader());
        readers.add(new ProtocolVersionMessageReader());
        readers.add(new SysexMessageReader());
        readers.add(new StringSysexMessageReader());
        readers.add(new I2cReplyMessageReader());
    }

    public Firmata(ISerial iSerial) {
        this();
        setSerial(iSerial);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void send(Message message) throws SerialException {
        IMessageWriter iMessageWriter = writers.get(message.getClass());
        if (iMessageWriter == null) {
            throw new RuntimeException("Unknown message type: " + message.getClass());
        }
        iMessageWriter.write(message, this.serial);
    }

    @Override // name.antonsmirnov.firmata.serial.ISerialListener
    public void onDataReceived(Object obj) {
        int i;
        try {
            if (this.serial.available() <= 0 || (i = this.serial.read()) < 0) {
                return;
            }
            onDataReceived(i);
        } catch (SerialException unused) {
        }
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void onDataReceived(int i) {
        byte[] bArr = this.buffer;
        int i2 = this.bufferLength;
        int i3 = i2 + 1;
        this.bufferLength = i3;
        bArr[i2] = (byte) i;
        IMessageReader iMessageReader = this.activeReader;
        if (iMessageReader == null) {
            int iDECODE_COMMAND = BytesHelper.DECODE_COMMAND(i);
            if (this.potentialReaders.size() == 0) {
                findPotentialReaders(iDECODE_COMMAND);
            } else {
                filterPotentialReaders(iDECODE_COMMAND);
            }
            tryHandle();
            return;
        }
        iMessageReader.read(bArr, i3);
        if (this.activeReader.finishedReading()) {
            Iterator<IFirmata.Listener> it = this.listeners.iterator();
            while (it.hasNext()) {
                this.activeReader.fireEvent(it.next());
            }
            reinitBuffer();
        }
    }

    private void filterPotentialReaders(int i) {
        ArrayList arrayList = new ArrayList();
        for (IMessageReader iMessageReader : this.potentialReaders) {
            if (iMessageReader.canRead(this.buffer, this.bufferLength, i)) {
                arrayList.add(iMessageReader);
            }
        }
        this.potentialReaders = arrayList;
    }

    private void tryHandle() {
        int size = this.potentialReaders.size();
        if (size != 0) {
            if (size != 1) {
                return;
            }
            IMessageReader iMessageReader = this.potentialReaders.get(0);
            this.activeReader = iMessageReader;
            iMessageReader.startReading();
            return;
        }
        for (int i = 0; i < this.bufferLength; i++) {
            Iterator<IFirmata.Listener> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onUnknownByteReceived(this.buffer[i]);
            }
        }
        reinitBuffer();
    }

    private void reinitBuffer() {
        this.bufferLength = 0;
        this.activeReader = null;
        this.potentialReaders.clear();
    }

    private void findPotentialReaders(int i) {
        for (IMessageReader iMessageReader : readers) {
            if (iMessageReader.canRead(this.buffer, this.bufferLength, i)) {
                this.potentialReaders.add(iMessageReader);
            }
        }
    }
}
