package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ah, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1629ah {
    AbstractC1629ah() {
    }

    static AbstractC1629ah a(C0767v2[] c0767v2Arr) {
        C0767v2 c0767v2 = c0767v2Arr[0];
        for (C0767v2 c0767v3 : c0767v2Arr) {
            if (c0767v3 != c0767v2) {
                return new C1782ch(c0767v2Arr);
            }
        }
        return new C1709bh(c0767v2Arr[0]);
    }

    abstract C0767v2 a(int i);

    abstract AbstractC1629ah a(AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2);

    public abstract boolean equals(Object obj);
}
