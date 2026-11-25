package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.internal.C2102gl;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class M3 extends X0 {
    M3(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "sparse-switch";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean I() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        int iU = u();
        int iX = x() + iU;
        c2102gl.a(this.e, z() + iU, iX);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "SparseSwitch";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 44;
    }

    public M3(int i) {
        super(i);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        return a(RegisterSpec.PREFIX + ((int) this.e) + ", :label_" + (u() + this.f));
    }
}
