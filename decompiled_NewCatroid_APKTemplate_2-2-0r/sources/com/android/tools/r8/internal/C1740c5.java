package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.c5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1740c5 extends AbstractC2347k implements Serializable {
    private transient boolean[] a;
    private int b;

    public C1740c5(boolean[] zArr, int i) {
        this.a = zArr;
        this.b = i;
        if (i > zArr.length) {
            throw new IllegalArgumentException(AbstractC1588a5.a("The provided size (", i, ") is larger than or equal to the array size (").append(zArr.length).append(Constant.AFTER_QUTO).toString());
        }
    }

    static /* synthetic */ int b(C1740c5 c1740c5) {
        int i = c1740c5.b;
        c1740c5.b = i - 1;
        return i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.b = 0;
    }

    public final Object clone() {
        try {
            C1740c5 c1740c5 = (C1740c5) super.clone();
            c1740c5.a = (boolean[]) this.a.clone();
            return c1740c5;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2347k
    public final boolean d(boolean z) {
        int i;
        int i2 = this.b;
        while (true) {
            i = i2 - 1;
            if (i2 == 0) {
                i = -1;
                break;
            }
            if (this.a[i] == z) {
                break;
            }
            i2 = i;
        }
        if (i == -1) {
            return false;
        }
        int i3 = (this.b - i) - 1;
        for (int i4 = 0; i4 < i3; i4++) {
            boolean[] zArr = this.a;
            int i5 = i + i4;
            zArr[i5] = zArr[i5 + 1];
        }
        this.b--;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.b == 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC1889e5, java.util.Set
    public final InterfaceC2282j5 iterator() {
        return new C1666b5(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h
    public final boolean a(boolean z) {
        int i;
        int i2 = this.b;
        while (true) {
            i = i2 - 1;
            if (i2 == 0) {
                i = -1;
                break;
            }
            if (this.a[i] == z) {
                break;
            }
            i2 = i;
        }
        if (i != -1) {
            return false;
        }
        int i3 = this.b;
        if (i3 == this.a.length) {
            boolean[] zArr = new boolean[i3 == 0 ? 2 : i3 * 2];
            while (true) {
                int i4 = i3 - 1;
                if (i3 == 0) {
                    break;
                }
                zArr[i4] = this.a[i4];
                i3 = i4;
            }
            this.a = zArr;
        }
        boolean[] zArr2 = this.a;
        int i5 = this.b;
        this.b = i5 + 1;
        zArr2[i5] = z;
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h
    public final boolean b(boolean z) {
        int i;
        int i2 = this.b;
        while (true) {
            i = i2 - 1;
            if (i2 == 0) {
                i = -1;
                break;
            }
            if (this.a[i] == z) {
                break;
            }
            i2 = i;
        }
        return i != -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC1889e5, java.util.Set
    public final Iterator iterator() {
        return new C1666b5(this);
    }
}
