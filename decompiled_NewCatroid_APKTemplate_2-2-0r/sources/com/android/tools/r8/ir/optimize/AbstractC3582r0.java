package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC2967s4;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3044tC;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3479z4;
import com.android.tools.r8.internal.FL;
import com.android.tools.r8.internal.ML;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.SW;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.r0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3582r0 {
    public static boolean a(C2409kl c2409kl) {
        return b(c2409kl);
    }

    private static boolean b(C2409kl c2409kl) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        for (C3332x4 c3332x4 : c2409kl.d) {
            HashSet hashSet = new HashSet(c3332x4.y());
            for (boolean z4 = true; z4; z4 = z) {
                z = false;
                for (ML ml : c3332x4.x()) {
                    Iterator<C3332x4> it = c3332x4.y().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            C3332x4 next = it.next();
                            if (!next.m().M1() || AbstractC2967s4.a(next) != c3332x4) {
                            }
                        } else if (ml.Z() == 1 && ml.b0() == 1) {
                            Iterator<S10> it2 = ml.k0().iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    C3044tC c3044tCK0 = ml.f0().k0();
                                    if (c3044tCK0 != null && !c3044tCK0.J2().A()) {
                                        HashSet hashSet2 = new HashSet();
                                        int iB = 0;
                                        C3332x4 c3332x4A = c3332x4;
                                        while (true) {
                                            if (c3332x4A != null) {
                                                hashSet2.add(c3332x4A);
                                                Iterator<AbstractC2412ko> it3 = c3332x4A.p().iterator();
                                                while (true) {
                                                    if (!it3.hasNext()) {
                                                        c3332x4A = (!c3332x4A.m().M1() || hashSet2.contains(c3332x4A.m().P().K2())) ? null : AbstractC2967s4.a(c3332x4A);
                                                    } else {
                                                        if (c3044tCK0 == it3.next()) {
                                                            break;
                                                        }
                                                        int iA = iB - FL.a(c3044tCK0);
                                                        if (iA >= 0) {
                                                            iB = iA + FL.b(c3044tCK0);
                                                        }
                                                    }
                                                }
                                            }
                                            iB = Integer.MIN_VALUE;
                                            break;
                                        }
                                        if (iB == 0) {
                                            Iterator<S10> it4 = ml.k0().iterator();
                                            while (true) {
                                                if (!it4.hasNext()) {
                                                    ArrayList arrayList = new ArrayList();
                                                    Iterator<S10> it5 = ml.k0().iterator();
                                                    while (it5.hasNext()) {
                                                        arrayList.add(it5.next().b.H0());
                                                    }
                                                    for (int i = 0; i < arrayList.size(); i++) {
                                                        SW sw = (SW) arrayList.get(i);
                                                        ml.a(i, sw.J2());
                                                        sw.J2().e(sw);
                                                        sw.h().g(sw);
                                                    }
                                                    c3044tCK0.b().g(ml);
                                                    c3044tCK0.J2().e(c3044tCK0);
                                                    c3044tCK0.h().g(c3044tCK0);
                                                    ml.p0();
                                                    z2 = true;
                                                    break;
                                                }
                                                S10 next2 = it4.next();
                                                AbstractC2412ko abstractC2412ko = next2.b;
                                                if (abstractC2412ko != null && (abstractC2412ko instanceof SW) && hashSet.contains(abstractC2412ko.h())) {
                                                    AbstractC2412ko abstractC2412ko2 = next2.b;
                                                    C3332x4 c3332x4H = abstractC2412ko2.h();
                                                    C3479z4 c3479z4D = c3332x4H.d(c3332x4H.p().size() - 1);
                                                    int iA2 = 0;
                                                    while (c3479z4D.hasPrevious() && c3479z4D.previous() != abstractC2412ko2) {
                                                        int iB2 = iA2 - FL.b(abstractC2412ko2);
                                                        if (iB2 < 0) {
                                                            iA2 = Integer.MIN_VALUE;
                                                            break;
                                                        }
                                                        iA2 = iB2 + FL.a(abstractC2412ko2);
                                                    }
                                                    if (iA2 != 0) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (it2.next().Z() != 1) {
                                }
                            }
                        }
                        z2 = false;
                        break;
                    }
                    z |= z2;
                }
                if (z) {
                    z3 = true;
                }
            }
        }
        return z3;
    }
}
