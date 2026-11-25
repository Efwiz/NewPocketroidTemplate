package com.reandroid.archive.io;

import com.reandroid.common.FileChannelInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/* JADX INFO: loaded from: classes10.dex */
public class ZipFileInput extends ZipInput {
    private final File file;
    private FileChannel fileChannel;
    private InputStream mCurrentInputStream;

    public ZipFileInput(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    @Override // com.reandroid.archive.io.RandomStream
    public long position() throws IOException {
        FileChannel fileChannel = this.fileChannel;
        if (fileChannel != null) {
            return fileChannel.position();
        }
        return 0L;
    }

    @Override // com.reandroid.archive.io.RandomStream
    public void position(long j) throws IOException {
        getFileChannel().position(j);
    }

    @Override // com.reandroid.archive.io.ReadOnlyStream
    public long getLength() {
        return this.file.length();
    }

    @Override // com.reandroid.archive.io.ReadOnlyStream
    public InputStream getInputStream(long j, long j2) throws IOException {
        closeCurrentInputStream();
        FileChannel fileChannel = getFileChannel();
        fileChannel.position(j);
        FileChannelInputStream fileChannelInputStream = new FileChannelInputStream(fileChannel, j2);
        this.mCurrentInputStream = fileChannelInputStream;
        return fileChannelInputStream;
    }

    @Override // com.reandroid.archive.io.ZipInput
    public byte[] getFooter(int i) throws IOException {
        long length = getLength();
        if (i > length) {
            i = (int) length;
        }
        FileChannel fileChannel = getFileChannel();
        fileChannel.position(length - ((long) i));
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i);
        fileChannel.read(byteBufferAllocate);
        return byteBufferAllocate.array();
    }

    public FileChannel getFileChannel() throws IOException {
        FileChannel fileChannelOpen;
        FileChannel fileChannel = this.fileChannel;
        if (fileChannel != null) {
            return fileChannel;
        }
        synchronized (this) {
            if (!this.file.isFile()) {
                throw new FileNotFoundException("No such file: " + this.file);
            }
            fileChannelOpen = FileChannel.open(this.file.toPath(), StandardOpenOption.READ);
            this.fileChannel = fileChannelOpen;
        }
        return fileChannelOpen;
    }

    @Override // com.reandroid.archive.io.RandomStream, java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        closeCurrentInputStream();
        closeChannel();
    }

    @Override // com.reandroid.archive.io.RandomStream, java.nio.channels.Channel
    public boolean isOpen() {
        boolean zIsOpen;
        FileChannel fileChannel = this.fileChannel;
        if (fileChannel == null) {
            return false;
        }
        synchronized (this) {
            zIsOpen = fileChannel.isOpen();
        }
        return zIsOpen;
    }

    private void closeChannel() throws IOException {
        FileChannel fileChannel = this.fileChannel;
        if (fileChannel == null) {
            return;
        }
        synchronized (this) {
            fileChannel.close();
            this.fileChannel = null;
        }
    }

    private void closeCurrentInputStream() throws IOException {
        InputStream inputStream = this.mCurrentInputStream;
        if (inputStream == null) {
            return;
        }
        inputStream.close();
        this.mCurrentInputStream = null;
    }

    public String toString() {
        return "File: " + this.file;
    }
}
