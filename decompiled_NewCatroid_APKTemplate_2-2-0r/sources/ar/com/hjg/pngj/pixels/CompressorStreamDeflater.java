package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.PngjOutputException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

/* JADX INFO: loaded from: classes.dex */
public class CompressorStreamDeflater extends CompressorStream {
    protected byte[] buf;
    protected Deflater deflater;
    protected boolean deflaterIsOwn;

    public CompressorStreamDeflater(OutputStream outputStream, int i, long j) {
        this(outputStream, i, j, null);
    }

    public CompressorStreamDeflater(OutputStream outputStream, int i, long j, Deflater deflater) {
        super(outputStream, i, j);
        this.buf = new byte[4092];
        this.deflaterIsOwn = true;
        this.deflater = deflater == null ? new Deflater() : deflater;
        this.deflaterIsOwn = deflater == null;
    }

    public CompressorStreamDeflater(OutputStream outputStream, int i, long j, int i2, int i3) {
        this(outputStream, i, j, new Deflater(i2));
        this.deflaterIsOwn = true;
        this.deflater.setStrategy(i3);
    }

    @Override // ar.com.hjg.pngj.pixels.CompressorStream
    public void mywrite(byte[] bArr, int i, int i2) {
        if (this.deflater.finished() || this.done || this.closed) {
            throw new PngjOutputException("write beyond end of stream");
        }
        this.deflater.setInput(bArr, i, i2);
        this.bytesIn += (long) i2;
        while (!this.deflater.needsInput()) {
            deflate();
        }
    }

    protected void deflate() {
        Deflater deflater = this.deflater;
        byte[] bArr = this.buf;
        int iDeflate = deflater.deflate(bArr, 0, bArr.length);
        if (iDeflate > 0) {
            this.bytesOut += (long) iDeflate;
            try {
                if (this.os != null) {
                    this.os.write(this.buf, 0, iDeflate);
                }
            } catch (IOException e) {
                throw new PngjOutputException(e);
            }
        }
    }

    @Override // ar.com.hjg.pngj.pixels.CompressorStream
    public void done() {
        if (this.done) {
            return;
        }
        if (!this.deflater.finished()) {
            this.deflater.finish();
            while (!this.deflater.finished()) {
                deflate();
            }
        }
        this.done = true;
        flush();
    }

    @Override // ar.com.hjg.pngj.pixels.CompressorStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        done();
        try {
            if (this.deflaterIsOwn) {
                this.deflater.end();
            }
        } catch (Exception unused) {
        }
        super.close();
    }

    @Override // ar.com.hjg.pngj.pixels.CompressorStream
    public void reset() {
        super.reset();
        this.deflater.reset();
    }
}
