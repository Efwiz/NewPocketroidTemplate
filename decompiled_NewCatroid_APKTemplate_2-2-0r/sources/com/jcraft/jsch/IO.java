package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public class IO {
    InputStream in;
    OutputStream out;
    OutputStream out_ext;
    private boolean in_dontclose = false;
    private boolean out_dontclose = false;
    private boolean out_ext_dontclose = false;

    void setOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    void setOutputStream(OutputStream outputStream, boolean z) {
        this.out_dontclose = z;
        setOutputStream(outputStream);
    }

    void setExtOutputStream(OutputStream outputStream) {
        this.out_ext = outputStream;
    }

    void setExtOutputStream(OutputStream outputStream, boolean z) {
        this.out_ext_dontclose = z;
        setExtOutputStream(outputStream);
    }

    void setInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    void setInputStream(InputStream inputStream, boolean z) {
        this.in_dontclose = z;
        setInputStream(inputStream);
    }

    public void put(Packet packet) throws IOException {
        this.out.write(packet.buffer.buffer, 0, packet.buffer.index);
        this.out.flush();
    }

    void put(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        this.out.flush();
    }

    void put_ext(byte[] bArr, int i, int i2) throws IOException {
        this.out_ext.write(bArr, i, i2);
        this.out_ext.flush();
    }

    int getByte() throws IOException {
        return this.in.read();
    }

    void getByte(byte[] bArr) throws IOException {
        getByte(bArr, 0, bArr.length);
    }

    void getByte(byte[] bArr, int i, int i2) throws IOException {
        do {
            int i3 = this.in.read(bArr, i, i2);
            if (i3 < 0) {
                throw new IOException("End of IO Stream Read");
            }
            i += i3;
            i2 -= i3;
        } while (i2 > 0);
    }

    void out_close() {
        try {
            OutputStream outputStream = this.out;
            if (outputStream != null && !this.out_dontclose) {
                outputStream.close();
            }
            this.out = null;
        } catch (Exception unused) {
        }
    }

    public void close() {
        try {
            InputStream inputStream = this.in;
            if (inputStream != null && !this.in_dontclose) {
                inputStream.close();
            }
            this.in = null;
        } catch (Exception unused) {
        }
        out_close();
        try {
            OutputStream outputStream = this.out_ext;
            if (outputStream != null && !this.out_ext_dontclose) {
                outputStream.close();
            }
            this.out_ext = null;
        } catch (Exception unused2) {
        }
    }
}
