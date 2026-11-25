package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class KP extends AbstractC2094ge {
    static final /* synthetic */ boolean h = true;
    private final R4 f;
    private final Map g;

    KP(C0702m c0702m, Q4 q4, IdentityHashMap identityHashMap) {
        super(c0702m);
        this.f = q4;
        this.g = identityHashMap;
    }

    public static JP a(C0702m c0702m) {
        return new JP(c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    public final com.android.tools.r8.graph.proto.k d(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        if (this == abstractC1476Wj) {
            return com.android.tools.r8.graph.proto.k.h();
        }
        C0684j2 c0684j2F = f(c0684j2);
        com.android.tools.r8.graph.proto.k kVarD = k().d((AbstractC1476Wj) null, c0684j2F);
        return c0684j2F == c0684j2 ? kVarD : kVarD.a((com.android.tools.r8.graph.proto.k) this.g.getOrDefault(c0684j2, com.android.tools.r8.graph.proto.k.h()));
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    public final C0684j2 f(C0684j2 c0684j2) {
        Object objC = this.f.c(c0684j2);
        Object obj = c0684j2;
        if (objC != null) {
            obj = objC;
        }
        return (C0684j2) obj;
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge
    public final C0684j2 h(C0684j2 c0684j2) {
        return (C0684j2) this.f.getOrDefault(c0684j2, c0684j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    protected final KE a(KE ke, C0684j2 c0684j2) {
        C0684j2 c0684j3 = (C0684j2) ke.b();
        C0684j2 c0684j2H = h(c0684j3);
        if (c0684j3 == c0684j2H) {
            return ke;
        }
        if (h || !ke.c() || ke.b() == ke.a()) {
            return ((JE) KE.d().a(ke.e().a((com.android.tools.r8.graph.proto.k) this.g.getOrDefault(c0684j2H, com.android.tools.r8.graph.proto.k.h()))).b(c0684j2H)).a(ke.f()).a();
        }
        throw new AssertionError();
    }
}
