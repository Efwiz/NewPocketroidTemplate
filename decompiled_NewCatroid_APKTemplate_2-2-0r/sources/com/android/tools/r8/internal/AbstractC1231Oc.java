package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0757u1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3970u0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Oc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1231Oc {
    static final /* synthetic */ boolean a = true;

    public static C0767v2 a(C0702m c0702m, S10 s10) {
        C1106Jl c1106JlA = C1106Jl.a();
        s10.getClass();
        S10 s10A = s10.a(c1106JlA, CM.a());
        if (s10A.i()) {
            return null;
        }
        AbstractC2412ko abstractC2412ko = s10A.b;
        abstractC2412ko.getClass();
        if (abstractC2412ko instanceof C3446yc) {
            return s10A.b.A().K2();
        }
        AbstractC2412ko abstractC2412ko2 = s10A.b;
        abstractC2412ko2.getClass();
        if (abstractC2412ko2 instanceof C1721bt) {
            C1721bt c1721btG0 = s10A.b.g0();
            C0757u1 c0757u1 = c0702m.a().p4;
            C0684j2 c0684j2S2 = c1721btG0.S2();
            if (c0684j2S2 == c0757u1.b || c0684j2S2 == c0757u1.c) {
                return a(c1721btG0, c0702m);
            }
        }
        return null;
    }

    public static C0767v2 a(C1721bt c1721bt, C0702m c0702m) {
        if (!a) {
            C0757u1 c0757u1 = c0702m.a().p4;
            C0684j2 c0684j2S2 = c1721bt.S2();
            if (c0684j2S2 != c0757u1.b && c0684j2S2 != c0757u1.c) {
                throw new AssertionError();
            }
        }
        if (c1721bt.b.size() == 1 || c1721bt.b.size() == 3) {
            S10 s10 = (S10) c1721bt.b.get(0);
            if (s10.K()) {
                String string = s10.n().F().J2().toString();
                String strH = C3970u0.E(string) ? C3970u0.H(string) : null;
                if (strH != null) {
                    return c0702m.a().e(strH);
                }
                return null;
            }
            if (s10.M()) {
                AbstractC0744s2 abstractC0744s2J2 = s10.n().J().J2();
                abstractC0744s2J2.getClass();
                if (abstractC0744s2J2 instanceof C0767v2) {
                    return abstractC0744s2J2.w0();
                }
            }
        }
        return null;
    }
}
