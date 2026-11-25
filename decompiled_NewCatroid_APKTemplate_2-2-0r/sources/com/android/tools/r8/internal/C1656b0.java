package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.b0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1656b0 extends AbstractC1880e0 {
    static final /* synthetic */ boolean d = true;
    int a;
    int b = -1;
    final /* synthetic */ C1730c0 c;

    C1656b0(C1730c0 c1730c0, int i) {
        this.c = c1730c0;
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1880e0
    public final void a(long j) {
        if (this.b == -1) {
            throw new IllegalStateException();
        }
        C1730c0 c1730c0 = this.c;
        int i = this.a;
        this.a = i + 1;
        c1730c0.a(i, j);
        this.b = -1;
        if (d) {
            return;
        }
        this.c.b();
    }

    @Override // com.android.tools.r8.internal.AbstractC1880e0
    public final void b(long j) {
        int i = this.b;
        if (i == -1) {
            throw new IllegalStateException();
        }
        this.c.b(i, j);
    }

    @Override // com.android.tools.r8.internal.XC
    public final long c() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        C1730c0 c1730c0 = this.c;
        YC yc = c1730c0.a;
        int i = c1730c0.b;
        int i2 = this.a;
        this.a = i2 + 1;
        this.b = i2;
        return yc.b(i + i2);
    }

    @Override // com.android.tools.r8.internal.Z, com.android.tools.r8.internal.TC
    public final long e() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        C1730c0 c1730c0 = this.c;
        YC yc = c1730c0.a;
        int i = c1730c0.b;
        int i2 = this.a - 1;
        this.a = i2;
        this.b = i2;
        return yc.b(i + i2);
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.a < this.c.size();
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.a > 0;
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.a;
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.a - 1;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final void remove() {
        int i = this.b;
        if (i == -1) {
            throw new IllegalStateException();
        }
        this.c.e(i);
        int i2 = this.b;
        int i3 = this.a;
        if (i2 < i3) {
            this.a = i3 - 1;
        }
        this.b = -1;
        if (d) {
            return;
        }
        this.c.b();
    }
}
