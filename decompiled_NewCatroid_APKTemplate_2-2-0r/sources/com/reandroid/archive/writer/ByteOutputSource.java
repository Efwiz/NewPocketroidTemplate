package com.reandroid.archive.writer;

import com.reandroid.archive.InputSource;
import com.reandroid.archive.io.ZipByteOutput;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
class ByteOutputSource extends OutputSource {
    ByteOutputSource(InputSource inputSource) {
        super(inputSource);
    }

    void writeApk(ZipByteOutput zipByteOutput, ZipAligner zipAligner) throws IOException {
        ZipByteOutput zipByteOutput2 = new ZipByteOutput();
        writeBuffer(zipByteOutput2);
        zipByteOutput2.close();
        writeLFH(zipByteOutput, zipAligner);
        getLocalFileHeader().setFileOffset(zipByteOutput.position());
        zipByteOutput.write(zipByteOutput2.toByteArray());
        writeDD(zipByteOutput);
    }
}
