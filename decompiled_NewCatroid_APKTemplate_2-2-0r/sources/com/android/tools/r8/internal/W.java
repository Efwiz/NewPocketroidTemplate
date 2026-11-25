package com.android.tools.r8.internal;

import java.util.RandomAccess;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class W extends X implements RandomAccess {
    private final X a;
    private final int b;
    private int c;

    public W(X x, int i, int i2) {
        AbstractC0936Ds.c(x, SchemaSymbols.ATTVAL_LIST);
        this.a = x;
        this.b = i;
        T.a(i, i2, x.size());
        this.c = i2 - i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2578n
    public final int a() {
        return this.c;
    }

    @Override // java.util.List
    public final Object get(int i) {
        int i2 = this.c;
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }
        return this.a.get(this.b + i);
    }
}
