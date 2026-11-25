package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.O2$$ExternalSyntheticLambda4;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.C3894f2;
import com.android.tools.r8.utils.C3912j0;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.c4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3701c4 {
    static final /* synthetic */ boolean i = true;
    private final C0729q2 a;
    private final Collection b;
    private final S2 c;
    private final Map d;
    private final C3712e3 e;
    private final HashSet f;
    private final Set g;
    final /* synthetic */ C3707d4 h;

    private C3701c4(C3707d4 c3707d4, C0729q2 c0729q2, List list, S2 s2, Map map, C3712e3 c3712e3) {
        this.h = c3707d4;
        this.f = OU.b();
        this.g = OU.c();
        boolean z = i;
        if (!z && !s2.A()) {
            throw new AssertionError();
        }
        if (!z && s2.t().G().d) {
            throw new AssertionError();
        }
        this.a = c0729q2;
        this.b = list;
        this.c = s2;
        this.d = map;
        this.e = c3712e3;
    }

    final void a(AbstractC3714f abstractC3714f) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.h.a.c(c0767v2);
        if (abstractC0727q0C == null || abstractC0727q0C.I1() || !this.g.add(c0767v2)) {
            return;
        }
        for (C0767v2 c0767v3 : abstractC0727q0C.g.a) {
            a(c0767v3);
        }
        if (abstractC0727q0C.isInterface()) {
            if (this.a == abstractC0727q0C) {
                return;
            }
            abstractC0727q0C.b(new Consumer() { // from class: com.android.tools.r8.shaking.c4$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((AbstractC0748t0) obj);
                }
            }, new O2$$ExternalSyntheticLambda4());
            return;
        }
        C0767v2 c0767v4 = abstractC0727q0C.f;
        if (c0767v4 != null) {
            a(c0767v4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC0748t0 abstractC0748t0) {
        if (this.f.add(C3894f2.a().c(abstractC0748t0.getReference()))) {
            for (C3775o3 c3775o3 : this.b) {
                if (c3775o3.a(abstractC0748t0, this.h.a, new Consumer() { // from class: com.android.tools.r8.shaking.c4$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a((AbstractC3714f) obj);
                    }
                }, this.h.A)) {
                    a(abstractC0748t0, c3775o3);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0072  */
    private void a(AbstractC0748t0 abstractC0748t0, final C3775o3 c3775o3) {
        final o5 o5Var;
        I4.c<?> cVarO = ((C0680j) this.h.a.f()).g(this.a, abstractC0748t0.getReference()).o();
        if (cVarO == null || !cVarO.y() || cVarO.d() == this.a || cVarO.d().I1() || !cVarO.d().isInterface()) {
            return;
        }
        final o5 o5VarS = cVarO.s();
        C3707d4 c3707d4 = this.h;
        C0729q2 c0729q2 = this.a;
        com.android.tools.r8.graph.W0 w0E = o5VarS.e();
        if (!c3707d4.a.Y().Y0()) {
            if (com.android.tools.r8.utils.B.b(w0E.J0(), c0729q2.g.a)) {
                C0684j2 reference = o5VarS.getReference();
                C0729q2 c0729q3 = this.a;
                C0722p1 c0722p1A = this.h.a.a();
                reference.getClass();
                C0684j2 c0684j2B = reference.b(c0729q3.getType(), c0722p1A);
                o5Var = (o5) this.h.x.computeIfAbsent(c0684j2B, new Function() { // from class: com.android.tools.r8.shaking.c4$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a(o5VarS, (C0684j2) obj);
                    }
                });
                if (!i && !c0684j2B.equals(o5Var.getReference())) {
                    throw new AssertionError();
                }
            } else {
                o5Var = o5VarS;
            }
        } else {
            C0684j2 reference2 = o5VarS.getReference();
            C0729q2 c0729q4 = this.a;
            C0722p1 c0722p1A2 = this.h.a.a();
            reference2.getClass();
            C0684j2 c0684j2B2 = reference2.b(c0729q4.getType(), c0722p1A2);
            o5Var = (o5) this.h.x.computeIfAbsent(c0684j2B2, new Function() { // from class: com.android.tools.r8.shaking.c4$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(o5VarS, (C0684j2) obj);
                }
            });
            if (!i) {
                throw new AssertionError();
            }
        }
        this.h.y.add(new C3835z(o5Var, o5VarS, new Consumer() { // from class: com.android.tools.r8.shaking.c4$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o5Var, c3775o3, (C3707d4) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public o5 a(o5 o5Var, C0684j2 c0684j2) {
        C0729q2 c0729q2 = this.a;
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        C0729q2 c0729q3 = this.a;
        C0702m c0702m = this.h.a;
        w0E.getClass();
        return new o5(c0729q2, w0E.a(c0702m, c0729q3, C3912j0.b()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(o5 o5Var, C3775o3 c3775o3, C3707d4 c3707d4) {
        c3707d4.a(o5Var, this.c, c3775o3, C3707d4.a(o5Var.e(), this.d), this.e);
    }
}
