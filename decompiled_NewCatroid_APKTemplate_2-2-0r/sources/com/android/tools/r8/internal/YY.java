package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class YY extends AbstractC3097u {
    public int g;
    public int h;
    public C3338xA i;
    public ArrayList j;

    public YY(int i, int i2, C3338xA c3338xA, C3338xA... c3338xAArr) {
        super(170);
        this.g = i;
        this.h = i2;
        this.i = c3338xA;
        this.j = P10.a(c3338xAArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final void a(AbstractC1899eF abstractC1899eF) {
        int size = this.j.size();
        C3263wA[] c3263wAArr = new C3263wA[size];
        for (int i = 0; i < size; i++) {
            c3263wAArr[i] = ((C3338xA) this.j.get(i)).d();
        }
        abstractC1899eF.a(this.g, this.h, this.i.d(), c3263wAArr);
        b(abstractC1899eF);
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final int c() {
        return 11;
    }

    @Override // com.android.tools.r8.internal.AbstractC3097u
    public final AbstractC3097u a(Map map) {
        int i = this.g;
        int i2 = this.h;
        C3338xA c3338xA = (C3338xA) map.get(this.i);
        ArrayList arrayList = this.j;
        int size = arrayList.size();
        C3338xA[] c3338xAArr = new C3338xA[size];
        for (int i3 = 0; i3 < size; i3++) {
            c3338xAArr[i3] = (C3338xA) map.get(arrayList.get(i3));
        }
        return new YY(i, i2, c3338xA, c3338xAArr).a(this);
    }
}
