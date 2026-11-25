package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Lr extends P implements Serializable {
    protected transient int[] a;
    protected transient int b;
    protected transient boolean c;
    protected transient int d;
    protected transient int e;
    protected int f;
    protected final float g;

    public Lr(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.g = 0.75f;
        int iA = AbstractC3307wk.a(i, 0.75f);
        this.d = iA;
        this.b = iA - 1;
        this.e = AbstractC3307wk.b(iA, 0.75f);
        this.a = new int[this.d + 1];
    }

    private void m(int i) {
        int i2;
        int i3;
        this.f--;
        int[] iArr = this.a;
        loop0: while (true) {
            int i4 = (i + 1) & this.b;
            while (true) {
                i2 = iArr[i4];
                if (i2 != 0) {
                    int iA = AbstractC3307wk.a(i2);
                    int i5 = this.b;
                    int i6 = iA & i5;
                    if (i > i4) {
                        if (i >= i6 && i6 > i4) {
                            break;
                        } else {
                            i4 = (i4 + 1) & i5;
                        }
                    } else if (i >= i6 || i6 > i4) {
                        break;
                    } else {
                        i4 = (i4 + 1) & i5;
                    }
                } else {
                    break loop0;
                }
            }
            iArr[i] = i2;
            i = i4;
        }
        iArr[i] = 0;
        if (this.f >= this.e / 4 || (i3 = this.d) <= 16) {
            return;
        }
        l(i3 / 2);
    }

    @Override // com.android.tools.r8.internal.J
    public final boolean a(InterfaceC3088tr interfaceC3088tr) {
        if (this.g <= 0.5d) {
            int iA = AbstractC3307wk.a(interfaceC3088tr.size(), this.g);
            if (iA > this.d) {
                l(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((interfaceC3088tr.size() + this.f) / this.g), 2L, 1073741824L);
            if (iA2 > this.d) {
                l(iA2);
            }
        }
        return super.a(interfaceC3088tr);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean addAll(Collection collection) {
        if (this.g <= 0.5d) {
            int iA = AbstractC3307wk.a(collection.size(), this.g);
            if (iA > this.d) {
                l(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((collection.size() + this.f) / this.g), 2L, 1073741824L);
            if (iA2 > this.d) {
                l(iA2);
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
        Arrays.fill(this.a, 0);
    }

    public final Object clone() {
        try {
            Lr lr = (Lr) super.clone();
            lr.a = (int[]) this.a.clone();
            lr.c = this.c;
            return lr;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean f(int i) {
        int i2;
        if (i == 0) {
            return this.c;
        }
        int[] iArr = this.a;
        int iA = AbstractC3307wk.a(i) & this.b;
        int i3 = iArr[iA];
        if (i3 == 0) {
            return false;
        }
        if (i == i3) {
            return true;
        }
        do {
            iA = (iA + 1) & this.b;
            i2 = iArr[iA];
            if (i2 == 0) {
                return false;
            }
        } while (i != i2);
        return true;
    }

    @Override // com.android.tools.r8.internal.P, java.util.Collection, java.util.Set
    public final int hashCode() {
        int i;
        int i2 = this.c ? this.f - 1 : this.f;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i2 - 1;
            if (i2 == 0) {
                return i3;
            }
            while (true) {
                i = this.a[i4];
                if (i == 0) {
                    i4++;
                }
            }
            i3 += i;
            i4++;
            i2 = i5;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.f == 0;
    }

    @Override // com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC0841Ar iterator() {
        return new C1140Kr(this, 0);
    }

    @Override // com.android.tools.r8.internal.P
    public final boolean k(int i) {
        int i2;
        if (i == 0) {
            if (!this.c) {
                return false;
            }
            this.c = false;
            int[] iArr = this.a;
            int i3 = this.d;
            iArr[i3] = 0;
            int i4 = this.f - 1;
            this.f = i4;
            if (i4 < this.e / 4 && i3 > 16) {
                l(i3 / 2);
            }
            return true;
        }
        int[] iArr2 = this.a;
        int iA = AbstractC3307wk.a(i) & this.b;
        int i5 = iArr2[iA];
        if (i5 == 0) {
            return false;
        }
        if (i == i5) {
            m(iA);
            return true;
        }
        do {
            iA = (iA + 1) & this.b;
            i2 = iArr2[iA];
            if (i2 == 0) {
                return false;
            }
        } while (i != i2);
        m(iA);
        return true;
    }

    protected final void l(int i) {
        int i2;
        int[] iArr = this.a;
        int i3 = i - 1;
        int[] iArr2 = new int[i + 1];
        int i4 = this.d;
        int i5 = this.c ? this.f - 1 : this.f;
        while (true) {
            int i6 = i5 - 1;
            if (i5 == 0) {
                this.d = i;
                this.b = i3;
                this.e = AbstractC3307wk.b(i, this.g);
                this.a = iArr2;
                return;
            }
            do {
                i4--;
                i2 = iArr[i4];
            } while (i2 == 0);
            int iA = AbstractC3307wk.a(i2) & i3;
            if (iArr2[iA] != 0) {
                do {
                    iA = (iA + 1) & i3;
                } while (iArr2[iA] != 0);
            }
            iArr2[iA] = iArr[i4];
            i5 = i6;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.f;
    }

    public Lr() {
        this(16);
    }

    public Lr(C3238vr c3238vr) {
        this(c3238vr.size());
        a(c3238vr);
    }

    @Override // com.android.tools.r8.internal.J, com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean a(int i) {
        int i2;
        if (i == 0) {
            if (this.c) {
                return false;
            }
            this.c = true;
        } else {
            int[] iArr = this.a;
            int iA = AbstractC3307wk.a(i) & this.b;
            int i3 = iArr[iA];
            if (i3 != 0) {
                if (i3 == i) {
                    return false;
                }
                do {
                    iA = (iA + 1) & this.b;
                    i2 = iArr[iA];
                    if (i2 != 0) {
                    }
                } while (i2 != i);
                return false;
            }
            iArr[iA] = i;
        }
        int i4 = this.f;
        this.f = i4 + 1;
        if (i4 >= this.e) {
            l(AbstractC3307wk.a(i4 + 2, this.g));
        }
        return true;
    }
}
