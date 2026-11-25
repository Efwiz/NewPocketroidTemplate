package com.reandroid.apk;

import com.reandroid.utils.CRCDigest;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
@Deprecated
public class CrcOutputStream extends OutputStream {
    private final CRCDigest crc = new CRCDigest();
    private long length;
    private long mCheckSum;

    public long getLength() {
        return this.length;
    }

    public long getCrcValue() {
        if (this.mCheckSum == 0) {
            this.mCheckSum = this.crc.getValue();
        }
        return this.mCheckSum;
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        this.crc.update(i);
        this.length++;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.crc.update(bArr, i, i2);
        this.length += (long) i2;
    }
}
