package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class OP extends C3503zP {
    static final /* synthetic */ boolean h = true;

    OP(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        super(i, z, z2, z3, z4, z5);
    }

    @Override // com.android.tools.r8.internal.C3503zP
    public final OP a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.C3503zP
    public final boolean a(boolean z) {
        return true;
    }

    @Override // com.android.tools.r8.internal.C3503zP
    public final boolean i() {
        return false;
    }

    @Override // com.android.tools.r8.internal.C3503zP
    public final boolean k() {
        return true;
    }

    @Override // com.android.tools.r8.internal.C3503zP
    public final void l() {
        if (!h && b() < 51) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.C3503zP
    public final int a(boolean z, AP ap) {
        int iB = ap.a(n() - 51).b();
        if (iB == 9) {
            return 1;
        }
        if (iB != 12) {
            return iB != 17 ? 0 : 1;
        }
        return com.android.tools.r8.utils.M.a(z);
    }
}
