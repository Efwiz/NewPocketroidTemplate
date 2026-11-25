package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.do, reason: invalid class name */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Cdo {
    protected S10 a;
    protected AbstractC1915eM b;

    public abstract Cdo a();

    final AbstractC2412ko a(AbstractC2412ko abstractC2412ko) {
        AbstractC1915eM abstractC1915eM = this.b;
        if (abstractC1915eM != null) {
            abstractC2412ko.b(abstractC1915eM);
        }
        return abstractC2412ko;
    }

    public final Cdo b(AbstractC2412ko abstractC2412ko) {
        this.b = abstractC2412ko.Y0();
        return a();
    }

    public final Cdo a(S10 s10) {
        this.a = s10;
        return a();
    }

    public final Cdo a(T10 t10, AbstractC3472z00 abstractC3472z00, com.android.tools.r8.graph.X x) {
        this.a = ((C2409kl) t10).a(x, abstractC3472z00);
        return a();
    }

    public final Cdo a(AbstractC1915eM abstractC1915eM) {
        this.b = abstractC1915eM;
        return a();
    }
}
