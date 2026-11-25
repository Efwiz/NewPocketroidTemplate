package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class QQ extends AbstractC2094ge {
    private final Set f;
    private final Map g;

    public QQ(C0702m c0702m, IdentityHashMap identityHashMap, Set set) {
        super(c0702m);
        this.f = set;
        this.g = identityHashMap;
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    protected final KE a(KE ke, C0684j2 c0684j2) {
        if (!this.g.containsKey(ke.b())) {
            return ke;
        }
        C0684j2 c0684j3 = (C0684j2) ke.b();
        do {
            c0684j3 = (C0684j2) this.g.get(c0684j3);
        } while (this.g.containsKey(c0684j3));
        boolean zContains = this.f.contains(c0684j3.B0());
        if (ke.f().e() && zContains) {
            return ke;
        }
        return ((JE) ((JE) KE.d().b(c0684j3)).a(c0684j3)).a(ke.e()).a((zContains && ke.f().f()) ? EnumC1953et.d : ke.f()).a();
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: b */
    public final C0684j2 c(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        if (this == abstractC1476Wj) {
            return c0684j2;
        }
        C0684j2 c0684j2C = k().c(abstractC1476Wj, c0684j2);
        return (C0684j2) this.g.getOrDefault(c0684j2C, c0684j2C);
    }
}
