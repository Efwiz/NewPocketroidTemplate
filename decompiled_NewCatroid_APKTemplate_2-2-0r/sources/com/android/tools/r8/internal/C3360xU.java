package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3360xU {
    private final com.android.tools.r8.graph.Q2 a;
    private final C0767v2 b;

    private C3360xU(com.android.tools.r8.graph.Q2 q2, C0767v2 c0767v2) {
        this.a = (com.android.tools.r8.graph.Q2) ((com.android.tools.r8.graph.Q2) com.android.tools.r8.graph.Q2.k(q2.s()).G()).M();
        this.b = c0767v2;
    }

    public static C3360xU a(com.android.tools.r8.graph.T0 t0) {
        return new C3360xU(t0.getAccessFlags(), t0.getType());
    }

    public final boolean equals(Object obj) {
        if (obj == null || C3360xU.class != obj.getClass()) {
            return false;
        }
        C3360xU c3360xU = (C3360xU) obj;
        return this.a.s() == c3360xU.a.s() && this.b == c3360xU.b;
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    public static C3360xU a(com.android.tools.r8.graph.T0 t0, C0722p1 c0722p1) {
        return new C3360xU(t0.getAccessFlags(), t0.getType().X0() ? c0722p1.Z1 : t0.getType());
    }

    public final C3360xU a(C0722p1 c0722p1) {
        return new C3360xU(this.a, this.b.X0() ? c0722p1.Z1 : this.b);
    }
}
