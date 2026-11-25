package org.apache.commons.io.input;

/* JADX INFO: loaded from: classes13.dex */
public class InfiniteCircularInputStream extends CircularInputStream {
    public InfiniteCircularInputStream(byte[] bArr) {
        super(bArr, -1L);
    }
}
