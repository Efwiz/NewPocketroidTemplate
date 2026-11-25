package com.koushikdutta.async.stream;

import com.koushikdutta.async.ByteBufferList;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes10.dex */
public class ByteBufferListInputStream extends InputStream {
    ByteBufferList bb;

    public ByteBufferListInputStream(ByteBufferList byteBufferList) {
        this.bb = byteBufferList;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.bb.remaining() <= 0) {
            return -1;
        }
        return this.bb.get();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.bb.remaining() <= 0) {
            return -1;
        }
        int iMin = Math.min(i2, this.bb.remaining());
        this.bb.get(bArr, i, iMin);
        return iMin;
    }
}
