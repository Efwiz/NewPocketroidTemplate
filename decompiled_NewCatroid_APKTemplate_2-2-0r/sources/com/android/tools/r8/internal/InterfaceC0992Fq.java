package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC0992Fq extends InterfaceC3087tq, Map {
    /* JADX INFO: renamed from: a */
    Object put(Integer num, Object obj);

    InterfaceC2529mK c();

    @Override // java.util.Map
    Object get(Object obj);

    @Override // java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    InterfaceC1277Pr keySet();

    @Override // java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    InterfaceC2703oS values();
}
