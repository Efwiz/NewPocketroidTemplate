package com.android.tools.r8.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class N1 extends LinkedHashMap {
    private final int a;

    public N1(int i) {
        super(i, 0.75f);
        this.a = i;
    }

    @Override // java.util.LinkedHashMap
    protected final boolean removeEldestEntry(Map.Entry entry) {
        return size() > this.a;
    }
}
