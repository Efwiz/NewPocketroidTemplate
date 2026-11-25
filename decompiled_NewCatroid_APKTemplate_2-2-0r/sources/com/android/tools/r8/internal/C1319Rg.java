package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1319Rg extends AbstractC3424yG {
    static final /* synthetic */ boolean q = true;
    private final C1 n;
    private final Map o;
    private final C3226vg p;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1319Rg(C0702m c0702m, Q4 q4, final P4 p4, IdentityHashMap identityHashMap, AbstractC2178hm abstractC2178hm) {
        super(c0702m, q4, new Function() { // from class: com.android.tools.r8.internal.Rg$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (C0684j2) p4.b((C0684j2) obj);
            }
        }, identityHashMap, p4);
        Objects.requireNonNull(p4);
        if (!q && c0702m.e0().c()) {
            throw new AssertionError();
        }
        this.n = c0702m.b();
        this.o = abstractC2178hm;
        this.p = c0702m.e0();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final C1319Rg a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    protected final EnumC1953et b(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et) {
        if (!this.h.containsKey(c0684j3.B0())) {
            return enumC1953et;
        }
        if (q || c0684j2 != c0684j3) {
            return EnumC1953et.e;
        }
        throw new AssertionError();
    }

    /* JADX WARN: Code duplicated, block: B:11:0x003c  */
    @Override // com.android.tools.r8.internal.AbstractC3424yG
    protected final com.android.tools.r8.graph.proto.k a(C0684j2 c0684j2, com.android.tools.r8.graph.proto.k kVar) {
        AbstractC3292wV abstractC3292wVA;
        if (kVar.f()) {
            com.android.tools.r8.graph.proto.l lVarC = kVar.c();
            if (lVarC.l()) {
                AbstractC3292wV abstractC3292wVI = lVarC.i();
                if (abstractC3292wVI.B()) {
                    AbstractC2706oV abstractC2706oVP = abstractC3292wVI.p();
                    if (this.p.b(abstractC2706oVP.F())) {
                        abstractC3292wVA = this.n.a(this.p.a(abstractC2706oVP.F()));
                    } else {
                        abstractC3292wVA = abstractC3292wVI;
                    }
                } else {
                    abstractC3292wVA = abstractC3292wVI;
                }
                if (abstractC3292wVA != abstractC3292wVI) {
                    kVar = kVar.a(com.android.tools.r8.graph.proto.l.e().a(lVarC.f()).c(lVarC.h()).b(lVarC.g()).a(abstractC3292wVA).a());
                }
            }
        }
        return kVar.a((com.android.tools.r8.graph.proto.k) this.o.getOrDefault(c0684j2, com.android.tools.r8.graph.proto.k.h()));
    }

    public static C1293Qg a(C0702m c0702m) {
        return new C1293Qg(c0702m);
    }
}
