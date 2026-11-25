package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.B2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0757u1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0816z2;
import com.android.tools.r8.graph.I2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C0978Fc;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2477lf;
import com.android.tools.r8.internal.C2561ml;
import com.android.tools.r8.internal.C3012sl;
import com.android.tools.r8.internal.C3141ua;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.C3446yc;
import com.android.tools.r8.internal.EnumC3070ta;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.QW;
import com.android.tools.r8.internal.RW;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3738j;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.v, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3590v {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C3012sl b;
    private final C0722p1 c;
    private C3588u d = null;

    public C3590v(C0702m c0702m, C3012sl c3012sl) {
        this.a = c0702m;
        this.b = c3012sl;
        this.c = c0702m.a();
    }

    public final C3585t a(C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.n nVar) {
        com.android.tools.r8.graph.T0 t0S;
        if (this.a.Y().W0) {
            return C3585t.b;
        }
        final o5 o5VarH = c2409kl.h();
        if (o5VarH.a().f(this.a)) {
            return C3585t.b;
        }
        if (!o5VarH.e().u1()) {
            return C3585t.b;
        }
        final Set setC = OU.c();
        IdentityHashMap identityHashMap = (IdentityHashMap) a(o5VarH, c2409kl, setC);
        if (identityHashMap.isEmpty()) {
            if (e || setC.isEmpty()) {
                return C3585t.b;
            }
            throw new AssertionError();
        }
        final IdentityHashMap identityHashMap2 = new IdentityHashMap();
        identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(setC, identityHashMap2, o5VarH, (com.android.tools.r8.graph.T0) obj, (RW) obj2);
            }
        });
        if (!setC.isEmpty()) {
            final Set setC2 = OU.c();
            C2561ml c2561ml = new C2561ml(c2409kl);
            while (c2561ml.hasNext()) {
                AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
                if (abstractC2412ko.c() && setC.contains(abstractC2412ko.G0())) {
                    S10 s10Value = abstractC2412ko.G0().value();
                    c2561ml.s();
                    if (s10Value.Z() <= 0) {
                        if (s10Value.K()) {
                            setC2.add(s10Value.b);
                        } else if (!s10Value.i() && s10Value.b.f2()) {
                            setC2.add(s10Value.b);
                        }
                    }
                }
            }
            if (setC2.size() > 0) {
                C2561ml c2561ml2 = new C2561ml(c2409kl);
                Predicate predicate = new Predicate() { // from class: com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return setC2.contains((AbstractC2412ko) obj);
                    }
                };
                while (c2561ml2.hasNext()) {
                    if (predicate.test(c2561ml2.next())) {
                        c2561ml2.remove();
                    }
                }
            }
        }
        if (!this.a.q() || !this.b.b()) {
            identityHashMap2.forEach(new u$$ExternalSyntheticLambda0());
        } else if (this.a.f().i()) {
            final C3733i c3733i = (C3733i) this.a.i0().f();
            Stream map = identityHashMap.values().stream().filter(new Predicate() { // from class: com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return setC.contains((RW) obj);
                }
            }).map(new Function() { // from class: com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((RW) obj).getField();
                }
            });
            Objects.requireNonNull(c3733i);
            final Set set = (Set) map.map(new v$$ExternalSyntheticLambda9(c3733i)).map(new v$$ExternalSyntheticLambda10()).filter(new Predicate() { // from class: com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda11
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return c3733i.f((com.android.tools.r8.graph.T0) obj);
                }
            }).map(new Function() { // from class: com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda12
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3590v.a((com.android.tools.r8.graph.T0) obj);
                }
            }).collect(Collectors.toSet());
            for (AbstractC2412ko abstractC2412ko2 : c2409kl.r()) {
                if (abstractC2412ko2.c() && (t0S = c3733i.c(abstractC2412ko2.G0().getField()).s()) != null) {
                    set.remove(t0S.getReference());
                }
            }
            nVar.a(new Consumer() { // from class: com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3590v.a(set, (C3738j) obj);
                }
            });
            synchronized (this) {
                C3588u c3588u = this.d;
                if (c3588u == null) {
                    this.d = new C3588u(identityHashMap2);
                    this.b.a(new InterfaceC3880d() { // from class: com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda2
                        @Override // com.android.tools.r8.utils.InterfaceC3880d
                        public final void a() {
                            this.f$0.a();
                        }
                    });
                } else {
                    c3588u.a(identityHashMap2);
                }
            }
        } else if (!e) {
            throw new AssertionError();
        }
        return new C3585t(identityHashMap2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, Map map, o5 o5Var, com.android.tools.r8.graph.T0 t0, RW rw) {
        EnumC3070ta enumC3070ta;
        C0767v2 c0767v2 = t0.getReference().h;
        S10 s10M = rw.value().m();
        if (set.contains(rw)) {
            if (c0767v2 == this.c.X1) {
                C0767v2 c0767v2T = o5Var.t();
                com.android.tools.r8.utils.structural.k jVar = null;
                if (!s10M.i()) {
                    if (s10M.L()) {
                        if (s10M.J()) {
                            if (!e && !s10M.V()) {
                                throw new AssertionError();
                            }
                            jVar = I2.b;
                        } else if (s10M.K()) {
                            jVar = new B2.j(s10M.n().F().J2());
                        } else if (s10M.M()) {
                            C2477lf c2477lfJ = s10M.n().J();
                            if (!e && c2477lfJ.K2().e()) {
                                throw new AssertionError();
                            }
                            jVar = new C0816z2(c2477lfJ.J2(), c2477lfJ.K2());
                        } else if (!e) {
                            throw new AssertionError();
                        }
                    } else {
                        C0684j2 c0684j2S2 = s10M.m().b.i0().S2();
                        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2T);
                        if (abstractC0727q0C == null) {
                            if (!e) {
                                throw new AssertionError();
                            }
                        } else if (this.a.q() && ((C3733i) this.a.i0().f()).a((com.android.tools.r8.graph.O0) abstractC0727q0C)) {
                            C0757u1 c0757u1 = this.c.p4;
                            if (c0684j2S2 == c0757u1.e) {
                                jVar = new C0816z2(c0767v2T, C3141ua.a(EnumC3070ta.b));
                            } else if (c0684j2S2 == c0757u1.f) {
                                jVar = new C0816z2(c0767v2T, C3141ua.a(EnumC3070ta.d));
                            } else if (c0684j2S2 == c0757u1.g) {
                                jVar = new C0816z2(c0767v2T, C3141ua.a(EnumC3070ta.e));
                            } else if (!e) {
                                throw new AssertionError();
                            }
                        } else {
                            C0757u1 c0757u2 = this.c.p4;
                            if (c0684j2S2 == c0757u2.e) {
                                enumC3070ta = EnumC3070ta.b;
                            } else if (c0684j2S2 == c0757u2.f) {
                                enumC3070ta = EnumC3070ta.d;
                            } else {
                                enumC3070ta = c0684j2S2 == c0757u2.g ? EnumC3070ta.e : null;
                            }
                            if (enumC3070ta != null) {
                                jVar = new B2.j(enumC3070ta.a(c0767v2T.b1(), abstractC0727q0C, this.c, 0));
                            } else if (!e) {
                                throw new AssertionError();
                            }
                        }
                    }
                }
                map.put(t0, jVar);
                return;
            }
            if (!c0767v2.O0() && !c0767v2.M0()) {
                C0978Fc c0978FcE = s10M.n().E();
                C0722p1 c0722p1 = this.c;
                if (c0767v2 == c0722p1.v1) {
                    map.put(t0, B2.b.a(c0978FcE.K2()));
                    return;
                }
                if (c0767v2 == c0722p1.w1) {
                    map.put(t0, B2.c.a((byte) c0978FcE.N2()));
                    return;
                }
                if (c0767v2 == c0722p1.C1) {
                    map.put(t0, B2.i.a((short) c0978FcE.N2()));
                    return;
                }
                if (c0767v2 == c0722p1.A1) {
                    map.put(t0, B2.g.j(c0978FcE.N2()));
                    return;
                }
                if (c0767v2 == c0722p1.B1) {
                    map.put(t0, B2.h.a(c0978FcE.O2()));
                    return;
                }
                if (c0767v2 == c0722p1.z1) {
                    map.put(t0, B2.f.a(c0978FcE.M2()));
                    return;
                } else if (c0767v2 == c0722p1.y1) {
                    map.put(t0, B2.e.a(c0978FcE.L2()));
                    return;
                } else {
                    if (c0767v2 == c0722p1.x1) {
                        map.put(t0, B2.d.a((char) c0978FcE.N2()));
                        return;
                    }
                    throw new C3405y10("Unexpected field type " + c0767v2 + ".");
                }
            }
            if (s10M.V()) {
                map.put(t0, I2.b);
                return;
            }
            throw new C3405y10("Unexpected default value for field type " + c0767v2 + ".");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Y0 a(com.android.tools.r8.graph.T0 t0) {
        return t0.getReference();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, final C3738j c3738j) {
        Objects.requireNonNull(c3738j);
        set.forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c3738j.a((Y0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() {
        this.d.a();
        this.d = null;
    }

    /* JADX WARN: Code duplicated, block: B:70:0x0167 A[Catch: all -> 0x0209, TryCatch #0 {all -> 0x0209, blocks: (B:3:0x001a, B:4:0x001e, B:6:0x0024, B:8:0x002f, B:9:0x003a, B:11:0x0040, B:13:0x004c, B:15:0x0058, B:18:0x0060, B:20:0x0064, B:22:0x0076, B:24:0x007d, B:27:0x0085, B:29:0x008b, B:31:0x009b, B:34:0x00a3, B:36:0x00bd, B:39:0x00c5, B:41:0x00cb, B:44:0x00df, B:46:0x00e5, B:48:0x00ed, B:50:0x00f3, B:52:0x00ff, B:53:0x010d, B:55:0x0115, B:78:0x0190, B:59:0x0122, B:61:0x012e, B:62:0x013c, B:70:0x0167, B:72:0x0173, B:73:0x0180, B:75:0x0186, B:77:0x018c, B:65:0x014f, B:68:0x015f, B:80:0x01a2, B:84:0x01ac, B:86:0x01b4, B:89:0x01bc, B:92:0x01c7, B:94:0x01cd, B:96:0x01d3, B:98:0x01dd, B:102:0x01e7, B:104:0x01f1), top: B:111:0x001a }] */
    /* JADX WARN: Code duplicated, block: B:72:0x0173 A[Catch: all -> 0x0209, TryCatch #0 {all -> 0x0209, blocks: (B:3:0x001a, B:4:0x001e, B:6:0x0024, B:8:0x002f, B:9:0x003a, B:11:0x0040, B:13:0x004c, B:15:0x0058, B:18:0x0060, B:20:0x0064, B:22:0x0076, B:24:0x007d, B:27:0x0085, B:29:0x008b, B:31:0x009b, B:34:0x00a3, B:36:0x00bd, B:39:0x00c5, B:41:0x00cb, B:44:0x00df, B:46:0x00e5, B:48:0x00ed, B:50:0x00f3, B:52:0x00ff, B:53:0x010d, B:55:0x0115, B:78:0x0190, B:59:0x0122, B:61:0x012e, B:62:0x013c, B:70:0x0167, B:72:0x0173, B:73:0x0180, B:75:0x0186, B:77:0x018c, B:65:0x014f, B:68:0x015f, B:80:0x01a2, B:84:0x01ac, B:86:0x01b4, B:89:0x01bc, B:92:0x01c7, B:94:0x01cd, B:96:0x01d3, B:98:0x01dd, B:102:0x01e7, B:104:0x01f1), top: B:111:0x001a }] */
    /* JADX WARN: Code duplicated, block: B:73:0x0180 A[Catch: all -> 0x0209, TryCatch #0 {all -> 0x0209, blocks: (B:3:0x001a, B:4:0x001e, B:6:0x0024, B:8:0x002f, B:9:0x003a, B:11:0x0040, B:13:0x004c, B:15:0x0058, B:18:0x0060, B:20:0x0064, B:22:0x0076, B:24:0x007d, B:27:0x0085, B:29:0x008b, B:31:0x009b, B:34:0x00a3, B:36:0x00bd, B:39:0x00c5, B:41:0x00cb, B:44:0x00df, B:46:0x00e5, B:48:0x00ed, B:50:0x00f3, B:52:0x00ff, B:53:0x010d, B:55:0x0115, B:78:0x0190, B:59:0x0122, B:61:0x012e, B:62:0x013c, B:70:0x0167, B:72:0x0173, B:73:0x0180, B:75:0x0186, B:77:0x018c, B:65:0x014f, B:68:0x015f, B:80:0x01a2, B:84:0x01ac, B:86:0x01b4, B:89:0x01bc, B:92:0x01c7, B:94:0x01cd, B:96:0x01d3, B:98:0x01dd, B:102:0x01e7, B:104:0x01f1), top: B:111:0x001a }] */
    private Map a(o5 o5Var, C2409kl c2409kl, Set set) {
        Set set2;
        boolean zA;
        IdentityHashMap identityHashMap = new IdentityHashMap();
        IdentityHashMap identityHashMap2 = new IdentityHashMap();
        Set setC = OU.c();
        int iA = c2409kl.A();
        try {
            C3332x4 c3332x4J = c2409kl.j();
            while (!c3332x4J.c(iA) && c3332x4J.y().size() <= 1) {
                c3332x4J.e(iA);
                for (AbstractC2412ko abstractC2412ko : c3332x4J.p()) {
                    if (abstractC2412ko.r1()) {
                        if (abstractC2412ko.v().b(this.a, o5Var)) {
                            Map mapA = a(identityHashMap, identityHashMap2);
                            c2409kl.b(iA);
                            return mapA;
                        }
                    } else {
                        if (abstractC2412ko instanceof QW) {
                            com.android.tools.r8.graph.T0 t0A = o5Var.a().a(abstractC2412ko.F0().getField());
                            if (t0A != null) {
                                setC.add(t0A);
                            } else {
                                Map mapA2 = a(identityHashMap, identityHashMap2);
                                c2409kl.b(iA);
                                return mapA2;
                            }
                        } else {
                            if (abstractC2412ko.c()) {
                                RW rwG0 = abstractC2412ko.G0();
                                if (rwG0.getField().e != o5Var.t()) {
                                    Map mapA3 = a(identityHashMap, identityHashMap2);
                                    c2409kl.b(iA);
                                    return mapA3;
                                }
                                Y0 field = rwG0.getField();
                                com.android.tools.r8.graph.T0 t0A2 = o5Var.a().a(field);
                                S10 s10M = rwG0.value().m();
                                AbstractC3472z00 abstractC3472z00W = s10M.w();
                                if (t0A2 != null) {
                                    if (!setC.contains(t0A2) && (!s10M.M() || !s10M.n().J().K2().e())) {
                                        if (s10M.L()) {
                                            if (field.h.X0() && s10M.V()) {
                                                identityHashMap.put(t0A2, rwG0);
                                                set.add(rwG0);
                                                if (identityHashMap2.containsKey(field)) {
                                                    set.addAll((Collection) identityHashMap2.get(field));
                                                    identityHashMap2.remove(field);
                                                }
                                            } else if (field.h.W0() || field.h == this.c.X1) {
                                                identityHashMap.put(t0A2, rwG0);
                                                set.add(rwG0);
                                                if (identityHashMap2.containsKey(field)) {
                                                    set.addAll((Collection) identityHashMap2.get(field));
                                                    identityHashMap2.remove(field);
                                                }
                                            } else {
                                                set2 = setC;
                                            }
                                        } else {
                                            C0729q2 c0729q2A = o5Var.a();
                                            set2 = setC;
                                            if (rwG0.getField().h == this.c.X1) {
                                                S10 s10M2 = rwG0.value().m();
                                                if (!s10M2.i()) {
                                                    zA = a(c0729q2A, s10M2.b);
                                                }
                                                if (zA) {
                                                    identityHashMap.put(t0A2, rwG0);
                                                    set.add(rwG0);
                                                    if (identityHashMap2.containsKey(field)) {
                                                        set.addAll((Collection) identityHashMap2.get(field));
                                                        identityHashMap2.remove(field);
                                                    }
                                                } else if (!abstractC3472z00W.I() && abstractC3472z00W.x()) {
                                                    identityHashMap.put(t0A2, rwG0);
                                                }
                                            }
                                            zA = false;
                                            if (zA) {
                                                identityHashMap.put(t0A2, rwG0);
                                                set.add(rwG0);
                                                if (identityHashMap2.containsKey(field)) {
                                                    set.addAll((Collection) identityHashMap2.get(field));
                                                    identityHashMap2.remove(field);
                                                }
                                            } else if (!abstractC3472z00W.I()) {
                                            }
                                        }
                                        ((Set) identityHashMap2.computeIfAbsent(field, new Function() { // from class: com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return C3590v.a((Y0) obj);
                                            }
                                        })).add(rwG0);
                                    }
                                } else {
                                    Map mapA4 = a(identityHashMap, identityHashMap2);
                                    c2409kl.b(iA);
                                    return mapA4;
                                }
                            } else {
                                set2 = setC;
                                if (abstractC2412ko.c(this.a, o5Var)) {
                                    Map mapA5 = a(identityHashMap, identityHashMap2);
                                    c2409kl.b(iA);
                                    return mapA5;
                                }
                                if (!a(o5Var.a(), abstractC2412ko) && abstractC2412ko.U1() && abstractC2412ko.d1() && abstractC2412ko.b().B()) {
                                    Map mapA6 = a(identityHashMap, identityHashMap2);
                                    c2409kl.b(iA);
                                    return mapA6;
                                }
                            }
                            setC = set2;
                        }
                        set2 = setC;
                        setC = set2;
                    }
                }
                Set set3 = setC;
                if (c3332x4J.m().M1()) {
                    c3332x4J = c3332x4J.m().P().K2();
                }
                setC = set3;
            }
            c2409kl.b(iA);
            return a(identityHashMap, identityHashMap2);
        } catch (Throwable th) {
            c2409kl.b(iA);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(Y0 y0) {
        return OU.c();
    }

    private static Map a(final IdentityHashMap identityHashMap, IdentityHashMap identityHashMap2) {
        identityHashMap2.keySet().forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.v$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                identityHashMap.remove((Y0) obj);
            }
        });
        return identityHashMap;
    }

    private boolean a(C0729q2 c0729q2, AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.f2()) {
            return false;
        }
        C2115gt c2115gtI0 = abstractC2412ko.i0();
        if (!this.c.p4.n.contains(c2115gtI0.S2())) {
            return false;
        }
        S10 s10 = (S10) ((ArrayList) c2115gtI0.h1()).get(0);
        if (s10.i()) {
            return false;
        }
        AbstractC2412ko abstractC2412ko2 = s10.b;
        abstractC2412ko2.getClass();
        return (abstractC2412ko2 instanceof C3446yc) && s10.b.A().K2() == c0729q2.d;
    }
}
