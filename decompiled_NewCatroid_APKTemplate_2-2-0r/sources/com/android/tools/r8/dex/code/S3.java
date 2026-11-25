package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.internal.C2102gl;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class S3 extends AbstractC0540z3 {
    S3(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.b());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "sput-object";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.g(getField());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "SputObject";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 105;
    }

    public S3(int i, com.android.tools.r8.graph.Y0 y0) {
        super(i, y0);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.b(this.e, getField());
    }
}
