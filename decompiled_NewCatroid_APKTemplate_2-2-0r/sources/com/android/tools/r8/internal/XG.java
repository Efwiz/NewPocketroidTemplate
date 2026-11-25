package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class XG extends AbstractC2309jV {
    static final /* synthetic */ boolean e = true;
    private final com.android.tools.r8.utils.A1 b;
    private final com.android.tools.r8.shaking.g4 c;
    private final com.android.tools.r8.synthesis.D d;

    public XG(C0702m c0702m, EnumC0928Dk enumC0928Dk, com.android.tools.r8.shaking.g4 g4Var) {
        if (!e && g4Var == null && !enumC0928Dk.a()) {
            throw new AssertionError();
        }
        this.b = c0702m.Y();
        this.c = g4Var;
        this.d = c0702m.D();
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        com.android.tools.r8.shaking.g4 g4Var = this.c;
        if (g4Var != null) {
            return !g4Var.a(c0729q2);
        }
        if (e || this.d.b(c0729q2)) {
            return true;
        }
        throw new AssertionError("Expected synthetic, got: " + c0729q2.o1());
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoDirectRuntimeTypeChecks";
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean j() {
        this.b.O0().getClass();
        return false;
    }
}
