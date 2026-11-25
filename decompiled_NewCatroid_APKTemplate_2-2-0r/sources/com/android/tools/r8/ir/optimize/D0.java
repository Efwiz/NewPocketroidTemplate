package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C1616aU;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2561ml;
import com.android.tools.r8.internal.C2901r9;
import com.android.tools.r8.internal.C2972s9;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.WH;
import com.android.tools.r8.internal.ZT;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.O3;
import com.android.tools.r8.utils.y3;
import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class D0 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final com.android.tools.r8.androidapi.b b;
    private final com.android.tools.r8.synthesis.D c;

    public D0(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.d();
        this.c = c0702m.D();
    }

    public final void a(o5 o5Var, C2409kl c2409kl, y3 y3Var) {
        y3Var.a("Compute and insert checkcast on return values");
        if (!a(this.a.g(), o5Var, c2409kl).isEmpty()) {
            C2561ml c2561ml = new C2561ml(c2409kl);
            while (c2561ml.hasNext()) {
                C1616aU c1616aUB0 = ((AbstractC2412ko) c2561ml.next()).B0();
                if (c1616aUB0 != null) {
                    C0767v2 c0767v2G = o5Var.G();
                    S10 s10L2 = c1616aUB0.L2();
                    int i = C2972s9.k;
                    C2901r9 c2901r9B = new C2901r9().b(s10L2);
                    C0702m c0702m = this.a;
                    WH whN = s10L2.w().N();
                    c0767v2G.getClass();
                    C2972s9 c2972s9B = ((C2901r9) ((C2901r9) c2901r9B.a(c2409kl, AbstractC3472z00.a(c0767v2G, whN, (C0702m<?>) c0702m), null)).a(c0767v2G).a(c1616aUB0.Y0())).b();
                    c2561ml.a(c2972s9B, (Set) null);
                    c2561ml.add(((ZT) new ZT().a(c1616aUB0.Y0())).b(c2972s9B.b()).b());
                }
            }
        }
        y3Var.c();
    }

    private Set a(final C0680j c0680j, o5 o5Var, C2409kl c2409kl) {
        if (!this.c.a(o5Var.t(), new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.ir.optimize.D0$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.R;
            }
        }) && !this.c.a(o5Var.t(), new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.ir.optimize.D0$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.S;
            }
        })) {
            final C0767v2 c0767v2G = o5Var.G();
            if (!c0767v2G.O0()) {
                return Collections.emptySet();
            }
            if (c0767v2G == this.a.a().Z1) {
                return Collections.emptySet();
            }
            AbstractC0727q0 abstractC0727q0C = c0680j.c(c0767v2G);
            if (abstractC0727q0C != null && abstractC0727q0C.f0()) {
                if (this.b.a(c0767v2G, com.android.tools.r8.androidapi.h.f()).H()) {
                    return Collections.emptySet();
                }
                final Set setC = OU.c();
                final Set setC2 = OU.c();
                c2409kl.e().forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.D0$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(c0680j, c0767v2G, setC, setC2, (C3332x4) obj);
                    }
                });
                return setC2;
            }
            return Collections.emptySet();
        }
        return Collections.emptySet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0680j c0680j, C0767v2 c0767v2, Set set, Set set2, C3332x4 c3332x4) {
        C1616aU c1616aUB0 = c3332x4.m().B0();
        O3 o3A = O3.a(set, c1616aUB0.L2().m());
        while (o3A.c()) {
            S10 s10 = (S10) o3A.f();
            if (s10.i()) {
                o3A.a((Iterable) s10.l().k0());
            }
            AbstractC3472z00 abstractC3472z00W = s10.w();
            if (!abstractC3472z00W.w()) {
                if (!d && !abstractC3472z00W.E() && !abstractC3472z00W.r()) {
                    throw new AssertionError();
                }
            } else {
                C0767v2 c0767v2R = abstractC3472z00W.b().R();
                AbstractC0727q0 abstractC0727q0C = c0680j.c(c0767v2R);
                if (abstractC0727q0C != null && abstractC0727q0C.f0() && c0680j.b(c0767v2R, c0767v2)) {
                    com.android.tools.r8.androidapi.h hVarA = this.b.a(c0767v2R, com.android.tools.r8.androidapi.h.f());
                    com.android.tools.r8.androidapi.h hVarO = this.a.o();
                    if (!hVarA.H() && (!hVarA.y() || !hVarA.b0().a().b(EnumC3891f.G))) {
                        if (hVarA.a(hVarO)) {
                            if (hVarO.a(EnumC3891f.K_WATCH).c() ? true : hVarA.b(EnumC3891f.R).c()) {
                                set2.add(c1616aUB0);
                                return;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
    }
}
