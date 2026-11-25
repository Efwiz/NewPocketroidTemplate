package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3505zR extends AbstractC2659o1 implements Cloneable {
    protected transient Object[] b;
    protected transient int[] c;
    protected transient int d;
    protected transient boolean e;
    protected transient int f;
    protected transient int g;
    protected transient long[] h;
    protected transient int i;
    protected transient int j;
    protected int k;
    protected transient NR l;
    protected transient InterfaceC3506zS m;
    protected transient InterfaceC3088tr n;

    public C3505zR(int i) {
        this.f = -1;
        this.g = -1;
        if (i < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        int iA = AbstractC3307wk.a(i, 0.75f);
        this.i = iA;
        this.d = iA - 1;
        this.j = AbstractC3307wk.b(iA, 0.75f);
        int i2 = this.i + 1;
        this.b = new Object[i2];
        this.c = new int[i2];
        this.h = new long[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int h(int i) {
        Object obj;
        int i2;
        int i3 = this.c[i];
        this.k--;
        f(i);
        Object[] objArr = this.b;
        loop0: while (true) {
            int i4 = (i + 1) & this.d;
            while (true) {
                obj = objArr[i4];
                if (obj != null) {
                    int iA = AbstractC3307wk.a(System.identityHashCode(obj));
                    int i5 = this.d;
                    int i6 = iA & i5;
                    if (i > i4) {
                        if (i >= i6 && i6 > i4) {
                            break;
                        }
                        i4 = (i4 + 1) & i5;
                    } else {
                        if (i >= i6 || i6 > i4) {
                            break;
                        }
                        i4 = (i4 + 1) & i5;
                    }
                } else {
                    break loop0;
                }
            }
            objArr[i] = obj;
            int[] iArr = this.c;
            iArr[i] = iArr[i4];
            c(i4, i);
            i = i4;
        }
        objArr[i] = null;
        if (this.k < this.j / 4 && (i2 = this.i) > 16) {
            g(i2 / 2);
        }
        return i3;
    }

    protected final void c(int i, int i2) {
        if (this.k == 1) {
            this.g = i2;
            this.f = i2;
            this.h[i2] = -1;
            return;
        }
        if (this.f == i) {
            this.f = i2;
            long[] jArr = this.h;
            int i3 = (int) jArr[i];
            long j = jArr[i3];
            jArr[i3] = ((-4294967296L) & (((4294967295L & ((long) i2)) << 32) ^ j)) ^ j;
            jArr[i2] = jArr[i];
            return;
        }
        if (this.g == i) {
            this.g = i2;
            long[] jArr2 = this.h;
            int i4 = (int) (jArr2[i] >>> 32);
            long j2 = jArr2[i4];
            jArr2[i4] = j2 ^ (4294967295L & ((((long) i2) & 4294967295L) ^ j2));
            jArr2[i2] = jArr2[i];
            return;
        }
        long[] jArr3 = this.h;
        long j3 = jArr3[i];
        int i5 = (int) (j3 >>> 32);
        int i6 = (int) j3;
        long j4 = jArr3[i5];
        long j5 = ((long) i2) & 4294967295L;
        jArr3[i5] = ((j4 ^ j5) & 4294967295L) ^ j4;
        long j6 = jArr3[i6];
        jArr3[i6] = ((-4294967296L) & ((j5 << 32) ^ j6)) ^ j6;
        jArr3[i2] = j3;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        if (this.k == 0) {
            return;
        }
        this.k = 0;
        this.e = false;
        Arrays.fill(this.b, (Object) null);
        this.g = -1;
        this.f = -1;
    }

    public final Object clone() {
        try {
            C3505zR c3505zR = (C3505zR) super.clone();
            c3505zR.m = null;
            c3505zR.n = null;
            c3505zR.l = null;
            c3505zR.e = this.e;
            c3505zR.b = (Object[]) this.b.clone();
            c3505zR.c = (int[]) this.c.clone();
            c3505zR.h = (long[]) this.h.clone();
            return c3505zR;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // java.util.SortedMap
    public final Comparator comparator() {
        return null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        Object obj2;
        if (obj == null) {
            return this.e;
        }
        Object[] objArr = this.b;
        int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.d;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return false;
        }
        if (obj == obj3) {
            return true;
        }
        do {
            iA = (iA + 1) & this.d;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return false;
            }
        } while (obj != obj2);
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2581n1
    public final int d(Object obj) {
        Object obj2;
        int i;
        if (obj == null) {
            if (!this.e) {
                return this.a;
            }
            this.e = false;
            Object[] objArr = this.b;
            int i2 = this.i;
            objArr[i2] = null;
            int i3 = this.c[i2];
            this.k--;
            f(i2);
            if (this.k < this.j / 4 && (i = this.i) > 16) {
                g(i / 2);
            }
            return i3;
        }
        Object[] objArr2 = this.b;
        int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.d;
        Object obj3 = objArr2[iA];
        if (obj3 == null) {
            return this.a;
        }
        if (obj == obj3) {
            return h(iA);
        }
        do {
            iA = (iA + 1) & this.d;
            obj2 = objArr2[iA];
            if (obj2 == null) {
                return this.a;
            }
        } while (obj != obj2);
        return h(iA);
    }

    @Override // java.util.SortedMap
    public final Object firstKey() {
        if (this.k != 0) {
            return this.b[this.f];
        }
        throw new NoSuchElementException();
    }

    protected final void g(int i) {
        int i2;
        Object[] objArr = this.b;
        int[] iArr = this.c;
        int i3 = i - 1;
        int i4 = i + 1;
        Object[] objArr2 = new Object[i4];
        int[] iArr2 = new int[i4];
        int i5 = this.f;
        long[] jArr = this.h;
        long[] jArr2 = new long[i4];
        int i6 = -1;
        this.f = -1;
        int i7 = this.k;
        int i8 = -1;
        int i9 = -1;
        while (true) {
            int i10 = i7 - 1;
            if (i7 == 0) {
                break;
            }
            Object obj = objArr[i5];
            if (obj != null) {
                int iA = AbstractC3307wk.a(System.identityHashCode(obj));
                while (true) {
                    i2 = iA & i3;
                    if (objArr2[i2] == null) {
                        break;
                    } else {
                        iA = i2 + 1;
                    }
                }
            } else {
                i2 = i;
            }
            objArr2[i2] = objArr[i5];
            iArr2[i2] = iArr[i5];
            if (i9 != i6) {
                long j = jArr2[i8];
                jArr2[i8] = j ^ ((j ^ (((long) i2) & 4294967295L)) & 4294967295L);
                long j2 = jArr2[i2];
                int i11 = i2;
                jArr2[i11] = j2 ^ ((((((long) i8) & 4294967295L) << 32) ^ j2) & (-4294967296L));
                i2 = i11;
            } else {
                this.f = i2;
                jArr2[i2] = -1;
            }
            i8 = i2;
            i7 = i10;
            iArr = iArr;
            i6 = -1;
            int i12 = i5;
            i5 = (int) jArr[i5];
            objArr = objArr;
            i9 = i12;
        }
        this.h = jArr2;
        this.g = i8;
        if (i8 != -1) {
            jArr2[i8] = jArr2[i8] | 4294967295L;
        }
        this.i = i;
        this.d = i3;
        this.j = AbstractC3307wk.b(i, 0.75f);
        this.b = objArr2;
        this.c = iArr2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2581n1, java.util.Map
    public final int hashCode() {
        Object obj;
        int i = this.e ? this.k - 1 : this.k;
        int i2 = 0;
        int i3 = 0;
        int iIdentityHashCode = 0;
        while (true) {
            int i4 = i - 1;
            if (i == 0) {
                break;
            }
            while (true) {
                obj = this.b[i2];
                if (obj != null) {
                    break;
                }
                i2++;
            }
            if (this != obj) {
                iIdentityHashCode = System.identityHashCode(obj);
            }
            iIdentityHashCode ^= this.c[i2];
            i3 += iIdentityHashCode;
            i2++;
            i = i4;
        }
        return this.e ? i3 + this.c[this.i] : i3;
    }

    @Override // java.util.SortedMap
    public final SortedMap headMap(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.k == 0;
    }

    @Override // java.util.Map, java.util.SortedMap
    /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
    public final InterfaceC3506zS keySet() {
        if (this.m == null) {
            this.m = new C3128uR(this, 0);
        }
        return this.m;
    }

    @Override // com.android.tools.r8.internal.CR
    /* JADX INFO: renamed from: l, reason: merged with bridge method [inline-methods] */
    public final NR f() {
        if (this.l == null) {
            this.l = new C3288wR(this, 0);
        }
        return this.l;
    }

    @Override // java.util.SortedMap
    public final Object lastKey() {
        if (this.k != 0) {
            return this.b[this.g];
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.AbstractC2581n1, java.util.Map
    public final void putAll(Map map) {
        if (0.75f <= 0.5d) {
            int iA = AbstractC3307wk.a(map.size(), 0.75f);
            if (iA > this.i) {
                g(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((map.size() + this.k) / 0.75f), 2L, 1073741824L);
            if (iA2 > this.i) {
                g(iA2);
            }
        }
        super.putAll(map);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.k;
    }

    @Override // java.util.SortedMap
    public final SortedMap subMap(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.SortedMap
    public final SortedMap tailMap(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, java.util.SortedMap
    public final Collection values() {
        if (this.n == null) {
            this.n = new C2921rR(this);
        }
        return this.n;
    }

    static void a(C3505zR c3505zR) {
        int i;
        c3505zR.e = false;
        Object[] objArr = c3505zR.b;
        int i2 = c3505zR.i;
        objArr[i2] = null;
        int i3 = c3505zR.c[i2];
        c3505zR.k--;
        c3505zR.f(i2);
        if (c3505zR.k >= c3505zR.j / 4 || (i = c3505zR.i) <= 16) {
            return;
        }
        c3505zR.g(i / 2);
    }

    protected final void f(int i) {
        if (this.k == 0) {
            this.g = -1;
            this.f = -1;
            return;
        }
        if (this.f == i) {
            long[] jArr = this.h;
            int i2 = (int) jArr[i];
            this.f = i2;
            if (i2 >= 0) {
                jArr[i2] = (-4294967296L) | jArr[i2];
                return;
            }
            return;
        }
        if (this.g == i) {
            long[] jArr2 = this.h;
            int i3 = (int) (jArr2[i] >>> 32);
            this.g = i3;
            if (i3 >= 0) {
                jArr2[i3] = jArr2[i3] | 4294967295L;
                return;
            }
            return;
        }
        long[] jArr3 = this.h;
        long j = jArr3[i];
        int i4 = (int) (j >>> 32);
        int i5 = (int) j;
        long j2 = jArr3[i4];
        jArr3[i4] = (4294967295L & ((j & 4294967295L) ^ j2)) ^ j2;
        long j3 = jArr3[i5];
        jArr3[i5] = ((-4294967296L) & ((j & (-4294967296L)) ^ j3)) ^ j3;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x003d  */
    /* JADX WARN: Code duplicated, block: B:20:0x0048  */
    /* JADX WARN: Code duplicated, block: B:23:0x006c  */
    @Override // com.android.tools.r8.internal.AbstractC2581n1, com.android.tools.r8.internal.InterfaceC2849qR
    public final int a(int i, Object obj) {
        int i2;
        int i3;
        int i4;
        if (obj == null) {
            if (this.e) {
                i3 = this.i;
            } else {
                this.e = true;
                i2 = this.i;
                this.b[i2] = obj;
                this.c[i2] = i;
                i4 = this.k;
                if (i4 == 0) {
                    this.g = i2;
                    this.f = i2;
                    this.h[i2] = -1;
                } else {
                    long[] jArr = this.h;
                    int i5 = this.g;
                    long j = jArr[i5];
                    jArr[i5] = j ^ (((((long) i2) & 4294967295L) ^ j) & 4294967295L);
                    jArr[i2] = ((((long) i5) & 4294967295L) << 32) | 4294967295L;
                    this.g = i2;
                }
                this.k = i4 + 1;
                if (i4 >= this.j) {
                    g(AbstractC3307wk.a(i4 + 2, 0.75f));
                }
                i3 = -1;
            }
        } else {
            Object[] objArr = this.b;
            int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.d;
            Object obj2 = objArr[iA];
            if (obj2 != null) {
                if (obj2 != obj) {
                    while (true) {
                        iA = (iA + 1) & this.d;
                        Object obj3 = objArr[iA];
                        if (obj3 != null) {
                            if (obj3 == obj) {
                            }
                        }
                    }
                }
                i3 = iA;
            }
            i2 = iA;
            this.b[i2] = obj;
            this.c[i2] = i;
            i4 = this.k;
            if (i4 == 0) {
                this.g = i2;
                this.f = i2;
                this.h[i2] = -1;
            } else {
                long[] jArr2 = this.h;
                int i6 = this.g;
                long j2 = jArr2[i6];
                jArr2[i6] = j2 ^ (((((long) i2) & 4294967295L) ^ j2) & 4294967295L);
                jArr2[i2] = ((((long) i6) & 4294967295L) << 32) | 4294967295L;
                this.g = i2;
            }
            this.k = i4 + 1;
            if (i4 >= this.j) {
                g(AbstractC3307wk.a(i4 + 2, 0.75f));
            }
            i3 = -1;
        }
        if (i3 < 0) {
            return this.a;
        }
        int[] iArr = this.c;
        int i7 = iArr[i3];
        iArr[i3] = i;
        return i7;
    }

    @Override // com.android.tools.r8.internal.AbstractC2581n1
    public final boolean d(int i) {
        int[] iArr = this.c;
        Object[] objArr = this.b;
        if (this.e && iArr[this.i] == i) {
            return true;
        }
        int i2 = this.i;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return false;
            }
            if (objArr[i3] != null && iArr[i3] == i) {
                return true;
            }
            i2 = i3;
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2849qR
    public final int a(Object obj) {
        Object obj2;
        if (obj == null) {
            return this.e ? this.c[this.i] : this.a;
        }
        Object[] objArr = this.b;
        int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.d;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return this.a;
        }
        if (obj == obj3) {
            return this.c[iA];
        }
        do {
            iA = (iA + 1) & this.d;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return this.a;
            }
        } while (obj != obj2);
        return this.c[iA];
    }

    public C3505zR() {
        this(16);
    }
}
