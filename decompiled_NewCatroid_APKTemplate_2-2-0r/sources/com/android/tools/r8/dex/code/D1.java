package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0656f5;
import java.nio.ShortBuffer;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class D1 extends O {
    public B1[] a(ShortBuffer shortBuffer, int i, int i2, C0656f5 c0656f5) {
        C1 c1 = new C1(i, i2, shortBuffer);
        ArrayList arrayList = new ArrayList(i2);
        while (c1.a - c1.d > 0) {
            arrayList.add(O.a(c1.b(), c1.b(), c1, c0656f5));
        }
        return (B1[]) arrayList.toArray(B1.b);
    }
}
