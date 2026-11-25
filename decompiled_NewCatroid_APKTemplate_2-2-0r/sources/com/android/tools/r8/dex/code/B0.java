package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.EnumC2527mI;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class B0 extends I0 {
    B0(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "float-to-double";
    }

    @Override // com.android.tools.r8.dex.code.I0, com.android.tools.r8.dex.code.B1
    public final /* bridge */ /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "FloatToDouble";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 137;
    }

    public B0(int i, int i2) {
        super(i, i2);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.a(EnumC2527mI.DOUBLE, EnumC2527mI.FLOAT, this.e, this.f);
    }
}
