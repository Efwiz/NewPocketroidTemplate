package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.EnumC1953et;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class U1 extends Q1 {
    U1(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.d());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "invoke-static";
    }

    @Override // com.android.tools.r8.dex.code.Q1
    public final EnumC1953et N() {
        return EnumC1953et.e;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.e(s());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "InvokeStatic";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 113;
    }

    public U1(int i, C0684j2 c0684j2, int i2, int i3, int i4, int i5, int i6) {
        super(i, c0684j2, i2, i3, i4, i5, i6);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.a(EnumC1953et.e, s(), (C0737r2) null, this.e, new int[]{this.f, this.g, this.h, this.i, this.j});
    }
}
