package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jS, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2306jS extends AbstractC2820q1 implements Cloneable {
    protected transient Object[] a;
    protected transient Object[] b;
    protected transient int c;
    protected transient boolean d;
    protected transient int e;
    protected transient int f;
    protected int g;
    protected transient InterfaceC1614aS h;
    protected transient InterfaceC3211vS i;
    protected transient InterfaceC2703oS j;

    public C2306jS(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        int iA = AbstractC3307wk.a(i, 0.75f);
        this.e = iA;
        this.c = iA - 1;
        this.f = AbstractC3307wk.b(iA, 0.75f);
        int i2 = this.e + 1;
        this.a = new Object[i2];
        this.b = new Object[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object e(int i) {
        Object obj;
        int i2;
        Object[] objArr = this.b;
        Object obj2 = objArr[i];
        objArr[i] = null;
        this.g--;
        Object[] objArr2 = this.a;
        loop0: while (true) {
            int i3 = (i + 1) & this.c;
            while (true) {
                obj = objArr2[i3];
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
            objArr2[i] = obj;
            Object[] objArr3 = this.b;
            objArr3[i] = objArr3[i3];
            i = i3;
        }
        objArr2[i] = null;
        this.b[i] = null;
        if (this.g < this.f / 4 && (i2 = this.e) > 16) {
            d(i2 / 2);
        }
        return obj2;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        if (this.g == 0) {
            return;
        }
        this.g = 0;
        this.d = false;
        Arrays.fill(this.a, (Object) null);
        Arrays.fill(this.b, (Object) null);
    }

    public final Object clone() {
        try {
            C2306jS c2306jS = (C2306jS) super.clone();
            c2306jS.i = null;
            c2306jS.j = null;
            c2306jS.h = null;
            c2306jS.d = this.d;
            c2306jS.a = (Object[]) this.a.clone();
            c2306jS.b = (Object[]) this.b.clone();
            return c2306jS;
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
        Object[] objArr = this.b;
        Object[] objArr2 = this.a;
        if (this.d && objArr[this.e] == obj) {
            return true;
        }
        int i = this.e;
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
        Object obj;
        Object[] objArr = this.a;
        Object[] objArr2 = this.b;
        int i2 = i - 1;
        int i3 = i + 1;
        Object[] objArr3 = new Object[i3];
        Object[] objArr4 = new Object[i3];
        int i4 = this.e;
        int i5 = this.d ? this.g - 1 : this.g;
        while (true) {
            int i6 = i5 - 1;
            if (i5 == 0) {
                objArr4[i] = objArr2[this.e];
                this.e = i;
                this.c = i2;
                this.f = AbstractC3307wk.b(i, 0.75f);
                this.a = objArr3;
                this.b = objArr4;
                return;
            }
            do {
                i4--;
                obj = objArr[i4];
            } while (obj == null);
            int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & i2;
            if (objArr3[iA] != null) {
                do {
                    iA = (iA + 1) & i2;
                } while (objArr3[iA] != null);
            }
            objArr3[iA] = objArr[i4];
            objArr4[iA] = objArr2[i4];
            i5 = i6;
        }
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        Object obj2;
        if (obj == null) {
            if (this.d) {
                return this.b[this.e];
            }
            return null;
        }
        Object[] objArr = this.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.c;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return null;
        }
        if (obj == obj3) {
            return this.b[iA];
        }
        do {
            iA = (iA + 1) & this.c;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return null;
            }
        } while (obj != obj2);
        return this.b[iA];
    }

    @Override // java.util.Map
    public final int hashCode() {
        Object obj;
        int i = this.d ? this.g - 1 : this.g;
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
                iIdentityHashCode = System.identityHashCode(obj);
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
        Object obj3 = this.b[this.e];
        return i3 + (obj3 != null ? System.identityHashCode(obj3) : 0);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.g == 0;
    }

    @Override // java.util.Map
    public final Set keySet() {
        if (this.i == null) {
            this.i = new C1925eS(this, 0);
        }
        return this.i;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0042  */
    @Override // com.android.tools.r8.internal.AbstractC2820q1, java.util.Map
    public final Object put(Object obj, Object obj2) {
        int i;
        int i2;
        int i3;
        if (obj != null) {
            Object[] objArr = this.a;
            int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.c;
            Object obj3 = objArr[iA];
            if (obj3 != null) {
                if (obj3 != obj) {
                    while (true) {
                        iA = (iA + 1) & this.c;
                        Object obj4 = objArr[iA];
                        if (obj4 != null) {
                            if (obj4 == obj) {
                            }
                        }
                    }
                }
                i2 = iA;
            }
            i = iA;
            this.a[i] = obj;
            this.b[i] = obj2;
            i3 = this.g;
            this.g = i3 + 1;
            if (i3 >= this.f) {
                d(AbstractC3307wk.a(i3 + 2, 0.75f));
            }
            i2 = -1;
        } else if (this.d) {
            i2 = this.e;
        } else {
            this.d = true;
            i = this.e;
            this.a[i] = obj;
            this.b[i] = obj2;
            i3 = this.g;
            this.g = i3 + 1;
            if (i3 >= this.f) {
                d(AbstractC3307wk.a(i3 + 2, 0.75f));
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

    @Override // com.android.tools.r8.internal.AbstractC2820q1, java.util.Map
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

    @Override // java.util.Map
    public final Object remove(Object obj) {
        Object obj2;
        if (obj != null) {
            Object[] objArr = this.a;
            int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.c;
            Object obj3 = objArr[iA];
            if (obj3 == null) {
                return null;
            }
            if (obj == obj3) {
                return e(iA);
            }
            do {
                iA = (iA + 1) & this.c;
                obj2 = objArr[iA];
                if (obj2 == null) {
                    return null;
                }
            } while (obj != obj2);
            return e(iA);
        }
        if (!this.d) {
            return null;
        }
        this.d = false;
        Object[] objArr2 = this.a;
        int i = this.e;
        objArr2[i] = null;
        Object[] objArr3 = this.b;
        Object obj4 = objArr3[i];
        objArr3[i] = null;
        int i2 = this.g - 1;
        this.g = i2;
        if (i2 < this.f / 4 && i > 16) {
            d(i / 2);
        }
        return obj4;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.g;
    }

    @Override // java.util.Map
    public final Collection values() {
        if (this.j == null) {
            this.j = new C1694bS(this);
        }
        return this.j;
    }

    static void a(C2306jS c2306jS) {
        c2306jS.d = false;
        Object[] objArr = c2306jS.a;
        int i = c2306jS.e;
        objArr[i] = null;
        Object[] objArr2 = c2306jS.b;
        Object obj = objArr2[i];
        objArr2[i] = null;
        int i2 = c2306jS.g - 1;
        c2306jS.g = i2;
        if (i2 >= c2306jS.f / 4 || i <= 16) {
            return;
        }
        c2306jS.d(i / 2);
    }
}
