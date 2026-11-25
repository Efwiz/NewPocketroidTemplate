package com.esotericsoftware.kryo.io;

import com.esotericsoftware.kryo.KryoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: classes8.dex */
public class ByteBufferInput extends Input {
    private static final ByteOrder nativeOrder = ByteOrder.nativeOrder();
    protected ByteBuffer byteBuffer;
    private byte[] tempBuffer;

    public ByteBufferInput() {
    }

    public ByteBufferInput(int i) {
        this.capacity = i;
        this.byteBuffer = ByteBuffer.allocateDirect(i);
    }

    public ByteBufferInput(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public ByteBufferInput(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(bArr.length);
        byteBufferAllocateDirect.put(bArr);
        flipBuffer(byteBufferAllocateDirect);
        setBuffer(byteBufferAllocateDirect);
    }

    public ByteBufferInput(ByteBuffer byteBuffer) {
        setBuffer(byteBuffer);
    }

    public ByteBufferInput(InputStream inputStream) {
        this(4096);
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null.");
        }
        this.inputStream = inputStream;
    }

    public ByteBufferInput(InputStream inputStream, int i) {
        this(i);
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null.");
        }
        this.inputStream = inputStream;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public byte[] getBuffer() {
        throw new UnsupportedOperationException("This input does not used a byte[], see #getByteBuffer().");
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public void setBuffer(byte[] bArr) {
        throw new UnsupportedOperationException("This input does not used a byte[], see #setByteBuffer(ByteBuffer).");
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public void setBuffer(byte[] bArr, int i, int i2) {
        throw new UnsupportedOperationException("This input does not used a byte[], see #setByteBufferByteBuffer().");
    }

    public void setBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("buffer cannot be null.");
        }
        this.byteBuffer = byteBuffer;
        this.position = byteBuffer.position();
        this.limit = byteBuffer.limit();
        this.capacity = byteBuffer.capacity();
        this.total = 0L;
        this.inputStream = null;
    }

    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.limit = 0;
        reset();
    }

    @Override // com.esotericsoftware.kryo.io.Input, java.io.InputStream, com.esotericsoftware.kryo.util.Pool.Poolable
    public void reset() {
        super.reset();
        setBufferPosition(this.byteBuffer, 0);
    }

    protected int fill(ByteBuffer byteBuffer, int i, int i2) throws KryoException {
        if (this.inputStream == null) {
            return -1;
        }
        try {
            if (this.tempBuffer == null) {
                this.tempBuffer = new byte[2048];
            }
            setBufferPosition(byteBuffer, i);
            int i3 = 0;
            while (i2 > 0) {
                InputStream inputStream = this.inputStream;
                byte[] bArr = this.tempBuffer;
                int i4 = inputStream.read(bArr, 0, Math.min(bArr.length, i2));
                if (i4 != -1) {
                    byteBuffer.put(this.tempBuffer, 0, i4);
                    i2 -= i4;
                    i3 += i4;
                } else if (i3 == 0) {
                    return -1;
                }
            }
            return i3;
        } catch (IOException e) {
            throw new KryoException(e);
        }
    }

    @Override // com.esotericsoftware.kryo.io.Input
    protected int require(int i) throws KryoException {
        int i2 = this.limit - this.position;
        if (i2 >= i) {
            return i2;
        }
        if (i > this.capacity) {
            throw new KryoException("Buffer too small: capacity: " + this.capacity + ", required: " + i);
        }
        if (i2 > 0) {
            int iFill = fill(this.byteBuffer, this.limit, this.capacity - this.limit);
            if (iFill == -1) {
                throw new KryoException("Buffer underflow.");
            }
            setBufferPosition(this.byteBuffer, this.position);
            i2 += iFill;
            if (i2 >= i) {
                this.limit += iFill;
                return i2;
            }
        }
        this.byteBuffer.compact();
        this.total += (long) this.position;
        this.position = 0;
        do {
            int iFill2 = fill(this.byteBuffer, i2, this.capacity - i2);
            if (iFill2 == -1) {
                if (i2 >= i) {
                    break;
                }
                throw new KryoException("Buffer underflow.");
            }
            i2 += iFill2;
        } while (i2 < i);
        this.limit = i2;
        setBufferPosition(this.byteBuffer, 0);
        return i2;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    protected int optional(int i) throws KryoException {
        int i2 = this.limit - this.position;
        if (i2 >= i) {
            return i;
        }
        int iMin = Math.min(i, this.capacity);
        int iFill = fill(this.byteBuffer, this.limit, this.capacity - this.limit);
        setBufferPosition(this.byteBuffer, this.position);
        if (iFill == -1) {
            if (i2 == 0) {
                return -1;
            }
            return Math.min(i2, iMin);
        }
        int i3 = i2 + iFill;
        if (i3 >= iMin) {
            this.limit += iFill;
            return iMin;
        }
        this.byteBuffer.compact();
        this.total += (long) this.position;
        this.position = 0;
        do {
            int iFill2 = fill(this.byteBuffer, i3, this.capacity - i3);
            if (iFill2 == -1) {
                break;
            }
            i3 += iFill2;
        } while (i3 < iMin);
        this.limit = i3;
        setBufferPosition(this.byteBuffer, 0);
        if (i3 == 0) {
            return -1;
        }
        return Math.min(i3, iMin);
    }

    @Override // com.esotericsoftware.kryo.io.Input, java.io.InputStream
    public int read() throws KryoException {
        if (optional(1) <= 0) {
            return -1;
        }
        this.position++;
        return this.byteBuffer.get() & 255;
    }

    @Override // com.esotericsoftware.kryo.io.Input, java.io.InputStream
    public int read(byte[] bArr) throws KryoException {
        return read(bArr, 0, bArr.length);
    }

    @Override // com.esotericsoftware.kryo.io.Input, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws KryoException {
        if (bArr == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        int iMin = Math.min(this.limit - this.position, i2);
        int i3 = i2;
        do {
            this.byteBuffer.get(bArr, i, iMin);
            this.position += iMin;
            i3 -= iMin;
            if (i3 == 0) {
                break;
            }
            i += iMin;
            iMin = optional(i3);
            if (iMin == -1) {
                if (i2 != i3) {
                    break;
                }
                return -1;
            }
        } while (this.position != this.limit);
        return i2 - i3;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public void setPosition(int i) {
        this.position = i;
        setBufferPosition(this.byteBuffer, i);
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public void setLimit(int i) {
        this.limit = i;
        setBufferLimit(this.byteBuffer, i);
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public void skip(int i) throws KryoException {
        super.skip(i);
        setBufferPosition(this.byteBuffer, this.position);
    }

    @Override // com.esotericsoftware.kryo.io.Input, java.io.InputStream
    public long skip(long j) throws KryoException {
        long j2 = j;
        while (j2 > 0) {
            int iMin = (int) Math.min(2147483639L, j2);
            skip(iMin);
            j2 -= (long) iMin;
        }
        return j;
    }

    @Override // com.esotericsoftware.kryo.io.Input, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws KryoException {
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    private int getBufferPosition(Buffer buffer) {
        return buffer.position();
    }

    private void setBufferPosition(Buffer buffer, int i) {
        buffer.position(i);
    }

    private void setBufferLimit(Buffer buffer, int i) {
        buffer.limit(i);
    }

    private void flipBuffer(Buffer buffer) {
        buffer.flip();
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public byte readByte() throws KryoException {
        if (this.position == this.limit) {
            require(1);
        }
        this.position++;
        return this.byteBuffer.get();
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public int readByteUnsigned() throws KryoException {
        if (this.position == this.limit) {
            require(1);
        }
        this.position++;
        return this.byteBuffer.get() & 255;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public byte[] readBytes(int i) throws KryoException {
        byte[] bArr = new byte[i];
        readBytes(bArr, 0, i);
        return bArr;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public void readBytes(byte[] bArr, int i, int i2) throws KryoException {
        if (bArr == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        int iMin = Math.min(this.limit - this.position, i2);
        while (true) {
            this.byteBuffer.get(bArr, i, iMin);
            this.position += iMin;
            i2 -= iMin;
            if (i2 == 0) {
                return;
            }
            i += iMin;
            iMin = Math.min(i2, this.capacity);
            require(iMin);
        }
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public int readInt() throws KryoException {
        require(4);
        this.position += 4;
        ByteBuffer byteBuffer = this.byteBuffer;
        return ((byteBuffer.get() & 255) << 24) | (byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 8) | ((byteBuffer.get() & 255) << 16);
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public int readVarInt(boolean z) throws KryoException {
        if (require(1) < 5) {
            return readVarInt_slow(z);
        }
        byte b = this.byteBuffer.get();
        int i = b & 127;
        if ((b & 128) != 0) {
            ByteBuffer byteBuffer = this.byteBuffer;
            byte b2 = byteBuffer.get();
            i |= (b2 & 127) << 7;
            if ((b2 & 128) != 0) {
                byte b3 = byteBuffer.get();
                i |= (b3 & 127) << 14;
                if ((b3 & 128) != 0) {
                    byte b4 = byteBuffer.get();
                    i |= (b4 & 127) << 21;
                    if ((b4 & 128) != 0) {
                        i |= (byteBuffer.get() & 127) << 28;
                    }
                }
            }
        }
        this.position = getBufferPosition(this.byteBuffer);
        return z ? i : (i >>> 1) ^ (-(i & 1));
    }

    private int readVarInt_slow(boolean z) {
        this.position++;
        byte b = this.byteBuffer.get();
        int i = b & 127;
        if ((b & 128) != 0) {
            if (this.position == this.limit) {
                require(1);
            }
            ByteBuffer byteBuffer = this.byteBuffer;
            this.position++;
            byte b2 = byteBuffer.get();
            i |= (b2 & 127) << 7;
            if ((b2 & 128) != 0) {
                if (this.position == this.limit) {
                    require(1);
                }
                this.position++;
                byte b3 = byteBuffer.get();
                i |= (b3 & 127) << 14;
                if ((b3 & 128) != 0) {
                    if (this.position == this.limit) {
                        require(1);
                    }
                    this.position++;
                    byte b4 = byteBuffer.get();
                    i |= (b4 & 127) << 21;
                    if ((b4 & 128) != 0) {
                        if (this.position == this.limit) {
                            require(1);
                        }
                        this.position++;
                        i |= (byteBuffer.get() & 127) << 28;
                    }
                }
            }
        }
        return z ? i : (i >>> 1) ^ (-(i & 1));
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public boolean canReadVarInt() throws KryoException {
        if (this.limit - this.position >= 5) {
            return true;
        }
        if (optional(5) <= 0) {
            return false;
        }
        int i = this.position;
        int i2 = this.limit;
        ByteBuffer byteBuffer = this.byteBuffer;
        int i3 = i + 1;
        if ((byteBuffer.get(i) & 128) == 0) {
            return true;
        }
        if (i3 == i2) {
            return false;
        }
        int i4 = i + 2;
        if ((byteBuffer.get(i3) & 128) == 0) {
            return true;
        }
        if (i4 == i2) {
            return false;
        }
        int i5 = i + 3;
        if ((byteBuffer.get(i4) & 128) == 0) {
            return true;
        }
        if (i5 == i2) {
            return false;
        }
        return (byteBuffer.get(i5) & 128) == 0 || i + 4 != i2;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public boolean readVarIntFlag() {
        if (this.position == this.limit) {
            require(1);
        }
        return (this.byteBuffer.get(this.position) & 128) != 0;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public int readVarIntFlag(boolean z) {
        if (require(1) < 5) {
            return readVarIntFlag_slow(z);
        }
        byte b = this.byteBuffer.get();
        int i = b & 63;
        if ((b & 64) != 0) {
            ByteBuffer byteBuffer = this.byteBuffer;
            byte b2 = byteBuffer.get();
            i |= (b2 & 127) << 6;
            if ((b2 & 128) != 0) {
                byte b3 = byteBuffer.get();
                i |= (b3 & 127) << 13;
                if ((b3 & 128) != 0) {
                    byte b4 = byteBuffer.get();
                    i |= (b4 & 127) << 20;
                    if ((b4 & 128) != 0) {
                        i |= (byteBuffer.get() & 127) << 27;
                    }
                }
            }
        }
        this.position = getBufferPosition(this.byteBuffer);
        return z ? i : (i >>> 1) ^ (-(i & 1));
    }

    private int readVarIntFlag_slow(boolean z) {
        this.position++;
        byte b = this.byteBuffer.get();
        int i = b & 63;
        if ((b & 64) != 0) {
            if (this.position == this.limit) {
                require(1);
            }
            this.position++;
            ByteBuffer byteBuffer = this.byteBuffer;
            byte b2 = byteBuffer.get();
            i |= (b2 & 127) << 6;
            if ((b2 & 128) != 0) {
                if (this.position == this.limit) {
                    require(1);
                }
                this.position++;
                byte b3 = byteBuffer.get();
                i |= (b3 & 127) << 13;
                if ((b3 & 128) != 0) {
                    if (this.position == this.limit) {
                        require(1);
                    }
                    this.position++;
                    byte b4 = byteBuffer.get();
                    i |= (b4 & 127) << 20;
                    if ((b4 & 128) != 0) {
                        if (this.position == this.limit) {
                            require(1);
                        }
                        this.position++;
                        i |= (byteBuffer.get() & 127) << 27;
                    }
                }
            }
        }
        return z ? i : (i >>> 1) ^ (-(i & 1));
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public long readLong() throws KryoException {
        require(8);
        this.position += 8;
        ByteBuffer byteBuffer = this.byteBuffer;
        return (((long) byteBuffer.get()) << 56) | ((long) (((byteBuffer.get() & 255) << 8) | (byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 16))) | (((long) (byteBuffer.get() & 255)) << 24) | (((long) (byteBuffer.get() & 255)) << 32) | (((long) (byteBuffer.get() & 255)) << 40) | (((long) (byteBuffer.get() & 255)) << 48);
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public long readVarLong(boolean z) throws KryoException {
        if (require(1) < 9) {
            return readVarLong_slow(z);
        }
        byte b = this.byteBuffer.get();
        long j = b & 127;
        if ((b & 128) != 0) {
            ByteBuffer byteBuffer = this.byteBuffer;
            byte b2 = byteBuffer.get();
            j |= (long) ((b2 & 127) << 7);
            if ((b2 & 128) != 0) {
                byte b3 = byteBuffer.get();
                j |= (long) ((b3 & 127) << 14);
                if ((b3 & 128) != 0) {
                    byte b4 = byteBuffer.get();
                    j |= (long) ((b4 & 127) << 21);
                    if ((b4 & 128) != 0) {
                        byte b5 = byteBuffer.get();
                        j |= ((long) (b5 & 127)) << 28;
                        if ((b5 & 128) != 0) {
                            byte b6 = byteBuffer.get();
                            j |= ((long) (b6 & 127)) << 35;
                            if ((b6 & 128) != 0) {
                                byte b7 = byteBuffer.get();
                                j |= ((long) (b7 & 127)) << 42;
                                if ((b7 & 128) != 0) {
                                    byte b8 = byteBuffer.get();
                                    j |= ((long) (b8 & 127)) << 49;
                                    if ((b8 & 128) != 0) {
                                        j |= ((long) byteBuffer.get()) << 56;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.position = getBufferPosition(this.byteBuffer);
        if (z) {
            return j;
        }
        return (-(j & 1)) ^ (j >>> 1);
    }

    private long readVarLong_slow(boolean z) {
        this.position++;
        byte b = this.byteBuffer.get();
        long j = b & 127;
        if ((b & 128) != 0) {
            if (this.position == this.limit) {
                require(1);
            }
            ByteBuffer byteBuffer = this.byteBuffer;
            this.position++;
            byte b2 = byteBuffer.get();
            j |= (long) ((b2 & 127) << 7);
            if ((b2 & 128) != 0) {
                if (this.position == this.limit) {
                    require(1);
                }
                this.position++;
                byte b3 = byteBuffer.get();
                j |= (long) ((b3 & 127) << 14);
                if ((b3 & 128) != 0) {
                    if (this.position == this.limit) {
                        require(1);
                    }
                    this.position++;
                    byte b4 = byteBuffer.get();
                    j |= (long) ((b4 & 127) << 21);
                    if ((b4 & 128) != 0) {
                        if (this.position == this.limit) {
                            require(1);
                        }
                        this.position++;
                        byte b5 = byteBuffer.get();
                        j |= ((long) (b5 & 127)) << 28;
                        if ((b5 & 128) != 0) {
                            if (this.position == this.limit) {
                                require(1);
                            }
                            this.position++;
                            byte b6 = byteBuffer.get();
                            j |= ((long) (b6 & 127)) << 35;
                            if ((b6 & 128) != 0) {
                                if (this.position == this.limit) {
                                    require(1);
                                }
                                this.position++;
                                byte b7 = byteBuffer.get();
                                j |= ((long) (b7 & 127)) << 42;
                                if ((b7 & 128) != 0) {
                                    if (this.position == this.limit) {
                                        require(1);
                                    }
                                    this.position++;
                                    byte b8 = byteBuffer.get();
                                    j |= ((long) (b8 & 127)) << 49;
                                    if ((b8 & 128) != 0) {
                                        if (this.position == this.limit) {
                                            require(1);
                                        }
                                        this.position++;
                                        j |= ((long) byteBuffer.get()) << 56;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (z) {
            return j;
        }
        return (-(j & 1)) ^ (j >>> 1);
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public boolean canReadVarLong() throws KryoException {
        if (this.limit - this.position >= 9) {
            return true;
        }
        if (optional(5) <= 0) {
            return false;
        }
        int i = this.position;
        int i2 = this.limit;
        ByteBuffer byteBuffer = this.byteBuffer;
        int i3 = i + 1;
        if ((byteBuffer.get(i) & 128) == 0) {
            return true;
        }
        if (i3 == i2) {
            return false;
        }
        int i4 = i + 2;
        if ((byteBuffer.get(i3) & 128) == 0) {
            return true;
        }
        if (i4 == i2) {
            return false;
        }
        int i5 = i + 3;
        if ((byteBuffer.get(i4) & 128) == 0) {
            return true;
        }
        if (i5 == i2) {
            return false;
        }
        int i6 = i + 4;
        if ((byteBuffer.get(i5) & 128) == 0) {
            return true;
        }
        if (i6 == i2) {
            return false;
        }
        int i7 = i + 5;
        if ((byteBuffer.get(i6) & 128) == 0) {
            return true;
        }
        if (i7 == i2) {
            return false;
        }
        int i8 = i + 6;
        if ((byteBuffer.get(i7) & 128) == 0) {
            return true;
        }
        if (i8 == i2) {
            return false;
        }
        int i9 = i + 7;
        if ((byteBuffer.get(i8) & 128) == 0) {
            return true;
        }
        if (i9 == i2) {
            return false;
        }
        return (byteBuffer.get(i9) & 128) == 0 || i + 8 != i2;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public float readFloat() throws KryoException {
        require(4);
        ByteBuffer byteBuffer = this.byteBuffer;
        this.position += 4;
        return Float.intBitsToFloat((byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 8) | ((byteBuffer.get() & 255) << 16) | ((byteBuffer.get() & 255) << 24));
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public double readDouble() throws KryoException {
        require(8);
        ByteBuffer byteBuffer = this.byteBuffer;
        this.position += 8;
        return Double.longBitsToDouble((((long) byteBuffer.get()) << 56) | ((long) (((byteBuffer.get() & 255) << 8) | (byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 16))) | (((long) (byteBuffer.get() & 255)) << 24) | (((long) (byteBuffer.get() & 255)) << 32) | (((long) (byteBuffer.get() & 255)) << 40) | (((long) (byteBuffer.get() & 255)) << 48));
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public boolean readBoolean() throws KryoException {
        if (this.position == this.limit) {
            require(1);
        }
        this.position++;
        return this.byteBuffer.get() == 1;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public short readShort() throws KryoException {
        require(2);
        this.position += 2;
        return (short) ((this.byteBuffer.get() & 255) | ((this.byteBuffer.get() & 255) << 8));
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public int readShortUnsigned() throws KryoException {
        require(2);
        this.position += 2;
        return (this.byteBuffer.get() & 255) | ((this.byteBuffer.get() & 255) << 8);
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public char readChar() throws KryoException {
        require(2);
        this.position += 2;
        return (char) ((this.byteBuffer.get() & 255) | ((this.byteBuffer.get() & 255) << 8));
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public String readString() {
        if (!readVarIntFlag()) {
            return readAsciiString();
        }
        int varIntFlag = readVarIntFlag(true);
        if (varIntFlag == 0) {
            return null;
        }
        if (varIntFlag == 1) {
            return "";
        }
        int i = varIntFlag - 1;
        readUtf8Chars(i);
        return new String(this.chars, 0, i);
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public StringBuilder readStringBuilder() {
        if (!readVarIntFlag()) {
            return new StringBuilder(readAsciiString());
        }
        int varIntFlag = readVarIntFlag(true);
        if (varIntFlag == 0) {
            return null;
        }
        if (varIntFlag == 1) {
            return new StringBuilder("");
        }
        int i = varIntFlag - 1;
        readUtf8Chars(i);
        StringBuilder sb = new StringBuilder(i);
        sb.append(this.chars, 0, i);
        return sb;
    }

    private void readUtf8Chars(int i) {
        if (this.chars.length < i) {
            this.chars = new char[i];
        }
        char[] cArr = this.chars;
        ByteBuffer byteBuffer = this.byteBuffer;
        int iMin = Math.min(require(1), i);
        int i2 = 0;
        while (i2 < iMin) {
            byte b = byteBuffer.get();
            if (b < 0) {
                break;
            }
            cArr[i2] = (char) b;
            i2++;
        }
        this.position += i2;
        if (i2 < i) {
            setBufferPosition(byteBuffer, this.position);
            readUtf8Chars_slow(i, i2);
        }
    }

    private void readUtf8Chars_slow(int i, int i2) {
        ByteBuffer byteBuffer = this.byteBuffer;
        char[] cArr = this.chars;
        while (i2 < i) {
            if (this.position == this.limit) {
                require(1);
            }
            this.position++;
            byte b = byteBuffer.get();
            int i3 = b & 255;
            switch (i3 >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    cArr[i2] = (char) i3;
                    break;
                case 12:
                case 13:
                    if (this.position == this.limit) {
                        require(1);
                    }
                    this.position++;
                    cArr[i2] = (char) (((b & 31) << 6) | (byteBuffer.get() & 63));
                    break;
                case 14:
                    require(2);
                    this.position += 2;
                    cArr[i2] = (char) (((b & 15) << 12) | ((byteBuffer.get() & 63) << 6) | (byteBuffer.get() & 63));
                    break;
            }
            i2++;
        }
    }

    private String readAsciiString() {
        char[] cArr = this.chars;
        ByteBuffer byteBuffer = this.byteBuffer;
        int iMin = Math.min(cArr.length, this.limit - this.position);
        int i = 0;
        while (i < iMin) {
            byte b = byteBuffer.get();
            if ((b & 128) == 128) {
                this.position = getBufferPosition(byteBuffer);
                cArr[i] = (char) (b & 127);
                return new String(cArr, 0, i + 1);
            }
            cArr[i] = (char) b;
            i++;
        }
        this.position = getBufferPosition(byteBuffer);
        return readAscii_slow(i);
    }

    private String readAscii_slow(int i) {
        char[] cArr = this.chars;
        ByteBuffer byteBuffer = this.byteBuffer;
        while (true) {
            if (this.position == this.limit) {
                require(1);
            }
            this.position++;
            byte b = byteBuffer.get();
            if (i == cArr.length) {
                char[] cArr2 = new char[i * 2];
                System.arraycopy(cArr, 0, cArr2, 0, i);
                this.chars = cArr2;
                cArr = cArr2;
            }
            if ((b & 128) == 128) {
                cArr[i] = (char) (b & 127);
                return new String(cArr, 0, i + 1);
            }
            cArr[i] = (char) b;
            i++;
        }
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public int[] readInts(int i) throws KryoException {
        int[] iArr = new int[i];
        int i2 = i << 2;
        int i3 = 0;
        if (optional(i2) == i2) {
            ByteBuffer byteBuffer = this.byteBuffer;
            while (i3 < i) {
                iArr[i3] = (byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 8) | ((byteBuffer.get() & 255) << 16) | ((byteBuffer.get() & 255) << 24);
                i3++;
            }
            this.position = getBufferPosition(byteBuffer);
        } else {
            while (i3 < i) {
                iArr[i3] = readInt();
                i3++;
            }
        }
        return iArr;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public long[] readLongs(int i) throws KryoException {
        long[] jArr = new long[i];
        int i2 = i << 3;
        int i3 = 0;
        if (optional(i2) == i2) {
            ByteBuffer byteBuffer = this.byteBuffer;
            while (i3 < i) {
                jArr[i3] = ((long) ((byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 8) | ((byteBuffer.get() & 255) << 16))) | (((long) (byteBuffer.get() & 255)) << 24) | (((long) (byteBuffer.get() & 255)) << 32) | (((long) (byteBuffer.get() & 255)) << 40) | (((long) (byteBuffer.get() & 255)) << 48) | (((long) byteBuffer.get()) << 56);
                i3++;
            }
            this.position = getBufferPosition(byteBuffer);
        } else {
            while (i3 < i) {
                jArr[i3] = readLong();
                i3++;
            }
        }
        return jArr;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public float[] readFloats(int i) throws KryoException {
        float[] fArr = new float[i];
        int i2 = i << 2;
        int i3 = 0;
        if (optional(i2) == i2) {
            ByteBuffer byteBuffer = this.byteBuffer;
            while (i3 < i) {
                fArr[i3] = Float.intBitsToFloat((byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 8) | ((byteBuffer.get() & 255) << 16) | ((byteBuffer.get() & 255) << 24));
                i3++;
            }
            this.position = getBufferPosition(byteBuffer);
        } else {
            while (i3 < i) {
                fArr[i3] = readFloat();
                i3++;
            }
        }
        return fArr;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public double[] readDoubles(int i) throws KryoException {
        double[] dArr = new double[i];
        int i2 = i << 3;
        int i3 = 0;
        if (optional(i2) == i2) {
            ByteBuffer byteBuffer = this.byteBuffer;
            while (i3 < i) {
                dArr[i3] = Double.longBitsToDouble(((long) ((byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 8) | ((byteBuffer.get() & 255) << 16))) | (((long) (byteBuffer.get() & 255)) << 24) | (((long) (byteBuffer.get() & 255)) << 32) | (((long) (byteBuffer.get() & 255)) << 40) | (((long) (byteBuffer.get() & 255)) << 48) | (((long) byteBuffer.get()) << 56));
                i3++;
            }
            this.position = getBufferPosition(byteBuffer);
        } else {
            while (i3 < i) {
                dArr[i3] = readDouble();
                i3++;
            }
        }
        return dArr;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public short[] readShorts(int i) throws KryoException {
        short[] sArr = new short[i];
        int i2 = i << 1;
        int i3 = 0;
        if (optional(i2) == i2) {
            ByteBuffer byteBuffer = this.byteBuffer;
            while (i3 < i) {
                sArr[i3] = (short) ((byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 8));
                i3++;
            }
            this.position = getBufferPosition(byteBuffer);
        } else {
            while (i3 < i) {
                sArr[i3] = readShort();
                i3++;
            }
        }
        return sArr;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public char[] readChars(int i) throws KryoException {
        char[] cArr = new char[i];
        int i2 = i << 1;
        int i3 = 0;
        if (optional(i2) == i2) {
            ByteBuffer byteBuffer = this.byteBuffer;
            while (i3 < i) {
                cArr[i3] = (char) ((byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 8));
                i3++;
            }
            this.position = getBufferPosition(byteBuffer);
        } else {
            while (i3 < i) {
                cArr[i3] = readChar();
                i3++;
            }
        }
        return cArr;
    }

    @Override // com.esotericsoftware.kryo.io.Input
    public boolean[] readBooleans(int i) throws KryoException {
        boolean[] zArr = new boolean[i];
        if (optional(i) == i) {
            ByteBuffer byteBuffer = this.byteBuffer;
            for (int i2 = 0; i2 < i; i2++) {
                zArr[i2] = byteBuffer.get() != 0;
            }
            this.position = getBufferPosition(byteBuffer);
        } else {
            for (int i3 = 0; i3 < i; i3++) {
                zArr[i3] = readBoolean();
            }
        }
        return zArr;
    }
}
