package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.PngjOutputException;
import java.io.OutputStream;
import java.util.zip.Deflater;

/* JADX INFO: loaded from: classes.dex */
public class CompressorStreamLz4 extends CompressorStream {
    private static final int MAX_BUFFER_SIZE = 16000;
    private byte[] buf;
    private final int buffer_size;
    private int inbuf;
    private final DeflaterEstimatorLz4 lz4;

    public CompressorStreamLz4(OutputStream outputStream, int i, long j) {
        super(outputStream, i, j);
        this.inbuf = 0;
        this.lz4 = new DeflaterEstimatorLz4();
        this.buffer_size = (int) (j > 16000 ? 16000L : j);
    }

    public CompressorStreamLz4(OutputStream outputStream, int i, long j, Deflater deflater) {
        this(outputStream, i, j);
    }

    public CompressorStreamLz4(OutputStream outputStream, int i, long j, int i2, int i3) {
        this(outputStream, i, j);
    }

    @Override // ar.com.hjg.pngj.pixels.CompressorStream
    public void mywrite(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return;
        }
        if (this.done || this.closed) {
            throw new PngjOutputException("write beyond end of stream");
        }
        this.bytesIn += (long) i2;
        while (i2 > 0) {
            if (this.inbuf == 0 && (i2 >= 16000 || this.bytesIn == this.totalbytes)) {
                this.bytesOut += (long) this.lz4.compressEstim(bArr, i, i2);
                i2 = 0;
            } else {
                if (this.buf == null) {
                    this.buf = new byte[this.buffer_size];
                }
                int i3 = this.inbuf;
                int i4 = i3 + i2;
                int i5 = this.buffer_size;
                int i6 = i4 <= i5 ? i2 : i5 - i3;
                if (i6 > 0) {
                    System.arraycopy(bArr, i, this.buf, i3, i6);
                }
                int i7 = this.inbuf + i6;
                this.inbuf = i7;
                i2 -= i6;
                i += i6;
                if (i7 == this.buffer_size) {
                    compressFromBuffer();
                }
            }
        }
    }

    void compressFromBuffer() {
        if (this.inbuf > 0) {
            this.bytesOut += (long) this.lz4.compressEstim(this.buf, 0, this.inbuf);
            this.inbuf = 0;
        }
    }

    @Override // ar.com.hjg.pngj.pixels.CompressorStream
    public void done() {
        if (this.done) {
            return;
        }
        compressFromBuffer();
        this.done = true;
        flush();
    }

    @Override // ar.com.hjg.pngj.pixels.CompressorStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        done();
        if (this.closed) {
            return;
        }
        super.close();
        this.buf = null;
    }

    @Override // ar.com.hjg.pngj.pixels.CompressorStream
    public void reset() {
        done();
        super.reset();
    }
}
