package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Bc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0852Bc extends SA {
    private final C0978Fc a;

    public C0852Bc(C0978Fc c0978Fc) {
        this.a = c0978Fc;
    }

    @Override // com.android.tools.r8.internal.SA
    public final C0852Bc a() {
        return this;
    }

    public final C0978Fc c() {
        return this.a;
    }

    public final int d() {
        return this.a.N2();
    }

    public final String toString() {
        return this.a.toString();
    }

    @Override // com.android.tools.r8.internal.SA
    public final SA a(SA sa) {
        sa.getClass();
        if (sa instanceof C2009fZ) {
            return this;
        }
        if (sa instanceof C3106u5) {
            return sa;
        }
        return ((sa instanceof C0852Bc) && this.a.b(sa.a().a)) ? this : C3106u5.c();
    }
}
