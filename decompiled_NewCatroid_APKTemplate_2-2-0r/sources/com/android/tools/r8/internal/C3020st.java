package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.st, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3020st extends S {
    final /* synthetic */ Iterator c;
    final /* synthetic */ InterfaceC3205vM d;

    C3020st(Iterator it, InterfaceC3205vM interfaceC3205vM) {
        this.c = it;
        this.d = interfaceC3205vM;
    }

    @Override // com.android.tools.r8.internal.S
    protected final Object a() {
        while (this.c.hasNext()) {
            Object next = this.c.next();
            if (this.d.apply(next)) {
                return next;
            }
        }
        b();
        return null;
    }
}
