package com.android.tools.r8.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class W2 {
    static final /* synthetic */ boolean c = true;
    private final AbstractC0727q0 a;
    private X2 b;

    private W2(AbstractC0727q0 abstractC0727q0, X2 x2) {
        this.a = abstractC0727q0;
        this.b = x2;
    }

    public static W2 a(AbstractC0727q0 abstractC0727q0, T0[] t0Arr, T0[] t0Arr2) {
        X2 x2A;
        int length = t0Arr.length + t0Arr2.length;
        if (length > 30) {
            x2A = Y2.a(length);
            x2A.b(t0Arr);
            x2A.a(t0Arr2);
        } else {
            x2A = V2.a(t0Arr, t0Arr2);
        }
        return new W2(abstractC0727q0, x2A);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(T0 t0) {
        if (c) {
            return;
        }
        e(t0);
    }

    private void e(T0 t0) {
        if (c || t0.J0() == this.a.d) {
            return;
        }
        throw new AssertionError("Expected field `" + t0.getReference().r0() + "` to have holder `" + this.a.d.r0() + "`");
    }

    public final void b(T0 t0) {
        boolean z = c;
        if (!z) {
            e(t0);
        }
        this.b.b(t0);
        if (z) {
            return;
        }
        this.b.h();
    }

    public final void c() {
        this.b.b();
    }

    public final boolean f() {
        return this.b.e() > 0;
    }

    public final List g() {
        return this.b.c();
    }

    public final int h() {
        return this.b.f();
    }

    public final List i() {
        return this.b.g();
    }

    public final void j() {
        a(new Consumer() { // from class: com.android.tools.r8.graph.W2$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.d((T0) obj);
            }
        });
        if (c) {
            return;
        }
        this.b.h();
    }

    public final T0 c(Y0 y0) {
        return this.b.c(y0);
    }

    public final boolean d() {
        return this.b.b(new Function() { // from class: com.android.tools.r8.graph.W2$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return W2.c((T0) obj);
            }
        }).f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 c(T0 t0) {
        if (t0.y0()) {
            return com.android.tools.r8.utils.D3.d();
        }
        return com.android.tools.r8.utils.D3.e();
    }

    public final void b(T0[] t0Arr) {
        this.b.b(t0Arr);
        if (c) {
            return;
        }
        this.b.h();
    }

    public final void b() {
        this.b.a();
    }

    public final boolean e() {
        return this.b.d() > 0;
    }

    public final T0 b(Y0 y0) {
        return this.b.b(y0);
    }

    public final void a(final Consumer consumer) {
        this.b.b(new Function() { // from class: com.android.tools.r8.graph.W2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return W2.a(consumer, (T0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(Consumer consumer, T0 t0) {
        consumer.accept(t0);
        return com.android.tools.r8.utils.D3.e();
    }

    public final Iterable a(Predicate predicate) {
        return this.b.a(predicate);
    }

    public final void a(Collection collection) {
        if (!c) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                T0 t0 = (T0) it.next();
                if (!c) {
                    e(t0);
                }
            }
        }
        this.b.a(collection);
        if (c) {
            return;
        }
        this.b.h();
    }

    public final void a(T0 t0) {
        boolean z = c;
        if (!z) {
            e(t0);
        }
        this.b.a(t0);
        if (z) {
            return;
        }
        this.b.h();
    }

    public final void a(T0[] t0Arr) {
        this.b.a(t0Arr);
        if (c) {
            return;
        }
        this.b.h();
    }

    public final T0 a(Y0 y0) {
        return this.b.a(y0);
    }

    public final void a(Function function) {
        this.b.a(function);
    }

    public final ArrayList a() {
        final ArrayList arrayList = new ArrayList(this.b.f());
        a(new Consumer() { // from class: com.android.tools.r8.graph.W2$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add((T0) obj);
            }
        });
        arrayList.sort(Comparator.comparing(new T0$$ExternalSyntheticLambda1()));
        return arrayList;
    }
}
