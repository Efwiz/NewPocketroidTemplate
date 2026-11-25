package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.EnumC0691k2;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C1113Js;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.EnumC1968f20;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class K1 extends Z0 {
    K1(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.a());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "invoke-custom";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        C0721p0 c0721p0 = (C0721p0) this.k;
        byte b = this.e;
        int[] iArr = {this.f, this.g, this.h, this.i, this.j};
        c2102gl.getClass();
        C0699l2 c0699l2 = c0721p0.f;
        ArrayList arrayList = new ArrayList(b);
        EnumC0691k2 enumC0691k2 = c0699l2.d;
        enumC0691k2.getClass();
        int iC = 0;
        if (enumC0691k2 != EnumC0691k2.b) {
            EnumC0691k2 enumC0691k3 = c0699l2.d;
            enumC0691k3.getClass();
            if (enumC0691k3 != EnumC0691k2.c && !c0699l2.d.g()) {
                int i = iArr[0];
                EnumC1968f20 enumC1968f20 = EnumC1968f20.OBJECT;
                arrayList.add(c2102gl.b(i, enumC1968f20));
                iC = enumC1968f20.c();
            }
        }
        String string = c0721p0.e.d.toString();
        for (int i2 = 1; i2 < string.length(); i2++) {
            EnumC1968f20 enumC1968f20A = EnumC1968f20.a(string.charAt(i2));
            arrayList.add(c2102gl.b(iArr[iC], enumC1968f20A));
            iC += enumC1968f20A.c();
        }
        c2102gl.a(new C1113Js(c0721p0, null, arrayList));
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final C0721p0 q() {
        return (C0721p0) this.k;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "InvokeCustom";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 252;
    }

    public K1(int i, C0721p0 c0721p0, int i2, int i3, int i4, int i5, int i6) {
        super(i, c0721p0, i2, i3, i4, i5, i6);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        C0721p0 c0721p0A = c2363kB.a((C0721p0) this.k, o5Var);
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
        j5.a((C0721p0) this.k);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(this.e, this.j, shortBuffer, v());
        B1.a(c2363kB.a((C0721p0) this.k, o5Var), shortBuffer, c0645e5);
        shortBuffer.put(B1.d(B1.e(this.i, this.h), B1.e(this.g, this.f)));
    }
}
