package name.antonsmirnov.firmata.serial;

/* JADX INFO: loaded from: classes12.dex */
public interface ISerial {
    void addListener(ISerialListener iSerialListener);

    int available() throws SerialException;

    void clear() throws SerialException;

    boolean isStopping();

    int read() throws SerialException;

    void removeListener(ISerialListener iSerialListener);

    void start() throws SerialException;

    void stop() throws SerialException;

    void write(int i) throws SerialException;

    void write(byte[] bArr) throws SerialException;
}
