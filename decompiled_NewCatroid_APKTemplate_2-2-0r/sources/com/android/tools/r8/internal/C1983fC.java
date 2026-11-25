package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1983fC extends AbstractC1748cC {
    private static final C1983fC b = new C1983fC();

    private C1983fC() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1748cC
    public final int a(int i, Object obj) {
        return ((C2214iC) obj).a;
    }

    @Override // com.android.tools.r8.internal.AbstractC1748cC
    public final Object a(int i, int i2) {
        return new C2214iC(i);
    }
}
