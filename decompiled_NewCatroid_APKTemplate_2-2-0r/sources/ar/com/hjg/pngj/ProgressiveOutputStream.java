package ar.com.hjg.pngj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
abstract class ProgressiveOutputStream extends ByteArrayOutputStream {
    private long countFlushed = 0;
    private int size;

    protected abstract void flushBuffer(byte[] bArr, int i);

    public ProgressiveOutputStream(int i) {
        this.size = i;
    }

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        try {
            flush();
        } catch (Exception unused) {
        }
        super.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public final void flush() throws IOException {
        super.flush();
        checkFlushBuffer(true);
    }

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream
    public final void write(byte[] bArr, int i, int i2) {
        super.write(bArr, i, i2);
        checkFlushBuffer(false);
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr) throws IOException {
        super.write(bArr);
        checkFlushBuffer(false);
    }

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream
    public final void write(int i) {
        super.write(i);
        checkFlushBuffer(false);
    }

    @Override // java.io.ByteArrayOutputStream
    public final synchronized void reset() {
        super.reset();
    }

    private final void checkFlushBuffer(boolean z) {
        while (true) {
            if (!z && this.count < this.size) {
                return;
            }
            int i = this.size;
            if (i > this.count) {
                i = this.count;
            }
            if (i == 0) {
                return;
            }
            flushBuffer(this.buf, i);
            this.countFlushed += (long) i;
            int i2 = this.count - i;
            this.count = i2;
            if (i2 > 0) {
                System.arraycopy(this.buf, i, this.buf, 0, i2);
            }
        }
    }

    public void setSize(int i) {
        this.size = i;
        System.out.println("setting size: " + i + " count" + this.count);
        checkFlushBuffer(false);
    }

    public long getCountFlushed() {
        return this.countFlushed;
    }
}
