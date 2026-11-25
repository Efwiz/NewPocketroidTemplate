package com.esotericsoftware.kryo.io;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.util.Pool;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes8.dex */
public class Input extends InputStream implements Pool.Poolable {
    protected byte[] buffer;
    protected int capacity;
    protected char[] chars;
    protected InputStream inputStream;
    protected int limit;
    protected int position;
    protected long total;
    protected boolean varEncoding;

    public Input() {
        this.chars = new char[32];
        this.varEncoding = true;
    }

    public Input(int i) {
        this.chars = new char[32];
        this.varEncoding = true;
        this.capacity = i;
        this.buffer = new byte[i];
    }

    public Input(byte[] bArr) {
        this.chars = new char[32];
        this.varEncoding = true;
        setBuffer(bArr, 0, bArr.length);
    }

    public Input(byte[] bArr, int i, int i2) {
        this.chars = new char[32];
        this.varEncoding = true;
        setBuffer(bArr, i, i2);
    }

    public Input(InputStream inputStream) {
        this(4096);
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null.");
        }
        this.inputStream = inputStream;
    }

    public Input(InputStream inputStream, int i) {
        this(i);
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null.");
        }
        this.inputStream = inputStream;
    }

    public void setBuffer(byte[] bArr) {
        setBuffer(bArr, 0, bArr.length);
    }

    public void setBuffer(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        this.buffer = bArr;
        this.position = i;
        this.limit = i + i2;
        this.capacity = bArr.length;
        this.total = 0L;
        this.inputStream = null;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.limit = 0;
        reset();
    }

    public boolean getVariableLengthEncoding() {
        return this.varEncoding;
    }

    public void setVariableLengthEncoding(boolean z) {
        this.varEncoding = z;
    }

    public long total() {
        return this.total + ((long) this.position);
    }

    public void setTotal(long j) {
        this.total = j;
    }

    public int position() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public int limit() {
        return this.limit;
    }

    public void setLimit(int i) {
        this.limit = i;
    }

    @Override // java.io.InputStream, com.esotericsoftware.kryo.util.Pool.Poolable
    public void reset() {
        this.position = 0;
        this.total = 0L;
    }

    public void skip(int i) throws KryoException {
        int iMin = Math.min(this.limit - this.position, i);
        while (true) {
            this.position += iMin;
            i -= iMin;
            if (i == 0) {
                return;
            }
            iMin = Math.min(i, this.capacity);
            require(iMin);
        }
    }

    protected int fill(byte[] bArr, int i, int i2) throws KryoException {
        InputStream inputStream = this.inputStream;
        if (inputStream == null) {
            return -1;
        }
        try {
            return inputStream.read(bArr, i, i2);
        } catch (IOException e) {
            throw new KryoException(e);
        }
    }

    protected int require(int i) throws KryoException {
        int i2 = this.limit;
        int i3 = i2 - this.position;
        if (i3 >= i) {
            return i3;
        }
        int i4 = this.capacity;
        if (i > i4) {
            throw new KryoException("Buffer too small: capacity: " + this.capacity + ", required: " + i);
        }
        if (i3 > 0) {
            int iFill = fill(this.buffer, i2, i4 - i2);
            if (iFill == -1) {
                throw new KryoException("Buffer underflow.");
            }
            i3 += iFill;
            if (i3 >= i) {
                this.limit += iFill;
                return i3;
            }
        }
        byte[] bArr = this.buffer;
        System.arraycopy(bArr, this.position, bArr, 0, i3);
        this.total += (long) this.position;
        this.position = 0;
        do {
            int iFill2 = fill(this.buffer, i3, this.capacity - i3);
            if (iFill2 == -1) {
                if (i3 >= i) {
                    break;
                }
                throw new KryoException("Buffer underflow.");
            }
            i3 += iFill2;
        } while (i3 < i);
        this.limit = i3;
        return i3;
    }

    protected int optional(int i) throws KryoException {
        int i2 = this.limit - this.position;
        if (i2 >= i) {
            return i;
        }
        int iMin = Math.min(i, this.capacity);
        byte[] bArr = this.buffer;
        int i3 = this.limit;
        int iFill = fill(bArr, i3, this.capacity - i3);
        if (iFill == -1) {
            if (i2 == 0) {
                return -1;
            }
            return Math.min(i2, iMin);
        }
        int i4 = i2 + iFill;
        if (i4 >= iMin) {
            this.limit += iFill;
            return iMin;
        }
        byte[] bArr2 = this.buffer;
        System.arraycopy(bArr2, this.position, bArr2, 0, i4);
        this.total += (long) this.position;
        this.position = 0;
        do {
            int iFill2 = fill(this.buffer, i4, this.capacity - i4);
            if (iFill2 == -1) {
                break;
            }
            i4 += iFill2;
        } while (i4 < iMin);
        this.limit = i4;
        if (i4 == 0) {
            return -1;
        }
        return Math.min(i4, iMin);
    }

    public boolean end() {
        return optional(1) <= 0;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        int i = this.limit - this.position;
        InputStream inputStream = this.inputStream;
        return i + (inputStream != null ? inputStream.available() : 0);
    }

    @Override // java.io.InputStream
    public int read() throws KryoException {
        if (optional(1) <= 0) {
            return -1;
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        return bArr[i] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws KryoException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws KryoException {
        if (bArr == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        int iMin = Math.min(this.limit - this.position, i2);
        int i3 = i2;
        do {
            System.arraycopy(this.buffer, this.position, bArr, i, iMin);
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

    @Override // java.io.InputStream
    public long skip(long j) throws KryoException {
        long j2 = j;
        while (j2 > 0) {
            int iMin = (int) Math.min(2147483639L, j2);
            skip(iMin);
            j2 -= (long) iMin;
        }
        return j;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws KryoException {
        InputStream inputStream = this.inputStream;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public byte readByte() throws KryoException {
        if (this.position == this.limit) {
            require(1);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        return bArr[i];
    }

    public int readByteUnsigned() throws KryoException {
        if (this.position == this.limit) {
            require(1);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        return bArr[i] & 255;
    }

    public byte[] readBytes(int i) throws KryoException {
        byte[] bArr = new byte[i];
        readBytes(bArr, 0, i);
        return bArr;
    }

    public void readBytes(byte[] bArr) throws KryoException {
        readBytes(bArr, 0, bArr.length);
    }

    public void readBytes(byte[] bArr, int i, int i2) throws KryoException {
        if (bArr == null) {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
        int iMin = Math.min(this.limit - this.position, i2);
        while (true) {
            System.arraycopy(this.buffer, this.position, bArr, i, iMin);
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

    public int readInt() throws KryoException {
        require(4);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    public int readInt(boolean z) throws KryoException {
        return this.varEncoding ? readVarInt(z) : readInt();
    }

    public boolean canReadInt() throws KryoException {
        if (this.varEncoding) {
            return canReadVarInt();
        }
        return this.limit - this.position >= 4 || optional(4) == 4;
    }

    public int readVarInt(boolean z) throws KryoException {
        if (require(1) < 5) {
            return readVarInt_slow(z);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        int i2 = i + 1;
        this.position = i2;
        byte b = bArr[i];
        int i3 = b & 127;
        if ((b & 128) != 0) {
            int i4 = i + 2;
            byte b2 = bArr[i2];
            i3 |= (b2 & 127) << 7;
            if ((b2 & 128) != 0) {
                int i5 = i + 3;
                byte b3 = bArr[i4];
                i3 |= (b3 & 127) << 14;
                if ((b3 & 128) != 0) {
                    i4 = i + 4;
                    byte b4 = bArr[i5];
                    i3 |= (b4 & 127) << 21;
                    if ((b4 & 128) != 0) {
                        i3 = ((bArr[i4] & 127) << 28) | i3;
                        i4 = 5 + i;
                    }
                } else {
                    i4 = i5;
                }
            }
            this.position = i4;
        }
        return z ? i3 : (i3 >>> 1) ^ (-(i3 & 1));
    }

    private int readVarInt_slow(boolean z) {
        byte[] bArr = this.buffer;
        int i = this.position;
        int i2 = i + 1;
        this.position = i2;
        byte b = bArr[i];
        int i3 = b & 127;
        if ((b & 128) != 0) {
            if (i2 == this.limit) {
                require(1);
            }
            byte[] bArr2 = this.buffer;
            int i4 = this.position;
            int i5 = i4 + 1;
            this.position = i5;
            byte b2 = bArr2[i4];
            i3 |= (b2 & 127) << 7;
            if ((b2 & 128) != 0) {
                if (i5 == this.limit) {
                    require(1);
                }
                int i6 = this.position;
                int i7 = i6 + 1;
                this.position = i7;
                byte b3 = bArr2[i6];
                i3 |= (b3 & 127) << 14;
                if ((b3 & 128) != 0) {
                    if (i7 == this.limit) {
                        require(1);
                    }
                    int i8 = this.position;
                    int i9 = i8 + 1;
                    this.position = i9;
                    byte b4 = bArr2[i8];
                    i3 |= (b4 & 127) << 21;
                    if ((b4 & 128) != 0) {
                        if (i9 == this.limit) {
                            require(1);
                        }
                        int i10 = this.position;
                        this.position = i10 + 1;
                        i3 |= (bArr2[i10] & 127) << 28;
                    }
                }
            }
        }
        return z ? i3 : (i3 >>> 1) ^ (-(i3 & 1));
    }

    public boolean canReadVarInt() throws KryoException {
        if (this.limit - this.position >= 5) {
            return true;
        }
        if (optional(5) <= 0) {
            return false;
        }
        int i = this.position;
        int i2 = this.limit;
        byte[] bArr = this.buffer;
        int i3 = i + 1;
        if ((bArr[i] & 128) == 0) {
            return true;
        }
        if (i3 == i2) {
            return false;
        }
        int i4 = i + 2;
        if ((bArr[i3] & 128) == 0) {
            return true;
        }
        if (i4 == i2) {
            return false;
        }
        int i5 = i + 3;
        if ((bArr[i4] & 128) == 0) {
            return true;
        }
        if (i5 == i2) {
            return false;
        }
        return (bArr[i5] & 128) == 0 || i + 4 != i2;
    }

    public boolean readVarIntFlag() {
        if (this.position == this.limit) {
            require(1);
        }
        return (this.buffer[this.position] & 128) != 0;
    }

    public int readVarIntFlag(boolean z) {
        if (require(1) < 5) {
            return readVarIntFlag_slow(z);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        int i2 = i + 1;
        this.position = i2;
        byte b = bArr[i];
        int i3 = b & 63;
        if ((b & 64) != 0) {
            int i4 = i + 2;
            byte b2 = bArr[i2];
            i3 |= (b2 & 127) << 6;
            if ((b2 & 128) != 0) {
                int i5 = i + 3;
                byte b3 = bArr[i4];
                i3 |= (b3 & 127) << 13;
                if ((b3 & 128) != 0) {
                    i4 = i + 4;
                    byte b4 = bArr[i5];
                    i3 |= (b4 & 127) << 20;
                    if ((b4 & 128) != 0) {
                        i3 = ((bArr[i4] & 127) << 27) | i3;
                        i4 = 5 + i;
                    }
                } else {
                    i4 = i5;
                }
            }
            this.position = i4;
        }
        return z ? i3 : (i3 >>> 1) ^ (-(i3 & 1));
    }

    private int readVarIntFlag_slow(boolean z) {
        byte[] bArr = this.buffer;
        int i = this.position;
        int i2 = i + 1;
        this.position = i2;
        byte b = bArr[i];
        int i3 = b & 63;
        if ((b & 64) != 0) {
            if (i2 == this.limit) {
                require(1);
            }
            byte[] bArr2 = this.buffer;
            int i4 = this.position;
            int i5 = i4 + 1;
            this.position = i5;
            byte b2 = bArr2[i4];
            i3 |= (b2 & 127) << 6;
            if ((b2 & 128) != 0) {
                if (i5 == this.limit) {
                    require(1);
                }
                int i6 = this.position;
                int i7 = i6 + 1;
                this.position = i7;
                byte b3 = bArr2[i6];
                i3 |= (b3 & 127) << 13;
                if ((b3 & 128) != 0) {
                    if (i7 == this.limit) {
                        require(1);
                    }
                    int i8 = this.position;
                    int i9 = i8 + 1;
                    this.position = i9;
                    byte b4 = bArr2[i8];
                    i3 |= (b4 & 127) << 20;
                    if ((b4 & 128) != 0) {
                        if (i9 == this.limit) {
                            require(1);
                        }
                        int i10 = this.position;
                        this.position = i10 + 1;
                        i3 |= (bArr2[i10] & 127) << 27;
                    }
                }
            }
        }
        return z ? i3 : (i3 >>> 1) ^ (-(i3 & 1));
    }

    public long readLong() throws KryoException {
        require(8);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 8;
        return (((long) bArr[i + 7]) << 56) | ((long) (((bArr[i + 1] & 255) << 8) | (bArr[i] & 255) | ((bArr[i + 2] & 255) << 16))) | (((long) (bArr[i + 3] & 255)) << 24) | (((long) (bArr[i + 4] & 255)) << 32) | (((long) (bArr[i + 5] & 255)) << 40) | (((long) (bArr[i + 6] & 255)) << 48);
    }

    public long readLong(boolean z) throws KryoException {
        return this.varEncoding ? readVarLong(z) : readLong();
    }

    /* JADX WARN: Code duplicated, block: B:23:0x008d A[PHI: r5 r6
      0x008d: PHI (r5v3 int) = (r5v2 int), (r5v6 int), (r5v9 int), (r5v12 int) binds: [B:9:0x0029, B:13:0x0045, B:17:0x0063, B:21:0x0081] A[DONT_GENERATE, DONT_INLINE]
      0x008d: PHI (r6v5 long) = (r6v4 long), (r6v7 long), (r6v9 long), (r6v11 long) binds: [B:9:0x0029, B:13:0x0045, B:17:0x0063, B:21:0x0081] A[DONT_GENERATE, DONT_INLINE]] */
    public long readVarLong(boolean z) throws KryoException {
        if (require(1) < 9) {
            return readVarLong_slow(z);
        }
        int i = this.position;
        byte[] bArr = this.buffer;
        int i2 = i + 1;
        byte b = bArr[i];
        long j = b & 127;
        if ((b & 128) != 0) {
            int i3 = i + 2;
            byte b2 = bArr[i2];
            j |= (long) ((b2 & 127) << 7);
            if ((b2 & 128) != 0) {
                i2 = i + 3;
                byte b3 = bArr[i3];
                j |= (long) ((b3 & 127) << 14);
                if ((b3 & 128) != 0) {
                    i3 = i + 4;
                    byte b4 = bArr[i2];
                    j |= (long) ((b4 & 127) << 21);
                    if ((b4 & 128) != 0) {
                        i2 = i + 5;
                        byte b5 = bArr[i3];
                        j |= ((long) (b5 & 127)) << 28;
                        if ((b5 & 128) != 0) {
                            i3 = i + 6;
                            byte b6 = bArr[i2];
                            j |= ((long) (b6 & 127)) << 35;
                            if ((b6 & 128) != 0) {
                                i2 = i + 7;
                                byte b7 = bArr[i3];
                                j |= ((long) (b7 & 127)) << 42;
                                if ((b7 & 128) != 0) {
                                    i3 = i + 8;
                                    byte b8 = bArr[i2];
                                    j |= ((long) (b8 & 127)) << 49;
                                    if ((b8 & 128) != 0) {
                                        i2 = i + 9;
                                        j |= ((long) bArr[i3]) << 56;
                                    } else {
                                        i2 = i3;
                                    }
                                }
                            } else {
                                i2 = i3;
                            }
                        }
                    } else {
                        i2 = i3;
                    }
                }
            } else {
                i2 = i3;
            }
        }
        this.position = i2;
        return z ? j : (j >>> 1) ^ (-(1 & j));
    }

    private long readVarLong_slow(boolean z) {
        byte[] bArr = this.buffer;
        int i = this.position;
        int i2 = i + 1;
        this.position = i2;
        byte b = bArr[i];
        long j = b & 127;
        if ((b & 128) != 0) {
            if (i2 == this.limit) {
                require(1);
            }
            byte[] bArr2 = this.buffer;
            int i3 = this.position;
            int i4 = i3 + 1;
            this.position = i4;
            byte b2 = bArr2[i3];
            j |= (long) ((b2 & 127) << 7);
            if ((b2 & 128) != 0) {
                if (i4 == this.limit) {
                    require(1);
                }
                int i5 = this.position;
                int i6 = i5 + 1;
                this.position = i6;
                byte b3 = bArr2[i5];
                j |= (long) ((b3 & 127) << 14);
                if ((b3 & 128) != 0) {
                    if (i6 == this.limit) {
                        require(1);
                    }
                    int i7 = this.position;
                    int i8 = i7 + 1;
                    this.position = i8;
                    byte b4 = bArr2[i7];
                    j |= (long) ((b4 & 127) << 21);
                    if ((b4 & 128) != 0) {
                        if (i8 == this.limit) {
                            require(1);
                        }
                        int i9 = this.position;
                        int i10 = i9 + 1;
                        this.position = i10;
                        byte b5 = bArr2[i9];
                        j |= ((long) (b5 & 127)) << 28;
                        if ((b5 & 128) != 0) {
                            if (i10 == this.limit) {
                                require(1);
                            }
                            int i11 = this.position;
                            int i12 = i11 + 1;
                            this.position = i12;
                            byte b6 = bArr2[i11];
                            j |= ((long) (b6 & 127)) << 35;
                            if ((b6 & 128) != 0) {
                                if (i12 == this.limit) {
                                    require(1);
                                }
                                int i13 = this.position;
                                int i14 = i13 + 1;
                                this.position = i14;
                                byte b7 = bArr2[i13];
                                j |= ((long) (b7 & 127)) << 42;
                                if ((b7 & 128) != 0) {
                                    if (i14 == this.limit) {
                                        require(1);
                                    }
                                    int i15 = this.position;
                                    int i16 = i15 + 1;
                                    this.position = i16;
                                    byte b8 = bArr2[i15];
                                    j |= ((long) (b8 & 127)) << 49;
                                    if ((b8 & 128) != 0) {
                                        if (i16 == this.limit) {
                                            require(1);
                                        }
                                        int i17 = this.position;
                                        this.position = i17 + 1;
                                        j |= ((long) bArr2[i17]) << 56;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return z ? j : (j >>> 1) ^ (-(j & 1));
    }

    public boolean canReadLong() throws KryoException {
        if (this.varEncoding) {
            return canReadVarLong();
        }
        return this.limit - this.position >= 8 || optional(8) == 8;
    }

    public boolean canReadVarLong() throws KryoException {
        if (this.limit - this.position >= 9) {
            return true;
        }
        if (optional(5) <= 0) {
            return false;
        }
        int i = this.position;
        int i2 = this.limit;
        byte[] bArr = this.buffer;
        int i3 = i + 1;
        if ((bArr[i] & 128) == 0) {
            return true;
        }
        if (i3 == i2) {
            return false;
        }
        int i4 = i + 2;
        if ((bArr[i3] & 128) == 0) {
            return true;
        }
        if (i4 == i2) {
            return false;
        }
        int i5 = i + 3;
        if ((bArr[i4] & 128) == 0) {
            return true;
        }
        if (i5 == i2) {
            return false;
        }
        int i6 = i + 4;
        if ((bArr[i5] & 128) == 0) {
            return true;
        }
        if (i6 == i2) {
            return false;
        }
        int i7 = i + 5;
        if ((bArr[i6] & 128) == 0) {
            return true;
        }
        if (i7 == i2) {
            return false;
        }
        int i8 = i + 6;
        if ((bArr[i7] & 128) == 0) {
            return true;
        }
        if (i8 == i2) {
            return false;
        }
        int i9 = i + 7;
        if ((bArr[i8] & 128) == 0) {
            return true;
        }
        if (i9 == i2) {
            return false;
        }
        return (bArr[i9] & 128) == 0 || i + 8 != i2;
    }

    public float readFloat() throws KryoException {
        require(4);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 4;
        return Float.intBitsToFloat(((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16));
    }

    public float readVarFloat(float f, boolean z) throws KryoException {
        return readVarInt(z) / f;
    }

    public double readDouble() throws KryoException {
        require(8);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 8;
        return Double.longBitsToDouble((((long) bArr[i + 7]) << 56) | ((long) (((bArr[i + 1] & 255) << 8) | (bArr[i] & 255) | ((bArr[i + 2] & 255) << 16))) | (((long) (bArr[i + 3] & 255)) << 24) | (((long) (bArr[i + 4] & 255)) << 32) | (((long) (bArr[i + 5] & 255)) << 40) | (((long) (bArr[i + 6] & 255)) << 48));
    }

    public double readVarDouble(double d, boolean z) throws KryoException {
        return readVarLong(z) / d;
    }

    public short readShort() throws KryoException {
        require(2);
        int i = this.position;
        this.position = i + 2;
        byte[] bArr = this.buffer;
        return (short) (((bArr[i + 1] & 255) << 8) | (bArr[i] & 255));
    }

    public int readShortUnsigned() throws KryoException {
        require(2);
        int i = this.position;
        this.position = i + 2;
        byte[] bArr = this.buffer;
        return ((bArr[i + 1] & 255) << 8) | (bArr[i] & 255);
    }

    public char readChar() throws KryoException {
        require(2);
        int i = this.position;
        this.position = i + 2;
        byte[] bArr = this.buffer;
        return (char) (((bArr[i + 1] & 255) << 8) | (bArr[i] & 255));
    }

    public boolean readBoolean() throws KryoException {
        if (this.position == this.limit) {
            require(1);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        return bArr[i] == 1;
    }

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

    public StringBuilder readStringBuilder() {
        if (!readVarIntFlag()) {
            return new StringBuilder(readAsciiString());
        }
        int varIntFlag = readVarIntFlag(true);
        if (varIntFlag == 0) {
            return null;
        }
        if (varIntFlag == 1) {
            return new StringBuilder(0);
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
        byte[] bArr = this.buffer;
        char[] cArr = this.chars;
        int iMin = Math.min(require(1), i);
        int i2 = this.position;
        int i3 = 0;
        while (i3 < iMin) {
            int i4 = i2 + 1;
            byte b = bArr[i2];
            if (b < 0) {
                break;
            }
            cArr[i3] = (char) b;
            i3++;
            i2 = i4;
        }
        this.position = i2;
        if (i3 < i) {
            readUtf8Chars_slow(i, i3);
        }
    }

    private void readUtf8Chars_slow(int i, int i2) {
        char[] cArr = this.chars;
        byte[] bArr = this.buffer;
        while (i2 < i) {
            if (this.position == this.limit) {
                require(1);
            }
            int i3 = this.position;
            int i4 = i3 + 1;
            this.position = i4;
            byte b = bArr[i3];
            int i5 = b & 255;
            switch (i5 >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    cArr[i2] = (char) i5;
                    break;
                case 12:
                case 13:
                    if (i4 == this.limit) {
                        require(1);
                    }
                    int i6 = this.position;
                    this.position = i6 + 1;
                    cArr[i2] = (char) (((b & 31) << 6) | (bArr[i6] & 63));
                    break;
                case 14:
                    require(2);
                    int i7 = this.position;
                    this.position = i7 + 2;
                    cArr[i2] = (char) (((b & 15) << 12) | ((bArr[i7] & 63) << 6) | (bArr[i7 + 1] & 63));
                    break;
            }
            i2++;
        }
    }

    private String readAsciiString() {
        char[] cArr = this.chars;
        byte[] bArr = this.buffer;
        int i = this.position;
        int iMin = Math.min(cArr.length, this.limit - i);
        int i2 = 0;
        while (i2 < iMin) {
            byte b = bArr[i];
            if ((b & 128) == 128) {
                this.position = i + 1;
                cArr[i2] = (char) (b & 127);
                return new String(cArr, 0, i2 + 1);
            }
            cArr[i2] = (char) b;
            i2++;
            i++;
        }
        this.position = i;
        return readAscii_slow(i2);
    }

    private String readAscii_slow(int i) {
        char[] cArr = this.chars;
        byte[] bArr = this.buffer;
        while (true) {
            if (this.position == this.limit) {
                require(1);
            }
            int i2 = this.position;
            this.position = i2 + 1;
            byte b = bArr[i2];
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

    public int[] readInts(int i) throws KryoException {
        int[] iArr = new int[i];
        int i2 = i << 2;
        int i3 = 0;
        if (optional(i2) == i2) {
            byte[] bArr = this.buffer;
            int i4 = this.position;
            while (i3 < i) {
                iArr[i3] = (bArr[i4] & 255) | ((bArr[i4 + 1] & 255) << 8) | ((bArr[i4 + 2] & 255) << 16) | ((bArr[i4 + 3] & 255) << 24);
                i3++;
                i4 += 4;
            }
            this.position = i4;
        } else {
            while (i3 < i) {
                iArr[i3] = readInt();
                i3++;
            }
        }
        return iArr;
    }

    public int[] readInts(int i, boolean z) throws KryoException {
        if (this.varEncoding) {
            int[] iArr = new int[i];
            for (int i2 = 0; i2 < i; i2++) {
                iArr[i2] = readVarInt(z);
            }
            return iArr;
        }
        return readInts(i);
    }

    public long[] readLongs(int i) throws KryoException {
        long[] jArr = new long[i];
        int i2 = i << 3;
        int i3 = 0;
        if (optional(i2) == i2) {
            byte[] bArr = this.buffer;
            int i4 = this.position;
            while (i3 < i) {
                jArr[i3] = ((long) ((bArr[i4] & 255) | ((bArr[i4 + 1] & 255) << 8) | ((bArr[i4 + 2] & 255) << 16))) | (((long) (bArr[i4 + 3] & 255)) << 24) | (((long) (bArr[i4 + 4] & 255)) << 32) | (((long) (bArr[i4 + 5] & 255)) << 40) | (((long) (bArr[i4 + 6] & 255)) << 48) | (((long) bArr[i4 + 7]) << 56);
                i3++;
                i4 += 8;
            }
            this.position = i4;
        } else {
            while (i3 < i) {
                jArr[i3] = readLong();
                i3++;
            }
        }
        return jArr;
    }

    public long[] readLongs(int i, boolean z) throws KryoException {
        if (this.varEncoding) {
            long[] jArr = new long[i];
            for (int i2 = 0; i2 < i; i2++) {
                jArr[i2] = readVarLong(z);
            }
            return jArr;
        }
        return readLongs(i);
    }

    public float[] readFloats(int i) throws KryoException {
        float[] fArr = new float[i];
        int i2 = i << 2;
        int i3 = 0;
        if (optional(i2) == i2) {
            byte[] bArr = this.buffer;
            int i4 = this.position;
            while (i3 < i) {
                fArr[i3] = Float.intBitsToFloat((bArr[i4] & 255) | ((bArr[i4 + 1] & 255) << 8) | ((bArr[i4 + 2] & 255) << 16) | ((bArr[i4 + 3] & 255) << 24));
                i3++;
                i4 += 4;
            }
            this.position = i4;
        } else {
            while (i3 < i) {
                fArr[i3] = readFloat();
                i3++;
            }
        }
        return fArr;
    }

    public double[] readDoubles(int i) throws KryoException {
        double[] dArr = new double[i];
        int i2 = i << 3;
        int i3 = 0;
        if (optional(i2) == i2) {
            byte[] bArr = this.buffer;
            int i4 = this.position;
            while (i3 < i) {
                dArr[i3] = Double.longBitsToDouble(((long) ((bArr[i4] & 255) | ((bArr[i4 + 1] & 255) << 8) | ((bArr[i4 + 2] & 255) << 16))) | (((long) (bArr[i4 + 3] & 255)) << 24) | (((long) (bArr[i4 + 4] & 255)) << 32) | (((long) (bArr[i4 + 5] & 255)) << 40) | (((long) (bArr[i4 + 6] & 255)) << 48) | (((long) bArr[i4 + 7]) << 56));
                i3++;
                i4 += 8;
            }
            this.position = i4;
        } else {
            while (i3 < i) {
                dArr[i3] = readDouble();
                i3++;
            }
        }
        return dArr;
    }

    public short[] readShorts(int i) throws KryoException {
        short[] sArr = new short[i];
        int i2 = i << 1;
        int i3 = 0;
        if (optional(i2) == i2) {
            byte[] bArr = this.buffer;
            int i4 = this.position;
            while (i3 < i) {
                sArr[i3] = (short) ((bArr[i4] & 255) | ((bArr[i4 + 1] & 255) << 8));
                i3++;
                i4 += 2;
            }
            this.position = i4;
        } else {
            while (i3 < i) {
                sArr[i3] = readShort();
                i3++;
            }
        }
        return sArr;
    }

    public char[] readChars(int i) throws KryoException {
        char[] cArr = new char[i];
        int i2 = i << 1;
        int i3 = 0;
        if (optional(i2) == i2) {
            byte[] bArr = this.buffer;
            int i4 = this.position;
            while (i3 < i) {
                cArr[i3] = (char) ((bArr[i4] & 255) | ((bArr[i4 + 1] & 255) << 8));
                i3++;
                i4 += 2;
            }
            this.position = i4;
        } else {
            while (i3 < i) {
                cArr[i3] = readChar();
                i3++;
            }
        }
        return cArr;
    }

    public boolean[] readBooleans(int i) throws KryoException {
        boolean[] zArr = new boolean[i];
        if (optional(i) == i) {
            byte[] bArr = this.buffer;
            int i2 = this.position;
            int i3 = 0;
            while (i3 < i) {
                zArr[i3] = bArr[i2] != 0;
                i3++;
                i2++;
            }
            this.position = i2;
        } else {
            for (int i4 = 0; i4 < i; i4++) {
                zArr[i4] = readBoolean();
            }
        }
        return zArr;
    }
}
