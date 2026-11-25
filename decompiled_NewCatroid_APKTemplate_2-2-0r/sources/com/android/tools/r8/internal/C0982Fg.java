package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0757u1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0798x1;
import com.android.tools.r8.shaking.C3730h1;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.StringDiagnostic;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0982Fg extends AbstractC0922Dg {
    static final /* synthetic */ boolean l = true;
    private final C0702m a;
    private final C0722p1 b;
    private C1155Lg c;
    private final Set d = OU.a();
    private final ConcurrentHashMap e = new ConcurrentHashMap();
    private C1749cD f;
    private C1675bD g;
    private final AbstractC0735r0 h;
    private C1346Sg i;
    private final boolean j;
    private final ConcurrentHashMap k;

    C0982Fg(C0702m c0702m) {
        this.a = c0702m;
        C0722p1 c0722p1A = c0702m.a();
        this.b = c0722p1A;
        if (c0702m.Y().n1.N) {
            this.j = true;
            this.k = new ConcurrentHashMap();
        } else {
            this.j = false;
            this.k = null;
        }
        if (!l && c0702m.Y().W0) {
            throw new AssertionError();
        }
        this.h = ((C3733i) c0702m.f()).c(c0722p1A.r4.b).c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ZC a(ZC zc, AbstractC1476Wj abstractC1476Wj) {
        return zc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Set set, C0767v2 c0767v2) {
        C0729q2 c0729q2A = a(c0767v2);
        if (c0729q2A != null) {
            set.add(c0729q2A.getType());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int c(C0767v2 c0767v2) {
        return ((List) this.k.get(c0767v2)).size();
    }

    private C3226vg e() {
        final C1946em c1946emE = AbstractC2178hm.e();
        this.c.a(new BiConsumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c1946emE, (C0729q2) obj, (Set) obj2);
            }
        });
        this.e.clear();
        return new C3226vg(c1946emE.a());
    }

    private void f() {
        this.c.a(new Consumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        });
    }

    private void i() {
        if (!l && !this.j) {
            throw new AssertionError();
        }
        com.android.tools.r8.utils.O2 o2A0 = this.a.a0();
        AbstractC1048Hm abstractC1048HmC = this.c.c();
        o2A0.info(new StringDiagnostic("Unboxed " + abstractC1048HmC.size() + " enums: " + Arrays.toString(abstractC1048HmC.toArray())));
        final StringBuilder sbAppend = new StringBuilder("Unable to unbox ").append(this.k.size()).append(" enums.").append(System.lineSeparator()).append(System.lineSeparator());
        TreeMap treeMap = new TreeMap(Comparator.comparingInt(new ToIntFunction() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda8
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return this.f$0.c((C0767v2) obj);
            }
        }).thenComparing(Function.identity()));
        treeMap.putAll(this.k);
        ArrayList arrayList = new ArrayList();
        Iterator it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            List list = (List) entry.getValue();
            if (list.size() > 1) {
                break;
            } else if (list.get(0) == AbstractC3287wQ.c) {
                arrayList.add((C0767v2) entry.getKey());
                it.remove();
            }
        }
        if (!arrayList.isEmpty()) {
            sbAppend.append("Pinned: ").append(Arrays.toString(arrayList.toArray()));
        }
        treeMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0982Fg.a(sbAppend, (C0767v2) obj, (List) obj2);
            }
        });
        sbAppend.append(System.lineSeparator());
        final PI pi = new PI();
        this.k.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda10
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0982Fg.a(pi, (C0767v2) obj, (List) obj2);
            }
        });
        ArrayList arrayList2 = new ArrayList(pi.keySet());
        arrayList2.sort(new Comparator() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda12
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return C0982Fg.a(pi, obj, obj2);
            }
        });
        arrayList2.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0982Fg.a(sbAppend, pi, obj);
            }
        });
        o2A0.info(new StringDiagnostic(sbAppend.toString()));
    }

    @Override // com.android.tools.r8.internal.AbstractC0922Dg
    public final void d() {
        for (C0729q2 c0729q2 : this.d) {
            C1155Lg c1155Lg = this.c;
            c1155Lg.getClass();
            c1155Lg.d(c0729q2.getType());
        }
        this.d.clear();
    }

    public final C3226vg g() {
        f();
        d();
        C3226vg c3226vgE = e();
        if (this.j) {
            Set setKeySet = this.k.keySet();
            final C1155Lg c1155Lg = this.c;
            Objects.requireNonNull(c1155Lg);
            setKeySet.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda16
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    c1155Lg.d((C0767v2) obj);
                }
            });
            i();
        }
        if (l || c3226vgE.b().size() == this.c.c().size()) {
            return c3226vgE;
        }
        throw new AssertionError();
    }

    public final AbstractC0735r0 h() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC0922Dg
    public final void a(C0729q2 c0729q2, OW ow) {
        if (ow == null || !(ow instanceof NW)) {
            return;
        }
        if (!l && !c0729q2.B1()) {
            throw new AssertionError();
        }
        NW nwA = ow.a();
        if (a(c0729q2.d) != null) {
            this.e.put(c0729q2.d, nwA);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC0922Dg
    public final void c() {
        this.i = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0767v2 c0767v2) {
        C0729q2 c0729q2A = a(c0767v2);
        if (c0729q2A != null) {
            a(c0729q2A, AbstractC3287wQ.p);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List d(C0767v2 c0767v2) {
        return Collections.synchronizedList(new ArrayList());
    }

    private boolean a(C0729q2 c0729q2, AbstractC3287wQ abstractC3287wQ) {
        if (!l && !c0729q2.B1()) {
            throw new AssertionError();
        }
        if (a(c0729q2.getType(), abstractC3287wQ)) {
            return false;
        }
        this.d.add(c0729q2);
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC0922Dg
    public final void b() {
        this.f = this.f.c(this.a.E());
    }

    private C0729q2 a(AbstractC3472z00 abstractC3472z00) {
        if (abstractC3472z00.w()) {
            return a(abstractC3472z00.b().R());
        }
        if (!abstractC3472z00.r()) {
            return null;
        }
        C1969f3 c1969f3A = abstractC3472z00.a();
        if (c1969f3A.R().w()) {
            return a(c1969f3A.R());
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC0922Dg
    public final void b(com.android.tools.r8.graph.o5 o5Var) {
        a(o5Var);
    }

    private C0729q2 a(C0767v2 c0767v2) {
        if (c0767v2.M0()) {
            return a(c0767v2.b(this.a.a()));
        }
        if (c0767v2.W0() || c0767v2.Y0()) {
            return null;
        }
        if (l || c0767v2.O0()) {
            return this.c.a(c0767v2);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC0922Dg
    public final void a(C2409kl c2409kl, final TE te) {
        C0729q2 c0729q2A;
        C0729q2 c0729q2A2;
        Set setC = OU.c();
        for (C3332x4 c3332x4 : c2409kl.d) {
            for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                S10 s10B = abstractC2412ko.b();
                if (s10B != null) {
                    C0729q2 c0729q2A3 = a(s10B.a(this.a));
                    if (c0729q2A3 != null && a(c2409kl, s10B, c0729q2A3) == AbstractC3287wQ.a) {
                        setC.add(c0729q2A3.d);
                    }
                    if (s10B.w().E()) {
                        a(c2409kl, s10B, setC);
                    }
                } else if (abstractC2412ko.Y1() && (c0729q2A = a(abstractC2412ko.b0().L2())) != null) {
                    setC.add(c0729q2A.d);
                }
                int iG2 = abstractC2412ko.G2();
                if (iG2 == 10) {
                    C2972s9 c2972s9Y = abstractC2412ko.y();
                    if (!c2972s9Y.J2().M0() && (c0729q2A2 = a(c2972s9Y.J2().b(this.b))) != null) {
                        if (c2972s9Y.e().a(this.a).a(AbstractC3472z00.a(c2972s9Y.J2(), WH.b(), (C0702m<?>) this.a))) {
                            setC.add(c0729q2A2.d);
                        } else {
                            a(c0729q2A2, AbstractC3287wQ.d);
                        }
                    }
                } else if (iG2 != 12) {
                    if (iG2 != 30) {
                        if (iG2 == 32) {
                            a(abstractC2412ko.Y(), setC, c2409kl.h());
                        } else if (iG2 == 38) {
                            C1721bt c1721btG0 = abstractC2412ko.g0();
                            com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
                            C0729q2 c0729q2A4 = a(c1721btG0.S2().e);
                            if (c0729q2A4 != null) {
                                if (c1721btG0.h(this.a, o5VarH) != null) {
                                    setC.add(c0729q2A4.d);
                                } else {
                                    a(c0729q2A4, AbstractC3287wQ.n);
                                }
                            }
                        } else if (iG2 == 27) {
                            C0729q2 c0729q2A5 = a(abstractC2412ko.R().K2());
                            if (c0729q2A5 != null) {
                                setC.add(c0729q2A5.getType());
                            }
                        } else if (iG2 == 28 || iG2 == 59 || iG2 == 60) {
                        }
                    }
                    AbstractC1182Mh abstractC1182MhN = abstractC2412ko.N();
                    com.android.tools.r8.graph.o5 o5VarH2 = c2409kl.h();
                    com.android.tools.r8.graph.Y0 field = abstractC1182MhN.getField();
                    C0729q2 c0729q2A6 = a(field.B0());
                    if (c0729q2A6 != null) {
                        C3733i c3733i = (C3733i) this.a.f();
                        c3733i.getClass();
                        if (c3733i.a(field.e, field, o5VarH2).B()) {
                            setC.add(c0729q2A6.getType());
                        } else {
                            a(c0729q2A6, AbstractC3287wQ.i);
                        }
                    }
                } else {
                    C3446yc c3446ycA = abstractC2412ko.A();
                    com.android.tools.r8.graph.o5 o5VarH3 = c2409kl.h();
                    C0767v2 c0767v2K2 = c3446ycA.K2();
                    if (this.c.c(c0767v2K2)) {
                        if (c3446ycA.b() == null) {
                            setC.add(c0767v2K2);
                        } else {
                            C0729q2 c0729q2A0 = this.a.c(c0767v2K2).a0();
                            if (c3446ycA.b().C()) {
                                a(c0729q2A0, AbstractC3287wQ.j);
                            } else {
                                Iterator it = c3446ycA.b().a(C1552Zd.a()).iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) it.next();
                                        abstractC2412ko2.getClass();
                                        if (abstractC2412ko2 instanceof N3) {
                                            if (abstractC2412ko2.b().C()) {
                                                a(c0729q2A0, AbstractC3287wQ.j);
                                                break;
                                            }
                                        } else {
                                            if (abstractC2412ko2 instanceof C1721bt) {
                                                C1721bt c1721btG1 = abstractC2412ko2.g0();
                                                AbstractC0748t0 abstractC0748t0H = c1721btG1.h(this.a, o5VarH3);
                                                if (abstractC0748t0H != null) {
                                                    C0684j2 reference = abstractC0748t0H.getReference();
                                                    C0798x1 c0798x1 = this.b.r4;
                                                    if (reference == c0798x1.c) {
                                                        this.c.a(c0729q2A0, c0798x1.a);
                                                        this.f.a(this.a.E(), o5VarH3);
                                                        if (!c1721btG1.d1()) {
                                                            continue;
                                                        } else if (!c1721btG1.b().C()) {
                                                            Iterator<AbstractC2412ko> it2 = c1721btG1.b().h0().iterator();
                                                            while (true) {
                                                                if (it2.hasNext()) {
                                                                    AbstractC2412ko next = it2.next();
                                                                    if (next.t1()) {
                                                                        if (next.w().J2().a().o().a(c0729q2A0.getType().c(this.a))) {
                                                                        }
                                                                    } else if (!next.w1() || next.y().J2() != c0729q2A0.getType()) {
                                                                    }
                                                                } else {
                                                                    continue;
                                                                }
                                                            }
                                                        }
                                                    } else if (abstractC0748t0H.getReference() == this.b.s4.a) {
                                                        this.f.a(this.a.E(), o5VarH3);
                                                    }
                                                }
                                                a(c0729q2A0, AbstractC3287wQ.j);
                                                break;
                                                break;
                                            }
                                            if (abstractC2412ko2.f2()) {
                                                C0684j2 c0684j2S2 = abstractC2412ko2.i0().S2();
                                                C0757u1 c0757u1 = this.b.p4;
                                                if (c0684j2S2 == c0757u1.a) {
                                                    if (!o5VarH3.e().u1() || o5VarH3.a() != c0729q2A0) {
                                                    }
                                                } else if (c0684j2S2 == c0757u1.e || c0684j2S2 == c0757u1.f || c0684j2S2 == c0757u1.g) {
                                                }
                                            }
                                            a(c0729q2A0, AbstractC3287wQ.j);
                                            break;
                                            break;
                                        }
                                    } else {
                                        setC.add(c0767v2K2);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (ML ml : c3332x4.x()) {
                C0729q2 c0729q2A7 = a(ml.w());
                if (c0729q2A7 != null && a(c2409kl, ml, c0729q2A7) == AbstractC3287wQ.a) {
                    setC.add(c0729q2A7.d);
                }
                if (ml.w().E()) {
                    a(c2409kl, ml, setC);
                }
            }
        }
        if (!setC.isEmpty()) {
            Iterator it3 = setC.iterator();
            while (it3.hasNext()) {
                this.c.a((C0767v2) it3.next(), c2409kl.h());
            }
        }
        if (this.f.b(this.a.E(), c2409kl.h())) {
            c2409kl.b(new Consumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((CE.a) obj).a(te);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, C0767v2 c0767v2) {
        C0729q2 c0729q2A = a(c0767v2);
        if (c0729q2A != null) {
            set.add(c0729q2A.getType());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2, C0767v2 c0767v3) {
        if (c0767v2 != c0767v3) {
            C0729q2 c0729q2A = a(c0767v2);
            if (c0729q2A != null) {
                a(c0729q2A, AbstractC3287wQ.p);
            }
            C0729q2 c0729q2A2 = a(c0767v3);
            if (c0729q2A2 != null) {
                a(c0729q2A2, AbstractC3287wQ.p);
            }
        }
    }

    private void a(C1113Js c1113Js, final Set set, com.android.tools.r8.graph.o5 o5Var) {
        c1113Js.Q2().e.d(new Consumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(set, (C0767v2) obj);
            }
        });
        C0721p0 c0721p0Q2 = c1113Js.Q2();
        C0702m c0702m = this.a;
        JA jaB = JA.b(c0721p0Q2, c0702m, (C0680j) c0702m.f(), o5Var);
        if (jaB == null) {
            a(c1113Js, new Consumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda19
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((C0767v2) obj);
                }
            });
        } else {
            a(c1113Js, new Consumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda20
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(set, (C0767v2) obj);
                }
            });
            jaB.a(new BiConsumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda21
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a((C0767v2) obj, (C0767v2) obj2);
                }
            });
        }
    }

    private void a(C1113Js c1113Js, final Consumer consumer) {
        c1113Js.Q2().g.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (com.android.tools.r8.graph.B2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Consumer consumer, com.android.tools.r8.graph.B2 b2) {
        b2.getClass();
        if (b2 instanceof com.android.tools.r8.graph.G2) {
            C0699l2 c0699l2 = (C0699l2) b2.G0().j1();
            if (c0699l2.d.h()) {
                C0684j2 c0684j2U0 = c0699l2.u0();
                b(c0684j2U0.B0());
                C0737r2 c0737r2H0 = c0684j2U0.H0();
                consumer.accept(c0737r2H0.e);
                c0737r2H0.f.forEach(consumer);
                return;
            }
            if (!l && !c0699l2.d.b()) {
                throw new AssertionError();
            }
            com.android.tools.r8.graph.Y0 y0T0 = c0699l2.t0();
            b(y0T0.B0());
            consumer.accept(y0T0.h);
            return;
        }
        if (b2 instanceof com.android.tools.r8.graph.H2) {
            C0737r2 c0737r2 = (C0737r2) b2.H0().j1();
            consumer.accept(c0737r2.e);
            c0737r2.f.forEach(consumer);
        }
    }

    private void a(C2409kl c2409kl, S10 s10, Set set) {
        C0729q2 c0729q2A;
        C0729q2 c0729q2A2;
        for (AbstractC2412ko abstractC2412ko : s10.h0()) {
            if (abstractC2412ko.Y1()) {
                AbstractC1305Qs abstractC1305QsB0 = abstractC2412ko.b0();
                C0684j2 c0684j2S2 = abstractC1305QsB0.S2();
                for (C0767v2 c0767v2 : c0684j2S2.h.f.a) {
                    if (this.c.c(c0767v2)) {
                        set.add(c0767v2);
                    }
                }
                if (abstractC1305QsB0.Z1() && abstractC1305QsB0.c0().U2() == s10 && (c0729q2A = a(c0684j2S2.e)) != null) {
                    a(c0729q2A, AbstractC3287wQ.y);
                }
            } else if (abstractC2412ko.L1()) {
                C0729q2 c0729q2A3 = a(abstractC2412ko.N().getField().getType());
                if (c0729q2A3 != null) {
                    set.add(c0729q2A3.getType());
                }
            } else if (abstractC2412ko.u2() && (c0729q2A2 = a(c2409kl.h().G())) != null) {
                set.add(c0729q2A2.getType());
            }
        }
    }

    private AbstractC3287wQ a(C2409kl c2409kl, S10 s10, C0729q2 c0729q2) {
        AbstractC3287wQ abstractC3287wQA;
        AbstractC3287wQ abstractC3287wQ = AbstractC3287wQ.a;
        for (AbstractC2412ko abstractC2412ko : s10.h0()) {
            com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
            int iG2 = abstractC2412ko.G2();
            if (iG2 == 25) {
                abstractC3287wQA = a(abstractC2412ko.Q(), c0729q2);
            } else if (iG2 == 28) {
                C1189Mn c1189MnT = abstractC2412ko.T();
                if (!l && c1189MnT.getField().e != c0729q2.d) {
                    throw new AssertionError();
                }
                this.c.a(c0729q2, c1189MnT.getField());
                abstractC3287wQA = AbstractC3287wQ.a;
            } else if (iG2 == 30) {
                abstractC3287wQA = a(abstractC2412ko.V(), c2409kl, o5VarH, c0729q2, s10);
            } else if (iG2 == 36) {
                C1385Ts c1385TsE0 = abstractC2412ko.e0();
                AbstractC3472z00 abstractC3472z00A = c1385TsE0.a();
                boolean z = l;
                if (!z && !abstractC3472z00A.r()) {
                    throw new AssertionError();
                }
                C1229Oa c1229OaB = abstractC3472z00A.a().R().b();
                if (c1229OaB == null) {
                    if (z) {
                        abstractC3287wQA = AbstractC3287wQ.u;
                    } else {
                        throw new AssertionError();
                    }
                } else if (c1229OaB.R() != c0729q2.d) {
                    abstractC3287wQA = AbstractC3287wQ.u;
                } else {
                    Iterator it = ((ArrayList) c1385TsE0.h1()).iterator();
                    while (true) {
                        if (it.hasNext()) {
                            AbstractC3472z00 abstractC3472z00W = ((S10) it.next()).w();
                            if (abstractC3472z00W.r()) {
                                boolean z2 = l;
                                if (!z2 && !abstractC3472z00W.a().R().w()) {
                                    throw new AssertionError();
                                }
                                if (!z2 && abstractC3472z00W.a().U() != abstractC3472z00A.a().U() - 1) {
                                    throw new AssertionError();
                                }
                                abstractC3472z00W = abstractC3472z00W.a().R();
                            }
                            if (!c1229OaB.a(abstractC3472z00W)) {
                                abstractC3287wQA = AbstractC3287wQ.u;
                            }
                        } else {
                            abstractC3287wQA = AbstractC3287wQ.a;
                        }
                    }
                }
            } else if (iG2 == 56) {
                abstractC2412ko.B0();
                C0767v2 c0767v2G = o5VarH.G();
                if (c0767v2G != c0729q2.d && c0767v2G.b(this.b) != c0729q2.d) {
                    abstractC3287wQA = AbstractC3287wQ.r;
                } else {
                    abstractC3287wQA = AbstractC3287wQ.a;
                }
            } else if (iG2 == 60) {
                abstractC3287wQA = a(abstractC2412ko.G0(), c2409kl, o5VarH, c0729q2, s10);
            } else if (iG2 != 33 && iG2 != 34) {
                switch (iG2) {
                    case 6:
                        abstractC2412ko.t();
                        abstractC3287wQA = AbstractC3287wQ.a;
                        break;
                    case 7:
                        abstractC2412ko.u();
                        abstractC3287wQA = AbstractC3287wQ.a;
                        break;
                    case 8:
                        C1736c3 c1736c3V = abstractC2412ko.v();
                        boolean z3 = l;
                        if (!z3 && c1736c3V.d() != EnumC2523mE.OBJECT) {
                            throw new AssertionError();
                        }
                        AbstractC3472z00 abstractC3472z00W2 = c1736c3V.J2().w();
                        if (!z3 && !abstractC3472z00W2.r()) {
                            throw new AssertionError();
                        }
                        if (!z3 && !abstractC3472z00W2.a().R().w()) {
                            throw new AssertionError();
                        }
                        C1229Oa c1229OaB2 = abstractC3472z00W2.a().R().b();
                        AbstractC3472z00 abstractC3472z00W3 = c1736c3V.value().w();
                        if (abstractC3472z00W3.r()) {
                            if (!z3 && !abstractC3472z00W3.a().R().w()) {
                                throw new AssertionError();
                            }
                            if (!z3 && abstractC3472z00W3.a().U() != abstractC3472z00W2.a().U() - 1) {
                                throw new AssertionError();
                            }
                            abstractC3472z00W3 = abstractC3472z00W3.a().R();
                        }
                        if (c1229OaB2.a(abstractC3472z00W3) && c1229OaB2.R() == c0729q2.d) {
                            abstractC3287wQA = AbstractC3287wQ.a;
                        } else {
                            abstractC3287wQA = AbstractC3287wQ.t;
                        }
                        break;
                        break;
                    case 9:
                        abstractC3287wQA = a(c2409kl, abstractC2412ko.w().b(), c0729q2);
                        break;
                    case 10:
                        C2972s9 c2972s9Y = abstractC2412ko.y();
                        if (c2972s9Y.e().a(this.a).a(AbstractC3472z00.a(c2972s9Y.J2(), WH.b(), (C0702m<?>) this.a))) {
                            abstractC3287wQA = AbstractC3287wQ.a;
                        } else {
                            abstractC3287wQA = AbstractC3287wQ.d;
                        }
                        break;
                    default:
                        switch (iG2) {
                            case 38:
                            case 39:
                            case 40:
                                abstractC3287wQA = a(abstractC2412ko.b0(), c2409kl, o5VarH, c0729q2, s10);
                                break;
                            default:
                                abstractC3287wQA = AbstractC3287wQ.z;
                                break;
                        }
                        break;
                }
            } else {
                abstractC3287wQA = a(abstractC2412ko.b0(), c2409kl, o5VarH, c0729q2, s10);
            }
            if (abstractC3287wQA != AbstractC3287wQ.a) {
                if (a(c0729q2, abstractC3287wQA)) {
                    return abstractC3287wQA;
                }
                abstractC3287wQ = abstractC3287wQA;
            }
        }
        Iterator it2 = s10.g0().iterator();
        while (it2.hasNext()) {
            for (S10 s11 : ((ML) it2.next()).k0()) {
                if (!s11.w().E() && a(s11.w()) != c0729q2) {
                    C2989sQ c2989sQ = AbstractC3287wQ.k;
                    a(c0729q2, c2989sQ);
                    return c2989sQ;
                }
            }
        }
        return abstractC3287wQ;
    }

    @Override // com.android.tools.r8.internal.AbstractC0922Dg
    public final void a(AbstractC1476Wj abstractC1476Wj) {
        boolean z = l;
        if (!z && this.a.E() != abstractC1476Wj) {
            throw new AssertionError();
        }
        if (!z && this.g != null) {
            throw new AssertionError();
        }
        this.g = C1675bD.a(abstractC1476Wj);
        if (!z && this.c != null) {
            throw new AssertionError();
        }
        this.c = new C1100Jg(this.a, this).a(abstractC1476Wj);
        this.f = C1749cD.a(abstractC1476Wj);
    }

    @Override // com.android.tools.r8.internal.AbstractC0922Dg
    public final void a(final C0702m c0702m, C3012sl c3012sl, C2844qM c2844qM, ExecutorService executorService, com.android.tools.r8.ir.optimize.info.o oVar) {
        boolean z = l;
        if (!z) {
            oVar.b();
        }
        if (!z && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        C3226vg c3226vgG = g();
        if (!z && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        c0702m.a(c3226vgG);
        if (this.c.e()) {
            if (!z && !c3226vgG.c()) {
                throw new AssertionError();
            }
            return;
        }
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        AbstractC1048Hm abstractC1048HmC = this.c.c();
        AbstractC1048Hm abstractC1048HmB = this.c.b();
        C1749cD c1749cDA = this.c.a();
        this.c.d();
        a(abstractC1048HmC);
        C1502Xg c1502XgA = new C1473Wg(c0702m).a(abstractC1048HmB, c3226vgG).a(c3012sl, executorService);
        C1675bD c1675bD = this.g;
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C0982Fg.a((ZC) obj, (AbstractC1476Wj) obj2);
            }
        };
        c1675bD.getClass();
        AN anA = c1675bD.a(biFunction, c0702m.E()).a(c0702m, new Function() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0982Fg.a(c0702m, (ZC) obj);
            }
        });
        anA.a(new BiPredicate() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda5
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return C0982Fg.a((com.android.tools.r8.graph.o5) obj, (Set) obj2);
            }
        });
        C1372Tg c1372TgC = new C1400Ug(c0702m, anA, c3226vgG, abstractC1048HmB, c1502XgA).c(c3012sl, executorService);
        C1319Rg c1319RgB = c1372TgC.b();
        C2844qM c2844qMA = c2844qM.a(c0702m).a(c1372TgC.c().g()).a(c1749cDA.c(c0702m.E()).a(c1372TgC.c().g()));
        C1749cD c1749cD = this.f;
        c1749cD.getClass();
        c2844qMA.a(c1749cD.c(c0702m.E()).a(c1372TgC.c().g()));
        this.f.a();
        DH dhD = this.a.E().d();
        if (!z) {
            dhD.getClass();
            if (!(dhD instanceof C1319Rg)) {
                throw new AssertionError();
            }
        }
        AbstractC1476Wj abstractC1476WjK = dhD.k();
        if (!z && abstractC1476WjK != abstractC1476WjE) {
            throw new AssertionError();
        }
        oVar.a(this.a, executorService, new C0950Eg(this, dhD, abstractC1476WjK, c1372TgC));
        this.i = new C1346Sg(c0702m, c1372TgC.a(), c1319RgB, c3226vgG, c1502XgA);
        c0702m.Y().n1.a(new Fg$$ExternalSyntheticLambda6(c2844qM));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C0702m c0702m, ZC zc) {
        return zc.a(c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(com.android.tools.r8.graph.o5 o5Var, Set set) {
        AbstractC1008Gg abstractC1008GgN = o5Var.B().n();
        abstractC1008GgN.getClass();
        return !(abstractC1008GgN instanceof E9);
    }

    private void a(final AbstractC1048Hm abstractC1048Hm) {
        ((C3733i) this.a.f()).w().a(new Consumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0982Fg.a(abstractC1048Hm, (C3730h1) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, C3730h1 c3730h1) {
        c3730h1.b(com.android.tools.r8.graph.u5.a().e(set).a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:101:0x0279  */
    /* JADX WARN: Code duplicated, block: B:105:0x0293  */
    /* JADX WARN: Code duplicated, block: B:108:0x02ae  */
    /* JADX WARN: Code duplicated, block: B:111:0x02e0  */
    /* JADX WARN: Code duplicated, block: B:118:0x030c  */
    /* JADX WARN: Code duplicated, block: B:130:0x0334 A[EDGE_INSN: B:130:0x0334->B:132:0x0343 BREAK  A[LOOP:3: B:106:0x02a8->B:193:0x02a8]] */
    /* JADX WARN: Code duplicated, block: B:131:0x033a A[EDGE_INSN: B:131:0x033a->B:132:0x0343 BREAK  A[LOOP:3: B:106:0x02a8->B:193:0x02a8]] */
    /* JADX WARN: Code duplicated, block: B:134:0x0349  */
    /* JADX WARN: Code duplicated, block: B:137:0x034e  */
    /* JADX WARN: Code duplicated, block: B:139:0x0351  */
    /* JADX WARN: Code duplicated, block: B:142:0x035e A[EDGE_INSN: B:142:0x035e->B:144:0x0364 BREAK  A[LOOP:2: B:95:0x0257->B:140:0x0358]] */
    /* JADX WARN: Code duplicated, block: B:146:0x0368  */
    /* JADX WARN: Code duplicated, block: B:148:0x0374  */
    /* JADX WARN: Code duplicated, block: B:149:0x0376  */
    /* JADX WARN: Code duplicated, block: B:181:0x0363 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:182:0x035c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:183:0x028d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:186:0x0358 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:187:0x0330 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:188:0x02cb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:190:0x032c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:191:0x032c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:192:0x0327 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:97:0x025d  */
    /* JADX WARN: Code duplicated, block: B:99:0x0275  */
    public void a(C1946em c1946em, C0729q2 c0729q2, Set set) {
        C1946em c1946emE;
        Iterator it;
        AbstractC2178hm abstractC2178hmA;
        int iF;
        C3150ug c3150ug;
        com.android.tools.r8.graph.Y0 y0;
        com.android.tools.r8.graph.T0 t0S;
        boolean zR0;
        C1426Ul c1426Ul;
        InterfaceC0841Ar it2;
        Iterator it3;
        AbstractC0828Ag c3380xg;
        Integer numValueOf;
        B1 b1A;
        boolean z;
        OptionalInt optionalIntEmpty;
        OptionalInt optionalIntEmpty2;
        OptionalInt optionalIntEmpty3;
        if (!c0729q2.v1()) {
            c3150ug = new C3150ug(AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC1048Hm.k(), -1);
        } else {
            C1946em c1946emE2 = AbstractC2178hm.e();
            C3017sq c3017sq = new C3017sq();
            boolean zB = this.c.b(c0729q2.getType());
            C3017sq c3017sq2 = new C3017sq();
            C0862Bm c0862BmG = AbstractC1048Hm.g();
            NW nw = (NW) this.e.get(c0729q2.d);
            if (nw == null) {
                a(c0729q2.getType(), new C2701oQ());
            } else {
                Iterator<com.android.tools.r8.graph.T0> it4 = c0729q2.N1().iterator();
                C1858dh c1858dhA = null;
                while (true) {
                    if (!it4.hasNext()) {
                        if (c1858dhA == null) {
                            c1946emE = AbstractC2178hm.e();
                            it = set.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (c1946emE != null) {
                                        abstractC2178hmA = c1946emE.a();
                                        break;
                                    }
                                } else {
                                    y0 = (com.android.tools.r8.graph.Y0) it.next();
                                    t0S = ((C3733i) this.a.f()).a(c0729q2, y0).s();
                                    if (t0S == null) {
                                        if (l) {
                                            a(c0729q2.getType(), new C2848qQ(y0));
                                            c3380xg = C3520zg.c();
                                        } else {
                                            throw new AssertionError();
                                        }
                                    } else {
                                        zR0 = y0.h.R0();
                                        c1426Ul = new C1426Ul();
                                        it2 = ((C2879qr) c3017sq2.keySet()).iterator();
                                        while (true) {
                                            if (it2.hasNext()) {
                                                it3 = it;
                                                if (zR0) {
                                                    c3380xg = new C3450yg();
                                                    break;
                                                } else {
                                                    c3380xg = new C3380xg(c1426Ul.a());
                                                    break;
                                                }
                                            }
                                            numValueOf = Integer.valueOf(((K) it2).t());
                                            b1A = ((AbstractC3121uK) c3017sq2.get(numValueOf)).a(t0S);
                                            if (!b1A.g()) {
                                                a(c0729q2.getType(), new C2848qQ(numValueOf.intValue(), y0));
                                                c3380xg = C3520zg.c();
                                            } else {
                                                z = b1A instanceof C2853qV;
                                                if (z) {
                                                }
                                                it = it;
                                                c1426Ul.a(numValueOf.intValue() + 1, b1A);
                                                if (!zR0) {
                                                    if (l) {
                                                    }
                                                    if (b1A.e().G() != numValueOf.intValue()) {
                                                        zR0 = false;
                                                    }
                                                }
                                            }
                                        }
                                        if (c3380xg.b()) {
                                            if (!this.j) {
                                                c1946emE = null;
                                            }
                                        }
                                        if (c1946emE != null) {
                                            c1946emE.a(y0, c3380xg.a());
                                        }
                                        it = it3;
                                    }
                                    it3 = it;
                                    if (c3380xg.b()) {
                                        if (!this.j) {
                                            c1946emE = null;
                                        }
                                    }
                                    if (c1946emE != null) {
                                        c1946emE.a(y0, c3380xg.a());
                                    }
                                    it = it3;
                                }
                                abstractC2178hmA = null;
                                break;
                            }
                            if (abstractC2178hmA == null) {
                                AbstractC2178hm abstractC2178hmA2 = c1946emE2.a();
                                AbstractC1048Hm abstractC1048HmA = c0862BmG.a();
                                if (c1858dhA == null) {
                                    iF = -1;
                                } else {
                                    iF = c1858dhA.f();
                                }
                                c3150ug = new C3150ug(abstractC2178hmA, abstractC2178hmA2, abstractC1048HmA, iF);
                                break;
                            }
                            break;
                        }
                        int i = 0;
                        while (true) {
                            if (i < c1858dhA.f()) {
                                if (!c3017sq2.a(i)) {
                                    AbstractC3121uK abstractC3121uKB = c1858dhA.b(i);
                                    if (abstractC3121uKB.e()) {
                                        a(c0729q2.getType(), new C2848qQ(i, this.b.r4.b));
                                    } else {
                                        boolean z2 = l;
                                        if (!z2) {
                                            B1 b1A2 = abstractC3121uKB.a(this.h.e());
                                            b1A2.getClass();
                                            if (b1A2 instanceof C2853qV) {
                                                optionalIntEmpty2 = OptionalInt.of(b1A2.e().G());
                                            } else {
                                                optionalIntEmpty2 = OptionalInt.empty();
                                            }
                                            if (!optionalIntEmpty2.isPresent()) {
                                                throw new AssertionError();
                                            }
                                        }
                                        if (!z2) {
                                            B1 b1A3 = abstractC3121uKB.a(this.h.e());
                                            b1A3.getClass();
                                            if (b1A3 instanceof C2853qV) {
                                                optionalIntEmpty = OptionalInt.of(b1A3.e().G());
                                            } else {
                                                optionalIntEmpty = OptionalInt.empty();
                                            }
                                            if (optionalIntEmpty.getAsInt() != i) {
                                                throw new AssertionError();
                                            }
                                        }
                                        c3017sq2.a(i, abstractC3121uKB);
                                        if (zB) {
                                            C0767v2 c0767v2A = c1858dhA.a(i);
                                            if (c0767v2A == null) {
                                                a(c0729q2.getType(), new C2777pQ(i));
                                            } else {
                                                c3017sq.a(i, c0767v2A);
                                            }
                                        } else {
                                            continue;
                                        }
                                    }
                                }
                                i++;
                            } else {
                                c1946emE = AbstractC2178hm.e();
                                it = set.iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        if (c1946emE != null) {
                                            abstractC2178hmA = c1946emE.a();
                                            break;
                                        }
                                    } else {
                                        y0 = (com.android.tools.r8.graph.Y0) it.next();
                                        t0S = ((C3733i) this.a.f()).a(c0729q2, y0).s();
                                        if (t0S == null) {
                                            if (l) {
                                                a(c0729q2.getType(), new C2848qQ(y0));
                                                c3380xg = C3520zg.c();
                                            } else {
                                                throw new AssertionError();
                                            }
                                        } else {
                                            zR0 = y0.h.R0();
                                            c1426Ul = new C1426Ul();
                                            it2 = ((C2879qr) c3017sq2.keySet()).iterator();
                                            while (true) {
                                                if (it2.hasNext()) {
                                                    it3 = it;
                                                    if (zR0) {
                                                        c3380xg = new C3450yg();
                                                        break;
                                                    } else {
                                                        c3380xg = new C3380xg(c1426Ul.a());
                                                        break;
                                                    }
                                                }
                                                numValueOf = Integer.valueOf(((K) it2).t());
                                                b1A = ((AbstractC3121uK) c3017sq2.get(numValueOf)).a(t0S);
                                                if (!b1A.g()) {
                                                    a(c0729q2.getType(), new C2848qQ(numValueOf.intValue(), y0));
                                                    c3380xg = C3520zg.c();
                                                } else {
                                                    z = b1A instanceof C2853qV;
                                                    if (z && !b1A.C()) {
                                                        a(c0729q2.getType(), new C3127uQ(numValueOf.intValue(), y0));
                                                        c3380xg = C3520zg.c();
                                                    } else {
                                                        it = it;
                                                        c1426Ul.a(numValueOf.intValue() + 1, b1A);
                                                        if (!zR0) {
                                                            if (l && !z) {
                                                                throw new AssertionError();
                                                            }
                                                            if (b1A.e().G() != numValueOf.intValue()) {
                                                                zR0 = false;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (c3380xg.b()) {
                                                if (!this.j) {
                                                    c1946emE = null;
                                                }
                                            }
                                            if (c1946emE != null) {
                                                c1946emE.a(y0, c3380xg.a());
                                            }
                                            it = it3;
                                        }
                                        it3 = it;
                                        if (c3380xg.b()) {
                                            if (!this.j) {
                                                c1946emE = null;
                                            }
                                        }
                                        if (c1946emE != null) {
                                            c1946emE.a(y0, c3380xg.a());
                                        }
                                        it = it3;
                                    }
                                    abstractC2178hmA = null;
                                    break;
                                }
                                if (abstractC2178hmA == null) {
                                    AbstractC2178hm abstractC2178hmA3 = c1946emE2.a();
                                    AbstractC1048Hm abstractC1048HmA2 = c0862BmG.a();
                                    if (c1858dhA == null) {
                                        iF = -1;
                                    } else {
                                        iF = c1858dhA.f();
                                    }
                                    c3150ug = new C3150ug(abstractC2178hmA, abstractC2178hmA3, abstractC1048HmA2, iF);
                                    break;
                                }
                                break;
                            }
                        }
                    } else {
                        com.android.tools.r8.graph.T0 next = it4.next();
                        C0798x1 c0798x1 = this.b.r4;
                        C0767v2 c0767v2 = c0729q2.d;
                        c0798x1.getClass();
                        if (C0798x1.a(next, c0767v2)) {
                            AbstractC3121uK abstractC3121uKA = nw.a(next.getReference());
                            if (abstractC3121uKA == null) {
                                if (!next.X0().i()) {
                                    a(c0729q2.getType(), new C2920rQ(next.getReference()));
                                }
                            } else {
                                B1 b1A4 = abstractC3121uKA.a(this.h.e());
                                b1A4.getClass();
                                if (b1A4 instanceof C2853qV) {
                                    optionalIntEmpty3 = OptionalInt.of(b1A4.e().G());
                                } else {
                                    optionalIntEmpty3 = OptionalInt.empty();
                                }
                                if (!optionalIntEmpty3.isPresent()) {
                                    a(c0729q2.getType(), new C2848qQ(this.b.r4.b, next.getReference()));
                                } else {
                                    int asInt = optionalIntEmpty3.getAsInt();
                                    c1946emE2.a(next.getReference(), Integer.valueOf(asInt + 1));
                                    c3017sq2.a(asInt, abstractC3121uKA);
                                    if (zB) {
                                        AbstractC1154Lf abstractC1154LfG = next.X0().g();
                                        if (abstractC1154LfG.j()) {
                                            c3017sq.a(asInt, abstractC1154LfG.e().R());
                                        } else {
                                            a(c0729q2.getType(), new C2777pQ(next.getReference()));
                                        }
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        } else if (this.b.r4.b(next, c0729q2.d)) {
                            AbstractC3121uK abstractC3121uKA2 = nw.a(next.getReference());
                            if (abstractC3121uKA2 == null) {
                                if (!next.X0().i()) {
                                    a(c0729q2.getType(), new C2610nQ(next.getReference()));
                                }
                            } else {
                                boolean z3 = l;
                                if (!z3 && !(abstractC3121uKA2 instanceof C1858dh)) {
                                    throw new AssertionError();
                                }
                                if (!z3 && c1858dhA != null && !c1858dhA.equals(abstractC3121uKA2.a())) {
                                    throw new AssertionError();
                                }
                                c1858dhA = abstractC3121uKA2.a();
                                c0862BmG.a(next.getReference());
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            c3150ug = null;
            break;
        }
        if (c3150ug == null) {
            C1155Lg c1155Lg = this.c;
            c1155Lg.getClass();
            c1155Lg.d(c0729q2.getType());
        } else {
            if (this.j && this.k.containsKey(c0729q2.getType())) {
                return;
            }
            c1946em.a(c0729q2.d, c3150ug);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2) {
        for (com.android.tools.r8.graph.W0 w0 : c0729q2.V0()) {
            if (w0.C1() && w0.i1().l().e() && a(c0729q2, AbstractC3287wQ.l)) {
                break;
            }
        }
        if (c0729q2.b(this.a)) {
            a(c0729q2, AbstractC3287wQ.m);
        }
    }

    private C2989sQ a(AbstractC1182Mh abstractC1182Mh, C2409kl c2409kl, com.android.tools.r8.graph.o5 o5Var, C0729q2 c0729q2, S10 s10) {
        if (!l && !abstractC1182Mh.S1() && !abstractC1182Mh.c()) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.T0 t0S = ((C3733i) this.a.f()).c(abstractC1182Mh.getField()).s();
        if (t0S == null) {
            return AbstractC3287wQ.s;
        }
        C0702m c0702m = this.a;
        C0767v2 c0767v2J0 = t0S.J0();
        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        c0702m.getClass();
        if (C0729q2.a(c0702m.b(o5VarH.a(), c0767v2J0)) == null) {
            return AbstractC3287wQ.s;
        }
        if (abstractC1182Mh.S1() && abstractC1182Mh.V().e() == s10) {
            if (o5Var.a() == c0729q2 && o5Var.e().C1()) {
                return AbstractC3287wQ.a;
            }
            return AbstractC3287wQ.x;
        }
        if (t0S.getReference().h.b(this.b) != c0729q2.d) {
            return AbstractC3287wQ.v;
        }
        return AbstractC3287wQ.a;
    }

    private static C2989sQ a(C1015Gl c1015Gl, C0729q2 c0729q2) {
        boolean z = l;
        if (!z && c1015Gl.L2() != EnumC1076Il.EQ && c1015Gl.L2() != EnumC1076Il.NE) {
            throw new AssertionError("Comparing a reference with " + c1015Gl.L2().toString());
        }
        if (c1015Gl.N2()) {
            return AbstractC3287wQ.a;
        }
        AbstractC3472z00 abstractC3472z00W = c1015Gl.O2().w();
        if (abstractC3472z00W.a(c1015Gl.P2().w())) {
            if (!z && !abstractC3472z00W.w()) {
                throw new AssertionError();
            }
            if (z || abstractC3472z00W.b().R() == c0729q2.d) {
                return AbstractC3287wQ.a;
            }
            throw new AssertionError();
        }
        return AbstractC3287wQ.w;
    }

    private AbstractC3287wQ a(AbstractC1305Qs abstractC1305Qs, C2409kl c2409kl, com.android.tools.r8.graph.o5 o5Var, C0729q2 c0729q2, S10 s10) {
        AbstractC3287wQ c3209vQ;
        if (abstractC1305Qs.S2().e.M0()) {
            if (abstractC1305Qs.S2().f == this.b.d0) {
                return AbstractC3287wQ.a;
            }
            return AbstractC3287wQ.q;
        }
        AbstractC0748t0 abstractC0748t0H = abstractC1305Qs.h(this.a, c2409kl.h());
        if (abstractC0748t0H == null) {
            return AbstractC3287wQ.n;
        }
        C0684j2 reference = abstractC0748t0H.getReference();
        AbstractC0727q0 abstractC0727q0A = abstractC0748t0H.a();
        if (abstractC0727q0A.e0()) {
            if (abstractC0727q0A.B1() && abstractC0748t0H.e().C1()) {
                if (c2409kl.h().a() == abstractC0727q0A && c2409kl.u().A1()) {
                    if (c2409kl.u().C1() && !abstractC1305Qs.c(0).Q()) {
                        return AbstractC3287wQ.l;
                    }
                } else {
                    return AbstractC3287wQ.l;
                }
            }
            AbstractC1008Gg abstractC1008GgN = abstractC0748t0H.B().n();
            abstractC1008GgN.getClass();
            if ((abstractC1008GgN instanceof E9) && abstractC1008GgN.a().a(abstractC1305Qs.g0(), s10)) {
                final AbstractC1476Wj abstractC1476WjE = this.a.E();
                ((ZC) this.g.a(abstractC0748t0H.g0(), com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return ZC.a(abstractC1476WjE);
                    }
                }), abstractC1476WjE)).a(c0729q2, abstractC1476WjE);
                return AbstractC3287wQ.a;
            }
            for (int i = 0; i < abstractC0748t0H.D().size(); i++) {
                if (abstractC1305Qs.c(com.android.tools.r8.utils.M.a(!(abstractC1305Qs instanceof C1721bt)) + i) == s10 && abstractC0748t0H.b(i).b(this.b) != c0729q2.getType()) {
                    return new C2536mQ(reference);
                }
            }
            if (abstractC1305Qs.Z1() && abstractC1305Qs.c0().U2() == s10 && abstractC0727q0A.isInterface()) {
                return AbstractC3287wQ.h;
            }
            return AbstractC3287wQ.a;
        }
        if (abstractC0727q0A instanceof C0754u0) {
            return AbstractC3287wQ.o;
        }
        boolean z = l;
        if (!z && !abstractC0727q0A.f0()) {
            throw new AssertionError();
        }
        C0767v2 type = abstractC0727q0A.getType();
        C0722p1 c0722p1 = this.b;
        if (type == c0722p1.e2) {
            C0798x1 c0798x1 = c0722p1.r4;
            if (reference == c0798x1.g || reference == c0798x1.h || reference == c0798x1.i) {
                c3209vQ = a(abstractC1305Qs);
            } else if (reference != c0798x1.e && reference != c0798x1.f) {
                if (reference == c0798x1.d || reference == c0798x1.j) {
                    c3209vQ = AbstractC3287wQ.a;
                } else if (reference == c0798x1.k && c2409kl.u().C1() && c2409kl.h().a() == c0729q2) {
                    c3209vQ = AbstractC3287wQ.a;
                } else {
                    c3209vQ = new C3209vQ(reference);
                }
            } else {
                if (!z && abstractC1305Qs.c0().U2() != s10) {
                    throw new AssertionError();
                }
                this.c.a(c0729q2, this.b.r4.a);
                c3209vQ = AbstractC3287wQ.a;
            }
        } else {
            C0767v2 type2 = abstractC0727q0A.getType();
            C0722p1 c0722p2 = this.b;
            if (type2 == c0722p2.Z1) {
                if (reference == c0722p2.g4.e && abstractC1305Qs.f1()) {
                    c3209vQ = AbstractC3287wQ.a;
                } else {
                    C0722p1.b bVar = this.b.g4;
                    if (reference == bVar.i) {
                        if (!z && abstractC1305Qs.c0().U2() != s10) {
                            throw new AssertionError();
                        }
                        this.c.a(c0729q2, this.b.r4.a);
                        c3209vQ = AbstractC3287wQ.a;
                    } else if (reference == bVar.f) {
                        c3209vQ = AbstractC3287wQ.a;
                    } else if (reference == bVar.d) {
                        c3209vQ = a(abstractC1305Qs);
                    } else {
                        c3209vQ = new C3209vQ(reference);
                    }
                }
            } else {
                C0767v2 type3 = abstractC0727q0A.getType();
                C0722p1 c0722p3 = this.b;
                if (type3 == c0722p3.g2) {
                    com.android.tools.r8.graph.S1 s1 = c0722p3.f4;
                    if (reference != s1.e && reference != s1.f) {
                        c3209vQ = new C3209vQ(reference);
                    } else {
                        c3209vQ = AbstractC3287wQ.a;
                    }
                } else {
                    C0767v2 type4 = abstractC0727q0A.getType();
                    C0722p1 c0722p4 = this.b;
                    if (type4 == c0722p4.X1) {
                        if (reference == c0722p4.k4.s) {
                            this.c.a(c0729q2, c0722p4.r4.a);
                            c3209vQ = AbstractC3287wQ.a;
                        } else {
                            c3209vQ = new C3209vQ(reference);
                        }
                    } else if (abstractC0727q0A.getType() != this.b.v2 && abstractC0727q0A.getType() != this.b.w2) {
                        C0767v2 type5 = abstractC0727q0A.getType();
                        C0722p1 c0722p5 = this.b;
                        if (type5 == c0722p5.z2) {
                            com.android.tools.r8.graph.G1 g1 = c0722p5.u4;
                            if (reference == g1.a || reference == g1.b) {
                                c3209vQ = AbstractC3287wQ.a;
                            } else {
                                c3209vQ = new C3209vQ(reference);
                            }
                        } else {
                            c3209vQ = new C3209vQ(reference);
                        }
                    } else {
                        C0722p1 c0722p6 = this.b;
                        if (reference != c0722p6.W3.k && reference != c0722p6.X3.k) {
                            c3209vQ = new C3209vQ(reference);
                        } else {
                            this.c.a(c0729q2, c0722p6.r4.a);
                            c3209vQ = AbstractC3287wQ.a;
                        }
                    }
                }
            }
        }
        if (c3209vQ == AbstractC3287wQ.a) {
            this.f.a(this.a.E(), o5Var);
        }
        return c3209vQ;
    }

    private AbstractC3287wQ a(AbstractC1305Qs abstractC1305Qs) {
        boolean z = l;
        if (!z && ((ArrayList) abstractC1305Qs.h1()).size() != 2) {
            throw new AssertionError();
        }
        AbstractC3472z00 abstractC3472z00W = abstractC1305Qs.c(0).w();
        AbstractC3472z00 abstractC3472z00W2 = abstractC1305Qs.K2().w();
        C0729q2 c0729q2A = a(abstractC3472z00W);
        C0729q2 c0729q2A2 = a(abstractC3472z00W2);
        if (!z && c0729q2A == null && c0729q2A2 == null) {
            throw new AssertionError();
        }
        if (abstractC3472z00W.E() || abstractC3472z00W2.E()) {
            return AbstractC3287wQ.a;
        }
        if (c0729q2A == c0729q2A2) {
            return AbstractC3287wQ.a;
        }
        return new C3058tQ(abstractC1305Qs.S2(), abstractC3472z00W, abstractC3472z00W2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final StringBuilder sb, C0767v2 c0767v2, List list) {
        sb.append(c0767v2).append(" (").append(list.size()).append(" reasons):");
        C3454yk.a((Iterable) list).a(new ObjIntConsumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda2
            @Override // java.util.function.ObjIntConsumer
            public final void accept(Object obj, int i) {
                C0982Fg.a(sb, (AbstractC3287wQ) obj, i);
            }
        });
        sb.append(System.lineSeparator());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, AbstractC3287wQ abstractC3287wQ, int i) {
        sb.append(System.lineSeparator()).append(" - ").append(abstractC3287wQ).append(" (").append(i).append(Constant.AFTER_QUTO);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final GI gi, C0767v2 c0767v2, List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0982Fg.a(gi, (AbstractC3287wQ) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(GI gi, AbstractC3287wQ abstractC3287wQ) {
        gi.a(gi.a(abstractC3287wQ) + 1, abstractC3287wQ.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(GI gi, Object obj, Object obj2) {
        int iA = gi.a(obj) - gi.a(obj2);
        return iA != 0 ? iA : System.identityHashCode(obj) - System.identityHashCode(obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, GI gi, Object obj) {
        sb.append(obj).append(" (").append(gi.a(obj)).append(Constant.AFTER_QUTO).append(System.lineSeparator());
    }

    final boolean a(C0767v2 c0767v2, AbstractC3287wQ abstractC3287wQ) {
        if (!this.j) {
            return false;
        }
        ((List) this.k.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.internal.Fg$$ExternalSyntheticLambda22
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0982Fg.d((C0767v2) obj);
            }
        })).add(abstractC3287wQ);
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC0922Dg
    public final void a(com.android.tools.r8.graph.o5 o5Var) {
        this.c.a(o5Var);
        this.f.a(this.a.E(), o5Var.getReference());
    }

    @Override // com.android.tools.r8.internal.AbstractC0922Dg
    public final Set a(C2409kl c2409kl, TE te, com.android.tools.r8.graph.proto.k kVar) {
        C1346Sg c1346Sg = this.i;
        if (c1346Sg != null) {
            return c1346Sg.a(c2409kl, te, kVar);
        }
        return OU.c();
    }
}
