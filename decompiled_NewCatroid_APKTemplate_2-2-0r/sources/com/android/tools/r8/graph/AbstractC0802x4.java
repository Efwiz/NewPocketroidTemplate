package com.android.tools.r8.graph;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.x4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0802x4 {
    abstract W0 a(C0684j2 c0684j2);

    abstract W0 a(C0684j2 c0684j2, Function function);

    abstract W0 a(C0737r2 c0737r2, C0759u2 c0759u2);

    abstract W0 a(Predicate predicate);

    abstract AbstractC0802x4 a(Function function);

    abstract void a();

    abstract void a(W0 w0);

    abstract void a(Collection collection);

    abstract void a(LinkedHashSet linkedHashSet);

    abstract void a(Set set);

    final void a(final Consumer consumer, final Predicate predicate) {
        g(new Function() { // from class: com.android.tools.r8.graph.x4$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC0802x4.a(predicate, consumer, (W0) obj);
            }
        });
    }

    abstract void a(W0[] w0Arr);

    abstract W0 b(C0684j2 c0684j2);

    abstract W0 b(C0684j2 c0684j2, Function function);

    abstract W0 b(Predicate predicate);

    abstract void b();

    abstract void b(W0 w0);

    abstract void b(Collection collection);

    final void b(Consumer consumer) {
        a(consumer, new Predicate() { // from class: com.android.tools.r8.graph.x4$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.e((W0) obj);
            }
        });
    }

    abstract void b(Function function);

    abstract void b(W0[] w0Arr);

    abstract W0 c(C0684j2 c0684j2);

    abstract Iterable c();

    abstract void c(W0 w0);

    abstract void c(Function function);

    abstract String d();

    abstract void d(Function function);

    final boolean d(W0 w0) {
        return w0.f.N();
    }

    abstract Iterable e();

    abstract void e(Function function);

    final boolean e(W0 w0) {
        return w0.R0();
    }

    abstract int f();

    abstract void f(Function function);

    abstract int g();

    abstract com.android.tools.r8.utils.D3 g(Function function);

    abstract int h();

    abstract void i();

    abstract Iterable j();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(Predicate predicate, Consumer consumer, W0 w0) {
        if (predicate.test(w0)) {
            consumer.accept(w0);
        }
        return com.android.tools.r8.utils.D3.e();
    }

    final void a(Consumer consumer) {
        a(consumer, new Predicate() { // from class: com.android.tools.r8.graph.x4$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.d((W0) obj);
            }
        });
    }
}
