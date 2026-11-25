package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class I2 {
    static final /* synthetic */ boolean a = true;

    public static void a(C0702m c0702m, C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.b bVar) {
        C3406y2 c3406y2Q;
        Set setC = OU.c();
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        final InterfaceC2641no interfaceC2641noA = c2409kl.j().a(c2409kl);
        while (interfaceC2641noA.hasNext() && (c3406y2Q = interfaceC2641noA.next().q()) != null) {
            S10 s10B = c3406y2Q.q().b();
            if (!s10B.A()) {
                B1 b1A = bVar.a(c3406y2Q.J2());
                if (b1A.g()) {
                    AbstractC3292wV abstractC3292wVJ = b1A.j();
                    if (abstractC3292wVJ.b(c0702m, c2409kl.h())) {
                        AbstractC2412ko abstractC2412koA = abstractC3292wVJ.a(c0702m, c2409kl.h(), c2409kl.e, c3406y2Q);
                        abstractC2412koA.b(c3406y2Q.Y0());
                        setC.addAll(s10B.a());
                        s10B.g(abstractC2412koA.b());
                        linkedList2.add(abstractC2412koA);
                    }
                }
                if (s10B.w().I()) {
                    AbstractC1154Lf abstractC1154LfB = bVar.b(c3406y2Q.J2());
                    if (abstractC1154LfB.m()) {
                        continue;
                    } else if (abstractC1154LfB.h()) {
                        if (!a) {
                            throw new AssertionError();
                        }
                    } else if (abstractC1154LfB.f().f()) {
                        C0978Fc c0978FcI = c2409kl.i();
                        c0978FcI.b(c3406y2Q.Y0());
                        setC.addAll(s10B.a());
                        s10B.g(c0978FcI.b());
                        linkedList2.add(c0978FcI);
                    } else if (!abstractC1154LfB.k()) {
                        C1207Nf c1207NfA = abstractC1154LfB.a();
                        AbstractC3472z00 abstractC3472z00W = s10B.w();
                        c1207NfA.getClass();
                        if (c1207NfA.b(c0702m, C1207Nf.a((C0702m<C3733i>) c0702m, abstractC3472z00W))) {
                            S10 s10A = c2409kl.a(s10B.w());
                            setC.addAll(s10B.a());
                            s10B.g(s10A);
                            N3 n3 = new N3(new L3(c1207NfA), null, s10A, s10B, c3406y2Q, c0702m);
                            n3.b(c3406y2Q.Y0());
                            linkedList.add(n3);
                            s10B = s10A;
                        }
                        if (!a && !s10B.w().I()) {
                            throw new AssertionError();
                        }
                        if (abstractC1154LfB.f().e() && !s10B.w().x()) {
                            S10 s10A2 = c2409kl.a(s10B.w().d().P());
                            setC.addAll(s10B.a());
                            s10B.g(s10A2);
                            N3 n3A = N3.a(s10A2, s10B, c3406y2Q, c0702m);
                            n3A.b(c3406y2Q.Y0());
                            linkedList.add(n3A);
                        }
                    } else if (!s10B.w().x()) {
                        S10 s10A3 = c2409kl.a(s10B.w().d().P());
                        s10B.a(s10A3, setC);
                        N3 n3A2 = N3.a(s10A3, s10B, c3406y2Q, c0702m);
                        n3A2.b(c3406y2Q.Y0());
                        linkedList.add(n3A2);
                    }
                } else {
                    continue;
                }
            }
        }
        boolean z = a;
        if (!z && interfaceC2641noA.j().m1()) {
            throw new AssertionError();
        }
        interfaceC2641noA.previous();
        if (!z && !interfaceC2641noA.j().m1()) {
            throw new AssertionError();
        }
        linkedList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.I2$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC2641noA.add((N3) obj);
            }
        });
        linkedList2.forEach(new I2$$ExternalSyntheticLambda1(interfaceC2641noA));
        if (setC.isEmpty()) {
            return;
        }
        new C3030t00(c0702m, false).a((Iterable) setC);
    }
}
