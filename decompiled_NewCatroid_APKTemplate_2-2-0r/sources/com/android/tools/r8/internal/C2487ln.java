package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.IdentityHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ln, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2487ln extends AbstractC2797pg {
    private final C0702m a;
    private final IdentityHashMap b = new IdentityHashMap();

    public C2487ln(C0702m c0702m) {
        this.a = c0702m;
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        C0767v2 c0767v2 = c0729q2.d;
        C0767v2 c0767v3 = this.a.a().Z1;
        if (o5Var == null) {
            this.b.put(c0767v2, c0767v3);
            return;
        }
        C0680j c0680j = (C0680j) this.a.f();
        C0767v2 c0767v2T = o5Var.t();
        this.b.put(c0767v2, C1229Oa.a(c0680j, c0767v2T, (C0767v2) this.b.getOrDefault(c0767v2, c0767v2T)));
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.shaking.L l) {
        C0702m c0702m = this.a;
        c0702m.a(new C2411kn(c0702m, this.b));
    }
}
