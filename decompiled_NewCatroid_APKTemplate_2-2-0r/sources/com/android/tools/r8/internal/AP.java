package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class AP {
    static final /* synthetic */ boolean b = true;
    private C1303Qq a = new C1303Qq();

    public final C3503zP a(int i) {
        C3503zP c3503zP = (C3503zP) this.a.get(i);
        if (c3503zP == null) {
            int i2 = i & 255;
            c3503zP = i2 < 51 ? new C3503zP(i2, com.android.tools.r8.utils.J.b(i, 256), com.android.tools.r8.utils.J.b(i, 512), com.android.tools.r8.utils.J.b(i, 1024), com.android.tools.r8.utils.J.b(i, 2048), com.android.tools.r8.utils.J.b(i, 4096)) : new OP(i2, com.android.tools.r8.utils.J.b(i, 256), com.android.tools.r8.utils.J.b(i, 512), com.android.tools.r8.utils.J.b(i, 1024), com.android.tools.r8.utils.J.b(i, 2048), com.android.tools.r8.utils.J.b(i, 4096));
            if (!b && c3503zP.n() != i) {
                throw new AssertionError();
            }
            this.a.a(i, c3503zP);
        }
        return c3503zP;
    }
}
