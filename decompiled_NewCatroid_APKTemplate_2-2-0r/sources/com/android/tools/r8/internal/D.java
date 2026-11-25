package com.android.tools.r8.internal;

import java.util.Set;
import java.util.SortedMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class D extends B implements InterfaceC1456Vp {
    protected D() {
    }

    @Override // com.android.tools.r8.internal.B
    /* JADX INFO: renamed from: e */
    public final InterfaceC2529mK entrySet() {
        return b();
    }

    @Override // com.android.tools.r8.internal.B, java.util.Map
    public final Set entrySet() {
        return b();
    }

    @Override // java.util.SortedMap
    public final Object firstKey() {
        return Integer.valueOf(a());
    }

    @Override // java.util.SortedMap
    public final SortedMap headMap(Object obj) {
        return c(((Integer) obj).intValue());
    }

    @Override // java.util.SortedMap
    public final Object lastKey() {
        return Integer.valueOf(d());
    }

    @Override // java.util.SortedMap
    public final SortedMap subMap(Object obj, Object obj2) {
        return a(((Integer) obj).intValue(), ((Integer) obj2).intValue());
    }

    @Override // java.util.SortedMap
    public final SortedMap tailMap(Object obj) {
        return b(((Integer) obj).intValue());
    }
}
