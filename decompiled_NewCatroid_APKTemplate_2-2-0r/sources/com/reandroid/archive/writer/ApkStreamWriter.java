package com.reandroid.archive.writer;

import com.reandroid.archive.InputSource;
import com.reandroid.archive.io.ZipStreamOutput;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public class ApkStreamWriter extends ApkWriter<ZipStreamOutput, StreamOutputSource> {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.archive.writer.ApkWriter
    public void prepareOutputs(StreamOutputSource[] streamOutputSourceArr) throws IOException {
    }

    public ApkStreamWriter(ZipStreamOutput zipStreamOutput, InputSource[] inputSourceArr) {
        super(zipStreamOutput, inputSourceArr);
    }

    public ApkStreamWriter(OutputStream outputStream, InputSource[] inputSourceArr) {
        this(new ZipStreamOutput(outputStream), inputSourceArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.archive.writer.ApkWriter
    public void writeApk(StreamOutputSource streamOutputSource, ZipAligner zipAligner) throws IOException {
        streamOutputSource.writeApk(getZipOutput(), zipAligner);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.archive.writer.ApkWriter
    public StreamOutputSource toOutputSource(InputSource inputSource) {
        return new StreamOutputSource(inputSource);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.archive.writer.ApkWriter
    public StreamOutputSource[] createOutArray(int i) {
        return new StreamOutputSource[i];
    }
}
