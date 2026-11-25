package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.S10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class y0 {
    static final /* synthetic */ boolean c = true;
    private final Y0 a;
    private final S10 b;

    private y0(Y0 y0, S10 s10) {
        if (!c && s10 != s10.m()) {
            throw new AssertionError();
        }
        this.a = y0;
        this.b = s10;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof y0)) {
            return false;
        }
        y0 y0Var = (y0) obj;
        return y0Var.b == this.b && y0Var.a == this.a;
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 7);
    }
}
