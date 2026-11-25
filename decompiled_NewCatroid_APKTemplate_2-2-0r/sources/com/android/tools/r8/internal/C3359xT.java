package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import java.util.OptionalInt;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3359xT {
    private final C3634k.b a;
    private final OptionalInt b;

    private C3359xT(C3634k.b bVar, OptionalInt optionalInt) {
        this.a = bVar;
        this.b = optionalInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C3359xT a(C3634k.b bVar) {
        com.android.tools.r8.naming.N0 n0 = bVar.c;
        return new C3359xT(bVar, (n0 == null || n0.c() != 1) ? OptionalInt.empty() : OptionalInt.of(bVar.c.a));
    }
}
