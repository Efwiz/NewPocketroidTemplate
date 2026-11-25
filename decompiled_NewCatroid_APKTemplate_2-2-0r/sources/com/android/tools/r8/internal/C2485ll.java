package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ll, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2485ll implements InterfaceC2488lo {
    static final /* synthetic */ boolean d = true;
    private final B4 a;
    private InterfaceC2641no b;
    private final C2409kl c;

    public C2485ll(C2409kl c2409kl) {
        B4 b4S = c2409kl.s();
        this.a = b4S;
        this.c = c2409kl;
        this.b = b4S.next().a(c2409kl);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b.hasNext() || this.a.hasNext();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2488lo, java.util.ListIterator
    public final boolean hasPrevious() {
        return this.b.hasPrevious() || this.a.hasPrevious();
    }

    @Override // java.util.Iterator
    public final AbstractC2412ko next() {
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

    @Override // com.android.tools.r8.internal.InterfaceC2488lo, java.util.ListIterator
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
}
