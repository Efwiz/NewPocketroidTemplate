package com.reandroid.archive;

import com.reandroid.archive.io.ArchiveFileEntrySource;
import com.reandroid.archive.io.ZipFileInput;
import com.reandroid.utils.io.FileUtil;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/* JADX INFO: loaded from: classes10.dex */
public class ArchiveFile extends Archive<ZipFileInput> {
    public ArchiveFile(ZipFileInput zipFileInput) throws IOException {
        super(zipFileInput);
    }

    public ArchiveFile(File file) throws IOException {
        this(new ZipFileInput(file));
    }

    @Override // com.reandroid.archive.Archive
    InputSource createInputSource(ArchiveEntry archiveEntry) {
        return new ArchiveFileEntrySource(getZipInput(), archiveEntry);
    }

    @Override // com.reandroid.archive.Archive
    void extractStored(File file, ArchiveEntry archiveEntry) throws IOException {
        FileUtil.createNewFile(file);
        FileChannel fileChannelOpen = FileChannel.open(file.toPath(), StandardOpenOption.WRITE);
        FileChannel fileChannel = getZipInput().getFileChannel();
        fileChannel.position(archiveEntry.getFileOffset());
        fileChannelOpen.transferFrom(fileChannel, 0L, archiveEntry.getDataSize());
        fileChannelOpen.close();
    }
}
