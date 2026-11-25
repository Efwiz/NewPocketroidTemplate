package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.InterfaceC3937n1;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3299wc implements InterfaceC2090ga {
    static final /* synthetic */ boolean b = true;
    private final AbstractC3350xL a;

    public C3299wc(AbstractC3350xL abstractC3350xL) {
        if (!b && abstractC3350xL.g()) {
            throw new AssertionError();
        }
        this.a = abstractC3350xL;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2090ga
    public final InterfaceC2090ga a(final com.android.tools.r8.graph.proto.c cVar) {
        AbstractC3350xL abstractC3350xL = this.a;
        abstractC3350xL.getClass();
        if (abstractC3350xL instanceof D5) {
            return this;
        }
        final C0905Cp c0905Cp = new C0905Cp(16);
        this.a.e().a(new InterfaceC3937n1() { // from class: com.android.tools.r8.internal.wc$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3937n1
            public final void a(int i, Object obj) {
                C3299wc.a(cVar, c0905Cp, i, (AbstractC3274wL) obj);
            }
        });
        return new C3299wc(BH.a(c0905Cp));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.proto.c cVar, InterfaceC2947rp interfaceC2947rp, int i, AbstractC3274wL abstractC3274wL) {
        com.android.tools.r8.graph.proto.b bVarA = cVar.a(i);
        if (bVarA.c()) {
            return;
        }
        if ((bVarA instanceof com.android.tools.r8.graph.proto.l) && bVarA.b().g().R0()) {
            return;
        }
        interfaceC2947rp.a(cVar.b(i), abstractC3274wL);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2090ga
    public final AbstractC3204vL a(int i) {
        return this.a.a(i).a(U1.a());
    }

    @Override // com.android.tools.r8.internal.InterfaceC2090ga
    public final boolean a(C0702m c0702m, C0729q2 c0729q2, int i) {
        AbstractC0727q0 abstractC0727q0C;
        AbstractC3204vL abstractC3204vLA = this.a.a(i).a(U1.a());
        if (abstractC3204vLA.e()) {
            return true;
        }
        if (abstractC3204vLA.g()) {
            return false;
        }
        for (C0767v2 c0767v2 : abstractC3204vLA.b().l()) {
            if (!c0767v2.O0() || (abstractC0727q0C = c0702m.c(c0767v2)) == null || !((C3733i) c0702m.f()).c(c0729q2, abstractC0727q0C)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2090ga
    public final boolean a(C0702m c0702m, C0729q2 c0729q2, int i, AbstractC3121uK abstractC3121uK, com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0727q0 abstractC0727q0C;
        AbstractC3204vL abstractC3204vLA = this.a.a(i).a(U1.a());
        if (abstractC3204vLA.e()) {
            return true;
        }
        if (abstractC3204vLA.g()) {
            return false;
        }
        C3494zH c3494zHB = abstractC3204vLA.b();
        if (c3494zHB.o() || c3494zHB.p()) {
            return false;
        }
        for (C0767v2 c0767v2 : c3494zHB.l()) {
            if (!c0767v2.O0() || (abstractC0727q0C = c0702m.c(c0767v2)) == null || !((C3733i) c0702m.f()).c(c0729q2, abstractC0727q0C)) {
                return false;
            }
        }
        for (com.android.tools.r8.graph.Y0 y0 : c3494zHB.m()) {
            com.android.tools.r8.graph.T0 t0C = y0.c(c0702m.c(y0.B0()));
            if (t0C == null) {
                return false;
            }
            B1 b1A = abstractC3121uK.a(t0C);
            if (!b1A.A() || !b1A.n().b(c0702m, o5Var)) {
                return false;
            }
        }
        return true;
    }
}
