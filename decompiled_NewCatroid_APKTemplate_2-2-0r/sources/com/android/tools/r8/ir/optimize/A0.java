package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3292wV;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2616nV;
import com.android.tools.r8.internal.C2853qV;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.WH;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class A0 implements z0 {
    static final /* synthetic */ boolean c = true;
    private final AbstractC3292wV a;
    final /* synthetic */ B0 b;

    private A0(B0 b0, AbstractC3292wV abstractC3292wV) {
        this.b = b0;
        if (!c && !abstractC3292wV.b(b0.a.i0(), b0.b)) {
            throw new AssertionError();
        }
        this.a = abstractC3292wV;
    }

    @Override // com.android.tools.r8.ir.optimize.z0
    public final void a(AbstractC2412ko abstractC2412ko, InterfaceC2641no interfaceC2641no) {
        this.b.f.addAll(abstractC2412ko.b().a());
        AbstractC3292wV abstractC3292wV = this.a;
        C0702m c0702mH0 = this.b.a.h0();
        C2409kl c2409kl = this.b.c;
        abstractC3292wV.getClass();
        interfaceC2641no.d(abstractC3292wV.a(c0702mH0, c2409kl.h(), c2409kl.e, abstractC2412ko));
    }

    @Override // com.android.tools.r8.ir.optimize.z0
    public final AbstractC3472z00 a(C0702m c0702m, AbstractC3472z00 abstractC3472z00) {
        C0722p1 c0722p1A = c0702m.a();
        if (!this.a.C()) {
            AbstractC3292wV abstractC3292wV = this.a;
            abstractC3292wV.getClass();
            if (!(abstractC3292wV instanceof C2616nV)) {
                if (this.a.B()) {
                    return AbstractC3472z00.a(this.a.p().F().getType(), WH.i(), (C0702m<?>) c0702m);
                }
                boolean z = c;
                if (!z) {
                    AbstractC3292wV abstractC3292wV2 = this.a;
                    abstractC3292wV2.getClass();
                    if (!(abstractC3292wV2 instanceof C2853qV)) {
                        throw new AssertionError();
                    }
                }
                if (abstractC3472z00.I()) {
                    if (!z && !this.a.x()) {
                        throw new AssertionError();
                    }
                    return AbstractC3472z00.m();
                }
                if (z || abstractC3472z00.H()) {
                    return abstractC3472z00;
                }
                throw new AssertionError();
            }
        }
        C0767v2 c0767v2 = c0722p1A.X1;
        C0702m c0702m2 = this.b.a;
        WH whB = WH.b();
        c0767v2.getClass();
        return AbstractC3472z00.a(c0767v2, whB, (C0702m<?>) c0702m2);
    }
}
