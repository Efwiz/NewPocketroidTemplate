package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.EnumC1953et;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class X1 extends R1 {
    X1(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.d());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "invoke-super/range";
    }

    @Override // com.android.tools.r8.dex.code.R1
    public final EnumC1953et N() {
        return EnumC1953et.f;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.g(s());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "InvokeSuperRange";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 117;
    }

    public X1(int i, int i2, C0684j2 c0684j2) {
        super(i, i2, c0684j2);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.a(EnumC1953et.f, s(), (C0737r2) null, this.e, this.f);
    }
}
