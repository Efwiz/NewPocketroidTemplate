package com.android.tools.r8.graph;

import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S1 {
    public final C0684j2 a;
    public final C0684j2 b;
    public final C0684j2 c;
    public final C0684j2 d;
    public final C0684j2 e;
    public final C0684j2 f;
    public final C0684j2 g;
    public final C0684j2 h;
    public final C0684j2 i;
    public final C0684j2 j;
    public final C0684j2 k;

    private S1(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.g2;
        C0767v2 c0767v3 = c0722p1.v1;
        C0767v2 c0767v4 = c0722p1.Z1;
        this.a = c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v4, c0767v4), Namer.EQUALS_METHOD_NAME);
        this.b = c0722p1.a(c0722p1.g2, c0722p1.b(c0722p1.A1, c0722p1.Z1), "hashCode");
        this.c = c0722p1.a(c0722p1.g2, c0722p1.b(c0722p1.v1, c0722p1.Z1), "isNull");
        this.d = c0722p1.a(c0722p1.g2, c0722p1.b(c0722p1.v1, c0722p1.Z1), "nonNull");
        C0767v2 c0767v5 = c0722p1.g2;
        C0767v2 c0767v6 = c0722p1.Z1;
        this.h = c0722p1.a(c0767v5, c0722p1.b(c0767v6, c0767v6, c0767v6), "requireNonNullElse");
        C0767v2 c0767v7 = c0722p1.g2;
        C0767v2 c0767v8 = c0722p1.Z1;
        this.i = c0722p1.a(c0767v7, c0722p1.b(c0767v8, c0767v8, c0722p1.R2), "requireNonNullElseGet");
        this.j = c0722p1.a(c0722p1.g2, c0722p1.b(c0722p1.X1, c0722p1.Z1), "toString");
        C0767v2 c0767v9 = c0722p1.g2;
        C0767v2 c0767v10 = c0722p1.X1;
        this.k = c0722p1.a(c0767v9, c0722p1.b(c0767v10, c0722p1.Z1, c0767v10), "toString");
        C0759u2 c0759u2C = c0722p1.c("requireNonNull");
        C0767v2 c0767v11 = c0722p1.g2;
        C0767v2 c0767v12 = c0722p1.Z1;
        this.e = c0722p1.a(c0767v11, c0722p1.b(c0767v12, c0767v12), c0759u2C);
        C0767v2 c0767v13 = c0722p1.g2;
        C0767v2 c0767v14 = c0722p1.Z1;
        this.f = c0722p1.a(c0767v13, c0722p1.b(c0767v14, c0767v14, c0722p1.X1), c0759u2C);
        C0767v2 c0767v15 = c0722p1.g2;
        C0767v2 c0767v16 = c0722p1.Z1;
        this.g = c0722p1.a(c0767v15, c0722p1.b(c0767v16, c0767v16, c0722p1.R2), c0759u2C);
    }
}
