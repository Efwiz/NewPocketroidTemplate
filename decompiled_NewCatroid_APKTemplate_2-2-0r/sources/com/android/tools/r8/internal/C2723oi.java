package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oi, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2723oi extends S10 {
    private final ML q;

    public C2723oi(ML ml) {
        super(ml.u(), ml.w(), ml.s());
        this.q = ml;
    }

    @Override // com.android.tools.r8.internal.S10
    public final boolean L() {
        return false;
    }

    @Override // com.android.tools.r8.internal.S10
    public final boolean Y() {
        return true;
    }

    public final int a(C2971s8 c2971s8) {
        return c2971s8.a(this.q);
    }

    public final ML j0() {
        return this.q;
    }

    @Override // com.android.tools.r8.internal.S10
    public final String toString() {
        return "fixed:v" + this.q.u();
    }
}
