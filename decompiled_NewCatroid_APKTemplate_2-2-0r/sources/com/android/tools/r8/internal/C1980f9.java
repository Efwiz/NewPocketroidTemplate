package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.f9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1980f9 extends Reader {
    private CharSequence a;
    private int b;
    private int c;

    public C1980f9(CharSequence charSequence) {
        this.a = (CharSequence) AbstractC3123uM.a(charSequence);
    }

    private void c() throws IOException {
        if (this.a == null) {
            throw new IOException("reader closed");
        }
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public final synchronized void close() {
        this.a = null;
    }

    @Override // java.io.Reader
    public final synchronized void mark(int i) {
        boolean z = i >= 0;
        int i2 = AbstractC3123uM.a;
        if (!z) {
            throw new IllegalArgumentException(AbstractC2619nY.a("readAheadLimit (%s) may not be negative", Integer.valueOf(i)));
        }
        c();
        this.c = this.b;
    }

    @Override // java.io.Reader
    public final boolean markSupported() {
        return true;
    }

    @Override // java.io.Reader, java.lang.Readable
    public final synchronized int read(CharBuffer charBuffer) {
        int i = AbstractC3123uM.a;
        charBuffer.getClass();
        c();
        if (this.a.length() - this.b <= 0) {
            return -1;
        }
        int iMin = Math.min(charBuffer.remaining(), this.a.length() - this.b);
        for (int i2 = 0; i2 < iMin; i2++) {
            CharSequence charSequence = this.a;
            int i3 = this.b;
            this.b = i3 + 1;
            charBuffer.put(charSequence.charAt(i3));
        }
        return iMin;
    }

    @Override // java.io.Reader
    public final synchronized boolean ready() {
        c();
        return true;
    }

    @Override // java.io.Reader
    public final synchronized void reset() {
        c();
        this.b = this.c;
    }

    @Override // java.io.Reader
    public final synchronized long skip(long j) {
        int iMin;
        boolean z = j >= 0;
        int i = AbstractC3123uM.a;
        if (!z) {
            throw new IllegalArgumentException(AbstractC2619nY.a("n (%s) may not be negative", Long.valueOf(j)));
        }
        c();
        iMin = (int) Math.min(this.a.length() - this.b, j);
        this.b += iMin;
        return iMin;
    }

    @Override // java.io.Reader
    public final synchronized int read() {
        int iCharAt;
        c();
        int length = this.a.length();
        int i = this.b;
        if (length - i > 0) {
            CharSequence charSequence = this.a;
            this.b = i + 1;
            iCharAt = charSequence.charAt(i);
        } else {
            iCharAt = -1;
        }
        return iCharAt;
    }

    @Override // java.io.Reader
    public final synchronized int read(char[] cArr, int i, int i2) {
        AbstractC3123uM.a(i, i + i2, cArr.length);
        c();
        if (this.a.length() - this.b <= 0) {
            return -1;
        }
        int iMin = Math.min(i2, this.a.length() - this.b);
        for (int i3 = 0; i3 < iMin; i3++) {
            CharSequence charSequence = this.a;
            int i4 = this.b;
            this.b = i4 + 1;
            cArr[i + i3] = charSequence.charAt(i4);
        }
        return iMin;
    }
}
