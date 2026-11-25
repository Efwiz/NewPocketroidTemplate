package com.reandroid.archive;

import com.reandroid.archive.InputSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public class RenamedInputSource<T extends InputSource> extends InputSource {
    private final T inputSource;

    public RenamedInputSource(String str, T t) {
        super(str);
        this.inputSource = t;
        super.setMethod(t.getMethod());
        super.setSort(t.getSort());
    }

    public <T1 extends InputSource> T1 getParentInputSource(Class<T1> cls) {
        RenamedInputSource renamedInputSource = (T1) getInputSource();
        if (cls.isInstance(renamedInputSource)) {
            return renamedInputSource;
        }
        if (renamedInputSource instanceof RenamedInputSource) {
            return (T1) renamedInputSource.getParentInputSource(cls);
        }
        return null;
    }

    public T getInputSource() {
        return this.inputSource;
    }

    @Override // com.reandroid.archive.InputSource
    public void close(InputStream inputStream) throws IOException {
        getInputSource().close(inputStream);
    }

    @Override // com.reandroid.archive.InputSource
    public long getLength() throws IOException {
        return getInputSource().getLength();
    }

    @Override // com.reandroid.archive.InputSource
    public long getCrc() throws IOException {
        return getInputSource().getCrc();
    }

    @Override // com.reandroid.archive.InputSource
    public void write(File file) throws IOException {
        getInputSource().write(file);
    }

    @Override // com.reandroid.archive.InputSource
    public long write(OutputStream outputStream) throws IOException {
        return getInputSource().write(outputStream);
    }

    @Override // com.reandroid.archive.InputSource
    public InputStream openStream() throws IOException {
        return getInputSource().openStream();
    }
}
