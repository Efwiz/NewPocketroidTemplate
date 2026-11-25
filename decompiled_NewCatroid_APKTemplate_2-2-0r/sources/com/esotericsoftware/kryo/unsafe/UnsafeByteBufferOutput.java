package com.esotericsoftware.kryo.unsafe;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class UnsafeByteBufferOutput extends ByteBufferOutput {
    private long bufferAddress;

    public UnsafeByteBufferOutput() {
    }

    public UnsafeByteBufferOutput(int i) {
        super(i);
        updateBufferAddress();
    }

    public UnsafeByteBufferOutput(int i, int i2) {
        super(i, i2);
        updateBufferAddress();
    }

    public UnsafeByteBufferOutput(OutputStream outputStream) {
        super(outputStream);
        updateBufferAddress();
    }

    public UnsafeByteBufferOutput(OutputStream outputStream, int i) {
        super(outputStream, i);
        updateBufferAddress();
    }

    public UnsafeByteBufferOutput(long j, int i) {
        super(UnsafeUtil.newDirectBuffer(j, i));
        updateBufferAddress();
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput
    public void setBuffer(ByteBuffer byteBuffer, int i) {
        if (!(byteBuffer instanceof DirectBuffer)) {
            throw new IllegalArgumentException("buffer must be direct.");
        }
        if (byteBuffer != this.byteBuffer) {
            UnsafeUtil.dispose(this.byteBuffer);
        }
        super.setBuffer(byteBuffer, i);
        updateBufferAddress();
    }

    private void updateBufferAddress() {
        this.bufferAddress = this.byteBuffer.address();
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    protected boolean require(int i) throws KryoException {
        ByteBuffer byteBuffer = this.byteBuffer;
        boolean zRequire = super.require(i);
        if (this.byteBuffer != byteBuffer) {
            UnsafeUtil.dispose(byteBuffer);
            updateBufferAddress();
        }
        return zRequire;
    }

    public void dispose() {
        UnsafeUtil.dispose(this.byteBuffer);
        this.byteBuffer = null;
        this.bufferAddress = 0L;
    }

    private void setBufferPosition(Buffer buffer, int i) {
        buffer.position(i);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output, java.io.OutputStream
    public void write(int i) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        long j = this.bufferAddress;
        int i2 = this.position;
        this.position = i2 + 1;
        unsafe.putByte(j + ((long) i2), (byte) i);
        setBufferPosition(this.byteBuffer, this.position);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeByte(byte b) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        long j = this.bufferAddress;
        int i = this.position;
        this.position = i + 1;
        unsafe.putByte(j + ((long) i), b);
        setBufferPosition(this.byteBuffer, this.position);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeByte(int i) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        long j = this.bufferAddress;
        int i2 = this.position;
        this.position = i2 + 1;
        unsafe.putByte(j + ((long) i2), (byte) i);
        setBufferPosition(this.byteBuffer, this.position);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeInt(int i) throws KryoException {
        require(4);
        UnsafeUtil.unsafe.putInt(this.bufferAddress + ((long) this.position), i);
        this.position += 4;
        setBufferPosition(this.byteBuffer, this.position);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeLong(long j) throws KryoException {
        require(8);
        UnsafeUtil.unsafe.putLong(this.bufferAddress + ((long) this.position), j);
        this.position += 8;
        setBufferPosition(this.byteBuffer, this.position);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeFloat(float f) throws KryoException {
        require(4);
        UnsafeUtil.unsafe.putFloat(this.bufferAddress + ((long) this.position), f);
        this.position += 4;
        setBufferPosition(this.byteBuffer, this.position);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeDouble(double d) throws KryoException {
        require(8);
        UnsafeUtil.unsafe.putDouble(this.bufferAddress + ((long) this.position), d);
        this.position += 8;
        setBufferPosition(this.byteBuffer, this.position);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeShort(int i) throws KryoException {
        require(2);
        UnsafeUtil.unsafe.putShort(this.bufferAddress + ((long) this.position), (short) i);
        this.position += 2;
        setBufferPosition(this.byteBuffer, this.position);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeChar(char c) throws KryoException {
        require(2);
        UnsafeUtil.unsafe.putChar(this.bufferAddress + ((long) this.position), c);
        this.position += 2;
        setBufferPosition(this.byteBuffer, this.position);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeBoolean(boolean z) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        long j = this.bufferAddress;
        int i = this.position;
        this.position = i + 1;
        unsafe.putByte(j + ((long) i), z ? (byte) 1 : (byte) 0);
        setBufferPosition(this.byteBuffer, this.position);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeInts(int[] iArr, int i, int i2) throws KryoException {
        writeBytes(iArr, UnsafeUtil.intArrayBaseOffset, iArr.length << 2);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeLongs(long[] jArr, int i, int i2) throws KryoException {
        writeBytes(jArr, UnsafeUtil.longArrayBaseOffset, jArr.length << 3);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeFloats(float[] fArr, int i, int i2) throws KryoException {
        writeBytes(fArr, UnsafeUtil.floatArrayBaseOffset, fArr.length << 2);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeDoubles(double[] dArr, int i, int i2) throws KryoException {
        writeBytes(dArr, UnsafeUtil.doubleArrayBaseOffset, dArr.length << 3);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeShorts(short[] sArr, int i, int i2) throws KryoException {
        writeBytes(sArr, UnsafeUtil.shortArrayBaseOffset, sArr.length << 1);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeChars(char[] cArr, int i, int i2) throws KryoException {
        writeBytes(cArr, UnsafeUtil.charArrayBaseOffset, cArr.length << 1);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeBooleans(boolean[] zArr, int i, int i2) throws KryoException {
        writeBytes(zArr, UnsafeUtil.booleanArrayBaseOffset, zArr.length);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferOutput, com.esotericsoftware.kryo.io.Output
    public void writeBytes(byte[] bArr, int i, int i2) throws KryoException {
        writeBytes(bArr, UnsafeUtil.byteArrayBaseOffset + ((long) i), i2);
    }

    public void writeBytes(Object obj, long j, int i) throws KryoException {
        long j2 = j;
        int iMin = Math.min(this.capacity - this.position, i);
        int i2 = i;
        while (true) {
            long j3 = iMin;
            UnsafeUtil.unsafe.copyMemory(obj, j2, (Object) null, this.bufferAddress + ((long) this.position), j3);
            this.position += iMin;
            i2 -= iMin;
            if (i2 != 0) {
                j2 += j3;
                iMin = Math.min(this.capacity, i2);
                require(iMin);
            } else {
                setBufferPosition(this.byteBuffer, this.position);
                return;
            }
        }
    }
}
