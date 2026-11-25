package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.o5;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.u0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3805u0 extends AbstractC3709e0 {
    protected final com.android.tools.r8.graph.Y0 a;
    protected final o5 b;
    protected final I c;

    AbstractC3805u0(com.android.tools.r8.graph.Y0 y0, o5 o5Var, I i) {
        this.a = y0;
        this.b = o5Var;
        this.c = i;
    }

    protected final boolean a(AbstractC3805u0 abstractC3805u0) {
        return this.a == abstractC3805u0.a && this.b.b(abstractC3805u0.b) && this.c.equals(abstractC3805u0.c);
    }

    public int hashCode() {
        return Objects.hash(this.a, this.b.getReference(), this.c);
    }
}
