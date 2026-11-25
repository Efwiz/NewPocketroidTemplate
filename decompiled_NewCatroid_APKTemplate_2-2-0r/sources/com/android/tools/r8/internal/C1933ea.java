package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3546c0;
import com.android.tools.r8.ir.optimize.C3579q;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ea, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1933ea {
    static final /* synthetic */ boolean b = true;
    private final ConcurrentHashMap a = new ConcurrentHashMap();

    public final void a(final C0702m c0702m, com.android.tools.r8.ir.optimize.F f, ZX zx, final C1555Zg c1555Zg, final com.android.tools.r8.graph.o5 o5Var, final C2409kl c2409kl, final com.android.tools.r8.ir.optimize.info.n nVar, final TE te, C1230Ob c1230Ob, final C3546c0 c3546c0, com.android.tools.r8.utils.P1 p1) {
        C0729q2 c0729q2A;
        C0767v2 c0767v2;
        ArrayList arrayListA = AbstractC2763pC.a(c2409kl.b(new Predicate() { // from class: com.android.tools.r8.internal.ea$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1933ea.a((AbstractC2412ko) obj);
            }
        }));
        boolean z = false;
        boolean z2 = false;
        while (true) {
            Iterator it = arrayListA.iterator();
            boolean z3 = z;
            boolean zA = z2;
            boolean z4 = false;
            while (it.hasNext()) {
                AbstractC2412ko abstractC2412ko = (AbstractC2412ko) it.next();
                C3157un c3157un = new C3157un(c0702m, c3546c0, new Function() { // from class: com.android.tools.r8.internal.ea$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a(c0702m, (C0729q2) obj);
                    }
                }, te, o5Var, abstractC2412ko);
                EnumC1851da enumC1851daF = c3157un.f();
                EnumC1851da enumC1851da = EnumC1851da.a;
                if (enumC1851daF != enumC1851da) {
                    it.remove();
                } else if (c3157un.e() != enumC1851da) {
                    it.remove();
                } else if (c3157un.a(p1) != null) {
                    continue;
                } else {
                    C3387xn c3387xn = new C3387xn(c0702m, o5Var, c2409kl, te);
                    if (new C2010fa(c0702m, c3387xn, c3157un.d()).a(c2409kl, c3157un.b(), c3157un.a(), c3157un.c())) {
                        it.remove();
                    } else {
                        z4 = true;
                        if (c0702m.Z() != null && abstractC2412ko.p2() && (c0729q2A = C0729q2.a(c0702m.c(abstractC2412ko.t0().h))) != null) {
                            VP vp = c0702m.Z().h;
                            vp.getClass();
                            C0767v2 c0767v3 = c0729q2A.f;
                            C0767v2 c0767v4 = vp.j;
                            if ((c0767v3 == c0767v4 || c0767v3 == vp.k) && (c0767v2 = c0729q2A.d) != c0767v4 && c0767v2 != vp.k) {
                                z3 = true;
                            }
                        }
                        Set setC = OU.c();
                        C3579q c3579q = new C3579q(c0702m, c2409kl, setC);
                        try {
                            zA |= c3157un.a(c2409kl, setC, c3579q, c3387xn);
                        } catch (C3084tn unused) {
                            if (!b) {
                                throw new AssertionError();
                            }
                            zA = true;
                        }
                        c3579q.a((Set) null);
                        c2409kl.a((C2102gl) null, setC);
                        c3579q.a();
                        if (!b && !c2409kl.b((C0702m<?>) c0702m)) {
                            throw new AssertionError();
                        }
                        it.remove();
                    }
                }
            }
            if (!z4) {
                if (z3) {
                    c0702m.c(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.ea$$ExternalSyntheticLambda2
                        @Override // com.android.tools.r8.utils.InterfaceC3905h3
                        public final void accept(Object obj) {
                            ((C1132Kj) obj).a(o5Var, c2409kl, c1555Zg, nVar, te, c3546c0);
                        }
                    });
                }
                if (zA) {
                    f.a(c2409kl, o5Var, te, c1230Ob);
                    f.n(c2409kl);
                    if (c0702m.Y().W0 || o5Var.a().f(c0702m)) {
                        return;
                    }
                    zx.a(c2409kl);
                    zx.b(c2409kl);
                    return;
                }
                return;
            }
            z = z3;
            z2 = zA;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.p2() || (abstractC2412ko instanceof QW);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:28:0x0081 A[EDGE_INSN: B:28:0x0081->B:30:0x0086 BREAK  A[LOOP:0: B:13:0x0031->B:44:0x0031]] */
    public EnumC1851da a(C0702m c0702m, C0729q2 c0729q2) {
        EnumC1851da enumC1851da;
        EnumC1851da enumC1851da2 = (EnumC1851da) this.a.get(c0729q2);
        if (enumC1851da2 != null) {
            return enumC1851da2;
        }
        if (c0729q2 != null && !c0729q2.x1() && !c0729q2.isInterface() && ((C3733i) c0702m.f()).a(c0729q2)) {
            C0722p1 c0722p1A = c0702m.a();
            Iterator<com.android.tools.r8.graph.W0> it = c0729q2.P1().iterator();
            while (true) {
                if (it.hasNext()) {
                    com.android.tools.r8.graph.W0 next = it.next();
                    if (next.getReference().f == c0722p1A.m0 && next.getReference().h == c0722p1A.g4.h.h) {
                        enumC1851da = EnumC1851da.b;
                        break;
                    }
                } else {
                    if (c0729q2.b((C0702m<?>) c0702m)) {
                        enumC1851da = EnumC1851da.b;
                        break;
                    }
                    if (!c0702m.Y().n1.z) {
                        com.android.tools.r8.synthesis.D D = c0702m.D();
                        D.getClass();
                        if (D.i(c0729q2.d)) {
                            enumC1851da = EnumC1851da.b;
                            break;
                        }
                        enumC1851da = EnumC1851da.a;
                        break;
                    }
                    enumC1851da = EnumC1851da.a;
                    break;
                }
            }
        } else {
            enumC1851da = EnumC1851da.b;
        }
        EnumC1851da enumC1851da3 = enumC1851da;
        EnumC1851da enumC1851da4 = (EnumC1851da) this.a.putIfAbsent(c0729q2, enumC1851da3);
        if (b || enumC1851da4 == null || enumC1851da4 == enumC1851da3) {
            return enumC1851da4 == null ? enumC1851da3 : enumC1851da4;
        }
        throw new AssertionError();
    }
}
