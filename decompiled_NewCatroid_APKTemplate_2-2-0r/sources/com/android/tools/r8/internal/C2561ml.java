package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ml, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2561ml implements InterfaceC2641no {
    static final /* synthetic */ boolean d = true;
    private final B4 a;
    private InterfaceC2641no b;
    private final C2409kl c;

    public C2561ml(C2409kl c2409kl) {
        B4 b4S = c2409kl.s();
        this.a = b4S;
        this.c = c2409kl;
        this.b = b4S.next().a(c2409kl);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final S10 a(C2409kl c2409kl, com.android.tools.r8.utils.A1 a1, long j, AbstractC3472z00 abstractC3472z00) {
        return this.b.a(c2409kl, a1, j, abstractC3472z00);
    }

    @Override // java.util.ListIterator
    public final void add(AbstractC2412ko abstractC2412ko) {
        this.b.add(abstractC2412ko);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void b(C0702m c0702m, C2409kl c2409kl, C0759u2 c0759u2) {
        this.b.b(c0702m, c2409kl, c0759u2);
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.b.hasNext() || this.a.hasNext();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2488lo, java.util.ListIterator
    public final boolean hasPrevious() {
        return this.b.hasPrevious() || this.a.hasPrevious();
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final Object next() {
        if (this.b.hasNext()) {
            return this.b.next();
        }
        if (!this.a.hasNext()) {
            throw new NoSuchElementException();
        }
        InterfaceC2641no interfaceC2641noA = this.a.next().a(this.c);
        this.b = interfaceC2641noA;
        if (d || interfaceC2641noA.hasNext()) {
            return this.b.next();
        }
        throw new AssertionError();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void r() {
        this.b.r();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no, com.android.tools.r8.internal.InterfaceC2488lo, java.util.Iterator, java.util.ListIterator
    public final void remove() {
        this.b.remove();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void s() {
        this.b.s();
    }

    @Override // java.util.ListIterator
    public final void set(AbstractC2412ko abstractC2412ko) {
        this.b.set(abstractC2412ko);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void v() {
        this.b.v();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final S10 a(C0702m c0702m, C2409kl c2409kl, C0759u2 c0759u2) {
        return this.b.a((C0702m<?>) c0702m, c2409kl, c0759u2);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.ListIterator
    public final AbstractC2412ko previous() {
        if (this.b.hasPrevious()) {
            return this.b.previous();
        }
        if (!this.a.hasPrevious()) {
            throw new NoSuchElementException();
        }
        C3332x4 c3332x4Previous = this.a.previous();
        InterfaceC2641no interfaceC2641noA = c3332x4Previous.a(this.c, c3332x4Previous.p().size());
        this.b = interfaceC2641noA;
        if (d || interfaceC2641noA.hasPrevious()) {
            return this.b.previous();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final AbstractC1305Qs a(C0702m c0702m, C2409kl c2409kl, B4 b4, S10 s10, AbstractC1915eM abstractC1915eM) {
        return this.b.a((C0702m<?>) c0702m, c2409kl, b4, s10, abstractC1915eM);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return this.b.a((C0702m<?>) c0702m, o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final boolean a(C0702m c0702m, C2409kl c2409kl, C0767v2 c0767v2, Consumer consumer) {
        return this.b.a((C0702m<?>) c0702m, c2409kl, c0767v2, (Consumer<C2179hn>) consumer);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, C2409kl c2409kl, C0767v2 c0767v2, com.android.tools.r8.graph.X x) {
        this.b.a((C0702m<?>) c0702m, c2409kl, c0767v2, x);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C2409kl c2409kl, int i) {
        this.b.a(c2409kl, i);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, S10 s10) {
        this.b.a((C0702m<?>) c0702m, s10);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, C2409kl c2409kl, com.android.tools.r8.graph.Y0 y0, Set set) {
        this.b.a((C0702m<?>) c0702m, c2409kl, y0, (Set<S10>) set);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, C2409kl c2409kl, B4 b4, S10 s10, Set set, Set set2) {
        throw new Z00();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, C2409kl c2409kl, ListIterator listIterator, Set set, Set set2) {
        throw new Z00();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, ListIterator listIterator, boolean z) {
        throw new Z00();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, int i, ListIterator listIterator) {
        throw new Z00();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, B4 b4, com.android.tools.r8.utils.A1 a1, UnaryOperator unaryOperator) {
        throw new Z00();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C0702m c0702m, C2409kl c2409kl, C2409kl c2409kl2, ListIterator listIterator, Set set, C0729q2 c0729q2) {
        throw new Z00();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, ListIterator listIterator, AbstractC2412ko abstractC2412ko, com.android.tools.r8.utils.A1 a1) {
        return this.b.a(c2409kl, (ListIterator<C3332x4>) listIterator, abstractC2412ko, a1);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(AbstractC2412ko abstractC2412ko, Set set) {
        this.b.a(abstractC2412ko, (Set<S10>) set);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(AbstractC1915eM abstractC1915eM) {
        this.b.a(abstractC1915eM);
    }
}
