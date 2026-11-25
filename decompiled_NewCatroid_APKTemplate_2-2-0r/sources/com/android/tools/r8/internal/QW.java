package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0512u3;
import com.android.tools.r8.dex.code.C0517v3;
import com.android.tools.r8.dex.code.C0522w3;
import com.android.tools.r8.dex.code.C0527x3;
import com.android.tools.r8.dex.code.C0535y3;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3804u;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class QW extends AbstractC1182Mh implements InterfaceC1102Jh {
    static final /* synthetic */ boolean j = true;
    public static final /* synthetic */ int k = 0;

    public QW(com.android.tools.r8.graph.Y0 y0, S10 s10) {
        super(y0, s10, (S10) null);
    }

    public static QW a(C2409kl c2409kl, QW qw) {
        S10 s10 = new S10(c2409kl.e.a(), qw.a(), qw.j());
        if (j || s10 != qw.b()) {
            return new QW(qw.getField(), s10);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final QW F0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 59;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean J1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final InterfaceC1102Jh M() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1182Mh
    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        if (c0702m.t().a(getField()).f()) {
            return false;
        }
        return super.b(c0702m, o5Var, c2332jo);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return b(c0702m, o5Var, C2332jo.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        C0767v2 c0767v2 = getField().e;
        if (c0702m.q()) {
            return c0767v2.a(o5Var, c0702m);
        }
        return c0767v2 != o5Var.t();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean j1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + "; field: " + getField().r0();
    }

    @Override // com.android.tools.r8.internal.AbstractC1182Mh
    public final S10 value() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 c0535y3;
        int iA = c2322jf.a(W0(), this.a);
        com.android.tools.r8.graph.Y0 field = getField();
        switch (J2().ordinal()) {
            case 0:
                c0535y3 = new C0535y3(iA, field);
                break;
            case 1:
                c0535y3 = new C0517v3(iA, field);
                break;
            case 2:
                c0535y3 = new C0522w3(iA, field);
                break;
            case 3:
                c0535y3 = new C0527x3(iA, field);
                break;
            case 4:
                c0535y3 = new com.android.tools.r8.dex.code.A3(iA, field);
                break;
            case 5:
            case 6:
                c0535y3 = new C0512u3(iA, field);
                break;
            case 7:
            case 8:
                c0535y3 = new com.android.tools.r8.dex.code.B3(iA, field);
                break;
            default:
                throw new C3405y10("Unexpected type: " + J2());
        }
        c2322jf.a(this, c0535y3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        if (!(abstractC2412ko instanceof QW)) {
            return false;
        }
        QW qwF0 = abstractC2412ko.F0();
        return qwF0.getField() == getField() && qwF0.J2() == J2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, S10 s10) {
        boolean z = j;
        if (!z && (s10 == null || !s10.w().I())) {
            throw new AssertionError();
        }
        if (!z && this.a == null) {
            throw new AssertionError();
        }
        AbstractC3472z00 abstractC3472z00W = this.a.w();
        if (abstractC3472z00W.H()) {
            return false;
        }
        if (c0702m.f().i() && abstractC3472z00W.w() && s10.w().w() && c0702m.f().o().a(abstractC3472z00W.b().R(), s10.w().b().R())) {
            return false;
        }
        return abstractC3472z00W.I();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        return b(c0702m, o5Var, c2332jo);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.d(getField(), o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        com.android.tools.r8.graph.Y0 field = getField();
        com.android.tools.r8.graph.Y0 field2 = getField();
        com.android.tools.r8.graph.T0 t0S = l6.a.g().c(field2).s();
        if (t0S != null) {
            field2 = t0S.getReference();
        }
        l6.a(new O8(field, field2), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return getField().h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.a(getField().h, WH.i(), (C0702m<?>) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        return T9.a(this, c0767v2, c0702m, u9, s9);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(Set set) {
        return getField().h.N0();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.e(getField());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(getField());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
    }
}
