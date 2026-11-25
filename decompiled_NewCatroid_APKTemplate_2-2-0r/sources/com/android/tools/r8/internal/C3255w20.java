package com.android.tools.r8.internal;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.w20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3255w20 {
    static final /* synthetic */ boolean d = true;
    private final C3255w20 a;
    private final com.android.tools.r8.graph.o5 b;
    private final CN c = CN.b();

    C3255w20(com.android.tools.r8.graph.o5 o5Var, C3255w20 c3255w20) {
        this.a = c3255w20;
        this.b = o5Var;
    }

    final void a(com.android.tools.r8.graph.o5 o5Var) {
        boolean z = d;
        if (!z && o5Var == this.b) {
            throw new AssertionError();
        }
        if (!z && !o5Var.z().equals(this.b.z())) {
            throw new AssertionError();
        }
        this.c.add(o5Var);
        C3255w20 c3255w20 = this.a;
        if (c3255w20 != null) {
            c3255w20.a(o5Var);
        }
    }

    final void a(Consumer consumer) {
        consumer.accept(this.b);
        this.c.forEach(consumer);
    }
}
