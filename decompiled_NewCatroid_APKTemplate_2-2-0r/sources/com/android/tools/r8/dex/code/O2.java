package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class O2 extends P0 {
    O2(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.g());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "new-array";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        C0767v2 c0767v2 = (C0767v2) this.g;
        abstractC1476WjE.getClass();
        C0767v2 c0767v2A = abstractC1476WjE.a(AbstractC1476Wj.e(), c0767v2);
        c0767v2A.getClass();
        if (l.a(c0767v2A)) {
            C0759u2 c0759u2C = c0702m.z().c(c0767v2A);
            c0759u2C.getClass();
            l.a(c0759u2C);
        }
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "NewArray";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 35;
    }

    public O2(int i, int i2, C0767v2 c0767v2) {
        super(i, i2, c0767v2);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.f((C0767v2) this.g);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, (C0767v2) this.g);
        B1.a(this.f, this.e, shortBuffer, v());
        B1.a(c0767v2A, shortBuffer, c0645e5);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.c(this.e, this.f, (C0767v2) this.g);
    }
}
