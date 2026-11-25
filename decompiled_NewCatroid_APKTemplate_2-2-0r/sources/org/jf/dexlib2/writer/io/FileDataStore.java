package org.jf.dexlib2.writer.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import org.jf.util.RandomAccessFileInputStream;
import org.jf.util.RandomAccessFileOutputStream;

/* JADX INFO: loaded from: classes6.dex */
public class FileDataStore implements DexDataStore {
    private final RandomAccessFile raf;

    public FileDataStore(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        this.raf = randomAccessFile;
        randomAccessFile.setLength(0L);
    }

    @Override // org.jf.dexlib2.writer.io.DexDataStore
    public OutputStream outputAt(int i) {
        return new RandomAccessFileOutputStream(this.raf, i);
    }

    @Override // org.jf.dexlib2.writer.io.DexDataStore
    public InputStream readAt(int i) {
        return new RandomAccessFileInputStream(this.raf, i);
    }

    @Override // org.jf.dexlib2.writer.io.DexDataStore
    public void close() throws IOException {
        this.raf.close();
    }
}
