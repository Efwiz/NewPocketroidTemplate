package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.internal.C2102gl;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.u1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0510u1 extends AbstractC0520w1 implements InterfaceC0414b {
    C0510u1(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.b());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "iget-char";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.a(this);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "IgetChar";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 87;
    }

    public C0510u1(int i, int i2, com.android.tools.r8.graph.Y0 y0) {
        super(i, i2, y0);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.a(this.e, this.f, getField());
    }
}
