package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kJ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2375kJ extends R0 implements Cloneable {
    protected transient Object[] b;
    protected transient Object[] c;
    protected transient int d;
    protected transient boolean e;
    protected InterfaceC2101gk f;
    protected transient int g = -1;
    protected transient int h = -1;
    protected transient long[] i;
    protected transient int j;
    protected transient int k;
    protected int l;
    protected transient FJ m;
    protected transient InterfaceC2842qK n;
    protected transient ZJ o;

    public C2375kJ(InterfaceC2101gk interfaceC2101gk, int i) {
        this.f = interfaceC2101gk;
        int iA = AbstractC3307wk.a(16, 0.75f);
        this.j = iA;
        this.d = iA - 1;
        this.k = AbstractC3307wk.b(iA, 0.75f);
        int i2 = this.j + 1;
        this.b = new Object[i2];
        this.c = new Object[i2];
        this.i = new long[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object f(int i) {
        Object obj;
        int i2;
        Object[] objArr = this.c;
        Object obj2 = objArr[i];
        objArr[i] = null;
        this.l--;
        d(i);
        Object[] objArr2 = this.b;
        loop0: while (true) {
            int i3 = (i + 1) & this.d;
            while (true) {
                obj = objArr2[i3];
                if (obj != null) {
                    int iA = AbstractC3307wk.a(this.f.a(obj));
                    int i4 = this.d;
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
            Object[] objArr3 = this.c;
            objArr3[i] = objArr3[i3];
            c(i3, i);
            i = i3;
        }
        objArr2[i] = null;
        this.c[i] = null;
        if (this.l < this.k / 4 && (i2 = this.j) > 16) {
            e(i2 / 2);
        }
        return obj2;
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: b */
    public final R0 subMap(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    protected final void c(int i, int i2) {
        if (this.l == 1) {
            this.h = i2;
            this.g = i2;
            this.i[i2] = -1;
            return;
        }
        if (this.g == i) {
            this.g = i2;
            long[] jArr = this.i;
            int i3 = (int) jArr[i];
            long j = jArr[i3];
            jArr[i3] = ((-4294967296L) & (((4294967295L & ((long) i2)) << 32) ^ j)) ^ j;
            jArr[i2] = jArr[i];
            return;
        }
        if (this.h == i) {
            this.h = i2;
            long[] jArr2 = this.i;
            int i4 = (int) (jArr2[i] >>> 32);
            long j2 = jArr2[i4];
            jArr2[i4] = j2 ^ (4294967295L & ((((long) i2) & 4294967295L) ^ j2));
            jArr2[i2] = jArr2[i];
            return;
        }
        long[] jArr3 = this.i;
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

    @Override // com.android.tools.r8.internal.P0, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        if (this.l == 0) {
            return;
        }
        this.l = 0;
        this.e = false;
        Arrays.fill(this.b, (Object) null);
        Arrays.fill(this.c, (Object) null);
        this.h = -1;
        this.g = -1;
    }

    public final Object clone() {
        try {
            C2375kJ c2375kJ = (C2375kJ) super.clone();
            c2375kJ.n = null;
            c2375kJ.o = null;
            c2375kJ.m = null;
            c2375kJ.e = this.e;
            c2375kJ.b = (Object[]) this.b.clone();
            c2375kJ.c = (Object[]) this.c.clone();
            c2375kJ.i = (long[]) this.i.clone();
            c2375kJ.f = this.f;
            return c2375kJ;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // java.util.SortedMap
    public final Comparator comparator() {
        return null;
    }

    @Override // com.android.tools.r8.internal.P0, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        Object obj2;
        if (this.f.a(obj, null)) {
            return this.e;
        }
        Object[] objArr = this.b;
        int iA = AbstractC3307wk.a(this.f.a(obj)) & this.d;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return false;
        }
        if (this.f.a(obj, obj3)) {
            return true;
        }
        do {
            iA = (iA + 1) & this.d;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return false;
            }
        } while (!this.f.a(obj, obj2));
        return true;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        Object obj2;
        Object[] objArr = this.c;
        Object[] objArr2 = this.b;
        if (this.e && ((obj2 = objArr[this.j]) != null ? obj2.equals(obj) : obj == null)) {
            return true;
        }
        int i = this.j;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (objArr2[i2] != null) {
                Object obj3 = objArr[i2];
                if (obj3 == null) {
                    if (obj == null) {
                        return true;
                    }
                } else if (obj3.equals(obj)) {
                    return true;
                }
            }
            i = i2;
        }
    }

    protected final void d(int i) {
        if (this.l == 0) {
            this.h = -1;
            this.g = -1;
            return;
        }
        if (this.g == i) {
            long[] jArr = this.i;
            int i2 = (int) jArr[i];
            this.g = i2;
            if (i2 >= 0) {
                jArr[i2] = (-4294967296L) | jArr[i2];
                return;
            }
            return;
        }
        if (this.h == i) {
            long[] jArr2 = this.i;
            int i3 = (int) (jArr2[i] >>> 32);
            this.h = i3;
            if (i3 >= 0) {
                jArr2[i3] = jArr2[i3] | 4294967295L;
                return;
            }
            return;
        }
        long[] jArr3 = this.i;
        long j = jArr3[i];
        int i4 = (int) (j >>> 32);
        int i5 = (int) j;
        long j2 = jArr3[i4];
        jArr3[i4] = (4294967295L & ((j & 4294967295L) ^ j2)) ^ j2;
        long j3 = jArr3[i5];
        jArr3[i5] = ((-4294967296L) & ((j & (-4294967296L)) ^ j3)) ^ j3;
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: e */
    public final R0 tailMap(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.SortedMap
    public final Object firstKey() {
        if (this.l != 0) {
            return this.b[this.g];
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        Object obj2;
        if (this.f.a(obj, null)) {
            return this.e ? this.c[this.j] : this.a;
        }
        Object[] objArr = this.b;
        int iA = AbstractC3307wk.a(this.f.a(obj)) & this.d;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return this.a;
        }
        if (this.f.a(obj, obj3)) {
            return this.c[iA];
        }
        do {
            iA = (iA + 1) & this.d;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return this.a;
            }
        } while (!this.f.a(obj, obj2));
        return this.c[iA];
    }

    @Override // com.android.tools.r8.internal.P0, java.util.Map
    public final int hashCode() {
        Object obj;
        int i = this.e ? this.l - 1 : this.l;
        int i2 = 0;
        int i3 = 0;
        int iHashCode = 0;
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
                iHashCode = this.f.a(obj);
            }
            Object obj2 = this.c[i2];
            if (this != obj2) {
                iHashCode = (obj2 == null ? 0 : obj2.hashCode()) ^ iHashCode;
            }
            i3 += iHashCode;
            i2++;
            i = i4;
        }
        if (!this.e) {
            return i3;
        }
        Object obj3 = this.c[this.j];
        return i3 + (obj3 != null ? obj3.hashCode() : 0);
    }

    @Override // com.android.tools.r8.internal.R0, java.util.SortedMap
    public final SortedMap headMap(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.l == 0;
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: l, reason: merged with bridge method [inline-methods] */
    public final FJ k() {
        if (this.m == null) {
            this.m = new C2146hJ(this, 0);
        }
        return this.m;
    }

    @Override // java.util.SortedMap
    public final Object lastKey() {
        if (this.l != 0) {
            return this.b[this.h];
        }
        throw new NoSuchElementException();
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0052  */
    /* JADX WARN: Code duplicated, block: B:22:0x005d  */
    /* JADX WARN: Code duplicated, block: B:25:0x0081  */
    @Override // com.android.tools.r8.internal.P0, java.util.Map
    public final Object put(Object obj, Object obj2) {
        int i;
        int i2;
        int i3;
        if (!this.f.a(obj, null)) {
            Object[] objArr = this.b;
            int iA = AbstractC3307wk.a(this.f.a(obj)) & this.d;
            Object obj3 = objArr[iA];
            if (obj3 != null) {
                if (!this.f.a(obj3, obj)) {
                    while (true) {
                        iA = (iA + 1) & this.d;
                        Object obj4 = objArr[iA];
                        if (obj4 != null) {
                            if (this.f.a(obj4, obj)) {
                            }
                        }
                    }
                }
                i2 = iA;
            }
            i = iA;
            this.b[i] = obj;
            this.c[i] = obj2;
            i3 = this.l;
            if (i3 == 0) {
                this.h = i;
                this.g = i;
                this.i[i] = -1;
            } else {
                long[] jArr = this.i;
                int i4 = this.h;
                long j = jArr[i4];
                jArr[i4] = j ^ (((((long) i) & 4294967295L) ^ j) & 4294967295L);
                jArr[i] = ((((long) i4) & 4294967295L) << 32) | 4294967295L;
                this.h = i;
            }
            this.l = i3 + 1;
            if (i3 >= this.k) {
                e(AbstractC3307wk.a(i3 + 2, 0.75f));
            }
            i2 = -1;
        } else if (this.e) {
            i2 = this.j;
        } else {
            this.e = true;
            i = this.j;
            this.b[i] = obj;
            this.c[i] = obj2;
            i3 = this.l;
            if (i3 == 0) {
                this.h = i;
                this.g = i;
                this.i[i] = -1;
            } else {
                long[] jArr2 = this.i;
                int i5 = this.h;
                long j2 = jArr2[i5];
                jArr2[i5] = j2 ^ (((((long) i) & 4294967295L) ^ j2) & 4294967295L);
                jArr2[i] = ((((long) i5) & 4294967295L) << 32) | 4294967295L;
                this.h = i;
            }
            this.l = i3 + 1;
            if (i3 >= this.k) {
                e(AbstractC3307wk.a(i3 + 2, 0.75f));
            }
            i2 = -1;
        }
        if (i2 < 0) {
            return this.a;
        }
        Object[] objArr2 = this.c;
        Object obj5 = objArr2[i2];
        objArr2[i2] = obj2;
        return obj5;
    }

    @Override // com.android.tools.r8.internal.P0, java.util.Map
    public final void putAll(Map map) {
        if (0.75f <= 0.5d) {
            int iA = AbstractC3307wk.a(map.size(), 0.75f);
            if (iA > this.j) {
                e(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((map.size() + this.l) / 0.75f), 2L, 1073741824L);
            if (iA2 > this.j) {
                e(iA2);
            }
        }
        super.putAll(map);
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        Object obj2;
        int i;
        if (!this.f.a(obj, null)) {
            Object[] objArr = this.b;
            int iA = AbstractC3307wk.a(this.f.a(obj)) & this.d;
            Object obj3 = objArr[iA];
            if (obj3 == null) {
                return this.a;
            }
            if (this.f.a(obj, obj3)) {
                return f(iA);
            }
            do {
                iA = (iA + 1) & this.d;
                obj2 = objArr[iA];
                if (obj2 == null) {
                    return this.a;
                }
            } while (!this.f.a(obj, obj2));
            return f(iA);
        }
        if (!this.e) {
            return this.a;
        }
        this.e = false;
        Object[] objArr2 = this.b;
        int i2 = this.j;
        objArr2[i2] = null;
        Object[] objArr3 = this.c;
        Object obj4 = objArr3[i2];
        objArr3[i2] = null;
        this.l--;
        d(i2);
        if (this.l < this.k / 4 && (i = this.j) > 16) {
            e(i / 2);
        }
        return obj4;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.l;
    }

    @Override // com.android.tools.r8.internal.R0, java.util.SortedMap
    public final SortedMap subMap(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.R0, java.util.SortedMap
    public final SortedMap tailMap(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, java.util.SortedMap
    public final Collection values() {
        if (this.o == null) {
            this.o = new C1755cJ(this);
        }
        return this.o;
    }

    static void a(C2375kJ c2375kJ) {
        int i;
        c2375kJ.e = false;
        Object[] objArr = c2375kJ.b;
        int i2 = c2375kJ.j;
        objArr[i2] = null;
        Object[] objArr2 = c2375kJ.c;
        Object obj = objArr2[i2];
        objArr2[i2] = null;
        c2375kJ.l--;
        c2375kJ.d(i2);
        if (c2375kJ.l >= c2375kJ.k / 4 || (i = c2375kJ.j) <= 16) {
            return;
        }
        c2375kJ.e(i / 2);
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: e */
    public final InterfaceC2842qK keySet() {
        if (this.n == null) {
            this.n = new C1992fJ(this, 0);
        }
        return this.n;
    }

    protected final void e(int i) {
        int i2;
        Object[] objArr;
        Object[] objArr2 = this.b;
        Object[] objArr3 = this.c;
        int i3 = i - 1;
        int i4 = i + 1;
        Object[] objArr4 = new Object[i4];
        Object[] objArr5 = new Object[i4];
        int i5 = this.g;
        long[] jArr = this.i;
        long[] jArr2 = new long[i4];
        this.g = -1;
        int i6 = this.l;
        int i7 = -1;
        int i8 = -1;
        while (true) {
            int i9 = i6 - 1;
            if (i6 == 0) {
                break;
            }
            if (!this.f.a(objArr2[i5], null)) {
                int iA = AbstractC3307wk.a(this.f.a(objArr2[i5]));
                while (true) {
                    i2 = iA & i3;
                    if (objArr4[i2] == null) {
                        break;
                    } else {
                        iA = i2 + 1;
                    }
                }
            } else {
                i2 = i;
            }
            objArr4[i2] = objArr2[i5];
            objArr5[i2] = objArr3[i5];
            if (i8 != -1) {
                long j = jArr2[i7];
                Object[] objArr6 = objArr2;
                jArr2[i7] = j ^ ((j ^ (((long) i2) & 4294967295L)) & 4294967295L);
                long j2 = jArr2[i2];
                objArr = objArr6;
                jArr2[i2] = j2 ^ ((((((long) i7) & 4294967295L) << 32) ^ j2) & (-4294967296L));
            } else {
                objArr = objArr2;
                this.g = i2;
                jArr2[i2] = -1;
            }
            i7 = i2;
            objArr3 = objArr3;
            i6 = i9;
            i8 = i5;
            i5 = (int) jArr[i5];
            objArr2 = objArr;
        }
        this.i = jArr2;
        this.h = i7;
        if (i7 != -1) {
            jArr2[i7] = jArr2[i7] | 4294967295L;
        }
        this.j = i;
        this.d = i3;
        this.k = AbstractC3307wk.b(i, 0.75f);
        this.b = objArr4;
        this.c = objArr5;
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: d */
    public final R0 headMap(Object obj) {
        throw new UnsupportedOperationException();
    }
}
