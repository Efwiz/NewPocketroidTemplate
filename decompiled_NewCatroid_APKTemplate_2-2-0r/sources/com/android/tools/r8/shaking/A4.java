package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC3424yG;
import com.android.tools.r8.internal.C2750p20;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.internal.JE;
import com.android.tools.r8.internal.KE;
import com.android.tools.r8.internal.M4;
import com.android.tools.r8.internal.Q4;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class A4 extends AbstractC3424yG {
    static final /* synthetic */ boolean t = true;
    private final C0702m n;
    private C2750p20 o;
    private final Map p;
    private Set q;
    private final Map r;
    private final Map s;

    private A4(C0702m c0702m, C2750p20 c2750p20, Q4 q4, IdentityHashMap identityHashMap, AbstractC1048Hm abstractC1048Hm, IdentityHashMap identityHashMap2, M4 m4, IdentityHashMap identityHashMap3, IdentityHashMap identityHashMap4) {
        super(c0702m, q4, identityHashMap, c2750p20.b(), m4);
        this.n = c0702m;
        this.o = c2750p20;
        this.p = identityHashMap2;
        this.q = abstractC1048Hm;
        this.r = identityHashMap3;
        this.s = identityHashMap4;
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.DH
    public final KE a(KE ke, C0684j2 c0684j2) {
        Map map;
        z4 z4Var;
        boolean z = t;
        if (!z && c0684j2 == null) {
            e((C0684j2) ke.b());
        }
        if (!z && c0684j2 != null && ke.f() == null) {
            throw new AssertionError();
        }
        if (ke.f() == EnumC1953et.f && !this.q.contains(c0684j2) && (map = (Map) this.p.get(c0684j2.B0())) != null && (z4Var = (z4) map.get(ke.b())) != null) {
            return z4Var.a(ke.e());
        }
        C0684j2 c0684j3 = (C0684j2) this.g.apply((C0684j2) ke.b());
        return c0684j3 == null ? ke : ((JE) KE.d().b(c0684j3)).a(a(c0684j3, ke.e())).a(AbstractC3424yG.a(this.n, c0684j3, (C0684j2) ke.b(), ke.f())).a();
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    protected final EnumC1953et b(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et) {
        return AbstractC3424yG.a(this.n, c0684j2, c0684j3, enumC1953et);
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean e(C0684j2 c0684j2) {
        boolean z = t;
        if (!z && !k().e(c0684j2)) {
            throw new AssertionError();
        }
        final C0684j2 c0684j2D = k().d(c0684j2);
        if (z || this.p.values().stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.shaking.A4$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return A4.a(c0684j2D, (Map) obj);
            }
        })) {
            return true;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.DH
    public final C0684j2 f(C0684j2 c0684j2) {
        return super.f((C0684j2) this.r.getOrDefault(c0684j2, c0684j2));
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    protected final Iterable g(C0767v2 c0767v2) {
        Set setB = this.o.b(c0767v2);
        Iterable iterableB = com.android.tools.r8.utils.I1.b(c0767v2);
        return setB == null ? iterableB : AbstractC0984Fi.a(iterableB, setB);
    }

    @Override // com.android.tools.r8.internal.DH
    public final C0684j2 g(C0684j2 c0684j2) {
        Object objC = this.i.c(c0684j2);
        Object obj = c0684j2;
        if (objC != null) {
            obj = objC;
        }
        return super.f((C0684j2) obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean g() {
        return this.p.isEmpty() && k().g();
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG
    protected final com.android.tools.r8.graph.proto.k a(C0684j2 c0684j2, com.android.tools.r8.graph.proto.k kVar) {
        return kVar.a((com.android.tools.r8.graph.proto.k) this.s.getOrDefault(c0684j2, com.android.tools.r8.graph.proto.k.h()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0684j2 c0684j2, Map map) {
        return map.containsKey(c0684j2);
    }
}
