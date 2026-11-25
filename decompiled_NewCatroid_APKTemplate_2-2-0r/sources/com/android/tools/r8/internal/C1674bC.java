package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1674bC extends AbstractC1748cC {
    static final /* synthetic */ boolean b = true;

    private C1674bC() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1748cC
    public final int a(int i, Object obj) {
        Integer num = (Integer) obj;
        if (b || num != null) {
            return i - num.intValue();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC1748cC
    public final Object a(int i, int i2) {
        return Integer.valueOf(i2 - i);
    }
}
