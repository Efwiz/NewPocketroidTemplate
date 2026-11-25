package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ro, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2946ro extends V0 {
    int a = -1;
    int b = 0;
    final /* synthetic */ C3015so c;

    C2946ro(C3015so c3015so) {
        this.c = c3015so;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b < this.c.a.d;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int[] iArr = this.c.a.b;
        int i = this.b;
        this.a = i;
        int i2 = iArr[i];
        int[] iArr2 = this.c.a.c;
        int i3 = this.b;
        this.b = i3 + 1;
        return new C3249w(i2, iArr2[i3]);
    }

    @Override // com.android.tools.r8.internal.V0, java.util.Iterator, java.util.ListIterator
    public final void remove() {
        if (this.a == -1) {
            throw new IllegalStateException();
        }
        this.a = -1;
        int iB = C3085to.b(this.c.a);
        int i = this.b;
        this.b = i - 1;
        int i2 = iB - i;
        System.arraycopy(this.c.a.b, this.b + 1, this.c.a.b, this.b, i2);
        System.arraycopy(this.c.a.c, this.b + 1, this.c.a.c, this.b, i2);
    }
}
