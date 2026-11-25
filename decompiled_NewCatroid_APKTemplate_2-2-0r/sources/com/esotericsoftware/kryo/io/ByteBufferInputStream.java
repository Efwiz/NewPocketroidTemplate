package com.esotericsoftware.kryo.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class ByteBufferInputStream extends InputStream {
    private ByteBuffer byteBuffer;

    public ByteBufferInputStream() {
    }

    public ByteBufferInputStream(int i) {
        this(ByteBuffer.allocate(i));
        flipBuffer(this.byteBuffer);
    }

    public ByteBufferInputStream(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.byteBuffer.hasRemaining()) {
            return this.byteBuffer.get() & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        int iMin = Math.min(this.byteBuffer.remaining(), i2);
        if (iMin == 0) {
            return -1;
        }
        this.byteBuffer.get(bArr, i, iMin);
        return iMin;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.byteBuffer.remaining();
    }

    private void flipBuffer(Buffer buffer) {
        buffer.flip();
    }
}
