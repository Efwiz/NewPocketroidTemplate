package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.n5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC3082tl;
import com.android.tools.r8.internal.C2133h6;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3352xN;
import com.android.tools.r8.internal.C3385xl;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.C3431yN;
import com.android.tools.r8.internal.C3455yl;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.internal.CN;
import com.android.tools.r8.internal.CN$$ExternalSyntheticLambda2;
import com.android.tools.r8.utils.C3879c3;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Q extends P {
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final L b;
    private final L.a c;
    private final com.android.tools.r8.utils.A1 d;
    private final S e;
    private final C3352xN f = C3352xN.c();
    private final C3431yN g = C3431yN.a();

    /* JADX INFO: renamed from: $r8$lambda$n8g1vpkWBRrj-fdp-to3dCowVT4, reason: not valid java name */
    public static /* synthetic */ LinkedHashSet m2190$r8$lambda$n8g1vpkWBRrjfdpto3dCowVT4() {
        return new LinkedHashSet();
    }

    Q(C0702m c0702m, L l, L.a aVar) {
        this.a = c0702m;
        this.b = l;
        this.c = aVar;
        this.d = c0702m.Y();
        this.e = new S(c0702m);
    }

    private void b(m5 m5Var) {
        Set set = (Set) this.f.c(m5Var);
        if (set != null) {
            this.b.m().a(set);
        }
        this.g.a(m5Var);
    }

    /* JADX WARN: Code duplicated, block: B:66:0x0108  */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x010c, code lost:
    
        if (r2.a(r0) != false) goto L68;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean c(com.android.tools.r8.graph.m5 r7) {
        /*
            Method dump skipped, instruction units count: 281
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.shaking.Q.c(com.android.tools.r8.graph.m5):boolean");
    }

    @Override // com.android.tools.r8.shaking.P
    public final boolean a(com.android.tools.r8.graph.Y0 y0, AbstractC0677i3 abstractC0677i3, o5 o5Var, H h2, I i) {
        Object c3811v0;
        m5 m5VarU = abstractC0677i3.u();
        if (m5VarU == null) {
            return false;
        }
        boolean zO = m5VarU.getAccessFlags().o();
        H h3 = H.c;
        if (zO != (h2 == h3 || h2 == H.d)) {
            b(m5VarU);
            return false;
        }
        if (o5Var.a().f(this.a)) {
            b(m5VarU);
            return false;
        }
        if (h2 == H.a || h2 == h3) {
            C3749l c3749lT = this.a.t();
            c3749lT.getClass();
            if (!c3749lT.a(m5VarU.getReference()).d().isUnknown()) {
                b(m5VarU);
                return false;
            }
            if (m5VarU.getAccessFlags().o() && m5VarU.e().Z0()) {
                b(m5VarU);
                return false;
            }
        }
        if (!c(m5VarU)) {
            b(m5VarU);
            return false;
        }
        I iD = i.d();
        int iOrdinal = h2.ordinal();
        if (iOrdinal == 0) {
            c3811v0 = new C3811v0(y0, o5Var, iD);
        } else if (iOrdinal == 1) {
            c3811v0 = new C3817w0(y0, o5Var, iD);
        } else if (iOrdinal == 2) {
            c3811v0 = new C0(y0, o5Var, iD);
        } else {
            if (iOrdinal != 3) {
                throw new C3405y10();
            }
            c3811v0 = new D0(y0, o5Var, iD);
        }
        ((Set) this.f.a((n5) m5VarU, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.shaking.Q$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return Q.m2190$r8$lambda$n8g1vpkWBRrjfdpto3dCowVT4();
            }
        }))).add(c3811v0);
        if (h2 == h3 || h2 == H.d) {
            this.b.m().c(m5VarU.a(), this.b.g().a(m5VarU.a(), o5Var));
            this.b.m().a(m5VarU.a());
        }
        return true;
    }

    @Override // com.android.tools.r8.shaking.P
    public final void a(m5 m5Var) {
        b(m5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3698c1 c3698c1) {
        return (c3698c1.l() && c3698c1.m()) ? false : true;
    }

    @Override // com.android.tools.r8.shaking.P
    public final boolean a(final F0 f0) {
        return this.f.a(new BiPredicate() { // from class: com.android.tools.r8.shaking.Q$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return this.f$0.a(f0, (m5) obj, (Set) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(F0 f0, m5 m5Var, Set set) {
        if (c(m5Var)) {
            return false;
        }
        f0.a(set);
        return true;
    }

    @Override // com.android.tools.r8.shaking.P
    public final void a(ExecutorService executorService) {
        final com.android.tools.r8.graph.T2 t2F = this.b.f();
        final CN cnB = CN.b();
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.f.a(new BiConsumer() { // from class: com.android.tools.r8.shaking.Q$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Q.a(t2F, identityHashMap, cnB, (m5) obj, (Set) obj2);
            }
        });
        this.f.a();
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        C3879c3.a(cnB, new com.android.tools.r8.utils.U1() { // from class: com.android.tools.r8.shaking.Q$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.U1
            public final void accept(Object obj, int i) {
                this.f$0.a(concurrentHashMap, identityHashMap, (o5) obj, i);
            }
        }, executorService, C3879c3.a.b);
        concurrentHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.Q$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0729q2) obj, (CN) obj2);
            }
        });
        if (!h && !this.b.m().b.isEmpty()) {
            throw new AssertionError();
        }
        identityHashMap.values().forEach(new Consumer() { // from class: com.android.tools.r8.shaking.Q$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Q.a(t2F, (m5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.T2 t2, final Map map, CN cn, final m5 m5Var, Set set) {
        com.android.tools.r8.graph.U2 u2B = t2.b(m5Var.getReference());
        map.put(m5Var.getReference(), m5Var);
        Objects.requireNonNull(cn);
        u2B.a(new CN$$ExternalSyntheticLambda2(cn));
        u2B.b(new Consumer() { // from class: com.android.tools.r8.shaking.Q$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                map.put((com.android.tools.r8.graph.Y0) obj, m5Var);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2, o5 o5Var) {
        this.b.e(c0729q2.getType(), o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final C0729q2 c0729q2, CN cn) {
        cn.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.Q$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0729q2, (o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.T2 t2, m5 m5Var) {
        t2.d(m5Var.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, Map map2, o5 o5Var, int i) {
        CE.a aVar;
        AbstractC3082tl c3455yl;
        if (this.c.c()) {
            aVar = new CE.a(this.d).e();
        } else {
            aVar = new CE.a(this.d);
        }
        aVar.b();
        C0702m c0702m = this.a;
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        C2409kl c2409klA = w0E.q1() ? w0E.a1().a(o5Var, c0702m, o5Var.getOrigin(), aVar) : null;
        this.e.a(c2409klA, map, map2);
        this.e.a().e(c2409klA);
        this.e.b().a(c2409klA, com.android.tools.r8.utils.y3.b());
        if (aVar.a()) {
            c3455yl = new C3385xl(this.a, this.e.b());
        } else {
            c3455yl = new C3455yl(this.a, this.e.b());
        }
        o5Var.a(c3455yl.a(c2409klA, C2133h6.b(), com.android.tools.r8.utils.y3.b()), this.a);
    }
}
