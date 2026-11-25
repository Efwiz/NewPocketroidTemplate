package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import java.util.Collection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.b4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC1665b4 {
    B7 a(I7 i7, C0722p1 c0722p1);

    default Collection a(I7 i7, C0722p1 c0722p1, AC ac) {
        return AbstractC1639am.c(a(i7, c0722p1));
    }
}
