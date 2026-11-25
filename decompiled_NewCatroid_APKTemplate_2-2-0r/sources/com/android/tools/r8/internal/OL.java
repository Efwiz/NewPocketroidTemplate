package com.android.tools.r8.internal;

import java.util.function.Function;
import java.util.function.IntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class OL extends SB {
    static final /* synthetic */ boolean b = true;
    private final S10[] a;

    OL(RB rb) {
        this.a = new S10[rb.c() + rb.a()];
    }

    @Override // com.android.tools.r8.internal.SB
    public final S10 a(Object obj, AbstractC2366kC abstractC2366kC) {
        int iIntValue = ((Integer) obj).intValue();
        S10 s10 = this.a[iIntValue];
        if (s10 != null) {
            return s10;
        }
        S10 s11 = new S10(iIntValue, AbstractC3472z00.f(), null);
        this.a[iIntValue] = s11;
        return s11;
    }

    @Override // com.android.tools.r8.internal.SB
    public final S10 a(int i, AbstractC3472z00 abstractC3472z00, Function function) {
        com.android.tools.r8.graph.X x = (com.android.tools.r8.graph.X) function.apply(Integer.valueOf(i));
        S10 s10 = this.a[i];
        if (s10 == null) {
            S10 s11 = new S10(i, abstractC3472z00, x);
            this.a[i] = s11;
            return s11;
        }
        s10.a(abstractC3472z00);
        if (x == null) {
            return s10;
        }
        if (!s10.A()) {
            s10.a(x);
        }
        if (b || x == s10.s()) {
            return s10;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.SB
    public final ML a(int i, IntFunction intFunction, AbstractC3472z00 abstractC3472z00, Function function, AbstractC2366kC abstractC2366kC) {
        ML ml = new ML(i, (C3332x4) intFunction.apply(i), abstractC3472z00, (com.android.tools.r8.graph.X) function.apply(Integer.valueOf(i)), ML.a.a);
        S10 s10 = this.a[i];
        if (s10 != null) {
            if (!b && s10.i()) {
                throw new AssertionError();
            }
            s10.g(ml);
        }
        this.a[i] = ml;
        return ml;
    }
}
