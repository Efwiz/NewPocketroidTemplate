package com.android.tools.r8.dex.code;

import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.EnumC2527mI;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class G2 extends Q0 {
    G2(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "mul-int/lit16";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.h(EnumC2527mI.INT, this.e, this.f, this.g);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "MulIntLit16";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 210;
    }

    public G2(int i, int i2, int i3) {
        super(i, i2, i3);
    }
}
