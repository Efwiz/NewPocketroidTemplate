package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Zb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1550Zb extends AbstractC2793pc {
    static final /* synthetic */ boolean c = true;
    private AbstractC1154Lf b;

    C1550Zb(B1 b1, AbstractC1154Lf abstractC1154Lf) {
        super(b1);
        this.b = abstractC1154Lf;
    }

    public static AbstractC1860di a(B1 b1, AbstractC1154Lf abstractC1154Lf) {
        return (b1.isUnknown() && abstractC1154Lf.m()) ? C2276j10.e() : new C1550Zb(b1, abstractC1154Lf);
    }

    @Override // com.android.tools.r8.internal.AbstractC1860di
    public final C1550Zb b() {
        return this;
    }

    public final AbstractC1154Lf e() {
        return this.b;
    }

    public final AbstractC1860di a(C0702m c0702m, B1 b1, C1207Nf c1207Nf, com.android.tools.r8.graph.m5 m5Var) {
        if (!c && !m5Var.getType().O0()) {
            throw new AssertionError();
        }
        this.a = this.a.a(b1, c0702m.b(), true, false);
        this.b = F20.a(c0702m, this.b.a(c0702m, c1207Nf), m5Var.getType());
        return (this.a.isUnknown() && this.b.m()) ? C2276j10.e() : this;
    }
}
