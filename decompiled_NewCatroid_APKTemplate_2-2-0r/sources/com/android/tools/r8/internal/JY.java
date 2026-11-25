package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class JY {
    static final /* synthetic */ boolean a = true;

    public static boolean a(IY iy, int i) {
        S10 s10Value = iy.value();
        if (iy.T1()) {
            com.android.tools.r8.utils.W1 w1X = s10Value.x();
            return w1X != null && w1X.d() && w1X.a((long) iy.W().f(i));
        }
        if (!a && !iy.y2()) {
            throw new AssertionError();
        }
        S10 s10M = s10Value.m();
        return s10M.b(new JY$$ExternalSyntheticLambda0()) && iy.I0().f(i) == s10M.b.F().J2();
    }

    public static boolean a(IY iy, B1 b1, int i) {
        S10 s10Value = iy.value();
        if (iy.T1()) {
            int iF = iy.W().f(i);
            if (!b1.w() || b1.k().a(iF)) {
                return s10Value.G() && !s10Value.x().a((long) iF);
            }
            return true;
        }
        if (!a && !iy.y2()) {
            throw new AssertionError();
        }
        S10 s10M = s10Value.m();
        return s10M.b(new JY$$ExternalSyntheticLambda0()) && iy.I0().f(i) != s10M.b.F().J2();
    }

    public static boolean a(IY iy, B1 b1) {
        if (iy.T1()) {
            C1458Vr c1458VrW = iy.W();
            if (b1.w()) {
                return b1.k().a(c1458VrW.Q2()).d();
            }
            return false;
        }
        if (a || iy.y2()) {
            return false;
        }
        throw new AssertionError();
    }
}
