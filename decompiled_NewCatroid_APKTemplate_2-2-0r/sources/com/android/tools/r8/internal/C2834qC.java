package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2834qC implements W5 {
    private int a;
    private final int b;
    final /* synthetic */ C2904rC c;

    /* synthetic */ C2834qC(C2904rC c2904rC, int i) {
        this(c2904rC);
    }

    @Override // com.android.tools.r8.internal.W5
    public final byte f() {
        try {
            byte[] bArr = this.c.c;
            int i = this.a;
            this.a = i + 1;
            return bArr[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a < this.b;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return Byte.valueOf(f());
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private C2834qC(C2904rC c2904rC) {
        this.c = c2904rC;
        this.a = 0;
        this.b = c2904rC.size();
    }
}
