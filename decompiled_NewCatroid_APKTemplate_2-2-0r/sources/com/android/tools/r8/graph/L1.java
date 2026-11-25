package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class L1 extends O1 {
    public final Y0 a;
    public final Y0 b;
    public final Y0 c;
    public final Y0 d;
    public final Y0 e;
    public final Y0 f;
    public final Y0 g;

    public L1(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.A3;
        this.a = c0722p1.a(c0767v2, c0767v2, "DAYS");
        C0767v2 c0767v3 = c0722p1.A3;
        this.b = c0722p1.a(c0767v3, c0767v3, "HOURS");
        C0767v2 c0767v4 = c0722p1.A3;
        this.c = c0722p1.a(c0767v4, c0767v4, "MICROSECONDS");
        C0767v2 c0767v5 = c0722p1.A3;
        this.d = c0722p1.a(c0767v5, c0767v5, "MILLISECONDS");
        C0767v2 c0767v6 = c0722p1.A3;
        this.e = c0722p1.a(c0767v6, c0767v6, "MINUTES");
        C0767v2 c0767v7 = c0722p1.A3;
        this.f = c0722p1.a(c0767v7, c0767v7, "NANOSECONDS");
        C0767v2 c0767v8 = c0722p1.A3;
        this.g = c0722p1.a(c0767v8, c0767v8, "SECONDS");
    }

    @Override // com.android.tools.r8.graph.O1
    public final void a(Consumer consumer) {
        consumer.accept(this.a);
        consumer.accept(this.b);
        consumer.accept(this.c);
        consumer.accept(this.d);
        consumer.accept(this.e);
        consumer.accept(this.f);
        consumer.accept(this.g);
    }
}
