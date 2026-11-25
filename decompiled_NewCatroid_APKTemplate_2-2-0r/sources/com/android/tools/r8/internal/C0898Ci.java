package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ci, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0898Ci extends AbstractC0984Fi {
    final /* synthetic */ Iterable b;

    C0898Ci(Iterable iterable) {
        this.b = iterable;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        Iterator it = this.b.iterator();
        C2337jt c2337jt = new C2337jt();
        int i = AbstractC3123uM.a;
        return new C3393xt(new C3090tt(it, c2337jt));
    }
}
