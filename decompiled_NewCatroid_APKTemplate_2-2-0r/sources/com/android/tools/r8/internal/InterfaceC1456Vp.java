package com.android.tools.r8.internal;

import java.util.SortedMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC1456Vp extends InterfaceC2947rp, SortedMap {
    int a();

    InterfaceC1456Vp a(int i, int i2);

    InterfaceC1456Vp b(int i);

    @Override // com.android.tools.r8.internal.InterfaceC2947rp
    InterfaceC2842qK b();

    InterfaceC1456Vp c(int i);

    @Override // java.util.SortedMap
    InterfaceC3391xr comparator();

    int d();

    @Override // java.util.Map, java.util.SortedMap
    InterfaceC1357Sr keySet();

    @Override // com.android.tools.r8.internal.InterfaceC2947rp, java.util.Map, java.util.SortedMap
    ZJ values();
}
