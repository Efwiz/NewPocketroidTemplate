package com.reandroid.archive.writer;

import com.reandroid.archive.io.ZipFileInput;

/* JADX INFO: loaded from: classes10.dex */
public class EntryBuffer {
    private final long length;
    private final long offset;
    private final ZipFileInput zipFileInput;

    public EntryBuffer(ZipFileInput zipFileInput, long j, long j2) {
        this.zipFileInput = zipFileInput;
        this.offset = j;
        this.length = j2;
    }

    public ZipFileInput getZipFileInput() {
        return this.zipFileInput;
    }

    public long getOffset() {
        return this.offset;
    }

    public long getLength() {
        return this.length;
    }
}
