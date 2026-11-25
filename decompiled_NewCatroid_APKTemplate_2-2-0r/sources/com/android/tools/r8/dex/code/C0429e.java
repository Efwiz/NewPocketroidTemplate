package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.EnumC2527mI;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.e, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0429e extends I0 {
    C0429e(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "add-double/2addr";
    }

    @Override // com.android.tools.r8.dex.code.I0, com.android.tools.r8.dex.code.B1
    public final /* bridge */ /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "AddDouble2Addr";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 203;
    }

    public C0429e(int i, int i2) {
        super(i, i2);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        EnumC2527mI enumC2527mI = EnumC2527mI.DOUBLE;
        byte b = this.e;
        c2102gl.a(enumC2527mI, b, b, this.f);
    }
}
