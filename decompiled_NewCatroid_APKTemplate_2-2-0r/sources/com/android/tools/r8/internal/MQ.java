package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class MQ {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final C3012sl b;

    private MQ(C0702m c0702m) {
        this.a = c0702m;
        this.b = new C3012sl(c0702m);
    }

    public static MQ a(C0702m c0702m) {
        if (c0702m.q() && c0702m.Y().Y0() && c0702m.Y().n1.u0) {
            return new MQ(c0702m);
        }
        return null;
    }

    public final void a() {
        for (C0684j2 c0684j2 : ((C3733i) this.a.f()).K) {
            AbstractC0727q0 abstractC0727q0A = this.a.a(c0684j2.B0());
            boolean z = c;
            if (!z && !abstractC0727q0A.e0()) {
                throw new AssertionError();
            }
            com.android.tools.r8.graph.o5 o5VarF = abstractC0727q0A.a0().f(c0684j2);
            if (!z && o5VarF == null) {
                throw new AssertionError();
            }
            com.android.tools.r8.graph.W wA1 = o5VarF.e().a1();
            C0702m c0702m = this.a;
            Origin origin = o5VarF.getOrigin();
            wA1.getClass();
            C2409kl c2409klA = wA1.a(o5VarF, c0702m, origin, new CE.a(c0702m.Y()));
            B4 b4S = c2409klA.s();
            boolean z2 = false;
            while (b4S.hasNext()) {
                InterfaceC2641no interfaceC2641noA = b4S.next().a(c2409klA);
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next = interfaceC2641noA.next();
                    next.getClass();
                    if (next instanceof LQ) {
                        LQ lqZ0 = next.z0();
                        com.android.tools.r8.graph.o5 o5VarH = c2409klA.h();
                        List listH1 = lqZ0.h1();
                        com.android.tools.r8.graph.Y0[] y0ArrJ2 = lqZ0.J2();
                        if (!c && ((ArrayList) listH1).size() != y0ArrJ2.length) {
                            throw new AssertionError();
                        }
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < y0ArrJ2.length; i++) {
                            C3733i c3733i = (C3733i) this.a.f();
                            com.android.tools.r8.graph.Y0 y0B = this.a.E().b((AbstractC1476Wj) null, y0ArrJ2[i]);
                            c3733i.getClass();
                            if (c3733i.a(y0B.e, y0B, o5VarH).B()) {
                                arrayList.add((S10) ((ArrayList) listH1).get(i));
                            }
                        }
                        C0978Fc c0978FcA = c2409klA.a(arrayList.size(), (com.android.tools.r8.graph.X) null);
                        AbstractC1915eM abstractC1915eMU = this.a.Y().W0 ? AbstractC1915eM.u() : lqZ0.Y0();
                        c0978FcA.b(abstractC1915eMU);
                        interfaceC2641noA.previous();
                        interfaceC2641noA.add(c0978FcA);
                        interfaceC2641noA.next();
                        AG ag = new AG(lqZ0.b(), c0978FcA.b(), this.a.a().c2);
                        ag.b(lqZ0.Y0());
                        interfaceC2641noA.d(ag);
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            C0978Fc c0978FcA2 = c2409klA.a(i2, (com.android.tools.r8.graph.X) null);
                            c0978FcA2.b(abstractC1915eMU);
                            interfaceC2641noA.add(c0978FcA2);
                            C1736c3 c1736c3 = new C1736c3(EnumC2523mE.OBJECT, ag.b(), c0978FcA2.b(), (S10) arrayList.get(i2));
                            interfaceC2641noA.add(c1736c3);
                            c1736c3.b(lqZ0.Y0());
                        }
                        if (ag.h().D()) {
                            a(c2409klA, ag.h(), b4S);
                        }
                        z2 = true;
                    }
                }
            }
            if (!c && !z2) {
                throw new AssertionError();
            }
            this.b.a(c2409klA, com.android.tools.r8.ir.optimize.info.q.b(), com.android.tools.r8.utils.y3.b());
        }
    }

    private void a(C2409kl c2409kl, C3332x4 c3332x4, B4 b4) {
        InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
        while (c3332x4 != null && interfaceC2641noA.hasNext()) {
            if (((AbstractC2412ko) interfaceC2641noA.a(new A4$$ExternalSyntheticLambda5())) != null) {
                C3332x4 c3332x4A = interfaceC2641noA.a(c2409kl, b4);
                b4.previous();
                c3332x4A.a(c2409kl, b4, c3332x4, this.a.Y());
                C3332x4 next = b4.next();
                if (!c && next != c3332x4A) {
                    throw new AssertionError();
                }
                interfaceC2641noA = c3332x4A.a(c2409kl);
                c3332x4 = c3332x4A;
            } else {
                if (!c && interfaceC2641noA.hasNext()) {
                    throw new AssertionError();
                }
                interfaceC2641noA = null;
                c3332x4 = null;
            }
        }
    }
}
