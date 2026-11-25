package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.IdentityHashMap;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3286wP {
    static final /* synthetic */ boolean d = true;
    private final VP a;
    private final IdentityHashMap b = new IdentityHashMap();
    private final ConcurrentHashMap c = new ConcurrentHashMap();

    public C3286wP(VP vp) {
        this.a = vp;
    }

    public final void a(C0729q2 c0729q2, OW ow) {
        if (ow == null || !(ow instanceof NW)) {
            return;
        }
        boolean z = d;
        if (!z && !c0729q2.B1()) {
            throw new AssertionError();
        }
        NW nwA = ow.a();
        if (!z && !c0729q2.B1()) {
            throw new AssertionError();
        }
        if (c0729q2.d == this.a.n ? true : c0729q2.f1().a(this.a.c)) {
            this.c.put(c0729q2.d, nwA);
        }
    }

    public final void a() {
        this.b.putAll(this.c);
        this.c.clear();
    }

    public final C2853qV a(C0729q2 c0729q2, com.android.tools.r8.graph.T0 t0, com.android.tools.r8.graph.T0 t1) {
        NW nw;
        AbstractC3121uK abstractC3121uKA;
        if (c0729q2 != null) {
            if (!d && !c0729q2.B1()) {
                throw new AssertionError();
            }
            if (!(c0729q2.d == this.a.n ? true : c0729q2.f1().a(this.a.c)) || (nw = (NW) this.b.get(c0729q2.d)) == null || (abstractC3121uKA = nw.a(t0.getReference())) == null) {
                return null;
            }
            return abstractC3121uKA.a(t1).e();
        }
        return null;
    }
}
