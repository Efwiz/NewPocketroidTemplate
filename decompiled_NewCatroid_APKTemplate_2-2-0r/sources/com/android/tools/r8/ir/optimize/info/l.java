package com.android.tools.r8.ir.optimize.info;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.graph.w5;
import com.android.tools.r8.internal.AbstractC1008Gg;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC1380Tn;
import com.android.tools.r8.internal.AbstractC1428Un;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1617aV;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.B1;
import com.android.tools.r8.internal.C1166Ls;
import com.android.tools.r8.internal.C1628ag;
import com.android.tools.r8.internal.C2892r10;
import com.android.tools.r8.internal.C3493zG;
import com.android.tools.r8.internal.DH;
import com.android.tools.r8.internal.E9;
import com.android.tools.r8.internal.InterfaceC2090ga;
import com.android.tools.r8.internal.M5;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.I;
import com.android.tools.r8.utils.M;
import java.util.BitSet;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class l extends h {
    private static final int s;
    static final /* synthetic */ boolean t = true;
    private a b;
    private Set c;
    private int d;
    private B1 e;
    private InterfaceC2090ga f;
    private boolean g;
    private AbstractC1008Gg h;
    private AbstractC1154Lf i;
    private int j;
    private AbstractC3938n2 k;
    private M5 l;
    private AbstractC1428Un m;
    private BitSet n;
    private BitSet o;
    private AbstractC1617aV p;
    private BitSet q;
    private int r;

    l() {
        this.b = a.c();
        this.c = d.c;
        this.d = -1;
        this.e = d.d;
        this.f = InterfaceC2090ga.a();
        this.g = false;
        this.h = AbstractC1008Gg.b();
        this.i = AbstractC1154Lf.n();
        this.j = 3;
        this.k = AbstractC3938n2.g();
        this.l = null;
        this.m = AbstractC1428Un.a();
        this.n = null;
        this.o = null;
        this.p = C3493zG.b;
        this.q = null;
        this.r = s;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean A() {
        return b(8);
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean B() {
        return b(32);
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean C() {
        return b(256);
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean D() {
        return b(16);
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean E() {
        return this.d != -1;
    }

    public final void F() {
        d(256);
    }

    public final void G() {
        d(1);
    }

    final void H() {
        d(2);
    }

    public void I() {
        int i;
        if (!t && (i = this.j) != 3 && i != 2) {
            throw new AssertionError();
        }
        this.j = 2;
    }

    final void J() {
        d(4);
    }

    final void K() {
        a(8);
    }

    final void L() {
        d(32);
    }

    final void M() {
        d(16);
    }

    public final l N() {
        return new l(this);
    }

    final void O() {
        this.g = true;
    }

    final void P() {
        d(128);
    }

    final void Q() {
        int i = this.j;
        if (i == 3) {
            this.j = 1;
        } else if (!t && i != 2) {
            throw new AssertionError();
        }
    }

    final void R() {
        this.e = C2892r10.F();
    }

    final void S() {
        this.l = null;
    }

    final void T() {
        a(2);
    }

    final void U() {
        this.f = InterfaceC2090ga.a();
    }

    final void V() {
        this.i = AbstractC1154Lf.n();
    }

    public final void W() {
        this.h = AbstractC1008Gg.b();
    }

    final void X() {
        this.j = 3;
    }

    final void Y() {
        this.c = Collections.emptySet();
    }

    final void Z() {
        a(128);
    }

    @Override // com.android.tools.r8.ir.optimize.info.g
    public final l a() {
        return this;
    }

    final void a0() {
        a(4);
    }

    @Override // com.android.tools.r8.ir.optimize.info.g
    public final g b() {
        return this;
    }

    final void b0() {
        this.m = C1628ag.d();
    }

    final void c(BitSet bitSet) {
        this.n = bitSet;
    }

    final void c0() {
        d(8);
    }

    @Override // com.android.tools.r8.ir.optimize.info.g
    public final boolean d() {
        return true;
    }

    final void d0() {
        a(32);
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean e() {
        return b(1);
    }

    final void e0() {
        this.o = null;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean f() {
        return b(2);
    }

    final void f0() {
        this.n = null;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean g() {
        return this.j == 2;
    }

    final void g0() {
        a(16);
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final B1 h() {
        return this.e;
    }

    final void h0() {
        this.d = -1;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final a i() {
        return this.b;
    }

    final void i0() {
        this.p = C3493zG.b;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final M5 j() {
        return this.l;
    }

    final void j0() {
        this.q = null;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final InterfaceC2090ga k() {
        return this.f;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final AbstractC1380Tn l() {
        return this.m.c();
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final AbstractC1154Lf m() {
        return this.i;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final AbstractC1008Gg n() {
        return this.h;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final Set o() {
        return this.c;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final BitSet p() {
        return this.o;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final BitSet q() {
        return this.n;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final int r() {
        if (t || E()) {
            return this.d;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final AbstractC1617aV s() {
        return this.p;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final BitSet t() {
        return this.q;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean u() {
        return b(4);
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean w() {
        return this.g;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean x() {
        return b(128);
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean y() {
        return this.j == 1;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final AbstractC3938n2 z() {
        return this.k;
    }

    private boolean b(int i) {
        return (i & this.r) != 0;
    }

    private void d(int i) {
        this.r = i | this.r;
    }

    public final l a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, Set set) {
        this.i = this.i.a(c0702m, abstractC1476Wj, set);
        return this;
    }

    final void c(int i) {
        int i2;
        boolean z = t;
        if (!z && i < 0) {
            throw new AssertionError();
        }
        if (!z && (i2 = this.d) != -1 && i2 != i) {
            throw new AssertionError();
        }
        this.d = i;
    }

    public final l a(C0702m c0702m, DH dh, AbstractC1476Wj abstractC1476Wj) {
        this.e = this.e.b(c0702m, dh, abstractC1476Wj);
        return this;
    }

    final void b(BitSet bitSet) {
        this.o = bitSet;
    }

    final void d(BitSet bitSet) {
        boolean z = t;
        if (!z) {
            v();
        }
        if (!z && v()) {
            I.a(this.q, bitSet);
        }
        if (bitSet.isEmpty()) {
            bitSet = null;
        }
        this.q = bitSet;
    }

    private void b(C0702m c0702m, W0 w0, AbstractC1154Lf abstractC1154Lf) {
        if (c0702m.q()) {
            AbstractC3472z00 abstractC3472z00C = w0.n1().c((C0702m<?>) c0702m);
            AbstractC3472z00 abstractC3472z00A = this.i.a(abstractC3472z00C);
            AbstractC3472z00 abstractC3472z00A2 = abstractC1154Lf.a(abstractC3472z00C);
            if (!t && !abstractC3472z00A2.b(abstractC3472z00A, (C0702m<?>) c0702m)) {
                throw new AssertionError("upper bound type changed from " + abstractC3472z00A + " to " + abstractC3472z00A2);
            }
        }
    }

    public final void a(C0702m c0702m, DH dh, AbstractC1476Wj abstractC1476Wj, u5 u5Var) {
        this.m = this.m.a(c0702m, dh, abstractC1476Wj, u5Var);
    }

    private void a(int i) {
        this.r = (~i) & this.r;
    }

    public final void a(C0702m c0702m, w5 w5Var) {
        a aVar = this.b;
        aVar.getClass();
        if (aVar instanceof b) {
            this.b = w5Var.a(this.b.a());
        }
        M5 m5 = this.l;
        if (m5 != null) {
            this.l = w5Var.a(m5);
        }
        this.f = w5Var.a(c0702m, this.f);
        this.h = w5Var.a(this.h);
        this.m = w5Var.a(c0702m, this.m);
        this.o = w5Var.b(this.o);
        this.n = w5Var.c(this.n);
        this.d = w5Var.a(this.d);
        this.p = w5Var.a(c0702m, this.p, c0702m.d0());
        a(w5Var.d(this.q));
    }

    final void a(a aVar) {
        this.b = aVar;
    }

    final void a(InterfaceC2090ga interfaceC2090ga) {
        this.f = interfaceC2090ga;
    }

    public final l a(AbstractC1008Gg abstractC1008Gg) {
        if (!t) {
            AbstractC1008Gg abstractC1008Gg2 = this.h;
            abstractC1008Gg2.getClass();
            if (abstractC1008Gg2 instanceof E9) {
                abstractC1008Gg.getClass();
                if (!(abstractC1008Gg instanceof E9)) {
                    throw new AssertionError();
                }
            }
        }
        this.h = abstractC1008Gg;
        return this;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final AbstractC1380Tn a(C1166Ls c1166Ls) {
        return this.m.b();
    }

    final void a(M5 m5) {
        this.l = m5;
    }

    public final void a(BitSet bitSet) {
        if (bitSet == null || bitSet.isEmpty()) {
            bitSet = null;
        }
        this.q = bitSet;
    }

    final void a(AbstractC3938n2 abstractC3938n2) {
        this.k = abstractC3938n2;
    }

    @Override // com.android.tools.r8.ir.optimize.info.h
    public final boolean a(AbstractC1305Qs abstractC1305Qs, A1 a1) {
        if (!b(8)) {
            return false;
        }
        if (!t) {
            Objects.requireNonNull(a1);
        }
        return !this.p.a(abstractC1305Qs);
    }

    final void a(AbstractC1617aV abstractC1617aV) {
        this.p = abstractC1617aV;
    }

    final void a(AbstractC1428Un abstractC1428Un) {
        this.m = abstractC1428Un;
    }

    final void a(Set set) {
        this.c = set;
    }

    final void a(B1 b1) {
        if (t || !this.e.g() || this.e.equals(b1)) {
            this.e = b1;
        } else {
            throw new AssertionError("return single value changed from " + this.e + " to " + b1);
        }
    }

    final void a(C0702m c0702m, W0 w0, AbstractC1154Lf abstractC1154Lf) {
        if (!t) {
            b(c0702m, w0, abstractC1154Lf);
        }
        this.i = abstractC1154Lf;
    }

    static {
        d dVar = d.b;
        s = (M.a(false) * 256) | M.a(false) | (M.a(false) * 2) | (M.a(false) * 4) | (M.a(true) * 8) | (M.a(false) * 16) | (M.a(false) * 32) | (M.a(false) * 128);
    }

    private l(l lVar) {
        this.b = a.c();
        this.c = d.c;
        this.d = -1;
        this.e = d.d;
        this.f = InterfaceC2090ga.a();
        this.g = false;
        this.h = AbstractC1008Gg.b();
        this.i = AbstractC1154Lf.n();
        this.j = 3;
        this.k = AbstractC3938n2.g();
        this.l = null;
        this.m = AbstractC1428Un.a();
        this.n = null;
        this.o = null;
        this.p = C3493zG.b;
        this.q = null;
        this.r = s;
        this.b = lVar.b;
        this.r = lVar.r;
        this.c = lVar.c;
        this.d = lVar.d;
        this.e = lVar.e;
        this.i = lVar.i;
        this.j = lVar.j;
        this.p = lVar.p;
        this.l = lVar.l;
        this.m = lVar.m;
        this.n = lVar.n;
        this.o = lVar.o;
        this.f = lVar.f;
        this.h = lVar.h;
    }
}
