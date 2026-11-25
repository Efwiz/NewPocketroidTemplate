package com.android.tools.r8.graph;

import com.android.tools.r8.internal.CM;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.w4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0792w4 {
    static final /* synthetic */ boolean d = true;
    private final AbstractC0727q0 a;
    private AbstractC0802x4 b;
    private W0 c = W0.t;

    /* JADX INFO: renamed from: com.android.tools.r8.graph.w4$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface a {
        static a a(final W0[] w0Arr, final W0[] w0Arr2) {
            return new a() { // from class: com.android.tools.r8.graph.w4$a$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.graph.C0792w4.a
                public final C0792w4 a(AbstractC0727q0 abstractC0727q0) {
                    return C0792w4.a.a(w0Arr, w0Arr2, abstractC0727q0);
                }
            };
        }

        static a empty() {
            W0[] w0Arr = W0.s;
            return a(w0Arr, w0Arr);
        }

        C0792w4 a(AbstractC0727q0 abstractC0727q0);

        /* JADX INFO: Access modifiers changed from: private */
        static C0792w4 a(W0[] w0Arr, W0[] w0Arr2, AbstractC0727q0 abstractC0727q0) {
            AbstractC0802x4 abstractC0802x4A;
            boolean z = C0792w4.d;
            int length = w0Arr.length + w0Arr2.length;
            if (length > 30) {
                abstractC0802x4A = C0812y4.a(length);
                abstractC0802x4A.a(w0Arr);
                abstractC0802x4A.b(w0Arr2);
            } else {
                abstractC0802x4A = C0769v4.a(w0Arr, w0Arr2);
            }
            return new C0792w4(abstractC0727q0, abstractC0802x4A);
        }
    }

    C0792w4(AbstractC0727q0 abstractC0727q0, AbstractC0802x4 abstractC0802x4) {
        this.a = abstractC0727q0;
        this.b = abstractC0802x4;
    }

    public final C0792w4 a(AbstractC0727q0 abstractC0727q0, Function function) {
        return new C0792w4(abstractC0727q0, this.b.a(function));
    }

    public final void b(final Consumer consumer, final Predicate predicate) {
        AbstractC0802x4 abstractC0802x4 = this.b;
        Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.graph.w4$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0792w4.b(predicate, consumer, (W0) obj);
            }
        };
        abstractC0802x4.getClass();
        abstractC0802x4.a(consumer2, CM.b());
    }

    public boolean c(Predicate<W0> predicate) {
        return this.b.a(predicate) != null;
    }

    public final Iterable d() {
        return this.b.c();
    }

    public final boolean e(Predicate predicate) {
        return this.b.b(predicate) != null;
    }

    public final com.android.tools.r8.utils.D3 f(Function function) {
        return this.b.g(function);
    }

    public final boolean g() {
        return this.b.g(new Function() { // from class: com.android.tools.r8.graph.w4$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0792w4.d((W0) obj);
            }
        }).f();
    }

    public boolean h() {
        return e(CM.b());
    }

    public final Iterable i() {
        return this.b.e();
    }

    public int j() {
        return this.b.f();
    }

    public int k() {
        return this.b.g();
    }

    public final int l() {
        return this.b.h();
    }

    public final void m() {
        if (!d && this.b.h() != 0) {
            throw new AssertionError();
        }
        this.b = C0812y4.k();
    }

    public final void n() {
        b(new Consumer() { // from class: com.android.tools.r8.graph.w4$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.e((W0) obj);
            }
        }, CM.b());
        if (d) {
            return;
        }
        this.b.i();
    }

    public final Iterable o() {
        return this.b.j();
    }

    public final void c(final Consumer consumer, final Predicate predicate) {
        this.b.b(new Consumer() { // from class: com.android.tools.r8.graph.w4$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0792w4.c(predicate, consumer, (W0) obj);
            }
        });
    }

    public final boolean d(Predicate predicate) {
        W0 w0A = this.b.a(predicate);
        if (w0A == null) {
            w0A = this.b.b(predicate);
        }
        return w0A != null;
    }

    public final void e(Function function) {
        this.b.f(function);
    }

    public final Iterable f(Predicate predicate) {
        return com.android.tools.r8.utils.I1.a(this.b.e(), predicate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Predicate predicate, Consumer consumer, W0 w0) {
        if (predicate.test(w0)) {
            consumer.accept(w0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Predicate predicate, Consumer consumer, W0 w0) {
        if (predicate.test(w0)) {
            consumer.accept(w0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(W0 w0) {
        if (d) {
            return;
        }
        f(w0);
    }

    public final void a(Consumer consumer) {
        b(consumer, CM.b());
    }

    public final void a(final Consumer consumer, final Predicate predicate) {
        this.b.a(new Consumer() { // from class: com.android.tools.r8.graph.w4$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0792w4.a(predicate, consumer, (W0) obj);
            }
        });
    }

    public final void d(Function function) {
        this.c = W0.t;
        this.b.e(function);
    }

    public final String e() {
        return this.b.d();
    }

    public final synchronized W0 f() {
        if (this.c == W0.t) {
            this.c = null;
            for (W0 w0 : this.b.c()) {
                if (w0.u1()) {
                    this.c = w0;
                    break;
                }
            }
        }
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, Consumer consumer, W0 w0) {
        if (predicate.test(w0)) {
            consumer.accept(w0);
        }
    }

    public final W0 b(C0684j2 c0684j2) {
        return this.b.a(c0684j2.H0(), c0684j2.C0());
    }

    public final W0 c(C0684j2 c0684j2) {
        return this.b.b(c0684j2);
    }

    public final W0 b(Predicate predicate) {
        return this.b.b(predicate);
    }

    public final void c(Function function) {
        this.c = W0.t;
        this.b.d(function);
    }

    public final W0 d(C0684j2 c0684j2) {
        W0 w0C = this.b.c(c0684j2);
        if (w0C != null) {
            this.b.getClass();
            if (w0C.f.N()) {
                this.c = W0.t;
            } else if (!d) {
                this.b.getClass();
                if (!w0C.R0()) {
                    throw new AssertionError();
                }
            }
        }
        return w0C;
    }

    public final ArrayList a() {
        ArrayList arrayList = new ArrayList(this.b.h());
        b(new C0793w4$$ExternalSyntheticLambda0(arrayList), CM.b());
        arrayList.sort(Comparator.comparing(new W0$$ExternalSyntheticLambda12()));
        return arrayList;
    }

    public final void b(W0 w0) {
        this.c = W0.t;
        this.b.b(w0);
    }

    public final void c(W0 w0) {
        this.b.c(w0);
    }

    public final void b() {
        this.c = W0.t;
        this.b.a();
    }

    public final void c() {
        this.b.b();
    }

    public final W0 b(C0684j2 c0684j2, Function function) {
        this.c = W0.t;
        return this.b.b(c0684j2, function);
    }

    public final W0 a(C0737r2 c0737r2, C0759u2 c0759u2) {
        return this.b.a(c0737r2, c0759u2);
    }

    public final void b(Function function) {
        this.b.c(function);
    }

    public final W0 a(AbstractC0717o2 abstractC0717o2) {
        return this.b.a(abstractC0717o2.b(), abstractC0717o2.a());
    }

    public final void b(Collection collection) {
        if (!d) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                W0 w0 = (W0) it.next();
                if (!d) {
                    f(w0);
                }
            }
        }
        this.b.b(collection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 d(W0 w0) {
        if (w0.z0()) {
            return com.android.tools.r8.utils.D3.d();
        }
        return com.android.tools.r8.utils.D3.e();
    }

    private void f(W0 w0) {
        if (d || w0.J0() == this.a.d) {
            return;
        }
        throw new AssertionError("Expected method `" + w0.getReference().r0() + "` to have holder `" + this.a.d.r0() + "`");
    }

    public final W0 a(C0684j2 c0684j2) {
        return this.b.a(c0684j2);
    }

    public final W0 a(Predicate predicate) {
        return this.b.a(predicate);
    }

    public final void a(W0 w0) {
        this.c = W0.t;
        this.b.a(w0);
    }

    public final void b(W0[] w0Arr) {
        if (!d && w0Arr != null) {
            for (W0 w0 : Arrays.asList(w0Arr)) {
                if (!d) {
                    f(w0);
                }
            }
        }
        this.b.b(w0Arr);
    }

    public final void a(Function function) {
        this.c = W0.t;
        this.b.b(function);
    }

    public final W0 a(C0684j2 c0684j2, Function function) {
        this.c = W0.t;
        return this.b.a(c0684j2, function);
    }

    public final void a(Collection collection) {
        if (!d) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                W0 w0 = (W0) it.next();
                if (!d) {
                    f(w0);
                }
            }
        }
        this.c = W0.t;
        this.b.a(collection);
    }

    public final void a(Set set) {
        this.b.a(set);
        this.c = W0.t;
    }

    public final void a(W0[] w0Arr) {
        if (!d && w0Arr != null) {
            for (W0 w0 : Arrays.asList(w0Arr)) {
                if (!d) {
                    f(w0);
                }
            }
        }
        this.c = W0.t;
        this.b.a(w0Arr);
    }

    public final void a(LinkedHashSet linkedHashSet) {
        this.b.a(linkedHashSet);
    }
}
