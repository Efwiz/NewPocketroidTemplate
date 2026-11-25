package com.huawei.hms.mlsdk.t;

import android.media.MediaDataSource;
import java.io.IOException;

/* JADX INFO: compiled from: MP3Segment.java */
/* JADX INFO: loaded from: classes10.dex */
public class b0 extends MediaDataSource {
    private y a;

    public b0(y yVar) {
        this.a = yVar;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        i0.c("MP3Segment", "close");
    }

    @Override // android.media.MediaDataSource
    public long getSize() throws IOException {
        i0.a("MP3Segment", "getSize");
        return this.a.e().length;
    }

    @Override // android.media.MediaDataSource
    public int readAt(long j, byte[] bArr, int i, int i2) throws IOException {
        if (j >= this.a.e().length) {
            return -1;
        }
        int i3 = (int) j;
        int i4 = i3 + i2;
        if (i4 > this.a.e().length) {
            i2 -= i4 - this.a.e().length;
        }
        System.arraycopy(this.a.e(), i3, bArr, i, i2);
        return i2;
    }
}
