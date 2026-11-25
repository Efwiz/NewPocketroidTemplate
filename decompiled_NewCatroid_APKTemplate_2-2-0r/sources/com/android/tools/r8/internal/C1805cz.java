package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1805cz {
    public final C0684j2 a;
    public final C0684j2 b;
    public final C0684j2 c;
    public final C0684j2 d;

    public C1805cz(C2037fz c2037fz) {
        C0767v2 c0767v2E = c2037fz.a.e("Lkotlin/jvm/internal/Intrinsics;");
        C0722p1 c0722p1 = c2037fz.a;
        this.a = c0722p1.a(c0767v2E, c0722p1.b(c0722p1.D1, c0722p1.X1), "throwParameterIsNullException");
        C0722p1 c0722p2 = c2037fz.a;
        this.b = c0722p2.a(c0767v2E, c0722p2.b(c0722p2.D1, c0722p2.X1), "throwParameterIsNullNPE");
        C0722p1 c0722p3 = c2037fz.a;
        c0722p3.a(c0767v2E, c0722p3.b(c0722p3.D1, c0722p3.X1), "throwParameterIsNullIAE");
        C0722p1 c0722p4 = c2037fz.a;
        this.c = c0722p4.a(c0767v2E, c0722p4.b(c0722p4.D1, c0722p4.Z1, c0722p4.X1), "checkParameterIsNotNull");
        C0722p1 c0722p5 = c2037fz.a;
        this.d = c0722p5.a(c0767v2E, c0722p5.b(c0722p5.D1, c0722p5.Z1, c0722p5.X1), "checkNotNullParameter");
        C0722p1 c0722p6 = c2037fz.a;
        c0722p6.a(c0767v2E, c0722p6.b(c0722p6.D1, new C0767v2[0]), "throwNpe");
    }
}
