package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Eo, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0964Eo extends AbstractC3401y implements Cloneable {
    protected transient int[] b;
    protected transient int[] c;
    protected transient int d;
    protected transient boolean e;
    protected transient int f;
    protected transient int g;
    protected transient long[] h;
    protected transient int i;
    protected transient int j;
    protected int k;
    protected transient InterfaceC1328Ro l;
    protected transient InterfaceC1357Sr m;
    protected transient InterfaceC3088tr n;

    public C0964Eo(int i) {
        this.f = -1;
        this.g = -1;
        int iA = AbstractC3307wk.a(16, 0.75f);
        this.i = iA;
        this.d = iA - 1;
        this.j = AbstractC3307wk.b(iA, 0.75f);
        int i2 = this.i + 1;
        this.b = new int[i2];
        this.c = new int[i2];
        this.h = new long[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int h(int i) {
        int i2;
        int i3;
        int i4 = this.c[i];
        this.k--;
        f(i);
        int[] iArr = this.b;
        loop0: while (true) {
            int i5 = (i + 1) & this.d;
            while (true) {
                i2 = iArr[i5];
                if (i2 != 0) {
                    int iA = AbstractC3307wk.a(i2);
                    int i6 = this.d;
                    int i7 = iA & i6;
                    if (i > i5) {
                        if (i >= i7 && i7 > i5) {
                            break;
                        }
                        i5 = (i5 + 1) & i6;
                    } else {
                        if (i >= i7 || i7 > i5) {
                            break;
                        }
                        i5 = (i5 + 1) & i6;
                    }
                } else {
                    break loop0;
                }
            }
            iArr[i] = i2;
            int[] iArr2 = this.c;
            iArr2[i] = iArr2[i5];
            c(i5, i);
            i = i5;
        }
        iArr[i] = 0;
        if (this.k < this.j / 4 && (i3 = this.i) > 16) {
            g(i3 / 2);
        }
        return i4;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0038  */
    /* JADX WARN: Code duplicated, block: B:20:0x0043  */
    /* JADX WARN: Code duplicated, block: B:23:0x0067  */
    @Override // com.android.tools.r8.internal.AbstractC3324x, com.android.tools.r8.internal.InterfaceC3162uo
    public final int b(int i, int i2) {
        int i3;
        int i4;
        int i5;
        if (i != 0) {
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
                i4 = iA;
            }
            i3 = iA;
            this.b[i3] = i;
            this.c[i3] = i2;
            i5 = this.k;
            if (i5 == 0) {
                this.g = i3;
                this.f = i3;
                this.h[i3] = -1;
            } else {
                long[] jArr = this.h;
                int i8 = this.g;
                long j = jArr[i8];
                jArr[i8] = j ^ (((((long) i3) & 4294967295L) ^ j) & 4294967295L);
                jArr[i3] = ((((long) i8) & 4294967295L) << 32) | 4294967295L;
                this.g = i3;
            }
            this.k = i5 + 1;
            if (i5 >= this.j) {
                g(AbstractC3307wk.a(i5 + 2, 0.75f));
            }
            i4 = -1;
        } else if (this.e) {
            i4 = this.i;
        } else {
            this.e = true;
            i3 = this.i;
            this.b[i3] = i;
            this.c[i3] = i2;
            i5 = this.k;
            if (i5 == 0) {
                this.g = i3;
                this.f = i3;
                this.h[i3] = -1;
            } else {
                long[] jArr2 = this.h;
                int i9 = this.g;
                long j2 = jArr2[i9];
                jArr2[i9] = j2 ^ (((((long) i3) & 4294967295L) ^ j2) & 4294967295L);
                jArr2[i3] = ((((long) i9) & 4294967295L) << 32) | 4294967295L;
                this.g = i3;
            }
            this.k = i5 + 1;
            if (i5 >= this.j) {
                g(AbstractC3307wk.a(i5 + 2, 0.75f));
            }
            i4 = -1;
        }
        if (i4 < 0) {
            return this.a;
        }
        int[] iArr2 = this.c;
        int i10 = iArr2[i4];
        iArr2[i4] = i2;
        return i10;
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
        Arrays.fill(this.b, 0);
        this.g = -1;
        this.f = -1;
    }

    public final Object clone() {
        try {
            C0964Eo c0964Eo = (C0964Eo) super.clone();
            c0964Eo.m = null;
            c0964Eo.n = null;
            c0964Eo.l = null;
            c0964Eo.e = this.e;
            c0964Eo.b = (int[]) this.b.clone();
            c0964Eo.c = (int[]) this.c.clone();
            c0964Eo.h = (long[]) this.h.clone();
            return c0964Eo;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // java.util.SortedMap
    public final /* bridge */ /* synthetic */ Comparator comparator() {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC3324x
    public final boolean d(int i) {
        int[] iArr = this.c;
        int[] iArr2 = this.b;
        if (this.e && iArr[this.i] == i) {
            return true;
        }
        int i2 = this.i;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return false;
            }
            if (iArr2[i3] != 0 && iArr[i3] == i) {
                return true;
            }
            i2 = i3;
        }
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

    protected final void g(int i) {
        int i2;
        int[] iArr = this.b;
        int[] iArr2 = this.c;
        int i3 = i - 1;
        int i4 = i + 1;
        int[] iArr3 = new int[i4];
        int[] iArr4 = new int[i4];
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
                    if (iArr3[i2] == 0) {
                        break;
                    } else {
                        iA = i2 + 1;
                    }
                }
            } else {
                i2 = i;
            }
            iArr3[i2] = iArr[i5];
            iArr4[i2] = iArr2[i5];
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
            iArr2 = iArr2;
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
        this.b = iArr3;
        this.c = iArr4;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3162uo
    public final int get(int i) {
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

    @Override // com.android.tools.r8.internal.AbstractC3324x, java.util.Map
    public final int hashCode() {
        int i;
        int i2 = this.e ? this.k - 1 : this.k;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i2 - 1;
            if (i2 == 0) {
                break;
            }
            while (true) {
                i = this.b[i4];
                if (i == 0) {
                    i4++;
                }
            }
            i3 += i ^ this.c[i4];
            i4++;
            i2 = i5;
        }
        return this.e ? i3 + this.c[this.i] : i3;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1050Ho
    public final InterfaceC2529mK i() {
        if (this.l == null) {
            this.l = new C0864Bo(this, 0);
        }
        return this.l;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.k == 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC3401y, java.util.Map, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        if (this.m == null) {
            this.m = new C3528zo(this, 0);
        }
        return this.m;
    }

    @Override // com.android.tools.r8.internal.AbstractC3324x, java.util.Map
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

    @Override // com.android.tools.r8.internal.AbstractC3324x, com.android.tools.r8.internal.InterfaceC3162uo
    public final int remove(int i) {
        int i2;
        int i3;
        if (i == 0) {
            if (!this.e) {
                return this.a;
            }
            this.e = false;
            int[] iArr = this.c;
            int i4 = this.i;
            int i5 = iArr[i4];
            this.k--;
            f(i4);
            if (this.k < this.j / 4 && (i3 = this.i) > 16) {
                g(i3 / 2);
            }
            return i5;
        }
        int[] iArr2 = this.b;
        int iA = AbstractC3307wk.a(i) & this.d;
        int i6 = iArr2[iA];
        if (i6 == 0) {
            return this.a;
        }
        if (i == i6) {
            return h(iA);
        }
        do {
            iA = (iA + 1) & this.d;
            i2 = iArr2[iA];
            if (i2 == 0) {
                return this.a;
            }
        } while (i != i2);
        return h(iA);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.k;
    }

    @Override // java.util.Map, java.util.SortedMap
    public final Collection values() {
        if (this.n == null) {
            this.n = new C3311wo(this);
        }
        return this.n;
    }

    static void a(C0964Eo c0964Eo) {
        int i;
        c0964Eo.e = false;
        int[] iArr = c0964Eo.c;
        int i2 = c0964Eo.i;
        int i3 = iArr[i2];
        c0964Eo.k--;
        c0964Eo.f(i2);
        if (c0964Eo.k >= c0964Eo.j / 4 || (i = c0964Eo.i) <= 16) {
            return;
        }
        c0964Eo.g(i / 2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3162uo
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

    public C0964Eo() {
        this(0);
    }
}
