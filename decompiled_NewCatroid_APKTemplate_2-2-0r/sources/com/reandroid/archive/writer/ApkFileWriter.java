package com.reandroid.archive.writer;

import com.reandroid.archive.InputSource;
import com.reandroid.archive.RenamedInputSource;
import com.reandroid.archive.io.ArchiveFileEntrySource;
import com.reandroid.archive.io.ZipFileOutput;
import com.reandroid.arsc.chunk.TableBlock;
import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: loaded from: classes10.dex */
public class ApkFileWriter extends ApkWriter<ZipFileOutput, FileOutputSource> {
    private BufferFileInput buffer;

    public ApkFileWriter(File file, InputSource[] inputSourceArr) throws IOException {
        super(new ZipFileOutput(file), inputSourceArr);
    }

    @Override // com.reandroid.archive.writer.ApkWriter
    void closeBuffer() throws IOException {
        this.buffer.close();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.archive.writer.ApkWriter
    public void writeApk(FileOutputSource fileOutputSource, ZipAligner zipAligner) throws IOException {
        fileOutputSource.writeApk(getZipOutput(), zipAligner);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.archive.writer.ApkWriter
    public void prepareOutputs(FileOutputSource[] fileOutputSourceArr) throws IOException {
        logMessage("Buffering compress changed files ...");
        BufferFileInput bufferFileInputWriteBuffer = writeBuffer(fileOutputSourceArr);
        bufferFileInputWriteBuffer.unlock();
        this.buffer = bufferFileInputWriteBuffer;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.archive.writer.ApkWriter
    public FileOutputSource[] createOutArray(int i) {
        return new FileOutputSource[i];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.archive.writer.ApkWriter
    public FileOutputSource toOutputSource(InputSource inputSource) {
        if (inputSource instanceof ArchiveFileEntrySource) {
            return new ArchiveOutputSource(inputSource);
        }
        if (inputSource instanceof RenamedInputSource) {
            RenamedInputSource renamedInputSource = (RenamedInputSource) inputSource;
            if (renamedInputSource.getParentInputSource(ArchiveFileEntrySource.class) != null) {
                return new RenamedArchiveSource(renamedInputSource);
            }
        }
        return new FileOutputSource(inputSource);
    }

    private BufferFileInput writeBuffer(FileOutputSource[] fileOutputSourceArr) throws IOException {
        File bufferFile = getBufferFile();
        BufferFileOutput bufferFileOutput = new BufferFileOutput(bufferFile);
        BufferFileInput bufferFileInput = new BufferFileInput(bufferFile);
        FileOutputSource fileOutputSource = null;
        for (FileOutputSource fileOutputSource2 : fileOutputSourceArr) {
            InputSource inputSource = fileOutputSource2.getInputSource();
            if (fileOutputSource == null && TableBlock.FILE_NAME.equals(inputSource.getAlias())) {
                fileOutputSource = fileOutputSource2;
            } else {
                onCompressFileProgress(inputSource.getAlias(), inputSource.getMethod(), bufferFileOutput.position());
                fileOutputSource2.makeBuffer(bufferFileInput, bufferFileOutput);
            }
        }
        if (fileOutputSource != null) {
            fileOutputSource.makeBuffer(bufferFileInput, bufferFileOutput);
        }
        bufferFileOutput.close();
        return bufferFileInput;
    }

    private File getBufferFile() {
        File file;
        File file2 = getZipOutput().getFile();
        File parentFile = file2.getParentFile();
        String str = Constants.TMP_DIRECTORY_NAME + file2.getAbsolutePath().hashCode();
        if (parentFile != null) {
            file = new File(parentFile, str);
        } else {
            file = new File(str);
        }
        file.deleteOnExit();
        return file;
    }
}
