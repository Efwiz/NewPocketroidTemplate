package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.m1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0704m1 extends O1 {
    public final Y0 a;
    public final Y0 b;
    public final Y0 c;
    public final Y0 d;
    public final Y0 e;

    public C0704m1(C0722p1 c0722p1) {
        this.a = c0722p1.a(c0722p1.P3, c0722p1.O3, "TRANSLATION_Z");
        this.b = c0722p1.a(c0722p1.P3, c0722p1.J1, "EMPTY_STATE_SET");
        this.c = c0722p1.a(c0722p1.P3, c0722p1.J1, "ENABLED_STATE_SET");
        this.d = c0722p1.a(c0722p1.P3, c0722p1.J1, "PRESSED_ENABLED_STATE_SET");
        this.e = c0722p1.a(c0722p1.P3, c0722p1.J1, "SELECTED_STATE_SET");
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
