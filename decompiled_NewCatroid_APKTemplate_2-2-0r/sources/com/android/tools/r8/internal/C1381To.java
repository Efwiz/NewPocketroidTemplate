package com.android.tools.r8.internal;

import java.util.Comparator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.To, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1381To implements Comparator {
    final /* synthetic */ C1429Uo a;

    C1381To(C1429Uo c1429Uo) {
        this.a = c1429Uo;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ((C1354So) this.a.b.k).a.j.compare(Integer.valueOf(((InterfaceC2806pp) obj).a()), Integer.valueOf(((InterfaceC2806pp) obj2).a()));
    }
}
