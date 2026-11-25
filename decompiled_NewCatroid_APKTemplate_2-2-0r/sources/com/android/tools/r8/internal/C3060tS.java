package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tS, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3060tS extends V0 {
    int a;
    int b;
    int c;
    boolean d;
    C2464lS e;
    final /* synthetic */ C3129uS f;

    private C3060tS(C3129uS c3129uS) {
        this.f = c3129uS;
        this.a = c3129uS.d;
        this.b = -1;
        this.c = c3129uS.f;
        this.d = c3129uS.c;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.c != 0;
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i;
        Object obj;
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.c--;
        if (this.d) {
            this.d = false;
            C3129uS c3129uS = this.f;
            int i2 = c3129uS.d;
            this.b = i2;
            return c3129uS.a[i2];
        }
        Object[] objArr = this.f.a;
        do {
            i = this.a - 1;
            this.a = i;
            if (i < 0) {
                this.b = Integer.MIN_VALUE;
                return this.e.get((-i) - 1);
            }
            obj = objArr[i];
        } while (obj == null);
        this.b = i;
        return obj;
    }

    @Override // com.android.tools.r8.internal.V0, java.util.Iterator, java.util.ListIterator
    public final void remove() {
        Object obj;
        int i = this.b;
        if (i == -1) {
            throw new IllegalStateException();
        }
        C3129uS c3129uS = this.f;
        int i2 = c3129uS.d;
        if (i == i2) {
            c3129uS.c = false;
            c3129uS.a[i2] = null;
        } else {
            int i3 = this.a;
            if (i3 < 0) {
                c3129uS.remove(this.e.set((-i3) - 1, null));
                this.b = -1;
                return;
            }
            Object[] objArr = c3129uS.a;
            loop0: while (true) {
                int i4 = (i + 1) & this.f.b;
                while (true) {
                    obj = objArr[i4];
                    if (obj != null) {
                        int iA = AbstractC3307wk.a(System.identityHashCode(obj));
                        int i5 = this.f.b;
                        int i6 = iA & i5;
                        if (i > i4) {
                            if (i >= i6 && i6 > i4) {
                                break;
                            } else {
                                i4 = (i4 + 1) & i5;
                            }
                        } else if (i >= i6 || i6 > i4) {
                            break;
                        } else {
                            i4 = (i4 + 1) & i5;
                        }
                    } else {
                        break loop0;
                    }
                }
                if (i4 < i) {
                    if (this.e == null) {
                        this.e = new C2464lS(2);
                    }
                    this.e.add(objArr[i4]);
                }
                objArr[i] = obj;
                i = i4;
            }
            objArr[i] = null;
        }
        this.f.f--;
        this.b = -1;
    }

    /* synthetic */ C3060tS(C3129uS c3129uS, int i) {
        this(c3129uS);
    }
}
