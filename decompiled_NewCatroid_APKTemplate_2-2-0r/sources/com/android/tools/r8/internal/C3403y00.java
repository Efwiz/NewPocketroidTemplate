package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.y00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3403y00 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final C2102gl b;
    private final HashMap c = new HashMap();

    public C3403y00(C0702m c0702m, C2102gl c2102gl) {
        this.a = c0702m;
        this.b = c2102gl;
    }

    public static AbstractC3472z00 a(EnumC1968f20 enumC1968f20) {
        switch (enumC1968f20) {
            case OBJECT:
                return AbstractC3472z00.f();
            case INT:
                return AbstractC3472z00.k();
            case FLOAT:
                return AbstractC3472z00.j();
            case INT_OR_FLOAT:
                return AbstractC3472z00.o();
            case INT_OR_FLOAT_OR_OBJECT:
                return AbstractC3472z00.p();
            case LONG:
                return AbstractC3472z00.l();
            case DOUBLE:
                return AbstractC3472z00.i();
            case LONG_OR_DOUBLE:
                return AbstractC3472z00.q();
            default:
                throw new C3405y10("Unexpected constraint type: " + enumC1968f20);
        }
    }

    public final void a(ArrayList arrayList, C2409kl c2409kl) {
        ArrayList<S10> arrayList2 = new ArrayList();
        for (C3332x4 c3332x4 : c2409kl.d) {
            for (ML ml : c3332x4.x()) {
                if (!ml.w().G()) {
                    arrayList2.add(ml);
                }
                for (S10 s10 : ml.k0()) {
                    S10 s11 = ml;
                    S10 s12 = s11;
                    while (s11 != null) {
                        S10 s13 = (S10) this.c.get(s11);
                        if (s13 != null) {
                            this.c.put(s12, s13);
                        }
                        s12 = s11;
                        s11 = s13;
                    }
                    S10 s14 = s10;
                    while (s10 != null) {
                        S10 s15 = (S10) this.c.get(s10);
                        if (s15 != null) {
                            this.c.put(s14, s15);
                        }
                        s14 = s10;
                        s10 = s15;
                    }
                    a(s12, s14);
                }
            }
            for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                if (abstractC2412ko.b() != null && !abstractC2412ko.a().G()) {
                    arrayList2.add(abstractC2412ko.b());
                }
                if (abstractC2412ko.N1() && ((ArrayList) abstractC2412ko.h1()).size() == 2) {
                    C1015Gl c1015GlQ = abstractC2412ko.Q();
                    if (!d && c1015GlQ.N2()) {
                        throw new AssertionError();
                    }
                    EnumC1076Il enumC1076IlL2 = c1015GlQ.L2();
                    if (enumC1076IlL2 == EnumC1076Il.EQ || enumC1076IlL2 == EnumC1076Il.NE) {
                        S10 s16 = (S10) ((ArrayList) c1015GlQ.h1()).get(0);
                        S10 s17 = (S10) ((ArrayList) c1015GlQ.h1()).get(1);
                        S10 s18 = s16;
                        while (s16 != null) {
                            S10 s19 = (S10) this.c.get(s16);
                            if (s19 != null) {
                                this.c.put(s18, s19);
                            }
                            s18 = s16;
                            s16 = s19;
                        }
                        S10 s110 = s17;
                        while (s17 != null) {
                            S10 s111 = (S10) this.c.get(s17);
                            if (s111 != null) {
                                this.c.put(s110, s111);
                            }
                            s110 = s17;
                            s17 = s111;
                        }
                        a(s18, s110);
                    }
                }
            }
        }
        ArrayList arrayList3 = new ArrayList(arrayList2.size());
        for (S10 s112 : arrayList2) {
            this.b.a(s112, a(false, s112));
            if (!s112.w().G()) {
                arrayList3.add(s112);
            }
        }
        new C3030t00(this.a, true).b(c2409kl);
        a(c2409kl, arrayList, arrayList3);
    }

    private void a(C2409kl c2409kl, ArrayList arrayList, ArrayList arrayList2) {
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((InterfaceC1299Qm) it.next()).a(this);
            }
        }
        ArrayList arrayList3 = new ArrayList(arrayList2.size());
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            S10 s10 = (S10) it2.next();
            this.b.a(s10, a(true, s10));
            if (!s10.w().G()) {
                arrayList3.add(s10);
            }
        }
        if (arrayList3.isEmpty()) {
            return;
        }
        this.a.Y().e.a(new StringDiagnostic("Cannot determine precise type for value: " + arrayList3.get(0) + ", its imprecise type is: " + ((S10) arrayList3.get(0)).w(), c2409kl.k, new MethodPosition(c2409kl.u().getReference().E0())));
        throw null;
    }

    public final void a(EnumC2523mE enumC2523mE, S10 s10, S10 s11, Consumer consumer) {
        EnumC1968f20 enumC1968f20A;
        if (!d && enumC2523mE.a()) {
            throw new AssertionError();
        }
        S10 s12 = s10;
        while (s10 != null) {
            S10 s13 = (S10) this.c.get(s10);
            if (s13 != null) {
                this.c.put(s12, s13);
            }
            s12 = s10;
            s10 = s13;
        }
        if (s11.w().r()) {
            enumC1968f20A = EnumC1968f20.a(s11.w().a().T());
        } else {
            enumC1968f20A = a(true, s12);
        }
        this.b.a(s12, enumC1968f20A);
        consumer.accept(EnumC2523mE.a(enumC2523mE, enumC1968f20A));
    }

    private static void a(S10 s10, AbstractC1048Hm abstractC1048Hm) {
        for (AbstractC2412ko abstractC2412ko : s10.h0()) {
            if (abstractC2412ko.N1()) {
                C1015Gl c1015GlQ = abstractC2412ko.Q();
                if (!c1015GlQ.N2()) {
                    S10 s11 = (S10) ((ArrayList) c1015GlQ.h1()).get(1 - ((ArrayList) c1015GlQ.h1()).indexOf(s10));
                    if (!abstractC1048Hm.contains(s11) && !d) {
                        C0862Bm c0862BmG = AbstractC1048Hm.g();
                        Iterator it = abstractC1048Hm.iterator();
                        while (it.hasNext()) {
                            c0862BmG.a(it.next());
                        }
                        a(s11, c0862BmG.a(s10).a());
                    }
                }
            } else if (abstractC2412ko.r1()) {
                C1736c3 c1736c3V = abstractC2412ko.v();
                boolean z = d;
                if (!z && s10 != c1736c3V.value()) {
                    throw new AssertionError();
                }
                if (!z && c1736c3V.d().a()) {
                    throw new AssertionError();
                }
                if (!z && !c1736c3V.J2().w().N().f()) {
                    throw new AssertionError();
                }
            } else if (!d) {
                throw new AssertionError();
            }
        }
    }

    private void a(S10 s10, S10 s11) {
        if (s10 == s11) {
            return;
        }
        AbstractC3472z00 abstractC3472z00W = s10.w();
        AbstractC3472z00 abstractC3472z00W2 = s11.w();
        if (abstractC3472z00W.G() && abstractC3472z00W2.G()) {
            if (abstractC3472z00W != abstractC3472z00W2) {
                if ((abstractC3472z00W instanceof G5 ? EnumC1968f20.OBJECT : EnumC1968f20.a(abstractC3472z00W)) != (abstractC3472z00W2 instanceof G5 ? EnumC1968f20.OBJECT : EnumC1968f20.a(abstractC3472z00W2))) {
                    throw new C1367Tb("Cannot unify types for values " + s10 + ":" + abstractC3472z00W + " and " + s11 + ":" + abstractC3472z00W2);
                }
                return;
            }
            return;
        }
        if (abstractC3472z00W.G()) {
            this.c.put(s11, s10);
        } else {
            this.c.put(s10, s11);
        }
    }

    private EnumC1968f20 a(boolean z, S10 s10) {
        S10 s11 = s10;
        S10 s12 = s11;
        while (s11 != null) {
            S10 s13 = (S10) this.c.get(s11);
            if (s13 != null) {
                this.c.put(s12, s13);
            }
            s12 = s11;
            s11 = s13;
        }
        AbstractC3472z00 abstractC3472z00W = s12.w();
        abstractC3472z00W.getClass();
        EnumC1968f20 enumC1968f20A = abstractC3472z00W instanceof G5 ? EnumC1968f20.OBJECT : EnumC1968f20.a(abstractC3472z00W);
        int iOrdinal = enumC1968f20A.ordinal();
        if (iOrdinal == 3) {
            if (!d && z) {
                a(s10, AbstractC1048Hm.k());
            }
            return z ? EnumC1968f20.INT : enumC1968f20A;
        }
        if (iOrdinal == 4) {
            if (d || !z) {
                return EnumC1968f20.INT_OR_FLOAT;
            }
            throw new AssertionError();
        }
        if (iOrdinal != 7) {
            return enumC1968f20A;
        }
        if (!d && z) {
            a(s10, AbstractC1048Hm.k());
        }
        return z ? EnumC1968f20.LONG : enumC1968f20A;
    }
}
