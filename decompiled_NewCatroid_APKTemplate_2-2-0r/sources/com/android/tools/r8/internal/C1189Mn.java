package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0495r1;
import com.android.tools.r8.dex.code.C0500s1;
import com.android.tools.r8.dex.code.C0505t1;
import com.android.tools.r8.dex.code.C0510u1;
import com.android.tools.r8.dex.code.C0515v1;
import com.android.tools.r8.dex.code.C0525x1;
import com.android.tools.r8.dex.code.C0533y1;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3804u;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1189Mn extends AbstractC1182Mh implements InterfaceC1102Jh, InterfaceC1108Jn {
    static final /* synthetic */ boolean j = true;

    public C1189Mn(com.android.tools.r8.graph.Y0 y0, S10 s10, S10 s11) {
        super(y0, s10, s11);
    }

    public static C1189Mn a(C2409kl c2409kl, C1189Mn c1189Mn) {
        S10 s10 = new S10(c2409kl.e.a(), c1189Mn.a(), c1189Mn.j());
        if (j || s10 != c1189Mn.b()) {
            return new C1189Mn(c1189Mn.getField(), s10, c1189Mn.e());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 15;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 15;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 28;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean I2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final InterfaceC1102Jh M() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final InterfaceC1108Jn S() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1189Mn T() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final S10 V0() {
        return e();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        if (!(abstractC2412ko instanceof C1189Mn)) {
            return false;
        }
        C1189Mn c1189MnT = abstractC2412ko.T();
        return c1189MnT.getField() == getField() && c1189MnT.J2() == J2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1108Jn
    public final S10 e() {
        if (j || this.b.size() == 1) {
            return (S10) this.b.get(0);
        }
        throw new AssertionError();
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
    public final boolean a(Set set) {
        return getField().h.N0();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 c0515v1;
        int iA = c2322jf.a(W0(), this.a);
        int iA2 = c2322jf.a(W0(), e());
        com.android.tools.r8.graph.Y0 field = getField();
        switch (J2().ordinal()) {
            case 0:
                c0515v1 = new C0515v1(iA, iA2, field);
                break;
            case 1:
                c0515v1 = new C0500s1(iA, iA2, field);
                break;
            case 2:
                c0515v1 = new C0505t1(iA, iA2, field);
                break;
            case 3:
                c0515v1 = new C0510u1(iA, iA2, field);
                break;
            case 4:
                c0515v1 = new C0525x1(iA, iA2, field);
                break;
            case 5:
            case 6:
                c0515v1 = new C0495r1(iA, iA2, field);
                break;
            case 7:
            case 8:
                c0515v1 = new C0533y1(iA, iA2, field);
                break;
            default:
                throw new C3405y10("Unexpected type: " + J2());
        }
        c2322jf.a(this, c0515v1);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, S10 s10) {
        boolean z = j;
        if (!z && (s10 == null || !s10.w().I())) {
            throw new AssertionError();
        }
        if (z || this.a != null) {
            return this.a.w().I();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        return b(c0702m, o5Var, c2332jo);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.b(getField(), o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.a(getField().h, WH.i(), (C0702m<?>) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return getField().h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
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
        l6.a(new C3413y7(field, field2), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        return e() == s10;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        return T9.a(this, c0767v2, c0702m, u9, s9);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.a(getField());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(getField(), e());
    }
}
