package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0412a2;
import com.android.tools.r8.dex.code.C0417b2;
import com.android.tools.r8.dex.code.C0422c2;
import com.android.tools.r8.dex.code.C0427d2;
import com.android.tools.r8.dex.code.C0432e2;
import com.android.tools.r8.dex.code.C0437f2;
import com.android.tools.r8.dex.code.C0442g2;
import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3749l;
import com.android.tools.r8.shaking.C3804u;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.co, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1793co extends AbstractC1182Mh implements InterfaceC1474Wh, InterfaceC1108Jn {
    static final /* synthetic */ boolean j = true;

    public C1793co(com.android.tools.r8.graph.Y0 y0, S10 s10, S10 s11) {
        this(y0, s10, s11, false);
    }

    public static C1793co a(com.android.tools.r8.graph.Y0 y0, S10 s10, S10 s11) {
        return new C1793co(y0, s10, s11, true);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 15;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        if (j) {
            return 0;
        }
        throw new AssertionError("InstancePut instructions define no values.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 30;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean I2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final InterfaceC1474Wh O() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean P1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final InterfaceC1108Jn S() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean S1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1793co V() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final S10 V0() {
        return e();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.S1()) {
            return false;
        }
        C1793co c1793coV = abstractC2412ko.V();
        return c1793coV.getField() == getField() && c1793coV.J2() == J2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1108Jn
    public S10 e() {
        return (S10) this.b.get(0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1474Wh
    public final int f() {
        return 1;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + "; field: " + getField().r0();
    }

    @Override // com.android.tools.r8.internal.AbstractC1182Mh
    public S10 value() {
        return (S10) this.b.get(1);
    }

    private C1793co(com.android.tools.r8.graph.Y0 y0, S10 s10, S10 s11, boolean z) {
        super(y0, (S10) null, Arrays.asList(s10, s11));
        if (z) {
            return;
        }
        boolean z2 = j;
        if (!z2) {
            e().a(EnumC1812d20.OBJECT);
        }
        if (z2) {
            return;
        }
        value().a(EnumC1812d20.a(y0.h));
    }

    @Override // com.android.tools.r8.internal.InterfaceC1474Wh
    public final void a(S10 s10) {
        a(1, s10);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 c0432e2;
        int iA = c2322jf.a(W0(), value());
        int iA2 = c2322jf.a(W0(), e());
        com.android.tools.r8.graph.Y0 field = getField();
        switch (J2().ordinal()) {
            case 0:
                c0432e2 = new C0432e2(iA, iA2, field);
                break;
            case 1:
                c0432e2 = new C0417b2(iA, iA2, field);
                break;
            case 2:
                c0432e2 = new C0422c2(iA, iA2, field);
                break;
            case 3:
                c0432e2 = new C0427d2(iA, iA2, field);
                break;
            case 4:
                c0432e2 = new C0437f2(iA, iA2, field);
                break;
            case 5:
            case 6:
                c0432e2 = new C0412a2(iA, iA2, field);
                break;
            case 7:
            case 8:
                c0432e2 = new C0442g2(iA, iA2, field);
                break;
            default:
                throw new C3405y10("Unexpected type: " + J2());
        }
        c2322jf.a(this, c0432e2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        if (!c0702m.f().i()) {
            return true;
        }
        C0702m<C3733i> c0702mI0 = c0702m.i0();
        C3733i c3733i = (C3733i) c0702mI0.f();
        AbstractC0677i3 abstractC0677i3C = c3733i.c(getField());
        if (a(c0702m, o5Var, c2332jo, abstractC0677i3C)) {
            return true;
        }
        AbstractC0735r0 abstractC0735r0C = abstractC0677i3C.c();
        if (!j && abstractC0735r0C == null) {
            throw new AssertionError("NoSuchFieldError (resolution failure) should be caught.");
        }
        if (abstractC0735r0C.getType().b(c0702mI0)) {
            return false;
        }
        C3749l c3749lT = c0702m.t();
        c3749lT.getClass();
        B1 b1D = c3749lT.a(abstractC0735r0C.getReference()).d();
        if (b1D.g() && b1D.j().a(c0702mI0)) {
            return false;
        }
        return c3733i.a(abstractC0735r0C.e()) || a(c0702mI0, abstractC0735r0C.e());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(AbstractC2412ko abstractC2412ko, com.android.tools.r8.ir.regalloc.f fVar, CE ce) {
        if (!super.a(abstractC2412ko, fVar, ce)) {
            return false;
        }
        fVar.b().getClass();
        return !value().w().r() || value() == abstractC2412ko.V().value();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.c(getField(), o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        com.android.tools.r8.graph.Y0 field = getField();
        com.android.tools.r8.graph.Y0 field2 = getField();
        com.android.tools.r8.graph.T0 t0S = l6.a.g().c(field2).s();
        if (t0S != null) {
            field2 = t0S.getReference();
        }
        l6.a(new C3482z7(field, field2), this);
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
        c3804u.c(getField());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(getField(), e(), value());
    }
}
