package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ck, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1787ck extends EZ {
    private EZ a;

    C1787ck() {
    }

    public final void a(EZ ez) {
        if (this.a != null) {
            throw new AssertionError();
        }
        this.a = ez;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) {
        EZ ez = this.a;
        if (ez != null) {
            return ez.a(c1486Wt);
        }
        throw new IllegalStateException();
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) {
        EZ ez = this.a;
        if (ez != null) {
            ez.a(c1799cu, obj);
            return;
        }
        throw new IllegalStateException();
    }
}
