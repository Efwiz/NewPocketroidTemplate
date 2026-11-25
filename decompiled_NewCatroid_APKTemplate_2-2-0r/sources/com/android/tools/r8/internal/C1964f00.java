package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.f00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1964f00 implements FZ {
    final /* synthetic */ Class a;
    final /* synthetic */ EZ b;

    C1964f00(Class cls, EZ ez) {
        this.a = cls;
        this.b = ez;
    }

    @Override // com.android.tools.r8.internal.FZ
    public final EZ a(C1862dk c1862dk, I00 i00) {
        if (i00.a() == this.a) {
            return this.b;
        }
        return null;
    }

    public final String toString() {
        return "Factory[type=" + this.a.getName() + ",adapter=" + this.b + "]";
    }
}
