package name.antonsmirnov.firmata.serial;

import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes12.dex */
public class ByteArrayByteBufferAdapter implements IByteBuffer {
    private byte[] array;
    private AtomicInteger readIndex = new AtomicInteger();
    private AtomicInteger writeIndex = new AtomicInteger();

    public ByteArrayByteBufferAdapter(byte[] bArr) {
        this.array = bArr;
        reset();
    }

    private void reset() {
        this.readIndex.set(0);
        this.writeIndex.set(0);
    }

    @Override // name.antonsmirnov.firmata.serial.IByteBuffer
    public void add(byte b) {
        this.array[this.writeIndex.getAndIncrement()] = b;
    }

    @Override // name.antonsmirnov.firmata.serial.IByteBuffer
    public byte get() {
        if (size() <= 0) {
            return (byte) -1;
        }
        byte b = this.array[this.readIndex.getAndIncrement()];
        if (this.readIndex == this.writeIndex) {
            reset();
        }
        return b;
    }

    @Override // name.antonsmirnov.firmata.serial.IByteBuffer
    public void clear() {
        reset();
    }

    @Override // name.antonsmirnov.firmata.serial.IByteBuffer
    public int size() {
        return this.writeIndex.get() - this.readIndex.get();
    }
}
