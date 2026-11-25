package com.android.tools.r8.internal;

import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class JL extends LL {
    static final /* synthetic */ boolean i = true;
    private final C2424kx d;
    private final C0846Aw e;
    private final KL f;
    private final List g;
    private final Map h;

    public JL(C3395xv c3395xv, C2574mx c2574mx, C1651ax c1651ax, Map map, List list, KL kl) {
        super(c3395xv, c1651ax);
        if (!i && kl.equals(KL.b)) {
            throw new AssertionError();
        }
        this.d = c2574mx.b;
        this.e = c2574mx.a;
        this.f = kl;
        this.h = map;
        this.g = list;
    }

    @Override // com.android.tools.r8.internal.LL
    final String b() {
        return this.f.a;
    }

    @Override // com.android.tools.r8.internal.LL
    final List c() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.LL
    final void c(StringBuilder sb) {
        AbstractC2925rU.a(sb, this.e, LL.a(this.d));
        if (this.g.isEmpty()) {
            sb.append(" { void finalize(); }");
        }
    }

    @Override // com.android.tools.r8.internal.LL
    final void b(StringBuilder sb, String str) {
        AbstractC2925rU.a((AbstractC1171Lw) this.h.get(str), C2781pU.a(sb));
    }
}
