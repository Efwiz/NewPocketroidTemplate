package com.reandroid.archive;

import com.reandroid.common.FileChannelInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes10.dex */
public class FileInputSource extends InputSource {
    private final File file;

    public FileInputSource(File file, String str) {
        super(str);
        this.file = file;
    }

    @Override // com.reandroid.archive.InputSource
    public byte[] getBytes(int i) throws IOException {
        return FileChannelInputStream.read(getFile(), i);
    }

    @Override // com.reandroid.archive.InputSource
    public long getLength() {
        return getFile().length();
    }

    @Override // com.reandroid.archive.InputSource
    public void close(InputStream inputStream) throws IOException {
        inputStream.close();
    }

    @Override // com.reandroid.archive.InputSource
    public FileChannelInputStream openStream() throws IOException {
        return new FileChannelInputStream(this.file);
    }

    public File getFile() {
        return this.file;
    }
}
