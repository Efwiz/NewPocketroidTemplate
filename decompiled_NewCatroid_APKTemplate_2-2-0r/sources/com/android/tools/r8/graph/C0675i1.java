package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.i1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0675i1 extends O1 {
    public final Y0 a;
    public final Y0 b;

    public C0675i1(C0722p1 c0722p1) {
        this.a = c0722p1.a(c0722p1.K3, c0722p1.L3, "CREATOR");
        C0767v2 c0767v2 = c0722p1.K3;
        this.b = c0722p1.a(c0767v2, c0767v2, "EMPTY");
    }

    @Override // com.android.tools.r8.graph.O1
    public final void a(Consumer consumer) {
        consumer.accept(this.a);
        consumer.accept(this.b);
    }
}
