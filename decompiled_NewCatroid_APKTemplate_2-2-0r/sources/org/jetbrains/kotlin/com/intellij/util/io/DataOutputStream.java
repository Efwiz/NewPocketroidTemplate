package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes3.dex */
public class DataOutputStream extends java.io.DataOutputStream {
    public DataOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override // java.io.DataOutputStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.DataOutput
    public void write(int i) throws IOException {
        this.out.write(i);
        this.written++;
    }

    @Override // java.io.DataOutputStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.DataOutput
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        this.written += i2;
    }

    public int getWrittenBytesCount() {
        return this.written;
    }

    int resetWrittenBytesCount() {
        int i = this.written;
        this.written = 0;
        return i;
    }
}
