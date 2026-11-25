package com.reandroid.archive.writer;

import com.reandroid.apk.APKLogger;
import com.reandroid.archive.ArchiveInfo;
import com.reandroid.archive.InputSource;
import com.reandroid.archive.WriteProgress;
import com.reandroid.archive.ZipSignature;
import com.reandroid.archive.block.ApkSignatureBlock;
import com.reandroid.archive.block.EndRecord;
import com.reandroid.archive.block.Zip64Locator;
import com.reandroid.archive.block.Zip64Record;
import com.reandroid.archive.block.ZipHeader;
import com.reandroid.archive.io.ZipOutput;
import com.reandroid.archive.writer.OutputSource;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ApkWriter<T extends ZipOutput, OUT extends OutputSource> implements Closeable {
    private APKLogger apkLogger;
    private ApkSignatureBlock apkSignatureBlock;
    private final InputSource[] inputSources;
    private WriteProgress writeProgress;
    private final T zipOutput;
    private final Object mLock = new Object();
    private ZipAligner zipAligner = ZipAligner.apkAligner();
    private final HeaderInterceptorChain interceptorChain = HeaderInterceptorChain.createDefault();

    void closeBuffer() throws IOException {
    }

    abstract OUT[] createOutArray(int i);

    abstract void prepareOutputs(OUT[] outArr) throws IOException;

    abstract OUT toOutputSource(InputSource inputSource);

    abstract void writeApk(OUT out, ZipAligner zipAligner) throws IOException;

    public ApkWriter(T t, InputSource[] inputSourceArr) {
        this.zipOutput = t;
        this.inputSources = inputSourceArr;
    }

    public void write() throws IOException {
        synchronized (this.mLock) {
            OutputSource[] outputSourceArrBuildOutputEntries = buildOutputEntries();
            prepareOutputs(outputSourceArrBuildOutputEntries);
            writeApkList(outputSourceArrBuildOutputEntries);
            closeBuffer();
            writeSignatureBlock();
            writeCEHList(outputSourceArrBuildOutputEntries);
            close();
        }
    }

    private void writeApkList(OUT[] outArr) throws IOException {
        int length = outArr.length;
        logMessage("Writing files: " + length);
        APKLogger apkLogger = getApkLogger();
        ZipAligner zipAligner = getZipAligner();
        for (int i = 0; i < length; i++) {
            OUT out = outArr[i];
            out.setAPKLogger(apkLogger);
            writeApk(out, zipAligner);
            if (i % 100 == 0) {
                out.logFileWrite();
            }
        }
    }

    private void writeCEHList(OUT[] outArr) throws IOException {
        EndRecord endRecord = new EndRecord();
        endRecord.setSignature(ZipSignature.END_RECORD);
        long jPosition = position();
        endRecord.setOffsetOfCentralDirectory(jPosition);
        int length = outArr.length;
        endRecord.setNumberOfDirectories(length);
        endRecord.setTotalNumberOfDirectories(length);
        ZipOutput zipOutput = getZipOutput();
        for (OUT out : outArr) {
            out.writeCEH(zipOutput);
        }
        endRecord.setLengthOfCentralDirectory(position() - jPosition);
        OutputStream outputStream = getOutputStream();
        Zip64Record zip64Record = endRecord.getZip64Record();
        if (zip64Record != null) {
            long jPosition2 = position();
            logMessage("ZIP64: " + zip64Record);
            zip64Record.writeBytes(outputStream);
            Zip64Locator zip64Locator = endRecord.getZip64Locator();
            zip64Locator.setOffsetZip64Record(jPosition2);
            logMessage("ZIP64: " + zip64Locator);
            zip64Locator.writeBytes(outputStream);
        }
        endRecord.writeBytes(getOutputStream());
    }

    /* JADX WARN: Multi-variable type inference failed */
    OUT[] buildOutputEntries() {
        InputSource[] inputSources = getInputSources();
        int length = inputSources.length;
        OUT[] outArr = (OUT[]) createOutArray(length);
        HeaderInterceptorChain interceptorChain = getInterceptorChain();
        if (interceptorChain.isDisabled()) {
            interceptorChain = null;
        }
        for (int i = 0; i < length; i++) {
            OutputSource outputSource = toOutputSource(inputSources[i]);
            outputSource.setHeaderInterceptor(interceptorChain);
            outArr[i] = outputSource;
        }
        return outArr;
    }

    long position() throws IOException {
        return this.zipOutput.position();
    }

    OutputStream getOutputStream() throws IOException {
        return this.zipOutput.getOutputStream();
    }

    public T getZipOutput() {
        return this.zipOutput;
    }

    InputSource[] getInputSources() {
        return this.inputSources;
    }

    public ZipAligner getZipAligner() {
        return this.zipAligner;
    }

    public void setZipAligner(ZipAligner zipAligner) {
        this.zipAligner = zipAligner;
    }

    public void setApkSignatureBlock(ApkSignatureBlock apkSignatureBlock) {
        this.apkSignatureBlock = apkSignatureBlock;
    }

    public ApkSignatureBlock getApkSignatureBlock() {
        return this.apkSignatureBlock;
    }

    void writeSignatureBlock() throws IOException {
        ApkSignatureBlock apkSignatureBlock = getApkSignatureBlock();
        if (apkSignatureBlock == null) {
            return;
        }
        logMessage("Writing signature block ...");
        long jPosition = position();
        if (ZipHeader.isZip64Length(jPosition)) {
            logMessage("ZIP64 mode, skip writing signature block!");
            return;
        }
        long j = 4096;
        int i = (int) ((j - (jPosition % j)) % j);
        OutputStream outputStream = getOutputStream();
        if (i > 0) {
            outputStream.write(new byte[i]);
        }
        apkSignatureBlock.updatePadding();
        apkSignatureBlock.writeBytes(outputStream);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.zipOutput.close();
    }

    public void setWriteProgress(WriteProgress writeProgress) {
        this.writeProgress = writeProgress;
    }

    public HeaderInterceptorChain getInterceptorChain() {
        return this.interceptorChain;
    }

    public void setArchiveInfo(ArchiveInfo archiveInfo) {
        getInterceptorChain().setArchiveInfo(archiveInfo);
    }

    public void setHeaderInterceptor(HeaderInterceptor headerInterceptor) {
        getInterceptorChain().setHeaderInterceptor(headerInterceptor);
    }

    public void setDataDescriptorFactory(DataDescriptorFactory dataDescriptorFactory) {
        getInterceptorChain().setDataDescriptorFactory(dataDescriptorFactory);
    }

    void onCompressFileProgress(String str, int i, long j) {
        WriteProgress writeProgress = this.writeProgress;
        if (writeProgress != null) {
            writeProgress.onCompressFile(str, i, j);
        }
    }

    APKLogger getApkLogger() {
        return this.apkLogger;
    }

    public void setAPKLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
    }

    void logMessage(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logMessage(str);
        }
    }
}
