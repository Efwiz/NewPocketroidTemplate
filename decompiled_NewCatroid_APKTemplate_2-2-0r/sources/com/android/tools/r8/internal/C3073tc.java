package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3073tc extends AbstractC1157Li {
    private ZF a;
    final /* synthetic */ Iterator b;
    final /* synthetic */ C3222vc c;

    C3073tc(C3222vc c3222vc, Iterator it) {
        this.c = c3222vc;
        this.b = it;
    }

    @Override // com.android.tools.r8.internal.AbstractC1183Mi
    protected final Object a() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC1157Li, java.util.Iterator
    public final Object next() {
        ZF zf = (ZF) super.next();
        this.a = zf;
        return zf;
    }

    @Override // java.util.Iterator
    public final void remove() {
        AbstractC3123uM.b("no calls to next() since the last call to remove()", this.a != null);
        this.c.a(this.a.b());
        this.a = null;
    }
}
