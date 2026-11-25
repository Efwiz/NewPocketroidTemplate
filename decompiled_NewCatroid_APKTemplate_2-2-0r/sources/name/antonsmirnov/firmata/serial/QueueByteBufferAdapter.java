package name.antonsmirnov.firmata.serial;

import java.util.Queue;

/* JADX INFO: loaded from: classes12.dex */
public class QueueByteBufferAdapter implements IByteBuffer {
    private Queue<Byte> queue;

    public QueueByteBufferAdapter(Queue<Byte> queue) {
        this.queue = queue;
    }

    @Override // name.antonsmirnov.firmata.serial.IByteBuffer
    public void add(byte b) {
        this.queue.add(Byte.valueOf(b));
    }

    @Override // name.antonsmirnov.firmata.serial.IByteBuffer
    public byte get() {
        return this.queue.poll().byteValue();
    }

    @Override // name.antonsmirnov.firmata.serial.IByteBuffer
    public void clear() {
        this.queue.clear();
    }

    @Override // name.antonsmirnov.firmata.serial.IByteBuffer
    public int size() {
        return this.queue.size();
    }
}
