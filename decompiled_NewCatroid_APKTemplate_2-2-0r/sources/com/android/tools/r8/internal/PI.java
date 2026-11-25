package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class PI extends L0 implements Cloneable {
    protected transient Object[] b;
    protected transient int[] c;
    protected transient int d;
    protected transient boolean e;
    protected transient int f;
    protected transient int g;
    protected int h;
    protected transient FI i;
    protected transient InterfaceC2529mK j;
    protected transient InterfaceC3088tr k;

    public PI(int i) {
        int iA = AbstractC3307wk.a(16, 0.75f);
        this.f = iA;
        this.d = iA - 1;
        this.g = AbstractC3307wk.b(iA, 0.75f);
        int i2 = this.f + 1;
        this.b = new Object[i2];
        this.c = new int[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int g(int i) {
        Object obj;
        int i2;
        int i3 = this.c[i];
        this.h--;
        Object[] objArr = this.b;
        loop0: while (true) {
            int i4 = (i + 1) & this.d;
            while (true) {
                obj = objArr[i4];
                if (obj != null) {
                    int iA = AbstractC3307wk.a(obj.hashCode());
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
            i = i4;
        }
        objArr[i] = null;
        if (this.h < this.g / 4 && (i2 = this.f) > 16) {
            f(i2 / 2);
        }
        return i3;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        if (this.h == 0) {
            return;
        }
        this.h = 0;
        this.e = false;
        Arrays.fill(this.b, (Object) null);
    }

    public final Object clone() {
        try {
            PI pi = (PI) super.clone();
            pi.j = null;
            pi.k = null;
            pi.i = null;
            pi.e = this.e;
            pi.b = (Object[]) this.b.clone();
            pi.c = (int[]) this.c.clone();
            return pi;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        Object obj2;
        if (obj == null) {
            return this.e;
        }
        Object[] objArr = this.b;
        int iA = AbstractC3307wk.a(obj.hashCode()) & this.d;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return false;
        }
        if (obj.equals(obj3)) {
            return true;
        }
        do {
            iA = (iA + 1) & this.d;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return false;
            }
        } while (!obj.equals(obj2));
        return true;
    }

    @Override // com.android.tools.r8.internal.L0
    public final int d(Object obj) {
        Object obj2;
        if (obj == null) {
            if (!this.e) {
                return this.a;
            }
            this.e = false;
            Object[] objArr = this.b;
            int i = this.f;
            objArr[i] = null;
            int i2 = this.c[i];
            int i3 = this.h - 1;
            this.h = i3;
            if (i3 < this.g / 4 && i > 16) {
                f(i / 2);
            }
            return i2;
        }
        Object[] objArr2 = this.b;
        int iA = AbstractC3307wk.a(obj.hashCode()) & this.d;
        Object obj3 = objArr2[iA];
        if (obj3 == null) {
            return this.a;
        }
        if (obj.equals(obj3)) {
            return g(iA);
        }
        do {
            iA = (iA + 1) & this.d;
            obj2 = objArr2[iA];
            if (obj2 == null) {
                return this.a;
            }
        } while (!obj.equals(obj2));
        return g(iA);
    }

    protected final void f(int i) {
        Object obj;
        Object[] objArr = this.b;
        int[] iArr = this.c;
        int i2 = i - 1;
        int i3 = i + 1;
        Object[] objArr2 = new Object[i3];
        int[] iArr2 = new int[i3];
        int i4 = this.f;
        int i5 = this.e ? this.h - 1 : this.h;
        while (true) {
            int i6 = i5 - 1;
            if (i5 == 0) {
                iArr2[i] = iArr[this.f];
                this.f = i;
                this.d = i2;
                this.g = AbstractC3307wk.b(i, 0.75f);
                this.b = objArr2;
                this.c = iArr2;
                return;
            }
            do {
                i4--;
                obj = objArr[i4];
            } while (obj == null);
            int iA = AbstractC3307wk.a(obj.hashCode()) & i2;
            if (objArr2[iA] != null) {
                do {
                    iA = (iA + 1) & i2;
                } while (objArr2[iA] != null);
            }
            objArr2[iA] = objArr[i4];
            iArr2[iA] = iArr[i4];
            i5 = i6;
        }
    }

    @Override // com.android.tools.r8.internal.L0, java.util.Map
    public final int hashCode() {
        Object obj;
        int i = this.e ? this.h - 1 : this.h;
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
                iHashCode = obj.hashCode();
            }
            iHashCode ^= this.c[i2];
            i3 += iHashCode;
            i2++;
            i = i4;
        }
        return this.e ? i3 + this.c[this.f] : i3;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.h == 0;
    }

    @Override // com.android.tools.r8.internal.GI
    public final InterfaceC2529mK j() {
        if (this.i == null) {
            this.i = new MI(this, 0);
        }
        return this.i;
    }

    @Override // java.util.Map
    /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
    public final InterfaceC2529mK keySet() {
        if (this.j == null) {
            this.j = new KI(this, 0);
        }
        return this.j;
    }

    @Override // com.android.tools.r8.internal.L0, java.util.Map
    public final void putAll(Map map) {
        if (0.75f <= 0.5d) {
            int iA = AbstractC3307wk.a(map.size(), 0.75f);
            if (iA > this.f) {
                f(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((map.size() + this.h) / 0.75f), 2L, 1073741824L);
            if (iA2 > this.f) {
                f(iA2);
            }
        }
        super.putAll(map);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.h;
    }

    @Override // java.util.Map
    public final Collection values() {
        if (this.k == null) {
            this.k = new HI(this);
        }
        return this.k;
    }

    static void a(PI pi) {
        pi.e = false;
        Object[] objArr = pi.b;
        int i = pi.f;
        objArr[i] = null;
        int i2 = pi.c[i];
        int i3 = pi.h - 1;
        pi.h = i3;
        if (i3 >= pi.g / 4 || i <= 16) {
            return;
        }
        pi.f(i / 2);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004a  */
    @Override // com.android.tools.r8.internal.L0, com.android.tools.r8.internal.InterfaceC3119uI
    public final int a(int i, Object obj) {
        int i2;
        int i3;
        int i4;
        if (obj == null) {
            if (this.e) {
                i3 = this.f;
            } else {
                this.e = true;
                i2 = this.f;
                this.b[i2] = obj;
                this.c[i2] = i;
                i4 = this.h;
                this.h = i4 + 1;
                if (i4 >= this.g) {
                    f(AbstractC3307wk.a(i4 + 2, 0.75f));
                }
                i3 = -1;
            }
        } else {
            Object[] objArr = this.b;
            int iA = AbstractC3307wk.a(obj.hashCode()) & this.d;
            Object obj2 = objArr[iA];
            if (obj2 != null) {
                if (!obj2.equals(obj)) {
                    while (true) {
                        iA = (iA + 1) & this.d;
                        Object obj3 = objArr[iA];
                        if (obj3 != null) {
                            if (obj3.equals(obj)) {
                            }
                        }
                    }
                }
                i3 = iA;
            }
            i2 = iA;
            this.b[i2] = obj;
            this.c[i2] = i;
            i4 = this.h;
            this.h = i4 + 1;
            if (i4 >= this.g) {
                f(AbstractC3307wk.a(i4 + 2, 0.75f));
            }
            i3 = -1;
        }
        if (i3 < 0) {
            return this.a;
        }
        int[] iArr = this.c;
        int i5 = iArr[i3];
        iArr[i3] = i;
        return i5;
    }

    public PI() {
        this(0);
    }

    @Override // com.android.tools.r8.internal.L0
    public final boolean d(int i) {
        int[] iArr = this.c;
        Object[] objArr = this.b;
        if (this.e && iArr[this.f] == i) {
            return true;
        }
        int i2 = this.f;
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

    @Override // com.android.tools.r8.internal.InterfaceC3119uI
    public final int a(Object obj) {
        Object obj2;
        if (obj == null) {
            return this.e ? this.c[this.f] : this.a;
        }
        Object[] objArr = this.b;
        int iA = AbstractC3307wk.a(obj.hashCode()) & this.d;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return this.a;
        }
        if (obj.equals(obj3)) {
            return this.c[iA];
        }
        do {
            iA = (iA + 1) & this.d;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return this.a;
            }
        } while (!obj.equals(obj2));
        return this.c[iA];
    }
}
