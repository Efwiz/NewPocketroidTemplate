package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.errors.BackportDiagnostic;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.utils.C3945p;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/* JADX INFO: renamed from: com.android.tools.r8.internal.j4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2281j4 implements C7 {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final C1815d4 b;

    public C2281j4(C0702m c0702m) {
        if (!c && !c0702m.Y().A0.b()) {
            throw new AssertionError();
        }
        this.a = c0702m;
        this.b = new C1815d4(c0702m);
    }

    public final boolean a() {
        return !this.b.c.isEmpty();
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        if (!b7.H()) {
            return C3224ve.c();
        }
        I7 i7L = b7.l();
        C0684j2 c0684j2T = i7L.T();
        AbstractC1739c4 abstractC1739c4 = null;
        C0684j2 c0684j2A = this.a.E().a((AbstractC1476Wj) null, c0684j2T);
        if (!c && c0684j2A == null) {
            throw new AssertionError();
        }
        AbstractC1739c4 abstractC1739c5 = (AbstractC1739c4) this.b.c.get(c0684j2A);
        Map mapQ = this.a.Y().C1.q();
        if (abstractC1739c5 == null && this.a.Y().C1.d() && mapQ.containsKey(c0684j2T.e)) {
            abstractC1739c5 = (AbstractC1739c4) this.b.c.get(this.a.a().a((C0767v2) mapQ.get(c0684j2T.e), c0684j2T.h, c0684j2T.f));
        }
        if (abstractC1739c5 == null || !this.a.Y().C0) {
            abstractC1739c4 = abstractC1739c5;
        } else if (this.a.Y().D0) {
            C0702m c0702m = this.a;
            if (!c0702m.f().f(abstractC1739c5.a.e).R().j()) {
                this.a.Y().e.error(new BackportDiagnostic(abstractC1739c5.a, o5Var.getOrigin(), MethodPosition.create(o5Var)));
            }
        }
        if (abstractC1739c4 != null && !this.a.D().a(o5Var.t(), new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.j4$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.C;
            }
        })) {
            return a(i7L, abstractC1739c4);
        }
        return C3224ve.c();
    }

    private C3224ve a(final I7 i7, final AbstractC1739c4 abstractC1739c4) {
        return new C3005se((C3075te) new C3075te().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.j4$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(abstractC1739c4, i7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection a(AbstractC1739c4 abstractC1739c4, I7 i7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return abstractC1739c4.a(i7, this.a, g7, c1230Ob, ac);
    }

    public static List<C0684j2> a(C3945p c3945p, com.android.tools.r8.utils.A1 a1, ExecutorService executorService) throws IOException {
        return a(c3945p != null ? new C0408c(c3945p, a1, com.android.tools.r8.utils.y3.b()).a(executorService) : null, a1);
    }

    public static List<C0684j2> a(AbstractC0681j0 abstractC0681j0, com.android.tools.r8.utils.A1 a1) throws Throwable {
        ArrayList arrayList = new ArrayList();
        a1.a(com.android.tools.r8.utils.y3.b(), abstractC0681j0);
        C0702m c0702mA = C0702m.a(abstractC0681j0 != null ? C0664h.a(abstractC0681j0, com.android.tools.r8.synthesis.y.d()) : null, a1.J0(), com.android.tools.r8.utils.y3.b());
        new C1815d4(c0702mA).a(new Wj$$ExternalSyntheticLambda2(arrayList));
        new C1006Ge(c0702mA).a(new Wj$$ExternalSyntheticLambda2(arrayList));
        return arrayList;
    }

    public static void a(com.android.tools.r8.utils.A1 a1) {
        C0722p1 c0722p1 = a1.a;
        X3.a(c0722p1, "Ljava/lang/ArithmeticException;", "Ljava/lang/AssertionError;", "Ljava/lang/Double;", "Ljava/lang/Exception;");
        X3.a(c0722p1, "Ljava/lang/ExceptionInInitializerError;", "Ljava/lang/Float;", "Ljava/lang/IllegalAccessException;", "Ljava/lang/IllegalArgumentException;");
        X3.a(c0722p1, "Ljava/lang/IndexOutOfBoundsException;", "Ljava/lang/Integer;", "Ljava/lang/Iterable;", "Ljava/lang/Long;");
        X3.a(c0722p1, "Ljava/lang/Math;", "Ljava/lang/NoSuchMethodException;", "Ljava/lang/NullPointerException;", "Ljava/lang/NumberFormatException;");
        X3.a(c0722p1, "Ljava/lang/OutOfMemoryError;", "Ljava/lang/Runnable;", "Ljava/lang/RuntimeException;", "Ljava/lang/SecurityException;");
        X3.a(c0722p1, "Ljava/lang/reflect/Constructor;", "Ljava/lang/reflect/InvocationTargetException;", "Ljava/lang/reflect/Method;", "Ljava/util/AbstractMap$SimpleImmutableEntry;");
        X3.a(c0722p1, "Ljava/util/ArrayList;", "Ljava/util/Arrays;", "Ljava/util/Collection;", "Ljava/util/Collections;");
        X3.a(c0722p1, "Ljava/util/Comparator;", "Ljava/util/Enumeration;", "Ljava/util/HashMap;", "Ljava/util/HashSet;");
        X3.a(c0722p1, "Ljava/util/Iterator;", "Ljava/util/List;", "Ljava/util/ListIterator;", "Ljava/util/Map$Entry;");
        X3.a(c0722p1, "Ljava/util/Map;", "Ljava/util/Objects;", "Ljava/util/Optional;", "Ljava/util/OptionalDouble;");
        X3.a(c0722p1, "Ljava/util/OptionalInt;", "Ljava/util/OptionalLong;", "Ljava/util/Set;", "Ljava/util/concurrent/atomic/AtomicReference;");
        X3.a(c0722p1, "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;", "Ljava/util/function/Consumer;", "Ljava/util/function/DoubleConsumer;");
        X3.a(c0722p1, "Ljava/util/function/IntConsumer;", "Ljava/util/function/LongConsumer;", "Ljava/util/function/Predicate;", "Ljava/util/function/Supplier;");
        X3.a(c0722p1, "Ljava/util/stream/DoubleStream;", "Ljava/util/stream/IntStream;", "Ljava/util/stream/LongStream;", "Ljava/util/stream/Stream;");
        X3.a(c0722p1, "Lsun/misc/Unsafe;", "[Ljava/lang/CharSequence;", "[Ljava/lang/Class;", "[Ljava/lang/Object;");
        c0722p1.d("[Ljava/lang/Throwable;");
        c0722p1.d("[Ljava/util/Map$Entry;");
    }
}
