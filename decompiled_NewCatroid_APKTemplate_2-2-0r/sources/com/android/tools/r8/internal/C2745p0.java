package com.android.tools.r8.internal;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.p0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2745p0 extends C2579n0 implements ListIterator {
    final /* synthetic */ C2818q0 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2745p0(C2818q0 c2818q0) {
        super(c2818q0);
        this.d = c2818q0;
    }

    private ListIterator a() {
        this.c.c();
        if (this.c.b == this.b) {
            return (ListIterator) this.a;
        }
        throw new ConcurrentModificationException();
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        boolean zIsEmpty = this.d.isEmpty();
        a().add(obj);
        AbstractC2889r0.b(this.d.f);
        if (zIsEmpty) {
            this.d.a();
        }
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return a().hasPrevious();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return a().nextIndex();
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        return a().previous();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return a().previousIndex();
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        a().set(obj);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2745p0(C2818q0 c2818q0, int i) {
        super(c2818q0, c2818q0.e().listIterator(i));
        this.d = c2818q0;
    }
}
