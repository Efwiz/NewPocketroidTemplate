package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: renamed from: com.android.tools.r8.internal.or, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2732or extends N implements RandomAccess, Cloneable, Serializable {
    protected transient int[] a;
    protected int b;

    protected C2732or(int[] iArr, int i) {
        this.a = iArr;
    }

    public static C2732or b(int[] iArr) {
        int length = iArr.length;
        if (length > iArr.length) {
            throw new IllegalArgumentException(AbstractC1588a5.a("The specified length (", length, ") is greater than the array size (").append(iArr.length).append(Constant.AFTER_QUTO).toString());
        }
        C2732or c2732or = new C2732or(iArr, 0);
        c2732or.b = length;
        return c2732or;
    }

    private void p(int i) {
        int[] iArr = this.a;
        int i2 = this.b;
        if (i > iArr.length) {
            int[] iArr2 = new int[(int) Math.max(Math.min(((long) iArr.length) * 2, 2147483639L), i)];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            iArr = iArr2;
        }
        this.a = iArr;
    }

    @Override // com.android.tools.r8.internal.N, com.android.tools.r8.internal.J, com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean a(int i) {
        p(this.b + 1);
        int[] iArr = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        iArr[i2] = i;
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final int c(int i, int i2) {
        if (i >= this.b) {
            throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than or equal to list size (").append(this.b).append(Constant.AFTER_QUTO).toString());
        }
        int[] iArr = this.a;
        int i3 = iArr[i];
        iArr[i] = i2;
        return i3;
    }

    @Override // com.android.tools.r8.internal.N, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        this.b = 0;
    }

    public final Object clone() {
        C2732or c2732or = new C2732or(this.b);
        System.arraycopy(this.a, 0, c2732or.a, 0, this.b);
        c2732or.b = this.b;
        return c2732or;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final int h(int i) {
        int i2 = this.b;
        if (i >= i2) {
            throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than or equal to list size (").append(this.b).append(Constant.AFTER_QUTO).toString());
        }
        int[] iArr = this.a;
        int i3 = iArr[i];
        int i4 = i2 - 1;
        this.b = i4;
        if (i != i4) {
            System.arraycopy(iArr, i + 1, iArr, i, i4 - i);
        }
        return i3;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final int i(int i) {
        if (i < this.b) {
            return this.a[i];
        }
        throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than or equal to list size (").append(this.b).append(Constant.AFTER_QUTO).toString());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return this.b == 0;
    }

    @Override // com.android.tools.r8.internal.J
    public final boolean j(int i) {
        int iM = m(i);
        if (iM == -1) {
            return false;
        }
        h(iM);
        return true;
    }

    @Override // com.android.tools.r8.internal.N
    public final int m(int i) {
        for (int i2 = 0; i2 < this.b; i2++) {
            if (i == this.a[i2]) {
                return i2;
            }
        }
        return -1;
    }

    @Override // com.android.tools.r8.internal.N
    public final int n(int i) {
        int i2 = this.b;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return -1;
            }
            if (i == this.a[i3]) {
                return i3;
            }
            i2 = i3;
        }
    }

    @Override // com.android.tools.r8.internal.N
    /* JADX INFO: renamed from: o */
    public final InterfaceC0993Fr listIterator(int i) {
        k(i);
        return new C2644nr(this, i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection collection) {
        int i;
        int[] iArr = this.a;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.b;
            if (i2 >= i) {
                break;
            }
            if (!collection.contains(Integer.valueOf(iArr[i2]))) {
                iArr[i3] = iArr[i2];
                i3++;
            }
            i2++;
        }
        boolean z = i != i3;
        this.b = i3;
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.b;
    }

    public C2732or(int i) {
        if (i >= 0) {
            this.a = new int[i];
            return;
        }
        throw new IllegalArgumentException("Initial capacity (" + i + ") is negative");
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final void a(int i, int[] iArr, int i2, int i3) {
        int length = iArr.length;
        if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + i2 + ") is negative");
        }
        if (i3 < 0) {
            throw new IllegalArgumentException("Length (" + i3 + ") is negative");
        }
        int i4 = i2 + i3;
        if (i4 <= length) {
            System.arraycopy(this.a, i, iArr, i2, i3);
            return;
        }
        throw new ArrayIndexOutOfBoundsException("Last index (" + i4 + ") is greater than array length (" + length + Constant.AFTER_QUTO);
    }

    public C2732or() {
        this(16);
    }

    public C2732or(InterfaceC1277Pr interfaceC1277Pr) {
        this(interfaceC1277Pr.size());
        this.b = AbstractC0935Dr.a(interfaceC1277Pr.iterator(), this.a);
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final void a(int i, int i2) {
        AbstractC2045g3.a(this.b, i, i2);
        int[] iArr = this.a;
        System.arraycopy(iArr, i2, iArr, i, this.b - i2);
        this.b -= i2 - i;
    }

    public C2732or(int[] iArr) {
        this(iArr, iArr.length, 0);
    }

    public C2732or(int[] iArr, int i, int i2) {
        this(i);
        System.arraycopy(iArr, 0, this.a, 0, i);
        this.b = i;
    }

    @Override // com.android.tools.r8.internal.J
    public final int[] a(int[] iArr) {
        if (iArr == null || iArr.length < this.b) {
            iArr = new int[this.b];
        }
        System.arraycopy(this.a, 0, iArr, 0, this.b);
        return iArr;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final void b(int i, int i2) {
        k(i);
        p(this.b + 1);
        int i3 = this.b;
        if (i != i3) {
            int[] iArr = this.a;
            System.arraycopy(iArr, i, iArr, i + 1, i3 - i);
        }
        this.a[i] = i2;
        this.b++;
    }

    @Override // com.android.tools.r8.internal.N
    public final boolean a(int i, InterfaceC0967Er interfaceC0967Er) {
        k(i);
        int size = interfaceC0967Er.size();
        if (size == 0) {
            return false;
        }
        p(this.b + size);
        int i2 = this.b;
        if (i != i2) {
            int[] iArr = this.a;
            System.arraycopy(iArr, i, iArr, i + size, i2 - i);
        }
        interfaceC0967Er.a(0, this.a, i, size);
        this.b += size;
        return true;
    }
}
