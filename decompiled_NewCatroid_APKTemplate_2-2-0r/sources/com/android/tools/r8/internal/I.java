package com.android.tools.r8.internal;

import java.util.Set;
import java.util.SortedMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class I extends G implements InterfaceC2415kr {
    protected I() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    /* JADX INFO: renamed from: a */
    public final InterfaceC2415kr tailMap(Integer num) {
        return b(num.intValue());
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    /* JADX INFO: renamed from: b */
    public final InterfaceC2415kr headMap(Integer num) {
        return c(num.intValue());
    }

    @Override // com.android.tools.r8.internal.G
    /* JADX INFO: renamed from: e */
    public final InterfaceC2529mK entrySet() {
        return c();
    }

    @Override // com.android.tools.r8.internal.G, java.util.Map
    public final InterfaceC2842qK entrySet() {
        return c();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final Integer firstKey() {
        return Integer.valueOf(a());
    }

    @Override // java.util.SortedMap
    public final SortedMap headMap(Object obj) {
        return c(((Integer) obj).intValue());
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final Integer lastKey() {
        return Integer.valueOf(d());
    }

    @Override // java.util.SortedMap
    public final SortedMap tailMap(Object obj) {
        return b(((Integer) obj).intValue());
    }

    @Override // java.util.SortedMap
    /* JADX INFO: renamed from: a */
    public final InterfaceC2415kr subMap(Integer num, Integer num2) {
        return a(num.intValue(), num2.intValue());
    }

    @Override // com.android.tools.r8.internal.G, java.util.Map
    public final Set entrySet() {
        return c();
    }

    @Override // java.util.SortedMap
    public final Object firstKey() {
        return Integer.valueOf(a());
    }

    @Override // java.util.SortedMap
    public final Object lastKey() {
        return Integer.valueOf(d());
    }
}
