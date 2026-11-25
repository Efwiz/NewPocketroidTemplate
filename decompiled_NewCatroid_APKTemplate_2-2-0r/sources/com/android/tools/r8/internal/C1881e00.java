package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.e00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1881e00 implements FZ {
    C1881e00() {
    }

    @Override // com.android.tools.r8.internal.FZ
    public final EZ a(C1862dk c1862dk, I00 i00) {
        Class clsA = i00.a();
        if (!Enum.class.isAssignableFrom(clsA) || clsA == Enum.class) {
            return null;
        }
        if (!clsA.isEnum()) {
            clsA = clsA.getSuperclass();
        }
        return new C2890r00(clsA);
    }
}
