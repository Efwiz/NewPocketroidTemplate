package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3546c0;
import com.android.tools.r8.ir.optimize.EnumC3542a0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.AbstractC3899g2;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3984w2;
import com.android.tools.r8.utils.InterfaceC3890e3;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1132Kj {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final VP b;
    private final boolean c;
    private final IdentityHashMap d = new IdentityHashMap();

    C1132Kj(C0702m c0702m, VP vp) {
        this.a = c0702m;
        this.b = vp;
        boolean zA = a();
        this.c = zA;
        if (!e && !zA) {
            throw new AssertionError();
        }
    }

    private boolean a() {
        AbstractC0727q0 abstractC0727q0D = ((C0680j) this.a.f()).d(this.b.j);
        AbstractC0727q0 abstractC0727q0D2 = ((C0680j) this.a.f()).d(this.b.k);
        if (abstractC0727q0D == null && abstractC0727q0D2 == null) {
            return false;
        }
        if (AbstractC3899g2.a(abstractC0727q0D, new Predicate() { // from class: com.android.tools.r8.internal.Kj$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1132Kj.a((AbstractC0727q0) obj);
            }
        }, true)) {
            this.a.Y().e.c("Unexpected implementation of `" + this.b.j.r0() + "`: disabling aggressive protobuf builder optimization.");
            return false;
        }
        if (!AbstractC3899g2.a(abstractC0727q0D2, new Predicate() { // from class: com.android.tools.r8.internal.Kj$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1132Kj.b((AbstractC0727q0) obj);
            }
        }, true)) {
            return true;
        }
        this.a.Y().e.c("Unexpected implementation of `" + this.b.k.r0() + "`: disabling aggressive protobuf builder optimization.");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(AbstractC0727q0 abstractC0727q0) {
        return abstractC0727q0.Y().d(new Kj$$ExternalSyntheticLambda3());
    }

    public final AbstractC2797pg b() {
        return new C1072Ij(this, OU.c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC0727q0 abstractC0727q0) {
        return abstractC0727q0.Y().d(new Kj$$ExternalSyntheticLambda3());
    }

    public final boolean a(C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var, BooleanSupplier booleanSupplier) {
        C0767v2 c0767v2;
        if (!this.c) {
            return false;
        }
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        if (this.b.a(w0E)) {
            VP vp = this.b;
            vp.getClass();
            C0767v2 c0767v3 = c0729q2.f;
            C0767v2 c0767v4 = vp.j;
            if ((c0767v3 == c0767v4 || c0767v3 == vp.k) && (c0767v2 = c0729q2.d) != c0767v4 && c0767v2 != vp.k && booleanSupplier.getAsBoolean()) {
                if (!e && this.d.containsKey(c0729q2) && ((com.android.tools.r8.graph.o5) this.d.get(c0729q2)).e() != w0E) {
                    throw new AssertionError();
                }
                this.d.put(c0729q2, o5Var);
                return true;
            }
        }
        return false;
    }

    public final void a(final C0702m c0702m, com.android.tools.r8.utils.y3 y3Var, ExecutorService executorService) {
        if (this.d.isEmpty()) {
            return;
        }
        y3Var.a("Remove dead builder references");
        final C3733i c3733i = (C3733i) c0702m.f();
        final C3012sl c3012sl = new C3012sl(c0702m);
        C3879c3.a(this.d, new InterfaceC3890e3() { // from class: com.android.tools.r8.internal.Kj$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3890e3
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c3733i, c0702m, c3012sl, (C0729q2) obj, (com.android.tools.r8.graph.o5) obj2);
            }
        }, executorService);
        this.d.clear();
        y3Var.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3733i c3733i, C0702m c0702m, C3012sl c3012sl, C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        if (c3733i.e(c0729q2)) {
            return;
        }
        a(c0702m, c0729q2, o5Var, c3012sl);
    }

    private void a(C0702m c0702m, final C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var, C3012sl c3012sl) {
        C0684j2 c0684j2;
        C2409kl c2409klA = o5Var.a((C0702m<?>) c0702m);
        c2409klA.getClass();
        C2561ml c2561ml = new C2561ml(c2409klA);
        boolean z = e;
        if (!z) {
            C0767v2 c0767v2 = c0729q2.f;
            VP vp = this.b;
            if (c0767v2 != vp.j && c0767v2 != vp.k) {
                throw new AssertionError();
            }
        }
        com.android.tools.r8.graph.Y0 y0A = this.b.a(o5Var.a());
        S10 s10A = c2409klA.a((AbstractC3472z00) C1229Oa.b(c0729q2.f, WH.b(), (C0702m<? extends C0680j>) c0702m));
        S10 s10A2 = c2409klA.a((AbstractC3472z00) C1229Oa.b(y0A.h, WH.i(), (C0702m<? extends C0680j>) c0702m));
        DG dg = (DG) c2561ml.a(new Predicate() { // from class: com.android.tools.r8.internal.Kj$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1132Kj.a(c0729q2, (AbstractC2412ko) obj);
            }
        });
        if (!z && dg == null) {
            throw new AssertionError();
        }
        c2561ml.a(new DG(c0729q2.f, s10A), (Set) null);
        C1166Ls c1166Ls = (C1166Ls) c2561ml.a(new Predicate() { // from class: com.android.tools.r8.internal.Kj$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1132Kj.a((AbstractC2412ko) obj);
            }
        });
        if (!z && c1166Ls == null) {
            throw new AssertionError();
        }
        c2561ml.a(new QW(y0A, s10A2), (Set) null);
        c2561ml.a(c1166Ls.Y0());
        C0767v2 c0767v3 = c0729q2.f;
        VP vp2 = this.b;
        if (c0767v3 == vp2.j) {
            c0684j2 = vp2.r.a;
        } else {
            c0684j2 = vp2.s.b;
        }
        c2561ml.add(new C1166Ls(c0684j2, null, AbstractC1639am.a(s10A, s10A2), false));
        c3012sl.a(c2409klA, com.android.tools.r8.ir.optimize.info.r.b(), com.android.tools.r8.utils.y3.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0729q2 c0729q2, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.p2() && abstractC2412ko.t0().h == c0729q2.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC2412ko abstractC2412ko) {
        if (e || abstractC2412ko.W1() || abstractC2412ko.B1()) {
            return abstractC2412ko.W1();
        }
        throw new AssertionError();
    }

    public static void a(C0702m c0702m, com.android.tools.r8.graph.B5 b5, C3984w2 c3984w2, Set set, Set set2, Set set3, Set set4) {
        C1104Jj c1104Jj = new C1104Jj(c0702m, c3984w2, set, set2, set3, set4);
        c1104Jj.a();
        c1104Jj.f.add(c1104Jj.b.q.a);
        C0767v2 c0767v2 = c1104Jj.b.m;
        c1104Jj.d.add(c0767v2);
        c1104Jj.e.add(c0767v2);
        c1104Jj.a(b5);
        c1104Jj.f.add(c1104Jj.b.s.a);
        C0767v2 c0767v3 = c1104Jj.b.j;
        c1104Jj.d.add(c0767v3);
        c1104Jj.e.add(c0767v3);
        C0767v2 c0767v4 = c1104Jj.b.k;
        c1104Jj.d.add(c0767v4);
        c1104Jj.e.add(c0767v4);
    }

    public final void a(ConcurrentHashMap concurrentHashMap) {
        C2768pH c2768pH = (C2768pH) concurrentHashMap.get(this.b.r.a);
        if (c2768pH != null) {
            ArrayList arrayList = new ArrayList();
            for (C2768pH c2768pH2 : c2768pH.e()) {
                VP vp = this.b;
                com.android.tools.r8.graph.W0 w0A = c2768pH2.a();
                vp.getClass();
                C0684j2 reference = w0A.getReference();
                TP tp = vp.q;
                if (reference == tp.b || reference == tp.c) {
                    arrayList.add(c2768pH2);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((C2768pH) it.next()).f(c2768pH);
            }
        }
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl, C1555Zg c1555Zg, com.android.tools.r8.ir.optimize.info.n nVar, TE te, C3546c0 c3546c0) {
        a(c2409kl);
        c3546c0.a(o5Var, c2409kl, nVar, te, com.android.tools.r8.utils.y3.b(), new BP(this.a, new C2634ni(EnumC3542a0.f)));
        if (c1555Zg != null) {
            c1555Zg.b(c2409kl);
        }
    }

    private void a(C2409kl c2409kl) {
        C1229Oa c1229OaB;
        Set setC = OU.c();
        c2409kl.getClass();
        C2561ml c2561ml = new C2561ml(c2409kl);
        while (true) {
            C2972s9 c2972s9 = (C2972s9) c2561ml.a(new Kj$$ExternalSyntheticLambda4());
            if (c2972s9 != null) {
                if (c2972s9.J2() == this.b.i) {
                    S10 s10M = c2972s9.e().m();
                    if (!s10M.i() && s10M.b.f2()) {
                        C2115gt c2115gtI0 = s10M.b.i0();
                        C0684j2 c0684j2S2 = c2115gtI0.S2();
                        if (!this.b.a(c0684j2S2)) {
                            TP tp = this.b.q;
                            if (c0684j2S2 == tp.b || c0684j2S2 == tp.c) {
                            }
                        }
                        if (!e && c0684j2S2.h.f.a[0] != this.b.n) {
                            throw new AssertionError();
                        }
                        S10 s10 = (S10) ((ArrayList) c2115gtI0.J2()).get(1);
                        UP up = this.b.t;
                        up.getClass();
                        S10 s10M2 = s10.m();
                        if (!s10M2.i()) {
                            AbstractC2412ko abstractC2412ko = s10M2.b;
                            abstractC2412ko.getClass();
                            if ((abstractC2412ko instanceof QW) && s10M2.b.F0().getField() == up.f && (c1229OaB = c2115gtI0.U2().a(this.a).b()) != null) {
                                C0680j c0680j = (C0680j) this.a.f();
                                C0767v2 c0767v2R = c1229OaB.R();
                                if (c0680j.b(c0767v2R, this.b.i)) {
                                    c2561ml.a(new C3133uU(c2409kl.a(c2972s9.j(), c1229OaB.a(WH.i())), c2972s9.e(), c0767v2R), setC);
                                }
                            }
                        }
                    }
                }
            } else {
                if (setC.isEmpty()) {
                    return;
                }
                new C3030t00(this.a, false).a((Iterable) setC);
                return;
            }
        }
    }
}
