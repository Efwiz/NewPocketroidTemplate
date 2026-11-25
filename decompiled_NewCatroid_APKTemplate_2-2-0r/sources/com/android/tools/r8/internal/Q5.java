package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Q5 implements Iterator {
    private final int a;
    private final byte[] b;
    private int c = 0;

    public Q5(byte[] bArr) {
        this.a = bArr.length;
        this.b = bArr;
    }

    public final void a(int i) {
        int i2 = this.c;
        int i3 = i2 + i;
        int i4 = this.a;
        if (i3 > i4) {
            i = i4 - i2;
        }
        this.c = i2 + i;
    }

    public final byte f() {
        byte[] bArr = this.b;
        int i = this.c;
        this.c = i + 1;
        return bArr[i];
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.c < this.a;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return Byte.valueOf(f());
    }
}
