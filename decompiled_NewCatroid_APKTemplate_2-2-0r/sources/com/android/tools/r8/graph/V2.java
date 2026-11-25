package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.OU;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class V2 extends X2 {
    static final /* synthetic */ boolean c = true;
    private T0[] a;
    private T0[] b;

    private V2(T0[] t0Arr, T0[] t0Arr2) {
        boolean z = c;
        if (!z && t0Arr == null) {
            throw new AssertionError();
        }
        if (!z && t0Arr2 == null) {
            throw new AssertionError();
        }
        this.a = t0Arr;
        this.b = t0Arr2;
    }

    public static V2 a(T0[] t0Arr, T0[] t0Arr2) {
        return new V2(t0Arr, t0Arr2);
    }

    private void i() {
        Set setC = OU.c();
        for (T0 t0 : a(CM.b())) {
            boolean zAdd = setC.add(t0.getReference());
            if (!c && !zAdd) {
                throw new AssertionError("Duplicate field `" + t0.getReference().r0() + "`");
            }
        }
    }

    @Override // com.android.tools.r8.graph.X2
    final com.android.tools.r8.utils.D3 b(Function function) {
        int i = 0;
        int i2 = 0;
        while (true) {
            T0[] t0Arr = this.a;
            if (i2 >= t0Arr.length) {
                while (true) {
                    T0[] t0Arr2 = this.b;
                    if (i >= t0Arr2.length) {
                        return com.android.tools.r8.utils.D3.e();
                    }
                    if (((com.android.tools.r8.utils.D3) function.apply(t0Arr2[i])).f()) {
                        return com.android.tools.r8.utils.D3.d();
                    }
                    i++;
                }
            } else {
                if (((com.android.tools.r8.utils.D3) function.apply(t0Arr[i2])).f()) {
                    return com.android.tools.r8.utils.D3.d();
                }
                i2++;
            }
        }
    }

    @Override // com.android.tools.r8.graph.X2
    final List c() {
        return com.android.tools.r8.utils.A1.b() ? Collections.unmodifiableList(Arrays.asList(this.b)) : Arrays.asList(this.b);
    }

    @Override // com.android.tools.r8.graph.X2
    final int d() {
        return this.b.length;
    }

    @Override // com.android.tools.r8.graph.X2
    final int e() {
        return this.a.length;
    }

    @Override // com.android.tools.r8.graph.X2
    final int f() {
        return this.a.length + this.b.length;
    }

    @Override // com.android.tools.r8.graph.X2
    final List g() {
        return com.android.tools.r8.utils.A1.b() ? Collections.unmodifiableList(Arrays.asList(this.a)) : Arrays.asList(this.a);
    }

    @Override // com.android.tools.r8.graph.X2
    final void h() {
        if (c) {
            return;
        }
        i();
    }

    @Override // com.android.tools.r8.graph.X2
    final Iterable a(Predicate predicate) {
        List listAsList = Arrays.asList(this.b);
        Objects.requireNonNull(predicate);
        return AbstractC0984Fi.a(AbstractC2734ot.c(listAsList, new V2$$ExternalSyntheticLambda0(predicate)), AbstractC2734ot.c(Arrays.asList(this.a), new V2$$ExternalSyntheticLambda0(predicate)));
    }

    @Override // com.android.tools.r8.graph.X2
    final T0 c(Y0 y0) {
        return a(this.a, y0);
    }

    @Override // com.android.tools.r8.graph.X2
    final void a(Collection collection) {
        T0[] t0Arr = this.a;
        T0[] t0Arr2 = new T0[collection.size() + t0Arr.length];
        System.arraycopy(t0Arr, 0, t0Arr2, 0, t0Arr.length);
        int length = t0Arr.length;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            t0Arr2[length] = (T0) it.next();
            length++;
        }
        this.a = t0Arr2;
    }

    @Override // com.android.tools.r8.graph.X2
    final void a(T0 t0) {
        T0[] t0Arr = this.b;
        T0[] t0Arr2 = new T0[t0Arr.length + 1];
        System.arraycopy(t0Arr, 0, t0Arr2, 0, t0Arr.length);
        t0Arr2[t0Arr.length] = t0;
        this.b = t0Arr2;
    }

    @Override // com.android.tools.r8.graph.X2
    final void b(T0 t0) {
        T0[] t0Arr = this.a;
        T0[] t0Arr2 = new T0[t0Arr.length + 1];
        System.arraycopy(t0Arr, 0, t0Arr2, 0, t0Arr.length);
        t0Arr2[t0Arr.length] = t0;
        this.a = t0Arr2;
    }

    @Override // com.android.tools.r8.graph.X2
    final void a() {
        this.b = T0.m;
    }

    @Override // com.android.tools.r8.graph.X2
    final void a(T0[] t0Arr) {
        if (!c && t0Arr == null) {
            throw new AssertionError();
        }
        this.b = t0Arr;
    }

    @Override // com.android.tools.r8.graph.X2
    final void b() {
        this.a = T0.m;
    }

    @Override // com.android.tools.r8.graph.X2
    public final void b(T0[] t0Arr) {
        if (!c && t0Arr == null) {
            throw new AssertionError();
        }
        this.a = t0Arr;
    }

    @Override // com.android.tools.r8.graph.X2
    final T0 a(Y0 y0) {
        T0 t0A = a(this.b, y0);
        return t0A == null ? a(this.a, y0) : t0A;
    }

    final void b(Collection collection) {
        T0[] t0Arr = this.b;
        T0[] t0Arr2 = new T0[collection.size() + t0Arr.length];
        System.arraycopy(t0Arr, 0, t0Arr2, 0, t0Arr.length);
        int length = t0Arr.length;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            t0Arr2[length] = (T0) it.next();
            length++;
        }
        this.b = t0Arr2;
    }

    @Override // com.android.tools.r8.graph.X2
    final void a(Function function) {
        this.a = a(this.a, function, new V2$$ExternalSyntheticLambda1(), new Consumer() { // from class: com.android.tools.r8.graph.V2$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((List) obj);
            }
        });
        this.b = a(this.b, function, new V2$$ExternalSyntheticLambda3(), new Consumer() { // from class: com.android.tools.r8.graph.V2$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((List) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.X2
    final T0 b(Y0 y0) {
        return a(this.b, y0);
    }

    private static T0 a(T0[] t0Arr, Y0 y0) {
        for (T0 t0 : t0Arr) {
            y0.getClass();
            Y0 reference = t0.getReference();
            if (reference.f == y0.f && reference.h == y0.h) {
                return t0;
            }
        }
        return null;
    }

    private static T0[] a(T0[] t0Arr, Function function, Predicate predicate, Consumer consumer) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < t0Arr.length; i++) {
            T0 t0 = t0Arr[i];
            boolean z = c;
            if (!z && !predicate.test(t0)) {
                throw new AssertionError();
            }
            T0 t1 = (T0) function.apply(t0);
            if (!z && t1 == null) {
                throw new AssertionError();
            }
            if (t0 != t1) {
                if (predicate.test(t1)) {
                    t0Arr[i] = t1;
                } else {
                    t0Arr[i] = null;
                    arrayList.add(t1);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return t0Arr;
        }
        consumer.accept(arrayList);
        return (T0[]) com.android.tools.r8.utils.B.a(t0Arr, new Predicate() { // from class: com.android.tools.r8.graph.V2$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((T0) obj);
            }
        }, T0.m, t0Arr.length - arrayList.size());
    }
}
