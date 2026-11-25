package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O3 implements I1 {
    private static final O3 a = new O3();
    static final /* synthetic */ boolean b = true;

    private O3() {
    }

    public static O3 a() {
        return a;
    }

    @Override // com.android.tools.r8.internal.I1
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof N3) || abstractC2412ko.w1();
    }

    @Override // com.android.tools.r8.internal.I1
    public final S10 a(AbstractC2412ko abstractC2412ko) {
        if (!b) {
            abstractC2412ko.getClass();
            if (!(abstractC2412ko instanceof N3) && !abstractC2412ko.w1()) {
                throw new AssertionError();
            }
        }
        return (S10) com.android.tools.r8.utils.V1.a(abstractC2412ko.b);
    }
}
