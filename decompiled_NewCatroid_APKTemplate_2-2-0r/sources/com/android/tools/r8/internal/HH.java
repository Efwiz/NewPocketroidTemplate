package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class HH extends AbstractC3053tL {
    static final /* synthetic */ boolean a = true;

    public HH(boolean z) {
        if (!a && z) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3053tL
    public final boolean a() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC3053tL
    public final boolean a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.ir.optimize.info.b bVar, int i, C0767v2 c0767v2) {
        if (c0767v2.X0()) {
            return (bVar.a(i).isUnknown() && ((i == 0 && o5Var.e().B1()) || bVar.b(i).f().g())) ? false : true;
        }
        if (a || c0767v2.W0()) {
            return bVar.a(i).y();
        }
        throw new AssertionError();
    }
}
