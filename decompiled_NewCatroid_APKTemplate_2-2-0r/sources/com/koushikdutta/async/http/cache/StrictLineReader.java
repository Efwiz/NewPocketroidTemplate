package com.koushikdutta.async.http.cache;

import com.koushikdutta.async.util.Charsets;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes10.dex */
class StrictLineReader implements Closeable {
    private static final byte CR = 13;
    private static final byte LF = 10;
    private byte[] buf;
    private int end;
    private final InputStream in;
    private int pos;

    public StrictLineReader(InputStream inputStream) {
        this(inputStream, 8192);
    }

    public StrictLineReader(InputStream inputStream, int i) {
        this(inputStream, i, Charsets.US_ASCII);
    }

    public StrictLineReader(InputStream inputStream, Charset charset) {
        this(inputStream, 8192, charset);
    }

    public StrictLineReader(InputStream inputStream, int i, Charset charset) {
        if (inputStream == null) {
            throw new NullPointerException("in == null");
        }
        if (charset == null) {
            throw new NullPointerException("charset == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        }
        if (!charset.equals(Charsets.US_ASCII) && !charset.equals(Charsets.UTF_8)) {
            throw new IllegalArgumentException("Unsupported encoding");
        }
        this.in = inputStream;
        this.buf = new byte[i];
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.in) {
            if (this.buf != null) {
                this.buf = null;
                this.in.close();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x002b  */
    public String readLine() throws IOException {
        int i;
        byte[] bArr;
        int i2;
        synchronized (this.in) {
            if (this.buf == null) {
                throw new IOException("LineReader is closed");
            }
            if (this.pos >= this.end) {
                fillBuf();
            }
            for (int i3 = this.pos; i3 != this.end; i3++) {
                byte[] bArr2 = this.buf;
                if (bArr2[i3] == 10) {
                    if (i3 != this.pos) {
                        i2 = i3 - 1;
                        if (bArr2[i2] != 13) {
                            i2 = i3;
                        }
                    } else {
                        i2 = i3;
                    }
                    byte[] bArr3 = this.buf;
                    int i4 = this.pos;
                    String str = new String(bArr3, i4, i2 - i4);
                    this.pos = i3 + 1;
                    return str;
                }
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((this.end - this.pos) + 80) { // from class: com.koushikdutta.async.http.cache.StrictLineReader.1
                @Override // java.io.ByteArrayOutputStream
                public String toString() {
                    return new String(this.buf, 0, (this.count <= 0 || this.buf[this.count + (-1)] != 13) ? this.count : this.count - 1);
                }
            };
            loop1: while (true) {
                byte[] bArr4 = this.buf;
                int i5 = this.pos;
                byteArrayOutputStream.write(bArr4, i5, this.end - i5);
                this.end = -1;
                fillBuf();
                i = this.pos;
                while (i != this.end) {
                    bArr = this.buf;
                    if (bArr[i] == 10) {
                        break loop1;
                    }
                    i++;
                }
            }
            int i6 = this.pos;
            if (i != i6) {
                byteArrayOutputStream.write(bArr, i6, i - i6);
            }
            this.pos = i + 1;
            return byteArrayOutputStream.toString();
        }
    }

    public int readInt() throws IOException {
        String line = readLine();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException unused) {
            throw new IOException("expected an int but was \"" + line + "\"");
        }
    }

    public boolean hasUnterminatedLine() {
        return this.end == -1;
    }

    private void fillBuf() throws IOException {
        InputStream inputStream = this.in;
        byte[] bArr = this.buf;
        int i = inputStream.read(bArr, 0, bArr.length);
        if (i == -1) {
            throw new EOFException();
        }
        this.pos = 0;
        this.end = i;
    }
}
