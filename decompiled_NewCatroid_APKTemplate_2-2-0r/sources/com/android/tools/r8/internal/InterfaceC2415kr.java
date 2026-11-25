package com.android.tools.r8.internal;

import java.util.SortedMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC2415kr extends InterfaceC0992Fq, SortedMap {
    int a();

    InterfaceC2415kr a(int i, int i2);

    /* JADX INFO: renamed from: a */
    InterfaceC2415kr tailMap(Integer num);

    /* JADX INFO: renamed from: a */
    InterfaceC2415kr subMap(Integer num, Integer num2);

    InterfaceC2415kr b(int i);

    /* JADX INFO: renamed from: b */
    InterfaceC2415kr headMap(Integer num);

    InterfaceC2415kr c(int i);

    @Override // com.android.tools.r8.internal.InterfaceC0992Fq
    InterfaceC2842qK c();

    @Override // java.util.SortedMap
    InterfaceC3391xr comparator();

    int d();

    @Override // java.util.Map, java.util.SortedMap
    InterfaceC2842qK entrySet();

    @Override // java.util.SortedMap
    Integer firstKey();

    @Override // com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    InterfaceC1357Sr keySet();

    @Override // java.util.SortedMap
    Integer lastKey();

    @Override // com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    InterfaceC2703oS values();
}
