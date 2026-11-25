package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class M {
    private final C0684j2 a;
    private final boolean b;

    private M(C0684j2 c0684j2, boolean z) {
        this.a = c0684j2;
        this.b = z;
    }

    public final boolean equals(Object obj) {
        if (obj == null || M.class != obj.getClass()) {
            return false;
        }
        M m = (M) obj;
        return this.a == m.a && this.b == m.b;
    }

    public final int hashCode() {
        return Objects.hash(this.a, Boolean.valueOf(this.b));
    }
}
