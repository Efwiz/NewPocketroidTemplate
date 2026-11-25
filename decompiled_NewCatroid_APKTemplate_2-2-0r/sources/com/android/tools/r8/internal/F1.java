package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.ir.optimize.C3566j0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3894f2;
import java.util.LinkedHashSet;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F1 {
    static final /* synthetic */ boolean g = true;
    private final AbstractC0681j0 a;
    private final C0702m b;
    private final com.android.tools.r8.utils.A1 c;
    private final com.android.tools.r8.graph.B5 d;
    private final C3566j0 e;
    private final C1612aQ f = C1692bQ.m();

    private F1(AbstractC0681j0 abstractC0681j0, C0702m c0702m, com.android.tools.r8.graph.B5 b5) {
        this.a = abstractC0681j0;
        this.b = c0702m;
        this.c = c0702m.Y();
        this.d = b5;
        this.e = new C3566j0(c0702m, b5, new Predicate() { // from class: com.android.tools.r8.internal.F1$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3566j0.a((com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Boolean a(com.android.tools.r8.graph.o5 o5Var, AbstractC0727q0 abstractC0727q0, Boolean bool) {
        return Boolean.valueOf(!o5Var.t().I0().equals(abstractC0727q0.getType().I0()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(com.android.tools.r8.graph.m5 m5Var) {
        if (((C3733i) this.b.f()).a(m5Var)) {
            a(m5Var);
            com.android.tools.r8.graph.Q2 accessFlags = m5Var.getAccessFlags();
            if (accessFlags.m()) {
                return;
            }
            accessFlags.u();
        }
    }

    public static AbstractC1476Wj a(ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var, AbstractC0681j0 abstractC0681j0, C0702m c0702m, com.android.tools.r8.graph.B5 b5) {
        return new F1(abstractC0681j0, c0702m, b5).a(executorService, y3Var);
    }

    private AbstractC1476Wj a(ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        this.e.a(executorService, y3Var);
        y3Var.a("Phase 2: promoteToPublic");
        C3733i c3733i = (C3733i) this.b.f();
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.F1$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0727q0) obj);
            }
        };
        c3733i.getClass();
        c3733i.a(AbstractC1639am.i(), consumer);
        a(this.b.a().Z1);
        y3Var.c();
        return this.f.a(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC0727q0 abstractC0727q0) {
        a(abstractC0727q0.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2) {
        C0729q2 c0729q2A = C0729q2.a(this.a.c(c0767v2));
        if (c0729q2A != null) {
            a(c0729q2A);
        }
        this.d.a(c0767v2, new Consumer() { // from class: com.android.tools.r8.internal.F1$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0767v2) obj);
            }
        });
    }

    private void a(C0729q2 c0729q2) {
        if (((C3733i) this.b.f()).a((InterfaceC0694k5) c0729q2)) {
            c0729q2.getAccessFlags().u();
        }
        c0729q2.e(new Consumer() { // from class: com.android.tools.r8.internal.F1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((com.android.tools.r8.graph.m5) obj);
            }
        }, CM.b());
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.F1$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(linkedHashSet, (com.android.tools.r8.graph.o5) obj);
            }
        });
        if (!linkedHashSet.isEmpty()) {
            c0729q2.a(linkedHashSet);
        }
        com.android.tools.r8.graph.J3 j3D1 = c0729q2.d1();
        if (j3D1 != null) {
            com.android.tools.r8.graph.J3 j3 = new com.android.tools.r8.graph.J3((j3D1.a() | 1) & (-7), j3D1.b(), j3D1.d(), j3D1.c());
            ListIterator<com.android.tools.r8.graph.J3> listIterator = c0729q2.e1().listIterator();
            while (listIterator.hasNext()) {
                if (c0729q2.d == listIterator.next().b()) {
                    listIterator.set(j3);
                    return;
                }
            }
            throw new C3405y10();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, com.android.tools.r8.graph.o5 o5Var) {
        if (a(o5Var)) {
            set.add(o5Var.e());
        }
    }

    private void a(final com.android.tools.r8.graph.m5 m5Var) {
        final com.android.tools.r8.graph.Q2 accessFlags = m5Var.getAccessFlags();
        com.android.tools.r8.graph.U2 u2B = ((C3733i) this.b.f()).v().b(m5Var.getReference());
        if (this.b.a(m5Var).c((com.android.tools.r8.shaking.K0) this.c) || u2B.m() || u2B.s() || !u2B.c(new Predicate() { // from class: com.android.tools.r8.internal.F1$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return F1.a(accessFlags, m5Var, (com.android.tools.r8.graph.o5) obj);
            }
        }) || accessFlags.h() || accessFlags.V()) {
            return;
        }
        accessFlags.t();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.Q2 q2, com.android.tools.r8.graph.m5 m5Var, com.android.tools.r8.graph.o5 o5Var) {
        return o5Var.e().A1() && o5Var.getAccessFlags().o() == q2.o() && o5Var.a() == m5Var.a();
    }

    private boolean a(final com.android.tools.r8.graph.o5 o5Var) {
        C0725p4 accessFlags = o5Var.getAccessFlags();
        if (accessFlags.m()) {
            return false;
        }
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        if (!((C3733i) this.b.f()).a((InterfaceC0694k5) o5Var) && w0E.O0()) {
            return false;
        }
        if (!o5Var.e().C1() && !accessFlags.l()) {
            if (accessFlags.i()) {
                if (((Boolean) this.e.a(o5Var.a()).a(C3894f2.a().c(o5Var.getReference()), Boolean.FALSE, Boolean.TRUE, new BiFunction() { // from class: com.android.tools.r8.internal.F1$$ExternalSyntheticLambda5
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return F1.a(o5Var, (AbstractC0727q0) obj, (Boolean) obj2);
                    }
                })).booleanValue()) {
                    return false;
                }
                o5Var.getAccessFlags().u();
                return false;
            }
            if (!g && !accessFlags.j()) {
                throw new AssertionError();
            }
            if (accessFlags.o()) {
                o5Var.getAccessFlags().u();
                return false;
            }
            if (o5Var.a().isInterface() || accessFlags.q()) {
                return false;
            }
            if (this.e.a(o5Var.a(), o5Var.getReference())) {
                ((C3733i) this.b.f()).b((InterfaceC0694k5) o5Var);
                return false;
            }
            this.f.a(o5Var.getReference());
            accessFlags.t();
            o5Var.getAccessFlags().u();
            w0E.a(AbstractC3938n2.b);
            return true;
        }
        o5Var.getAccessFlags().u();
        return false;
    }
}
