package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.o5;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.z, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3835z {
    private final o5 a;
    private final o5 b;
    private final Consumer c;

    C3835z(o5 o5Var, o5 o5Var2, Consumer consumer) {
        this.a = o5Var;
        this.b = o5Var2;
        this.c = consumer;
    }

    public final Consumer a() {
        return this.c;
    }

    public final o5 b() {
        return this.a;
    }

    public final o5 c() {
        return this.b;
    }
}
