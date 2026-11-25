package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0753u;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1601aH extends AbstractC2309jV {
    static final /* synthetic */ boolean c = true;
    private final C0702m b;

    public C1601aH(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        if (!c && !enumC0928Dk.b()) {
            throw new AssertionError();
        }
        this.b = c0702m;
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        return !AbstractC2734ot.b(c0729q2.R1(), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.aH$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "DontInlinePolicy";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.graph.o5 o5Var) {
        if (o5Var.e().C1()) {
            com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
            if (this.b.a(o5Var).h(this.b.Y()) && wA1 != null) {
                if (wA1.z0()) {
                    C0753u c0753uK = wA1.K();
                    C0702m c0702m = this.b;
                    if (c0753uK.a(o5Var, c0702m, c0702m.E(), o5Var) == com.android.tools.r8.ir.optimize.V.c) {
                    }
                } else if (wA1 instanceof com.android.tools.r8.graph.Z) {
                }
            }
            return true;
        }
        return false;
    }
}
