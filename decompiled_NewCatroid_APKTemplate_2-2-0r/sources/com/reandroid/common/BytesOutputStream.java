package com.reandroid.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes10.dex */
public class BytesOutputStream extends ByteArrayOutputStream {
    private int mLastGrow;

    public BytesOutputStream(int i) {
        super(check(i));
    }

    private static int check(int i) {
        if (i >= 0) {
            return i;
        }
        throw new IllegalArgumentException("Negative: " + i);
    }

    public BytesOutputStream() {
        this(32);
    }

    public int position() {
        return size();
    }

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream
    public synchronized void write(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return;
        }
        ensureCapacity(this.count + i2);
        System.arraycopy(bArr, i, this.buf, this.count, i2);
        this.count += i2;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    private void ensureCapacity(int i) {
        if (i - this.buf.length > 0) {
            grow(i);
        }
    }

    private void grow(int i) {
        if (this.mLastGrow == 0) {
            this.mLastGrow = 1 << 1;
        }
        int i2 = this.mLastGrow << 1;
        this.mLastGrow = i2;
        if (i2 > 65535) {
            this.mLastGrow = 65535;
        }
        byte[] bArr = this.buf;
        int length = bArr.length;
        int i3 = this.mLastGrow + i;
        if (i3 - i >= 0) {
            i = i3;
        }
        byte[] bArr2 = new byte[i];
        for (int i4 = 0; i4 < length; i4++) {
            bArr2[i4] = bArr[i4];
        }
        this.buf = bArr2;
    }

    public void write(InputStream inputStream) throws IOException {
        if (inputStream instanceof BytesInputStream) {
            write((BytesInputStream) inputStream);
            return;
        }
        byte[] bArr = new byte[2048];
        while (true) {
            int i = inputStream.read(bArr, 0, bArr.length);
            if (i >= 0) {
                write(bArr, 0, i);
                if (bArr.length < 81920) {
                    bArr = new byte[bArr.length + 500];
                }
            } else {
                inputStream.close();
                return;
            }
        }
    }

    public void write(BytesInputStream bytesInputStream) throws IOException {
        byte[] byteArray = bytesInputStream.toByteArray();
        write(byteArray, 0, byteArray.length);
    }

    @Override // java.io.ByteArrayOutputStream
    public synchronized byte[] toByteArray() {
        int i = this.count;
        byte[] bArr = this.buf;
        if (i == bArr.length) {
            return bArr;
        }
        if (i == 0) {
            byte[] bArr2 = new byte[0];
            this.buf = bArr2;
            return bArr2;
        }
        byte[] bArr3 = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr3[i2] = bArr[i2];
        }
        this.buf = bArr3;
        return bArr3;
    }

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        toByteArray();
    }

    @Override // java.io.ByteArrayOutputStream
    public String toString() {
        return "pos = " + size();
    }
}
