package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3113uC implements D4 {
    static final /* synthetic */ boolean d = true;
    private final VL a;
    private final VL b;
    private final GL c;

    public C3113uC() {
        VL vl = new VL(FL.a(new BF$$ExternalSyntheticLambda0()));
        this.a = vl;
        VL vl2 = new VL(FL.a(new BF$$ExternalSyntheticLambda0()));
        this.b = vl2;
        this.c = GL.a(vl, vl2);
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
        C3044tC c3044tCK0 = this.a.a(vdA).k0();
        C3044tC c3044tCK1 = this.b.a(vdA).k0();
        if (c3044tCK1.J2() != c3044tCK0.J2()) {
            return false;
        }
        boolean z = d;
        if (!z && c3044tCK1.J2().A()) {
            throw new AssertionError();
        }
        if (!z && c3044tCK0.J2().A()) {
            throw new AssertionError();
        }
        C2617nW c2617nW = (C2617nW) c3044tCK1.b();
        c2617nW.e(c3044tCK0);
        int iJ0 = c2617nW.j0();
        C2617nW c2617nWC = c2617nW.c(iJ0);
        C2617nW c2617nWC2 = c2617nW.c(iJ0 + 1);
        c3044tCK1.b().g(c2617nWC);
        c3044tCK0.b().g(c2617nWC2);
        c3486zB.a(new C1099Jf(c2617nWC, c2617nWC2, c2617nW), (Set<S10>) null);
        return true;
    }
}
