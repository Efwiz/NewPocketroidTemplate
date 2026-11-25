package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2995sW {
    final C0767v2 a;
    final C0767v2 b;
    final C0684j2 c;

    C2995sW(C0722p1 c0722p1) {
        this.a = c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServer;");
        C0767v2 c0767v2E = c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;");
        this.b = c0767v2E;
        this.c = c0722p1.a(c0767v2E, c0722p1.b(c0722p1.D1, c0722p1.X1), "addMethod");
    }
}
