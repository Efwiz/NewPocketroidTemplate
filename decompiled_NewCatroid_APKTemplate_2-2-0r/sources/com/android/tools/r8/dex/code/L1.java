package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class L1 extends AbstractC0411a1 {
    L1(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.a());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "invoke-custom/range";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.a(this.e, this.f, (C0721p0) this.g);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final C0721p0 q() {
        return (C0721p0) this.g;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "InvokeCustomRange";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 253;
    }

    public L1(int i, int i2, C0721p0 c0721p0) {
        super(i, i2, c0721p0);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        C0721p0 c0721p0A = c2363kB.a((C0721p0) this.g, o5Var);
        c0721p0A.getClass();
        if (l.a(c0721p0A)) {
            C0759u2 c0759u2 = c0721p0A.d;
            c0759u2.getClass();
            l.a(c0759u2);
            c0721p0A.e.a(c0702m, l);
            c0721p0A.f.a(c0702m, l);
            Iterator it = c0721p0A.g.iterator();
            while (it.hasNext()) {
                ((com.android.tools.r8.graph.B2) it.next()).a(c0702m, l);
            }
        }
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.a((C0721p0) this.g);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        C0721p0 c0721p0A = c2363kB.a((C0721p0) this.g, o5Var);
        B1.a(this.e, shortBuffer, v());
        B1.a(c0721p0A, shortBuffer, c0645e5);
        shortBuffer.put((short) this.f);
    }
}
