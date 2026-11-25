package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2342jx {
    private AbstractC2035fx a;
    private AbstractC3246vx b;

    private C2342jx() {
    }

    public final C2342jx a(AbstractC2035fx abstractC2035fx) {
        this.a = abstractC2035fx;
        return this;
    }

    public final C2342jx a(C3171ux c3171ux) {
        this.b = c3171ux;
        return this;
    }

    public final C2424kx a() {
        return new C2424kx(this.a, this.b);
    }
}
