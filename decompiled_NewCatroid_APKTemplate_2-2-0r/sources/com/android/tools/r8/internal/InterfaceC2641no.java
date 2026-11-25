package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3912j0;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.no, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC2641no extends InterfaceC2488lo, ListIterator<AbstractC2412ko>, OM {
    static {
        boolean z = AbstractC2565mo.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ boolean a(AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2) {
        return abstractC2412ko2 == abstractC2412ko;
    }

    AbstractC1305Qs a(C0702m<?> c0702m, C2409kl c2409kl, B4 b4, S10 s10, AbstractC1915eM abstractC1915eM);

    S10 a(C0702m<?> c0702m, C2409kl c2409kl, C0759u2 c0759u2);

    S10 a(C2409kl c2409kl, com.android.tools.r8.utils.A1 a1, long j, AbstractC3472z00 abstractC3472z00);

    C3332x4 a(C0702m<?> c0702m, C2409kl c2409kl, C2409kl c2409kl2, ListIterator<C3332x4> listIterator, Set<C3332x4> set, C0729q2 c0729q2);

    C3332x4 a(C2409kl c2409kl, int i, ListIterator<C3332x4> listIterator);

    C3332x4 a(C2409kl c2409kl, B4 b4, com.android.tools.r8.utils.A1 a1, UnaryOperator<C3332x4> unaryOperator);

    C3332x4 a(C2409kl c2409kl, ListIterator<C3332x4> listIterator, AbstractC2412ko abstractC2412ko, com.android.tools.r8.utils.A1 a1);

    C3332x4 a(C2409kl c2409kl, ListIterator<C3332x4> listIterator, boolean z);

    void a(C0702m<?> c0702m, S10 s10);

    void a(C0702m<?> c0702m, C2409kl c2409kl, com.android.tools.r8.graph.Y0 y0, Set<S10> set);

    void a(C0702m<?> c0702m, C2409kl c2409kl, C0767v2 c0767v2, com.android.tools.r8.graph.X x);

    void a(C0702m<?> c0702m, C2409kl c2409kl, B4 b4, S10 s10, Set<C3332x4> set, Set<S10> set2);

    void a(C0702m<?> c0702m, C2409kl c2409kl, ListIterator<C3332x4> listIterator, Set<C3332x4> set, Set<S10> set2);

    default void a(AbstractC1915eM abstractC1915eM) {
    }

    void a(C2409kl c2409kl, int i);

    void a(AbstractC2412ko abstractC2412ko, Set<S10> set);

    boolean a(C0702m<?> c0702m, com.android.tools.r8.graph.o5 o5Var);

    boolean a(C0702m<?> c0702m, C2409kl c2409kl, C0767v2 c0767v2, Consumer<C2179hn> consumer);

    void b(C0702m<?> c0702m, C2409kl c2409kl, C0759u2 c0759u2);

    default void b(AbstractC2412ko abstractC2412ko) {
        previous();
        add(abstractC2412ko);
        AbstractC2412ko abstractC2412koPrevious = previous();
        if (!AbstractC2565mo.a && abstractC2412koPrevious != abstractC2412ko) {
            throw new AssertionError();
        }
    }

    default void c(AbstractC2412ko abstractC2412ko) {
        add(abstractC2412ko);
        AbstractC2412ko abstractC2412koPrevious = previous();
        if (!AbstractC2565mo.a && abstractC2412koPrevious != abstractC2412ko) {
            throw new AssertionError();
        }
    }

    default void d(AbstractC2412ko abstractC2412ko) {
        a(abstractC2412ko, (Set<S10>) null);
    }

    default void r() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2488lo, java.util.Iterator, java.util.ListIterator
    void remove();

    void s();

    default void v() {
        throw new Z00();
    }

    default void d(Predicate predicate) {
        a(predicate);
        previous();
    }

    default S10 a(C2409kl c2409kl, com.android.tools.r8.utils.A1 a1) {
        return a(c2409kl, a1, 0L, AbstractC3472z00.m());
    }

    default S10 a(C2409kl c2409kl, com.android.tools.r8.utils.A1 a1, int i) {
        return a(c2409kl, a1, i, AbstractC3472z00.k());
    }

    default void c(C2409kl c2409kl) {
        a(c2409kl, 1);
    }

    default void a(final AbstractC2412ko abstractC2412ko) {
        b(new Predicate() { // from class: com.android.tools.r8.internal.no$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InterfaceC2641no.a(abstractC2412ko, (AbstractC2412ko) obj);
            }
        });
    }

    default AbstractC2412ko b(Predicate predicate) {
        c(predicate);
        return next();
    }

    default boolean a(C0702m c0702m, C2409kl c2409kl, C0767v2 c0767v2) {
        return a((C0702m<?>) c0702m, c2409kl, c0767v2, C3912j0.b());
    }

    default void b(C2409kl c2409kl) {
        a(c2409kl, 0);
    }

    default void a(C2409kl c2409kl, boolean z) {
        ((A4) this).a(c2409kl, com.android.tools.r8.utils.M.a(z));
    }

    default C3332x4 b(C2409kl c2409kl, int i) {
        return a(c2409kl, i, (ListIterator<C3332x4>) null);
    }

    default void a(C0702m c0702m, C2409kl c2409kl, String str) {
        b(c0702m, c2409kl, c0702m.a().c(str));
    }

    default C3332x4 a(C2409kl c2409kl, ListIterator<C3332x4> listIterator) {
        return a(c2409kl, listIterator, hasPrevious() && j().g());
    }

    default C3332x4 a(C2409kl c2409kl) {
        return a(c2409kl, (ListIterator<C3332x4>) null);
    }

    default C3332x4 a(C2409kl c2409kl, B4 b4, com.android.tools.r8.utils.A1 a1) {
        return a(c2409kl, b4, a1, (UnaryOperator<C3332x4>) null);
    }

    default C3332x4 a(C0702m<?> c0702m, C2409kl c2409kl, C2409kl c2409kl2) {
        Set<C3332x4> setC = OU.c();
        C3332x4 c3332x4A = a(c0702m, c2409kl, c2409kl2, (ListIterator<C3332x4>) null, setC, (C0729q2) null);
        c2409kl.b((Collection<C3332x4>) setC);
        return c3332x4A;
    }
}
