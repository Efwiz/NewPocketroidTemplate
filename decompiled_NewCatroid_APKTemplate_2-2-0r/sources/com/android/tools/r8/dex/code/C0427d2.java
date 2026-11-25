package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.internal.C2102gl;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.d2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0427d2 extends AbstractC0520w1 {
    C0427d2(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.b());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "iput-char";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.c(getField());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "IputChar";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 94;
    }

    public C0427d2(int i, int i2, com.android.tools.r8.graph.Y0 y0) {
        super(i, i2, y0);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.b(this.e, this.f, getField());
    }
}
