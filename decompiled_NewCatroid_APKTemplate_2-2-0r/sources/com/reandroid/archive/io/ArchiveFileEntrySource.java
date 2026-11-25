package com.reandroid.archive.io;

import com.reandroid.archive.Archive;
import com.reandroid.archive.ArchiveEntry;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/* JADX INFO: loaded from: classes10.dex */
public class ArchiveFileEntrySource extends ArchiveEntrySource<ZipFileInput> {
    public ArchiveFileEntrySource(ZipFileInput zipFileInput, ArchiveEntry archiveEntry) {
        super(zipFileInput, archiveEntry);
        setSort(archiveEntry.getIndex());
    }

    @Override // com.reandroid.archive.InputSource
    public byte[] getBytes(int i) throws IOException {
        FileChannel fileChannel = getFileChannel();
        if (getMethod() != Archive.STORED || fileChannel == null) {
            return super.getBytes(i);
        }
        byte[] bArr = new byte[i];
        fileChannel.read(ByteBuffer.wrap(bArr));
        return bArr;
    }

    FileChannel getFileChannel() throws IOException {
        FileChannel fileChannel = getZipSource().getFileChannel();
        fileChannel.position(getArchiveEntry().getFileOffset());
        return fileChannel;
    }

    @Override // com.reandroid.archive.InputSource
    public void write(File file) throws IOException {
        FileChannel fileChannel = getFileChannel();
        if (getMethod() != Archive.STORED || fileChannel == null) {
            super.write(file);
            return;
        }
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (file.isFile()) {
            file.delete();
        }
        file.createNewFile();
        FileChannel fileChannelOpen = FileChannel.open(file.toPath(), StandardOpenOption.WRITE);
        fileChannelOpen.transferFrom(fileChannel, 0L, getLength());
        fileChannelOpen.close();
    }
}
