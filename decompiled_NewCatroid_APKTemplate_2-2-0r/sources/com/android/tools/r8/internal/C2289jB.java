package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2289jB {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C0722p1 b;
    private final AbstractC0922Dg c;
    private final com.android.tools.r8.utils.A1 d;

    C2289jB(C0702m c0702m, AbstractC0922Dg abstractC0922Dg) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = abstractC0922Dg;
        this.d = c0702m.Y();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(M10 m10, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == m10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        return c3332x5 == c3332x4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C3332x4 c3332x4, C3332x4 c3332x5) {
        return c3332x5 == c3332x4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(C3332x4 c3332x4, C3332x4 c3332x5) {
        return c3332x5 == c3332x4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AbstractC2412ko b(C0767v2 c0767v2, S10 s10) {
        return new AF(s10, c0767v2, this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC2412ko c(C0767v2 c0767v2, S10 s10) {
        if (!c0767v2.W0() && !c0767v2.Y0()) {
            return new C3446yc(s10, c0767v2);
        }
        int i = QW.k;
        return ((PW) new PW().a(this.b.d(c0767v2).a()).a(s10)).b();
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 29381. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    private void a(com.android.tools.r8.internal.C2409kl r36, com.android.tools.r8.graph.o5 r37, com.android.tools.r8.internal.DH r38, com.android.tools.r8.internal.AbstractC1476Wj r39, com.android.tools.r8.graph.proto.k r40, java.util.Set r41, java.util.Set r42) {
        /*
            Method dump skipped, instruction units count: 2938
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C2289jB.a(com.android.tools.r8.internal.kl, com.android.tools.r8.graph.o5, com.android.tools.r8.internal.DH, com.android.tools.r8.internal.Wj, com.android.tools.r8.graph.proto.k, java.util.Set, java.util.Set):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C2363kB a(DH dh, AbstractC1476Wj abstractC1476Wj) {
        return new C2363kB(this.a, dh, abstractC1476Wj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2412ko a(C0767v2 c0767v2, S10 s10) {
        return new C2179hn(c0767v2, s10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC2412ko a(C0767v2 c0767v2, C0684j2 c0684j2, AbstractC1305Qs abstractC1305Qs, C0767v2 c0767v3, S10 s10) {
        return AbstractC1084Is.a(EnumC1953et.g, this.b.a(c0767v2.a(this.b, c0767v3), c0684j2.h, c0684j2.f), null, s10, abstractC1305Qs.h1(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(C2853qV c2853qV) {
        return new IdentityHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public S10 a(InterfaceC2641no interfaceC2641no, C2853qV c2853qV, C2409kl c2409kl, AbstractC3078th abstractC3078th, C0767v2 c0767v2, AbstractC1305Qs abstractC1305Qs, C0767v2 c0767v3) {
        interfaceC2641no.previous();
        C0702m c0702m = this.a;
        InterfaceC3176v00 interfaceC3176v00A = InterfaceC3176v00.a(null, abstractC3078th.a(c0702m, c0767v2));
        c2853qV.getClass();
        AbstractC2412ko abstractC2412koA = c2853qV.a(c0702m, c2409kl.h(), c2409kl.e, interfaceC3176v00A);
        if (!e && abstractC2412koA.g()) {
            throw new AssertionError();
        }
        abstractC2412koA.b(this.d.W0 ? abstractC1305Qs.Y0() : AbstractC1915eM.u());
        interfaceC2641no.add(abstractC2412koA);
        interfaceC2641no.next();
        return abstractC2412koA.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2412ko a(C2972s9 c2972s9, C0767v2 c0767v2, S10 s10) {
        return new C2972s9(s10, c2972s9.e(), c0767v2, c2972s9.K2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2412ko a(C1641ao c1641ao, C0767v2 c0767v2, S10 s10) {
        return new C1641ao(s10, c1641ao.value(), c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2412ko a(C1358Ss c1358Ss, C0767v2 c0767v2, S10 s10) {
        return new C1358Ss(c0767v2, s10, c1358Ss.h1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2412ko a(C1385Ts c1385Ts, C0767v2 c0767v2, S10 s10) {
        return new C1385Ts(c0767v2, s10, c1385Ts.h1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2412ko a(AG ag, C0767v2 c0767v2, S10 s10) {
        return new AG(s10, ag.J2(), c0767v2);
    }

    private static void a(com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl, Set set) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            final M10 m10 = (M10) it.next();
            if (!m10.b().y()) {
                InterfaceC2641no interfaceC2641noA = m10.h().a(c2409kl);
                interfaceC2641noA.a(new Predicate() { // from class: com.android.tools.r8.internal.jB$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C2289jB.a(m10, (AbstractC2412ko) obj);
                    }
                });
                interfaceC2641noA.s();
            } else {
                throw new C3405y10("Unused argument with users in " + o5Var.w());
            }
        }
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl, TE te) {
        com.android.tools.r8.graph.proto.k kVar;
        AbstractC1476Wj abstractC1476Wj;
        C3406y2 c3406y2B;
        AbstractC2412ko abstractC2412koA;
        AbstractC3472z00 abstractC3472z00C;
        DH dhD;
        ArrayDeque arrayDeque = new ArrayDeque(8);
        AbstractC1476Wj abstractC1476WjA = o5Var.e().a1().a(this.a);
        AbstractC1476Wj abstractC1476WjE = this.a.E();
        C0684j2 reference = o5Var.getReference();
        while (abstractC1476WjE != abstractC1476WjA) {
            if (!e && !abstractC1476WjE.i()) {
                throw new AssertionError();
            }
            DH dhD2 = abstractC1476WjE.d();
            dhD2.getClass();
            if (dhD2 instanceof C1319Rg) {
                dhD = dhD2;
            } else {
                AbstractC1476Wj abstractC1476WjK = dhD2.k();
                dhD = dhD2;
                while (abstractC1476WjK.i() && !(abstractC1476WjK instanceof C1319Rg) && abstractC1476WjK != abstractC1476WjA) {
                    dhD = abstractC1476WjK.d();
                    abstractC1476WjK = dhD.k();
                }
            }
            AbstractC1476Wj abstractC1476WjK2 = dhD.k();
            arrayDeque.addLast(new C2138hB(dhD2, abstractC1476WjK2, reference));
            reference = dhD2.a(abstractC1476WjK2, reference);
            abstractC1476WjE = abstractC1476WjK2;
        }
        if (!e && arrayDeque.size() > 8) {
            throw new AssertionError();
        }
        C0684j2 c0684j2A = this.a.E().a((AbstractC1476Wj) null, o5Var.getReference());
        while (!arrayDeque.isEmpty()) {
            C2138hB c2138hB = (C2138hB) arrayDeque.removeLast();
            com.android.tools.r8.graph.proto.k kVarD = c2138hB.a.d(c2138hB.b, c2138hB.c);
            DH dh = c2138hB.a;
            AbstractC1476Wj abstractC1476Wj2 = c2138hB.b;
            Set setC = OU.c();
            Set<M10> setC2 = OU.c();
            com.android.tools.r8.graph.proto.c cVarA = kVarD.a();
            LinkedList linkedList = new LinkedList();
            InterfaceC2641no interfaceC2641noA = c2409kl.j().a(c2409kl);
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko abstractC2412ko = (AbstractC2412ko) interfaceC2641noA.next();
                if (!abstractC2412ko.m1()) {
                    break;
                }
                C3406y2 c3406y2Q = abstractC2412ko.q();
                ArrayDeque arrayDeque2 = arrayDeque;
                com.android.tools.r8.graph.proto.b bVarA = cVarA.a(i);
                if (bVarA.c()) {
                    com.android.tools.r8.graph.proto.h hVarA = bVarA.a();
                    if (hVarA.h()) {
                        AbstractC3292wV abstractC3292wVF = hVarA.f();
                        if (hVarA.g().X0() && abstractC3292wVF.x()) {
                            abstractC3472z00C = AbstractC3472z00.m();
                        } else {
                            abstractC3472z00C = hVarA.g().c(this.a);
                        }
                        C0702m c0702m = this.a;
                        InterfaceC3176v00 interfaceC3176v00A = InterfaceC3176v00.a(c3406y2Q.j(), abstractC3472z00C);
                        abstractC3292wVF.getClass();
                        abstractC2412koA = abstractC3292wVF.a(c0702m, c2409kl.h(), c2409kl.e, interfaceC3176v00A);
                        abstractC2412koA.b(AbstractC1915eM.b.x().a(0).a(c0684j2A).a());
                    } else {
                        abstractC1476Wj2 = abstractC1476Wj2;
                        M10 m10 = new M10(c2409kl.a(hVarA.g().c(this.a)));
                        m10.b(AbstractC1915eM.u());
                        setC2.add(m10);
                        abstractC2412koA = m10;
                    }
                    c3406y2Q.b().g(abstractC2412koA.b());
                    setC.addAll(abstractC2412koA.b().g0());
                    linkedList.add(abstractC2412koA);
                    interfaceC2641noA.s();
                    i2++;
                    i3 = i3;
                } else {
                    dh = dh;
                    abstractC1476Wj2 = abstractC1476Wj2;
                    kVarD = kVarD;
                    int i4 = i3;
                    int iA = cVarA.a(i, i2);
                    if (bVarA instanceof com.android.tools.r8.graph.proto.l) {
                        AbstractC3472z00 abstractC3472z00C2 = bVarA.b().g().c(this.a);
                        int i5 = C3406y2.k;
                        c3406y2B = ((C3329x2) ((C3329x2) new C3329x2().a(iA).a(c2409kl, abstractC3472z00C2, c3406y2Q.j())).a(c3406y2Q.Y0())).b();
                        setC.addAll(c3406y2Q.b().g0());
                        c3406y2Q.b().g(c3406y2B.b());
                    } else if (iA != i) {
                        int i6 = C3406y2.k;
                        c3406y2B = ((C3329x2) ((C3329x2) new C3329x2().a(iA).a(c2409kl, c3406y2Q.a(), c3406y2Q.j())).a(c3406y2Q.Y0())).b();
                        c3406y2Q.b().g(c3406y2B.b());
                    } else {
                        c3406y2B = c3406y2Q;
                    }
                    if (iA == i4) {
                        if (c3406y2B != c3406y2Q) {
                            interfaceC2641noA.d(c3406y2B);
                        }
                        i3 = i4 + 1;
                    } else {
                        interfaceC2641noA.v();
                        ListIterator listIterator = linkedList.listIterator();
                        while (listIterator.hasNext()) {
                            AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) listIterator.next();
                            if (!abstractC2412ko2.m1() || c3406y2B.K2() < abstractC2412ko2.q().K2()) {
                                listIterator.previous();
                                break;
                            }
                        }
                        listIterator.add(c3406y2B);
                        i3 = i4;
                    }
                }
                i++;
                arrayDeque = arrayDeque2;
                kVarD = kVarD;
                abstractC1476Wj2 = abstractC1476Wj2;
                dh = dh;
            }
            DH dh2 = dh;
            AbstractC1476Wj abstractC1476Wj3 = abstractC1476Wj2;
            com.android.tools.r8.graph.proto.k kVar2 = kVarD;
            ArrayDeque arrayDeque3 = arrayDeque;
            interfaceC2641noA.previous();
            if (!linkedList.isEmpty()) {
                Iterator it = linkedList.iterator();
                while (it.hasNext()) {
                    interfaceC2641noA.add((AbstractC2412ko) it.next());
                }
            }
            dh2.getClass();
            boolean z = dh2 instanceof C1319Rg;
            if (z) {
                boolean z2 = e;
                if (!z2 && !z) {
                    throw new AssertionError();
                }
                if (z2) {
                    abstractC1476Wj = abstractC1476Wj3;
                } else {
                    abstractC1476Wj = abstractC1476Wj3;
                    if (dh2.k() != abstractC1476Wj) {
                        throw new AssertionError();
                    }
                }
                kVar = kVar2;
                setC.addAll(this.c.a(c2409kl, te, kVar));
            } else {
                kVar = kVar2;
                abstractC1476Wj = abstractC1476Wj3;
            }
            if (!setC2.isEmpty()) {
                for (M10 m11 : setC2) {
                    if (m11.b().C()) {
                        com.android.tools.r8.ir.optimize.F.a(m11);
                    }
                }
            }
            a(c2409kl, o5Var, dh2, abstractC1476Wj, kVar, setC, setC2);
            arrayDeque = arrayDeque3;
        }
        if (e) {
            return;
        }
        c2409kl.a(this.a);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private InterfaceC2641no a(C2409kl c2409kl, B4 b4, InterfaceC2641no interfaceC2641no, InterfaceC1474Wh interfaceC1474Wh, C1237Oh c1237Oh) {
        if (c1237Oh.h()) {
            interfaceC2641no.previous();
            int i = C3133uU.l;
            C2972s9 c2972s9B = ((C2901r9) ((C2901r9) new C3063tU().b(interfaceC1474Wh.value()).a(c2409kl, c1237Oh.f().c(this.a), null)).a(c1237Oh.f()).a(((AbstractC2412ko) interfaceC1474Wh).Y0())).b();
            interfaceC2641no.add(c2972s9B);
            interfaceC1474Wh.a(c2972s9B.b());
            if (c2972s9B.h().D()) {
                final C3332x4 c3332x4A = interfaceC2641no.a(c2409kl, b4, this.a.Y());
                C3332x4 c3332x4 = (C3332x4) com.android.tools.r8.utils.L1.b((ListIterator) b4, new Predicate() { // from class: com.android.tools.r8.internal.jB$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C2289jB.a(c3332x4A, (C3332x4) obj);
                    }
                });
                if (!e && c3332x4 != c3332x4A) {
                    throw new AssertionError();
                }
                b4.next();
                interfaceC2641no = c3332x4A.a(c2409kl);
            }
            AbstractC2412ko next = interfaceC2641no.next();
            if (!e && next != interfaceC1474Wh) {
                throw new AssertionError();
            }
        }
        return interfaceC2641no;
    }

    private InterfaceC2641no a(C2409kl c2409kl, B4 b4, InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs, KE ke) {
        com.android.tools.r8.graph.proto.k kVarE = ke.e();
        if (kVarE.g()) {
            return interfaceC2641no;
        }
        for (int i = 0; i < ((ArrayList) abstractC1305Qs.J2()).size(); i++) {
            com.android.tools.r8.graph.proto.l lVarB = kVarE.a().a(i).b();
            if (lVarB != null && lVarB.k()) {
                interfaceC2641no.previous();
                S10 s10C = abstractC1305Qs.c(i);
                int i2 = C3133uU.l;
                C2901r9 c2901r9B = new C3063tU().b(s10C);
                C0767v2 c0767v2F = lVarB.f();
                C0702m c0702m = this.a;
                WH whN = s10C.w().N();
                c0767v2F.getClass();
                C2972s9 c2972s9B = ((C2901r9) ((C2901r9) c2901r9B.a(c2409kl, AbstractC3472z00.a(c0767v2F, whN, (C0702m<?>) c0702m), null)).a(lVarB.f()).a(abstractC1305Qs.Y0())).b();
                interfaceC2641no.add(c2972s9B);
                abstractC1305Qs.a(i, c2972s9B.b());
                if (c2972s9B.h().D()) {
                    final C3332x4 c3332x4A = interfaceC2641no.a(c2409kl, b4, this.a.Y());
                    C3332x4 c3332x4 = (C3332x4) com.android.tools.r8.utils.L1.b((ListIterator) b4, new Predicate() { // from class: com.android.tools.r8.internal.jB$$ExternalSyntheticLambda8
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return C2289jB.c(c3332x4A, (C3332x4) obj);
                        }
                    });
                    if (!e && c3332x4 != c3332x4A) {
                        throw new AssertionError();
                    }
                    b4.next();
                    interfaceC2641no = c3332x4A.a(c2409kl);
                }
                AbstractC2412ko next = interfaceC2641no.next();
                if (!e && next != abstractC1305Qs) {
                    throw new AssertionError();
                }
            }
        }
        return interfaceC2641no;
    }

    private void a(C2409kl c2409kl, B4 b4, InterfaceC2641no interfaceC2641no, C1616aU c1616aU, com.android.tools.r8.graph.proto.k kVar) {
        if (kVar.f() && kVar.c().k()) {
            interfaceC2641no.previous();
            if (c1616aU.h().D()) {
                final C3332x4 c3332x4A = interfaceC2641no.a(c2409kl, b4, this.d);
                C3332x4 c3332x4 = (C3332x4) com.android.tools.r8.utils.L1.b((ListIterator) b4, new Predicate() { // from class: com.android.tools.r8.internal.jB$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C2289jB.b(c3332x4A, (C3332x4) obj);
                    }
                });
                if (!e && c3332x4 == null) {
                    throw new AssertionError();
                }
                b4.next();
                interfaceC2641no = c3332x4A.a(c2409kl);
            }
            C0767v2 c0767v2F = kVar.c().f();
            S10 s10L2 = c1616aU.L2();
            int i = C3133uU.l;
            C2901r9 c2901r9B = new C3063tU().b(s10L2);
            C0702m c0702m = this.a;
            WH whN = s10L2.w().N();
            c0767v2F.getClass();
            C2972s9 c2972s9B = ((C2901r9) ((C2901r9) c2901r9B.a(c2409kl, AbstractC3472z00.a(c0767v2F, whN, (C0702m<?>) c0702m), null)).a(c0767v2F).a(c1616aU.Y0())).b();
            interfaceC2641no.add(c2972s9B);
            c1616aU.a(0, c2972s9B.b());
            AbstractC2412ko next = interfaceC2641no.next();
            if (!e && next != c1616aU) {
                throw new AssertionError();
            }
        }
    }

    private com.android.tools.r8.graph.Y0 a(C1237Oh c1237Oh, com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0727q0 abstractC0727q0C;
        com.android.tools.r8.graph.T0 t0C;
        if (c1237Oh.c() && (t0C = ((com.android.tools.r8.graph.Y0) c1237Oh.a()).c((abstractC0727q0C = this.a.c(((com.android.tools.r8.graph.Y0) c1237Oh.a()).B0())))) != null) {
            AbstractC0735r0 abstractC0735r0A = AbstractC0735r0.a(abstractC0727q0C, t0C);
            if (AbstractC0639e.a(abstractC0735r0A, abstractC0727q0C, o5Var, this.a).d()) {
                return C1826dE.a(this.a, abstractC0735r0A, (com.android.tools.r8.graph.Y0) c1237Oh.b());
            }
        }
        return (com.android.tools.r8.graph.Y0) c1237Oh.b();
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0054  */
    /* JADX WARN: Code duplicated, block: B:23:0x005c  */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x002f, code lost:
    
        if (com.android.tools.r8.internal.EnumC1812d20.a((char) r10.e.e[0]) != com.android.tools.r8.internal.EnumC1812d20.a((char) r9.e.e[0])) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.android.tools.r8.internal.S10 a(com.android.tools.r8.internal.C2409kl r7, com.android.tools.r8.internal.InterfaceC2641no r8, com.android.tools.r8.graph.C0767v2 r9, com.android.tools.r8.graph.C0767v2 r10, com.android.tools.r8.internal.S10 r11) {
        /*
            r6 = this;
            com.android.tools.r8.internal.z00 r0 = r11.w()
            boolean r0 = r0.E()
            if (r0 == 0) goto L8d
            boolean r0 = r10.W0()
            if (r0 == 0) goto L32
            boolean r0 = r9.W0()
            if (r0 == 0) goto L38
            com.android.tools.r8.internal.d20 r0 = com.android.tools.r8.internal.EnumC1812d20.OBJECT
            com.android.tools.r8.graph.u2 r0 = r10.e
            byte[] r0 = r0.e
            r1 = 0
            r0 = r0[r1]
            char r0 = (char) r0
            com.android.tools.r8.internal.d20 r0 = com.android.tools.r8.internal.EnumC1812d20.a(r0)
            com.android.tools.r8.graph.u2 r9 = r9.e
            byte[] r9 = r9.e
            r9 = r9[r1]
            char r9 = (char) r9
            com.android.tools.r8.internal.d20 r9 = com.android.tools.r8.internal.EnumC1812d20.a(r9)
            if (r0 == r9) goto L8d
            goto L38
        L32:
            boolean r0 = r9.W0()
            if (r0 == 0) goto L6d
        L38:
            boolean r9 = com.android.tools.r8.internal.C2289jB.e
            if (r9 != 0) goto L49
            boolean r9 = r10.R0()
            if (r9 == 0) goto L43
            goto L49
        L43:
            java.lang.AssertionError r7 = new java.lang.AssertionError
            r7.<init>()
            throw r7
        L49:
            r8.previous()
            com.android.tools.r8.utils.A1 r2 = r6.d
            boolean r9 = r10.W0()
            if (r9 == 0) goto L5c
            com.android.tools.r8.graph.m r9 = r6.a
            r11 = 0
            com.android.tools.r8.internal.z00 r9 = com.android.tools.r8.internal.AbstractC3472z00.a(r10, r11, r9)
            goto L60
        L5c:
            com.android.tools.r8.internal.BS r9 = com.android.tools.r8.internal.AbstractC3472z00.m()
        L60:
            r5 = r9
            r3 = 0
            r0 = r8
            r1 = r7
            com.android.tools.r8.internal.S10 r7 = r0.a(r1, r2, r3, r5)
            r8.next()
            return r7
        L6d:
            boolean r7 = com.android.tools.r8.internal.C2289jB.e
            if (r7 != 0) goto L7e
            boolean r8 = r10.X0()
            if (r8 == 0) goto L78
            goto L7e
        L78:
            java.lang.AssertionError r7 = new java.lang.AssertionError
            r7.<init>()
            throw r7
        L7e:
            if (r7 != 0) goto L8d
            boolean r7 = r9.X0()
            if (r7 == 0) goto L87
            goto L8d
        L87:
            java.lang.AssertionError r7 = new java.lang.AssertionError
            r7.<init>()
            throw r7
        L8d:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C2289jB.a(com.android.tools.r8.internal.kl, com.android.tools.r8.internal.no, com.android.tools.r8.graph.v2, com.android.tools.r8.graph.v2, com.android.tools.r8.internal.S10):com.android.tools.r8.internal.S10");
    }
}
