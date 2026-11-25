package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nS, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2612nS extends AbstractC3177v1 implements Serializable {
    private transient Object[] a;
    private int b;

    public C2612nS(int i, Object[] objArr) {
        this.a = objArr;
        this.b = i;
        if (i > objArr.length) {
            throw new IllegalArgumentException(AbstractC1588a5.a("The provided size (", i, ") is larger than or equal to the array size (").append(objArr.length).append(Constant.AFTER_QUTO).toString());
        }
    }

    static /* synthetic */ int b(C2612nS c2612nS) {
        int i = c2612nS.b;
        c2612nS.b = i - 1;
        return i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        int i;
        int i2 = this.b;
        while (true) {
            i = i2 - 1;
            if (i2 == 0) {
                i = -1;
                break;
            }
            if (this.a[i] == obj) {
                break;
            }
            i2 = i;
        }
        if (i != -1) {
            return false;
        }
        int i3 = this.b;
        if (i3 == this.a.length) {
            Object[] objArr = new Object[i3 == 0 ? 2 : i3 * 2];
            while (true) {
                int i4 = i3 - 1;
                if (i3 == 0) {
                    break;
                }
                objArr[i4] = this.a[i4];
                i3 = i4;
            }
            this.a = objArr;
        }
        Object[] objArr2 = this.a;
        int i5 = this.b;
        this.b = i5 + 1;
        objArr2[i5] = obj;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        Arrays.fill(this.a, 0, this.b, (Object) null);
        this.b = 0;
    }

    public final Object clone() {
        try {
            C2612nS c2612nS = (C2612nS) super.clone();
            c2612nS.a = (Object[]) this.a.clone();
            return c2612nS;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        int i;
        int i2 = this.b;
        while (true) {
            i = i2 - 1;
            if (i2 == 0) {
                i = -1;
                break;
            }
            if (this.a[i] == obj) {
                break;
            }
            i2 = i;
        }
        return i != -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.b == 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC2891r1, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC2703oS
    public final InterfaceC1912eK iterator() {
        return new C2538mS(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        int i;
        int i2 = this.b;
        while (true) {
            i = i2 - 1;
            if (i2 == 0) {
                i = -1;
                break;
            }
            if (this.a[i] == obj) {
                break;
            }
            i2 = i;
        }
        if (i == -1) {
            return false;
        }
        int i3 = (this.b - i) - 1;
        for (int i4 = 0; i4 < i3; i4++) {
            Object[] objArr = this.a;
            int i5 = i + i4;
            objArr[i5] = objArr[i5 + 1];
        }
        int i6 = this.b - 1;
        this.b = i6;
        this.a[i6] = null;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC2891r1, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC2703oS
    public final Iterator iterator() {
        return new C2538mS(this);
    }
}
