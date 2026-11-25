package com.android.tools.r8.utils;

import com.android.tools.r8.internal.H4$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.OU;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class O3<T> {
    static final /* synthetic */ boolean c = true;
    private final ArrayDeque a;
    private final Set b;

    private O3(int i) {
        this(i == 1 ? new HashSet() : OU.c());
    }

    public static O3 a(Set set, Object obj) {
        O3 o3 = new O3(set);
        o3.c(obj);
        return o3;
    }

    public static O3 b(Set set) {
        return new O3(set);
    }

    public static <T> O3<T> c(Iterable<T> iterable) {
        O3<T> o3 = new O3<>(1);
        o3.a((Iterable) iterable);
        return o3;
    }

    public static O3 d() {
        return new O3(1);
    }

    public static O3 e() {
        return new O3(2);
    }

    public static O3 g(Object obj) {
        O3 o3 = new O3(1);
        o3.c(obj);
        return o3;
    }

    public static O3 h(Object obj) {
        O3 o3 = new O3(2);
        o3.c(obj);
        return o3;
    }

    public final void f(Object obj) {
        this.b.add(obj);
    }

    private O3(Set set) {
        this.a = new ArrayDeque();
        this.b = set;
    }

    public static O3 d(Iterable iterable) {
        O3 o3 = new O3(2);
        o3.a(iterable);
        return o3;
    }

    public final void b(Object obj) {
        this.a.addFirst(obj);
    }

    public final boolean e(Object obj) {
        return this.b.contains(obj);
    }

    public T f() {
        if (c || c()) {
            return (T) this.a.removeFirst();
        }
        throw new AssertionError();
    }

    public final void a(Set set) {
        final ArrayDeque arrayDeque = this.a;
        Objects.requireNonNull(arrayDeque);
        set.forEach(new Consumer() { // from class: com.android.tools.r8.utils.O3$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayDeque.addLast(obj);
            }
        });
    }

    public final void b(Iterable iterable) {
        iterable.forEach(new Consumer() { // from class: com.android.tools.r8.utils.O3$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.f(obj);
            }
        });
    }

    public boolean c(T t) {
        if (!this.b.add(t)) {
            return false;
        }
        this.a.addLast(t);
        return true;
    }

    public final Object g() {
        T tF = f();
        this.b.remove(tF);
        return tF;
    }

    public void a(Iterable<? extends T> iterable) {
        iterable.forEach(new H4$$ExternalSyntheticLambda0(this));
    }

    public Set<T> b() {
        return Collections.unmodifiableSet(this.b);
    }

    public final void d(Object obj) {
        this.a.addLast(obj);
    }

    public final boolean a(Object obj) {
        if (!this.b.add(obj)) {
            return false;
        }
        this.a.addFirst(obj);
        return true;
    }

    public boolean c() {
        return !this.a.isEmpty();
    }

    public final void a(final Consumer consumer) {
        a(new BiConsumer() { // from class: com.android.tools.r8.utils.O3$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                consumer.accept(obj);
            }
        });
    }

    public final O3 a(BiConsumer biConsumer) {
        while (c()) {
            biConsumer.accept(f(), this);
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ D3 a(Function function, Object obj, O3 o3) {
        return (D3) function.apply(obj);
    }

    public final D3 a(final Function function) {
        return a(new BiFunction() { // from class: com.android.tools.r8.utils.O3$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return O3.a(function, obj, (O3) obj2);
            }
        });
    }

    public final D3 a(BiFunction biFunction) {
        while (c()) {
            D3 d3 = (D3) biFunction.apply(f(), this);
            if (d3.f()) {
                return d3;
            }
        }
        return D3.e();
    }

    public final Set a() {
        return this.b;
    }
}
