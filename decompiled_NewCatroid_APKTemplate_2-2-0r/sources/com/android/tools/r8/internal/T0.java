package com.android.tools.r8.internal;

import java.util.Set;
import java.util.SortedMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class T0 extends S0 implements SortedMap {
    protected T0() {
    }

    @Override // java.util.Map, java.util.SortedMap
    public final Set entrySet() {
        OJ oj = (OJ) this;
        if (oj.k == null) {
            oj.k = new LJ(oj, 0);
        }
        return oj.k;
    }
}
