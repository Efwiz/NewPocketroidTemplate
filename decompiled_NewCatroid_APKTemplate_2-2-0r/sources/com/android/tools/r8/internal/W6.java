package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class W6 extends L7 {
    final EnumC1076Il b;
    final EnumC1812d20 c;
    final M7 d;

    W6(EnumC1076Il enumC1076Il, EnumC1812d20 enumC1812d20, M7 m7) {
        this.b = enumC1076Il;
        this.c = enumC1812d20;
        this.d = m7;
    }

    @Override // com.android.tools.r8.internal.B7
    public final M7 A() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean C() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean N() {
        return true;
    }

    @Override // com.android.tools.r8.internal.L7
    public final boolean T() {
        return true;
    }

    public final EnumC1076Il U() {
        return this.b;
    }

    public final EnumC1812d20 V() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
