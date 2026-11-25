package com.android.tools.r8.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class IL extends LL {
    static final /* synthetic */ boolean m = true;
    private final C2424kx d;
    private final C0846Aw e;
    private final Map f;
    private final List g;
    private final List h;
    private final KL i;
    private int j;
    private String k;
    private HashMap l;

    public IL(List list, C3395xv c3395xv, C2574mx c2574mx, C1651ax c1651ax, Map map, List list2, KL kl) {
        super(c3395xv, c1651ax);
        this.j = 1;
        this.k = null;
        this.l = new HashMap();
        this.d = c2574mx.b;
        this.e = c2574mx.a;
        this.f = map;
        this.g = list;
        this.h = list2;
        this.i = kl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int e() {
        int i = this.j;
        this.j = i + 1;
        return i;
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
    final List c() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.LL
    final boolean d() {
        return !(this.g.isEmpty() && this.i == KL.b);
    }

    @Override // com.android.tools.r8.internal.LL
    final void a(final StringBuilder sb) {
        AbstractC2925rU.a(sb, this.e, new BiConsumer() { // from class: com.android.tools.r8.internal.IL$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(sb, (StringBuilder) obj, (AbstractC2496lv) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.LL
    final void c(final StringBuilder sb) {
        AbstractC2925rU.a(sb, this.e, new BiConsumer() { // from class: com.android.tools.r8.internal.IL$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b(sb, (StringBuilder) obj, (AbstractC2496lv) obj2);
            }
        });
        if (this.h.isEmpty()) {
            sb.append(" { void finalize(); }");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(StringBuilder sb, StringBuilder sb2, AbstractC2496lv abstractC2496lv) {
        C2705oU c2705oUA = C2781pU.a(sb, new IL$$ExternalSyntheticLambda1(this));
        AbstractC2925rU.a(this.d, c2705oUA);
        this.k = c2705oUA.d.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(StringBuilder sb, StringBuilder sb2, AbstractC2496lv abstractC2496lv) {
        boolean z = m;
        if (!z && !abstractC2496lv.d() && !abstractC2496lv.b().equals(this.d)) {
            throw new AssertionError();
        }
        if (d()) {
            sb2.append(this.k);
        } else {
            if (!z && this.k != null) {
                throw new AssertionError();
            }
            AbstractC2925rU.a(this.d, C2781pU.a(sb));
        }
    }

    @Override // com.android.tools.r8.internal.LL
    final void a(StringBuilder sb, String str) {
        AbstractC1171Lw abstractC1171Lw = (AbstractC1171Lw) this.f.get(str);
        C2705oU c2705oUA = C2781pU.a(sb, new IL$$ExternalSyntheticLambda1(this));
        AbstractC2925rU.a(abstractC1171Lw, c2705oUA);
        this.l.put(str, c2705oUA.d.toString());
    }

    @Override // com.android.tools.r8.internal.LL
    final void b(StringBuilder sb, String str) {
        String str2;
        if (d() && (str2 = (String) this.l.get(str)) != null) {
            sb.append(str2);
        } else {
            AbstractC2925rU.a((AbstractC1171Lw) this.f.get(str), C2781pU.a(sb));
        }
    }
}
