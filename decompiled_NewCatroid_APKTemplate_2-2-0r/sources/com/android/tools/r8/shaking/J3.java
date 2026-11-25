package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1639am;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class J3 extends G3 {
    private static final G3 b = new J3(new O3.b("%"));
    private final O3 a;

    private J3(O3 o3) {
        super();
        this.a = o3;
    }

    @Override // com.android.tools.r8.shaking.G3
    protected final G3 a(C0722p1 c0722p1) {
        return new J3(this.a.f());
    }

    @Override // com.android.tools.r8.shaking.G3
    public final boolean b(C0767v2 c0767v2) {
        if (!c0767v2.W0()) {
            return false;
        }
        this.a.a(c0767v2.r0());
        return true;
    }

    @Override // com.android.tools.r8.shaking.G3
    protected final Iterable d() {
        return AbstractC1639am.c(this.a);
    }

    @Override // com.android.tools.r8.shaking.G3
    public final boolean equals(Object obj) {
        return obj instanceof J3;
    }

    @Override // com.android.tools.r8.shaking.G3
    public final int hashCode() {
        return J3.class.hashCode();
    }

    @Override // com.android.tools.r8.shaking.G3
    public final String toString() {
        return "%";
    }
}
