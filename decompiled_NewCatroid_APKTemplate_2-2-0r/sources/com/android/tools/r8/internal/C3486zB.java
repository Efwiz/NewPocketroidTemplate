package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3486zB implements InterfaceC2641no {
    static final /* synthetic */ boolean e = true;
    private final C2409kl a;
    private C3332x4 b;
    private InterfaceC2641no c;
    private Set d;

    public C3486zB(C2409kl c2409kl, C3332x4 c3332x4) {
        this(c2409kl, c3332x4, 0);
    }

    public final Set a() {
        return this.d;
    }

    @Override // java.util.ListIterator
    public final void add(AbstractC2412ko abstractC2412ko) {
        this.c.add(abstractC2412ko);
    }

    public final boolean b(C3332x4 c3332x4) {
        return this.d.contains(c3332x4);
    }

    public final void e(AbstractC2412ko abstractC2412ko) {
        this.c.add(abstractC2412ko);
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.c.hasNext();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2488lo, java.util.ListIterator
    public final boolean hasPrevious() {
        return this.c.hasPrevious() || a(this.b) != null;
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no, com.android.tools.r8.internal.InterfaceC2488lo, java.util.Iterator, java.util.ListIterator
    public final void remove() {
        this.c.remove();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void s() {
        this.c.s();
    }

    @Override // java.util.ListIterator
    public final void set(AbstractC2412ko abstractC2412ko) {
        this.c.set(abstractC2412ko);
    }

    public C3486zB(C2409kl c2409kl, C3332x4 c3332x4, int i) {
        this.d = OU.c();
        this.a = c2409kl;
        this.b = c3332x4;
        this.c = c3332x4.a(c2409kl, i);
        this.d.add(c3332x4);
        if (i > 0) {
            previous();
            next();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(AbstractC2412ko abstractC2412ko, Set set) {
        this.c.a(abstractC2412ko, (Set<S10>) set);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void b(C0702m c0702m, C2409kl c2409kl, C0759u2 c0759u2) {
        this.c.b(c0702m, c2409kl, c0759u2);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.ListIterator
    public final AbstractC2412ko previous() {
        if (this.c.hasPrevious()) {
            return this.c.previous();
        }
        C3332x4 c3332x4A = a(this.b);
        if (c3332x4A == null) {
            return this.c.previous();
        }
        this.b = c3332x4A;
        this.d.add(c3332x4A);
        C3332x4 c3332x4 = this.b;
        InterfaceC2641no interfaceC2641noA = c3332x4.a(this.a, c3332x4.p().size());
        this.c = interfaceC2641noA;
        interfaceC2641noA.previous();
        return this.c.previous();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final S10 a(C2409kl c2409kl, com.android.tools.r8.utils.A1 a1, long j, AbstractC3472z00 abstractC3472z00) {
        return this.c.a(c2409kl, a1, j, abstractC3472z00);
    }

    @Override // java.util.Iterator, java.util.ListIterator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final AbstractC2412ko next() {
        AbstractC2412ko next = this.c.next();
        if (!next.M1()) {
            return next;
        }
        C3332x4 c3332x4K2 = next.P().K2();
        if (!a(this.b, c3332x4K2)) {
            return next;
        }
        while (c3332x4K2.Q()) {
            C3332x4 c3332x4A = AbstractC2967s4.a(c3332x4K2);
            if (!a(c3332x4K2, c3332x4A)) {
                break;
            }
            this.d.add(c3332x4K2);
            c3332x4K2 = c3332x4A;
        }
        this.b = c3332x4K2;
        this.d.add(c3332x4K2);
        InterfaceC2641no interfaceC2641noA = this.b.a(this.a);
        this.c = interfaceC2641noA;
        return interfaceC2641noA.next();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final S10 a(C0702m c0702m, C2409kl c2409kl, C0759u2 c0759u2) {
        return this.c.a((C0702m<?>) c0702m, c2409kl, c0759u2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final AbstractC1305Qs a(C0702m c0702m, C2409kl c2409kl, B4 b4, S10 s10, AbstractC1915eM abstractC1915eM) {
        return this.c.a((C0702m<?>) c0702m, c2409kl, b4, s10, abstractC1915eM);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return this.c.a((C0702m<?>) c0702m, o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final boolean a(C0702m c0702m, C2409kl c2409kl, C0767v2 c0767v2, Consumer consumer) {
        return this.c.a((C0702m<?>) c0702m, c2409kl, c0767v2, (Consumer<C2179hn>) consumer);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, C2409kl c2409kl, C0767v2 c0767v2, com.android.tools.r8.graph.X x) {
        this.c.a((C0702m<?>) c0702m, c2409kl, c0767v2, x);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C2409kl c2409kl, int i) {
        this.c.a(c2409kl, i);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, S10 s10) {
        this.c.a((C0702m<?>) c0702m, s10);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, C2409kl c2409kl, com.android.tools.r8.graph.Y0 y0, Set set) {
        this.c.a((C0702m<?>) c0702m, c2409kl, y0, (Set<S10>) set);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, C2409kl c2409kl, B4 b4, S10 s10, Set set, Set set2) {
        this.c.a((C0702m<?>) c0702m, c2409kl, b4, s10, (Set<C3332x4>) set, (Set<S10>) set2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, C2409kl c2409kl, ListIterator listIterator, Set set, Set set2) {
        this.c.a((C0702m<?>) c0702m, c2409kl, (ListIterator<C3332x4>) listIterator, (Set<C3332x4>) set, (Set<S10>) set2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, ListIterator listIterator, boolean z) {
        return this.c.a(c2409kl, (ListIterator<C3332x4>) listIterator, z);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, int i, ListIterator listIterator) {
        return this.c.a(c2409kl, i, (ListIterator<C3332x4>) listIterator);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, B4 b4, com.android.tools.r8.utils.A1 a1, UnaryOperator unaryOperator) {
        return this.c.a(c2409kl, b4, a1, (UnaryOperator<C3332x4>) unaryOperator);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C0702m c0702m, C2409kl c2409kl, C2409kl c2409kl2, ListIterator listIterator, Set set, C0729q2 c0729q2) {
        return this.c.a((C0702m<?>) c0702m, c2409kl, c2409kl2, (ListIterator<C3332x4>) listIterator, (Set<C3332x4>) set, c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, ListIterator listIterator, AbstractC2412ko abstractC2412ko, com.android.tools.r8.utils.A1 a1) {
        return this.c.a(c2409kl, (ListIterator<C3332x4>) listIterator, abstractC2412ko, a1);
    }

    private static boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        boolean z = e;
        if (!z && !c3332x4.z().contains(c3332x5)) {
            throw new AssertionError();
        }
        if (!z && !c3332x5.y().contains(c3332x4)) {
            throw new AssertionError();
        }
        C1296Qj c1296QjP = c3332x4.m().P();
        return c1296QjP != null && c1296QjP.K2() == c3332x5 && c3332x5.y().size() == 1;
    }

    private static C3332x4 a(C3332x4 c3332x4) {
        C3332x4 c3332x5;
        if (c3332x4.y().size() != 1 || !a(c3332x4.y().get(0), c3332x4)) {
            return null;
        }
        C3332x4 c3332x6 = c3332x4.y().get(0);
        while (true) {
            c3332x5 = c3332x6;
            if (c3332x5.y().size() != 1 || !a(c3332x5.y().get(0), c3332x5) || !c3332x5.Q()) {
                break;
            }
            c3332x6 = c3332x5.y().get(0);
        }
        if (c3332x5.Q()) {
            return null;
        }
        return c3332x5;
    }
}
