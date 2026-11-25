package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.EnumC2527mI;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.e3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0433e3 extends T0 {
    C0433e3(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "rem-double";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final /* bridge */ /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "RemDouble";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 175;
    }

    public C0433e3(int i, int i2, int i3) {
        super(i, i2, i3);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.k(EnumC2527mI.DOUBLE, this.e, this.f, this.g);
    }
}
