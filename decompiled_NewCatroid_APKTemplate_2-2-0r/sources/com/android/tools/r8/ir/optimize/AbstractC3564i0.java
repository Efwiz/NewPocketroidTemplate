package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.G5;
import com.android.tools.r8.internal.AbstractC2251ih;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3894f2;
import com.android.tools.r8.utils.y3;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.i0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3564i0 {
    static final /* synthetic */ boolean e = true;
    final AbstractC2251ih a;
    final C0702m b;
    final B5 c;
    final ConcurrentHashMap d = new ConcurrentHashMap();

    AbstractC3564i0(C0702m c0702m, C3894f2 c3894f2, B5 b5) {
        this.b = c0702m;
        this.a = c3894f2;
        this.c = b5;
    }

    private HashSet b(C0729q2 c0729q2, Predicate predicate) {
        AbstractC0727q0 abstractC0727q0C;
        HashSet hashSet = new HashSet();
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(c0729q2);
        while (!arrayDeque.isEmpty()) {
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) arrayDeque.pop();
            if (!predicate.test(abstractC0727q0) && hashSet.add(abstractC0727q0)) {
                C0767v2 c0767v2 = abstractC0727q0.f;
                if (c0767v2 != null && (abstractC0727q0C = this.b.c(c0767v2)) != null) {
                    arrayDeque.add(abstractC0727q0C);
                }
                for (C0767v2 c0767v3 : abstractC0727q0.g.a) {
                    AbstractC0727q0 abstractC0727q0C2 = this.b.c(c0767v3);
                    if (abstractC0727q0C2 != null) {
                        arrayDeque.add(abstractC0727q0C2);
                    }
                }
            }
        }
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Deque deque, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.b.c(c0767v2);
        if (abstractC0727q0C != null) {
            deque.add(abstractC0727q0C);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Deque deque, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.b.c(c0767v2);
        if (abstractC0727q0C != null) {
            deque.add(abstractC0727q0C);
        }
    }

    abstract Runnable a(AbstractC0727q0 abstractC0727q0);

    public final void a(final ExecutorService executorService, y3 y3Var) {
        y3Var.a("Building member pool collection");
        try {
            final ArrayList arrayList = new ArrayList();
            G5.a(this.b).a(((C3733i) this.b.f()).d(), new Consumer() { // from class: com.android.tools.r8.ir.optimize.i0$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(arrayList, executorService, (AbstractC0727q0) obj);
                }
            });
            C3879c3.a(arrayList);
        } finally {
            y3Var.c();
        }
    }

    public final C3562h0 a(C0729q2 c0729q2, ExecutorService executorService, y3 y3Var) {
        y3Var.a("Building member pool collection");
        try {
            ArrayList arrayList = new ArrayList();
            final ConcurrentHashMap concurrentHashMap = this.d;
            Objects.requireNonNull(concurrentHashMap);
            Iterator it = b(c0729q2, new Predicate() { // from class: com.android.tools.r8.ir.optimize.i0$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return concurrentHashMap.containsKey((AbstractC0727q0) obj);
                }
            }).iterator();
            while (it.hasNext()) {
                arrayList.add(executorService.submit(a((AbstractC0727q0) it.next())));
            }
            final ConcurrentHashMap concurrentHashMap2 = this.d;
            Objects.requireNonNull(concurrentHashMap2);
            Iterator it2 = a(c0729q2, new Predicate() { // from class: com.android.tools.r8.ir.optimize.i0$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return concurrentHashMap2.containsKey((AbstractC0727q0) obj);
                }
            }).iterator();
            while (it2.hasNext()) {
                arrayList.add(executorService.submit(a((AbstractC0727q0) it2.next())));
            }
            C3879c3.a(arrayList);
            return a(c0729q2);
        } finally {
            y3Var.c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Deque deque, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.b.c(c0767v2);
        if (abstractC0727q0C != null) {
            deque.add(abstractC0727q0C);
        }
    }

    public final C3562h0 a(C0729q2 c0729q2) {
        if (!e && !this.d.containsKey(c0729q2)) {
            throw new AssertionError();
        }
        return (C3562h0) this.d.get(c0729q2);
    }

    public final boolean a(C0729q2 c0729q2, C0684j2 c0684j2) {
        C3562h0 c3562h0A = a(c0729q2);
        C2173hh c2173hhC = this.a.c(c0684j2);
        if (c3562h0A.a(c2173hhC)) {
            return true;
        }
        c3562h0A.b(c2173hhC);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List list, ExecutorService executorService, AbstractC0727q0 abstractC0727q0) {
        list.add(executorService.submit(a(abstractC0727q0)));
    }

    private HashSet a(C0729q2 c0729q2, Predicate predicate) {
        HashSet hashSet = new HashSet();
        final ArrayDeque arrayDeque = new ArrayDeque();
        B5 b5 = this.c;
        C0767v2 c0767v2 = c0729q2.d;
        b5.a(c0767v2).forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.i0$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(arrayDeque, (C0767v2) obj);
            }
        });
        B5 b6 = this.c;
        C0767v2 c0767v3 = c0729q2.d;
        b6.b(c0767v3).forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.i0$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(arrayDeque, (C0767v2) obj);
            }
        });
        while (!arrayDeque.isEmpty()) {
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) arrayDeque.pop();
            if (!predicate.test(abstractC0727q0) && hashSet.add(abstractC0727q0)) {
                B5 b7 = this.c;
                C0767v2 c0767v4 = abstractC0727q0.d;
                b7.a(c0767v4).forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.i0$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.c(arrayDeque, (C0767v2) obj);
                    }
                });
                B5 b8 = this.c;
                C0767v2 c0767v5 = abstractC0727q0.d;
                b8.b(c0767v5).forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.i0$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.d(arrayDeque, (C0767v2) obj);
                    }
                });
            }
        }
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Deque deque, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.b.c(c0767v2);
        if (abstractC0727q0C != null) {
            deque.add(abstractC0727q0C);
        }
    }
}
