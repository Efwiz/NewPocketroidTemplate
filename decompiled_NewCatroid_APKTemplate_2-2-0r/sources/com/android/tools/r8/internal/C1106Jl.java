package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1106Jl implements I1 {
    private static final C1106Jl a = new C1106Jl();
    static final /* synthetic */ boolean b = true;

    private C1106Jl() {
    }

    public static C1106Jl a() {
        return a;
    }

    @Override // com.android.tools.r8.internal.I1
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof N3) || (abstractC2412ko instanceof C1289Qd);
    }

    @Override // com.android.tools.r8.internal.I1
    public final S10 a(AbstractC2412ko abstractC2412ko) {
        if (!b) {
            abstractC2412ko.getClass();
            if (!(abstractC2412ko instanceof N3) && !(abstractC2412ko instanceof C1289Qd)) {
                throw new AssertionError();
            }
        }
        abstractC2412ko.getClass();
        return abstractC2412ko instanceof N3 ? abstractC2412ko.w().N2() : abstractC2412ko.G().K2();
    }
}
