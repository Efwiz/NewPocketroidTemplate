package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.EnumC1968f20;
import java.nio.ShortBuffer;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.n2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0477n2 extends Y0 {
    C0477n2(int i, InterfaceC0409a interfaceC0409a) {
        super(interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "move/16";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final /* bridge */ /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "Move16";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 3;
    }

    public C0477n2(int i, int i2) {
        super(i, i2);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.a(EnumC1968f20.INT_OR_FLOAT, this.e, this.f);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(0, shortBuffer, 3);
        shortBuffer.put((short) this.e);
        shortBuffer.put((short) this.f);
    }
}
