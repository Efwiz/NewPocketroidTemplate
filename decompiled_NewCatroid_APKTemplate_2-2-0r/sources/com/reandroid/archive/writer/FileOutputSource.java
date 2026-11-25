package com.reandroid.archive.writer;

import com.reandroid.archive.InputSource;
import com.reandroid.archive.io.ZipFileOutput;
import com.reandroid.archive.io.ZipOutput;
import java.io.IOException;
import java.nio.channels.FileChannel;

/* JADX INFO: loaded from: classes10.dex */
class FileOutputSource extends OutputSource {
    private EntryBuffer entryBuffer;

    EntryBuffer makeFromEntry() {
        return null;
    }

    FileOutputSource(InputSource inputSource) {
        super(inputSource);
    }

    void makeBuffer(BufferFileInput bufferFileInput, BufferFileOutput bufferFileOutput) throws IOException {
        if (this.entryBuffer != null) {
            return;
        }
        EntryBuffer entryBufferMakeFromEntry = makeFromEntry();
        if (entryBufferMakeFromEntry != null) {
            this.entryBuffer = entryBufferMakeFromEntry;
        } else {
            this.entryBuffer = writeBuffer(bufferFileInput, bufferFileOutput);
        }
    }

    private EntryBuffer writeBuffer(BufferFileInput bufferFileInput, ZipOutput zipOutput) throws IOException {
        long jPosition = zipOutput.position();
        writeBuffer(zipOutput);
        return new EntryBuffer(bufferFileInput, jPosition, zipOutput.position() - jPosition);
    }

    void writeApk(ZipFileOutput zipFileOutput, ZipAligner zipAligner) throws IOException {
        logLargeFileWrite();
        EntryBuffer entryBuffer = this.entryBuffer;
        FileChannel fileChannel = entryBuffer.getZipFileInput().getFileChannel();
        fileChannel.position(entryBuffer.getOffset());
        writeLFH(zipFileOutput, zipAligner);
        writeData(fileChannel, entryBuffer.getLength(), zipFileOutput);
        writeDD(zipFileOutput);
    }

    private void writeData(FileChannel fileChannel, long j, ZipFileOutput zipFileOutput) throws IOException {
        getLocalFileHeader().setFileOffset(zipFileOutput.position());
        zipFileOutput.write(fileChannel, j);
    }
}
