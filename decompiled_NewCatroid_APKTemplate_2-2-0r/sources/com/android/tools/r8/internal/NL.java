package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class NL extends ML {
    static final /* synthetic */ boolean w = true;

    public NL(int i, C3332x4 c3332x4, AbstractC3472z00 abstractC3472z00, com.android.tools.r8.graph.X x, ML.a aVar) {
        super(i, c3332x4, abstractC3472z00, x, aVar);
    }

    @Override // com.android.tools.r8.internal.ML
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        boolean z = w;
        if (!z && c0702m.q()) {
            throw new AssertionError();
        }
        if (this.n.H()) {
            return this.n.c().a(c0702m.a());
        }
        if (this.n.r()) {
            return this.n.a().a(c0702m.a());
        }
        if (z || this.n.w()) {
            return this.n.b().R();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.ML
    public final AbstractC3472z00 c(C0702m c0702m) {
        boolean z = w;
        if (!z && c0702m.q()) {
            throw new AssertionError();
        }
        if (this.n.H()) {
            return this.n;
        }
        if (!z && !this.n.I()) {
            throw new AssertionError();
        }
        WH whA = WH.a();
        Iterator<S10> it = k0().iterator();
        while (it.hasNext()) {
            whA = whA.a(it.next().n.N());
        }
        return this.n.d().a(whA);
    }
}
