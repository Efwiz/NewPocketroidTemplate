package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class K5 extends AbstractC2094ge {
    private final L4 f;

    public K5(C0702m c0702m, M4 m4) {
        super(c0702m.a(), c0702m.E());
        this.f = m4;
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: b */
    public final C0684j2 c(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        return h(k().c(abstractC1476Wj, c0684j2));
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    public final C0684j2 f(C0684j2 c0684j2) {
        Set setA = this.f.a(c0684j2);
        return setA.isEmpty() ? c0684j2 : (C0684j2) setA.iterator().next();
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge
    public final C0684j2 h(C0684j2 c0684j2) {
        return (C0684j2) this.f.getOrDefault(c0684j2, c0684j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean f() {
        return k().f();
    }
}
