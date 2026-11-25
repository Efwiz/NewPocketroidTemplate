package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class TW implements D4 {
    private final VL a;
    private final VL b;
    private final GL c;

    public TW() {
        VL vl = new VL(FL.a(new TW$$ExternalSyntheticLambda0()));
        this.a = vl;
        VL vl2 = new VL(new BF$$ExternalSyntheticLambda0());
        this.b = vl2;
        this.c = GL.b(vl, vl2);
    }

    @Override // com.android.tools.r8.internal.D4
    public final void a() {
    }

    @Override // com.android.tools.r8.internal.D4
    public final boolean a(C3486zB c3486zB) {
        VD vdA = this.c.a(c3486zB);
        if (vdA == null) {
            return false;
        }
        SW swH0 = this.a.a(vdA).H0();
        C3044tC c3044tCK0 = this.b.a(vdA).k0();
        if (c3044tCK0.J2() != swH0.b() || swH0.b().Z() != 1) {
            return false;
        }
        c3044tCK0.b().g(swH0.J2());
        swH0.J2().e(swH0);
        c3044tCK0.J2().e(c3044tCK0);
        c3486zB.s();
        c3486zB.next();
        c3486zB.s();
        FL.a(c3486zB, 1);
        return true;
    }
}
