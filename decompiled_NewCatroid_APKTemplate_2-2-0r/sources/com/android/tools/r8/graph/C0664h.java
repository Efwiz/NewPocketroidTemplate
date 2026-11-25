package com.android.tools.r8.graph;

import com.android.tools.r8.DesugarGraphConsumer;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.synthesis.C3842a;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.h, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0664h implements Q0 {
    static final /* synthetic */ boolean f = true;
    private final AbstractC0681j0 a;
    private final C0722p1 b;
    private final com.android.tools.r8.shaking.N1 c;
    private final com.android.tools.r8.synthesis.D d;
    private final com.android.tools.r8.utils.K e;

    public C0664h(C3842a c3842a, com.android.tools.r8.shaking.N1 n1) {
        this(c3842a.a(), c3842a.c(), n1, new com.android.tools.r8.utils.K());
    }

    public static C0664h a(AbstractC0681j0 abstractC0681j0, com.android.tools.r8.synthesis.y yVar) {
        return a(abstractC0681j0, yVar, com.android.tools.r8.shaking.N1.f());
    }

    public AbstractC0681j0 b() {
        if (!f) {
            c();
        }
        return this.a;
    }

    public final void c() {
        if (!f && this.e.a()) {
            throw new AssertionError();
        }
    }

    public Collection<C0729q2> d() {
        if (!f) {
            c();
        }
        return this.a.d();
    }

    public final Collection e() {
        if (!f) {
            c();
        }
        return this.a.e();
    }

    public final com.android.tools.r8.shaking.N1 f() {
        if (!f) {
            c();
        }
        return this.c;
    }

    public final com.android.tools.r8.synthesis.D g() {
        if (!f) {
            c();
        }
        return this.d;
    }

    public boolean h() {
        if (f) {
            return false;
        }
        c();
        return false;
    }

    public boolean i() {
        if (f) {
            return false;
        }
        c();
        return false;
    }

    public final boolean j() {
        return this.e.a();
    }

    public final void k() {
        this.e.f();
    }

    public final com.android.tools.r8.utils.A1 l() {
        return this.a.d;
    }

    public final void m() {
        this.e.g();
    }

    public C0680j n() {
        if (f) {
            return null;
        }
        c();
        return null;
    }

    public C3733i o() {
        if (f) {
            return null;
        }
        c();
        return null;
    }

    public static C0664h a(AbstractC0681j0 abstractC0681j0, com.android.tools.r8.synthesis.y yVar, com.android.tools.r8.shaking.N1 n1) {
        C3842a c3842aA = com.android.tools.r8.synthesis.D.a(abstractC0681j0, yVar);
        return new C0664h(c3842aA.a(), c3842aA.c(), n1, new com.android.tools.r8.utils.K());
    }

    public static W0 b(C0684j2 c0684j2, o5 o5Var) {
        W0 w0A;
        if (c0684j2.e == o5Var.t() && (w0A = o5Var.a().k.a(c0684j2)) != null && w0A.E0()) {
            return w0A;
        }
        return null;
    }

    public final AbstractC0727q0 d(C0767v2 c0767v2) {
        if (!f) {
            c();
        }
        com.android.tools.r8.synthesis.D d = this.d;
        AbstractC0681j0 abstractC0681j0 = this.a;
        Objects.requireNonNull(abstractC0681j0);
        return d.a(new h$$ExternalSyntheticLambda0(abstractC0681j0), c0767v2).R();
    }

    @Override // com.android.tools.r8.graph.Q0
    public O f(C0767v2 c0767v2) {
        if (!f) {
            c();
        }
        com.android.tools.r8.synthesis.D d = this.d;
        AbstractC0681j0 abstractC0681j0 = this.a;
        Objects.requireNonNull(abstractC0681j0);
        return d.a(new h$$ExternalSyntheticLambda0(abstractC0681j0), c0767v2);
    }

    C0664h(C0673i c0673i, C0664h c0664h) {
        this(c0664h.a, c0664h.d, c0664h.c, c0664h.e);
    }

    @Override // com.android.tools.r8.graph.Q0
    public AbstractC0727q0 c(C0767v2 c0767v2) {
        return d(c0767v2);
    }

    private C0664h(AbstractC0681j0 abstractC0681j0, com.android.tools.r8.synthesis.D d, com.android.tools.r8.shaking.N1 n1, com.android.tools.r8.utils.K k) {
        this.a = abstractC0681j0;
        this.b = abstractC0681j0.e;
        this.c = n1;
        this.d = d;
        this.e = k;
    }

    public C0664h a(u5 u5Var, ExecutorService executorService) {
        boolean z = f;
        if (!z && getClass() != C0664h.class) {
            throw new AssertionError();
        }
        if (!z) {
            c();
        }
        if (!z && u5Var.d() != b()) {
            throw new AssertionError();
        }
        if (u5Var.k()) {
            return this;
        }
        C3842a c3842aA = g().a(u5Var);
        return new C0664h(c3842aA.a(), c3842aA.c(), f().a(u5Var), new com.android.tools.r8.utils.K());
    }

    public C0664h a(com.android.tools.r8.shaking.N1 n1) {
        if (!f) {
            c();
        }
        return new C0664h(this.a, this.d, n1, new com.android.tools.r8.utils.K());
    }

    @Override // com.android.tools.r8.graph.Q0
    public C0722p1 a() {
        if (!f) {
            c();
        }
        return this.b;
    }

    public final void a(Consumer consumer) {
        Iterator<C0729q2> it = d().iterator();
        while (it.hasNext()) {
            it.next().l((Consumer<? super o5>) consumer);
        }
    }

    public final void a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
        boolean z = f;
        if (!z && abstractC0727q0.f0()) {
            throw new AssertionError();
        }
        if (!z && abstractC0727q1.f0()) {
            throw new AssertionError();
        }
        DesugarGraphConsumer desugarGraphConsumer = this.a.d.H1;
        if (desugarGraphConsumer == null) {
            return;
        }
        Origin origin = abstractC0727q1.c;
        List<Origin> listD = g().d(abstractC0727q0.getType());
        if (listD.isEmpty()) {
            Origin origin2 = abstractC0727q0.c;
            if (origin == com.android.tools.r8.origin.c.a() || origin2 == com.android.tools.r8.origin.c.a() || origin2 == origin) {
                return;
            }
            desugarGraphConsumer.accept(origin2, origin);
            return;
        }
        for (Origin origin3 : listD) {
            if (origin != com.android.tools.r8.origin.c.a() && origin3 != com.android.tools.r8.origin.c.a() && origin3 != origin) {
                desugarGraphConsumer.accept(origin3, origin);
            }
        }
    }

    public static W0 a(C0684j2 c0684j2, o5 o5Var) {
        W0 w0A;
        if (c0684j2.e != o5Var.t() || (w0A = o5Var.a().k.a(c0684j2)) == null || w0A.E0()) {
            return null;
        }
        return w0A;
    }

    public AbstractC0677i3 a(C0767v2 c0767v2, Y0 y0, o5 o5Var) {
        if (c0767v2 != o5Var.t()) {
            return AbstractC0677i3.p();
        }
        C0729q2 c0729q2A = o5Var.a();
        T0 t0A = c0729q2A.j.a(y0);
        if (t0A != null) {
            return AbstractC0677i3.a(c0729q2A, c0729q2A, t0A);
        }
        return AbstractC0677i3.C();
    }
}
