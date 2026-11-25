package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class OJ extends T0 implements Cloneable {
    protected transient Object[] a;
    protected transient Object[] b;
    protected transient int c;
    protected transient boolean d;
    protected transient int e = -1;
    protected transient int f = -1;
    protected transient long[] g;
    protected transient int h;
    protected transient int i;
    protected int j;
    protected transient RJ k;
    protected transient InterfaceC2842qK l;
    protected transient InterfaceC2703oS m;

    public OJ(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        int iA = AbstractC3307wk.a(i, 0.75f);
        this.h = iA;
        this.c = iA - 1;
        this.i = AbstractC3307wk.b(iA, 0.75f);
        int i2 = this.h + 1;
        this.a = new Object[i2];
        this.b = new Object[i2];
        this.g = new long[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object f(int i) {
        Object obj;
        int i2;
        Object[] objArr = this.b;
        Object obj2 = objArr[i];
        objArr[i] = null;
        this.j--;
        d(i);
        Object[] objArr2 = this.a;
        loop0: while (true) {
            int i3 = (i + 1) & this.c;
            while (true) {
                obj = objArr2[i3];
                if (obj != null) {
                    int iA = AbstractC3307wk.a(obj.hashCode());
                    int i4 = this.c;
                    int i5 = iA & i4;
                    if (i > i3) {
                        if (i >= i5 && i5 > i3) {
                            break;
                        }
                        i3 = (i3 + 1) & i4;
                    } else {
                        if (i >= i5 || i5 > i3) {
                            break;
                        }
                        i3 = (i3 + 1) & i4;
                    }
                } else {
                    break loop0;
                }
            }
            objArr2[i] = obj;
            Object[] objArr3 = this.b;
            objArr3[i] = objArr3[i3];
            c(i3, i);
            i = i3;
        }
        objArr2[i] = null;
        this.b[i] = null;
        if (this.j < this.i / 4 && (i2 = this.h) > 16) {
            e(i2 / 2);
        }
        return obj2;
    }

    protected final void c(int i, int i2) {
        if (this.j == 1) {
            this.f = i2;
            this.e = i2;
            this.g[i2] = -1;
            return;
        }
        if (this.e == i) {
            this.e = i2;
            long[] jArr = this.g;
            int i3 = (int) jArr[i];
            long j = jArr[i3];
            jArr[i3] = ((-4294967296L) & (((4294967295L & ((long) i2)) << 32) ^ j)) ^ j;
            jArr[i2] = jArr[i];
            return;
        }
        if (this.f == i) {
            this.f = i2;
            long[] jArr2 = this.g;
            int i4 = (int) (jArr2[i] >>> 32);
            long j2 = jArr2[i4];
            jArr2[i4] = j2 ^ (4294967295L & ((((long) i2) & 4294967295L) ^ j2));
            jArr2[i2] = jArr2[i];
            return;
        }
        long[] jArr3 = this.g;
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
        if (this.j == 0) {
            return;
        }
        this.j = 0;
        this.d = false;
        Arrays.fill(this.a, (Object) null);
        Arrays.fill(this.b, (Object) null);
        this.f = -1;
        this.e = -1;
    }

    public final Object clone() {
        try {
            OJ oj = (OJ) super.clone();
            oj.l = null;
            oj.m = null;
            oj.k = null;
            oj.d = this.d;
            oj.a = (Object[]) this.a.clone();
            oj.b = (Object[]) this.b.clone();
            oj.g = (long[]) this.g.clone();
            return oj;
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
            return this.d;
        }
        Object[] objArr = this.a;
        int iA = AbstractC3307wk.a(obj.hashCode()) & this.c;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return false;
        }
        if (obj.equals(obj3)) {
            return true;
        }
        do {
            iA = (iA + 1) & this.c;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return false;
            }
        } while (!obj.equals(obj2));
        return true;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        Object[] objArr = this.b;
        Object[] objArr2 = this.a;
        if (this.d && objArr[this.h] == obj) {
            return true;
        }
        int i = this.h;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (objArr2[i2] != null && objArr[i2] == obj) {
                return true;
            }
            i = i2;
        }
    }

    protected final void d(int i) {
        if (this.j == 0) {
            this.f = -1;
            this.e = -1;
            return;
        }
        if (this.e == i) {
            long[] jArr = this.g;
            int i2 = (int) jArr[i];
            this.e = i2;
            if (i2 >= 0) {
                jArr[i2] = (-4294967296L) | jArr[i2];
                return;
            }
            return;
        }
        if (this.f == i) {
            long[] jArr2 = this.g;
            int i3 = (int) (jArr2[i] >>> 32);
            this.f = i3;
            if (i3 >= 0) {
                jArr2[i3] = jArr2[i3] | 4294967295L;
                return;
            }
            return;
        }
        long[] jArr3 = this.g;
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
        Object[] objArr = this.a;
        Object[] objArr2 = this.b;
        int i3 = i - 1;
        int i4 = i + 1;
        Object[] objArr3 = new Object[i4];
        Object[] objArr4 = new Object[i4];
        int i5 = this.e;
        long[] jArr = this.g;
        long[] jArr2 = new long[i4];
        int i6 = -1;
        this.e = -1;
        int i7 = this.j;
        int i8 = -1;
        int i9 = -1;
        while (true) {
            int i10 = i7 - 1;
            if (i7 == 0) {
                break;
            }
            Object obj = objArr[i5];
            if (obj != null) {
                int iA = AbstractC3307wk.a(obj.hashCode());
                while (true) {
                    i2 = iA & i3;
                    if (objArr3[i2] == null) {
                        break;
                    } else {
                        iA = i2 + 1;
                    }
                }
            } else {
                i2 = i;
            }
            objArr3[i2] = objArr[i5];
            objArr4[i2] = objArr2[i5];
            if (i9 != i6) {
                long j = jArr2[i8];
                jArr2[i8] = j ^ ((j ^ (((long) i2) & 4294967295L)) & 4294967295L);
                long j2 = jArr2[i2];
                int i11 = i2;
                jArr2[i11] = j2 ^ ((((((long) i8) & 4294967295L) << 32) ^ j2) & (-4294967296L));
                i2 = i11;
            } else {
                this.e = i2;
                jArr2[i2] = -1;
            }
            i8 = i2;
            i7 = i10;
            objArr2 = objArr2;
            i6 = -1;
            int i12 = i5;
            i5 = (int) jArr[i5];
            objArr = objArr;
            i9 = i12;
        }
        this.g = jArr2;
        this.f = i8;
        if (i8 != -1) {
            jArr2[i8] = jArr2[i8] | 4294967295L;
        }
        this.h = i;
        this.c = i3;
        this.i = AbstractC3307wk.b(i, 0.75f);
        this.a = objArr3;
        this.b = objArr4;
    }

    @Override // java.util.SortedMap
    public final Object firstKey() {
        if (this.j != 0) {
            return this.a[this.e];
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        Object obj2;
        if (obj == null) {
            if (this.d) {
                return this.b[this.h];
            }
            return null;
        }
        Object[] objArr = this.a;
        int iA = AbstractC3307wk.a(obj.hashCode()) & this.c;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return null;
        }
        if (obj.equals(obj3)) {
            return this.b[iA];
        }
        do {
            iA = (iA + 1) & this.c;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return null;
            }
        } while (!obj.equals(obj2));
        return this.b[iA];
    }

    @Override // java.util.Map
    public final int hashCode() {
        Object obj;
        int i = this.d ? this.j - 1 : this.j;
        int i2 = 0;
        int i3 = 0;
        int iIdentityHashCode = 0;
        while (true) {
            int i4 = i - 1;
            if (i == 0) {
                break;
            }
            while (true) {
                obj = this.a[i2];
                if (obj != null) {
                    break;
                }
                i2++;
            }
            if (this != obj) {
                iIdentityHashCode = obj.hashCode();
            }
            Object obj2 = this.b[i2];
            if (this != obj2) {
                iIdentityHashCode = (obj2 == null ? 0 : System.identityHashCode(obj2)) ^ iIdentityHashCode;
            }
            i3 += iIdentityHashCode;
            i2++;
            i = i4;
        }
        if (!this.d) {
            return i3;
        }
        Object obj3 = this.b[this.h];
        return i3 + (obj3 != null ? System.identityHashCode(obj3) : 0);
    }

    @Override // java.util.SortedMap
    public final SortedMap headMap(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.j == 0;
    }

    @Override // java.util.Map, java.util.SortedMap
    public final Set keySet() {
        if (this.l == null) {
            this.l = new JJ(this, 0);
        }
        return this.l;
    }

    @Override // java.util.SortedMap
    public final Object lastKey() {
        if (this.j != 0) {
            return this.a[this.f];
        }
        throw new NoSuchElementException();
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0045  */
    /* JADX WARN: Code duplicated, block: B:22:0x0050  */
    /* JADX WARN: Code duplicated, block: B:25:0x0074  */
    @Override // com.android.tools.r8.internal.S0, java.util.Map
    public final Object put(Object obj, Object obj2) {
        int i;
        int i2;
        int i3;
        if (obj != null) {
            Object[] objArr = this.a;
            int iA = AbstractC3307wk.a(obj.hashCode()) & this.c;
            Object obj3 = objArr[iA];
            if (obj3 != null) {
                if (!obj3.equals(obj)) {
                    while (true) {
                        iA = (iA + 1) & this.c;
                        Object obj4 = objArr[iA];
                        if (obj4 != null) {
                            if (obj4.equals(obj)) {
                            }
                        }
                    }
                }
                i2 = iA;
            }
            i = iA;
            this.a[i] = obj;
            this.b[i] = obj2;
            i3 = this.j;
            if (i3 == 0) {
                this.f = i;
                this.e = i;
                this.g[i] = -1;
            } else {
                long[] jArr = this.g;
                int i4 = this.f;
                long j = jArr[i4];
                jArr[i4] = j ^ (((((long) i) & 4294967295L) ^ j) & 4294967295L);
                jArr[i] = ((((long) i4) & 4294967295L) << 32) | 4294967295L;
                this.f = i;
            }
            this.j = i3 + 1;
            if (i3 >= this.i) {
                e(AbstractC3307wk.a(i3 + 2, 0.75f));
            }
            i2 = -1;
        } else if (this.d) {
            i2 = this.h;
        } else {
            this.d = true;
            i = this.h;
            this.a[i] = obj;
            this.b[i] = obj2;
            i3 = this.j;
            if (i3 == 0) {
                this.f = i;
                this.e = i;
                this.g[i] = -1;
            } else {
                long[] jArr2 = this.g;
                int i5 = this.f;
                long j2 = jArr2[i5];
                jArr2[i5] = j2 ^ (((((long) i) & 4294967295L) ^ j2) & 4294967295L);
                jArr2[i] = ((((long) i5) & 4294967295L) << 32) | 4294967295L;
                this.f = i;
            }
            this.j = i3 + 1;
            if (i3 >= this.i) {
                e(AbstractC3307wk.a(i3 + 2, 0.75f));
            }
            i2 = -1;
        }
        if (i2 < 0) {
            return null;
        }
        Object[] objArr2 = this.b;
        Object obj5 = objArr2[i2];
        objArr2[i2] = obj2;
        return obj5;
    }

    @Override // com.android.tools.r8.internal.S0, java.util.Map
    public final void putAll(Map map) {
        if (0.75f <= 0.5d) {
            int iA = AbstractC3307wk.a(map.size(), 0.75f);
            if (iA > this.h) {
                e(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((map.size() + this.j) / 0.75f), 2L, 1073741824L);
            if (iA2 > this.h) {
                e(iA2);
            }
        }
        super.putAll(map);
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        Object obj2;
        int i;
        if (obj != null) {
            Object[] objArr = this.a;
            int iA = AbstractC3307wk.a(obj.hashCode()) & this.c;
            Object obj3 = objArr[iA];
            if (obj3 == null) {
                return null;
            }
            if (obj.equals(obj3)) {
                return f(iA);
            }
            do {
                iA = (iA + 1) & this.c;
                obj2 = objArr[iA];
                if (obj2 == null) {
                    return null;
                }
            } while (!obj.equals(obj2));
            return f(iA);
        }
        if (!this.d) {
            return null;
        }
        this.d = false;
        Object[] objArr2 = this.a;
        int i2 = this.h;
        objArr2[i2] = null;
        Object[] objArr3 = this.b;
        Object obj4 = objArr3[i2];
        objArr3[i2] = null;
        this.j--;
        d(i2);
        if (this.j < this.i / 4 && (i = this.h) > 16) {
            e(i / 2);
        }
        return obj4;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.j;
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
        if (this.m == null) {
            this.m = new GJ(this);
        }
        return this.m;
    }

    static void a(OJ oj) {
        int i;
        oj.d = false;
        Object[] objArr = oj.a;
        int i2 = oj.h;
        objArr[i2] = null;
        Object[] objArr2 = oj.b;
        Object obj = objArr2[i2];
        objArr2[i2] = null;
        oj.j--;
        oj.d(i2);
        if (oj.j >= oj.i / 4 || (i = oj.h) <= 16) {
            return;
        }
        oj.e(i / 2);
    }
}
