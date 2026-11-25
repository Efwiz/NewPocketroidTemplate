package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.OU;
import java.util.IdentityHashMap;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class q0 implements L {
    static final /* synthetic */ boolean p = true;
    private final Set a = OU.c();
    private final Set b = OU.c();
    private final Set c = OU.c();
    private final Set d = OU.c();
    private final Set e = OU.c();
    private final Set f = OU.c();
    private final Set g = OU.c();
    private final Set h = OU.c();
    public final IdentityHashMap i = new IdentityHashMap();
    public final IdentityHashMap j = new IdentityHashMap();
    public final IdentityHashMap k = new IdentityHashMap();
    public final IdentityHashMap l = new IdentityHashMap();
    public final IdentityHashMap m = new IdentityHashMap();
    public final IdentityHashMap n = new IdentityHashMap();
    public final IdentityHashMap o = new IdentityHashMap();

    public q0(C0702m c0702m) {
        c0702m.E();
    }

    public final boolean a(C0729q2 c0729q2) {
        return this.a.add(c0729q2);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(Y0 y0) {
        return this.e.add(y0);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0684j2 c0684j2) {
        return this.d.add(c0684j2);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0759u2 c0759u2) {
        return this.f.add(c0759u2);
    }

    public final void a(Set set) {
        this.f.addAll(set);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0737r2 c0737r2) {
        return this.b.add(c0737r2);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0767v2 c0767v2) {
        if (!p) {
            int i = com.android.tools.r8.synthesis.L.W;
            com.android.tools.r8.synthesis.L.a(c0767v2.b1());
        }
        return this.c.add(c0767v2);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0721p0 c0721p0) {
        return this.g.add(c0721p0);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0699l2 c0699l2) {
        return this.h.add(c0699l2);
    }

    final Set a() {
        return this.f;
    }
}
