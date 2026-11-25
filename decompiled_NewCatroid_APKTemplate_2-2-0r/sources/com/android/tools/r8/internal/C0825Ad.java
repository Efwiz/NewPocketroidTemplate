package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.utils.C3879c3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ad, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0825Ad extends TE {
    static final /* synthetic */ boolean i = true;
    private final AbstractC2151hN a;
    private final PM b;
    private final VE c;
    private final ExecutorService d;
    private final Set e = OU.c();
    private final List f = Collections.synchronizedList(new ArrayList());
    private final List g = Collections.synchronizedList(new ArrayList());
    private C1257Pb h;

    public C0825Ad(AbstractC2151hN abstractC2151hN, PM pm, ExecutorService executorService) {
        this.a = abstractC2151hN;
        this.b = pm;
        this.c = VE.a(abstractC2151hN);
        this.d = executorService;
        this.h = pm.a.p();
    }

    public final void a(C0729q2 c0729q2) {
        boolean zAdd = this.e.add(c0729q2.getType());
        if (!i && !zAdd) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.TE
    public final boolean b(com.android.tools.r8.graph.o5 o5Var) {
        return true;
    }

    public final void c(final com.android.tools.r8.graph.o5 o5Var, final G7 g7) {
        if (!this.e.contains(o5Var.t())) {
            com.android.tools.r8.synthesis.D D = this.b.a.D();
            C0729q2 c0729q2A = o5Var.a();
            D.getClass();
            if (!D.i(c0729q2A.d)) {
                return;
            }
        }
        this.g.add(this.d.submit(new Callable() { // from class: com.android.tools.r8.internal.Ad$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.a(o5Var, g7);
            }
        }));
    }

    @Override // com.android.tools.r8.internal.TE
    public final boolean d(com.android.tools.r8.graph.o5 o5Var) {
        return true;
    }

    public final AbstractC2151hN e() {
        return this.a;
    }

    public final void f() {
        this.h = this.b.a.p();
    }

    public final void g() {
        boolean z = i;
        if (!z && !this.f.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.g.isEmpty()) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.utils.y3 e(com.android.tools.r8.graph.o5 o5Var) {
        return this.b.c(o5Var, com.android.tools.r8.ir.optimize.info.q.b(), this, this.h.a(o5Var));
    }

    @Override // com.android.tools.r8.internal.TE
    public final VE b() {
        return this.c;
    }

    public final void d() {
        ArrayList arrayList;
        while (!this.g.isEmpty()) {
            synchronized (this.g) {
                arrayList = new ArrayList(this.g);
                this.g.clear();
            }
            C3879c3.a(arrayList);
        }
        int size = this.f.size();
        C3879c3.a(this.f);
        if (!i && this.f.size() != size) {
            throw new AssertionError();
        }
        this.f.clear();
    }

    @Override // com.android.tools.r8.internal.TE
    public final C1230Ob a(com.android.tools.r8.graph.o5 o5Var) {
        return this.h.a(o5Var);
    }

    public final void b(com.android.tools.r8.graph.o5 o5Var, G7 g7) {
        C0759u2 c0759u2;
        C0759u2 c0759u3;
        C0759u2 c0759u4;
        PM pm = this.b;
        C1230Ob c1230ObA = this.h.a(o5Var);
        pm.getClass();
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        if ((pm.j.f instanceof ClassFileConsumer) && w0E.p1()) {
            w0E.a(pm.a.Y().b(w0E.Z0()));
        }
        if (w0E.a1() != null && pm.j.a(w0E)) {
            if (pm.a.Y().p) {
                C0759u2 c0759u5 = o5Var.t().e;
                for (C0759u2 c0759u6 : pm.K.b()) {
                    c0759u5.getClass();
                    boolean z = true;
                    if (c0759u5.a(c0759u6.e)) {
                        pm.M.getAndSet(true);
                    } else {
                        Iterator it = pm.K.a().iterator();
                        while (it.hasNext()) {
                            if (!c0759u5.a(((C0759u2) it.next()).e)) {
                                pm.L.getAndSet(true);
                                break;
                            }
                        }
                    }
                    if (pm.M.get() && pm.L.get()) {
                        StringBuilder sb = new StringBuilder("Merging DEX file containing classes with prefix");
                        sb.append(pm.K.b().size() > 1 ? "es " : " ");
                        int i2 = 0;
                        while (i2 < pm.K.b().size()) {
                            sb.append(FormatHelper.QUOTE).append(((C0759u2) pm.K.b().get(i2)).toString().substring(1).replace('/', '.')).append(FormatHelper.QUOTE).append(i2 < pm.K.b().size() - 1 ? ", " : "");
                            i2++;
                        }
                        if (!pm.K.a().isEmpty()) {
                            sb.append(" with other classes, except classes with prefix").append(pm.K.a().size() > 1 ? "es " : " ");
                            int i3 = 0;
                            while (i3 < pm.K.a().size()) {
                                sb.append(FormatHelper.QUOTE).append(((C0759u2) pm.K.a().get(i3)).toString().substring(1).replace('/', '.')).append(FormatHelper.QUOTE).append(i3 < pm.K.a().size() - 1 ? ", " : "");
                                i3++;
                            }
                            sb.append(",");
                        } else {
                            sb.append(" with classes with any other prefixes");
                        }
                        sb.append(" is not allowed: ");
                        int i4 = 11;
                        for (C0729q2 c0729q2 : pm.a.f().e()) {
                            C0759u2 c0759u7 = c0729q2.d.e;
                            c0759u7.getClass();
                            if (!c0759u7.a(c0759u6.e)) {
                                Iterator it2 = pm.K.a().iterator();
                                do {
                                    if (!it2.hasNext()) {
                                        int i5 = i4 - 1;
                                        if (i4 >= 0) {
                                            if (z) {
                                                z = false;
                                            } else {
                                                sb.append(", ");
                                            }
                                            sb.append(c0729q2.d);
                                            i4 = i5;
                                            break;
                                        }
                                        sb.append("..");
                                        break;
                                    }
                                    c0759u3 = (C0759u2) it2.next();
                                    c0759u4 = c0729q2.d.e;
                                    c0759u4.getClass();
                                } while (!c0759u4.a(c0759u3.e));
                            }
                        }
                        sb.append(".");
                        throw new C1367Tb(sb.toString());
                    }
                }
            }
            com.android.tools.r8.utils.A1 a1 = pm.j;
            if (!(a1.f instanceof ClassFileConsumer) && a1.o && w0E.a1().B0()) {
                if (!PM.P && !w0E.a1().B0()) {
                    throw new AssertionError();
                }
            } else {
                pm.b(o5Var, g7, pm.G, this, c1230ObA);
            }
            if (pm.j.f instanceof ClassFileConsumer) {
                return;
            }
            synchronized (pm) {
                com.android.tools.r8.graph.W wA1 = w0E.a1();
                if (!C3012sl.N && !wA1.C0()) {
                    throw new AssertionError();
                }
                C0759u2 c0759u2B = wA1.u0().B();
                if (c0759u2B != null && ((c0759u2 = pm.H) == null || c0759u2B.a(c0759u2) > 0)) {
                    pm.H = c0759u2B;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.utils.y3 a(com.android.tools.r8.graph.o5 o5Var, G7 g7) {
        return this.b.b(o5Var, g7, com.android.tools.r8.ir.optimize.info.q.b(), this, this.h.a(o5Var));
    }

    @Override // com.android.tools.r8.internal.TE
    public final void c(final com.android.tools.r8.graph.o5 o5Var) {
        if (!this.e.contains(o5Var.t())) {
            com.android.tools.r8.synthesis.D D = this.b.a.D();
            C0729q2 c0729q2A = o5Var.a();
            D.getClass();
            if (!D.i(c0729q2A.d)) {
                return;
            }
        }
        if (o5Var.e().s1()) {
            return;
        }
        this.f.add(this.d.submit(new Callable() { // from class: com.android.tools.r8.internal.Ad$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.e(o5Var);
            }
        }));
    }

    public final void a(Iterable iterable) {
        iterable.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Ad$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.TE
    public final AbstractC2755p6 a() {
        throw new C3405y10("Invalid attempt to obtain call-site information in D8");
    }
}
