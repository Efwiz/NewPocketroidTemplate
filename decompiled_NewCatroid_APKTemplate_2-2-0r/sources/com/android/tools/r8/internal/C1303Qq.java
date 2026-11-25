package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1303Qq extends G implements Cloneable {
    protected transient int[] b;
    protected transient Object[] c;
    protected transient int d;
    protected transient boolean e;
    protected transient int f;
    protected transient int g;
    protected int h;
    protected final float i;
    protected transient InterfaceC0966Eq j;
    protected transient InterfaceC1277Pr k;
    protected transient InterfaceC2703oS l;

    public C1303Qq(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.i = 0.75f;
        int iA = AbstractC3307wk.a(i, 0.75f);
        this.f = iA;
        this.d = iA - 1;
        this.g = AbstractC3307wk.b(iA, 0.75f);
        int i2 = this.f + 1;
        this.b = new int[i2];
        this.c = new Object[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object e(int i) {
        int i2;
        int i3;
        Object[] objArr = this.c;
        Object obj = objArr[i];
        objArr[i] = null;
        this.h--;
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
            i = i4;
        }
        iArr[i] = 0;
        this.c[i] = null;
        if (this.h < this.g / 4 && (i3 = this.f) > 16) {
            d(i3 / 2);
        }
        return obj;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0992Fq
    public final InterfaceC2529mK c() {
        if (this.j == null) {
            this.j = new C1219Nq(this, 0);
        }
        return this.j;
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        if (this.h == 0) {
            return;
        }
        this.h = 0;
        this.e = false;
        Arrays.fill(this.b, 0);
        Arrays.fill(this.c, (Object) null);
    }

    public final Object clone() {
        try {
            C1303Qq c1303Qq = (C1303Qq) super.clone();
            c1303Qq.k = null;
            c1303Qq.l = null;
            c1303Qq.j = null;
            c1303Qq.e = this.e;
            c1303Qq.b = (int[]) this.b.clone();
            c1303Qq.c = (Object[]) this.c.clone();
            return c1303Qq;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        Object[] objArr = this.c;
        int[] iArr = this.b;
        if (this.e && objArr[this.f] == obj) {
            return true;
        }
        int i = this.f;
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
        int i2;
        int[] iArr = this.b;
        Object[] objArr = this.c;
        int i3 = i - 1;
        int i4 = i + 1;
        int[] iArr2 = new int[i4];
        Object[] objArr2 = new Object[i4];
        int i5 = this.f;
        int i6 = this.e ? this.h - 1 : this.h;
        while (true) {
            int i7 = i6 - 1;
            if (i6 == 0) {
                objArr2[i] = objArr[this.f];
                this.f = i;
                this.d = i3;
                this.g = AbstractC3307wk.b(i, this.i);
                this.b = iArr2;
                this.c = objArr2;
                return;
            }
            do {
                i5--;
                i2 = iArr[i5];
            } while (i2 == 0);
            int iA = AbstractC3307wk.a(i2) & i3;
            if (iArr2[iA] != 0) {
                do {
                    iA = (iA + 1) & i3;
                } while (iArr2[iA] != 0);
            }
            iArr2[iA] = iArr[i5];
            objArr2[iA] = objArr[i5];
            i6 = i7;
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3087tq
    public final Object get(int i) {
        int i2;
        if (i == 0) {
            return this.e ? this.c[this.f] : this.a;
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
        int i = this.e ? this.h - 1 : this.h;
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
        Object obj2 = this.c[this.f];
        return i3 + (obj2 != null ? System.identityHashCode(obj2) : 0);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.h == 0;
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC1277Pr keySet() {
        if (this.k == null) {
            this.k = new C1165Lq(this, 0);
        }
        return this.k;
    }

    @Override // com.android.tools.r8.internal.G, java.util.Map
    public final void putAll(Map map) {
        if (this.i <= 0.5d) {
            int iA = AbstractC3307wk.a(map.size(), this.i);
            if (iA > this.f) {
                d(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((map.size() + this.h) / this.i), 2L, 1073741824L);
            if (iA2 > this.f) {
                d(iA2);
            }
        }
        super.putAll(map);
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object remove(int i) {
        int i2;
        if (i == 0) {
            if (!this.e) {
                return this.a;
            }
            this.e = false;
            Object[] objArr = this.c;
            int i3 = this.f;
            Object obj = objArr[i3];
            objArr[i3] = null;
            int i4 = this.h - 1;
            this.h = i4;
            if (i4 < this.g / 4 && i3 > 16) {
                d(i3 / 2);
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
            return e(iA);
        }
        do {
            iA = (iA + 1) & this.d;
            i2 = iArr[iA];
            if (i2 == 0) {
                return this.a;
            }
        } while (i != i2);
        return e(iA);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC2703oS values() {
        if (this.l == null) {
            this.l = new C1082Iq(this);
        }
        return this.l;
    }

    static void a(C1303Qq c1303Qq) {
        c1303Qq.e = false;
        Object[] objArr = c1303Qq.c;
        int i = c1303Qq.f;
        Object obj = objArr[i];
        objArr[i] = null;
        int i2 = c1303Qq.h - 1;
        c1303Qq.h = i2;
        if (i2 >= c1303Qq.g / 4 || i <= 16) {
            return;
        }
        c1303Qq.d(i / 2);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x003e  */
    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object a(int i, Object obj) {
        int i2;
        int i3;
        int i4;
        if (i == 0) {
            if (this.e) {
                i3 = this.f;
            } else {
                this.e = true;
                i2 = this.f;
                this.b[i2] = i;
                this.c[i2] = obj;
                i4 = this.h;
                this.h = i4 + 1;
                if (i4 >= this.g) {
                    d(AbstractC3307wk.a(i4 + 2, this.i));
                }
                i3 = -1;
            }
        } else {
            int[] iArr = this.b;
            int iA = AbstractC3307wk.a(i) & this.d;
            int i5 = iArr[iA];
            if (i5 != 0) {
                if (i5 != i) {
                    while (true) {
                        iA = (iA + 1) & this.d;
                        int i6 = iArr[iA];
                        if (i6 != 0) {
                            if (i6 == i) {
                            }
                        }
                    }
                }
                i3 = iA;
            }
            i2 = iA;
            this.b[i2] = i;
            this.c[i2] = obj;
            i4 = this.h;
            this.h = i4 + 1;
            if (i4 >= this.g) {
                d(AbstractC3307wk.a(i4 + 2, this.i));
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

    public C1303Qq() {
        this(16);
    }

    public C1303Qq(InterfaceC0992Fq interfaceC0992Fq) {
        this(interfaceC0992Fq.size());
        putAll(interfaceC0992Fq);
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
}
