package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3298wb {
    public static InterfaceC1665b4 a() {
        return a("emptyList");
    }

    public static InterfaceC1665b4 b() {
        return a("emptyMap");
    }

    public static InterfaceC1665b4 c() {
        return a("emptySet");
    }

    private static InterfaceC1665b4 a(final String str) {
        return new InterfaceC1665b4() { // from class: com.android.tools.r8.internal.wb$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC1665b4
            public final B7 a(I7 i7, C0722p1 c0722p1) {
                return AbstractC3298wb.a(str, i7, c0722p1);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ B7 a(String str, I7 i7, C0722p1 c0722p1) {
        return new I7(184, c0722p1.a(c0722p1.h2, i7.T().h, str), false);
    }
}
