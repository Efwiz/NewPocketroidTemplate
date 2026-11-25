package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Oh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1237Oh extends AbstractC1597aE {
    private final C0767v2 c;
    private final C0767v2 d;

    private C1237Oh(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1, C0767v2 c0767v2, C0767v2 c0767v3) {
        super(y0, y1);
        this.c = c0767v2;
        this.d = c0767v3;
    }

    public static C1210Nh d() {
        return new C1210Nh();
    }

    public final C0767v2 b(Function function) {
        if (g()) {
            return (C0767v2) function.apply(this.c);
        }
        return null;
    }

    public final C0767v2 c(Function function) {
        if (h()) {
            return (C0767v2) function.apply(this.d);
        }
        return null;
    }

    public final C0767v2 e() {
        return this.c;
    }

    public final C0767v2 f() {
        return this.d;
    }

    public final boolean g() {
        return this.c != null;
    }

    public final boolean h() {
        return this.d != null;
    }
}
