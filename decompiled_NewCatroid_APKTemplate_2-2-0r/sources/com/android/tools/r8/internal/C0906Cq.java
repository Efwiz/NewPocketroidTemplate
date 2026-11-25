package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0906Cq extends I implements Cloneable {
    protected transient int[] b;
    protected transient Object[] c;
    protected transient int d;
    protected transient boolean e;
    protected transient int f = -1;
    protected transient int g = -1;
    protected transient long[] h;
    protected transient int i;
    protected transient int j;
    protected int k;
    protected transient InterfaceC2335jr l;
    protected transient InterfaceC1357Sr m;
    protected transient InterfaceC2703oS n;

    public C0906Cq() {
        int iA = AbstractC3307wk.a(16, 0.75f);
        this.i = iA;
        this.d = iA - 1;
        this.j = AbstractC3307wk.b(iA, 0.75f);
        int i = this.i + 1;
        this.b = new int[i];
        this.c = new Object[i];
        this.h = new long[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object f(int i) {
        int i2;
        int i3;
        Object[] objArr = this.c;
        Object obj = objArr[i];
        objArr[i] = null;
        this.k--;
        d(i);
        int[] iArr = this.b;
        loop0: while (true) {
            int i4 = (i + 1) & this.d;
            while (true) {
                i2 = iArr[i4];
                if (i2 != 0) {
                    int iA = AbstractC3307wk.a(i2);
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
            iArr[i] = i2;
            Object[] objArr2 = this.c;
            objArr2[i] = objArr2[i4];
            c(i4, i);
            i = i4;
        }
        iArr[i] = 0;
        this.c[i] = null;
        if (this.k < this.j / 4 && (i3 = this.i) > 16) {
            e(i3 / 2);
        }
        return obj;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr b(int i) {
        throw new UnsupportedOperationException();
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

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        if (this.k == 0) {
            return;
        }
        this.k = 0;
        this.e = false;
        Arrays.fill(this.b, 0);
        Arrays.fill(this.c, (Object) null);
        this.g = -1;
        this.f = -1;
    }

    public final Object clone() {
        try {
            C0906Cq c0906Cq = (C0906Cq) super.clone();
            c0906Cq.m = null;
            c0906Cq.n = null;
            c0906Cq.l = null;
            c0906Cq.e = this.e;
            c0906Cq.b = (int[]) this.b.clone();
            c0906Cq.c = (Object[]) this.c.clone();
            c0906Cq.h = (long[]) this.h.clone();
            return c0906Cq;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC3391xr comparator() {
        return null;
    }

    @Override // java.util.SortedMap
    public final /* bridge */ /* synthetic */ Comparator comparator() {
        return null;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        Object[] objArr = this.c;
        int[] iArr = this.b;
        if (this.e && objArr[this.i] == obj) {
            return true;
        }
        int i = this.i;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (iArr[i2] != 0 && objArr[i2] == obj) {
                return true;
            }
            i = i2;
        }
    }

    protected final void d(int i) {
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

    protected final void e(int i) {
        int i2;
        int[] iArr = this.b;
        Object[] objArr = this.c;
        int i3 = i - 1;
        int i4 = i + 1;
        int[] iArr2 = new int[i4];
        Object[] objArr2 = new Object[i4];
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
            int i11 = iArr[i5];
            if (i11 != 0) {
                int iA = AbstractC3307wk.a(i11);
                while (true) {
                    i2 = iA & i3;
                    if (iArr2[i2] == 0) {
                        break;
                    } else {
                        iA = i2 + 1;
                    }
                }
            } else {
                i2 = i;
            }
            iArr2[i2] = iArr[i5];
            objArr2[i2] = objArr[i5];
            if (i9 != i6) {
                long j = jArr2[i8];
                jArr2[i8] = j ^ ((j ^ (((long) i2) & 4294967295L)) & 4294967295L);
                long j2 = jArr2[i2];
                int i12 = i2;
                jArr2[i12] = j2 ^ ((((((long) i8) & 4294967295L) << 32) ^ j2) & (-4294967296L));
                i2 = i12;
            } else {
                this.f = i2;
                jArr2[i2] = -1;
            }
            i8 = i2;
            i7 = i10;
            objArr = objArr;
            i6 = -1;
            int i13 = i5;
            i5 = (int) jArr[i5];
            iArr = iArr;
            i9 = i13;
        }
        this.h = jArr2;
        this.g = i8;
        if (i8 != -1) {
            jArr2[i8] = jArr2[i8] | 4294967295L;
        }
        this.i = i;
        this.d = i3;
        this.j = AbstractC3307wk.b(i, 0.75f);
        this.b = iArr2;
        this.c = objArr2;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3087tq
    public final Object get(int i) {
        int i2;
        if (i == 0) {
            return this.e ? this.c[this.i] : this.a;
        }
        int[] iArr = this.b;
        int iA = AbstractC3307wk.a(i) & this.d;
        int i3 = iArr[iA];
        if (i3 == 0) {
            return this.a;
        }
        if (i == i3) {
            return this.c[iA];
        }
        do {
            iA = (iA + 1) & this.d;
            i2 = iArr[iA];
            if (i2 == 0) {
                return this.a;
            }
        } while (i != i2);
        return this.c[iA];
    }

    @Override // com.android.tools.r8.internal.G, java.util.Map
    public final int hashCode() {
        int iIdentityHashCode;
        int i = this.e ? this.k - 1 : this.k;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = i - 1;
            if (i == 0) {
                break;
            }
            while (true) {
                iIdentityHashCode = this.b[i2];
                if (iIdentityHashCode != 0) {
                    break;
                }
                i2++;
            }
            Object obj = this.c[i2];
            if (this != obj) {
                iIdentityHashCode ^= obj == null ? 0 : System.identityHashCode(obj);
            }
            i3 += iIdentityHashCode;
            i2++;
            i = i4;
        }
        if (!this.e) {
            return i3;
        }
        Object obj2 = this.c[this.i];
        return i3 + (obj2 != null ? System.identityHashCode(obj2) : 0);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.k == 0;
    }

    @Override // com.android.tools.r8.internal.I, com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        if (this.m == null) {
            this.m = new C3390xq(this, 0);
        }
        return this.m;
    }

    @Override // com.android.tools.r8.internal.G, java.util.Map
    public final void putAll(Map map) {
        if (0.75f <= 0.5d) {
            int iA = AbstractC3307wk.a(map.size(), 0.75f);
            if (iA > this.i) {
                e(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((map.size() + this.k) / 0.75f), 2L, 1073741824L);
            if (iA2 > this.i) {
                e(iA2);
            }
        }
        super.putAll(map);
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object remove(int i) {
        int i2;
        int i3;
        if (i == 0) {
            if (!this.e) {
                return this.a;
            }
            this.e = false;
            Object[] objArr = this.c;
            int i4 = this.i;
            Object obj = objArr[i4];
            objArr[i4] = null;
            this.k--;
            d(i4);
            if (this.k < this.j / 4 && (i3 = this.i) > 16) {
                e(i3 / 2);
            }
            return obj;
        }
        int[] iArr = this.b;
        int iA = AbstractC3307wk.a(i) & this.d;
        int i5 = iArr[iA];
        if (i5 == 0) {
            return this.a;
        }
        if (i == i5) {
            return f(iA);
        }
        do {
            iA = (iA + 1) & this.d;
            i2 = iArr[iA];
            if (i2 == 0) {
                return this.a;
            }
        } while (i != i2);
        return f(iA);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.k;
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC2703oS values() {
        if (this.n == null) {
            this.n = new C3164uq(this);
        }
        return this.n;
    }

    static void a(C0906Cq c0906Cq) {
        int i;
        c0906Cq.e = false;
        Object[] objArr = c0906Cq.c;
        int i2 = c0906Cq.i;
        Object obj = objArr[i2];
        objArr[i2] = null;
        c0906Cq.k--;
        c0906Cq.d(i2);
        if (c0906Cq.k >= c0906Cq.j / 4 || (i = c0906Cq.i) <= 16) {
            return;
        }
        c0906Cq.e(i / 2);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0038  */
    /* JADX WARN: Code duplicated, block: B:20:0x0043  */
    /* JADX WARN: Code duplicated, block: B:23:0x0067  */
    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object a(int i, Object obj) {
        int i2;
        int i3;
        int i4;
        if (i == 0) {
            if (this.e) {
                i3 = this.i;
            } else {
                this.e = true;
                i2 = this.i;
                this.b[i2] = i;
                this.c[i2] = obj;
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
                    e(AbstractC3307wk.a(i4 + 2, 0.75f));
                }
                i3 = -1;
            }
        } else {
            int[] iArr = this.b;
            int iA = AbstractC3307wk.a(i) & this.d;
            int i6 = iArr[iA];
            if (i6 != 0) {
                if (i6 != i) {
                    while (true) {
                        iA = (iA + 1) & this.d;
                        int i7 = iArr[iA];
                        if (i7 != 0) {
                            if (i7 == i) {
                            }
                        }
                    }
                }
                i3 = iA;
            }
            i2 = iA;
            this.b[i2] = i;
            this.c[i2] = obj;
            i4 = this.k;
            if (i4 == 0) {
                this.g = i2;
                this.f = i2;
                this.h[i2] = -1;
            } else {
                long[] jArr2 = this.h;
                int i8 = this.g;
                long j2 = jArr2[i8];
                jArr2[i8] = j2 ^ (((((long) i2) & 4294967295L) ^ j2) & 4294967295L);
                jArr2[i2] = ((((long) i8) & 4294967295L) << 32) | 4294967295L;
                this.g = i2;
            }
            this.k = i4 + 1;
            if (i4 >= this.j) {
                e(AbstractC3307wk.a(i4 + 2, 0.75f));
            }
            i3 = -1;
        }
        if (i3 < 0) {
            return this.a;
        }
        Object[] objArr = this.c;
        Object obj2 = objArr[i3];
        objArr[i3] = obj;
        return obj2;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr c(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.I, com.android.tools.r8.internal.InterfaceC0992Fq
    public final InterfaceC2529mK c() {
        if (this.l == null) {
            this.l = new C3530zq(this, 0);
        }
        return this.l;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final int d() {
        if (this.k != 0) {
            return this.b[this.g];
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.I, com.android.tools.r8.internal.InterfaceC0992Fq
    public final InterfaceC2842qK c() {
        if (this.l == null) {
            this.l = new C3530zq(this, 0);
        }
        return this.l;
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC3087tq
    public final boolean a(int i) {
        int i2;
        if (i == 0) {
            return this.e;
        }
        int[] iArr = this.b;
        int iA = AbstractC3307wk.a(i) & this.d;
        int i3 = iArr[iA];
        if (i3 == 0) {
            return false;
        }
        if (i == i3) {
            return true;
        }
        do {
            iA = (iA + 1) & this.d;
            i2 = iArr[iA];
            if (i2 == 0) {
                return false;
            }
        } while (i != i2);
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final int a() {
        if (this.k != 0) {
            return this.b[this.f];
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr a(int i, int i2) {
        throw new UnsupportedOperationException();
    }
}
