package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ug, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3150ug {
    static final /* synthetic */ boolean e = true;
    final AbstractC2178hm a;
    final AbstractC2178hm b;
    final AbstractC1048Hm c;
    final int d;

    public C3150ug(AbstractC2178hm abstractC2178hm, AbstractC2178hm abstractC2178hm2, AbstractC1048Hm abstractC1048Hm, int i) {
        this.a = abstractC2178hm;
        this.b = abstractC2178hm2;
        this.c = abstractC1048Hm;
        this.d = i;
    }

    public final AbstractC3303wg a(com.android.tools.r8.graph.Y0 y0) {
        if (e || this.a.containsKey(y0)) {
            return (AbstractC3303wg) this.a.get(y0);
        }
        throw new AssertionError();
    }
}
