package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.InterfaceC2541mV;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.nio.ShortBuffer;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.b0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0415b0 extends L0 implements InterfaceC2541mV {
    C0415b0(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "const/high16";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final /* bridge */ /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        short s = this.e;
        return b(RegisterSpec.PREFIX + ((int) s) + ", " + com.android.tools.r8.utils.Y2.a(a(), 8) + " (" + a() + Constant.AFTER_QUTO);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "ConstHigh16";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 21;
    }

    public C0415b0(int i, int i2) {
        super(i, i2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2541mV
    public final int a() {
        return this.f << 16;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(this.e, shortBuffer, v());
        shortBuffer.put((short) this.f);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        short s = this.e;
        return a(RegisterSpec.PREFIX + ((int) s) + ", " + com.android.tools.r8.utils.Y2.a(a(), 8) + "  # " + a());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        int iA = a();
        c2102gl.a(iA == 0 ? AbstractC3472z00.p() : AbstractC3472z00.o(), this.e, iA);
    }
}
