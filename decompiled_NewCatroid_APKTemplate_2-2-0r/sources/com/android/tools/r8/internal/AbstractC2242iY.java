package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2242iY {
    /* JADX WARN: Code duplicated, block: B:23:0x0061  */
    /* JADX WARN: Code duplicated, block: B:43:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:81:0x014e  */
    static void a(C2409kl c2409kl, C0722p1 c0722p1) {
        C1931eY c1931eY;
        S10 s10Value;
        S10 s10Value2;
        S10 s10Value3;
        int iA = c2409kl.A();
        ArrayList<C3332x4> arrayList = null;
        for (C3332x4 c3332x4 : c2409kl.d) {
            if (!c3332x4.c(iA)) {
                c3332x4.e(iA);
                AbstractC1954eu abstractC1954euM = c3332x4.m();
                if (abstractC1954euM.N1()) {
                    C1015Gl c1015GlQ = abstractC1954euM.Q();
                    s10Value2 = c1015GlQ.O2();
                    if (!a(c0722p1, s10Value2)) {
                        if (c1015GlQ.N2()) {
                            s10Value2 = null;
                        } else {
                            s10Value2 = c1015GlQ.P2();
                            if (!a(c0722p1, s10Value2)) {
                                s10Value2 = null;
                            }
                        }
                    }
                } else if (abstractC1954euM.T1()) {
                    s10Value2 = abstractC1954euM.W().value();
                    if (!a(c0722p1, s10Value2)) {
                        s10Value2 = null;
                    }
                } else {
                    s10Value2 = null;
                }
                if (s10Value2 != null) {
                    while (true) {
                        C3332x4 c3332x4A = AbstractC2162hY.a(c3332x4.m());
                        if (c3332x4A.c(iA)) {
                            c3332x4 = null;
                            break;
                        }
                        c3332x4A.e(iA);
                        AbstractC1954eu abstractC1954euM2 = c3332x4A.m();
                        if (abstractC1954euM2.N1()) {
                            C1015Gl c1015GlQ2 = abstractC1954euM2.Q();
                            s10Value3 = c1015GlQ2.O2();
                            if (!a(c0722p1, s10Value3)) {
                                if (c1015GlQ2.N2()) {
                                    s10Value3 = null;
                                } else {
                                    s10Value3 = c1015GlQ2.P2();
                                    if (!a(c0722p1, s10Value3)) {
                                        s10Value3 = null;
                                    }
                                }
                            }
                        } else if (abstractC1954euM2.T1()) {
                            s10Value3 = abstractC1954euM2.W().value();
                            if (!a(c0722p1, s10Value3)) {
                                s10Value3 = null;
                            }
                        } else {
                            s10Value3 = null;
                        }
                        if (s10Value3 == null) {
                            break;
                        } else {
                            c3332x4 = c3332x4A;
                        }
                    }
                    if (c3332x4 != null) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(c3332x4);
                    }
                }
            }
        }
        c2409kl.b(iA);
        if (arrayList != null) {
            boolean z = false;
            for (C3332x4 c3332x5 : arrayList) {
                C3332x4 c3332x4J = AbstractC2162hY.a(c3332x5.m()).j();
                C1773cY c1773cYA = new C1700bY().a(null, c3332x4J);
                if (c1773cYA == null) {
                    c1931eY = null;
                    break;
                }
                if (c1773cYA.a != null) {
                    AbstractC1954eu abstractC1954euM3 = c3332x5.m();
                    if (abstractC1954euM3.N1()) {
                        C1015Gl c1015GlQ3 = abstractC1954euM3.Q();
                        s10Value = c1015GlQ3.O2();
                        if (!a(c0722p1, s10Value)) {
                            if (c1015GlQ3.N2()) {
                                s10Value = null;
                            } else {
                                s10Value = c1015GlQ3.P2();
                                if (!a(c0722p1, s10Value)) {
                                    s10Value = null;
                                }
                            }
                        }
                    } else if (abstractC1954euM3.T1()) {
                        s10Value = abstractC1954euM3.W().value();
                        if (!a(c0722p1, s10Value)) {
                            s10Value = null;
                        }
                    } else {
                        s10Value = null;
                    }
                    if (!AbstractC2162hY.a && !a(c0722p1, s10Value)) {
                        throw new AssertionError();
                    }
                    S10 s10U2 = s10Value.b.i0().U2();
                    C2088gY c2088gYA = new C2008fY(c3332x4J, c0722p1, c1773cYA.b, s10U2).a((C2088gY) null, c3332x5);
                    if (c2088gYA != null) {
                        if (c2088gYA.a != null) {
                            if (c2088gYA.c.size() == c1773cYA.c.size()) {
                                LinkedHashMap linkedHashMap = new LinkedHashMap();
                                InterfaceC1912eK it = c2088gYA.c.keySet().iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        c1931eY = new C1931eY(c1773cYA.a, c2088gYA.a, linkedHashMap, s10U2);
                                        break;
                                    }
                                    C0759u2 c0759u2 = (C0759u2) it.next();
                                    C3332x4 c3332x6 = (C3332x4) c1773cYA.c.get(c2088gYA.c.a(c0759u2));
                                    if (c3332x6 != null) {
                                        linkedHashMap.put(c0759u2, c3332x6);
                                    }
                                }
                            }
                        } else if (!AbstractC1849dY.a) {
                            throw new AssertionError("Expected to find an insertion block");
                        }
                    }
                } else if (!AbstractC1849dY.a) {
                    throw new AssertionError("Expected to find a fallthrough block");
                }
                c1931eY = null;
                break;
                if (c1931eY != null) {
                    Iterator it2 = c1931eY.b.u().iterator();
                    while (it2.hasNext()) {
                        ((C3332x4) it2.next()).a(c1931eY.b, (Set) null);
                    }
                    c1931eY.b.X();
                    C0759u2[] c0759u2Arr = new C0759u2[c1931eY.c.size()];
                    int[] iArr = new int[c1931eY.c.size()];
                    MR mr = new MR();
                    int iU = c1931eY.b.U();
                    int i = 0;
                    for (Map.Entry entry : c1931eY.c.entrySet()) {
                        c0759u2Arr[i] = (C0759u2) entry.getKey();
                        C3332x4 c3332x7 = (C3332x4) entry.getValue();
                        if (mr.containsKey(c3332x7)) {
                            iArr[i] = mr.a(c3332x7);
                        } else {
                            iArr[i] = iU;
                            mr.a(iU, c3332x7);
                            c1931eY.b.h(c3332x7);
                            iU++;
                        }
                        i++;
                    }
                    c1931eY.b.h(c1931eY.a);
                    AbstractC1954eu abstractC1954euM4 = c1931eY.b.m();
                    abstractC1954euM4.h().a(c2409kl, (AbstractC2412ko) abstractC1954euM4).a(new C1620aY(c1931eY.d, c0759u2Arr, iArr, iU), (Set) null);
                    z = true;
                }
            }
            if (z) {
                c2409kl.y();
                c2409kl.z();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C0722p1 c0722p1, S10 s10) {
        S10 s10M = s10.m();
        if (s10M.i()) {
            return false;
        }
        AbstractC2412ko abstractC2412ko = s10M.b;
        return abstractC2412ko.f2() && abstractC2412ko.i0().S2() == c0722p1.k4.r;
    }
}
