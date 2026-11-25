package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.InterfaceC2541mV;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class Y extends G0 implements InterfaceC2541mV {
    Y(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "const/4";
    }

    @Override // com.android.tools.r8.dex.code.G0, com.android.tools.r8.dex.code.B1
    public final /* bridge */ /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.dex.code.G0, com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        byte b = this.e;
        return b(RegisterSpec.PREFIX + ((int) b) + ", " + com.android.tools.r8.utils.Y2.a(a(), 1) + " (" + a() + Constant.AFTER_QUTO);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "Const4";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 18;
    }

    public Y(int i, int i2) {
        super(i, i2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2541mV
    public final int a() {
        return this.f;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        byte b = this.e;
        return a(RegisterSpec.PREFIX + ((int) b) + ", " + com.android.tools.r8.utils.Y2.a(a(), 2) + "  # " + a());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        int iA = a();
        c2102gl.a(iA == 0 ? AbstractC3472z00.p() : AbstractC3472z00.o(), this.e, iA);
    }
}
