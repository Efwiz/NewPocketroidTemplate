package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.g1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0659g1 extends O1 {
    public final Y0 a;
    public final Y0 b;
    public final Y0 c;
    public final Y0 d;
    public final Y0 e;
    public final Y0 f;
    public final Y0 g;
    public final Y0 h;
    public final Y0 i;
    public final Y0 j;
    public final Y0 k;
    public final Y0 l;
    public final Y0 m;
    public final Y0 n;
    public final Y0 o;
    public final Y0 p;
    public final Y0 q;

    public C0659g1(C0722p1 c0722p1) {
        this.a = c0722p1.a(c0722p1.I3, c0722p1.X1, "BOOTLOADER");
        this.b = c0722p1.a(c0722p1.I3, c0722p1.X1, "BRAND");
        this.c = c0722p1.a(c0722p1.I3, c0722p1.X1, "CPU_ABI");
        this.d = c0722p1.a(c0722p1.I3, c0722p1.X1, "CPU_ABI2");
        this.e = c0722p1.a(c0722p1.I3, c0722p1.X1, "DEVICE");
        this.f = c0722p1.a(c0722p1.I3, c0722p1.X1, "DISPLAY");
        this.g = c0722p1.a(c0722p1.I3, c0722p1.X1, "FINGERPRINT");
        this.h = c0722p1.a(c0722p1.I3, c0722p1.X1, "HARDWARE");
        this.i = c0722p1.a(c0722p1.I3, c0722p1.X1, "MANUFACTURER");
        this.j = c0722p1.a(c0722p1.I3, c0722p1.X1, "MODEL");
        this.k = c0722p1.a(c0722p1.I3, c0722p1.X1, "PRODUCT");
        this.l = c0722p1.a(c0722p1.I3, c0722p1.X1, "SERIAL");
        this.m = c0722p1.a(c0722p1.I3, c0722p1.Y1, "SUPPORTED_32_BIT_ABIS");
        this.n = c0722p1.a(c0722p1.I3, c0722p1.Y1, "SUPPORTED_64_BIT_ABIS");
        this.o = c0722p1.a(c0722p1.I3, c0722p1.Y1, "SUPPORTED_ABIS");
        this.p = c0722p1.a(c0722p1.I3, c0722p1.B1, "TIME");
        this.q = c0722p1.a(c0722p1.I3, c0722p1.X1, "TYPE");
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
        consumer.accept(this.h);
        consumer.accept(this.i);
        consumer.accept(this.j);
        consumer.accept(this.k);
        consumer.accept(this.l);
        consumer.accept(this.m);
        consumer.accept(this.n);
        consumer.accept(this.o);
        consumer.accept(this.p);
        consumer.accept(this.q);
    }
}
