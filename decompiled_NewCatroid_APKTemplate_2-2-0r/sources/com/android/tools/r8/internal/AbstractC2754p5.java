package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;

/* JADX INFO: renamed from: com.android.tools.r8.internal.p5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2754p5 {
    private static InterfaceC1665b4 a(final P7 p7) {
        return new InterfaceC1665b4() { // from class: com.android.tools.r8.internal.p5$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC1665b4
            public final B7 a(I7 i7, C0722p1 c0722p1) {
                return AbstractC2754p5.a(p7, i7, c0722p1);
            }
        };
    }

    public static InterfaceC1665b4 b() {
        return a(P7.e);
    }

    public static InterfaceC1665b4 c() {
        return a(P7.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ B7 a(P7 p7, I7 i7, C0722p1 c0722p1) {
        return new Q7(p7, EnumC2527mI.INT);
    }

    public static InterfaceC1665b4 a() {
        return a(P7.d);
    }
}
