package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.r6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2898r6 implements Iterator {
    private int a = 0;
    final /* synthetic */ C3038t6 b;

    C2898r6(C3038t6 c3038t6) {
        this.b = c3038t6;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a < this.b.size();
    }

    @Override // java.util.Iterator
    public final Object next() {
        C0767v2 c0767v2 = (C0767v2) this.b.a.get(this.a);
        Object obj = this.b.b.get(this.a);
        this.a++;
        return new C2969s6(c0767v2, obj);
    }
}
