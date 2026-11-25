package name.antonsmirnov.firmata.serial;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

/* JADX INFO: loaded from: classes12.dex */
public class SocketSerialAdapter extends StreamingSerialAdapter implements Serializable {
    private String address;
    private int port;
    private transient Socket socket;

    public SocketSerialAdapter(String str, int i) {
        this.address = str;
        this.port = i;
    }

    @Override // name.antonsmirnov.firmata.serial.StreamingSerialAdapter, name.antonsmirnov.firmata.serial.ISerial
    public void start() throws SerialException {
        try {
            Socket socket = new Socket(this.address, this.port);
            this.socket = socket;
            setInStream(socket.getInputStream());
            setOutStream(this.socket.getOutputStream());
            super.start();
        } catch (IOException e) {
            throw new SerialException(e);
        }
    }

    @Override // name.antonsmirnov.firmata.serial.StreamingSerialAdapter, name.antonsmirnov.firmata.serial.ISerial
    public void stop() throws SerialException {
        setStopReading();
        try {
            Socket socket = this.socket;
            if (socket != null) {
                socket.close();
            }
            super.stop();
        } catch (IOException e) {
            throw new SerialException(e);
        }
    }
}
