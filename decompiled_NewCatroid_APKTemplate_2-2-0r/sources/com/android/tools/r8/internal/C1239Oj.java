package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0738r3;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0817z3;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Oj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1239Oj {
    private final C0702m a;
    private final C0738r3 b;

    public C1239Oj(C0702m c0702m, C0738r3 c0738r3) {
        this.a = c0702m;
        this.b = c0738r3;
    }

    public final void a(Collection collection, ExecutorService executorService) {
        final Predicate predicateA;
        if (!this.a.z().c() || this.a.f().i() || this.a.Y().a1()) {
            if (this.a.f().i()) {
                C3733i c3733i = (C3733i) this.a.i0().f();
                Objects.requireNonNull(c3733i);
                predicateA = new com.android.tools.r8.graph.C3$$ExternalSyntheticLambda0(c3733i);
            } else {
                predicateA = CM.a();
            }
            final Predicate predicate = new Predicate() { // from class: com.android.tools.r8.internal.Oj$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a(predicateA, (C0767v2) obj);
                }
            };
            final BiPredicate biPredicate = new BiPredicate() { // from class: com.android.tools.r8.internal.Oj$$ExternalSyntheticLambda3
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return this.f$0.a(predicateA, (C0767v2) obj, (C0767v2) obj2);
                }
            };
            C3879c3.a(com.android.tools.r8.utils.V1.a(collection, new Predicate() { // from class: com.android.tools.r8.internal.Oj$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C1239Oj.a(predicateA, (C0729q2) obj);
                }
            }), new Consumer() { // from class: com.android.tools.r8.internal.Oj$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(predicateA, biPredicate, predicate, (C0729q2) obj);
                }
            }, executorService);
        }
    }

    public final void b(Collection collection, ExecutorService executorService) {
        if (this.a.z().c()) {
            return;
        }
        a(collection, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Predicate predicate, C0767v2 c0767v2) {
        return C0738r3.b(this.a, c0767v2, predicate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Predicate predicate, C0767v2 c0767v2, C0767v2 c0767v3) {
        C0738r3 c0738r3 = this.b;
        C0702m c0702m = this.a;
        c0738r3.getClass();
        return C0738r3.a(c0702m, c0767v2, c0767v3, predicate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Predicate predicate, C0729q2 c0729q2) {
        return !predicate.test(c0729q2.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Predicate predicate, BiPredicate biPredicate, Predicate predicate2, C0729q2 c0729q2) {
        final C0817z3 c0817z3A;
        C0706m3.b bVarA1;
        C0738r3 c0738r3 = this.b;
        if (c0738r3 != null) {
            C0702m c0702m = this.a;
            c0817z3A = C0817z3.a(c0702m, c0738r3.a(c0702m, c0729q2.getType(), predicate), biPredicate, predicate2);
        } else {
            c0817z3A = null;
        }
        final com.android.tools.r8.graph.C3 c3 = new com.android.tools.r8.graph.C3(this.a, c0729q2, predicate2);
        if (c0817z3A != null) {
            bVarA1 = c0729q2.a1();
            if (!bVarA1.a()) {
                bVarA1 = bVarA1.a(c0817z3A);
            }
        } else {
            bVarA1 = c0729q2.a1();
        }
        c0729q2.a(c3.a(bVarA1));
        c0729q2.c(new Consumer() { // from class: com.android.tools.r8.internal.Oj$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1239Oj.a(c3, c0817z3A, (com.android.tools.r8.graph.T0) obj);
            }
        }, CM.b());
        c0729q2.f(new Consumer() { // from class: com.android.tools.r8.internal.Oj$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1239Oj.a(c3, c0817z3A, (com.android.tools.r8.graph.W0) obj);
            }
        });
        Iterator<com.android.tools.r8.graph.v5> it = c0729q2.k1().iterator();
        while (it.hasNext()) {
            c3.a(it.next().d());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.C3 c3, C0817z3 c0817z3, com.android.tools.r8.graph.T0 t0) {
        C0706m3.e eVarT0;
        if (c0817z3 != null) {
            eVarT0 = c0817z3.b(t0.T0());
        } else {
            eVarT0 = t0.T0();
        }
        t0.a(c3.a(eVarT0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.C3 c3, C0817z3 c0817z3, com.android.tools.r8.graph.W0 w0) {
        C0706m3.g gVarE1;
        if (c0817z3 != null) {
            gVarE1 = c0817z3.f(w0.e1().c()).a(w0.e1());
        } else {
            gVarE1 = w0.e1();
        }
        w0.a(c3.a(gVarE1));
    }
}
