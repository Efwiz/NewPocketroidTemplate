package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class H2 extends AbstractC3424yG {
    static final /* synthetic */ boolean o = true;
    private final Map n;

    H2(C0702m c0702m, Q4 q4, Q4 q5, IdentityHashMap identityHashMap) {
        super(c0702m, q4, q5, AbstractC3424yG.l);
        this.n = identityHashMap;
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    protected final com.android.tools.r8.graph.proto.k a(C0684j2 c0684j2, com.android.tools.r8.graph.proto.k kVar) {
        C0684j2 c0684j2F = super.f(c0684j2);
        if (!this.n.containsKey(c0684j2)) {
            return kVar;
        }
        com.android.tools.r8.graph.proto.k kVarA = kVar.a(i(c0684j2));
        if (o || c0684j2F.I0().Y0() || !c0684j2.I0().Y0() || kVarA.f()) {
            return kVarA;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    protected final EnumC1953et b(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et) {
        return (this.n.containsKey(c0684j2) && i(c0684j2).a().f()) ? EnumC1953et.e : enumC1953et;
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    public final com.android.tools.r8.graph.Y0 d(com.android.tools.r8.graph.Y0 y0) {
        return super.d(y0);
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.DH
    public final C0684j2 f(C0684j2 c0684j2) {
        return super.f(c0684j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    public final C0684j2 h(C0684j2 c0684j2) {
        return super.h(c0684j2);
    }

    public final com.android.tools.r8.graph.proto.k i(C0684j2 c0684j2) {
        if (o || this.n.containsKey(c0684j2)) {
            return (com.android.tools.r8.graph.proto.k) this.n.getOrDefault(c0684j2, com.android.tools.r8.graph.proto.k.h());
        }
        throw new AssertionError();
    }

    public final boolean j(C0684j2 c0684j2) {
        return this.n.containsKey(c0684j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    protected final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.DH
    protected final C1237Oh b(C1237Oh c1237Oh) {
        C1237Oh c1237OhB = super.b(c1237Oh);
        return ((com.android.tools.r8.graph.Y0) c1237OhB.b()).getType() != ((com.android.tools.r8.graph.Y0) c1237Oh.b()).getType() ? ((C1210Nh) ((C1210Nh) C1237Oh.d().a((com.android.tools.r8.graph.Y0) c1237OhB.a())).b((com.android.tools.r8.graph.Y0) c1237OhB.b())).a(c1237OhB.e()).b(((com.android.tools.r8.graph.Y0) c1237OhB.b()).getType()).a() : c1237OhB;
    }
}
