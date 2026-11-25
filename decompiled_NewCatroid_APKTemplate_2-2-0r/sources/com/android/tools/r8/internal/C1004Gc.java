package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1004Gc extends SA {
    static final /* synthetic */ boolean b = true;
    private final S10 a;

    public C1004Gc(S10 s10) {
        if (!b && !s10.G()) {
            throw new AssertionError();
        }
        this.a = s10;
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
        if (sa instanceof C1004Gc) {
            if (this.a.x().equals(sa.b().a.x())) {
                return this;
            }
        }
        return C3106u5.c();
    }

    @Override // com.android.tools.r8.internal.SA
    public final C1004Gc b() {
        return this;
    }

    public final S10 c() {
        return this.a;
    }

    public final String toString() {
        return this.a.toString();
    }
}
