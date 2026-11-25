package com.android.tools.r8.internal;

import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class HL extends LL {
    final C0846Aw d;
    final C0846Aw e;
    final Map f;
    final List g;
    private final List h;
    private final KL i;

    public HL(C3395xv c3395xv, C1651ax c1651ax, C2574mx c2574mx, C2574mx c2574mx2, Map map, List list, List list2, KL kl) {
        super(c3395xv, c1651ax);
        this.d = c2574mx.a;
        this.e = c2574mx2.a;
        this.f = map;
        this.g = list;
        this.h = list2;
        this.i = kl;
    }

    @Override // com.android.tools.r8.internal.LL
    final List a() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.LL
    final String b() {
        return this.i.a;
    }

    @Override // com.android.tools.r8.internal.LL
    final void c(StringBuilder sb) {
        AbstractC2925rU.a(sb, this.e, new HL$$ExternalSyntheticLambda0(this));
        if (this.h.isEmpty()) {
            sb.append(" { void finalize(); }");
        }
    }

    @Override // com.android.tools.r8.internal.LL
    final void a(StringBuilder sb) {
        AbstractC2925rU.a(sb, this.d, new HL$$ExternalSyntheticLambda0(this));
    }

    @Override // com.android.tools.r8.internal.LL
    final void a(StringBuilder sb, String str) {
        AbstractC2925rU.a((AbstractC1171Lw) this.f.get(str), C2781pU.a(sb));
    }

    @Override // com.android.tools.r8.internal.LL
    final void b(StringBuilder sb, String str) {
        AbstractC2925rU.a((AbstractC1171Lw) this.f.get(str), C2781pU.a(sb));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(StringBuilder sb, AbstractC2496lv abstractC2496lv) {
        AbstractC2925rU.a(abstractC2496lv.b(), C2781pU.a(sb));
    }

    @Override // com.android.tools.r8.internal.LL
    final List c() {
        return this.h;
    }
}
