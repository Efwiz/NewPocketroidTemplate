package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3749l;
import com.android.tools.r8.shaking.C3804u;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class RW extends AbstractC1182Mh implements InterfaceC1474Wh {
    static final /* synthetic */ boolean j = true;

    public RW(com.android.tools.r8.graph.Y0 y0, S10 s10) {
        super(y0, (S10) null, s10);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        if (j) {
            return 0;
        }
        throw new AssertionError("StaticPut instructions define no values.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final RW G0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 60;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean L1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final InterfaceC1474Wh O() {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1474Wh
    public final void a(S10 s10) {
        a(0, s10);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.c()) {
            return false;
        }
        RW rwG0 = abstractC2412ko.G0();
        return rwG0.getField() == getField() && rwG0.J2() == J2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC1474Wh
    public final boolean c() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        C0767v2 c0767v2 = getField().e;
        if (c0702m.q()) {
            return c0767v2.a(o5Var, c0702m);
        }
        return c0767v2 != o5Var.t();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1474Wh
    public final int f() {
        return 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + "; field: " + getField().r0();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean v2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1182Mh
    public final S10 value() {
        if (j || this.b.size() == 1) {
            return (S10) this.b.get(0);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 s3;
        int iA = c2322jf.a(W0(), value());
        com.android.tools.r8.graph.Y0 field = getField();
        switch (J2().ordinal()) {
            case 0:
                s3 = new com.android.tools.r8.dex.code.S3(iA, field);
                break;
            case 1:
                s3 = new com.android.tools.r8.dex.code.P3(iA, field);
                break;
            case 2:
                s3 = new com.android.tools.r8.dex.code.Q3(iA, field);
                break;
            case 3:
                s3 = new com.android.tools.r8.dex.code.R3(iA, field);
                break;
            case 4:
                s3 = new com.android.tools.r8.dex.code.T3(iA, field);
                break;
            case 5:
            case 6:
                s3 = new com.android.tools.r8.dex.code.O3(iA, field);
                break;
            case 7:
            case 8:
                s3 = new com.android.tools.r8.dex.code.U3(iA, field);
                break;
            default:
                throw new C3405y10("Unexpected type: " + J2());
        }
        c2322jf.a(this, s3);
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
        final AbstractC0735r0 abstractC0735r0C = abstractC0677i3C.c();
        if (!j && abstractC0735r0C == null) {
            throw new AssertionError("NoSuchFieldError (resolution failure) should be caught.");
        }
        if (((Boolean) c0702m.a((Object) Boolean.FALSE, new Function() { // from class: com.android.tools.r8.internal.RW$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return RW.a(abstractC0735r0C, (C0831Aj) obj);
            }
        })).booleanValue() || abstractC0735r0C.getType().b(c0702mI0)) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(AbstractC0735r0 abstractC0735r0, C0831Aj c0831Aj) {
        return Boolean.valueOf(c0831Aj.a(abstractC0735r0.getReference()));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(AbstractC2412ko abstractC2412ko, com.android.tools.r8.ir.regalloc.f fVar, CE ce) {
        if (!super.a(abstractC2412ko, fVar, ce)) {
            return false;
        }
        fVar.b().getClass();
        return !value().w().r() || value() == abstractC2412ko.G0().value();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.e(getField(), o5Var);
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
        l6.a(new P8(field, field2), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        return T9.a(this, c0767v2, c0702m, u9, s9);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.g(getField());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
    }
}
