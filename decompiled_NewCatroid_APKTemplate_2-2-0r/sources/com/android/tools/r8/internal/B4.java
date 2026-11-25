package com.android.tools.r8.internal;

import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class B4 implements ListIterator<C3332x4> {
    protected final C2409kl a;
    protected final ListIterator b;
    protected C3332x4 c;

    protected B4(C2409kl c2409kl) {
        this.a = c2409kl;
        this.b = c2409kl.d.listIterator();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        return c3332x5 == c3332x4;
    }

    @Override // java.util.ListIterator
    public final void add(C3332x4 c3332x4) {
        this.b.add(c3332x4);
    }

    @Override // java.util.ListIterator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C3332x4 previous() {
        C3332x4 c3332x4 = (C3332x4) this.b.previous();
        this.c = c3332x4;
        return c3332x4;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.b.hasNext();
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.b.hasPrevious();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.b.nextIndex();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.b.previousIndex();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        C3332x4 c3332x4 = this.c;
        if (c3332x4 == null) {
            throw new IllegalStateException();
        }
        InterfaceC2641no interfaceC2641noA = c3332x4.a(this.a);
        while (interfaceC2641noA.hasNext()) {
            interfaceC2641noA.next().P0();
            interfaceC2641noA.remove();
        }
        this.b.remove();
        this.c = null;
    }

    @Override // java.util.ListIterator
    public final void set(C3332x4 c3332x4) {
        this.b.set(c3332x4);
    }

    @Override // java.util.ListIterator, java.util.Iterator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C3332x4 next() {
        C3332x4 c3332x4 = (C3332x4) this.b.next();
        this.c = c3332x4;
        return c3332x4;
    }

    public final void a(final C3332x4 c3332x4) {
        next();
    }

    protected B4(C2409kl c2409kl, int i) {
        this.a = c2409kl;
        this.b = c2409kl.d.listIterator(i);
    }
}
