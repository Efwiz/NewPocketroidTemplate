package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import java.util.IdentityHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Zh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1556Zh {
    private final IdentityHashMap a;

    private C1556Zh() {
        this.a = new IdentityHashMap();
    }

    final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        this.a.put(y0, y1);
    }

    final C1630ai a(C0722p1 c0722p1) {
        return new C1630ai(this.a, c0722p1, AbstractC1476Wj.e());
    }
}
