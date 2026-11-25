package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Y2 {
    public static C3795s2 a(C0722p1 c0722p1, EnumC3891f enumC3891f) {
        X2 x2 = new X2(Origin.root(), enumC3891f);
        C3774o2 c3774o2 = new C3774o2();
        c3774o2.w();
        c3774o2.x();
        c3774o2.s();
        C3790r2 c3790r2G = C3795s2.G();
        c3790r2G.a = x2;
        c3790r2G.i = I2.b;
        return ((C3790r2) ((C3790r2) c3790r2G.a(AbstractC3833y2.a(G3.a(c0722p1.J3)))).c(AbstractC1639am.c(C3775o3.a().a(c3774o2).a(EnumC3791r3.FIELD).a(G3.a(c0722p1.A1)).a(N2.a.a("SDK_INT")).a(new C3786q3(new com.android.tools.r8.utils.W1(enumC3891f.d(), Integer.MAX_VALUE))).a()))).f();
    }

    public static boolean a(C0722p1 c0722p1, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            S2 s2 = (S2) it.next();
            if ((s2 instanceof C3808u2) || (s2 instanceof C3795s2)) {
                if (s2.d() == I2.b && s2.b().isEmpty() && s2.f().isEmpty() && (!s2.o() || s2.g().b(c0722p1.Z1))) {
                    if (!s2.c().d() && s2.c().a(c0722p1.J3)) {
                        for (C3775o3 c3775o3 : s2.j()) {
                            if (c3775o3.i() == EnumC3791r3.ALL || c3775o3.i() == EnumC3791r3.ALL_FIELDS || (c3775o3.i() == EnumC3791r3.FIELD && c3775o3.d().isEmpty() && !c3775o3.c().h() && !c3775o3.c().g() && !c3775o3.c().b() && !c3775o3.c().n() && !c3775o3.c().o() && !c3775o3.g().i() && !c3775o3.g().j() && !c3775o3.g().e() && c3775o3.j().b(c0722p1.A1) && c3775o3.f().a("SDK_INT"))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
