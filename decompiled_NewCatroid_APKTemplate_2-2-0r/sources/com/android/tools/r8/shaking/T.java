package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class T extends Y {
    private final C0767v2 a;

    T(C0767v2 c0767v2) {
        this.a = c0767v2;
    }

    @Override // com.android.tools.r8.shaking.Y
    public final T a() {
        return this;
    }

    @Override // com.android.tools.r8.shaking.Y
    public final boolean b() {
        return true;
    }

    public final C0767v2 d() {
        return this.a;
    }

    @Override // com.android.tools.r8.shaking.Y
    public final com.android.tools.r8.graph.O0 a(C0680j c0680j) {
        return c0680j.c(this.a);
    }
}
