package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class SC extends AbstractC1807d0 implements RandomAccess, Cloneable, Serializable {
    protected transient long[] a;
    protected int b;

    public SC(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Initial capacity (" + i + ") is negative");
        }
        this.a = new long[i];
    }

    @Override // com.android.tools.r8.internal.YC
    public final void a(int i, long j) {
        j(i);
        int i2 = this.b;
        int i3 = i2 + 1;
        long[] jArr = this.a;
        if (i3 > jArr.length) {
            long[] jArr2 = new long[(int) Math.max(Math.min(((long) jArr.length) * 2, 2147483639L), i3)];
            System.arraycopy(jArr, 0, jArr2, 0, i2);
            jArr = jArr2;
        }
        this.a = jArr;
        int i4 = this.b;
        if (i != i4) {
            System.arraycopy(jArr, i, jArr, i + 1, i4 - i);
        }
        this.a[i] = j;
        this.b++;
    }

    @Override // com.android.tools.r8.internal.YC
    public final long b(int i) {
        if (i < this.b) {
            return this.a[i];
        }
        throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than or equal to list size (").append(this.b).append(Constant.AFTER_QUTO).toString());
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    public final boolean c(long j) {
        int iD = d(j);
        if (iD == -1) {
            return false;
        }
        e(iD);
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        this.b = 0;
    }

    public final Object clone() {
        SC sc = new SC(this.b);
        System.arraycopy(this.a, 0, sc.a, 0, this.b);
        sc.b = this.b;
        return sc;
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0
    public final int d(long j) {
        for (int i = 0; i < this.b; i++) {
            if (j == this.a[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0
    public final int e(long j) {
        int i = this.b;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return -1;
            }
            if (j == this.a[i2]) {
                return i2;
            }
            i = i2;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return this.b == 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0
    /* JADX INFO: renamed from: l */
    public final AbstractC1880e0 listIterator(int i) {
        j(i);
        return new RC(this, i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection collection) {
        int i;
        long[] jArr = this.a;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.b;
            if (i2 >= i) {
                break;
            }
            if (!collection.contains(Long.valueOf(jArr[i2]))) {
                jArr[i3] = jArr[i2];
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

    @Override // com.android.tools.r8.internal.YC
    public final long e(int i) {
        int i2 = this.b;
        if (i < i2) {
            long[] jArr = this.a;
            long j = jArr[i];
            int i3 = i2 - 1;
            this.b = i3;
            if (i != i3) {
                System.arraycopy(jArr, i + 1, jArr, i, i3 - i);
            }
            return j;
        }
        throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than or equal to list size (").append(this.b).append(Constant.AFTER_QUTO).toString());
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    public final boolean a(long j) {
        int i = this.b;
        int i2 = i + 1;
        long[] jArr = this.a;
        if (i2 > jArr.length) {
            long[] jArr2 = new long[(int) Math.max(Math.min(((long) jArr.length) * 2, 2147483639L), i2)];
            System.arraycopy(jArr, 0, jArr2, 0, i);
            jArr = jArr2;
        }
        this.a = jArr;
        int i3 = this.b;
        this.b = i3 + 1;
        jArr[i3] = j;
        return true;
    }

    @Override // com.android.tools.r8.internal.YC
    public final void a(int i, int i2) {
        AbstractC2045g3.a(this.b, i, i2);
        long[] jArr = this.a;
        System.arraycopy(jArr, i2, jArr, i, this.b - i2);
        this.b -= i2 - i;
    }

    @Override // com.android.tools.r8.internal.YC
    public final long b(int i, long j) {
        if (i < this.b) {
            long[] jArr = this.a;
            long j2 = jArr[i];
            jArr[i] = j;
            return j2;
        }
        throw new IndexOutOfBoundsException(AbstractC1588a5.a("Index (", i, ") is greater than or equal to list size (").append(this.b).append(Constant.AFTER_QUTO).toString());
    }
}
