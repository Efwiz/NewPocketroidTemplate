package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2437l5 extends AbstractC2272j {
    private final boolean a;
    private int b;

    public C2437l5(boolean z) {
        this.a = z;
    }

    @Override // com.android.tools.r8.internal.AbstractC2272j
    public final boolean a() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        this.b = 0;
        return this.a;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.b == 0;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.b == 1;
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.b;
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.b - 1;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2282j5
    public final boolean q() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.b = 1;
        return this.a;
    }
}
