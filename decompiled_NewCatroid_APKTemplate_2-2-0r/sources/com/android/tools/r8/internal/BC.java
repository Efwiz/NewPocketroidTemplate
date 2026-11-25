package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BC extends C3251w00 {
    public ArrayList f;
    public ArrayList g;
    public ArrayList h;

    public BC(int i, C00 c00, C3338xA[] c3338xAArr, C3338xA[] c3338xAArr2, int[] iArr, String str) {
        super(i, c00, str, 0);
        this.f = P10.a(c3338xAArr);
        this.g = P10.a(c3338xAArr2);
        this.h = P10.a(iArr);
    }

    public final void a(AbstractC1899eF abstractC1899eF, boolean z) {
        int size = this.f.size();
        C3263wA[] c3263wAArr = new C3263wA[size];
        C3263wA[] c3263wAArr2 = new C3263wA[this.g.size()];
        int[] iArr = new int[this.h.size()];
        for (int i = 0; i < size; i++) {
            c3263wAArr[i] = ((C3338xA) this.f.get(i)).d();
            c3263wAArr2[i] = ((C3338xA) this.g.get(i)).d();
            iArr[i] = ((Integer) this.h.get(i)).intValue();
        }
        a(abstractC1899eF.a(this.d, this.e, c3263wAArr, c3263wAArr2, iArr, this.b, z));
    }
}
