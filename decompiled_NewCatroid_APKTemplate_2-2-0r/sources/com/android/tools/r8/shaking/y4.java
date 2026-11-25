package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.C0862Bm;
import com.android.tools.r8.internal.C2750p20;
import com.android.tools.r8.internal.InterfaceC3205vM;
import com.android.tools.r8.internal.KE;
import com.android.tools.r8.internal.M4;
import com.android.tools.r8.internal.Q4;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class y4 {
    static final /* synthetic */ boolean j = true;
    private final C0722p1 a;
    protected final Q4 b = new Q4();
    protected final IdentityHashMap c = new IdentityHashMap();
    private final C0862Bm d = AbstractC1048Hm.g();
    private final IdentityHashMap e = new IdentityHashMap();
    private final M4 f = M4.d();
    private final IdentityHashMap g = new IdentityHashMap();
    private final IdentityHashMap h = new IdentityHashMap();
    private final IdentityHashMap i = new IdentityHashMap();

    y4(C0722p1 c0722p1) {
        this.a = c0722p1;
    }

    static y4 a(final y4 y4Var, final C2750p20 c2750p20) {
        C0767v2 c0767v2A;
        final y4 y4Var2 = new y4(y4Var.a);
        y4Var.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.y4$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                y4.a(this.f$0, y4Var, c2750p20, (com.android.tools.r8.graph.Y0) obj, (com.android.tools.r8.graph.Y0) obj2);
            }
        });
        for (Map.Entry entry : y4Var.c.entrySet()) {
            y4Var2.c.put((C0684j2) entry.getKey(), y4Var.a((C0684j2) entry.getValue(), c2750p20));
        }
        AbstractC2963s10 it = y4Var.d.a().iterator();
        while (it.hasNext()) {
            y4Var2.d.a(y4Var.a((C0684j2) it.next(), c2750p20));
        }
        for (Map.Entry entry2 : y4Var.e.entrySet()) {
            C0767v2 c0767v2 = (C0767v2) entry2.getKey();
            if (!j) {
                if (c0767v2.M0()) {
                    C0767v2 c0767v2B = c0767v2.b(y4Var.a);
                    C0767v2 c0767v2A2 = c2750p20.a(c0767v2B, c0767v2B);
                    c0767v2A = c0767v2A2 != c0767v2B ? c0767v2.a(y4Var.a, c0767v2A2) : c0767v2;
                } else {
                    c0767v2A = c2750p20.a(c0767v2, c0767v2);
                }
                if (c0767v2 != c0767v2A) {
                    throw new AssertionError();
                }
            }
            for (Map.Entry entry3 : ((Map) entry2.getValue()).entrySet()) {
                C0684j2 c0684j2 = (C0684j2) entry3.getKey();
                final KE keA = ((z4) entry3.getValue()).a(com.android.tools.r8.graph.proto.k.h());
                final C0684j2 c0684j2A = y4Var.a((C0684j2) keA.b(), c2750p20);
                y4Var2.a(c0684j2, new z4() { // from class: com.android.tools.r8.shaking.y4$$ExternalSyntheticLambda2
                    @Override // com.android.tools.r8.shaking.z4
                    public final KE a(com.android.tools.r8.graph.proto.k kVar) {
                        return y4.a(c0684j2A, keA, kVar);
                    }
                }, c0767v2);
            }
        }
        y4Var.f.a(new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.shaking.y4$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                y4.a(this.f$0, c2750p20, y4Var2, (Set) obj, (C0684j2) obj2, (C0684j2) obj3);
            }
        });
        for (Map.Entry entry4 : y4Var.g.entrySet()) {
            y4Var2.g.put(y4Var.a((C0684j2) entry4.getKey(), c2750p20), (C0684j2) entry4.getValue());
        }
        y4Var.h.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.y4$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                y4.a(this.f$0, y4Var, c2750p20, (C0684j2) obj, (com.android.tools.r8.graph.proto.k) obj2);
            }
        });
        return y4Var2;
    }

    public final void b(C0684j2 c0684j2) {
        this.d.a(c0684j2);
    }

    public final void c(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.g.put(c0684j3, c0684j2);
    }

    public final void d(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.f.a(c0684j2, c0684j3);
        this.f.a(c0684j3, c0684j3);
        this.f.b(c0684j3, c0684j3);
    }

    public final y4 b(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.c.put(c0684j2, c0684j3);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(y4 y4Var, y4 y4Var2, C2750p20 c2750p20, com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        C0767v2 c0767v2A;
        y4Var2.getClass();
        if (!j && y1.e.M0()) {
            throw new AssertionError();
        }
        C0767v2 c0767v2 = y1.e;
        C0767v2 c0767v2A2 = c2750p20.a(c0767v2, c0767v2);
        C0767v2 c0767v3 = y1.h;
        if (c0767v3.M0()) {
            C0767v2 c0767v2B = c0767v3.b(y4Var2.a);
            C0767v2 c0767v2A3 = c2750p20.a(c0767v2B, c0767v2B);
            c0767v2A = c0767v2A3 != c0767v2B ? c0767v3.a(y4Var2.a, c0767v2A3) : c0767v3;
        } else {
            c0767v2A = c2750p20.a(c0767v3, c0767v3);
        }
        if (c0767v2 != c0767v2A2 || c0767v3 != c0767v2A) {
            y1 = y4Var2.a.a(c0767v2A2, c0767v2A, y1.f);
        }
        y4Var.b.put(y0, y1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ KE a(C0684j2 c0684j2, KE ke, com.android.tools.r8.graph.proto.k kVar) {
        return new KE(c0684j2, null, ke.f(), kVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(y4 y4Var, C2750p20 c2750p20, y4 y4Var2, Set set, C0684j2 c0684j2, C0684j2 c0684j3) {
        C0684j2 c0684j2A = y4Var.a(c0684j2, c2750p20);
        y4Var2.f.a((Iterable) set, (Object) c0684j2A);
        if (set.size() > 1) {
            y4Var2.f.b(c0684j2A, c0684j3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(y4 y4Var, y4 y4Var2, C2750p20 c2750p20, C0684j2 c0684j2, com.android.tools.r8.graph.proto.k kVar) {
        y4Var.h.put(y4Var2.a(c0684j2, c2750p20), kVar);
    }

    public final A4 a(C0702m c0702m, C2750p20 c2750p20) {
        if (c2750p20.c()) {
            return null;
        }
        return new A4(c0702m, c2750p20, this.b, this.c, this.d.a(), this.e, this.f, this.g, this.h);
    }

    private C0684j2 a(C0684j2 c0684j2, final C2750p20 c2750p20) {
        if (!j && c0684j2.e.M0()) {
            throw new AssertionError();
        }
        C0767v2 c0767v2 = c0684j2.e;
        C0767v2 c0767v2A = c2750p20.a(c0767v2, c0767v2);
        C0737r2 c0737r2 = c0684j2.h;
        C0737r2 c0737r2A = this.a.a(c0737r2, new Function() { // from class: com.android.tools.r8.shaking.y4$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c2750p20, (C0767v2) obj);
            }
        }, this.i);
        return (c0767v2 == c0767v2A && c0737r2 == c0737r2A) ? c0684j2 : this.a.a(c0767v2A, c0737r2A, c0684j2.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0767v2 a(C2750p20 c2750p20, C0767v2 c0767v2) {
        if (c0767v2.M0()) {
            C0767v2 c0767v2B = c0767v2.b(this.a);
            C0767v2 c0767v2A = c2750p20.a(c0767v2B, c0767v2B);
            return c0767v2A != c0767v2B ? c0767v2.a(this.a, c0767v2A) : c0767v2;
        }
        return c2750p20.a(c0767v2, c0767v2);
    }

    public final boolean a(C0729q2 c0729q2, C0684j2 c0684j2) {
        Map map = (Map) this.e.get(c0729q2.d);
        if (map != null) {
            return map.containsKey(c0684j2);
        }
        return false;
    }

    public final boolean a(C0684j2 c0684j2) {
        return this.f.containsValue(c0684j2) || this.g.containsKey(c0684j2);
    }

    public final void a(C0684j2 c0684j2, C0684j2 c0684j3, boolean z) {
        this.f.a(c0684j2, c0684j3);
        if (z) {
            this.h.put(c0684j3, com.android.tools.r8.graph.proto.k.a(AbstractC1639am.i(), (com.android.tools.r8.graph.proto.l) null, com.android.tools.r8.graph.proto.c.a().a(c0684j3.G0().size()).b().a()));
        }
    }

    public final void a(C0684j2 c0684j2, z4 z4Var, C0767v2 c0767v2) {
        ((Map) this.e.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.shaking.y4$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return y4.a((C0767v2) obj);
            }
        })).put(c0684j2, z4Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(C0767v2 c0767v2) {
        return new IdentityHashMap();
    }

    public final void a(y4 y4Var) {
        this.b.a(y4Var.b);
        this.c.putAll(y4Var.c);
        this.d.a((Set) y4Var.d.a());
        y4Var.f.a(new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.shaking.y4$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                this.f$0.a((Set) obj, (C0684j2) obj2, (C0684j2) obj3);
            }
        });
        this.h.putAll(y4Var.h);
        this.g.putAll(y4Var.g);
        for (C0767v2 c0767v2 : y4Var.e.keySet()) {
            Map map = (Map) this.e.get(c0767v2);
            Map map2 = (Map) y4Var.e.get(c0767v2);
            if (map != null) {
                map.putAll(map2);
            } else {
                this.e.put(c0767v2, map2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, final C0684j2 c0684j2, C0684j2 c0684j3) {
        if (AbstractC2734ot.b(set, new InterfaceC3205vM() { // from class: com.android.tools.r8.shaking.y4$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.a(c0684j2, (C0684j2) obj);
            }
        })) {
            boolean z = j;
            if (!z && set.size() != 1) {
                throw new AssertionError();
            }
            C0684j2 c0684j4 = (C0684j2) set.iterator().next();
            C0684j2 c0684j5 = (C0684j2) this.f.c(c0684j4);
            Set setA = this.f.a(c0684j4);
            if (!z && !setA.contains(c0684j4)) {
                throw new AssertionError();
            }
            this.f.a((Iterable) setA, (Object) c0684j2);
            this.f.b(c0684j2, c0684j5);
            return;
        }
        if (this.f.containsValue(c0684j2) && !this.f.f(c0684j2)) {
            M4 m4 = this.f;
            m4.b(c0684j2, (C0684j2) m4.c(c0684j2));
        }
        this.f.a((Iterable) set, (Object) c0684j2);
        if (set.size() <= 1 || this.f.f(c0684j2)) {
            return;
        }
        this.f.b(c0684j2, c0684j3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0684j2 c0684j2, C0684j2 c0684j3) {
        return this.f.containsValue(c0684j3) && c0684j3 != c0684j2;
    }
}
