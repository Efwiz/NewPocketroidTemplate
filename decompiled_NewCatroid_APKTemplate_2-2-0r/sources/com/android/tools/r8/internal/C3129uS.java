package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uS, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3129uS extends AbstractC3177v1 implements Serializable {
    protected transient Object[] a;
    protected transient int b;
    protected transient boolean c;
    protected transient int d;
    protected transient int e;
    protected int f;
    protected final float g = 0.75f;

    public C3129uS(int i) {
        int iA = AbstractC3307wk.a(16, 0.75f);
        this.d = iA;
        this.b = iA - 1;
        this.e = AbstractC3307wk.b(iA, 0.75f);
        this.a = new Object[this.d + 1];
    }

    private void k(int i) {
        Object obj;
        int i2;
        this.f--;
        Object[] objArr = this.a;
        loop0: while (true) {
            int i3 = (i + 1) & this.b;
            while (true) {
                obj = objArr[i3];
                if (obj != null) {
                    int iA = AbstractC3307wk.a(System.identityHashCode(obj));
                    int i4 = this.b;
                    int i5 = iA & i4;
                    if (i > i3) {
                        if (i >= i5 && i5 > i3) {
                            break;
                        } else {
                            i3 = (i3 + 1) & i4;
                        }
                    } else if (i >= i5 || i5 > i3) {
                        break;
                    } else {
                        i3 = (i3 + 1) & i4;
                    }
                } else {
                    break loop0;
                }
            }
            objArr[i] = obj;
            i = i3;
        }
        objArr[i] = null;
        if (this.f >= this.e / 4 || (i2 = this.d) <= 16) {
            return;
        }
        j(i2 / 2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        Object obj2;
        if (obj != null) {
            Object[] objArr = this.a;
            int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.b;
            Object obj3 = objArr[iA];
            if (obj3 != null) {
                if (obj3 == obj) {
                    return false;
                }
                do {
                    iA = (iA + 1) & this.b;
                    obj2 = objArr[iA];
                    if (obj2 != null) {
                    }
                } while (obj2 != obj);
                return false;
            }
            objArr[iA] = obj;
        } else {
            if (this.c) {
                return false;
            }
            this.c = true;
        }
        int i = this.f;
        this.f = i + 1;
        if (i >= this.e) {
            j(AbstractC3307wk.a(i + 2, this.g));
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean addAll(Collection collection) {
        if (this.g <= 0.5d) {
            int iA = AbstractC3307wk.a(collection.size(), this.g);
            if (iA > this.d) {
                j(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((collection.size() + this.f) / this.g), 2L, 1073741824L);
            if (iA2 > this.d) {
                j(iA2);
            }
        }
        return super.addAll(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        if (this.f == 0) {
            return;
        }
        this.f = 0;
        this.c = false;
        Arrays.fill(this.a, (Object) null);
    }

    public final Object clone() {
        try {
            C3129uS c3129uS = (C3129uS) super.clone();
            c3129uS.a = (Object[]) this.a.clone();
            c3129uS.c = this.c;
            return c3129uS;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        Object obj2;
        if (obj == null) {
            return this.c;
        }
        Object[] objArr = this.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.b;
        Object obj3 = objArr[iA];
        if (obj3 == null) {
            return false;
        }
        if (obj == obj3) {
            return true;
        }
        do {
            iA = (iA + 1) & this.b;
            obj2 = objArr[iA];
            if (obj2 == null) {
                return false;
            }
        } while (obj != obj2);
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3177v1, java.util.Collection, java.util.Set
    public final int hashCode() {
        Object obj;
        int i = this.c ? this.f - 1 : this.f;
        int iIdentityHashCode = 0;
        int i2 = 0;
        while (true) {
            int i3 = i - 1;
            if (i == 0) {
                return iIdentityHashCode;
            }
            while (true) {
                obj = this.a[i2];
                if (obj != null) {
                    break;
                }
                i2++;
            }
            if (this != obj) {
                iIdentityHashCode = System.identityHashCode(obj) + iIdentityHashCode;
            }
            i2++;
            i = i3;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.f == 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC2891r1, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC2703oS
    public final InterfaceC1912eK iterator() {
        return new C3060tS(this, 0);
    }

    protected final void j(int i) {
        Object obj;
        Object[] objArr = this.a;
        int i2 = i - 1;
        Object[] objArr2 = new Object[i + 1];
        int i3 = this.d;
        int i4 = this.c ? this.f - 1 : this.f;
        while (true) {
            int i5 = i4 - 1;
            if (i4 == 0) {
                this.d = i;
                this.b = i2;
                this.e = AbstractC3307wk.b(i, this.g);
                this.a = objArr2;
                return;
            }
            do {
                i3--;
                obj = objArr[i3];
            } while (obj == null);
            int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & i2;
            if (objArr2[iA] != null) {
                do {
                    iA = (iA + 1) & i2;
                } while (objArr2[iA] != null);
            }
            objArr2[iA] = objArr[i3];
            i4 = i5;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        Object obj2;
        if (obj == null) {
            if (!this.c) {
                return false;
            }
            this.c = false;
            Object[] objArr = this.a;
            int i = this.d;
            objArr[i] = null;
            int i2 = this.f - 1;
            this.f = i2;
            if (i2 < this.e / 4 && i > 16) {
                j(i / 2);
            }
            return true;
        }
        Object[] objArr2 = this.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(obj)) & this.b;
        Object obj3 = objArr2[iA];
        if (obj3 == null) {
            return false;
        }
        if (obj == obj3) {
            k(iA);
            return true;
        }
        do {
            iA = (iA + 1) & this.b;
            obj2 = objArr2[iA];
            if (obj2 == null) {
                return false;
            }
        } while (obj != obj2);
        k(iA);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.f;
    }
}
