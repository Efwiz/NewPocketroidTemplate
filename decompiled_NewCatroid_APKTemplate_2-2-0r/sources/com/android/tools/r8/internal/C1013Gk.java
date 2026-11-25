package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1013Gk extends AbstractC3424yG {
    private final Map n;
    private final C1105Jk o;

    private C1013Gk(C0702m c0702m, C1105Jk c1105Jk, IdentityHashMap identityHashMap, M4 m4, Map map, M4 m5) {
        super(c0702m, m4, map, c1105Jk.b(), m5);
        this.n = identityHashMap;
        this.o = c1105Jk;
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.DH
    public final KE a(KE ke, C0684j2 c0684j2) {
        List list = (List) this.n.get(ke.b());
        KE keA = super.a(ke, c0684j2);
        return list == null ? keA : ((JE) KE.d().b((C0684j2) keA.b())).a(keA.e().a(list)).a(keA.f()).a();
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.DH
    protected final C1237Oh b(C1237Oh c1237Oh) {
        C0767v2 c0767v2A;
        C1237Oh c1237OhB = super.b(c1237Oh);
        if (c1237OhB.b() == c1237Oh.b()) {
            return c1237OhB;
        }
        C1210Nh c1210Nh = (C1210Nh) ((C1210Nh) C1237Oh.d().b((com.android.tools.r8.graph.Y0) c1237OhB.b())).a((com.android.tools.r8.graph.Y0) c1237OhB.a());
        if (((com.android.tools.r8.graph.Y0) c1237OhB.b()).getType() != ((com.android.tools.r8.graph.Y0) c1237Oh.b()).getType()) {
            c0767v2A = a(AbstractC1476Wj.e(), ((com.android.tools.r8.graph.Y0) c1237Oh.b()).getType());
        } else {
            c0767v2A = null;
        }
        return c1210Nh.a(c0767v2A).b(c1237Oh.c(new Function() { // from class: com.android.tools.r8.internal.Gk$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.e((C0767v2) obj);
            }
        })).a();
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    protected final Iterable g(C0767v2 c0767v2) {
        return AbstractC0984Fi.a(com.android.tools.r8.utils.I1.b(c0767v2), this.o.c(c0767v2));
    }

    final C0684j2 i(C0684j2 c0684j2) {
        C0684j2 c0684j3 = (C0684j2) this.g.apply(c0684j2);
        return c0684j3 != null ? c0684j3 : c0684j2;
    }
}
