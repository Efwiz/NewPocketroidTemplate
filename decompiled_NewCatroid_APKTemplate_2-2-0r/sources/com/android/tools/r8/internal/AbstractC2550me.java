package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.me, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2550me {
    static final /* synthetic */ boolean e = true;
    protected final C0702m a;
    protected final com.android.tools.r8.graph.H3 b;
    private final IdentityHashMap c = new IdentityHashMap();
    private final ArrayList d = new ArrayList();

    public AbstractC2550me(C0702m c0702m, com.android.tools.r8.graph.H3 h3) {
        this.a = c0702m;
        this.b = h3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean d(C0729q2 c0729q2) {
        boolean z = e;
        if (!z && !c(c0729q2)) {
            throw new AssertionError();
        }
        if (z || b(c0729q2)) {
            return true;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean e(C0729q2 c0729q2) {
        return !b(c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(C0729q2 c0729q2) {
        boolean z = e;
        if (!z && this.c.containsKey(c0729q2)) {
            throw new AssertionError();
        }
        h(c0729q2);
        if (!z && b(c0729q2)) {
            throw new AssertionError();
        }
        if (!z && a(c0729q2)) {
            throw new AssertionError();
        }
        k(c0729q2);
        if (!z && this.c.containsKey(c0729q2)) {
            throw new AssertionError();
        }
        this.c.put(c0729q2, EnumC2476le.a);
        if (c(c0729q2)) {
            this.d.add(c0729q2);
        }
    }

    private void g(C0729q2 c0729q2) {
        a(new Consumer() { // from class: com.android.tools.r8.internal.me$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.j((C0729q2) obj);
            }
        }, c0729q2);
    }

    private void h(C0729q2 c0729q2) {
        boolean z = e;
        if (!z && b(c0729q2)) {
            throw new AssertionError();
        }
        if (!z && a(c0729q2)) {
            throw new AssertionError();
        }
        this.b.a(new Predicate() { // from class: com.android.tools.r8.internal.me$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.e((C0729q2) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.me$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.f((C0729q2) obj);
            }
        }, c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j(C0729q2 c0729q2) {
        if (a(c0729q2)) {
            return;
        }
        if (!b(c0729q2)) {
            h(c0729q2);
            boolean z = e;
            if (!z && b(c0729q2)) {
                throw new AssertionError();
            }
            if (!z && a(c0729q2)) {
                throw new AssertionError();
            }
            k(c0729q2);
            if (!z && this.c.containsKey(c0729q2)) {
                throw new AssertionError();
            }
            this.c.put(c0729q2, EnumC2476le.a);
        }
        g(c0729q2);
        if (!e && !b(c0729q2)) {
            throw new AssertionError();
        }
        this.c.put(c0729q2, EnumC2476le.b);
        i(c0729q2);
    }

    public void a(Collection collection) {
        ArrayDeque arrayDeque = new ArrayDeque();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            if (c(c0729q2)) {
                arrayDeque.add(c0729q2);
            }
        }
        while (!arrayDeque.isEmpty()) {
            j((C0729q2) arrayDeque.removeLast());
            a(arrayDeque);
        }
    }

    protected final boolean b(C0729q2 c0729q2) {
        return this.c.get(c0729q2) == EnumC2476le.a;
    }

    public boolean c(C0729q2 c0729q2) {
        C0767v2 c0767v2N1 = c0729q2.n1();
        if (c0767v2N1 == null) {
            return true;
        }
        if (C0729q2.a(this.a.c(c0767v2N1)) != null) {
            return false;
        }
        Iterator<C0767v2> it = c0729q2.f1().iterator();
        while (it.hasNext()) {
            if (C0729q2.a(this.a.c(it.next())) != null) {
                return false;
            }
        }
        return true;
    }

    public abstract void i(C0729q2 c0729q2);

    public abstract void k(C0729q2 c0729q2);

    private void a(ArrayDeque arrayDeque) {
        if (!e && !this.d.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.me$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.d((C0729q2) obj);
            }
        })) {
            throw new AssertionError();
        }
        arrayDeque.addAll(this.d);
        this.d.clear();
    }

    public void a(Consumer consumer, C0729q2 c0729q2) {
        this.b.a(c0729q2).forEach(consumer);
    }

    protected final boolean a(C0729q2 c0729q2) {
        return this.c.get(c0729q2) == EnumC2476le.b;
    }
}
