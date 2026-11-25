package com.android.tools.r8.internal;

import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0988Fm extends AbstractC1016Gm {
    private Object[] c;
    private int d;
    private int e;
    private int f;

    C0988Fm(int i) {
        super(i);
        int iK = AbstractC1048Hm.k(i);
        this.c = new Object[iK];
        this.d = AbstractC1048Hm.j(iK);
        this.e = (int) (((double) iK) * 0.7d);
    }

    @Override // com.android.tools.r8.internal.AbstractC1016Gm
    final AbstractC1016Gm a(Object obj) {
        int i;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        int iHashCode = obj.hashCode();
        int iA = AbstractC0900Ck.a(iHashCode);
        int length = this.c.length - 1;
        for (int i3 = iA; i3 - iA < this.d; i3++) {
            int i4 = i3 & length;
            Object obj2 = this.c[i4];
            if (obj2 == null) {
                b(obj);
                Object[] objArr = this.c;
                objArr[i4] = obj;
                this.f += iHashCode;
                int i5 = this.b;
                if (i5 > this.e && objArr.length < 1073741824) {
                    int length2 = objArr.length * 2;
                    Object[] objArr2 = this.a;
                    int i6 = AbstractC1048Hm.c;
                    Object[] objArr3 = new Object[length2];
                    int i7 = length2 - 1;
                    for (int i8 = 0; i8 < i5; i8++) {
                        Object obj3 = objArr2[i8];
                        int iA2 = AbstractC0900Ck.a(obj3.hashCode());
                        while (true) {
                            i = iA2 & i7;
                            if (objArr3[i] == null) {
                                break;
                            }
                            iA2++;
                        }
                        objArr3[i] = obj3;
                    }
                    this.c = objArr3;
                    this.d = AbstractC1048Hm.j(length2);
                    this.e = (int) (((double) length2) * 0.7d);
                }
                return this;
            }
            if (obj2.equals(obj)) {
                return this;
            }
        }
        return new C0962Em(this).a(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1016Gm
    final AbstractC1016Gm b() {
        return new C0988Fm(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC1016Gm
    final AbstractC1016Gm c() {
        int i;
        int iK = AbstractC1048Hm.k(this.b);
        if (iK * 2 < this.c.length) {
            Object[] objArr = this.a;
            int i2 = this.b;
            Object[] objArr2 = new Object[iK];
            int i3 = iK - 1;
            for (int i4 = 0; i4 < i2; i4++) {
                Object obj = objArr[i4];
                int iA = AbstractC0900Ck.a(obj.hashCode());
                while (true) {
                    i = iA & i3;
                    if (objArr2[i] == null) {
                        break;
                    }
                    iA++;
                }
                objArr2[i] = obj;
            }
            this.c = objArr2;
            this.d = AbstractC1048Hm.j(iK);
            this.e = (int) (((double) iK) * 0.7d);
        }
        return AbstractC1048Hm.a(this.c) ? new C0962Em(this) : this;
    }

    C0988Fm(C0988Fm c0988Fm) {
        super(c0988Fm);
        Object[] objArr = c0988Fm.c;
        this.c = Arrays.copyOf(objArr, objArr.length);
        this.d = c0988Fm.d;
        this.e = c0988Fm.e;
        this.f = c0988Fm.f;
    }

    @Override // com.android.tools.r8.internal.AbstractC1016Gm
    final AbstractC1048Hm a() {
        int i = this.b;
        if (i == 0) {
            int i2 = AbstractC1048Hm.c;
            return SS.h;
        }
        if (i != 1) {
            Object[] objArrCopyOf = this.a;
            if (i != objArrCopyOf.length) {
                objArrCopyOf = Arrays.copyOf(objArrCopyOf, i);
            }
            int i3 = this.f;
            Object[] objArr = this.c;
            return new SS(i3, objArr.length - 1, objArrCopyOf, objArr);
        }
        Object obj = this.a[0];
        int i4 = AbstractC1048Hm.c;
        return new AV(obj);
    }
}
