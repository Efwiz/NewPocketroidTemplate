package com.jcraft.jsch.jcraft;

import com.jcraft.jzlib.ZStream;

/* JADX INFO: loaded from: classes10.dex */
public class Compression implements com.jcraft.jsch.Compression {
    private static final int BUF_SIZE = 4096;
    private byte[] inflated_buf;
    private int type;
    private final int buffer_margin = 52;
    private byte[] tmpbuf = new byte[4096];
    private ZStream stream = new ZStream();

    @Override // com.jcraft.jsch.Compression
    public void init(int i, int i2) {
        if (i == 1) {
            this.stream.deflateInit(i2);
            this.type = 1;
        } else if (i == 0) {
            this.stream.inflateInit();
            this.inflated_buf = new byte[4096];
            this.type = 0;
        }
    }

    @Override // com.jcraft.jsch.Compression
    public byte[] compress(byte[] bArr, int i, int[] iArr) {
        this.stream.next_in = bArr;
        this.stream.next_in_index = i;
        this.stream.avail_in = iArr[0] - i;
        do {
            this.stream.next_out = this.tmpbuf;
            this.stream.next_out_index = 0;
            this.stream.avail_out = 4096;
            int iDeflate = this.stream.deflate(1);
            if (iDeflate == 0) {
                int i2 = 4096 - this.stream.avail_out;
                int i3 = i + i2;
                int i4 = i3 + 52;
                if (bArr.length < i4) {
                    byte[] bArr2 = new byte[i4 * 2];
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    bArr = bArr2;
                }
                System.arraycopy(this.tmpbuf, 0, bArr, i, i2);
                i = i3;
            } else {
                System.err.println("compress: deflate returnd " + iDeflate);
            }
        } while (this.stream.avail_out == 0);
        iArr[0] = i;
        return bArr;
    }

    @Override // com.jcraft.jsch.Compression
    public byte[] uncompress(byte[] bArr, int i, int[] iArr) {
        this.stream.next_in = bArr;
        this.stream.next_in_index = i;
        this.stream.avail_in = iArr[0];
        int i2 = 0;
        while (true) {
            this.stream.next_out = this.tmpbuf;
            this.stream.next_out_index = 0;
            this.stream.avail_out = 4096;
            int iInflate = this.stream.inflate(1);
            if (iInflate == -5) {
                if (i2 > bArr.length - i) {
                    byte[] bArr2 = new byte[i2 + i];
                    System.arraycopy(bArr, 0, bArr2, 0, i);
                    System.arraycopy(this.inflated_buf, 0, bArr2, i, i2);
                    bArr = bArr2;
                } else {
                    System.arraycopy(this.inflated_buf, 0, bArr, i, i2);
                }
                iArr[0] = i2;
                return bArr;
            }
            if (iInflate == 0) {
                int i3 = i2 + 4096;
                if (this.inflated_buf.length < i3 - this.stream.avail_out) {
                    int length = this.inflated_buf.length * 2;
                    if (length < i3 - this.stream.avail_out) {
                        length = i3 - this.stream.avail_out;
                    }
                    byte[] bArr3 = new byte[length];
                    System.arraycopy(this.inflated_buf, 0, bArr3, 0, i2);
                    this.inflated_buf = bArr3;
                }
                System.arraycopy(this.tmpbuf, 0, this.inflated_buf, i2, 4096 - this.stream.avail_out);
                i2 += 4096 - this.stream.avail_out;
                iArr[0] = i2;
            } else {
                System.err.println("uncompress: inflate returnd " + iInflate);
                return null;
            }
        }
    }
}
