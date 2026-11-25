package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.z0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0537z0 extends Z0 {
    C0537z0(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.g());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "filled-new-array";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        C0767v2 c0767v2 = (C0767v2) this.k;
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
        return "FilledNewArray";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 36;
    }

    public C0537z0(int i, C0767v2 c0767v2, int i2, int i3, int i4, int i5, int i6) {
        super(i, c0767v2, i2, i3, i4, i5, i6);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.a((C0767v2) this.k, this.e, new int[]{this.f, this.g, this.h, this.i, this.j});
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, (C0767v2) this.k);
        B1.a(this.e, this.j, shortBuffer, v());
        B1.a(c0767v2A, shortBuffer, c0645e5);
        shortBuffer.put(B1.d(B1.e(this.i, this.h), B1.e(this.g, this.f)));
    }
}
