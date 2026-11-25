package com.android.tools.r8.internal;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.dex.C0572k$$ExternalSyntheticLambda8;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.internal.e8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1892e8 {
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final S8 b;
    private final com.android.tools.r8.utils.A1 c;
    private final Set d = OU.a();
    private final AN e = AN.d();

    public C1892e8(C0702m c0702m) {
        this.a = c0702m;
        this.b = new S8(c0702m);
        this.c = c0702m.Y();
    }

    private void a(ExecutorService executorService) {
        C3879c3.a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.internal.e8$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
    }

    private void c() {
        com.android.tools.r8.utils.A1.l lVarC0 = this.c.C0();
        if (!f && !lVarC0.c() && lVarC0.b() && this.d.isEmpty()) {
            throw new AssertionError("Expected to find at least one open interface");
        }
        com.android.tools.r8.utils.O3 o3B = com.android.tools.r8.utils.O3.b(this.d);
        o3B.a(this.d);
        while (o3B.c()) {
            Iterator<C0767v2> it = ((AbstractC0727q0) o3B.f()).f1().iterator();
            while (it.hasNext()) {
                AbstractC0727q0 abstractC0727q0C = this.a.c(it.next());
                if (abstractC0727q0C != null) {
                    o3B.c(abstractC0727q0C);
                }
            }
        }
        this.a.a(new C3346xH((Set) this.d.stream().map(new e8$$ExternalSyntheticLambda0()).collect(Collectors.toCollection(new Supplier() { // from class: com.android.tools.r8.internal.e8$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a();
            }
        }))));
    }

    public final void b(ExecutorService executorService) {
        a(executorService);
        c();
        b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.e8$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        }, new C0572k$$ExternalSyntheticLambda8());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        Set setA;
        Set set = this.d;
        com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
        if (!wA1.z0()) {
            if (!f && !(wA1 instanceof com.android.tools.r8.graph.Z) && !wA1.B0() && !(wA1 instanceof com.android.tools.r8.graph.F5)) {
                throw new AssertionError();
            }
            setA = Collections.emptySet();
        } else {
            C0753u c0753uK = wA1.K();
            C1669b8 c1669b8 = new C1669b8(this, o5Var, c0753uK);
            C1979f8 c1979f8 = new C1979f8(this.a, o5Var, this.e);
            int iA = C2674o7.a(this.a, c0753uK, o5Var).a(c1669b8).a(new C1743c8(c1979f8)).a().a();
            boolean z = f;
            if (!z && iA != 4 && !c1979f8.a().isEmpty()) {
                throw new AssertionError();
            }
            if (iA == 2) {
                C2050g7 c2050g7A = C2050g7.a(c0753uK, this.c);
                C1819d8 c1819d8 = new C1819d8(this, c1669b8, o5Var);
                H7 h7 = new H7(this.a, C3258w5.k(), c2050g7A, c1819d8);
                AbstractC1125Kd abstractC1125KdF = h7.f(c2050g7A.a());
                if (!z) {
                    abstractC1125KdF.getClass();
                    if (!(abstractC1125KdF instanceof C1097Jd)) {
                        throw new AssertionError();
                    }
                }
                loop0: for (J6 j6 : c2050g7A.b()) {
                    if (!h7.d(j6)) {
                        AbstractC2359k7 abstractC2359k7A = (AbstractC2359k7) h7.e(j6);
                        abstractC2359k7A.getClass();
                        if (abstractC2359k7A instanceof C2325jh) {
                            c1979f8.a(o5Var, 0, abstractC2359k7A.f());
                            break;
                        }
                        do {
                            for (int iA2 = j6.a(); iA2 <= j6.b(); iA2++) {
                                B7 b7L = c0753uK.l(iA2);
                                c1979f8.a(b7L, abstractC2359k7A);
                                abstractC2359k7A = b7L.a(abstractC2359k7A, c1819d8.c.a, c1819d8.a);
                                abstractC2359k7A.getClass();
                                if (abstractC2359k7A instanceof C2325jh) {
                                    c1979f8.a(o5Var, iA2, abstractC2359k7A.f());
                                    break loop0;
                                }
                            }
                            j6 = h7.c(j6) ? (J6) c2050g7A.a(j6) : null;
                        } while (j6 != null);
                    }
                }
                c0753uK.o(iA);
            } else if (iA == 4) {
                c0753uK.o(iA);
            }
            setA = c1979f8.a();
        }
        set.addAll(setA);
    }

    private void b() {
        final com.android.tools.r8.utils.O2 o2 = this.a.Y().e;
        final ArrayList arrayList = new ArrayList(this.e.b());
        this.e.a(new BiConsumer() { // from class: com.android.tools.r8.internal.e8$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                arrayList.add((com.android.tools.r8.graph.o5) obj);
            }
        });
        arrayList.sort(Comparator.comparing(new e8$$ExternalSyntheticLambda3()));
        arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.e8$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o2, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Set a() {
        return com.android.tools.r8.utils.T2.b(this.d.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.utils.O2 o2, com.android.tools.r8.graph.o5 o5Var) {
        o2.warning((Diagnostic) this.e.b(o5Var));
    }
}
