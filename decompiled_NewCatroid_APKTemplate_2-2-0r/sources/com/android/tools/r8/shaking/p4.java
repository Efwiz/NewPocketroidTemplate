package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0718o3;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0738r3;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0791w3;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.graph.C0817z3;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1699bX;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.internal.KE;
import com.android.tools.r8.ir.optimize.C3562h0;
import com.android.tools.r8.utils.C3894f2;
import com.android.tools.r8.utils.C3912j0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class p4 {
    static final /* synthetic */ boolean g = true;
    private final C0729q2 a;
    private final C0729q2 b;
    private final y4 c;
    private final ArrayList d;
    private boolean e;
    final /* synthetic */ x4 f;

    private p4(x4 x4Var, C0729q2 c0729q2, C0729q2 c0729q3) {
        this.f = x4Var;
        this.c = new y4(x4Var.c.a());
        this.d = new ArrayList();
        this.e = false;
        this.a = c0729q2;
        this.b = c0729q3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0767v2 c0767v2) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0767v2 c0767v2, C0767v2 c0767v3) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(com.android.tools.r8.graph.W0 w0) {
        if (!g && w0.J1()) {
            throw new AssertionError();
        }
        w0.W1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(com.android.tools.r8.graph.W0 w0) {
        if (w0.J1()) {
            w0.a2();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean d(com.android.tools.r8.graph.W0 w0) {
        return !w0.J1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ KE b(C0684j2 c0684j2, EnumC1953et enumC1953et, com.android.tools.r8.graph.proto.k kVar) {
        return new KE(c0684j2, null, enumC1953et, kVar);
    }

    /* JADX WARN: Code duplicated, block: B:101:0x02fe  */
    /* JADX WARN: Code duplicated, block: B:104:0x0342 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:164:0x00ec A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:165:0x0187 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:168:0x00a6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:169:0x00f0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:170:0x0118 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:175:0x0187 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:176:0x0120 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:177:0x00a0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:34:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:36:0x0112  */
    /* JADX WARN: Code duplicated, block: B:49:0x013e  */
    /* JADX WARN: Code duplicated, block: B:52:0x0191  */
    /* JADX WARN: Code duplicated, block: B:54:0x01d1  */
    /* JADX WARN: Code duplicated, block: B:56:0x01dd  */
    /* JADX WARN: Code duplicated, block: B:58:0x01fd  */
    /* JADX WARN: Code duplicated, block: B:60:0x0205  */
    /* JADX WARN: Code duplicated, block: B:66:0x021c  */
    /* JADX WARN: Code duplicated, block: B:68:0x0220  */
    /* JADX WARN: Code duplicated, block: B:76:0x0246  */
    /* JADX WARN: Code duplicated, block: B:77:0x0248  */
    /* JADX WARN: Code duplicated, block: B:80:0x0268  */
    /* JADX WARN: Code duplicated, block: B:82:0x029b  */
    /* JADX WARN: Code duplicated, block: B:93:0x02d7  */
    /* JADX WARN: Code duplicated, block: B:95:0x02dc  */
    /* JADX WARN: Code duplicated, block: B:97:0x02e2  */
    /* JADX WARN: Code duplicated, block: B:98:0x02e5  */
    public final boolean a() {
        C0800x2 c0800x2;
        com.android.tools.r8.graph.W0 w0T;
        com.android.tools.r8.graph.W0 w0A;
        C0725p4 accessFlags;
        HashMap map;
        boolean z;
        EnumC1953et enumC1953et;
        com.android.tools.r8.graph.W0 w0A2;
        final HashSet hashSet = new HashSet();
        Iterable<com.android.tools.r8.graph.W0> iterableM1 = this.b.M1();
        C3894f2 c3894f2A = C3894f2.a();
        Iterator<com.android.tools.r8.graph.W0> it = iterableM1.iterator();
        while (it.hasNext()) {
            hashSet.add(c3894f2A.c(it.next().getReference()));
        }
        final HashMap map2 = new HashMap();
        final HashMap map3 = new HashMap();
        final Predicate predicate = new Predicate() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return p4.a(hashSet, map2, map3, (C0684j2) obj);
            }
        };
        C0729q2 c0729q2 = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(predicate, map2, (o5) obj);
            }
        };
        c0729q2.getClass();
        c0729q2.d(consumer, CM.b());
        Iterator<com.android.tools.r8.graph.W0> it2 = this.a.P1().iterator();
        while (true) {
            if (it2.hasNext()) {
                final com.android.tools.r8.graph.W0 next = it2.next();
                I4 i4G = this.f.b.g(this.b, next.getReference());
                if (!i4G.x()) {
                    this.e = true;
                } else {
                    w0T = i4G.t();
                    if (w0T != next) {
                        if (!g && w0T.S1() != next.S1()) {
                            throw new AssertionError();
                        }
                    }
                    if (w0T != null) {
                        if (next.s1()) {
                            y4 y4Var = this.c;
                            y4Var.c.put(next.getReference(), w0T.getReference());
                            y4Var.c.put(w0T.getReference(), w0T.getReference());
                            y4Var.d(next.getReference(), w0T.getReference());
                            if (next.R1() && w0T.R1()) {
                                w0T.f.b();
                            }
                        } else {
                            if (this.a.e.W()) {
                                x4 x4Var = this.f;
                                final C3562h0 c3562h0A = x4Var.g.a(this.b, x4Var.f, x4Var.h);
                                w0A = a(next, new Predicate() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return p4.a(predicate, c3562h0A, (C0684j2) obj);
                                    }
                                }, 1, this.f.c.a().c(next.getReference()));
                                w0A.f.D();
                                if (!w0A.a1().z0()) {
                                    this.e = true;
                                }
                                c3562h0A.a(w0A.getReference());
                            } else {
                                w0A = a(next, predicate, 1, next.getReference().h);
                                if (this.f.c.Y().D0().A()) {
                                    accessFlags = w0A.getAccessFlags();
                                    if (x4.s && accessFlags.W()) {
                                        throw new AssertionError();
                                    }
                                    accessFlags.I();
                                    accessFlags.K();
                                    accessFlags.C();
                                } else {
                                    if (x4.s && w0A.f.W()) {
                                        throw new AssertionError();
                                    }
                                    w0A.f.L();
                                    w0A.f.K();
                                    w0A.f.A();
                                }
                            }
                            if (w0A.f.N()) {
                                map = map2;
                            } else {
                                map = map3;
                            }
                            map.put(C3894f2.a().c(w0A.getReference()), w0A);
                            a(next, w0A);
                            this.c.b(w0A.getReference());
                            if (w0T == null) {
                                C0684j2 c0684j2A = this.f.a.e.a(this.b.d, next.getReference().h, next.getReference().f);
                                C0725p4 c0725p4P = next.f.P();
                                c0725p4P.f0();
                                c0725p4P.E();
                                c0725p4P.h0();
                                z = g;
                                if (z && !w0A.E0() && !w0A.H1() && !w0A.I1()) {
                                    throw new AssertionError();
                                }
                                C0684j2 c0684j2A2 = this.f.c.E().a((AbstractC1476Wj) null, next.getReference());
                                C0684j2 reference = w0A.getReference();
                                if (w0A.E0()) {
                                    enumC1953et = EnumC1953et.e;
                                } else if (w0A.H1()) {
                                    enumC1953et = EnumC1953et.g;
                                } else {
                                    enumC1953et = EnumC1953et.c;
                                }
                                v4 v4Var = new v4(c0684j2A, c0684j2A2, reference, enumC1953et, this.b.isInterface());
                                this.d.add(v4Var);
                                w0A2 = com.android.tools.r8.graph.W0.Y1().a(c0684j2A).a(c0725p4P).a(v4Var).a(next.p1() ? next.Z0() : null).b(next.I0()).a(next.I0()).a(next.D1()).a(next.e1()).a();
                                if (!next.f.k() && !z && !w0A2.f.k()) {
                                    throw new AssertionError();
                                }
                                this.c.c(next.getReference(), w0A2.getReference());
                                map3.put(C3894f2.a().c(w0A2.getReference()), w0A2);
                                w0T = w0A2;
                            }
                            final o5 o5Var = new o5(this.b, w0T);
                            AbstractC3735i1 abstractC3735i1V = this.f.c.v();
                            Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    this.f$0.a(o5Var, next, (C3730h1) obj);
                                }
                            };
                            C3730h1 c3730h1 = (C3730h1) abstractC3735i1V;
                            c3730h1.getClass();
                            consumer2.accept(c3730h1);
                            this.c.c.put(next.getReference(), w0T.getReference());
                            this.c.a(next.getReference(), w0A.getReference(), w0A.E0());
                        }
                    } else {
                        if (this.e) {
                            if (!g) {
                                a(AbstractC1699bX.a(map2.values().stream(), map3.values().stream()));
                            }
                            return false;
                        }
                        if (next.s1()) {
                            if (!this.b.x1()) {
                                if (g && !this.f.c.Y().n1.w0) {
                                    throw new AssertionError();
                                }
                                this.e = true;
                                return false;
                            }
                            com.android.tools.r8.graph.W0 w0A3 = a(next, predicate, 3, next.getReference().h);
                            w0A3.a(next.D1());
                            this.c.c.put(next.getReference(), w0A3.getReference());
                            this.c.a(next.getReference(), w0A3.getReference(), false);
                            map3.put(C3894f2.a().c(w0A3.getReference()), w0A3);
                        } else {
                            if (this.a.e.W()) {
                                x4 x4Var2 = this.f;
                                final C3562h0 c3562h0A2 = x4Var2.g.a(this.b, x4Var2.f, x4Var2.h);
                                w0A = a(next, new Predicate() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return p4.a(predicate, c3562h0A2, (C0684j2) obj);
                                    }
                                }, 1, this.f.c.a().c(next.getReference()));
                                w0A.f.D();
                                if (!w0A.a1().z0()) {
                                    this.e = true;
                                }
                                c3562h0A2.a(w0A.getReference());
                            } else {
                                w0A = a(next, predicate, 1, next.getReference().h);
                                if (this.f.c.Y().D0().A()) {
                                    accessFlags = w0A.getAccessFlags();
                                    if (x4.s) {
                                    }
                                    accessFlags.I();
                                    accessFlags.K();
                                    accessFlags.C();
                                } else {
                                    if (x4.s) {
                                    }
                                    w0A.f.L();
                                    w0A.f.K();
                                    w0A.f.A();
                                }
                            }
                            if (w0A.f.N()) {
                                map = map2;
                            } else {
                                map = map3;
                            }
                            map.put(C3894f2.a().c(w0A.getReference()), w0A);
                            a(next, w0A);
                            this.c.b(w0A.getReference());
                            if (w0T == null) {
                                C0684j2 c0684j2A3 = this.f.a.e.a(this.b.d, next.getReference().h, next.getReference().f);
                                C0725p4 c0725p4P2 = next.f.P();
                                c0725p4P2.f0();
                                c0725p4P2.E();
                                c0725p4P2.h0();
                                z = g;
                                if (z) {
                                }
                                C0684j2 c0684j2A4 = this.f.c.E().a((AbstractC1476Wj) null, next.getReference());
                                C0684j2 reference2 = w0A.getReference();
                                if (w0A.E0()) {
                                    enumC1953et = EnumC1953et.e;
                                } else if (w0A.H1()) {
                                    enumC1953et = EnumC1953et.g;
                                } else {
                                    enumC1953et = EnumC1953et.c;
                                }
                                v4 v4Var2 = new v4(c0684j2A3, c0684j2A4, reference2, enumC1953et, this.b.isInterface());
                                this.d.add(v4Var2);
                                w0A2 = com.android.tools.r8.graph.W0.Y1().a(c0684j2A3).a(c0725p4P2).a(v4Var2).a(next.p1() ? next.Z0() : null).b(next.I0()).a(next.I0()).a(next.D1()).a(next.e1()).a();
                                if (!next.f.k()) {
                                }
                                this.c.c(next.getReference(), w0A2.getReference());
                                map3.put(C3894f2.a().c(w0A2.getReference()), w0A2);
                                w0T = w0A2;
                            }
                            final o5 o5Var2 = new o5(this.b, w0T);
                            AbstractC3735i1 abstractC3735i1V2 = this.f.c.v();
                            Consumer consumer3 = new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    this.f$0.a(o5Var2, next, (C3730h1) obj);
                                }
                            };
                            C3730h1 c3730h2 = (C3730h1) abstractC3735i1V2;
                            c3730h2.getClass();
                            consumer3.accept(c3730h2);
                            this.c.c.put(next.getReference(), w0T.getReference());
                            this.c.a(next.getReference(), w0A.getReference(), w0A.E0());
                        }
                    }
                }
                w0T = null;
                if (w0T != null) {
                    if (next.s1()) {
                        y4 y4Var2 = this.c;
                        y4Var2.c.put(next.getReference(), w0T.getReference());
                        y4Var2.c.put(w0T.getReference(), w0T.getReference());
                        y4Var2.d(next.getReference(), w0T.getReference());
                        if (next.R1()) {
                        }
                    } else {
                        if (this.a.e.W()) {
                            x4 x4Var3 = this.f;
                            final C3562h0 c3562h0A3 = x4Var3.g.a(this.b, x4Var3.f, x4Var3.h);
                            w0A = a(next, new Predicate() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return p4.a(predicate, c3562h0A3, (C0684j2) obj);
                                }
                            }, 1, this.f.c.a().c(next.getReference()));
                            w0A.f.D();
                            if (!w0A.a1().z0()) {
                                this.e = true;
                            }
                            c3562h0A3.a(w0A.getReference());
                        } else {
                            w0A = a(next, predicate, 1, next.getReference().h);
                            if (this.f.c.Y().D0().A()) {
                                accessFlags = w0A.getAccessFlags();
                                if (x4.s) {
                                }
                                accessFlags.I();
                                accessFlags.K();
                                accessFlags.C();
                            } else {
                                if (x4.s) {
                                }
                                w0A.f.L();
                                w0A.f.K();
                                w0A.f.A();
                            }
                        }
                        if (w0A.f.N()) {
                            map = map2;
                        } else {
                            map = map3;
                        }
                        map.put(C3894f2.a().c(w0A.getReference()), w0A);
                        a(next, w0A);
                        this.c.b(w0A.getReference());
                        if (w0T == null) {
                            C0684j2 c0684j2A5 = this.f.a.e.a(this.b.d, next.getReference().h, next.getReference().f);
                            C0725p4 c0725p4P3 = next.f.P();
                            c0725p4P3.f0();
                            c0725p4P3.E();
                            c0725p4P3.h0();
                            z = g;
                            if (z) {
                            }
                            C0684j2 c0684j2A6 = this.f.c.E().a((AbstractC1476Wj) null, next.getReference());
                            C0684j2 reference3 = w0A.getReference();
                            if (w0A.E0()) {
                                enumC1953et = EnumC1953et.e;
                            } else if (w0A.H1()) {
                                enumC1953et = EnumC1953et.g;
                            } else {
                                enumC1953et = EnumC1953et.c;
                            }
                            v4 v4Var3 = new v4(c0684j2A5, c0684j2A6, reference3, enumC1953et, this.b.isInterface());
                            this.d.add(v4Var3);
                            w0A2 = com.android.tools.r8.graph.W0.Y1().a(c0684j2A5).a(c0725p4P3).a(v4Var3).a(next.p1() ? next.Z0() : null).b(next.I0()).a(next.I0()).a(next.D1()).a(next.e1()).a();
                            if (!next.f.k()) {
                            }
                            this.c.c(next.getReference(), w0A2.getReference());
                            map3.put(C3894f2.a().c(w0A2.getReference()), w0A2);
                            w0T = w0A2;
                        }
                        final o5 o5Var3 = new o5(this.b, w0T);
                        AbstractC3735i1 abstractC3735i1V3 = this.f.c.v();
                        Consumer consumer4 = new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                this.f$0.a(o5Var3, next, (C3730h1) obj);
                            }
                        };
                        C3730h1 c3730h3 = (C3730h1) abstractC3735i1V3;
                        c3730h3.getClass();
                        consumer4.accept(c3730h3);
                        this.c.c.put(next.getReference(), w0T.getReference());
                        this.c.a(next.getReference(), w0A.getReference(), w0A.E0());
                    }
                } else {
                    if (this.e) {
                        if (!g) {
                            a(AbstractC1699bX.a(map2.values().stream(), map3.values().stream()));
                        }
                        return false;
                    }
                    if (next.s1()) {
                        if (!this.b.x1()) {
                            if (g) {
                            }
                            this.e = true;
                            return false;
                        }
                        com.android.tools.r8.graph.W0 w0A4 = a(next, predicate, 3, next.getReference().h);
                        w0A4.a(next.D1());
                        this.c.c.put(next.getReference(), w0A4.getReference());
                        this.c.a(next.getReference(), w0A4.getReference(), false);
                        map3.put(C3894f2.a().c(w0A4.getReference()), w0A4);
                    } else {
                        if (this.a.e.W()) {
                            x4 x4Var4 = this.f;
                            final C3562h0 c3562h0A4 = x4Var4.g.a(this.b, x4Var4.f, x4Var4.h);
                            w0A = a(next, new Predicate() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return p4.a(predicate, c3562h0A4, (C0684j2) obj);
                                }
                            }, 1, this.f.c.a().c(next.getReference()));
                            w0A.f.D();
                            if (!w0A.a1().z0()) {
                                this.e = true;
                            }
                            c3562h0A4.a(w0A.getReference());
                        } else {
                            w0A = a(next, predicate, 1, next.getReference().h);
                            if (this.f.c.Y().D0().A()) {
                                accessFlags = w0A.getAccessFlags();
                                if (x4.s) {
                                }
                                accessFlags.I();
                                accessFlags.K();
                                accessFlags.C();
                            } else {
                                if (x4.s) {
                                }
                                w0A.f.L();
                                w0A.f.K();
                                w0A.f.A();
                            }
                        }
                        if (w0A.f.N()) {
                            map = map2;
                        } else {
                            map = map3;
                        }
                        map.put(C3894f2.a().c(w0A.getReference()), w0A);
                        a(next, w0A);
                        this.c.b(w0A.getReference());
                        if (w0T == null) {
                            C0684j2 c0684j2A7 = this.f.a.e.a(this.b.d, next.getReference().h, next.getReference().f);
                            C0725p4 c0725p4P4 = next.f.P();
                            c0725p4P4.f0();
                            c0725p4P4.E();
                            c0725p4P4.h0();
                            z = g;
                            if (z) {
                            }
                            C0684j2 c0684j2A8 = this.f.c.E().a((AbstractC1476Wj) null, next.getReference());
                            C0684j2 reference4 = w0A.getReference();
                            if (w0A.E0()) {
                                enumC1953et = EnumC1953et.e;
                            } else if (w0A.H1()) {
                                enumC1953et = EnumC1953et.g;
                            } else {
                                enumC1953et = EnumC1953et.c;
                            }
                            v4 v4Var4 = new v4(c0684j2A7, c0684j2A8, reference4, enumC1953et, this.b.isInterface());
                            this.d.add(v4Var4);
                            w0A2 = com.android.tools.r8.graph.W0.Y1().a(c0684j2A7).a(c0725p4P4).a(v4Var4).a(next.p1() ? next.Z0() : null).b(next.I0()).a(next.I0()).a(next.D1()).a(next.e1()).a();
                            if (!next.f.k()) {
                            }
                            this.c.c(next.getReference(), w0A2.getReference());
                            map3.put(C3894f2.a().c(w0A2.getReference()), w0A2);
                            w0T = w0A2;
                        }
                        final o5 o5Var4 = new o5(this.b, w0T);
                        AbstractC3735i1 abstractC3735i1V4 = this.f.c.v();
                        Consumer consumer5 = new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                this.f$0.a(o5Var4, next, (C3730h1) obj);
                            }
                        };
                        C3730h1 c3730h4 = (C3730h1) abstractC3735i1V4;
                        c3730h4.getClass();
                        consumer5.accept(c3730h4);
                        this.c.c.put(next.getReference(), w0T.getReference());
                        this.c.a(next.getReference(), w0A.getReference(), w0A.E0());
                    }
                }
            } else {
                if (this.e) {
                    if (!g) {
                        a(AbstractC1699bX.a(map2.values().stream(), map3.values().stream()));
                    }
                    return false;
                }
                a(this.b, this.a, map2.values(), map3.values());
                this.b.f(new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a((o5) obj);
                    }
                }, new Predicate() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return p4.a((com.android.tools.r8.graph.W0) obj);
                    }
                });
                final HashSet hashSet2 = new HashSet();
                Iterator<com.android.tools.r8.graph.T0> it3 = this.b.W0().iterator();
                while (it3.hasNext()) {
                    hashSet2.add(it3.next().getReference().f);
                }
                Predicate predicate2 = new Predicate() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return p4.a(hashSet2, (com.android.tools.r8.graph.Y0) obj);
                    }
                };
                com.android.tools.r8.graph.T0[] t0ArrA = a(this.a.w1(), this.b.w1(), predicate2, hashSet2);
                com.android.tools.r8.graph.T0[] t0ArrA2 = a(this.a.N1(), this.b.N1(), predicate2, hashSet2);
                C0767v2[] c0767v2Arr = this.b.g.a;
                C0767v2[] c0767v2Arr2 = this.a.g.a;
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Collections.addAll(linkedHashSet, c0767v2Arr);
                Collections.addAll(linkedHashSet, c0767v2Arr2);
                if (this.a.isInterface()) {
                    linkedHashSet.remove(this.a.d);
                } else {
                    if (!g && this.b.isInterface()) {
                        throw new AssertionError();
                    }
                    this.b.f = this.a.f;
                }
                C0729q2 c0729q3 = this.b;
                if (linkedHashSet.isEmpty()) {
                    c0800x2 = C0800x2.s0();
                } else {
                    c0800x2 = new C0800x2((C0767v2[]) linkedHashSet.toArray(C0767v2.g));
                }
                c0729q3.g = c0800x2;
                Collection collectionValues = map2.values();
                final com.android.tools.r8.ir.optimize.info.r rVar = this.f.k;
                Objects.requireNonNull(rVar);
                collectionValues.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        rVar.f((com.android.tools.r8.graph.W0) obj);
                    }
                });
                Collection collectionValues2 = map3.values();
                final com.android.tools.r8.ir.optimize.info.r rVar2 = this.f.k;
                Objects.requireNonNull(rVar2);
                collectionValues2.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        rVar2.f((com.android.tools.r8.graph.W0) obj);
                    }
                });
                for (int i = 0; i < this.a.w1().size(); i++) {
                    this.f.k.c(t0ArrA[i]);
                }
                for (int i2 = 0; i2 < this.a.N1().size(); i2++) {
                    this.f.k.c(t0ArrA2[i2]);
                }
                this.b.a(map2.values());
                this.b.b(map3.values());
                this.b.a(t0ArrA);
                this.b.b(t0ArrA2);
                this.a.Y().b();
                this.a.Y().c();
                this.a.O0();
                this.a.T0();
                this.f.m.a(this.a.d, this.b.d);
                if (this.a.isInterface()) {
                    this.f.n.a(this.a.d, this.b.d);
                }
                boolean z2 = g;
                if (!z2 && this.e) {
                    throw new AssertionError();
                }
                if (!z2) {
                    C0702m c0702m = this.f.c;
                    C0791w3.a aVarA = C0791w3.b(c0702m, C0738r3.a(c0702m, this.b)).a(this.b);
                    aVarA.getClass();
                    if (aVarA != C0791w3.a.VALID) {
                        throw new AssertionError();
                    }
                }
                return true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, Map map, Map map2, C0684j2 c0684j2) {
        C2173hh c2173hhC = C3894f2.a().c(c0684j2);
        return (set.contains(c2173hhC) || map.containsKey(c2173hhC) || map2.containsKey(c2173hhC)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Predicate predicate, Map map, o5 o5Var) {
        C0684j2 c0684j2A;
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        if (w0E.C1()) {
            if (!g && !w0E.C1()) {
                throw new AssertionError();
            }
            C0767v2 c0767v2J0 = w0E.J0();
            int i = 1;
            do {
                c0684j2A = this.f.a.e.a(this.b.d, w0E.getReference().h, a(i, c0767v2J0, "$r8$constructor"));
                i++;
            } while (!predicate.test(c0684j2A));
            com.android.tools.r8.graph.W0 w0A = w0E.a(c0684j2A);
            w0A.g1().I();
            this.c.c.put(w0E.getReference(), w0A.getReference());
            this.c.a(w0E.getReference(), w0A.getReference(), false);
            w0A.f.j0();
            if (!x4.s && w0A.f.W()) {
                throw new AssertionError();
            }
            w0A.f.L();
            w0A.f.K();
            w0A.f.A();
            map.put(C3894f2.a().c(w0A.getReference()), w0A);
            this.c.b(w0A.getReference());
            return;
        }
        final com.android.tools.r8.graph.W0 w0A2 = a(w0E, predicate, w0E.u1() ? 3 : 2, w0E.getReference().h);
        map.put(C3894f2.a().c(w0A2.getReference()), w0A2);
        this.c.c.put(o5Var.getReference(), w0A2.getReference());
        this.c.a(o5Var.getReference(), w0A2.getReference(), false);
        this.c.b(w0A2.getReference());
        if (w0E.B1() && w0E.O0() && AbstractC0639e.a(o5Var, this.a, this.b, this.f.c).d()) {
            this.c.a(o5Var.getReference(), new z4() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda7
                @Override // com.android.tools.r8.shaking.z4
                public final KE a(com.android.tools.r8.graph.proto.k kVar) {
                    return p4.a(w0A2, kVar);
                }
            }, this.b.getType());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ KE a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.proto.k kVar) {
        return new KE(w0.getReference(), null, EnumC1953et.c, kVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Predicate predicate, C3562h0 c3562h0, C0684j2 c0684j2) {
        return predicate.test(c0684j2) && !c3562h0.a(C3894f2.a().c(c0684j2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(o5 o5Var, final com.android.tools.r8.graph.W0 w0, final C3730h1 c3730h1) {
        c3730h1.a(o5Var, new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c3730h1, w0, (C3754l1) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3730h1 c3730h1, com.android.tools.r8.graph.W0 w0, C3754l1 c3754l1) {
        c3754l1.a(c3730h1.a(this.a, w0).w());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(com.android.tools.r8.graph.W0 w0) {
        com.android.tools.r8.graph.W wA1 = w0.a1();
        wA1.getClass();
        return wA1 instanceof com.android.tools.r8.graph.Z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(o5 o5Var) {
        C0702m c0702m = this.f.c;
        int i = com.android.tools.r8.graph.Z.f;
        com.android.tools.r8.graph.Z.a(c0702m, o5Var.a().n1(), o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, com.android.tools.r8.graph.Y0 y0) {
        return !set.contains(y0.f);
    }

    private void a(C0729q2 c0729q2, C0729q2 c0729q3, Collection collection, Collection collection2) {
        C0706m3.b bVarA1 = c0729q2.a1();
        if (bVarA1.a()) {
            collection.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((com.android.tools.r8.graph.W0) obj).E();
                }
            });
            collection2.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((com.android.tools.r8.graph.W0) obj).E();
                }
            });
            c0729q3.W0().forEach(new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((com.android.tools.r8.graph.T0) obj).E();
                }
            });
            return;
        }
        final C0817z3 c0817z3A = a(c0729q2, c0729q3);
        if (c0817z3A == null) {
            c0729q2.L0();
            c0729q2.L1().forEach(new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((com.android.tools.r8.graph.U0) obj).E();
                }
            });
            return;
        }
        C0706m3.b bVarA2 = c0729q3.a1();
        if (!bVarA2.a()) {
            bVarA2 = bVarA2.a(c0817z3A);
        }
        C0718o3 c0718o3C = C0706m3.b.c();
        c0718o3C.a(bVarA1.d());
        if (!c0729q3.isInterface()) {
            if (bVarA2.b()) {
                c0718o3C.b(bVarA2.g());
            } else {
                c0718o3C.b(new C0706m3.c(c0729q3.f));
            }
        } else {
            c0718o3C.b(bVarA1.g());
        }
        HashSet hashSet = new HashSet();
        if (c0729q3.isInterface()) {
            hashSet.add(c0729q3.d);
        }
        for (C0706m3.c cVar : bVarA1.h()) {
            if (hashSet.add(cVar.q())) {
                c0718o3C.a(cVar);
            }
        }
        if (bVarA2.b()) {
            for (C0706m3.c cVar2 : bVarA2.h()) {
                if (!hashSet.contains(cVar2.q())) {
                    c0718o3C.a(cVar2);
                }
            }
        } else {
            for (C0767v2 c0767v2 : c0729q3.g) {
                if (!hashSet.contains(c0767v2)) {
                    c0718o3C.a(new C0706m3.c(c0767v2));
                }
            }
        }
        c0729q2.a(c0718o3C.a());
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                p4.a(c0817z3A, (com.android.tools.r8.graph.W0) obj);
            }
        };
        Collection[] collectionArr = {collection, collection2};
        for (int i = 0; i < 2; i++) {
            collectionArr[i].forEach(consumer);
        }
        c0729q3.c(new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                p4.a(c0817z3A, (com.android.tools.r8.graph.T0) obj);
            }
        }, CM.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0817z3 c0817z3, com.android.tools.r8.graph.W0 w0) {
        C0706m3.g gVarE1 = w0.e1();
        if (gVarE1.a()) {
            return;
        }
        w0.a(c0817z3.f(gVarE1.c()).a(gVarE1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0817z3 c0817z3, com.android.tools.r8.graph.T0 t0) {
        if (t0.T0().a()) {
            return;
        }
        t0.a(c0817z3.b(t0.T0()));
    }

    private C0817z3 a(C0729q2 c0729q2, C0729q2 c0729q3) {
        boolean z = g;
        if (!z && !c0729q2.a1().b()) {
            throw new AssertionError();
        }
        List listA = c0729q2.a1().a(c0729q3.d);
        if (listA == null) {
            if (z) {
                return null;
            }
            throw new AssertionError("Type should be present in generic signature");
        }
        HashMap map = new HashMap();
        List listD = c0729q3.a1().d();
        if (listA.size() != listD.size()) {
            if (!listA.isEmpty()) {
                if (z) {
                    return null;
                }
                throw new AssertionError("Invalid argument count to formals");
            }
        } else {
            for (int i = 0; i < listD.size(); i++) {
                map.put(((C0706m3.f) listD.get(i)).a(), (C0706m3.e) listA.get(i));
            }
        }
        return C0817z3.a(this.f.c, C0738r3.a.a().a(map), new BiPredicate() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda9
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return p4.a((C0767v2) obj, (C0767v2) obj2);
            }
        }, new Predicate() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return p4.a((C0767v2) obj);
            }
        });
    }

    private void a(Stream stream) {
        stream.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                p4.b((com.android.tools.r8.graph.W0) obj);
            }
        });
        this.a.f(new Consumer() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                p4.c((com.android.tools.r8.graph.W0) obj);
            }
        });
        if (!g && !AbstractC1699bX.a(AbstractC1699bX.a(this.a.M1()), AbstractC1699bX.a(this.b.M1())).allMatch(new Predicate() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return p4.d((com.android.tools.r8.graph.W0) obj);
            }
        })) {
            throw new AssertionError();
        }
    }

    private void a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.W0 w1) {
        C0684j2 reference = w0.getReference();
        final C0684j2 reference2 = w1.getReference();
        final EnumC1953et enumC1953et = w1.H1() ? EnumC1953et.g : EnumC1953et.c;
        if (this.a.e.W()) {
            this.c.a(reference, new z4() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda18
                @Override // com.android.tools.r8.shaking.z4
                public final KE a(com.android.tools.r8.graph.proto.k kVar) {
                    return p4.a(reference2, kVar);
                }
            }, this.b.d);
            return;
        }
        C0729q2 c0729q2A = this.b;
        while (c0729q2A != null && c0729q2A.e0()) {
            C0722p1 c0722p1A = this.f.c.a();
            reference.getClass();
            C0684j2 c0684j2B = reference.b(c0729q2A.getType(), c0722p1A);
            if (c0729q2A.d(c0684j2B) == null) {
                x4 x4Var = this.f;
                if (x4Var.b.a(c0684j2B, c0729q2A, x4Var.c) == null) {
                    return;
                }
            }
            this.c.a(c0684j2B, new z4() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda19
                @Override // com.android.tools.r8.shaking.z4
                public final KE a(com.android.tools.r8.graph.proto.k kVar) {
                    return p4.a(reference2, enumC1953et, kVar);
                }
            }, this.b.d);
            Iterator it = this.f.m.a(c0729q2A.d).iterator();
            while (it.hasNext()) {
                C0684j2 c0684j2B2 = reference.b((C0767v2) it.next(), this.f.c.a());
                if (!this.f.p.a(c0729q2A, c0684j2B2)) {
                    x4 x4Var2 = this.f;
                    if (x4Var2.b.a(c0684j2B, c0729q2A, x4Var2.c) != null) {
                    }
                }
                this.c.a(c0684j2B2, new z4() { // from class: com.android.tools.r8.shaking.p4$$ExternalSyntheticLambda20
                    @Override // com.android.tools.r8.shaking.z4
                    public final KE a(com.android.tools.r8.graph.proto.k kVar) {
                        return p4.b(reference2, enumC1953et, kVar);
                    }
                }, this.b.d);
            }
            C0767v2 c0767v2 = c0729q2A.f;
            c0729q2A = c0767v2 != null ? C0729q2.a(this.f.b.c(c0767v2)) : null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ KE a(C0684j2 c0684j2, com.android.tools.r8.graph.proto.k kVar) {
        return new KE(c0684j2, null, EnumC1953et.e, kVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ KE a(C0684j2 c0684j2, EnumC1953et enumC1953et, com.android.tools.r8.graph.proto.k kVar) {
        return new KE(c0684j2, null, enumC1953et, kVar);
    }

    private com.android.tools.r8.graph.T0[] a(List list, List list2, Predicate predicate, HashSet hashSet) {
        com.android.tools.r8.graph.Y0 y0A;
        com.android.tools.r8.graph.T0[] t0Arr = new com.android.tools.r8.graph.T0[list2.size() + list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            com.android.tools.r8.graph.T0 t0 = (com.android.tools.r8.graph.T0) it.next();
            C0759u2 c0759u2 = t0.getReference().f;
            C0767v2 c0767v2J0 = t0.J0();
            com.android.tools.r8.graph.Y0 y0A2 = this.f.a.e.a(this.b.d, t0.getReference().h, c0759u2);
            if (!predicate.test(y0A2)) {
                int i2 = 1;
                do {
                    y0A = this.f.a.e.a(this.b.d, t0.getReference().h, a(i2, c0767v2J0, c0759u2.r0()));
                    i2++;
                } while (!predicate.test(y0A));
                y0A2 = y0A;
            }
            com.android.tools.r8.graph.T0 t0A = t0.a(this.f.c, y0A2, C3912j0.b());
            hashSet.add(t0A.getReference().f);
            this.c.b.put(t0.getReference(), t0A.getReference());
            t0Arr[i] = t0A;
            i++;
        }
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            t0Arr[i] = (com.android.tools.r8.graph.T0) it2.next();
            i++;
        }
        return t0Arr;
    }

    private C0759u2 a(int i, C0767v2 c0767v2, String str) {
        String str2 = str + "$" + c0767v2.r0().replace('.', '$');
        if (i > 1) {
            str2 = str2 + i;
        }
        return this.f.a.e.c(str2);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0069  */
    private com.android.tools.r8.graph.W0 a(com.android.tools.r8.graph.W0 w0, Predicate predicate, int i, C0737r2 c0737r2) {
        int i2;
        C0684j2 c0684j2A;
        C0684j2 c0684j2A2;
        boolean z = g;
        if (!z && w0.f.Y() && i != 3) {
            throw new AssertionError();
        }
        C0759u2 c0759u2 = w0.getReference().f;
        C0767v2 c0767v2J0 = w0.J0();
        if (i != 0) {
            int i3 = i - 1;
            if (i3 == 0) {
                i2 = 1;
                do {
                    c0684j2A = this.f.a.e.a(this.b.d, c0737r2, a(i2, c0767v2J0, c0759u2.r0()));
                    i2++;
                } while (!predicate.test(c0684j2A));
                c0684j2A2 = c0684j2A;
            } else if (i3 == 1) {
                c0684j2A2 = this.f.a.e.a(this.b.d, c0737r2, c0759u2);
                if (!predicate.test(c0684j2A2)) {
                    i2 = 1;
                    do {
                        c0684j2A = this.f.a.e.a(this.b.d, c0737r2, a(i2, c0767v2J0, c0759u2.r0()));
                        i2++;
                    } while (!predicate.test(c0684j2A));
                    c0684j2A2 = c0684j2A;
                }
            } else if (i3 == 2) {
                c0684j2A2 = this.f.a.e.a(this.b.d, c0737r2, c0759u2);
                if (!z && !predicate.test(c0684j2A2)) {
                    throw new AssertionError();
                }
            } else {
                throw new C3405y10();
            }
            return w0.a(c0684j2A2);
        }
        throw null;
    }
}
