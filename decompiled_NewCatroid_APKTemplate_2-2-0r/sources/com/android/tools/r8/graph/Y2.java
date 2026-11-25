package com.android.tools.r8.graph;

import com.android.tools.r8.internal.OJ;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Y2 extends X2 {
    static final /* synthetic */ boolean b = true;
    private SortedMap a;

    private Y2(OJ oj) {
        this.a = oj;
    }

    public static Y2 a(int i) {
        return new Y2(new OJ(i));
    }

    @Override // com.android.tools.r8.graph.X2
    final com.android.tools.r8.utils.D3 b(Function function) {
        Iterator it = this.a.values().iterator();
        while (it.hasNext()) {
            com.android.tools.r8.utils.D3 d3 = (com.android.tools.r8.utils.D3) function.apply((T0) it.next());
            if (d3.f()) {
                return d3;
            }
        }
        return com.android.tools.r8.utils.D3.e();
    }

    @Override // com.android.tools.r8.graph.X2
    final List c() {
        return b(new V2$$ExternalSyntheticLambda3());
    }

    @Override // com.android.tools.r8.graph.X2
    final int d() {
        V2$$ExternalSyntheticLambda3 v2$$ExternalSyntheticLambda3 = new V2$$ExternalSyntheticLambda3();
        Iterator it = this.a.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (v2$$ExternalSyntheticLambda3.test((T0) it.next())) {
                i++;
            }
        }
        return i;
    }

    @Override // com.android.tools.r8.graph.X2
    final int e() {
        V2$$ExternalSyntheticLambda1 v2$$ExternalSyntheticLambda1 = new V2$$ExternalSyntheticLambda1();
        Iterator it = this.a.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (v2$$ExternalSyntheticLambda1.test((T0) it.next())) {
                i++;
            }
        }
        return i;
    }

    @Override // com.android.tools.r8.graph.X2
    final int f() {
        return this.a.size();
    }

    @Override // com.android.tools.r8.graph.X2
    final List g() {
        return b(new V2$$ExternalSyntheticLambda1());
    }

    @Override // com.android.tools.r8.graph.X2
    final void h() {
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.Y2$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Y2.a((Z0) obj, (T0) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.graph.X2
    final T0 c(Y0 y0) {
        T0 t0 = (T0) this.a.get(Z0.a(y0));
        if (t0 == null || !t0.E0()) {
            return null;
        }
        return t0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Z0 z0, T0 t0) {
        if (!b && !z0.b(t0.getReference())) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.graph.X2
    final void b(T0 t0) {
        boolean z = b;
        if (!z && !t0.E0()) {
            throw new AssertionError();
        }
        T0 t1 = (T0) this.a.put(Z0.a(t0.getReference()), t0);
        if (!z && t1 != null) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.graph.X2
    final Iterable a(Predicate predicate) {
        return com.android.tools.r8.utils.I1.a(this.a.values(), predicate);
    }

    @Override // com.android.tools.r8.graph.X2
    final void a(Collection collection) {
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.graph.Y2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((T0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.X2
    final void a(T0 t0) {
        boolean z = b;
        if (!z && t0.E0()) {
            throw new AssertionError();
        }
        T0 t1 = (T0) this.a.put(Z0.a(t0.getReference()), t0);
        if (!z && t1 != null) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.graph.X2
    final void b() {
        this.a.values().removeIf(new V2$$ExternalSyntheticLambda1());
    }

    @Override // com.android.tools.r8.graph.X2
    final void b(T0[] t0Arr) {
        a(t0Arr, new V2$$ExternalSyntheticLambda1());
    }

    @Override // com.android.tools.r8.graph.X2
    final T0 b(Y0 y0) {
        T0 t0 = (T0) this.a.get(Z0.a(y0));
        if (t0 == null || t0.E0()) {
            return null;
        }
        return t0;
    }

    @Override // com.android.tools.r8.graph.X2
    final void a() {
        this.a.values().removeIf(new V2$$ExternalSyntheticLambda3());
    }

    @Override // com.android.tools.r8.graph.X2
    final void a(T0[] t0Arr) {
        a(t0Arr, new V2$$ExternalSyntheticLambda3());
    }

    @Override // com.android.tools.r8.graph.X2
    final T0 a(Y0 y0) {
        return (T0) this.a.get(Z0.a(y0));
    }

    private List b(final Predicate predicate) {
        final ArrayList arrayList = new ArrayList(this.a.size());
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.Y2$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Y2.a(predicate, arrayList, (Z0) obj, (T0) obj2);
            }
        });
        return Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.tools.r8.graph.X2
    final void a(Function function) {
        for (T0 t0 : new ArrayList(this.a.values())) {
            T0 t1 = (T0) function.apply(t0);
            if (t1 != t0) {
                Z0 z0A = Z0.a(t0.getReference());
                Z0 z0A2 = Z0.a(t1.getReference());
                if (!z0A2.isEqualTo(z0A) && this.a.get(z0A) == t0) {
                    this.a.remove(z0A);
                }
                this.a.put(z0A2, t1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, List list, Z0 z0, T0 t0) {
        if (predicate.test(t0)) {
            list.add(t0);
        }
    }

    private void a(T0[] t0Arr, final Predicate predicate) {
        if (t0Arr.length == 0 && this.a.isEmpty()) {
            return;
        }
        final OJ oj = new OJ(this.a.size() + t0Arr.length);
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.Y2$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Y2.a(predicate, oj, (Z0) obj, (T0) obj2);
            }
        });
        for (T0 t0 : t0Arr) {
            if (!b && !predicate.test(t0)) {
                throw new AssertionError();
            }
            oj.put(Z0.a(t0.getReference()), t0);
        }
        this.a = oj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, SortedMap sortedMap, Z0 z0, T0 t0) {
        if (predicate.test(t0)) {
            return;
        }
        sortedMap.put(z0, t0);
    }
}
