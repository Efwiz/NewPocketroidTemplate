package com.reandroid.archive.writer;

import com.reandroid.archive.RenamedInputSource;
import com.reandroid.archive.io.ArchiveFileEntrySource;

/* JADX INFO: loaded from: classes10.dex */
public class RenamedArchiveSource extends ArchiveOutputSource {
    public RenamedArchiveSource(RenamedInputSource<?> renamedInputSource) {
        super(renamedInputSource);
    }

    @Override // com.reandroid.archive.writer.ArchiveOutputSource
    ArchiveFileEntrySource getArchiveSource() {
        return (ArchiveFileEntrySource) ((RenamedInputSource) getInputSource()).getParentInputSource(ArchiveFileEntrySource.class);
    }
}
