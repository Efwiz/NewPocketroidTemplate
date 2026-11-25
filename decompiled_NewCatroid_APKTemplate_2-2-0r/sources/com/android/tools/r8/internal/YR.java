package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class YR extends AbstractC2747p1 implements Cloneable {
    protected transient Object[] a;
    protected transient long[] b;
    protected transient int c;
    protected transient boolean d;
    protected transient int e;
    protected transient int f;
    protected int g;
    protected transient PR h;
    protected transient InterfaceC3211vS i;
    protected transient UC j;

    public YR(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        int iA = AbstractC3307wk.a(i, 0.75f);
        this.e = iA;
        this.c = iA - 1;
        this.f = AbstractC3307wk.b(iA, 0.75f);
        int i2 = this.e + 1;
        this.a = new Object[i2];
        this.b = new long[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long e(int i) {
        Object obj;
        int i2;
        long j = this.b[i];
        this.g--;
        Object[] objArr = this.a;
        loop0: while (true) {
            int i3 = (i + 1) & this.c;
            while (true) {
                obj = objArr[i3];
                if (obj != null) {
                    int iA = AbstractC3307wk.a(System.identityHashCode(obj));
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
            objArr[i] = obj;
            long[] jArr = this.b;
            jArr[i] = jArr[i3];
            i = i3;
        }
        objArr[i] = null;
        if (this.g < this.f / 4 && (i2 = this.e) > 16) {
            d(i2 / 2);
        }
        return j;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        if (this.g == 0) {
            return;
        }
        this.g = 0;
        this.d = false;
        Arrays.fill(this.a, (Object) null);
    }

    public final Object clone() {
        try {
            YR yr = (YR) super.clone();
            yr.i = null;
            yr.j = null;
            yr.h = null;
            yr.d = this.d;
            yr.a = (Object[]) this.a.clone();
            yr.b = (long[]) this.b.clone();
            return yr;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        Object obj2;
        if (obj == null) {
            return this.d;
        }
        Object[] objArr = this.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.c;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return false;
        }
        if (obj == obj3) {
            return true;
        }
        do {
            iA = (iA + 1) & this.c;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return false;
            }
        } while (obj != obj2);
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2747p1
    public final long d(Object obj) {
        Object obj2;
        if (obj == null) {
            if (!this.d) {
                return 0L;
            }
            this.d = false;
            Object[] objArr = this.a;
            int i = this.e;
            objArr[i] = null;
            long j = this.b[i];
            int i2 = this.g - 1;
            this.g = i2;
            if (i2 < this.f / 4 && i > 16) {
                d(i / 2);
            }
            return j;
        }
        Object[] objArr2 = this.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.c;
        Object obj3 = objArr2[iA];
        if (obj3 == null) {
            return 0L;
        }
        if (obj == obj3) {
            return e(iA);
        }
        do {
            iA = (iA + 1) & this.c;
            obj2 = objArr2[iA];
            if (obj2 == null) {
                return 0L;
            }
        } while (obj != obj2);
        return e(iA);
    }

    @Override // java.util.Map
    public final int hashCode() {
        Object obj;
        int i = this.d ? this.g - 1 : this.g;
        int i2 = 0;
        int i3 = 0;
        int iA = 0;
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
                iA = System.identityHashCode(obj);
            }
            iA ^= AbstractC3307wk.a(this.b[i2]);
            i3 += iA;
            i2++;
            i = i4;
        }
        return this.d ? i3 + AbstractC3307wk.a(this.b[this.e]) : i3;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.g == 0;
    }

    @Override // java.util.Map
    public final Set keySet() {
        if (this.i == null) {
            this.i = new TR(this, 0);
        }
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2747p1, java.util.Map
    public final void putAll(Map map) {
        if (0.75f <= 0.5d) {
            int iA = AbstractC3307wk.a(map.size(), 0.75f);
            if (iA > this.e) {
                d(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((map.size() + this.g) / 0.75f), 2L, 1073741824L);
            if (iA2 > this.e) {
                d(iA2);
            }
        }
        super.putAll(map);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.g;
    }

    @Override // java.util.Map
    public final Collection values() {
        if (this.j == null) {
            this.j = new QR(this);
        }
        return this.j;
    }

    static void a(YR yr) {
        yr.d = false;
        Object[] objArr = yr.a;
        int i = yr.e;
        objArr[i] = null;
        long j = yr.b[i];
        int i2 = yr.g - 1;
        yr.g = i2;
        if (i2 >= yr.f / 4 || i <= 16) {
            return;
        }
        yr.d(i / 2);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0042  */
    @Override // com.android.tools.r8.internal.AbstractC2747p1
    public final long a(Object obj, long j) {
        int i;
        int i2;
        int i3;
        if (obj == null) {
            if (this.d) {
                i2 = this.e;
            } else {
                this.d = true;
                i = this.e;
                this.a[i] = obj;
                this.b[i] = j;
                i3 = this.g;
                this.g = i3 + 1;
                if (i3 >= this.f) {
                    d(AbstractC3307wk.a(i3 + 2, 0.75f));
                }
                i2 = -1;
            }
        } else {
            Object[] objArr = this.a;
            int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.c;
            Object obj2 = objArr[iA];
            if (obj2 != null) {
                if (obj2 != obj) {
                    while (true) {
                        iA = (iA + 1) & this.c;
                        Object obj3 = objArr[iA];
                        if (obj3 != null) {
                            if (obj3 == obj) {
                            }
                        }
                    }
                }
                i2 = iA;
            }
            i = iA;
            this.a[i] = obj;
            this.b[i] = j;
            i3 = this.g;
            this.g = i3 + 1;
            if (i3 >= this.f) {
                d(AbstractC3307wk.a(i3 + 2, 0.75f));
            }
            i2 = -1;
        }
        if (i2 < 0) {
            return 0L;
        }
        long[] jArr = this.b;
        long j2 = jArr[i2];
        jArr[i2] = j;
        return j2;
    }

    protected final void d(int i) {
        Object obj;
        Object[] objArr = this.a;
        long[] jArr = this.b;
        int i2 = i - 1;
        int i3 = i + 1;
        Object[] objArr2 = new Object[i3];
        long[] jArr2 = new long[i3];
        int i4 = this.e;
        int i5 = this.d ? this.g - 1 : this.g;
        while (true) {
            int i6 = i5 - 1;
            if (i5 != 0) {
                do {
                    i4--;
                    obj = objArr[i4];
                } while (obj == null);
                int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & i2;
                if (objArr2[iA] != null) {
                    do {
                        iA = (iA + 1) & i2;
                    } while (objArr2[iA] != null);
                }
                objArr2[iA] = objArr[i4];
                jArr2[iA] = jArr[i4];
                i5 = i6;
            } else {
                jArr2[i] = jArr[this.e];
                this.e = i;
                this.c = i2;
                this.f = AbstractC3307wk.b(i, 0.75f);
                this.a = objArr2;
                this.b = jArr2;
                return;
            }
        }
    }

    public final long e(Object obj) {
        Object obj2;
        if (obj == null) {
            if (this.d) {
                return this.b[this.e];
            }
            return 0L;
        }
        Object[] objArr = this.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.c;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return 0L;
        }
        if (obj == obj3) {
            return this.b[iA];
        }
        do {
            iA = (iA + 1) & this.c;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return 0L;
            }
        } while (obj != obj2);
        return this.b[iA];
    }
}
