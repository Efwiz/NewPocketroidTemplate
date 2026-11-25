package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2879qr extends P implements Serializable {
    private transient int[] a;
    private int b;

    public C2879qr() {
        this.a = AbstractC2949rr.a;
    }

    static /* synthetic */ int b(C2879qr c2879qr) {
        int i = c2879qr.b;
        c2879qr.b = i - 1;
        return i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.b = 0;
    }

    public final Object clone() {
        try {
            C2879qr c2879qr = (C2879qr) super.clone();
            c2879qr.a = (int[]) this.a.clone();
            return c2879qr;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean f(int i) {
        int i2;
        int i3 = this.b;
        while (true) {
            i2 = i3 - 1;
            if (i3 == 0) {
                i2 = -1;
                break;
            }
            if (this.a[i2] == i) {
                break;
            }
            i3 = i2;
        }
        return i2 != -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.b == 0;
    }

    @Override // com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC0841Ar iterator() {
        return new C2808pr(this);
    }

    @Override // com.android.tools.r8.internal.P
    public final boolean k(int i) {
        int i2;
        int i3 = this.b;
        while (true) {
            i2 = i3 - 1;
            if (i3 == 0) {
                i2 = -1;
                break;
            }
            if (this.a[i2] == i) {
                break;
            }
            i3 = i2;
        }
        if (i2 == -1) {
            return false;
        }
        int i4 = (this.b - i2) - 1;
        for (int i5 = 0; i5 < i4; i5++) {
            int[] iArr = this.a;
            int i6 = i2 + i5;
            iArr[i6] = iArr[i6 + 1];
        }
        this.b--;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.J, com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean a(int i) {
        int i2;
        int i3 = this.b;
        while (true) {
            i2 = i3 - 1;
            if (i3 == 0) {
                i2 = -1;
                break;
            }
            if (this.a[i2] == i) {
                break;
            }
            i3 = i2;
        }
        if (i2 != -1) {
            return false;
        }
        int i4 = this.b;
        if (i4 == this.a.length) {
            int[] iArr = new int[i4 == 0 ? 2 : i4 * 2];
            while (true) {
                int i5 = i4 - 1;
                if (i4 == 0) {
                    break;
                }
                iArr[i5] = this.a[i5];
                i4 = i5;
            }
            this.a = iArr;
        }
        int[] iArr2 = this.a;
        int i6 = this.b;
        this.b = i6 + 1;
        iArr2[i6] = i;
        return true;
    }

    @Override // com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final Iterator iterator() {
        return new C2808pr(this);
    }

    public C2879qr(int i) {
        this.a = new int[i];
    }

    public C2879qr(InterfaceC1277Pr interfaceC1277Pr) {
        this(interfaceC1277Pr.size());
        a(interfaceC1277Pr);
    }

    public C2879qr(Set set) {
        this(set.size());
        addAll(set);
    }

    public C2879qr(int[] iArr, int i) {
        this.a = iArr;
        this.b = i;
        if (i > iArr.length) {
            throw new IllegalArgumentException(AbstractC1588a5.a("The provided size (", i, ") is larger than or equal to the array size (").append(iArr.length).append(Constant.AFTER_QUTO).toString());
        }
    }
}
