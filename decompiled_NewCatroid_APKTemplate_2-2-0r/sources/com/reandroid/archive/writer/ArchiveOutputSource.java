package com.reandroid.archive.writer;

import com.reandroid.archive.InputSource;
import com.reandroid.archive.block.LocalFileHeader;
import com.reandroid.archive.io.ArchiveFileEntrySource;
import com.reandroid.archive.io.ZipFileInput;

/* JADX INFO: loaded from: classes10.dex */
public class ArchiveOutputSource extends FileOutputSource {
    @Override // com.reandroid.archive.writer.OutputSource
    public /* bridge */ /* synthetic */ void setHeaderInterceptor(HeaderInterceptor headerInterceptor) {
        super.setHeaderInterceptor(headerInterceptor);
    }

    public ArchiveOutputSource(InputSource inputSource) {
        super(inputSource);
    }

    ArchiveFileEntrySource getArchiveSource() {
        return (ArchiveFileEntrySource) super.getInputSource();
    }

    @Override // com.reandroid.archive.writer.FileOutputSource
    EntryBuffer makeFromEntry() {
        ArchiveFileEntrySource archiveSource = getArchiveSource();
        ZipFileInput zipSource = archiveSource.getZipSource();
        LocalFileHeader localFileHeader = archiveSource.getArchiveEntry().getLocalFileHeader();
        if (localFileHeader.getMethod() != getInputSource().getMethod()) {
            return null;
        }
        return new EntryBuffer(zipSource, localFileHeader.getFileOffset(), localFileHeader.getDataSize());
    }

    @Override // com.reandroid.archive.writer.OutputSource
    LocalFileHeader createLocalFileHeader() {
        return getArchiveSource().getArchiveEntry().getLocalFileHeader().copy();
    }
}
