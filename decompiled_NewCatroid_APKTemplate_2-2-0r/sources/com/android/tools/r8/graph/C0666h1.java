package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.h1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0666h1 extends O1 {
    public final Y0 a;
    public final Y0 b;
    public final Y0 c;
    public final Y0 d;
    public final Y0 e;

    public C0666h1(C0722p1 c0722p1) {
        this.a = c0722p1.a(c0722p1.J3, c0722p1.X1, "CODENAME");
        this.b = c0722p1.a(c0722p1.J3, c0722p1.X1, "RELEASE");
        this.c = c0722p1.a(c0722p1.J3, c0722p1.X1, "SDK");
        this.d = c0722p1.a(c0722p1.J3, c0722p1.A1, "SDK_INT");
        this.e = c0722p1.a(c0722p1.J3, c0722p1.X1, "SECURITY_PATCH");
    }

    @Override // com.android.tools.r8.graph.O1
    public final void a(Consumer consumer) {
        consumer.accept(this.a);
        consumer.accept(this.b);
        consumer.accept(this.c);
        consumer.accept(this.d);
        consumer.accept(this.e);
    }
}
