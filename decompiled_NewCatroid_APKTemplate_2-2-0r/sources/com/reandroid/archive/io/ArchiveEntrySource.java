package com.reandroid.archive.io;

import com.reandroid.archive.Archive;
import com.reandroid.archive.ArchiveEntry;
import com.reandroid.archive.InputSource;
import com.reandroid.archive.io.ZipInput;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/* JADX INFO: loaded from: classes10.dex */
public class ArchiveEntrySource<T extends ZipInput> extends InputSource {
    private final ArchiveEntry archiveEntry;
    private final T zipInput;

    public ArchiveEntrySource(T t, ArchiveEntry archiveEntry) {
        super(archiveEntry.getSanitizedName());
        this.zipInput = t;
        this.archiveEntry = archiveEntry;
        setMethod(archiveEntry.getMethod());
    }

    public T getZipSource() {
        return this.zipInput;
    }

    public ArchiveEntry getArchiveEntry() {
        return this.archiveEntry;
    }

    @Override // com.reandroid.archive.InputSource
    public InputStream openStream() throws IOException {
        if (isCompressed()) {
            return openInflaterInputStream();
        }
        ArchiveEntry archiveEntry = getArchiveEntry();
        return getZipSource().getInputStream(archiveEntry.getFileOffset(), archiveEntry.getDataSize());
    }

    private boolean isCompressed() {
        ArchiveEntry archiveEntry = getArchiveEntry();
        int method = archiveEntry.getMethod();
        if (method == Archive.STORED) {
            return false;
        }
        if (method == Archive.DEFLATED) {
            return true;
        }
        try {
            openInflaterInputStream().read(new byte[1024], 0, 1024);
            archiveEntry.setMethod(Archive.DEFLATED);
            setMethod(Archive.DEFLATED);
            return true;
        } catch (Throwable unused) {
            archiveEntry.setMethod(Archive.STORED);
            setMethod(Archive.STORED);
            long size = archiveEntry.getSize();
            long compressedSize = archiveEntry.getCompressedSize();
            if (size > compressedSize) {
                archiveEntry.setCompressedSize(size);
            } else if (compressedSize > size) {
                archiveEntry.setSize(compressedSize);
            }
            return false;
        }
    }

    private InputStream openInflaterInputStream() throws IOException {
        ArchiveEntry archiveEntry = getArchiveEntry();
        return new InflaterInputStream(getZipSource().getInputStream(archiveEntry.getFileOffset(), archiveEntry.getDataSize()), new Inflater(true), 512);
    }

    @Override // com.reandroid.archive.InputSource
    public long getLength() throws IOException {
        return getArchiveEntry().getDataSize();
    }

    @Override // com.reandroid.archive.InputSource
    public long getCrc() throws IOException {
        return getArchiveEntry().getCrc();
    }
}
