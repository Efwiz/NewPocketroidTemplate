package com.reandroid.archive.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/* JADX INFO: loaded from: classes10.dex */
public class ZipFileOutput extends ZipOutput {
    private final File file;
    private FileChannel fileChannel;
    private FileChannelOutputStream outputStream;

    public ZipFileOutput(File file) throws IOException {
        initFile(file);
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public void write(FileChannel fileChannel, long j) throws IOException {
        FileChannel fileChannel2 = getFileChannel();
        long jPosition = fileChannel2.position();
        fileChannel2.position(jPosition + fileChannel2.transferFrom(fileChannel, jPosition, j));
    }

    @Override // com.reandroid.archive.io.RandomStream
    public long position() throws IOException {
        return getFileChannel().position();
    }

    @Override // com.reandroid.archive.io.RandomStream
    public void position(long j) throws IOException {
        getFileChannel().position(j);
    }

    @Override // com.reandroid.archive.io.RandomStream, java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        FileChannel fileChannel = this.fileChannel;
        if (fileChannel != null) {
            fileChannel.close();
        }
    }

    @Override // com.reandroid.archive.io.RandomStream, java.nio.channels.Channel
    public boolean isOpen() {
        FileChannel fileChannel = this.fileChannel;
        if (fileChannel != null) {
            return fileChannel.isOpen();
        }
        return false;
    }

    private FileChannel getFileChannel() throws IOException {
        FileChannel fileChannelOpen;
        FileChannel fileChannel = this.fileChannel;
        if (fileChannel != null) {
            return fileChannel;
        }
        synchronized (this) {
            fileChannelOpen = FileChannel.open(this.file.toPath(), StandardOpenOption.WRITE);
            this.fileChannel = fileChannelOpen;
        }
        return fileChannelOpen;
    }

    @Override // com.reandroid.archive.io.WriteOnlyStream
    public void write(InputStream inputStream) throws IOException {
        FileChannel fileChannel = getFileChannel();
        long jPosition = fileChannel.position();
        byte[] bArr = new byte[10240000];
        long j = 0;
        while (true) {
            int i = inputStream.read(bArr, 0, 10240000);
            if (i > 0) {
                fileChannel.write(ByteBuffer.wrap(bArr, 0, i));
                j += (long) i;
            } else {
                inputStream.close();
                fileChannel.position(jPosition + j);
                return;
            }
        }
    }

    @Override // com.reandroid.archive.io.WriteOnlyStream
    public FileChannelOutputStream getOutputStream() throws IOException {
        FileChannelOutputStream fileChannelOutputStream = this.outputStream;
        if (fileChannelOutputStream != null) {
            return fileChannelOutputStream;
        }
        FileChannelOutputStream fileChannelOutputStream2 = new FileChannelOutputStream(getFileChannel());
        this.outputStream = fileChannelOutputStream2;
        return fileChannelOutputStream2;
    }

    private static void initFile(File file) throws IOException {
        if (file.isDirectory()) {
            throw new IOException("Not file: " + file);
        }
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
    }
}
