package com.android.tools.r8.internal;

import com.android.tools.r8.errors.d$$ExternalSyntheticLambda0;
import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.shaking.C3704d1;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3762m1;
import com.android.tools.r8.utils.AbstractC3875c;
import com.android.tools.r8.utils.AbstractC3896g;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.C3932m1;
import com.android.tools.r8.utils.C3979v2;
import com.android.tools.r8.utils.InterfaceC3937n1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class N2 {
    static final /* synthetic */ boolean l = true;
    private final C0722p1 a;
    private final com.android.tools.r8.utils.A1 b;
    private final com.android.tools.r8.utils.A1.c c;
    private final HashMap d = new HashMap();
    private final AN e = AN.c();
    private final HashMap f = new HashMap();
    private final HashMap g = new HashMap();
    private final HashMap h = new HashMap();
    private final R2 i;
    private final C1257Pb j;
    final /* synthetic */ O2 k;

    /* JADX INFO: renamed from: $r8$lambda$sUZQVfQ4XNh1oZg9vIcD2TcdV-o, reason: not valid java name */
    public static /* synthetic */ HashMap m1021$r8$lambda$sUZQVfQ4XNh1oZg9vIcD2TcdVo() {
        return new HashMap();
    }

    public N2(O2 o2, R2 r2, C1257Pb c1257Pb) {
        this.k = o2;
        this.a = o2.a.a();
        this.b = o2.a.Y();
        this.c = o2.a.Y().c();
        this.i = r2;
        this.j = c1257Pb;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(int i) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(int i) {
        return true;
    }

    private AbstractC3292wV c(com.android.tools.r8.graph.o5 o5Var) {
        B1 b1H;
        if (o5Var.G().b(this.k.a)) {
            b1H = this.k.a.b().a();
        } else if (!o5Var.e().R0() || !this.e.a(o5Var)) {
            b1H = o5Var.B().h();
        } else {
            if (!l && !o5Var.getAccessFlags().W()) {
                throw new AssertionError();
            }
            b1H = (B1) this.e.b(o5Var);
        }
        if (b1H.g() && b1H.j().a(this.k.a)) {
            return b1H.j();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean d(com.android.tools.r8.graph.o5 o5Var) {
        return this.k.a.a(o5Var).o(this.b) && o5Var.B().z().a();
    }

    private boolean a(CN cn) {
        return !((com.android.tools.r8.graph.o5) ((AbstractC0748t0) cn.iterator().next())).G().Y0() && AbstractC2734ot.a((Iterable) cn, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.d((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Map map, Set set, com.android.tools.r8.graph.m5 m5Var) {
        C0767v2 c0767v2A;
        AbstractC1154Lf abstractC1154LfG = m5Var.e().X0().g();
        C0767v2 type = m5Var.getType();
        if (!abstractC1154LfG.m()) {
            C3704d1 c3704d1A = this.k.a.a(m5Var);
            boolean z = l;
            if (!z && c3704d1A.c((com.android.tools.r8.shaking.K0) this.b)) {
                throw new AssertionError();
            }
            if (c3704d1A.m() && !abstractC1154LfG.l() && !abstractC1154LfG.k()) {
                AbstractC3472z00 abstractC3472z00O = abstractC1154LfG.a().o();
                if (!z && !abstractC3472z00O.I()) {
                    throw new AssertionError();
                }
                C1229Oa c1229OaB = type.c(this.k.a).b();
                if (!abstractC3472z00O.a(c1229OaB)) {
                    if (abstractC3472z00O.c(c1229OaB, this.k.a)) {
                        if (abstractC3472z00O.w()) {
                            C1229Oa c1229OaB2 = abstractC3472z00O.b();
                            if (c1229OaB2.R() != this.a.Z1) {
                                c0767v2A = c1229OaB2.R();
                            } else if (c1229OaB2.S().e()) {
                                c0767v2A = c1229OaB2.S().d();
                            }
                        } else {
                            c0767v2A = abstractC3472z00O.a().a(this.a);
                        }
                        if (c0767v2A != type && AbstractC3875c.a(this.k.a, c0767v2A, type) && AbstractC3896g.a(this.k.a, c0767v2A, type)) {
                            type = c0767v2A;
                        }
                    } else if (!z && !this.b.n1.C) {
                        throw new AssertionError();
                    }
                }
            }
        }
        if (type != m5Var.getType()) {
            map.put(m5Var.getReference(), type);
        } else {
            set.add(m5Var.getReference());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0717o2 abstractC0717o2) {
        a(abstractC0717o2, abstractC0717o2, M2.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public G2 a(Set set, C2720of c2720of, Consumer consumer) {
        b(set);
        a(set, c2720of);
        C0702m c0702m = this.k.a;
        boolean z = H2.o;
        G2 g2 = new G2(c0702m);
        ArrayList<C0729q2> arrayList = new ArrayList(set);
        arrayList.sort(Comparator.comparing(new d$$ExternalSyntheticLambda0()));
        for (C0729q2 c0729q2 : arrayList) {
            if (a(c0729q2, c2720of, g2)) {
                consumer.accept(c0729q2);
            }
        }
        return g2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.W0 w0) {
        return !w0.C1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0) {
        return (abstractC0727q0 == null || abstractC0727q0.e0() || !set.add(abstractC0727q0)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2720of c2720of, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0) {
        O2 o2 = this.k;
        C2720of c2720ofA = (C2720of) o2.d.get(abstractC0727q0);
        if (c2720ofA == null) {
            c2720ofA = o2.a(abstractC0727q0);
        }
        c2720of.a(c2720ofA);
    }

    private void a(AbstractC0717o2 abstractC0717o2, AbstractC0717o2 abstractC0717o3, M2 m2) {
        ((Map) this.f.computeIfAbsent(abstractC0717o3, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return N2.m1021$r8$lambda$sUZQVfQ4XNh1oZg9vIcD2TcdVo();
            }
        }))).put(m2, abstractC0717o2);
        this.h.put(abstractC0717o2, new C3979v2(m2, abstractC0717o3));
    }

    private void a(Set set, final C2720of c2720of) {
        a(set).forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda21
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c2720of, (AbstractC0717o2) obj, (CN) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:25:0x0083  */
    public void a(final C2720of c2720of, AbstractC0717o2 abstractC0717o2, CN cn) {
        boolean z;
        AbstractC3292wV abstractC3292wVJ;
        B1 b1H;
        if (AbstractC2734ot.b(cn, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.a(c2720of, (com.android.tools.r8.graph.o5) obj);
            }
        })) {
            return;
        }
        Iterator it = cn.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2A = ((com.android.tools.r8.graph.o5) it.next()).a();
            if (c0729q2A.isInterface() && ((C3733i) this.k.a.f()).z().b(c0729q2A)) {
                return;
            }
        }
        int i = 0;
        if (cn.size() <= 1) {
            com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) ((AbstractC0748t0) cn.iterator().next());
            z = o5Var.B().v() && o5Var.B().t().get(0) && AbstractC2913rL.a(this.k.a, o5Var) && AbstractC2913rL.a(this.k.a, o5Var, 0);
        }
        C1303Qq c1303Qq = new C1303Qq(16);
        C2879qr c2879qr = new C2879qr();
        while (true) {
            C0767v2 c0767v2 = null;
            if (i < abstractC0717o2.b().u0().size()) {
                int i2 = i + 1;
                Iterator it2 = cn.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        com.android.tools.r8.graph.o5 o5Var2 = (com.android.tools.r8.graph.o5) it2.next();
                        if (!o5Var2.e().s1() && (!o5Var2.B().v() || !o5Var2.B().t().get(i2) || !AbstractC2913rL.a(this.k.a, o5Var2) || !AbstractC2913rL.a(this.k.a, o5Var2, i2))) {
                            com.android.tools.r8.ir.optimize.info.a aVarI = o5Var2.B().i();
                            aVarI.getClass();
                            if (aVarI instanceof com.android.tools.r8.ir.optimize.info.b) {
                                B1 b1A = aVarI.a().a(i2);
                                if (!b1A.g() || !b1A.j().b(this.k.a, o5Var2)) {
                                }
                            }
                            Iterator it3 = cn.iterator();
                            C0767v2 c0767v3 = null;
                            while (true) {
                                if (it3.hasNext()) {
                                    com.android.tools.r8.graph.o5 o5Var3 = (com.android.tools.r8.graph.o5) it3.next();
                                    if (!o5Var3.getAccessFlags().W()) {
                                        C0767v2 c0767v2B = b(o5Var3, i);
                                        if (c0767v2B == null || !(c0767v3 == null || c0767v3 == c0767v2B)) {
                                            break;
                                        } else {
                                            c0767v3 = c0767v2B;
                                        }
                                    }
                                } else {
                                    if (!l && c0767v3 != null && c0767v3 == ((com.android.tools.r8.graph.o5) ((AbstractC0748t0) cn.iterator().next())).b(i)) {
                                        throw new AssertionError();
                                    }
                                    c0767v2 = c0767v3;
                                    break;
                                }
                            }
                            if (c0767v2 != null) {
                                c1303Qq.a(i, c0767v2);
                            }
                        }
                    } else {
                        c2879qr.a(i);
                    }
                    i = i2;
                }
            } else {
                if (abstractC0717o2.b().v0().Y0()) {
                    abstractC3292wVJ = null;
                    break;
                }
                Iterator it4 = cn.iterator();
                abstractC3292wVJ = null;
                while (it4.hasNext()) {
                    com.android.tools.r8.graph.o5 o5Var4 = (com.android.tools.r8.graph.o5) it4.next();
                    if (!o5Var4.e().s1()) {
                        if (((C3733i) this.k.a.f()).a(this.k.a, o5Var4)) {
                            if (o5Var4.G().b(this.k.a)) {
                                b1H = this.k.a.b().a();
                            } else {
                                b1H = o5Var4.B().h();
                            }
                            if (b1H.g() && b1H.j().a(this.k.a) && (abstractC3292wVJ == null || b1H.equals(abstractC3292wVJ))) {
                                abstractC3292wVJ = b1H.j();
                            }
                        }
                        abstractC3292wVJ = null;
                        break;
                    }
                }
                if (abstractC3292wVJ == null && !a(cn)) {
                    Iterator it5 = cn.iterator();
                    C0767v2 c0767v4 = null;
                    while (true) {
                        if (it5.hasNext()) {
                            com.android.tools.r8.graph.o5 o5Var5 = (com.android.tools.r8.graph.o5) it5.next();
                            if (!o5Var5.e().s1()) {
                                C0767v2 c0767v2A = a(o5Var5, AbstractC3938n2.c, (AbstractC3292wV) null);
                                if (c0767v2A == null || !(c0767v4 == null || c0767v4 == c0767v2A)) {
                                    break;
                                } else {
                                    c0767v4 = c0767v2A;
                                }
                            }
                        } else {
                            if (!l && c0767v4 != null && c0767v4 == ((com.android.tools.r8.graph.o5) ((AbstractC0748t0) cn.iterator().next())).G()) {
                                throw new AssertionError();
                            }
                            c0767v2 = c0767v4;
                            break;
                        }
                    }
                } else {
                    c0767v2 = this.a.D1;
                }
                if (z || c0767v2 != null || !c1303Qq.isEmpty() || !c2879qr.isEmpty()) {
                    this.d.put(abstractC0717o2, new M2(z, c0767v2, c1303Qq, c2879qr));
                }
                if (c0767v2 != this.a.D1 || abstractC3292wVJ == null) {
                    return;
                }
                Iterator it6 = cn.iterator();
                while (it6.hasNext()) {
                    com.android.tools.r8.graph.o5 o5Var6 = (com.android.tools.r8.graph.o5) it6.next();
                    if (o5Var6.getAccessFlags().W()) {
                        this.e.a(o5Var6, abstractC3292wVJ);
                    }
                }
                return;
            }
        }
    }

    private void b(Set set) {
        final C2720of c2720ofA = C2720of.a();
        final Set setC = OU.c();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda23
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(c2720ofA, (com.android.tools.r8.graph.o5) obj);
                }
            }, new Predicate() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda22
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return N2.a((com.android.tools.r8.graph.W0) obj);
                }
            });
            this.k.b.a(c0729q2, new BiPredicate() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda24
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return N2.a(setC, (C0767v2) obj, (AbstractC0727q0) obj2);
                }
            }, new BiConsumer() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda25
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(c2720ofA, (C0767v2) obj, (AbstractC0727q0) obj2);
                }
            });
        }
        c2720ofA.forEach(new Consumer() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda26
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0717o2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C2720of c2720of, com.android.tools.r8.graph.o5 o5Var) {
        C3762m1 c3762m1A = this.k.a.a(o5Var);
        if (c3762m1A.b((com.android.tools.r8.shaking.K0) this.b) && c3762m1A.d((com.android.tools.r8.shaking.K0) this.b)) {
            return;
        }
        c2720of.a(o5Var.z());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(com.android.tools.r8.graph.T0 t0) {
        return t0.getType().O0();
    }

    private com.android.tools.r8.graph.proto.k b(com.android.tools.r8.graph.o5 o5Var) {
        M2 m2 = (M2) this.d.get(o5Var.z());
        if (m2 == null) {
            return com.android.tools.r8.graph.proto.k.h();
        }
        final InterfaceC1277Pr interfaceC1277Pr = m2.d;
        final InterfaceC0992Fq interfaceC0992Fq = m2.c;
        if (o5Var.getAccessFlags().W()) {
            return a(o5Var, m2.b, interfaceC0992Fq, interfaceC1277Pr);
        }
        boolean z = m2.a;
        C0767v2 c0767v2 = m2.b;
        Objects.requireNonNull(interfaceC0992Fq);
        IntFunction intFunction = new IntFunction() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda13
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return (C0767v2) interfaceC0992Fq.get(i);
            }
        };
        Objects.requireNonNull(interfaceC1277Pr);
        com.android.tools.r8.graph.proto.k kVarA = a(o5Var, z, c0767v2, intFunction, new IntPredicate() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda14
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return interfaceC1277Pr.f(i);
            }
        });
        if (!l) {
            if (kVarA.a().h() != interfaceC1277Pr.size() + com.android.tools.r8.utils.M.a(z)) {
                throw new AssertionError();
            }
        }
        return kVarA;
    }

    private static HashMap a(Set set) {
        final HashMap map = new HashMap();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    N2.a(map, (com.android.tools.r8.graph.o5) obj);
                }
            };
            c0729q2.getClass();
            c0729q2.h(consumer, CM.b());
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, com.android.tools.r8.graph.o5 o5Var) {
        ((CN) map.computeIfAbsent(o5Var.z(), com.android.tools.r8.utils.Z1.a(new N2$$ExternalSyntheticLambda12()))).add(o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C2720of c2720of, com.android.tools.r8.graph.o5 o5Var) {
        return !((!this.k.a.a(o5Var).j(this.b) || o5Var.e().D1().c() || ((C3733i) this.k.a.f()).a(o5Var) || c2720of.b(o5Var)) ? false : true);
    }

    private boolean a(C0729q2 c0729q2, final C2720of c2720of, final G2 g2) {
        final com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K();
        final Set setC = OU.c();
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        c0729q2.e(new Consumer() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(identityHashMap, setC, (com.android.tools.r8.graph.m5) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return N2.b((com.android.tools.r8.graph.T0) obj);
            }
        });
        c0729q2.e(new Consumer() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(setC, identityHashMap, g2, k, (com.android.tools.r8.graph.m5) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return N2.a((com.android.tools.r8.graph.T0) obj);
            }
        });
        final C2720of c2720ofA = C2720of.a();
        final AN anC = AN.c();
        c0729q2.f(new Consumer() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2720of, c2720ofA, anC, (com.android.tools.r8.graph.o5) obj);
            }
        }, CM.b());
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(anC, c2720of, c2720ofA, g2, k, (com.android.tools.r8.graph.o5) obj);
            }
        });
        return k.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.T0 t0) {
        return t0.getType().O0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, Map map, G2 g2, com.android.tools.r8.utils.K k, com.android.tools.r8.graph.m5 m5Var) {
        com.android.tools.r8.graph.Y0 y0A = a(map, set, m5Var);
        if (y0A != m5Var.getReference()) {
            g2.a(m5Var.getReference(), y0A);
            k.f();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AN an, final C2720of c2720of, final C2720of c2720of2, G2 g2, com.android.tools.r8.utils.K k, final com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.proto.k kVarA = (com.android.tools.r8.graph.proto.k) an.a((com.android.tools.r8.graph.n5) o5Var, new Supplier() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda15
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a(o5Var, c2720of, c2720of2);
            }
        });
        if (o5Var.e().C1()) {
            if (kVarA.g()) {
                if (!l && !c2720of2.b(o5Var)) {
                    throw new AssertionError();
                }
                return;
            }
            kVarA = a(o5Var, kVarA, c2720of2);
        }
        C0684j2 c0684j2A = a(o5Var, kVarA);
        if (c0684j2A != o5Var.getReference()) {
            g2.a(o5Var.getReference(), c0684j2A, kVarA);
            k.f();
        } else {
            if (kVarA.g()) {
                return;
            }
            g2.a(o5Var.getReference(), kVarA);
            k.f();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.proto.k a(com.android.tools.r8.graph.o5 o5Var, C2720of c2720of, C2720of c2720of2) {
        com.android.tools.r8.graph.proto.k kVarH;
        if (o5Var.e().f.N()) {
            if (!l && !o5Var.e().f.N()) {
                throw new AssertionError();
            }
            if (this.k.a.a(o5Var).j(this.b) && !o5Var.e().D1().c() && !((C3733i) this.k.a.f()).a(o5Var) && !c2720of.b(o5Var)) {
                kVarH = a(o5Var);
            } else {
                kVarH = com.android.tools.r8.graph.proto.k.h();
            }
            return (!o5Var.e().C1() || c2720of2 == null) ? kVarH : a(o5Var, kVarH, c2720of2);
        }
        return b(o5Var);
    }

    private com.android.tools.r8.graph.Y0 a(Map map, final Set set, com.android.tools.r8.graph.m5 m5Var) {
        C0767v2 c0767v2 = (C0767v2) map.getOrDefault(m5Var.getReference(), m5Var.getType());
        if (c0767v2 == m5Var.getType()) {
            if (l || set.contains(m5Var.getReference())) {
                return m5Var.getReference();
            }
            throw new AssertionError();
        }
        C0722p1 c0722p1 = this.a;
        C0767v2 c0767v2T = m5Var.t();
        String string = m5Var.u().toString();
        Objects.requireNonNull(set);
        return c0722p1.a(c0767v2T, c0767v2, string, new Predicate() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return set.add((com.android.tools.r8.graph.Y0) obj);
            }
        });
    }

    private C0684j2 a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.proto.k kVar) {
        final AbstractC0717o2 abstractC0717o2Z = o5Var.z();
        final M2 m2A = M2.a(o5Var, kVar);
        AbstractC0717o2 abstractC0717o2 = (AbstractC0717o2) ((Map) this.f.getOrDefault(abstractC0717o2Z, Collections.emptyMap())).get(m2A);
        if (abstractC0717o2 != null) {
            if (!l && !abstractC0717o2.b().equals(kVar.a(this.a, o5Var).H0())) {
                throw new AssertionError();
            }
            C0767v2 c0767v2T = o5Var.t();
            C0722p1 c0722p1 = this.a;
            c0767v2T.getClass();
            return c0722p1.a(c0767v2T, abstractC0717o2.b(), abstractC0717o2.a());
        }
        C0684j2 c0684j2A = kVar.a(this.a, o5Var);
        AbstractC0717o2 abstractC0717o2J0 = c0684j2A.J0();
        if (!this.h.containsKey(abstractC0717o2J0)) {
            if (!o5Var.e().C1()) {
                a(abstractC0717o2J0, abstractC0717o2Z, m2A);
            }
            return c0684j2A;
        }
        C3979v2 c3979v2 = (C3979v2) this.h.get(abstractC0717o2J0);
        if (!l && ((M2) c3979v2.a()).equals(m2A) && ((AbstractC0717o2) c3979v2.b()).equals(abstractC0717o2Z)) {
            throw new AssertionError();
        }
        final C3932m1 c3932m1 = (C3932m1) this.g.computeIfAbsent(abstractC0717o2J0, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda19
            @Override // java.util.function.Supplier
            public final Object get() {
                return new C3932m1();
            }
        }));
        C0684j2 c0684j2A2 = this.a.a(o5Var.u().toString(), c0684j2A.H0(), o5Var.t(), new Predicate() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(c3932m1, abstractC0717o2Z, m2A, (C0684j2) obj);
            }
        }, c3932m1.a());
        if (!o5Var.e().C1()) {
            a(c0684j2A2.J0(), abstractC0717o2Z, m2A);
        }
        return c0684j2A2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C3932m1 c3932m1, AbstractC0717o2 abstractC0717o2, M2 m2, C0684j2 c0684j2) {
        c3932m1.c();
        C3979v2 c3979v2 = (C3979v2) this.h.get(c0684j2.J0());
        return c3979v2 == null || (((M2) c3979v2.a()).equals(m2) && ((AbstractC0717o2) c3979v2.b()).equals(abstractC0717o2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2720of c2720of, C2720of c2720of2, AN an, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.proto.k kVarH;
        if (!l && !o5Var.e().f.N()) {
            throw new AssertionError();
        }
        if (this.k.a.a(o5Var).j(this.b) && !o5Var.e().D1().c() && !((C3733i) this.k.a.f()).a(o5Var) && !c2720of.b(o5Var)) {
            kVarH = a(o5Var);
        } else {
            kVarH = com.android.tools.r8.graph.proto.k.h();
        }
        o5Var.e().C1();
        if (kVarH.g()) {
            c2720of2.getClass();
            c2720of2.a(o5Var.getReference());
        }
        an.a(o5Var, kVarH);
    }

    private com.android.tools.r8.graph.proto.k a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.proto.k kVar, C2720of c2720of) {
        if (c2720of.a(kVar.a(this.a, o5Var))) {
            return kVar;
        }
        if (!this.c.c()) {
            C0722p1 c0722p1 = this.a;
            AbstractC2963s10 it = AbstractC1639am.a(c0722p1.A1, c0722p1.Z1).iterator();
            while (it.hasNext()) {
                com.android.tools.r8.graph.proto.k kVarA = kVar.a(Arrays.asList(new C3151uh((C0767v2) it.next())));
                if (c2720of.a(kVarA.a(this.a, o5Var))) {
                    return kVarA;
                }
            }
        }
        com.android.tools.r8.synthesis.D D = this.k.a.D();
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda28
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l2) {
                return l2.x;
            }
        };
        C1314Rb c1314RbA = this.j.a(o5Var).a();
        C0702m c0702m = this.k.a;
        D.getClass();
        C0729q2 c0729q2A = D.a(c, c1314RbA, c0702m, C3912j0.b());
        this.i.a(c0729q2A, o5Var);
        com.android.tools.r8.graph.proto.k kVarA2 = kVar.a(Arrays.asList(new C3151uh(c0729q2A.getType())));
        boolean zA = c2720of.a(kVarA2.a(this.a, o5Var));
        if (l || zA) {
            return kVarA2;
        }
        throw new AssertionError();
    }

    private com.android.tools.r8.graph.proto.k a(final com.android.tools.r8.graph.o5 o5Var, C0767v2 c0767v2, final InterfaceC0992Fq interfaceC0992Fq, final InterfaceC1277Pr interfaceC1277Pr) {
        final com.android.tools.r8.graph.proto.c.a aVarA = com.android.tools.r8.graph.proto.c.a().a(o5Var.e().h1());
        C0800x2 c0800x2D = o5Var.D();
        InterfaceC3937n1 interfaceC3937n1 = new InterfaceC3937n1() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3937n1
            public final void a(int i, Object obj) {
                N2.a(o5Var, interfaceC1277Pr, aVarA, interfaceC0992Fq, i, (C0767v2) obj);
            }
        };
        int i = 0;
        while (true) {
            C0767v2[] c0767v2Arr = c0800x2D.a;
            if (i < c0767v2Arr.length) {
                interfaceC3937n1.a(i, c0767v2Arr[i]);
                i++;
            } else {
                return com.android.tools.r8.graph.proto.k.a(Collections.emptyList(), a(c0767v2, o5Var), aVarA.a());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC1277Pr interfaceC1277Pr, com.android.tools.r8.graph.proto.c.a aVar, InterfaceC0992Fq interfaceC0992Fq, int i, C0767v2 c0767v2) {
        int iD1 = o5Var.e().d1() + i;
        if (interfaceC1277Pr.f(i)) {
            aVar.a(iD1, ((com.android.tools.r8.graph.proto.h.a) com.android.tools.r8.graph.proto.h.e().a(c0767v2)).a());
        } else if (interfaceC0992Fq.a(i)) {
            C0767v2 c0767v3 = (C0767v2) interfaceC0992Fq.get(i);
            aVar.a(iD1, com.android.tools.r8.graph.proto.l.e().a(c0767v3).c(c0767v2).b(c0767v3).a());
        }
    }

    private com.android.tools.r8.graph.proto.k a(final com.android.tools.r8.graph.o5 o5Var) {
        IntFunction intFunction;
        if (this.k.a.a(o5Var).l(this.b)) {
            intFunction = new IntFunction() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda16
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return this.f$0.b(o5Var, i);
                }
            };
        } else {
            intFunction = new IntFunction() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda17
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return N2.a(i);
                }
            };
        }
        return a(o5Var, true, a(o5Var, o5Var.B().z(), c(o5Var)), intFunction, new IntPredicate() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda18
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return N2.b(i);
            }
        });
    }

    private C0767v2 a(com.android.tools.r8.graph.o5 o5Var, AbstractC3938n2 abstractC3938n2, AbstractC3292wV abstractC3292wV) {
        C0767v2 c0767v2G = o5Var.G();
        if (c0767v2G.Y0()) {
            return null;
        }
        if (abstractC3292wV != null) {
            return this.a.D1;
        }
        C3762m1 c3762m1A = this.k.a.a(o5Var);
        if (c3762m1A.o(this.b) && abstractC3938n2.a()) {
            return this.a.D1;
        }
        if (!c3762m1A.m(this.b)) {
            return null;
        }
        AbstractC3472z00 abstractC3472z00A = o5Var.B().m().a(c0767v2G.c(this.k.a));
        boolean z = l;
        if (!z) {
            abstractC3472z00A.getClass();
            if (!(abstractC3472z00A instanceof C2163hZ) && !abstractC3472z00A.a(c0767v2G.c(this.k.a), this.k.a)) {
                throw new AssertionError();
            }
        }
        if (!abstractC3472z00A.w()) {
            if (z || abstractC3472z00A.r() || abstractC3472z00A.E() || (abstractC3472z00A instanceof C2163hZ)) {
                return null;
            }
            throw new AssertionError();
        }
        C0767v2 c0767v2A = abstractC3472z00A.b().a(this.a);
        if (c0767v2A != c0767v2G && ((C3733i) this.k.a.f()).c(c0767v2A, c0767v2G) && AbstractC3875c.a(this.k.a, c0767v2A, c0767v2G) && AbstractC3896g.a(this.k.a, c0767v2A, c0767v2G)) {
            return c0767v2A;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0767v2 b(com.android.tools.r8.graph.o5 o5Var, int i) {
        if (!this.k.a.a(o5Var).l(this.b)) {
            return null;
        }
        C0767v2 c0767v2B = o5Var.b(i);
        if (!c0767v2B.O0()) {
            return null;
        }
        AbstractC1154Lf abstractC1154LfB = o5Var.B().i().b(o5Var.e().d1() + i);
        if (abstractC1154LfB == null || abstractC1154LfB.m()) {
            return null;
        }
        AbstractC3472z00 abstractC3472z00C = c0767v2B.c(this.k.a);
        AbstractC3472z00 abstractC3472z00A = abstractC1154LfB.a(abstractC3472z00C);
        boolean z = l;
        if (!z && !abstractC3472z00A.a(abstractC3472z00C, this.k.a)) {
            throw new AssertionError();
        }
        if (!z && !abstractC3472z00A.I()) {
            throw new AssertionError();
        }
        if (abstractC3472z00A.E() || abstractC3472z00A.r()) {
            return null;
        }
        if (!z && !abstractC3472z00A.w()) {
            throw new AssertionError();
        }
        C0767v2 c0767v2A = abstractC3472z00A.b().a(this.a);
        if (c0767v2A != c0767v2B && ((C3733i) this.k.a.f()).c(c0767v2A, c0767v2B) && AbstractC3875c.a(this.k.a, c0767v2A, c0767v2B) && AbstractC3896g.a(this.k.a, c0767v2A, c0767v2B)) {
            return c0767v2A;
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0116  */
    /* JADX WARN: Code duplicated, block: B:35:0x011e  */
    /* JADX WARN: Code duplicated, block: B:50:0x0146 A[SYNTHETIC] */
    private com.android.tools.r8.graph.proto.k a(com.android.tools.r8.graph.o5 o5Var, boolean z, C0767v2 c0767v2, IntFunction intFunction, IntPredicate intPredicate) {
        C0767v2 c0767v3;
        C0767v2 c0767v2B;
        List listEmptyList = Collections.emptyList();
        com.android.tools.r8.graph.proto.l lVarA = a(c0767v2, o5Var);
        com.android.tools.r8.graph.proto.c.a aVarA = com.android.tools.r8.graph.proto.c.a().a(o5Var.e().h1());
        if (o5Var.e().B1() && z && o5Var.B().v() && o5Var.B().t().get(0) && AbstractC2913rL.a(this.k.a, o5Var) && AbstractC2913rL.a(this.k.a, o5Var, 0)) {
            aVarA.a(0, ((com.android.tools.r8.graph.proto.i) new com.android.tools.r8.graph.proto.i().a(o5Var.t())).a()).b();
        }
        com.android.tools.r8.ir.optimize.info.a aVarI = o5Var.B().i();
        for (int i = 0; i < o5Var.D().size(); i++) {
            int iD1 = o5Var.e().d1() + i;
            if (intPredicate.test(i)) {
                if (o5Var.B().v() && o5Var.B().t().get(iD1) && AbstractC2913rL.a(this.k.a, o5Var) && AbstractC2913rL.a(this.k.a, o5Var, iD1)) {
                    aVarA.a(iD1, ((com.android.tools.r8.graph.proto.h.a) com.android.tools.r8.graph.proto.h.e().a(o5Var.b(i))).a());
                } else {
                    B1 b1A = aVarI.a(iD1);
                    if (b1A.g() && b1A.j().b(this.k.a, o5Var)) {
                        aVarA.a(iD1, ((com.android.tools.r8.graph.proto.h.a) ((com.android.tools.r8.graph.proto.h.a) com.android.tools.r8.graph.proto.h.e().a(b1A.j())).a(o5Var.b(i))).a());
                    } else {
                        c0767v3 = (C0767v2) intFunction.apply(i);
                        if (c0767v3 != null) {
                            c0767v2B = o5Var.b(i);
                            if (l) {
                            }
                            aVarA.a(iD1, com.android.tools.r8.graph.proto.l.e().a(c0767v3).c(c0767v2B).b(c0767v3).a());
                        } else {
                            continue;
                        }
                    }
                }
            } else {
                c0767v3 = (C0767v2) intFunction.apply(i);
                if (c0767v3 != null) {
                    c0767v2B = o5Var.b(i);
                    if (l && c0767v3 == c0767v2B) {
                        throw new AssertionError();
                    }
                    aVarA.a(iD1, com.android.tools.r8.graph.proto.l.e().a(c0767v3).c(c0767v2B).b(c0767v3).a());
                } else {
                    continue;
                }
            }
        }
        return com.android.tools.r8.graph.proto.k.a(listEmptyList, lVarA, aVarA.a());
    }

    private com.android.tools.r8.graph.proto.l a(C0767v2 c0767v2, final com.android.tools.r8.graph.o5 o5Var) {
        if (c0767v2 == null) {
            if (l || !this.e.a(o5Var)) {
                return null;
            }
            throw new AssertionError();
        }
        if (l || c0767v2 != o5Var.G()) {
            return com.android.tools.r8.graph.proto.l.e().a(c0767v2 == this.a.D1, new Consumer() { // from class: com.android.tools.r8.internal.N2$$ExternalSyntheticLambda27
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(o5Var, (com.android.tools.r8.graph.proto.l.a) obj);
                }
            }).a(c0767v2).c(o5Var.G()).b(c0767v2).a();
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.proto.l.a aVar) {
        aVar.a(c(o5Var));
    }
}
