package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3385xl extends AbstractC3082tl {
    public C3385xl(C0702m c0702m, com.android.tools.r8.ir.optimize.M m) {
        super(c0702m, m);
    }

    @Override // com.android.tools.r8.internal.AbstractC3082tl
    public final com.android.tools.r8.graph.W a(C2409kl c2409kl, C2133h6 c2133h6, com.android.tools.r8.utils.y3 y3Var) {
        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        y3Var.a("Finalize CF code");
        C0753u c0753uA = new L6(this.a, o5VarH, c2409kl, c2133h6).a(this.b, y3Var);
        y3Var.c();
        return c0753uA;
    }
}
