package com.android.tools.r8.graph;

import com.android.tools.r8.dex.C0586x;
import com.android.tools.r8.internal.AbstractC2525mG;
import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: renamed from: com.android.tools.r8.graph.z2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0816z2 extends B2.l {
    static final /* synthetic */ boolean d = true;
    private final AbstractC2525mG c;

    public C0816z2(AbstractC0744s2 abstractC0744s2, AbstractC2525mG abstractC2525mG) {
        super(abstractC0744s2);
        this.c = abstractC2525mG;
    }

    @Override // com.android.tools.r8.graph.B2
    public final E2 N0() {
        return E2.k;
    }

    @Override // com.android.tools.r8.graph.B2
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        ((AbstractC0744s2) this.b).a(c0702m, l);
    }

    @Override // com.android.tools.r8.graph.B2
    final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
        boolean z = d;
        if (!z && N0() != E2.k) {
            throw new AssertionError();
        }
        if (!z && b2.N0() != E2.k) {
            throw new AssertionError();
        }
        b2.getClass();
        int iA = aVar.a(1, b2 instanceof C0816z2 ? 1 : 0);
        return iA != 0 ? iA : aVar.a((AbstractC0744s2) this.b, (AbstractC0744s2) b2.t0().b);
    }

    @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
    public final /* bridge */ /* synthetic */ boolean g1() {
        return true;
    }

    @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
    public final /* bridge */ /* synthetic */ void i1() {
    }

    public final AbstractC2525mG j1() {
        return this.c;
    }

    public final I3 k1() {
        return this.b;
    }

    @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
    public final Object s0() {
        return ((AbstractC0744s2) this.b).toString();
    }

    @Override // com.android.tools.r8.graph.B2
    public final C0816z2 t0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
    public final C0767v2 a(C0722p1 c0722p1) {
        return c0722p1.X1;
    }

    @Override // com.android.tools.r8.graph.B2
    public final com.android.tools.r8.internal.B1 a(com.android.tools.r8.internal.C1 c1) {
        AbstractC0744s2 abstractC0744s2 = (AbstractC0744s2) this.b;
        AbstractC2525mG abstractC2525mG = this.c;
        c1.getClass();
        return com.android.tools.r8.internal.C1.a(abstractC0744s2, abstractC2525mG);
    }

    @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
    public final void a(C0586x c0586x, C0645e5 c0645e5) {
        throw new C3405y10("DexItemBasedValueString values should always be rewritten into DexValueString");
    }

    @Override // com.android.tools.r8.graph.B2
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a((AbstractC0744s2) this.b);
    }
}
