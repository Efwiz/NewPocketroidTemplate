package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1097Jd extends AbstractC1125Kd {
    private final Map a;

    public C1097Jd(IdentityHashMap identityHashMap) {
        this.a = identityHashMap;
    }

    @Override // com.android.tools.r8.internal.AbstractC1125Kd
    public final C1097Jd a() {
        return this;
    }

    public final AbstractC3327x1 a(C0702m c0702m) {
        AbstractC3327x1 abstractC3327x1B = null;
        for (AbstractC3327x1 abstractC3327x1 : this.a.values()) {
            abstractC3327x1B = abstractC3327x1B != null ? abstractC3327x1B.b(c0702m, abstractC3327x1) : abstractC3327x1;
        }
        return abstractC3327x1B;
    }
}
