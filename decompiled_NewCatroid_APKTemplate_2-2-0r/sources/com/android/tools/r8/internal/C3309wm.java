package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3309wm extends AbstractC2963s10 {
    int a;
    Object b;
    final /* synthetic */ Iterator c;

    C3309wm(AbstractC2963s10 abstractC2963s10) {
        this.c = abstractC2963s10;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a > 0 || this.c.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.a <= 0) {
            ZF zf = (ZF) this.c.next();
            this.b = zf.b();
            this.a = zf.a();
        }
        this.a--;
        return this.b;
    }
}
