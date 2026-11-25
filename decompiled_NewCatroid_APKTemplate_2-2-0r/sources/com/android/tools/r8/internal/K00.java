package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class K00 {
    static final /* synthetic */ boolean a = true;

    /* JADX WARN: Code duplicated, block: B:28:0x0062  */
    /* JADX WARN: Code duplicated, block: B:31:0x0075  */
    /* JADX WARN: Code duplicated, block: B:34:0x007c  */
    /* JADX WARN: Code duplicated, block: B:38:0x0095  */
    /* JADX WARN: Code duplicated, block: B:65:0x009d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:73:0x008e A[SYNTHETIC] */
    public static AbstractC3472z00 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        AbstractC3472z00 abstractC3472z00F;
        AbstractC1305Qs abstractC1305QsB0;
        AbstractC3472z00 abstractC3472z00F2;
        int i;
        AbstractC3472z00 abstractC3472z00W = s10.w();
        AbstractC3472z00 abstractC3472z00F3 = AbstractC3472z00.f();
        com.android.tools.r8.utils.O3 o3D = com.android.tools.r8.utils.O3.d();
        a(s10, o3D);
        while (o3D.c()) {
            J00 j00 = (J00) o3D.f();
            InterfaceC2729oo interfaceC2729oo = j00.a;
            if (interfaceC2729oo.i()) {
                a(interfaceC2729oo.l(), o3D);
            } else {
                AbstractC2412ko abstractC2412koK = interfaceC2729oo.k();
                S10 s11 = j00.b;
                int iG2 = abstractC2412koK.G2();
                if (iG2 == 9) {
                    a(abstractC2412koK.w().b(), o3D);
                    abstractC3472z00F = AbstractC3472z00.f();
                } else if (iG2 == 10 || iG2 == 25) {
                    abstractC3472z00F = AbstractC3472z00.f();
                } else if (iG2 == 28) {
                    abstractC3472z00F = abstractC2412koK.T().getField().B0().c((C0702m<?>) c0702m);
                } else if (iG2 == 30) {
                    C1793co c1793coV = abstractC2412koK.V();
                    com.android.tools.r8.graph.Y0 field = c1793coV.getField();
                    AbstractC3472z00 abstractC3472z00F4 = AbstractC3472z00.f();
                    if (c1793coV.e() == s11) {
                        abstractC3472z00F4 = abstractC3472z00F4.a(c0702m, field.B0().c((C0702m<?>) c0702m));
                    }
                    abstractC3472z00F = c1793coV.value() == s11 ? abstractC3472z00F4.a(c0702m, field.getType().c((C0702m<?>) c0702m)) : abstractC3472z00F4;
                } else if (iG2 == 56) {
                    abstractC3472z00F = o5Var.G().c((C0702m<?>) c0702m);
                } else if (iG2 == 60) {
                    abstractC3472z00F = abstractC2412koK.G0().getField().getType().c((C0702m<?>) c0702m);
                } else if (iG2 != 33 && iG2 != 34) {
                    switch (iG2) {
                        case 38:
                        case 39:
                        case 40:
                            abstractC1305QsB0 = abstractC2412koK.b0();
                            abstractC3472z00F2 = AbstractC3472z00.f();
                            for (i = 0; i < abstractC1305QsB0.J2().size(); i++) {
                                if (abstractC1305QsB0.c(i) != s11) {
                                    abstractC3472z00F2 = abstractC3472z00F2.a(c0702m, abstractC1305QsB0.S2().a(i, abstractC1305QsB0 instanceof C1721bt).c((C0702m<?>) c0702m));
                                }
                            }
                            if (!a) {
                                abstractC3472z00F2.getClass();
                                if (abstractC3472z00F2 instanceof G5) {
                                    throw new AssertionError();
                                }
                            }
                            abstractC3472z00F = abstractC3472z00F2;
                            break;
                        default:
                            abstractC3472z00F = AbstractC3472z00.p();
                            break;
                    }
                } else {
                    abstractC1305QsB0 = abstractC2412koK.b0();
                    abstractC3472z00F2 = AbstractC3472z00.f();
                    while (i < abstractC1305QsB0.J2().size()) {
                        if (abstractC1305QsB0.c(i) != s11) {
                            abstractC3472z00F2 = abstractC3472z00F2.a(c0702m, abstractC1305QsB0.S2().a(i, abstractC1305QsB0 instanceof C1721bt).c((C0702m<?>) c0702m));
                        }
                    }
                    if (!a) {
                        abstractC3472z00F2.getClass();
                        if (abstractC3472z00F2 instanceof G5) {
                            throw new AssertionError();
                        }
                    }
                    abstractC3472z00F = abstractC3472z00F2;
                }
                abstractC3472z00F3 = abstractC3472z00F3.a(c0702m, abstractC3472z00F);
                abstractC3472z00F3.getClass();
                if ((abstractC3472z00F3 instanceof C2163hZ) || abstractC3472z00F3.a(abstractC3472z00W)) {
                    return abstractC3472z00W;
                }
            }
        }
        return abstractC3472z00F3;
    }

    private static void a(S10 s10, com.android.tools.r8.utils.O3 o3) {
        Iterator<AbstractC2412ko> it = s10.h0().iterator();
        while (it.hasNext()) {
            o3.c(new J00(it.next(), s10));
        }
        Iterator it2 = s10.g0().iterator();
        while (it2.hasNext()) {
            o3.c(new J00((ML) it2.next(), s10));
        }
    }
}
