package name.antonsmirnov.firmata.serial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes12.dex */
public class StreamingSerialAdapter implements ISerial {
    private transient InputStream inStream;
    private List<ISerialListener> listeners;
    private transient OutputStream outStream;
    private AtomicBoolean shouldStop;
    private ReadingThread thread;

    public InputStream getInStream() {
        return this.inStream;
    }

    public void setInStream(InputStream inputStream) {
        this.inStream = inputStream;
    }

    public OutputStream getOutStream() {
        return this.outStream;
    }

    public void setOutStream(OutputStream outputStream) {
        this.outStream = outputStream;
    }

    public StreamingSerialAdapter() {
        this.listeners = new ArrayList();
        this.shouldStop = new AtomicBoolean();
    }

    public StreamingSerialAdapter(InputStream inputStream, OutputStream outputStream) {
        this();
        setInStream(inputStream);
        setOutStream(outputStream);
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void addListener(ISerialListener iSerialListener) {
        this.listeners.add(iSerialListener);
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void removeListener(ISerialListener iSerialListener) {
        this.listeners.remove(iSerialListener);
    }

    private class ReadingThread extends Thread implements Thread.UncaughtExceptionHandler {
        public ReadingThread() {
            setUncaughtExceptionHandler(this);
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            handleException(th);
        }

        private void handleException(Throwable th) {
            if (StreamingSerialAdapter.this.shouldStop.get()) {
                return;
            }
            Iterator it = StreamingSerialAdapter.this.listeners.iterator();
            while (it.hasNext()) {
                ((ISerialListener) it.next()).onException(th);
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (!StreamingSerialAdapter.this.shouldStop.get()) {
                try {
                    if (StreamingSerialAdapter.this.inStream.available() > 0) {
                        Iterator it = StreamingSerialAdapter.this.listeners.iterator();
                        while (it.hasNext()) {
                            ((ISerialListener) it.next()).onDataReceived(StreamingSerialAdapter.this);
                        }
                    }
                } catch (IOException e) {
                    handleException(e);
                }
            }
            try {
                StreamingSerialAdapter.this.inStream.close();
            } catch (IOException unused) {
            }
        }
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void start() throws SerialException {
        if (this.thread != null) {
            return;
        }
        this.thread = new ReadingThread();
        this.shouldStop.set(false);
        this.thread.start();
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void stop() throws SerialException {
        if (this.thread == null) {
            return;
        }
        setStopReading();
        this.thread = null;
        try {
            this.outStream.close();
        } catch (IOException unused) {
        }
    }

    protected void setStopReading() {
        this.shouldStop.set(true);
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public boolean isStopping() {
        return this.shouldStop.get();
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public int available() throws SerialException {
        try {
            return this.inStream.available();
        } catch (IOException e) {
            return checkIsStoppingOrThrow(e, 0);
        }
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void clear() throws SerialException {
        try {
            this.inStream.reset();
        } catch (IOException e) {
            checkIsStoppingOrThrow(e);
        }
    }

    private void checkIsStoppingOrThrow(IOException iOException) throws SerialException {
        checkIsStoppingOrThrow(iOException, 0);
    }

    private int checkIsStoppingOrThrow(IOException iOException, int i) throws SerialException {
        if (this.shouldStop.get()) {
            return i;
        }
        throw new SerialException(iOException);
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public int read() throws SerialException {
        try {
            return this.inStream.read();
        } catch (IOException e) {
            return checkIsStoppingOrThrow(e, -1);
        }
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void write(int i) throws SerialException {
        try {
            this.outStream.write(i);
        } catch (IOException e) {
            checkIsStoppingOrThrow(e);
        }
    }

    @Override // name.antonsmirnov.firmata.serial.ISerial
    public void write(byte[] bArr) throws SerialException {
        try {
            this.outStream.write(bArr);
        } catch (IOException e) {
            checkIsStoppingOrThrow(e);
        }
    }
}
