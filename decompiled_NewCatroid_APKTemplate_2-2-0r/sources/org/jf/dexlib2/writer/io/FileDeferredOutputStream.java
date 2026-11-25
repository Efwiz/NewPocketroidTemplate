package org.jf.dexlib2.writer.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.jf.util.io.ByteStreams;

/* JADX INFO: loaded from: classes6.dex */
public class FileDeferredOutputStream extends DeferredOutputStream {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private final File backingFile;
    private final NakedBufferedOutputStream output;
    private int writtenBytes;

    public FileDeferredOutputStream(File file) throws FileNotFoundException {
        this(file, 4096);
    }

    public FileDeferredOutputStream(File file, int i) throws FileNotFoundException {
        this.backingFile = file;
        this.output = new NakedBufferedOutputStream(new FileOutputStream(file), i);
    }

    @Override // org.jf.dexlib2.writer.io.DeferredOutputStream
    public void writeTo(OutputStream outputStream) throws IOException {
        byte[] buffer = this.output.getBuffer();
        int count = this.output.getCount();
        this.output.resetBuffer();
        this.output.close();
        if (count != this.writtenBytes) {
            ByteStreams.copy(new FileInputStream(this.backingFile), outputStream);
            this.backingFile.delete();
        }
        outputStream.write(buffer, 0, count);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        this.output.write(i);
        this.writtenBytes++;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        this.output.write(bArr);
        this.writtenBytes += bArr.length;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.output.write(bArr, i, i2);
        this.writtenBytes += i2;
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.output.flush();
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.output.close();
    }

    private static class NakedBufferedOutputStream extends BufferedOutputStream {
        public NakedBufferedOutputStream(OutputStream outputStream) {
            super(outputStream);
        }

        public NakedBufferedOutputStream(OutputStream outputStream, int i) {
            super(outputStream, i);
        }

        public int getCount() {
            return this.count;
        }

        public void resetBuffer() {
            this.count = 0;
        }

        public byte[] getBuffer() {
            return this.buf;
        }
    }

    public static DeferredOutputStreamFactory getFactory(File file) {
        return getFactory(file, 4096);
    }

    public static DeferredOutputStreamFactory getFactory(final File file, final int i) {
        return new DeferredOutputStreamFactory() { // from class: org.jf.dexlib2.writer.io.FileDeferredOutputStream.1
            @Override // org.jf.dexlib2.writer.io.DeferredOutputStreamFactory
            public DeferredOutputStream makeDeferredOutputStream() throws IOException {
                return new FileDeferredOutputStream(File.createTempFile("dexlibtmp", null, file), i);
            }
        };
    }
}
