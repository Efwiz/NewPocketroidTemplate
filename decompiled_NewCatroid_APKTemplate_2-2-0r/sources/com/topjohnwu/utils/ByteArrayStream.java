package com.topjohnwu.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class ByteArrayStream extends ByteArrayOutputStream {
    public byte[] getBuf() {
        return this.buf;
    }

    public synchronized void readFrom(InputStream inputStream) {
        readFrom(inputStream, Integer.MAX_VALUE);
    }

    public synchronized void readFrom(InputStream inputStream, int i) {
        byte[] bArr = new byte[4096];
        while (true) {
            try {
                int i2 = inputStream.read(bArr, 0, i < 4096 ? i : 4096);
                if (i2 <= 0) {
                    break;
                }
                write(bArr, 0, i2);
                i -= i2;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void writeTo(OutputStream outputStream, int i, int i2) throws IOException {
        outputStream.write(this.buf, i, i2);
    }

    public ByteArrayInputStream getInputStream() {
        return new ByteArrayInputStream(this.buf, 0, this.count);
    }
}
