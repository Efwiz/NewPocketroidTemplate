package com.android.tools.r8.ir.optimize.info;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.T0;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1008Gg;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1428Un;
import com.android.tools.r8.internal.AbstractC1617aV;
import com.android.tools.r8.internal.B1;
import com.android.tools.r8.internal.InterfaceC2090ga;
import com.android.tools.r8.internal.M5;
import com.android.tools.r8.ir.optimize.V;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3738j;
import com.android.tools.r8.utils.Y2;
import java.util.BitSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class o extends n {
    static final /* synthetic */ boolean e = true;
    private final C3738j a = C3733i.E();
    private final IdentityHashMap b = new IdentityHashMap();
    private final IdentityHashMap c = new IdentityHashMap();
    private final IdentityHashMap d = new IdentityHashMap();

    private synchronized k a(T0 t0) {
        k kVar = (k) this.b.get(t0);
        if (kVar != null) {
            return kVar;
        }
        k kVarN = ((k) t0.X0().b()).n();
        this.b.put(t0, kVarN);
        return kVarN;
    }

    private synchronized l g(W0 w0) {
        l lVar = (l) this.c.get(w0);
        if (lVar != null) {
            return lVar;
        }
        l lVarN = ((l) w0.i1().b()).N();
        this.c.put(w0, lVarN);
        return lVarN;
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void b(o5 o5Var) {
        g(o5Var.e()).X();
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void c(o5 o5Var) {
        g(o5Var.e()).L();
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void d(W0 w0) {
        g(w0).M();
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void e(W0 w0) {
        g(w0).H();
    }

    @Override // com.android.tools.r8.internal.NE
    public final void f(W0 w0) {
        g(w0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(Map.Entry entry) {
        return ((W0) entry.getKey()).J1();
    }

    public final void c() {
        Iterator it = this.c.entrySet().iterator();
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.ir.optimize.info.o$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return o.a((Map.Entry) obj);
            }
        };
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                it.remove();
            }
        }
        Iterator it2 = this.d.entrySet().iterator();
        Predicate predicate2 = new Predicate() { // from class: com.android.tools.r8.ir.optimize.info.o$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return o.b((Map.Entry) obj);
            }
        };
        while (it2.hasNext()) {
            if (predicate2.test(it2.next())) {
                it2.remove();
            }
        }
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.info.o$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((T0) obj).a((k) obj2);
            }
        });
        this.b.clear();
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.info.o$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((W0) obj).a((l) obj2);
            }
        });
        this.c.clear();
        this.d.forEach(new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.info.o$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((W0) obj).a((V) obj2);
            }
        });
        this.d.clear();
    }

    public final void b() {
        boolean z = e;
        if (!z && !this.a.a()) {
            throw new AssertionError();
        }
        if (!z && !this.b.isEmpty()) {
            throw new AssertionError(Y2.a(", ", this.b.keySet()));
        }
        if (!z && !this.c.isEmpty()) {
            throw new AssertionError(Y2.a(", ", this.c.keySet()));
        }
        if (!z && !this.d.isEmpty()) {
            throw new AssertionError(Y2.a(", ", this.d.keySet()));
        }
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(o5 o5Var, InterfaceC2090ga interfaceC2090ga) {
        g(o5Var.e()).a(interfaceC2090ga);
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(o5 o5Var, AbstractC1008Gg abstractC1008Gg) {
        g(o5Var.e()).a(abstractC1008Gg);
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void b(W0 w0) {
        g(w0).K();
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(o5 o5Var, AbstractC1617aV abstractC1617aV) {
        g(o5Var.e()).a(abstractC1617aV);
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void b(W0 w0, BitSet bitSet) {
        g(w0).b(bitSet);
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(o5 o5Var, BitSet bitSet) {
        g(o5Var.e()).d(bitSet);
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(o5 o5Var) {
        g(o5Var.e()).W();
    }

    public final void a(C0702m c0702m, ExecutorService executorService, m mVar) {
        c();
        n.a(c0702m.f().d(), executorService, mVar);
    }

    @Override // com.android.tools.r8.ir.optimize.info.n
    public final void a(Consumer consumer) {
        consumer.accept(this.a);
    }

    public final void a(C3733i c3733i) {
        this.a.a(c3733i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Map.Entry entry) {
        return ((W0) entry.getKey()).J1();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1448Vh
    public final void a(T0 t0, AbstractC1154Lf abstractC1154Lf) {
        a(t0).a(abstractC1154Lf);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1448Vh
    public final void a(T0 t0, int i) {
        a(t0).a(i);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1448Vh
    public final void a(T0 t0, C0702m c0702m, B1 b1) {
        boolean z = e;
        if (!z && !((C3733i) c0702m.f()).v().a(t0.getReference())) {
            throw new AssertionError();
        }
        if (!z && ((C3733i) c0702m.f()).v().b(t0.getReference()).k()) {
            throw new AssertionError();
        }
        if (((C3733i) c0702m.f()).b(c0702m, t0.getReference())) {
            a(t0).a(b1);
        }
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void c(W0 w0) {
        g(w0).J();
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(Set set, W0 w0) {
        g(w0).a(set);
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(W0 w0, int i) {
        g(w0).c(i);
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(W0 w0, C0702m c0702m, B1 b1) {
        if (((C3733i) c0702m.f()).b(c0702m, w0.getReference())) {
            g(w0).a(b1);
        }
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(C0702m c0702m, W0 w0, AbstractC1154Lf abstractC1154Lf) {
        g(w0).a(c0702m, w0, abstractC1154Lf);
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(W0 w0, V v) {
        this.d.put(w0, v);
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(W0 w0, M5 m5) {
        g(w0).a(m5);
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(W0 w0, AbstractC1428Un abstractC1428Un) {
        g(w0).a(abstractC1428Un);
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(W0 w0) {
        g(w0).P();
    }

    @Override // com.android.tools.r8.internal.NE
    public final synchronized void a(W0 w0, BitSet bitSet) {
        g(w0).c(bitSet);
    }
}
