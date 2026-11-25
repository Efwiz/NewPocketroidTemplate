package com.esotericsoftware.kryo.unsafe;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class UnsafeByteBufferInput extends ByteBufferInput {
    private long bufferAddress;

    public UnsafeByteBufferInput() {
    }

    public UnsafeByteBufferInput(int i) {
        super(i);
        updateBufferAddress();
    }

    public UnsafeByteBufferInput(byte[] bArr) {
        super(bArr);
        updateBufferAddress();
    }

    public UnsafeByteBufferInput(byte[] bArr, int i, int i2) {
        super(bArr, i, i2);
        updateBufferAddress();
    }

    public UnsafeByteBufferInput(ByteBuffer byteBuffer) {
        super(byteBuffer);
        updateBufferAddress();
    }

    public UnsafeByteBufferInput(long j, int i) {
        super(UnsafeUtil.newDirectBuffer(j, i));
        updateBufferAddress();
    }

    public UnsafeByteBufferInput(InputStream inputStream) {
        super(inputStream);
        updateBufferAddress();
    }

    public UnsafeByteBufferInput(InputStream inputStream, int i) {
        super(inputStream, i);
        updateBufferAddress();
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput
    public void setBuffer(ByteBuffer byteBuffer) {
        if (!(byteBuffer instanceof DirectBuffer)) {
            throw new IllegalArgumentException("buffer must be direct.");
        }
        if (byteBuffer != this.byteBuffer) {
            UnsafeUtil.dispose(this.byteBuffer);
        }
        super.setBuffer(byteBuffer);
        updateBufferAddress();
    }

    private void updateBufferAddress() {
        this.bufferAddress = this.byteBuffer.address();
    }

    private void setBufferPosition(Buffer buffer, int i) {
        buffer.position(i);
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input, java.io.InputStream
    public int read() throws KryoException {
        if (optional(1) <= 0) {
            return -1;
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        long j = this.bufferAddress;
        int i = this.position;
        this.position = i + 1;
        int i2 = unsafe.getByte(j + ((long) i)) & 255;
        setBufferPosition(this.byteBuffer, this.position);
        return i2;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public byte readByte() throws KryoException {
        if (this.position == this.limit) {
            require(1);
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        long j = this.bufferAddress;
        int i = this.position;
        this.position = i + 1;
        byte b = unsafe.getByte(j + ((long) i));
        setBufferPosition(this.byteBuffer, this.position);
        return b;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public int readByteUnsigned() throws KryoException {
        if (this.position == this.limit) {
            require(1);
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        long j = this.bufferAddress;
        int i = this.position;
        this.position = i + 1;
        int i2 = unsafe.getByte(j + ((long) i)) & 255;
        setBufferPosition(this.byteBuffer, this.position);
        return i2;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public int readInt() throws KryoException {
        require(4);
        int i = UnsafeUtil.unsafe.getInt(this.bufferAddress + ((long) this.position));
        this.position += 4;
        setBufferPosition(this.byteBuffer, this.position);
        return i;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public long readLong() throws KryoException {
        require(8);
        long j = UnsafeUtil.unsafe.getLong(this.bufferAddress + ((long) this.position));
        this.position += 8;
        setBufferPosition(this.byteBuffer, this.position);
        return j;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public float readFloat() throws KryoException {
        require(4);
        float f = UnsafeUtil.unsafe.getFloat(this.bufferAddress + ((long) this.position));
        this.position += 4;
        setBufferPosition(this.byteBuffer, this.position);
        return f;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public double readDouble() throws KryoException {
        require(8);
        double d = UnsafeUtil.unsafe.getDouble(this.bufferAddress + ((long) this.position));
        this.position += 8;
        setBufferPosition(this.byteBuffer, this.position);
        return d;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public short readShort() throws KryoException {
        require(2);
        short s = UnsafeUtil.unsafe.getShort(this.bufferAddress + ((long) this.position));
        this.position += 2;
        setBufferPosition(this.byteBuffer, this.position);
        return s;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public char readChar() throws KryoException {
        require(2);
        char c = UnsafeUtil.unsafe.getChar(this.bufferAddress + ((long) this.position));
        this.position += 2;
        setBufferPosition(this.byteBuffer, this.position);
        return c;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public boolean readBoolean() throws KryoException {
        if (this.position == this.limit) {
            require(1);
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        long j = this.bufferAddress;
        int i = this.position;
        this.position = i + 1;
        boolean z = unsafe.getByte(j + ((long) i)) != 0;
        setBufferPosition(this.byteBuffer, this.position);
        return z;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public int[] readInts(int i) throws KryoException {
        int[] iArr = new int[i];
        readBytes(iArr, UnsafeUtil.intArrayBaseOffset, i << 2);
        return iArr;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public long[] readLongs(int i) throws KryoException {
        long[] jArr = new long[i];
        readBytes(jArr, UnsafeUtil.longArrayBaseOffset, i << 3);
        return jArr;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public float[] readFloats(int i) throws KryoException {
        float[] fArr = new float[i];
        readBytes(fArr, UnsafeUtil.floatArrayBaseOffset, i << 2);
        return fArr;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public double[] readDoubles(int i) throws KryoException {
        double[] dArr = new double[i];
        readBytes(dArr, UnsafeUtil.doubleArrayBaseOffset, i << 3);
        return dArr;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public short[] readShorts(int i) throws KryoException {
        short[] sArr = new short[i];
        readBytes(sArr, UnsafeUtil.shortArrayBaseOffset, i << 1);
        return sArr;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public char[] readChars(int i) throws KryoException {
        char[] cArr = new char[i];
        readBytes(cArr, UnsafeUtil.charArrayBaseOffset, i << 1);
        return cArr;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public boolean[] readBooleans(int i) throws KryoException {
        boolean[] zArr = new boolean[i];
        readBytes(zArr, UnsafeUtil.booleanArrayBaseOffset, i);
        return zArr;
    }

    @Override // com.esotericsoftware.kryo.io.ByteBufferInput, com.esotericsoftware.kryo.io.Input
    public void readBytes(byte[] bArr, int i, int i2) throws KryoException {
        readBytes(bArr, UnsafeUtil.byteArrayBaseOffset + ((long) i), i2);
    }

    public void readBytes(Object obj, long j, int i) throws KryoException {
        long j2 = j;
        int iMin = Math.min(this.limit - this.position, i);
        int i2 = i;
        while (true) {
            long j3 = iMin;
            UnsafeUtil.unsafe.copyMemory((Object) null, this.bufferAddress + ((long) this.position), obj, j2, j3);
            this.position += iMin;
            i2 -= iMin;
            if (i2 != 0) {
                j2 += j3;
                iMin = Math.min(i2, this.capacity);
                require(iMin);
            } else {
                setBufferPosition(this.byteBuffer, this.position);
                return;
            }
        }
    }
}
