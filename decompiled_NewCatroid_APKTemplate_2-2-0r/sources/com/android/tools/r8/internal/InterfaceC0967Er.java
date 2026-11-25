package com.android.tools.r8.internal;

import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Er, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC0967Er extends List, Comparable, InterfaceC3088tr {
    void a(int i, int i2);

    void a(int i, int[] iArr, int i2, int i3);

    @Override // com.android.tools.r8.internal.InterfaceC3088tr
    boolean a(int i);

    void b(int i, int i2);

    int c(int i, int i2);

    int h(int i);

    int i(int i);

    @Override // java.util.List, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    InterfaceC0993Fr iterator();

    @Override // java.util.List
    InterfaceC0993Fr listIterator();
}
