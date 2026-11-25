package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC1899eF;
import com.android.tools.r8.internal.C1303Qq;
import com.android.tools.r8.internal.C3263wA;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class S extends AbstractC1899eF {
    private final String j;
    private final Map k;

    private S(String str, C1303Qq c1303Qq) {
        super(589824);
        this.j = str;
        this.k = c1303Qq;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, C3263wA c3263wA) {
        this.k.put(Integer.valueOf(i), this.j);
    }
}
