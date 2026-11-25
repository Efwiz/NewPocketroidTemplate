package com.reandroid.archive.io;

import com.reandroid.utils.CRCDigest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public class CountingOutputStream<T extends OutputStream> extends OutputStream {
    private CRCDigest crc;
    private boolean mClosed;
    private final T outputStream;
    private long size;

    public CountingOutputStream(T t, boolean z) {
        this.outputStream = t;
        this.crc = z ? null : new CRCDigest();
    }

    public CountingOutputStream(T t) {
        this(t, false);
    }

    public void disableCrc(boolean z) {
        if (!z) {
            if (this.crc == null) {
                this.crc = new CRCDigest();
                return;
            }
            return;
        }
        this.crc = null;
    }

    public void reset() {
        this.crc = new CRCDigest();
        this.size = 0L;
    }

    public T getOutputStream() {
        return this.outputStream;
    }

    public long getSize() {
        return this.size;
    }

    public long getCrc() {
        CRCDigest cRCDigest = this.crc;
        if (cRCDigest != null) {
            return cRCDigest.getValue();
        }
        return 0L;
    }

    public void write(InputStream inputStream) throws IOException {
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

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return;
        }
        this.outputStream.write(bArr, i, i2);
        this.size += (long) i2;
        CRCDigest cRCDigest = this.crc;
        if (cRCDigest != null) {
            cRCDigest.update(bArr, i, i2);
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        write(new byte[]{(byte) i}, 0, 1);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.outputStream.close();
        this.mClosed = true;
    }

    public boolean isOpen() {
        return !this.mClosed;
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.outputStream.flush();
    }

    public String toString() {
        return "[" + this.size + "]: " + this.outputStream.getClass().getSimpleName();
    }
}
