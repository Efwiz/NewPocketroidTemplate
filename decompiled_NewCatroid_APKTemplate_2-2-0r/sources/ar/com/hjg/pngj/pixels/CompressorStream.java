package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.PngjOutputException;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class CompressorStream extends FilterOutputStream {
    protected int block;
    public final int blockLen;
    protected long bytesIn;
    protected long bytesOut;
    boolean closed;
    protected boolean done;
    private byte[] firstBytes;
    protected OutputStream os;
    protected boolean storeFirstByte;
    public final long totalbytes;

    public abstract void done();

    protected abstract void mywrite(byte[] bArr, int i, int i2);

    public CompressorStream(OutputStream outputStream, int i, long j) {
        super(outputStream);
        this.closed = false;
        this.done = false;
        this.bytesIn = 0L;
        this.bytesOut = 0L;
        this.block = -1;
        this.storeFirstByte = false;
        i = i < 0 ? 4096 : i;
        j = j < 0 ? Long.MAX_VALUE : j;
        if (i < 1 || j < 1) {
            throw new RuntimeException(" maxBlockLen or totalLen invalid");
        }
        this.os = outputStream;
        this.blockLen = i;
        this.totalbytes = j;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        done();
        this.closed = true;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public final void write(byte[] bArr, int i, int i2) {
        this.block++;
        if (i2 <= this.blockLen) {
            mywrite(bArr, i, i2);
            if (this.storeFirstByte) {
                int i3 = this.block;
                byte[] bArr2 = this.firstBytes;
                if (i3 < bArr2.length) {
                    bArr2[i3] = bArr[i];
                }
            }
        } else {
            while (i2 > 0) {
                mywrite(bArr, i, this.blockLen);
                int i4 = this.blockLen;
                i += i4;
                i2 -= i4;
            }
        }
        if (this.bytesIn >= this.totalbytes) {
            done();
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public final void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) throws IOException {
        throw new PngjOutputException("should not be used");
    }

    public void reset() {
        reset(this.os);
    }

    public void reset(OutputStream outputStream) {
        if (this.closed) {
            throw new PngjOutputException("cannot reset, discarded object");
        }
        done();
        this.bytesIn = 0L;
        this.bytesOut = 0L;
        this.block = -1;
        this.done = false;
        this.os = outputStream;
    }

    public final double getCompressionRatio() {
        long j = this.bytesOut;
        if (j == 0) {
            return 1.0d;
        }
        return j / this.bytesIn;
    }

    public final long getBytesRaw() {
        return this.bytesIn;
    }

    public final long getBytesCompressed() {
        return this.bytesOut;
    }

    public OutputStream getOs() {
        return this.os;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public void flush() {
        OutputStream outputStream = this.os;
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException e) {
                throw new PngjOutputException(e);
            }
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public boolean isDone() {
        return this.done;
    }

    public byte[] getFirstBytes() {
        return this.firstBytes;
    }

    public void setStoreFirstByte(boolean z, int i) {
        this.storeFirstByte = z;
        if (z) {
            byte[] bArr = this.firstBytes;
            if (bArr == null || bArr.length < i) {
                this.firstBytes = new byte[i];
                return;
            }
            return;
        }
        this.firstBytes = null;
    }
}
