package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Collections;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2092gc extends AbstractC2744p implements InterfaceC1547Yy {
    static final /* synthetic */ boolean b = true;
    private final Set a;

    public C2092gc() {
        this.a = OU.c();
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p, com.android.tools.r8.internal.InterfaceC1547Yy
    public final C2092gc a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p
    public final InterfaceC1547Yy b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p
    public final boolean c() {
        return this.a.isEmpty();
    }

    final Set e() {
        return com.android.tools.r8.utils.A1.b() ? Collections.unmodifiableSet(this.a) : this.a;
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == C2092gc.class) {
            return this.a.equals(((C2092gc) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1547Yy
    public final int size() {
        return this.a.size();
    }

    public final C2092gc a(C2092gc c2092gc) {
        this.a.addAll(c2092gc.a);
        return this;
    }

    public final void b(com.android.tools.r8.graph.T0 t0) {
        this.a.add(t0);
    }

    public C2092gc(com.android.tools.r8.graph.T0 t0) {
        this.a = com.android.tools.r8.utils.T2.a(t0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p, com.android.tools.r8.internal.InterfaceC1547Yy
    public final boolean a(com.android.tools.r8.graph.T0 t0) {
        return this.a.contains(t0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p
    public final AbstractC2744p a(C0702m c0702m, com.android.tools.r8.graph.proto.c cVar) {
        if (!b && this.a.isEmpty()) {
            throw new AssertionError();
        }
        if (cVar.g()) {
            return this;
        }
        AbstractC2744p abstractC2744pA = C1501Xf.e().a(c0702m, cVar);
        if (abstractC2744pA.c()) {
            return this;
        }
        C2092gc c2092gcA = abstractC2744pA.a();
        c2092gcA.a.addAll(this.a);
        return c2092gcA;
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p
    public final AbstractC2744p a(C0702m c0702m, DH dh, com.android.tools.r8.graph.u5 u5Var) {
        if (!b && this.a.isEmpty()) {
            throw new AssertionError();
        }
        C2092gc c2092gc = new C2092gc();
        for (com.android.tools.r8.graph.T0 t0 : this.a) {
            if (!u5Var.a(t0.getReference())) {
                com.android.tools.r8.graph.Y0 y0C = dh.c((AbstractC1476Wj) null, t0.getReference());
                c0702m.getClass();
                com.android.tools.r8.graph.T0 t0C = y0C.c(c0702m.c(y0C.e));
                if (t0C == null) {
                    if (!b) {
                        throw new AssertionError();
                    }
                } else {
                    c2092gc.a.add(t0C);
                }
            }
        }
        return c2092gc;
    }
}
