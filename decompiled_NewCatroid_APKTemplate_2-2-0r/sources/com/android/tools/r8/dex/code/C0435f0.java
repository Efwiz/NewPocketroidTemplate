package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C1033Hc;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.WH;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.f0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0435f0 extends V0 {
    C0435f0(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.f());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "const-string/jumbo";
    }

    public C0759u2 N() {
        return this.f;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        return a(RegisterSpec.PREFIX + ((int) this.e) + ", \"" + this.f.toString() + "\"");
    }

    @Override // com.android.tools.r8.dex.code.V0, com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + ((int) this.e) + ", \"" + this.f.toString() + "\"");
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final C0435f0 e() {
        return this;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "ConstStringJumbo";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 27;
    }

    public C0435f0(int i, C0759u2 c0759u2) {
        super(i, c0759u2);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        short s = this.e;
        c2102gl.a(new C1033Hc(c2102gl.a(s, AbstractC3472z00.b((C0702m<?>) c2102gl.o, WH.b()), 2), this.f));
    }
}
