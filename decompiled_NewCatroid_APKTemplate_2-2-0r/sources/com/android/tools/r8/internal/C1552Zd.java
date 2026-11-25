package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Zd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1552Zd implements I1 {
    private static final C1552Zd a = new C1552Zd();
    static final /* synthetic */ boolean b = true;

    private C1552Zd() {
    }

    public static C1552Zd a() {
        return a;
    }

    @Override // com.android.tools.r8.internal.I1
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return abstractC2412ko instanceof N3;
    }

    @Override // com.android.tools.r8.internal.I1
    public final S10 a(AbstractC2412ko abstractC2412ko) {
        if (!b) {
            abstractC2412ko.getClass();
            if (!(abstractC2412ko instanceof N3)) {
                throw new AssertionError();
            }
        }
        return abstractC2412ko.w().N2();
    }
}
