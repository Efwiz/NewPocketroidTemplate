package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xa, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3374xa {
    final C0822Aa a;
    final C3444ya b;
    static final /* synthetic */ boolean d = true;
    static final C3374xa c = new C3374xa(C0822Aa.b, C3444ya.b);

    private C3374xa(C0822Aa c0822Aa, C3444ya c3444ya) {
        this.a = c0822Aa;
        this.b = c3444ya;
    }

    public final boolean a() {
        if (d || !this.b.a.isEmpty() || this.a.a.isEmpty()) {
            return this.b.a.isEmpty();
        }
        throw new AssertionError();
    }
}
