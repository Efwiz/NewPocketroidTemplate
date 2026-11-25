package com.android.tools.r8.naming;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0762u4;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0793w4$$ExternalSyntheticLambda0;
import com.android.tools.r8.graph.G5;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.C3011sk;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.InterfaceC3877c1;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.y3;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class V {
    static final /* synthetic */ boolean j = true;
    private final C0702m a;
    private final S b;
    private final IdentityHashMap c = new IdentityHashMap();
    private final U d = new U(this);
    private final C3011sk e;
    private final IdentityHashMap f;
    private final IdentityHashMap g;
    private final X h;
    private final C3604a0 i;

    V(C0702m c0702m, S s) {
        C3011sk c3011skL = C3011sk.l();
        this.e = c3011skL;
        IdentityHashMap identityHashMap = new IdentityHashMap();
        this.f = identityHashMap;
        this.g = new IdentityHashMap();
        this.a = c0702m;
        this.b = s;
        C3604a0 c3604a0A = C3604a0.a(c());
        this.i = c3604a0A;
        c3011skL.put(null, c3604a0A);
        X xA = X.a(b(), s, c3604a0A);
        this.h = xA;
        identityHashMap.put(null, xA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(C0684j2 c0684j2) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object b(C0684j2 c0684j2) {
        return c0684j2.h.f;
    }

    private Function c() {
        this.a.Y().D0().getClass();
        return new Function() { // from class: com.android.tools.r8.naming.V$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return V.b((C0684j2) obj);
            }
        };
    }

    private void d() {
        if (this.a.Y().D0().x()) {
            ((C3733i) this.a.f()).b(new Consumer() { // from class: com.android.tools.r8.naming.V$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C0754u0) obj);
                }
            });
        }
    }

    private void e() {
        a(this.a.a().Z1, this.a.a().Z1, this.i);
        G5.a(this.a).a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.naming.V$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((AbstractC0727q0) obj);
            }
        });
    }

    private Function b() {
        return this.a.Y().f instanceof ClassFileConsumer ? c() : new Function() { // from class: com.android.tools.r8.naming.V$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return V.a((C0684j2) obj);
            }
        };
    }

    final T a(List list, B5 b5, ExecutorService executorService, y3 y3Var) {
        y3Var.a("Phase 1");
        e();
        y3Var.c();
        y3Var.a("Phase 2");
        H h = new H(this.a, this.d, b5);
        y3Var.c();
        y3Var.a("Phase 3");
        h.a(y3Var, list);
        y3Var.c();
        y3Var.a("Phase 4");
        a();
        d();
        y3Var.c();
        y3Var.a("Phase 5: non-rebound references");
        a(executorService);
        y3Var.c();
        return new T(this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void b(AbstractC0727q0 abstractC0727q0) {
        C0767v2 c0767v2 = abstractC0727q0.d;
        C0767v2 c0767v3 = (C0767v2) this.g.getOrDefault(abstractC0727q0.f, c0767v2);
        if (c0767v3 != c0767v2 || abstractC0727q0.e0()) {
            C0767v2 c0767v4 = (C0767v2) this.g.put(abstractC0727q0.d, c0767v3);
            if (!j && c0767v4 != null) {
                throw new AssertionError();
            }
        }
        a(c0767v2, c0767v3, (C3604a0) this.e.getOrDefault(abstractC0727q0.f, this.i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public X b(C0767v2 c0767v2) {
        X xB;
        X x = (X) this.f.get(c0767v2);
        if (x != null) {
            return x;
        }
        if (c0767v2 == this.a.a().Z1) {
            xB = this.h;
        } else {
            AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
            if (abstractC0727q0C == null) {
                xB = b(this.a.a().Z1);
            } else {
                xB = b(abstractC0727q0C.f);
            }
        }
        C3604a0 c3604a0A = a(c0767v2);
        if (!j && c3604a0A == null) {
            throw new AssertionError("Could not find reservation state for " + c0767v2.toString());
        }
        X xA = xB.a(c3604a0A);
        this.f.put(c0767v2, xA);
        return xA;
    }

    private void a() {
        ((G5) G5.a(this.a).a()).a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.naming.V$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0727q0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final AbstractC0727q0 abstractC0727q0) {
        C0767v2 c0767v2 = abstractC0727q0.d;
        final C3604a0 c3604a0 = (C3604a0) this.e.get(this.g.getOrDefault(c0767v2, c0767v2));
        if (!j && c3604a0 == null) {
            throw new AssertionError("Could not find reservation state for " + c0767v2.toString());
        }
        X x = (X) this.f.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.naming.V$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(abstractC0727q0, c3604a0, (C0767v2) obj);
            }
        });
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (abstractC0727q0C == null || !this.b.a(abstractC0727q0C)) {
            return;
        }
        for (W0 w0 : abstractC0727q0C.I0()) {
            if (!w0.A1()) {
                C0759u2 c0759u2A = this.b.a(abstractC0727q0C, w0);
                if (c0759u2A == null || c0759u2A == w0.K0()) {
                    c0759u2A = x.a(w0);
                }
                if (w0.K0() != c0759u2A) {
                    this.c.put(w0.getReference(), c0759u2A);
                }
                x.a(c0759u2A, w0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ X a(AbstractC0727q0 abstractC0727q0, C3604a0 c3604a0, C0767v2 c0767v2) {
        return ((X) this.f.getOrDefault(abstractC0727q0.f, this.h)).a(c3604a0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0754u0 c0754u0) {
        for (W0 w0 : c0754u0.M1()) {
            C0759u2 c0759u2A = this.b.a(c0754u0, w0);
            if (c0759u2A != null && c0759u2A != w0.getReference().f) {
                this.c.put(w0.getReference(), c0759u2A);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void a(C0767v2 c0767v2, C0767v2 c0767v3, final C3604a0 c3604a0) {
        C3604a0 c3604a1 = (C3604a0) this.e.computeIfAbsent(c0767v3, new Function() { // from class: com.android.tools.r8.naming.V$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return V.a(c3604a0, (C0767v2) obj);
            }
        });
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (abstractC0727q0C != null) {
            for (W0 w0 : this.a.Y().n1.n.a(abstractC0727q0C.M1())) {
                C0759u2 c0759u2A = this.b.a(abstractC0727q0C, w0);
                if (c0759u2A != null) {
                    c3604a1.getClass();
                    try {
                        ((Z) c3604a1.c(w0.getReference())).a(c0759u2A, w0);
                    } catch (AssertionError e) {
                        throw new RuntimeException(String.format("Assertion error when trying to reserve name '%s' for method '%s'", c0759u2A, w0), e);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3604a0 a(C3604a0 c3604a0, C0767v2 c0767v2) {
        return c3604a0.a();
    }

    private C3604a0 a(C0767v2 c0767v2) {
        C3604a0 c3604a0 = (C3604a0) this.e.get(c0767v2);
        if (c3604a0 != null) {
            return c3604a0;
        }
        if (this.a.c(c0767v2) == null) {
            return (C3604a0) this.e.get(this.a.a().Z1);
        }
        boolean z = j;
        if (!z && !this.g.containsKey(c0767v2)) {
            throw new AssertionError();
        }
        C0767v2 c0767v3 = (C0767v2) this.g.get(c0767v2);
        C3604a0 c3604a1 = (C3604a0) this.e.get(c0767v3);
        if (z || c3604a1 != null) {
            return c3604a1;
        }
        throw new AssertionError("Could not find reservation state for frontier type " + c0767v3.toString());
    }

    private void a(ExecutorService executorService) {
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        final C0762u4 c0762u4X = ((C3733i) this.a.f()).x();
        Objects.requireNonNull(c0762u4X);
        C3879c3.a(new InterfaceC3877c1() { // from class: com.android.tools.r8.naming.V$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3877c1
            public final void forEach(Consumer consumer) {
                c0762u4X.a(consumer);
            }
        }, new InterfaceC3905h3() { // from class: com.android.tools.r8.naming.V$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a(concurrentHashMap, (C0684j2) obj);
            }
        }, executorService);
        this.c.putAll(concurrentHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2, Map map) {
        AbstractC0727q0 abstractC0727q0A;
        C0759u2 c0759u2;
        if (c0684j2.B0().M0() || (abstractC0727q0A = this.a.a(c0684j2.B0())) == null) {
            return;
        }
        I4 i4G = ((C3733i) this.a.f()).g(abstractC0727q0A, c0684j2);
        if (i4G.x()) {
            W0 w0T = i4G.t();
            if (w0T.getReference() == c0684j2 || (c0759u2 = (C0759u2) this.c.get(w0T.getReference())) == null) {
                return;
            }
            if (!j && c0759u2 == w0T.K0()) {
                throw new AssertionError();
            }
            map.put(c0684j2, c0759u2);
            return;
        }
        boolean z = j;
        if (!z && !i4G.h()) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList();
        i4G.k().a(C3912j0.b(), new C0793w4$$ExternalSyntheticLambda0(arrayList));
        if (arrayList.isEmpty()) {
            return;
        }
        final C0759u2 c0759u3 = (C0759u2) this.c.get(((W0) arrayList.get(0)).getReference());
        if (!z && !arrayList.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.naming.V$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(c0759u3, (W0) obj);
            }
        })) {
            throw new AssertionError();
        }
        if (c0759u3 != null) {
            if (!z && c0759u3 == ((W0) arrayList.get(0)).K0()) {
                throw new AssertionError();
            }
            map.put(c0684j2, c0759u3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0759u2 c0759u2, W0 w0) {
        return this.c.get(w0.getReference()) == c0759u2;
    }
}
