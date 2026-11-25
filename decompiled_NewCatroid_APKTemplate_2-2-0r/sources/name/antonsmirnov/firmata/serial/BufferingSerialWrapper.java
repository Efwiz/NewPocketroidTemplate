package name.antonsmirnov.firmata.serial;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes12.dex */
public class BufferingSerialWrapper<ConcreteSerialImpl> implements ISerial, ISerialListener<ConcreteSerialImpl> {
    private IByteBuffer buffer;
    private BufferingSerialWrapper<ConcreteSerialImpl>.BufferReadingThread readingThread;
    private ISerial serial;
    private int threadPriority = 5;
    private List<ISerialListener> listeners = new ArrayList();
    private AtomicBoolean shouldStop = new AtomicBoolean();

    public int getThreadPriority() {
        return this.threadPriority;
    }

    public void setThreadPriority(int i) {
        this.threadPriority = i;
    }

    public BufferingSerialWrapper(ISerial iSerial, IByteBuffer iByteBuffer) {
        this.serial = iSerial;
        iSerial.addListener(this);
        this.buffer = iByteBuffer;
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public int available() {
        return this.buffer.size();
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void addListener(ISerialListener iSerialListener) {
        this.listeners.add(iSerialListener);
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void removeListener(ISerialListener iSerialListener) {
        this.listeners.remove(iSerialListener);
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void start() throws SerialException {
        startReadingThread();
        this.serial.start();
    }

    public AtomicBoolean getShouldStop() {
        return this.shouldStop;
    }

    private class BufferReadingThread extends Thread {
        private BufferReadingThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (!BufferingSerialWrapper.this.shouldStop.get()) {
                if (BufferingSerialWrapper.this.available() > 0) {
                    Iterator it = BufferingSerialWrapper.this.listeners.iterator();
                    while (it.hasNext()) {
                        ((ISerialListener) it.next()).onDataReceived(this);
                    }
                }
            }
        }
    }

    private void startReadingThread() {
        this.readingThread = new BufferReadingThread();
        this.shouldStop.set(false);
        this.readingThread.start();
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void stop() throws SerialException {
        stopReadingThread();
        this.serial.stop();
        clear();
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public boolean isStopping() {
        return this.shouldStop.get();
    }

    private void stopReadingThread() {
        if (this.readingThread == null) {
            return;
        }
        this.shouldStop.set(true);
        this.readingThread = null;
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void clear() {
        this.buffer.clear();
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public int read() {
        return this.buffer.get();
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void write(int i) throws SerialException {
        this.serial.write(i);
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void write(byte[] bArr) throws SerialException {
        this.serial.write(bArr);
    }

    @Override // name.antonsmirnov.firmata.serial.ISerialListener
    public void onDataReceived(ConcreteSerialImpl concreteserialimpl) {
        try {
            this.buffer.add((byte) this.serial.read());
        } catch (SerialException e) {
            onException(e);
        }
    }

    @Override // name.antonsmirnov.firmata.serial.ISerialListener
    public void onException(Throwable th) {
        Iterator<ISerialListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onException(th);
        }
    }
}
