package com.reandroid.archive.io;

import com.reandroid.utils.CRCDigest;
import com.reandroid.utils.HexUtil;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes10.dex */
public class CountingInputStream<T extends InputStream> extends InputStream {
    private final CRCDigest crc;
    private final T inputStream;
    private long mCheckSum;
    private boolean mFinished;
    private long size;

    public CountingInputStream(T t, boolean z) {
        this.inputStream = t;
        this.crc = z ? null : new CRCDigest();
    }

    public CountingInputStream(T t) {
        this(t, false);
    }

    public T getInputStream() {
        return this.inputStream;
    }

    public long getSize() {
        return this.size;
    }

    public long getCrc() {
        return this.mCheckSum;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.mFinished) {
            return -1;
        }
        int i3 = this.inputStream.read(bArr, i, i2);
        if (i3 < 0) {
            onFinished();
            return i3;
        }
        this.size += (long) i3;
        CRCDigest cRCDigest = this.crc;
        if (cRCDigest != null) {
            cRCDigest.update(bArr, i, i3);
        }
        return i3;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        throw new IOException("Why one byte ?");
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        if (this.mFinished) {
            return 0L;
        }
        if (j <= 0) {
            return j;
        }
        T t = this.inputStream;
        if (t instanceof CountingInputStream) {
            return t.skip(j);
        }
        int i = j < ((long) 1024000) ? (int) j : 1024000;
        byte[] bArr = new byte[i];
        long j2 = j;
        while (true) {
            int i2 = t.read(bArr, 0, i);
            if (i2 < 0) {
                onFinished();
                break;
            }
            j2 -= (long) i2;
            if (j2 <= 0) {
                break;
            }
            if (j2 < i) {
                i = (int) j2;
            }
        }
        return j - j2;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.mFinished) {
            onFinished();
        }
        this.inputStream.close();
    }

    private void onFinished() {
        this.mFinished = true;
        CRCDigest cRCDigest = this.crc;
        if (cRCDigest != null) {
            this.mCheckSum = cRCDigest.getValue();
        }
    }

    public String toString() {
        if (!this.mFinished || this.crc == null) {
            return "[" + this.size + "]: " + this.inputStream.getClass().getSimpleName();
        }
        return "[size=" + this.size + ", crc=" + HexUtil.toHex8(this.mCheckSum) + "]: " + this.inputStream.getClass().getSimpleName();
    }
}
