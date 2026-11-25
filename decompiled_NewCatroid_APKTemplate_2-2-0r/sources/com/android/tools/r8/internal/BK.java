package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class BK {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ B7 a(I7 i7, C0722p1 c0722p1) {
        return new I7(184, c0722p1.a(c0722p1.c(c0722p1.R0), i7.T().h, "hashCode"), false);
    }

    public static InterfaceC1665b4 b() {
        return new InterfaceC1665b4() { // from class: com.android.tools.r8.internal.BK$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC1665b4
            public final B7 a(I7 i7, C0722p1 c0722p1) {
                return BK.a(i7, c0722p1);
            }
        };
    }

    public static InterfaceC1665b4 a() {
        return new AK();
    }
}
