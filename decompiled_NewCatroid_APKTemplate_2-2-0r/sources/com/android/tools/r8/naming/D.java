package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0757u1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AG;
import com.android.tools.r8.internal.AbstractC0824Ac;
import com.android.tools.r8.internal.AbstractC0833Al;
import com.android.tools.r8.internal.AbstractC1231Oc;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC2967s4;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C1736c3;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2477lf;
import com.android.tools.r8.internal.C2972s9;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3446yc;
import com.android.tools.r8.internal.InterfaceC2488lo;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.QW;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.b20$$ExternalSyntheticLambda1;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.Y2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class D {
    static final /* synthetic */ boolean a = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AG ag, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == ag;
    }

    public static boolean a(C0722p1 c0722p1, C0684j2 c0684j2) {
        int iF0 = c0684j2.F0();
        C0767v2 c0767v2 = c0684j2.e;
        C0767v2 c0767v3 = c0722p1.m2;
        if (c0767v2 == c0767v3) {
            if (iF0 != 0 && iF0 <= 3) {
                if (iF0 == 1) {
                    C0767v2 c0767v4 = c0684j2.h.e;
                    if (c0767v4 != c0767v3 && c0767v4 != c0722p1.q2) {
                        return false;
                    }
                } else if (iF0 == 2) {
                    if (c0684j2.h.e != c0722p1.r2) {
                        return false;
                    }
                } else if (c0684j2.h.e != c0767v3) {
                    return false;
                }
                C0767v2[] c0767v2Arr = c0684j2.h.f.a;
                if (c0767v2Arr[0] != c0722p1.X1) {
                    return false;
                }
                if (iF0 == 2 && c0767v2Arr[1] != c0722p1.d2) {
                    return false;
                }
                if (iF0 != 3 || c0767v2Arr[1] == c0722p1.v1 || c0767v2Arr[2] == c0722p1.o2) {
                }
            }
            return false;
        }
        C0759u2 c0759u2 = c0767v2.e;
        if (c0759u2 == c0722p1.U0 || c0759u2 == c0722p1.V0) {
            if (iF0 != 2) {
                return false;
            }
            C0737r2 c0737r2 = c0684j2.h;
            if (c0737r2.e != c0767v2) {
                return false;
            }
            C0767v2[] c0767v2Arr2 = c0737r2.f.a;
            if (c0767v2Arr2[0] != c0767v3 || c0767v2Arr2[1] != c0722p1.X1) {
                return false;
            }
        } else if (c0759u2 == c0722p1.W0) {
            if (iF0 != 3) {
                return false;
            }
            C0737r2 c0737r3 = c0684j2.h;
            if (c0737r3.e != c0767v2) {
                return false;
            }
            C0767v2[] c0767v2Arr3 = c0737r3.f.a;
            if (c0767v2Arr3[0] != c0767v3 || c0767v2Arr3[1] != c0767v3 || c0767v2Arr3[2] != c0722p1.X1) {
                return false;
            }
        } else {
            if (iF0 != 2 && iF0 != 3) {
                return false;
            }
            if (iF0 == 2) {
                if (c0684j2.h.e != c0722p1.q2) {
                    return false;
                }
            } else if (c0684j2.h.e != c0722p1.r2) {
                return false;
            }
            C0767v2[] c0767v2Arr4 = c0684j2.h.f.a;
            if (c0767v2Arr4[0] != c0767v3 || c0767v2Arr4[1] != c0722p1.X1) {
                return false;
            }
            if (iF0 == 3 && c0767v2Arr4[2] != c0722p1.d2) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:51:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:53:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:54:0x0100  */
    private static C0800x2 a(final AG ag, ArrayList arrayList, AbstractC2412ko abstractC2412ko, C0722p1 c0722p1) {
        int iN2;
        C0767v2 c0767v2A;
        AbstractC2412ko abstractC2412ko2;
        S10 s10J2 = ag.J2();
        int iN3 = s10J2.J() ? s10J2.n().E().N2() : -1;
        if (iN3 < 0) {
            return null;
        }
        if (iN3 == 0) {
            return C0800x2.s0();
        }
        C0767v2[] c0767v2Arr = new C0767v2[iN3];
        Set setC = OU.c();
        setC.addAll(ag.b().h0());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            setC.addAll(((C2972s9) it.next()).b().h0());
        }
        C3332x4 c3332x4H = ag.h();
        InterfaceC2488lo interfaceC2488loR = c3332x4H.R();
        interfaceC2488loR.a(new Predicate() { // from class: com.android.tools.r8.naming.D$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return D.a(ag, (AbstractC2412ko) obj);
            }
        });
        while (true) {
            int i = iN3;
            while (true) {
                if (interfaceC2488loR.hasNext()) {
                    AbstractC2412ko next = interfaceC2488loR.next();
                    if (!setC.contains(next)) {
                        continue;
                    } else {
                        if (next == abstractC2412ko) {
                            if (i == 0) {
                                return new C0800x2(c0767v2Arr);
                            }
                            return null;
                        }
                        if (!next.r1()) {
                            if (!next.w1() || !arrayList.contains(next.y())) {
                                c0767v2Arr = new C0767v2[iN3];
                            }
                        } else {
                            C1736c3 c1736c3V = next.v();
                            if (!c1736c3V.K2().J() || (iN2 = c1736c3V.K2().n().E().N2()) < 0 || iN2 >= c0767v2Arr.length) {
                                return null;
                            }
                            S10 s10Value = c1736c3V.value();
                            if (s10Value.i()) {
                                c0767v2A = null;
                            } else if (s10Value.L()) {
                                AbstractC0824Ac abstractC0824AcN = s10Value.n();
                                abstractC0824AcN.getClass();
                                if (abstractC0824AcN instanceof C3446yc) {
                                    c0767v2A = s10Value.n().A().K2();
                                } else {
                                    abstractC2412ko2 = s10Value.b;
                                    abstractC2412ko2.getClass();
                                    if (abstractC2412ko2 instanceof QW) {
                                        c0767v2A = c0722p1.x4.a(s10Value.b.F0().getField());
                                    } else {
                                        c0767v2A = null;
                                    }
                                }
                            } else {
                                abstractC2412ko2 = s10Value.b;
                                abstractC2412ko2.getClass();
                                if (abstractC2412ko2 instanceof QW) {
                                    c0767v2A = c0722p1.x4.a(s10Value.b.F0().getField());
                                } else {
                                    c0767v2A = null;
                                }
                            }
                            if (c0767v2A == null) {
                                return null;
                            }
                            if (c0767v2Arr[iN2] == null) {
                                i--;
                            }
                            c0767v2Arr[iN2] = c0767v2A;
                        }
                    }
                } else {
                    if (!c3332x4H.m().M1()) {
                        return null;
                    }
                    c3332x4H = AbstractC2967s4.a(c3332x4H);
                    if (c3332x4H.y().size() != 1 || (interfaceC2488loR = c3332x4H.R()) == null) {
                        return null;
                    }
                }
            }
        }
    }

    public static boolean a(C0722p1 c0722p1, S10 s10) {
        S10 s10M = s10.m();
        if (!s10M.b(new b20$$ExternalSyntheticLambda1())) {
            return false;
        }
        C2115gt c2115gtI0 = s10M.b.i0();
        C0757u1 c0757u1 = c0722p1.p4;
        return c0757u1.n.contains(c2115gtI0.S2());
    }

    /* JADX WARN: Code duplicated, block: B:130:0x0267  */
    /* JADX WARN: Code duplicated, block: B:132:0x0270  */
    /* JADX WARN: Code duplicated, block: B:133:0x0281  */
    /* JADX WARN: Code duplicated, block: B:138:0x0290 A[EDGE_INSN: B:138:0x0290->B:139:0x0291 BREAK  A[LOOP:3: B:121:0x023a->B:136:0x0285]] */
    /* JADX WARN: Multi-variable type inference failed */
    public static AbstractC0833Al a(AbstractC1305Qs abstractC1305Qs, C0702m c0702m, o5 o5Var) {
        AbstractC0727q0 abstractC0727q0B;
        C0800x2 c0800x2;
        C0767v2 c0767v2A;
        AbstractC2412ko abstractC2412ko;
        C0722p1 c0722p1A = c0702m.a();
        List listJ2 = abstractC1305Qs.J2();
        if (abstractC1305Qs instanceof C1721bt) {
            C1721bt c1721btG0 = abstractC1305Qs.g0();
            C0757u1 c0757u1 = c0722p1A.p4;
            C0684j2 c0684j2S2 = c1721btG0.S2();
            if (c0684j2S2 == c0757u1.b || c0684j2S2 == c0757u1.c) {
                return AbstractC0833Al.a(AbstractC1231Oc.a(c1721btG0, c0702m));
            }
        }
        int i = 0;
        C0684j2 reference = null;
        c0767v2W0 = null;
        c0767v2W0 = null;
        C0767v2 c0767v2W0 = null;
        if (abstractC1305Qs.f2()) {
            C2115gt c2115gtI0 = abstractC1305Qs.i0();
            if (c2115gtI0.S2() == c0722p1A.k4.i && (a(c0722p1A, c2115gtI0.U2()) || a(c0722p1A, (S10) ((ArrayList) c2115gtI0.h1()).get(1)))) {
                List listH1 = c2115gtI0.h1();
                int i2 = 0;
                while (true) {
                    ArrayList arrayList = (ArrayList) listH1;
                    if (i2 >= arrayList.size()) {
                        i2 = -1;
                        break;
                    }
                    S10 s10M = ((S10) arrayList.get(i2)).m();
                    if (s10M.K() || s10M.M()) {
                        break;
                    }
                    i2++;
                }
                if (i2 >= 0) {
                    S10 s10M2 = ((S10) ((ArrayList) c2115gtI0.h1()).get(i2)).m();
                    boolean z = a;
                    if (!z && s10M2.i()) {
                        throw new AssertionError();
                    }
                    if (!z && !s10M2.K() && !s10M2.M()) {
                        throw new AssertionError();
                    }
                    if (s10M2.K()) {
                        String string = s10M2.b.F().J2().toString();
                        String strH = C3970u0.E(string) ? C3970u0.H(string) : null;
                        if (strH != null) {
                            c0767v2W0 = c0702m.a().e(strH);
                        }
                    } else if (s10M2.M()) {
                        AbstractC0744s2 abstractC0744s2J2 = s10M2.b.J().J2();
                        abstractC0744s2J2.getClass();
                        if (abstractC0744s2J2 instanceof C0767v2) {
                            c0767v2W0 = abstractC0744s2J2.w0();
                        }
                    }
                    return AbstractC0833Al.b(c0767v2W0);
                }
            }
        }
        ArrayList arrayList2 = (ArrayList) listJ2;
        if (arrayList2.size() == 1) {
            return null;
        }
        Object[] objArr = abstractC1305Qs.L2().e == c0722p1A.W0;
        S10 s10 = (S10) arrayList2.get(objArr != false ? 2 : 1);
        if (s10.K()) {
            S10 s11 = (S10) arrayList2.get(0);
            if (!s11.I()) {
                return null;
            }
            C0767v2 c0767v2K2 = s11.n().A().K2();
            if (c0767v2K2.M0() || (abstractC0727q0B = c0702m.b(o5Var.a(), c0767v2K2)) == null) {
                return null;
            }
            C0759u2 c0759u2J2 = s10.n().F().J2();
            int size = arrayList2.size();
            if (objArr == true) {
                S10 s12 = (S10) arrayList2.get(1);
                if (s12.I()) {
                    return AbstractC0833Al.b(a(abstractC0727q0B, c0759u2J2.toString(), s12.n().A().K2()));
                }
                return null;
            }
            if (size == 2) {
                return AbstractC0833Al.b(a(abstractC0727q0B, c0759u2J2.toString(), (C0767v2) null));
            }
            if (!a && size != 3) {
                throw new AssertionError();
            }
            S10 s10E = (S10) arrayList2.get(2);
            ArrayList arrayList3 = new ArrayList();
            if (!s10E.i() && s10E.b.w1() && s10E.b.y().J2() == c0722p1A.d2) {
                while (!s10E.i() && s10E.b.w1()) {
                    arrayList3.add(s10E.b.y());
                    s10E = s10E.b.y().e();
                }
            }
            if (s10E.i()) {
                c0800x2 = null;
                break;
            }
            if (s10E.V()) {
                c0800x2 = C0800x2.s0();
            } else {
                AbstractC2412ko abstractC2412ko2 = s10E.b;
                abstractC2412ko2.getClass();
                if (abstractC2412ko2 instanceof AG) {
                    c0800x2 = a(s10E.b.r0(), arrayList3, abstractC1305Qs, c0722p1A);
                } else {
                    if (!s10E.b.b2()) {
                        c0800x2 = null;
                        break;
                    }
                    ArrayList arrayList4 = (ArrayList) s10E.b.e0().h1();
                    int size2 = arrayList4.size();
                    C0767v2[] c0767v2Arr = new C0767v2[size2];
                    while (true) {
                        if (i < size2) {
                            S10 s13 = (S10) arrayList4.get(i);
                            if (s13.i()) {
                                c0767v2A = null;
                            } else if (s13.L()) {
                                AbstractC0824Ac abstractC0824AcN = s13.n();
                                abstractC0824AcN.getClass();
                                if (abstractC0824AcN instanceof C3446yc) {
                                    c0767v2A = s13.n().A().K2();
                                } else {
                                    abstractC2412ko = s13.b;
                                    abstractC2412ko.getClass();
                                    if (abstractC2412ko instanceof QW) {
                                        c0767v2A = c0722p1A.x4.a(s13.b.F0().getField());
                                    } else {
                                        c0767v2A = null;
                                    }
                                }
                            } else {
                                abstractC2412ko = s13.b;
                                abstractC2412ko.getClass();
                                if (abstractC2412ko instanceof QW) {
                                    c0767v2A = c0722p1A.x4.a(s13.b.F0().getField());
                                } else {
                                    c0767v2A = null;
                                }
                            }
                            if (c0767v2A == null) {
                                c0800x2 = null;
                                break;
                            }
                            c0767v2Arr[i] = c0767v2A;
                            i++;
                        } else {
                            c0800x2 = new C0800x2(c0767v2Arr);
                            break;
                        }
                    }
                }
            }
            if (c0800x2 == null) {
                return null;
            }
            String string2 = c0759u2J2.toString();
            for (W0 w0 : abstractC0727q0B.M1()) {
                if (w0.getReference().f.toString().equals(string2) && w0.getReference().h.f.equals(c0800x2)) {
                    reference = w0.getReference();
                    break;
                }
            }
            return AbstractC0833Al.b(reference);
        }
        if (!s10.M()) {
            return null;
        }
        C2477lf c2477lfJ = s10.n().J();
        AbstractC0744s2 abstractC0744s2J3 = c2477lfJ.J2();
        abstractC0744s2J3.getClass();
        if (abstractC0744s2J3 instanceof C0767v2) {
            return AbstractC0833Al.c(c2477lfJ.J2().w0());
        }
        return AbstractC0833Al.a(c2477lfJ.J2().u0());
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00a0 A[EDGE_INSN: B:33:0x00a0->B:34:0x00a1 BREAK  A[LOOP:0: B:28:0x007a->B:45:?]] */
    static AbstractC0744s2 a(C0702m c0702m, C0759u2 c0759u2) {
        C0684j2 c0684j2A;
        String str;
        String strSubstring;
        String string = c0759u2.toString();
        List<String> listA = Y2.a(string, '#');
        if (listA.size() > 2) {
            c0684j2A = null;
            break;
        }
        if (listA.size() == 2) {
            str = listA.get(0);
            strSubstring = listA.get(1);
        } else {
            int iLastIndexOf = string.lastIndexOf(".");
            if (iLastIndexOf <= 0 || iLastIndexOf >= string.length() - 1) {
                str = null;
                strSubstring = null;
            } else {
                String strSubstring2 = string.substring(0, iLastIndexOf);
                strSubstring = string.substring(iLastIndexOf + 1);
                str = strSubstring2;
            }
        }
        if (str == null) {
            c0684j2A = null;
            break;
        }
        String strH = C3970u0.E(str) ? C3970u0.H(str) : null;
        if (strH != null) {
            AbstractC0727q0 abstractC0727q0D = ((C3733i) c0702m.f()).d(c0702m.a().e(strH));
            if (abstractC0727q0D == null) {
                c0684j2A = null;
                break;
            }
            c0684j2A = a(abstractC0727q0D, strSubstring, (C0767v2) null);
            if (c0684j2A == null) {
                Iterator<W0> it = abstractC0727q0D.M1().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        c0684j2A = null;
                        break;
                    }
                    W0 next = it.next();
                    if (next.getReference().f.toString().equals(strSubstring)) {
                        c0684j2A = next.getReference();
                        break;
                    }
                }
            }
        } else {
            c0684j2A = null;
            break;
        }
        if (c0684j2A != null) {
            return c0684j2A;
        }
        String string2 = c0759u2.toString();
        String strH2 = C3970u0.E(string2) ? C3970u0.H(string2) : null;
        if (strH2 != null) {
            return c0702m.a().e(strH2);
        }
        return null;
    }

    private static Y0 a(AbstractC0727q0 abstractC0727q0, String str, C0767v2 c0767v2) {
        for (com.android.tools.r8.graph.T0 t0 : abstractC0727q0.W0()) {
            if (t0.getReference().f.toString().equals(str) && (c0767v2 == null || t0.getReference().h == c0767v2)) {
                return t0.getReference();
            }
        }
        return null;
    }
}
