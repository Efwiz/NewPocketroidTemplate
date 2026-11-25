package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vi, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3228vi extends AbstractC3305wi {
    private final InterfaceC2880qs[] c;

    public C3228vi(int i, InterfaceC2880qs[] interfaceC2880qsArr) {
        super(i, a(interfaceC2880qsArr), 0);
        this.c = interfaceC2880qsArr;
    }

    private static int a(Object[] objArr) {
        int length = objArr.length - 1;
        if (length == 0) {
            return 1;
        }
        for (int i = 31; i >= 0; i--) {
            if (((1 << i) & length) != 0) {
                return i + 1;
            }
        }
        throw new IllegalStateException("Empty enum: " + objArr.getClass());
    }

    @Override // com.android.tools.r8.internal.AbstractC3305wi
    public final Object a(int i) {
        int i2 = (1 << this.b) - 1;
        int i3 = this.a;
        int i4 = (i & (i2 << i3)) >> i3;
        for (InterfaceC2880qs interfaceC2880qs : this.c) {
            if (interfaceC2880qs.a() == i4) {
                return interfaceC2880qs;
            }
        }
        return null;
    }
}
