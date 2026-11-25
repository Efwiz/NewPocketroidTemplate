package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2056gD extends AbstractC3097u {
    public C3338xA g;
    public ArrayList h;
    public ArrayList i;

    public C2056gD(C3338xA c3338xA, int[] iArr, C3338xA[] c3338xAArr) {
        super(171);
        this.g = c3338xA;
        this.h = P10.a(iArr);
        this.i = P10.a(c3338xAArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final void a(AbstractC1899eF abstractC1899eF) {
        int size = this.h.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ((Integer) this.h.get(i)).intValue();
        }
        int size2 = this.i.size();
        C3263wA[] c3263wAArr = new C3263wA[size2];
        for (int i2 = 0; i2 < size2; i2++) {
            c3263wAArr[i2] = ((C3338xA) this.i.get(i2)).d();
        }
        abstractC1899eF.a(this.g.d(), iArr, c3263wAArr);
        b(abstractC1899eF);
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final int c() {
        return 12;
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final AbstractC3097u a(Map map) {
        C3338xA c3338xA = (C3338xA) map.get(this.g);
        ArrayList arrayList = this.i;
        int size = arrayList.size();
        C3338xA[] c3338xAArr = new C3338xA[size];
        for (int i = 0; i < size; i++) {
            c3338xAArr[i] = (C3338xA) map.get(arrayList.get(i));
        }
        C2056gD c2056gD = new C2056gD(c3338xA, null, c3338xAArr);
        c2056gD.h.addAll(this.h);
        return c2056gD.a(this);
    }
}
