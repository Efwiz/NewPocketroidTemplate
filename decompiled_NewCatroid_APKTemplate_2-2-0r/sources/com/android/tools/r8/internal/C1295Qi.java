package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qi, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1295Qi extends AbstractC3097u {
    public int g;
    public ArrayList h;
    public ArrayList i;

    private C1295Qi() {
        super(-1);
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final void a(AbstractC1899eF abstractC1899eF) {
        int i = this.g;
        if (i == -1 || i == 0) {
            abstractC1899eF.a(i, this.h.size(), a(this.h), this.i.size(), a(this.i));
            return;
        }
        if (i == 1) {
            abstractC1899eF.a(i, this.h.size(), a(this.h), 0, (Object[]) null);
            return;
        }
        if (i == 2) {
            abstractC1899eF.a(i, this.h.size(), (Object[]) null, 0, (Object[]) null);
        } else if (i == 3) {
            abstractC1899eF.a(i, 0, (Object[]) null, 0, (Object[]) null);
        } else {
            if (i != 4) {
                throw new IllegalArgumentException();
            }
            abstractC1899eF.a(i, 0, (Object[]) null, 1, a(this.i));
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final int c() {
        return 14;
    }

    public C1295Qi(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        super(-1);
        this.g = i;
        if (i == -1 || i == 0) {
            this.h = P10.a(i2, objArr);
            this.i = P10.a(i3, objArr2);
            return;
        }
        if (i == 1) {
            this.h = P10.a(i2, objArr);
            return;
        }
        if (i == 2) {
            this.h = P10.a(i2);
        } else if (i != 3) {
            if (i != 4) {
                throw new IllegalArgumentException();
            }
            this.i = P10.a(1, objArr2);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final AbstractC3097u a(Map map) {
        C1295Qi c1295Qi = new C1295Qi();
        c1295Qi.g = this.g;
        if (this.h != null) {
            c1295Qi.h = new ArrayList();
            int size = this.h.size();
            for (int i = 0; i < size; i++) {
                Object obj = this.h.get(i);
                if (obj instanceof C3338xA) {
                    obj = map.get(obj);
                }
                c1295Qi.h.add(obj);
            }
        }
        if (this.i != null) {
            c1295Qi.i = new ArrayList();
            int size2 = this.i.size();
            for (int i2 = 0; i2 < size2; i2++) {
                Object obj2 = this.i.get(i2);
                if (obj2 instanceof C3338xA) {
                    obj2 = map.get(obj2);
                }
                c1295Qi.i.add(obj2);
            }
        }
        return c1295Qi;
    }

    private static Object[] a(ArrayList arrayList) {
        int size = arrayList.size();
        Object[] objArr = new Object[size];
        for (int i = 0; i < size; i++) {
            Object objD = arrayList.get(i);
            if (objD instanceof C3338xA) {
                objD = ((C3338xA) objD).d();
            }
            objArr[i] = objD;
        }
        return objArr;
    }
}
