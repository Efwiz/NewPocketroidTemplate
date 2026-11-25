package com.android.tools.r8.internal;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2537mR extends AbstractC2350k1 implements Cloneable, InterfaceC1613aR {
    protected transient Object[] a;
    protected transient boolean[] b;
    protected transient int c;
    protected transient boolean d;
    protected transient int e;
    protected transient int f;
    protected int g;
    protected transient ZQ h;
    protected transient InterfaceC3211vS i;
    protected transient InterfaceC1889e5 j;

    public C2537mR(int i) {
        int iA = AbstractC3307wk.a(16, 0.75f);
        this.e = iA;
        this.c = iA - 1;
        this.f = AbstractC3307wk.b(iA, 0.75f);
        int i2 = this.e + 1;
        this.a = new Object[i2];
        this.b = new boolean[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e(int i) {
        Object obj;
        int i2;
        boolean z = this.b[i];
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
            boolean[] zArr = this.b;
            zArr[i] = zArr[i3];
            i = i3;
        }
        objArr[i] = null;
        if (this.g < this.f / 4 && (i2 = this.e) > 16) {
            d(i2 / 2);
        }
        return z;
    }

    @Override // com.android.tools.r8.internal.XQ
    public final boolean b(Object obj) {
        Object obj2;
        if (obj == null) {
            if (this.d) {
                return this.b[this.e];
            }
            return false;
        }
        Object[] objArr = this.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.c;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return false;
        }
        if (obj == obj3) {
            return this.b[iA];
        }
        do {
            iA = (iA + 1) & this.c;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return false;
            }
        } while (obj != obj2);
        return this.b[iA];
    }

    @Override // com.android.tools.r8.internal.AbstractC2350k1
    public final boolean c(Object obj) {
        Object obj2;
        if (obj == null) {
            if (this.d) {
                return e();
            }
            return false;
        }
        Object[] objArr = this.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.c;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return false;
        }
        if (obj == obj3) {
            return e(iA);
        }
        do {
            iA = (iA + 1) & this.c;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return false;
            }
        } while (obj != obj2);
        return e(iA);
    }

    @Override // com.android.tools.r8.internal.AbstractC2350k1, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
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
            C2537mR c2537mR = (C2537mR) super.clone();
            c2537mR.i = null;
            c2537mR.j = null;
            c2537mR.h = null;
            c2537mR.d = this.d;
            c2537mR.a = (Object[]) this.a.clone();
            c2537mR.b = (boolean[]) this.b.clone();
            return c2537mR;
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

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        if (obj != null) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean[] zArr = this.b;
            Object[] objArr = this.a;
            if (!this.d || zArr[this.e] != zBooleanValue) {
                int i = this.e;
                while (true) {
                    int i2 = i - 1;
                    if (i == 0) {
                        break;
                    }
                    if (objArr[i2] == null || zArr[i2] != zBooleanValue) {
                        i = i2;
                    }
                }
            }
            return true;
        }
        return false;
    }

    protected final void d(int i) {
        Object obj;
        Object[] objArr = this.a;
        boolean[] zArr = this.b;
        int i2 = i - 1;
        int i3 = i + 1;
        Object[] objArr2 = new Object[i3];
        boolean[] zArr2 = new boolean[i3];
        int i4 = this.e;
        int i5 = this.d ? this.g - 1 : this.g;
        while (true) {
            int i6 = i5 - 1;
            if (i5 == 0) {
                zArr2[i] = zArr[this.e];
                this.e = i;
                this.c = i2;
                this.f = AbstractC3307wk.b(i, 0.75f);
                this.a = objArr2;
                this.b = zArr2;
                return;
            }
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
            zArr2[iA] = zArr[i4];
            i5 = i6;
        }
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return h();
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (map.size() == this.g) {
                return ((AbstractCollection) h()).containsAll(map.entrySet());
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1613aR
    public final InterfaceC2529mK h() {
        if (this.h == null) {
            this.h = new C2305jR(this, 0);
        }
        return this.h;
    }

    @Override // java.util.Map
    public final int hashCode() {
        int i;
        Object obj;
        int i2 = this.d ? this.g - 1 : this.g;
        int i3 = 0;
        int i4 = 0;
        int iIdentityHashCode = 0;
        while (true) {
            int i5 = i2 - 1;
            i = XKeySym.XK_kana_MA;
            if (i2 == 0) {
                break;
            }
            while (true) {
                obj = this.a[i3];
                if (obj != null) {
                    break;
                }
                i3++;
            }
            if (this != obj) {
                iIdentityHashCode = System.identityHashCode(obj);
            }
            if (!this.b[i3]) {
                i = 1237;
            }
            iIdentityHashCode ^= i;
            i4 += iIdentityHashCode;
            i3++;
            i2 = i5;
        }
        if (!this.d) {
            return i4;
        }
        if (!this.b[this.e]) {
            i = 1237;
        }
        return i4 + i;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.g == 0;
    }

    @Override // java.util.Map
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
        int size = map.size();
        Iterator it = map.entrySet().iterator();
        if (map instanceof InterfaceC1613aR) {
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                YQ yq = (YQ) it.next();
                a(yq.getKey(), yq.getBooleanValue());
                size = i;
            }
        } else {
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return;
                }
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Boolean bool = (Boolean) entry.getValue();
                containsKey(key);
                a(key, bool.booleanValue());
                size = i2;
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.g;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        InterfaceC1912eK it = ((C2305jR) h()).iterator();
        int i = this.g;
        boolean z = true;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                sb.append("}");
                return sb.toString();
            }
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            YQ yq = (YQ) ((C2001fR) it).next();
            if (this == yq.getKey()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(yq.getKey()));
            }
            sb.append("=>");
            sb.append(String.valueOf(yq.getBooleanValue()));
            i = i2;
        }
    }

    @Override // java.util.Map
    public final Collection values() {
        if (this.j == null) {
            this.j = new C1924eR(this);
        }
        return this.j;
    }

    @Override // java.util.Map
    public final InterfaceC3211vS keySet() {
        if (this.i == null) {
            this.i = new C2155hR(this, 0);
        }
        return this.i;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0042  */
    @Override // com.android.tools.r8.internal.AbstractC2350k1
    public final boolean a(Object obj, boolean z) {
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
                this.b[i] = z;
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
            this.b[i] = z;
            i3 = this.g;
            this.g = i3 + 1;
            if (i3 >= this.f) {
                d(AbstractC3307wk.a(i3 + 2, 0.75f));
            }
            i2 = -1;
        }
        if (i2 < 0) {
            return false;
        }
        boolean[] zArr = this.b;
        boolean z2 = zArr[i2];
        zArr[i2] = z;
        return z2;
    }

    public C2537mR() {
        this(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e() {
        this.d = false;
        Object[] objArr = this.a;
        int i = this.e;
        objArr[i] = null;
        boolean z = this.b[i];
        int i2 = this.g - 1;
        this.g = i2;
        if (i2 < this.f / 4 && i > 16) {
            d(i / 2);
        }
        return z;
    }
}
