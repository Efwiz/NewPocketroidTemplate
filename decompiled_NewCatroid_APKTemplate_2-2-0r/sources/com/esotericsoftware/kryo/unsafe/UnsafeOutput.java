package com.esotericsoftware.kryo.unsafe;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Output;
import java.io.OutputStream;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes8.dex */
public class UnsafeOutput extends Output {
    public UnsafeOutput() {
    }

    public UnsafeOutput(int i) {
        this(i, i);
    }

    public UnsafeOutput(int i, int i2) {
        super(i, i2);
    }

    public UnsafeOutput(byte[] bArr) {
        this(bArr, bArr.length);
    }

    public UnsafeOutput(byte[] bArr, int i) {
        super(bArr, i);
    }

    public UnsafeOutput(OutputStream outputStream) {
        super(outputStream);
    }

    public UnsafeOutput(OutputStream outputStream, int i) {
        super(outputStream, i);
    }

    @Override // com.esotericsoftware.kryo.io.Output, java.io.OutputStream
    public void write(int i) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        byte[] bArr = this.buffer;
        long j = UnsafeUtil.byteArrayBaseOffset;
        int i2 = this.position;
        this.position = i2 + 1;
        unsafe.putByte(bArr, j + ((long) i2), (byte) i);
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeByte(byte b) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        byte[] bArr = this.buffer;
        long j = UnsafeUtil.byteArrayBaseOffset;
        int i = this.position;
        this.position = i + 1;
        unsafe.putByte(bArr, j + ((long) i), b);
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeByte(int i) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        byte[] bArr = this.buffer;
        long j = UnsafeUtil.byteArrayBaseOffset;
        int i2 = this.position;
        this.position = i2 + 1;
        unsafe.putByte(bArr, j + ((long) i2), (byte) i);
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeInt(int i) throws KryoException {
        require(4);
        UnsafeUtil.unsafe.putInt(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), i);
        this.position += 4;
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeLong(long j) throws KryoException {
        require(8);
        UnsafeUtil.unsafe.putLong(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), j);
        this.position += 8;
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeFloat(float f) throws KryoException {
        require(4);
        UnsafeUtil.unsafe.putFloat(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), f);
        this.position += 4;
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeDouble(double d) throws KryoException {
        require(8);
        UnsafeUtil.unsafe.putDouble(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), d);
        this.position += 8;
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeShort(int i) throws KryoException {
        require(2);
        UnsafeUtil.unsafe.putShort(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), (short) i);
        this.position += 2;
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeChar(char c) throws KryoException {
        require(2);
        UnsafeUtil.unsafe.putChar(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), c);
        this.position += 2;
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeBoolean(boolean z) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        Unsafe unsafe = UnsafeUtil.unsafe;
        byte[] bArr = this.buffer;
        long j = UnsafeUtil.byteArrayBaseOffset;
        int i = this.position;
        this.position = i + 1;
        unsafe.putByte(bArr, j + ((long) i), z ? (byte) 1 : (byte) 0);
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeInts(int[] iArr, int i, int i2) throws KryoException {
        writeBytes(iArr, UnsafeUtil.intArrayBaseOffset, iArr.length << 2);
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeLongs(long[] jArr, int i, int i2) throws KryoException {
        writeBytes(jArr, UnsafeUtil.longArrayBaseOffset, jArr.length << 3);
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeFloats(float[] fArr, int i, int i2) throws KryoException {
        writeBytes(fArr, UnsafeUtil.floatArrayBaseOffset, fArr.length << 2);
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeDoubles(double[] dArr, int i, int i2) throws KryoException {
        writeBytes(dArr, UnsafeUtil.doubleArrayBaseOffset, dArr.length << 3);
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeShorts(short[] sArr, int i, int i2) throws KryoException {
        writeBytes(sArr, UnsafeUtil.shortArrayBaseOffset, sArr.length << 1);
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeChars(char[] cArr, int i, int i2) throws KryoException {
        writeBytes(cArr, UnsafeUtil.charArrayBaseOffset, cArr.length << 1);
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeBooleans(boolean[] zArr, int i, int i2) throws KryoException {
        writeBytes(zArr, UnsafeUtil.booleanArrayBaseOffset, zArr.length);
    }

    @Override // com.esotericsoftware.kryo.io.Output
    public void writeBytes(byte[] bArr, int i, int i2) throws KryoException {
        writeBytes(bArr, UnsafeUtil.byteArrayBaseOffset + ((long) i), i2);
    }

    public void writeBytes(Object obj, long j, int i) throws KryoException {
        long j2 = j;
        int iMin = Math.min(this.capacity - this.position, i);
        int i2 = i;
        while (true) {
            long j3 = iMin;
            UnsafeUtil.unsafe.copyMemory(obj, j2, this.buffer, ((long) this.position) + UnsafeUtil.byteArrayBaseOffset, j3);
            this.position += iMin;
            i2 -= iMin;
            if (i2 == 0) {
                return;
            }
            j2 += j3;
            iMin = Math.min(this.capacity, i2);
            require(iMin);
        }
    }
}
