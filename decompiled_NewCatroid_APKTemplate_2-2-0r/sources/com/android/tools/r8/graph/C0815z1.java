package com.android.tools.r8.graph;

import java.util.Set;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.graph.z1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0815z1 {
    C0737r2 a;
    Supplier b;
    Set c;
    final /* synthetic */ C0722p1 d;

    C0815z1(C0722p1 c0722p1, C0737r2 c0737r2, Supplier supplier, Set set) {
        this.d = c0722p1;
        this.a = c0737r2;
        this.b = supplier;
        this.c = com.android.tools.r8.utils.T2.a((Iterable) set);
    }
}
