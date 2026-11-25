package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2433l20 {
    static final /* synthetic */ boolean b = true;
    private final C0702m a;

    private C2433l20(C0702m c0702m) {
        this.a = c0702m;
    }

    public static C2433l20 a(C0702m c0702m) {
        return new C2433l20(c0702m);
    }

    public final boolean a(AbstractC3472z00 abstractC3472z00, AbstractC3472z00 abstractC3472z01) {
        if (abstractC3472z00.H() != abstractC3472z01.H()) {
            return false;
        }
        if (abstractC3472z00.H()) {
            if (b || abstractC3472z01.H()) {
                return abstractC3472z00.equals(abstractC3472z01);
            }
            throw new AssertionError();
        }
        boolean z = b;
        if (!z && (!abstractC3472z00.I() || !abstractC3472z01.I())) {
            throw new AssertionError();
        }
        if (abstractC3472z00.E() && abstractC3472z01.I()) {
            return true;
        }
        if (abstractC3472z00.r() != abstractC3472z01.r()) {
            return abstractC3472z00.r() && abstractC3472z01.b().R() == this.a.a().Z1;
        }
        if (abstractC3472z00.r()) {
            if (z || abstractC3472z01.r()) {
                return a(abstractC3472z00.a().S(), abstractC3472z01.a().S());
            }
            throw new AssertionError();
        }
        if (!z && (!abstractC3472z00.w() || !abstractC3472z01.w())) {
            throw new AssertionError();
        }
        if (this.a.q()) {
            return abstractC3472z00.a(abstractC3472z01, this.a) || abstractC3472z00.a(this.a.h0());
        }
        return abstractC3472z00.b().R() != this.a.a().Z1 || abstractC3472z01.b().R() == this.a.a().Z1;
    }
}
