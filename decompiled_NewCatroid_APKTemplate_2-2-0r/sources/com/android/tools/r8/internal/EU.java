package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class EU extends S {
    final Iterator c;
    final /* synthetic */ FU d;

    EU(FU fu) {
        this.d = fu;
        this.c = fu.a.iterator();
    }

    @Override // com.android.tools.r8.internal.S
    protected final Object a() {
        while (this.c.hasNext()) {
            Object next = this.c.next();
            if (this.d.b.contains(next)) {
                return next;
            }
        }
        b();
        return null;
    }
}
