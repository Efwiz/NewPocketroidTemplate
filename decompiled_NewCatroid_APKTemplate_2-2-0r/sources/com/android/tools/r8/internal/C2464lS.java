package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lS, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2464lS extends AbstractC3100u1 implements RandomAccess, Cloneable, Serializable {
    protected transient Object[] a;
    protected int b;

    public C2464lS(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Initial capacity (" + i + ") is negative");
        }
        this.a = new Object[i];
    }

    @Override // com.android.tools.r8.internal.InterfaceC2991sS
    public final void a(int i, int i2) {
        AbstractC2045g3.a(this.b, i, i2);
        Object[] objArr = this.a;
        System.arraycopy(objArr, i2, objArr, i, this.b - i2);
        int i3 = i2 - i;
        this.b -= i3;
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            this.a[this.b + i4] = null;
            i3 = i4;
        }
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        j(i);
        int i2 = this.b + 1;
        Object[] objArr = this.a;
        if (i2 > objArr.length) {
            Object[] objArr2 = new Object[(int) Math.max(Math.min(((long) objArr.length) * 2, 2147483639L), i2)];
            System.arraycopy(this.a, 0, objArr2, 0, this.b);
            this.a = objArr2;
        }
        int i3 = this.b;
        if (i != i3) {
            Object[] objArr3 = this.a;
            System.arraycopy(objArr3, i, objArr3, i + 1, i3 - i);
        }
        this.a[i] = obj;
        this.b++;
    }

    @Override // com.android.tools.r8.internal.AbstractC3100u1, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        Arrays.fill(this.a, 0, this.b, (Object) null);
        this.b = 0;
    }

    public final Object clone() {
        C2464lS c2464lS = new C2464lS(this.b);
        System.arraycopy(this.a, 0, c2464lS.a, 0, this.b);
        c2464lS.b = this.b;
        return c2464lS;
    }

    @Override // java.util.List
    public final Object get(int i) {
        if (i < this.b) {
            return this.a[i];
        }
        throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than or equal to list size (").append(this.b).append(Constant.AFTER_QUTO).toString());
    }

    @Override // com.android.tools.r8.internal.AbstractC3100u1, java.util.List
    public final int indexOf(Object obj) {
        for (int i = 0; i < this.b; i++) {
            if (obj == this.a[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return this.b == 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC3100u1
    /* JADX INFO: renamed from: l */
    public final InterfaceC2376kK listIterator(int i) {
        j(i);
        return new C2384kS(this, i);
    }

    @Override // com.android.tools.r8.internal.AbstractC3100u1, java.util.List
    public final int lastIndexOf(Object obj) {
        int i = this.b;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return -1;
            }
            if (obj == this.a[i2]) {
                return i2;
            }
            i = i2;
        }
    }

    @Override // java.util.List
    public final Object remove(int i) {
        int i2 = this.b;
        if (i >= i2) {
            throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than or equal to list size (").append(this.b).append(Constant.AFTER_QUTO).toString());
        }
        Object[] objArr = this.a;
        Object obj = objArr[i];
        int i3 = i2 - 1;
        this.b = i3;
        if (i != i3) {
            System.arraycopy(objArr, i + 1, objArr, i, i3 - i);
        }
        this.a[this.b] = null;
        return obj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection collection) {
        int i;
        Object[] objArr = this.a;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.b;
            if (i2 >= i) {
                break;
            }
            if (!collection.contains(objArr[i2])) {
                objArr[i3] = objArr[i2];
                i3++;
            }
            i2++;
        }
        Arrays.fill(objArr, i3, i, (Object) null);
        boolean z = this.b != i3;
        this.b = i3;
        return z;
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        if (i >= this.b) {
            throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than or equal to list size (").append(this.b).append(Constant.AFTER_QUTO).toString());
        }
        Object[] objArr = this.a;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        return obj2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.b;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        int i = this.b + 1;
        Object[] objArr = this.a;
        if (i > objArr.length) {
            Object[] objArr2 = new Object[(int) Math.max(Math.min(((long) objArr.length) * 2, 2147483639L), i)];
            System.arraycopy(this.a, 0, objArr2, 0, this.b);
            this.a = objArr2;
        }
        Object[] objArr3 = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        objArr3[i2] = obj;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf == -1) {
            return false;
        }
        remove(iIndexOf);
        return true;
    }
}
