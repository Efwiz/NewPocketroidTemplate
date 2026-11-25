package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.A4$$ExternalSyntheticLambda1;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3477z20;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.BP;
import com.android.tools.r8.internal.C1158Lj;
import com.android.tools.r8.internal.C1706be;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C1749cD;
import com.android.tools.r8.internal.C1763cQ;
import com.android.tools.r8.internal.C2289jB;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2844qM;
import com.android.tools.r8.internal.C2969s6;
import com.android.tools.r8.internal.C3012sl;
import com.android.tools.r8.internal.C3030t00;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3387xn;
import com.android.tools.r8.internal.CN;
import com.android.tools.r8.internal.InterfaceC2488lo;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.InterfaceC3457yn;
import com.android.tools.r8.internal.JH;
import com.android.tools.r8.internal.N3;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.TE;
import com.android.tools.r8.internal.V3;
import com.android.tools.r8.internal.V9;
import com.android.tools.r8.internal.ZC$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.wZ$$ExternalSyntheticLambda0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.InterfaceC3880d;
import com.android.tools.r8.utils.L1;
import com.android.tools.r8.utils.Z1;
import com.android.tools.r8.utils.y3;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.c0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3546c0 {
    static final /* synthetic */ boolean i = true;
    protected final C0702m a;
    private final C3012sl b;
    private final C2289jB c;
    private final C1749cD d;
    private final C3570l0 e;
    private final ConcurrentHashMap f = new ConcurrentHashMap();
    private final Set g = OU.c();
    private final V3 h;

    public C3546c0(C0702m c0702m, C3012sl c3012sl, C2289jB c2289jB) {
        this.a = c0702m;
        this.b = c3012sl;
        this.c = c2289jB;
        ((C3733i) c0702m.f()).f();
        this.e = new C3570l0(c0702m);
        this.d = C1749cD.a(c0702m.E());
        this.h = c0702m.Y().k() ? new V3(c0702m.Y()) : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        return c3332x5 == c3332x4;
    }

    static int b(C2409kl c2409kl) {
        Iterator<C3332x4> it = c2409kl.d.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            for (AbstractC2412ko abstractC2412ko : it.next().p()) {
                if (!i && abstractC2412ko.F1()) {
                    throw new AssertionError();
                }
                if (!abstractC2412ko.m1() && !(abstractC2412ko instanceof N3) && (!abstractC2412ko.M1() || abstractC2412ko.P().K2().y().size() != 1)) {
                    if (!abstractC2412ko.u2()) {
                        i2++;
                    }
                }
            }
        }
        return i2;
    }

    public final void c(o5 o5Var) {
        this.d.a(this.a.E(), o5Var.getReference());
    }

    public final void d(o5 o5Var) {
        this.d.a(this.a.E(), o5Var.getReference());
        this.e.c(o5Var);
    }

    private static boolean a(o5 o5Var, C2409kl c2409kl) {
        C0767v2 c0767v2V1 = o5Var.e().V1();
        Iterator<C3332x4> it = c2409kl.d.iterator();
        while (it.hasNext()) {
            InterfaceC2488lo interfaceC2488loR = it.next().R();
            while (interfaceC2488loR.hasNext()) {
                AbstractC2412ko abstractC2412ko = (AbstractC2412ko) interfaceC2488loR.a(new A4$$ExternalSyntheticLambda1());
                if (abstractC2412ko != null && c0767v2V1.N0() && !((S10) ((ArrayList) abstractC2412ko.h1()).get(0)).b((Set) null)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void a(o5 o5Var, C2409kl c2409kl, TE te, y3 y3Var) {
        this.e.a(o5Var, c2409kl, te, y3Var);
    }

    public final void a(o5 o5Var, C2409kl c2409kl, IdentityHashMap identityHashMap, C3387xn c3387xn, TE te, y3 y3Var) {
        Q q = new Q(this.a, o5Var, identityHashMap);
        a(q, q, o5Var, c2409kl, com.android.tools.r8.ir.optimize.info.q.b(), c3387xn, te, y3Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(o5 o5Var) {
        if (!o5Var.e().R0() && !o5Var.e().e1().b()) {
            return false;
        }
        o5Var.d(this.a);
        this.b.b(o5Var);
        return true;
    }

    public final void a(o5 o5Var, C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.n nVar, TE te, y3 y3Var) {
        a(o5Var, c2409kl, nVar, te, y3Var, a(te));
    }

    public static void b(final C0702m c0702m) {
        Iterator it = ((C3733i) c0702m.f()).e().iterator();
        while (it.hasNext()) {
            ((C0729q2) it.next()).g(new Consumer() { // from class: com.android.tools.r8.ir.optimize.c0$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3546c0.a(c0702m, (o5) obj);
                }
            }, new Predicate() { // from class: com.android.tools.r8.ir.optimize.c0$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C3546c0.a((W0) obj);
                }
            });
        }
    }

    public final void a(o5 o5Var, C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.n nVar, TE te, y3 y3Var, InterfaceC3457yn interfaceC3457yn) {
        N nA = a(o5Var, te, this.a.Y().P0().d - b(c2409kl), interfaceC3457yn);
        C3387xn c3387xn = new C3387xn(this.a, o5Var, c2409kl, te);
        if (!i) {
            c3387xn.c();
        }
        a(nA, nA, o5Var, c2409kl, nVar, c3387xn, te, y3Var);
    }

    public final InterfaceC3457yn a(TE te) {
        final C1706be c1706be = new C1706be(this.a, te.a());
        return (InterfaceC3457yn) this.a.b(c1706be, new Function() { // from class: com.android.tools.r8.ir.optimize.c0$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c1706be, (C1158Lj) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ InterfaceC3457yn a(C1706be c1706be, C1158Lj c1158Lj) {
        return new BP(this.a, c1706be);
    }

    public final N a(o5 o5Var, TE te, int i2, InterfaceC3457yn interfaceC3457yn) {
        return new N(this.a, interfaceC3457yn, o5Var, te, i2);
    }

    /* JADX WARN: Code duplicated, block: B:106:0x029e  */
    /* JADX WARN: Code duplicated, block: B:150:0x00d0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:153:0x00ca A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:154:0x019a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:155:0x0113 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:156:0x012c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:157:0x0142 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:158:0x0159 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:159:0x018b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:160:0x02d8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:161:0x02ca A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:162:0x02ae A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:39:0x00de  */
    /* JADX WARN: Code duplicated, block: B:40:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:45:0x0117  */
    /* JADX WARN: Code duplicated, block: B:48:0x0128  */
    /* JADX WARN: Code duplicated, block: B:51:0x0138  */
    /* JADX WARN: Code duplicated, block: B:60:0x0153  */
    /* JADX WARN: Code duplicated, block: B:64:0x015d  */
    /* JADX WARN: Code duplicated, block: B:65:0x0161  */
    /* JADX WARN: Code duplicated, block: B:69:0x018f  */
    /* JADX WARN: Code duplicated, block: B:80:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:83:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:91:0x0209  */
    /* JADX WARN: Code duplicated, block: B:93:0x022e  */
    private void a(InterfaceC3558f0 interfaceC3558f0, InterfaceC3556e0 interfaceC3556e0, o5 o5Var, C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.n nVar, C3387xn c3387xn, TE te, y3 y3Var) {
        final C3332x4 c3332x4;
        A1.i iVar;
        B4 b4;
        ArrayDeque arrayDeque;
        o5 o5VarA;
        W0 w0E;
        AbstractC3477z20 abstractC3477z20A;
        AbstractC3477z20 abstractC3477z20;
        A1.i iVar2;
        ArrayDeque arrayDeque2;
        X xA;
        W wA;
        B4 b5;
        Set<C3332x4> set;
        C3579q c3579q;
        Y yA;
        boolean z;
        S10 s10B;
        boolean zT;
        o5 o5Var2;
        interfaceC3558f0 = interfaceC3558f0;
        o5Var = o5Var;
        C2409kl c2409kl2 = c2409kl;
        C3579q c3579q2 = new C3579q(this.a, c2409kl2, OU.c());
        Set<C3332x4> setC = OU.c();
        B4 b4S = c2409kl.s();
        V9 v9 = new V9(this.a, c2409kl2);
        ArrayDeque arrayDeque3 = new ArrayDeque();
        A1.i iVarP0 = this.a.Y().P0();
        while (b4S.hasNext()) {
            C3332x4 c3332x4A = b4S.next();
            if (!arrayDeque3.isEmpty() && arrayDeque3.peekFirst() == c3332x4A) {
                arrayDeque3.pop();
            }
            if (!setC.contains(c3332x4A)) {
                InterfaceC2641no interfaceC2641noA = c3332x4A.a(c2409kl2);
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next = interfaceC2641noA.next();
                    if (next.Y1()) {
                        AbstractC1305Qs abstractC1305QsB0 = next.b0();
                        I4.c<?> cVarO = ((C3733i) this.a.f()).b(abstractC1305QsB0.S2(), abstractC1305QsB0.R2()).o();
                        if (cVarO == null) {
                            o5Var = o5Var;
                            b4S = b4S;
                            c2409kl2 = c2409kl2;
                        } else if (cVarO.a(o5Var, this.a).b()) {
                            continue;
                        } else {
                            AbstractC0748t0 abstractC0748t0Q = cVarO.c();
                            if (abstractC1305QsB0.Z1()) {
                                if (interfaceC2641noA.a(this.a, c2409kl.h())) {
                                    c3579q2.a(abstractC1305QsB0.J2());
                                } else {
                                    o5VarA = interfaceC3556e0.a(o5Var, abstractC1305QsB0);
                                    if (o5VarA == null) {
                                        AbstractC3477z20.a(this.a, o5Var, abstractC1305QsB0);
                                    } else {
                                        w0E = o5VarA.e();
                                        if (interfaceC3556e0.a()) {
                                            abstractC3477z20A = JH.w();
                                        } else {
                                            abstractC3477z20A = AbstractC3477z20.a(this.a, o5VarA, o5Var);
                                        }
                                        abstractC3477z20 = abstractC3477z20A;
                                        interfaceC2641noA = interfaceC2641noA;
                                        c3332x4A = c3332x4A;
                                        iVar2 = iVarP0;
                                        arrayDeque2 = arrayDeque3;
                                        xA = interfaceC3556e0.a(c2409kl, abstractC1305QsB0, cVarO, o5VarA, o5Var, v9, c3387xn, abstractC3477z20);
                                        if (xA == null) {
                                            if (!i) {
                                                abstractC3477z20.v();
                                            }
                                        } else if (xA instanceof C3544b0) {
                                            this.d.a(this.a.E(), o5Var);
                                        } else {
                                            wA = xA.a();
                                            if (wA.c == EnumC3542a0.d) {
                                                if (!i && !te.c()) {
                                                    throw new AssertionError();
                                                }
                                            } else {
                                                if (!interfaceC3558f0.a(wA, abstractC3477z20)) {
                                                    if (!i) {
                                                        abstractC3477z20.v();
                                                    }
                                                } else {
                                                    v9 = v9;
                                                    b5 = b4S;
                                                    set = setC;
                                                    c3579q = c3579q2;
                                                    yA = wA.a(this.a, abstractC1305QsB0, o5Var, c3387xn, this.c);
                                                    if (interfaceC3558f0.a(c2409kl, abstractC1305QsB0, yA, c3332x4A, abstractC3477z20)) {
                                                        if (!i) {
                                                            abstractC3477z20.v();
                                                        }
                                                        o5Var = o5Var;
                                                        c2409kl2 = c2409kl;
                                                        setC = set;
                                                        c3579q2 = c3579q;
                                                        b4S = b5;
                                                        v9 = v9;
                                                    } else {
                                                        z = i;
                                                        if (z && !o5VarA.e().M1()) {
                                                            throw new AssertionError();
                                                        }
                                                        s10B = abstractC1305QsB0.b();
                                                        if (s10B != null) {
                                                            c3579q.a(s10B);
                                                        }
                                                        zT = yA.b.t().t();
                                                        interfaceC2641noA.previous();
                                                        interfaceC3558f0.a(yA);
                                                        c3579q2 = c3579q;
                                                        b4S = b5;
                                                        interfaceC2641noA.a(this.a, c2409kl, yA.b, b5, set, wA.e);
                                                        if (yA.a != EnumC3542a0.c) {
                                                            o5Var2 = o5VarA;
                                                        } else {
                                                            if (z && !this.b.b()) {
                                                                throw new AssertionError();
                                                            }
                                                            nVar.c(w0E);
                                                            if (this.f.isEmpty()) {
                                                                this.b.a(new InterfaceC3880d() { // from class: com.android.tools.r8.ir.optimize.c0$$ExternalSyntheticLambda9
                                                                    @Override // com.android.tools.r8.utils.InterfaceC3880d
                                                                    public final void a() {
                                                                        this.f$0.a();
                                                                    }
                                                                });
                                                            }
                                                            CN cn = (CN) this.f.computeIfAbsent(o5VarA.a(), Z1.a(new wZ$$ExternalSyntheticLambda0()));
                                                            o5Var2 = o5VarA;
                                                            cn.add(o5Var2);
                                                        }
                                                        v9.b();
                                                        c2409kl2 = c2409kl;
                                                        setC = set;
                                                        a(c2409kl, b4S, c3332x4A, setC, y3Var);
                                                        if (o5Var.e().t1() && !yA.b.u().t1()) {
                                                            o5Var.e().f.R();
                                                        }
                                                        if (o5Var.e().f.q() && !yA.b.u().f.q()) {
                                                            o5Var.e().f.b();
                                                        }
                                                        o5Var.e().a(this.a, w0E);
                                                        if (zT) {
                                                            iVar = iVar2;
                                                            if (iVar.a(this.a, o5Var2, arrayDeque2.size() + 1)) {
                                                                arrayDeque = arrayDeque2;
                                                                arrayDeque.push((C3332x4) L1.a((ListIterator) b4S));
                                                                c3332x4 = c3332x4A;
                                                                b4 = b4S;
                                                                L1.b((ListIterator) b4, new Predicate() { // from class: com.android.tools.r8.ir.optimize.c0$$ExternalSyntheticLambda0
                                                                    @Override // java.util.function.Predicate
                                                                    public final boolean test(Object obj) {
                                                                        return C3546c0.a(c3332x4, (C3332x4) obj);
                                                                    }
                                                                });
                                                                b4.next();
                                                            } else {
                                                                iVarP0 = iVar;
                                                            }
                                                        } else {
                                                            iVarP0 = iVar2;
                                                        }
                                                        arrayDeque3 = arrayDeque2;
                                                    }
                                                }
                                                interfaceC2641noA = interfaceC2641noA;
                                                c3332x4A = c3332x4A;
                                                iVarP0 = iVar2;
                                                arrayDeque3 = arrayDeque2;
                                            }
                                        }
                                        c2409kl2 = c2409kl;
                                        b4S = b4S;
                                        interfaceC2641noA = interfaceC2641noA;
                                        c3332x4A = c3332x4A;
                                        iVarP0 = iVar2;
                                        arrayDeque3 = arrayDeque2;
                                    }
                                }
                            } else if ((abstractC1305QsB0 instanceof C1721bt) && interfaceC2641noA.a(this.a, c2409kl2, abstractC0748t0Q.t())) {
                                c3579q2.a(abstractC1305QsB0.J2());
                            } else {
                                o5VarA = interfaceC3556e0.a(o5Var, abstractC1305QsB0);
                                if (o5VarA == null) {
                                    AbstractC3477z20.a(this.a, o5Var, abstractC1305QsB0);
                                } else {
                                    w0E = o5VarA.e();
                                    if (interfaceC3556e0.a()) {
                                        abstractC3477z20A = JH.w();
                                    } else {
                                        abstractC3477z20A = AbstractC3477z20.a(this.a, o5VarA, o5Var);
                                    }
                                    abstractC3477z20 = abstractC3477z20A;
                                    interfaceC2641noA = interfaceC2641noA;
                                    c3332x4A = c3332x4A;
                                    iVar2 = iVarP0;
                                    arrayDeque2 = arrayDeque3;
                                    xA = interfaceC3556e0.a(c2409kl, abstractC1305QsB0, cVarO, o5VarA, o5Var, v9, c3387xn, abstractC3477z20);
                                    if (xA == null) {
                                        if (!i) {
                                            abstractC3477z20.v();
                                        }
                                    } else if (xA instanceof C3544b0) {
                                        this.d.a(this.a.E(), o5Var);
                                    } else {
                                        wA = xA.a();
                                        if (wA.c == EnumC3542a0.d) {
                                            if (!i) {
                                                throw new AssertionError();
                                            }
                                        } else {
                                            if (!interfaceC3558f0.a(wA, abstractC3477z20)) {
                                                if (!i) {
                                                    abstractC3477z20.v();
                                                }
                                            } else {
                                                v9 = v9;
                                                b5 = b4S;
                                                set = setC;
                                                c3579q = c3579q2;
                                                yA = wA.a(this.a, abstractC1305QsB0, o5Var, c3387xn, this.c);
                                                if (interfaceC3558f0.a(c2409kl, abstractC1305QsB0, yA, c3332x4A, abstractC3477z20)) {
                                                    if (!i) {
                                                        abstractC3477z20.v();
                                                    }
                                                    o5Var = o5Var;
                                                    c2409kl2 = c2409kl;
                                                    setC = set;
                                                    c3579q2 = c3579q;
                                                    b4S = b5;
                                                    v9 = v9;
                                                } else {
                                                    z = i;
                                                    if (z) {
                                                    }
                                                    s10B = abstractC1305QsB0.b();
                                                    if (s10B != null) {
                                                        c3579q.a(s10B);
                                                    }
                                                    zT = yA.b.t().t();
                                                    interfaceC2641noA.previous();
                                                    interfaceC3558f0.a(yA);
                                                    c3579q2 = c3579q;
                                                    b4S = b5;
                                                    interfaceC2641noA.a(this.a, c2409kl, yA.b, b5, set, wA.e);
                                                    if (yA.a != EnumC3542a0.c) {
                                                        if (z) {
                                                        }
                                                        nVar.c(w0E);
                                                        if (this.f.isEmpty()) {
                                                            this.b.a(new InterfaceC3880d() { // from class: com.android.tools.r8.ir.optimize.c0$$ExternalSyntheticLambda9
                                                                @Override // com.android.tools.r8.utils.InterfaceC3880d
                                                                public final void a() {
                                                                    this.f$0.a();
                                                                }
                                                            });
                                                        }
                                                        CN cn2 = (CN) this.f.computeIfAbsent(o5VarA.a(), Z1.a(new wZ$$ExternalSyntheticLambda0()));
                                                        o5Var2 = o5VarA;
                                                        cn2.add(o5Var2);
                                                    } else {
                                                        o5Var2 = o5VarA;
                                                    }
                                                    v9.b();
                                                    c2409kl2 = c2409kl;
                                                    setC = set;
                                                    a(c2409kl, b4S, c3332x4A, setC, y3Var);
                                                    if (o5Var.e().t1()) {
                                                        o5Var.e().f.R();
                                                    }
                                                    if (o5Var.e().f.q()) {
                                                        o5Var.e().f.b();
                                                    }
                                                    o5Var.e().a(this.a, w0E);
                                                    if (zT) {
                                                        iVar = iVar2;
                                                        if (iVar.a(this.a, o5Var2, arrayDeque2.size() + 1)) {
                                                            arrayDeque = arrayDeque2;
                                                            arrayDeque.push((C3332x4) L1.a((ListIterator) b4S));
                                                            c3332x4 = c3332x4A;
                                                            b4 = b4S;
                                                            L1.b((ListIterator) b4, new Predicate() { // from class: com.android.tools.r8.ir.optimize.c0$$ExternalSyntheticLambda0
                                                                @Override // java.util.function.Predicate
                                                                public final boolean test(Object obj) {
                                                                    return C3546c0.a(c3332x4, (C3332x4) obj);
                                                                }
                                                            });
                                                            b4.next();
                                                        } else {
                                                            iVarP0 = iVar;
                                                        }
                                                    } else {
                                                        iVarP0 = iVar2;
                                                    }
                                                    arrayDeque3 = arrayDeque2;
                                                }
                                            }
                                            interfaceC2641noA = interfaceC2641noA;
                                            c3332x4A = c3332x4A;
                                            iVarP0 = iVar2;
                                            arrayDeque3 = arrayDeque2;
                                        }
                                    }
                                    c2409kl2 = c2409kl;
                                    b4S = b4S;
                                    interfaceC2641noA = interfaceC2641noA;
                                    c3332x4A = c3332x4A;
                                    iVarP0 = iVar2;
                                    arrayDeque3 = arrayDeque2;
                                }
                            }
                        }
                        interfaceC3558f0 = interfaceC3558f0;
                    } else {
                        setC = setC;
                        c3579q2 = c3579q2;
                        c2409kl2 = c2409kl2;
                        interfaceC2641noA = interfaceC2641noA;
                        c3332x4 = c3332x4A;
                        iVar = iVarP0;
                        b4 = b4S;
                        arrayDeque = arrayDeque3;
                        v9 = v9;
                        if (next instanceof N3) {
                            c3579q2.a(next.w(), interfaceC2641noA);
                            iVarP0 = iVar;
                            c3332x4A = c3332x4;
                            interfaceC2641noA = interfaceC2641noA;
                            c3579q2 = c3579q2;
                            setC = setC;
                            v9 = v9;
                        }
                        arrayDeque3 = arrayDeque;
                        b4S = b4;
                        c2409kl2 = c2409kl2;
                    }
                    iVarP0 = iVar;
                    c3332x4A = c3332x4;
                    c3579q2 = c3579q2;
                    setC = setC;
                    v9 = v9;
                    interfaceC2641noA = interfaceC2641noA;
                    arrayDeque3 = arrayDeque;
                    b4S = b4;
                    c2409kl2 = c2409kl2;
                }
                interfaceC3558f0 = interfaceC3558f0;
                o5Var = o5Var;
            }
        }
        Set<C3332x4> set2 = setC;
        C3579q c3579q3 = c3579q2;
        C2409kl c2409kl3 = c2409kl2;
        ArrayDeque arrayDeque4 = arrayDeque3;
        V9 v10 = v9;
        boolean z2 = i;
        if (!z2 && !arrayDeque4.isEmpty()) {
            throw new AssertionError();
        }
        c3579q3.a(set2).a();
        v10.a();
        c2409kl3.b((Collection<C3332x4>) set2);
        c2409kl.y();
        if (!z2 && !c2409kl3.b(this.a)) {
            throw new AssertionError();
        }
    }

    public final V a(C2409kl c2409kl) {
        if (this.h == null) {
            if (!i && this.a.Y().k()) {
                throw new AssertionError();
            }
        } else {
            Iterator<C3332x4> it = c2409kl.d.iterator();
            while (it.hasNext()) {
                for (C2969s6 c2969s6 : it.next().n()) {
                    AbstractC0727q0 abstractC0727q0C = this.a.c(c2969s6.a);
                    if (abstractC0727q0C == null || abstractC0727q0C.f0()) {
                        if (this.h.a(c2969s6.a)) {
                            return V.c;
                        }
                    }
                }
            }
        }
        o5 o5VarH = c2409kl.h();
        if (this.a.Y().m() && a(o5VarH, c2409kl)) {
            return V.c;
        }
        V vA = V.d;
        C3550d0 c3550d0 = new C3550d0(this.a, AbstractC1476Wj.e());
        for (AbstractC2412ko abstractC2412ko : c2409kl.r()) {
            V vA2 = abstractC2412ko.a(c3550d0, o5VarH);
            V v = V.c;
            if (vA2 == v && abstractC2412ko.F1()) {
                vA2 = V.d;
            }
            if (vA2 == v) {
                return vA2;
            }
            vA = V.a(vA, vA2, (C0702m<?>) this.a);
        }
        return vA;
    }

    private void a(C2409kl c2409kl, B4 b4, C3332x4 c3332x4, final Set set, y3 y3Var) {
        C3332x4 c3332x5 = (C3332x4) L1.a((ListIterator) b4);
        final Set setC = OU.c();
        a(b4, c3332x4, new Consumer() { // from class: com.android.tools.r8.ir.optimize.c0$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3546c0.a(set, setC, (C3332x4) obj);
            }
        });
        a(c2409kl, b4, setC);
        a(b4, c3332x4, C3912j0.b());
        a(c2409kl, b4, setC, y3Var);
        a(b4, c3332x5, C3912j0.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, Set set2, C3332x4 c3332x4) {
        if (set.contains(c3332x4)) {
            return;
        }
        set2.add(c3332x4);
    }

    private void a(C2409kl c2409kl, B4 b4, Set set, y3 y3Var) {
        new C3577p(this.a).a(c2409kl, b4, new c0$$ExternalSyntheticLambda2(set), y3Var);
        if (!i && b4.hasNext()) {
            throw new AssertionError();
        }
    }

    private void a(C2409kl c2409kl, B4 b4, Set set) {
        Set setC = OU.c();
        C1763cQ c1763cQ = new C1763cQ(this.a);
        Objects.requireNonNull(set);
        c1763cQ.a(c2409kl, b4, setC, new c0$$ExternalSyntheticLambda2(set));
        if (!setC.isEmpty()) {
            new C3030t00(this.a, false).a((Iterable) setC);
        }
        if (!i && b4.hasNext()) {
            throw new AssertionError();
        }
    }

    private static void a(ListIterator listIterator, C3332x4 c3332x4, Consumer consumer) {
        C3332x4 c3332x5;
        while (listIterator.hasPrevious() && (c3332x5 = (C3332x4) listIterator.previous()) != c3332x4) {
            consumer.accept(c3332x5);
        }
        if (!i && L1.a(listIterator) != c3332x4) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.f.forEach(new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.c0$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0729q2) obj, (CN) obj2);
            }
        });
        this.f.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2, CN cn) {
        cn.removeIf(new Predicate() { // from class: com.android.tools.r8.ir.optimize.c0$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((o5) obj);
            }
        });
        if (cn.isEmpty()) {
            return;
        }
        c0729q2.Y().a(cn.a(new ZC$$ExternalSyntheticLambda0()));
        Iterator it = cn.iterator();
        while (it.hasNext()) {
            o5 o5Var = (o5) it.next();
            this.b.c(o5Var);
            this.g.add(o5Var.getReference());
        }
    }

    public final void a(C2844qM c2844qM, ExecutorService executorService, y3 y3Var) {
        C2844qM c2844qMA = c2844qM.a(this.a);
        C1749cD c1749cD = this.d;
        C0702m c0702m = this.a;
        c1749cD.getClass();
        c2844qMA.a(c1749cD.c(c0702m.E()).a(this.a, new Predicate() { // from class: com.android.tools.r8.ir.optimize.c0$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3546c0.a((o5) obj);
            }
        }));
        this.d.a();
        this.e.a(c2844qM, executorService, y3Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(o5 o5Var) {
        return o5Var.B().u();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(W0 w0) {
        return w0.i1().u();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, o5 o5Var) {
        if (!i && o5Var.e().q1() && o5Var.b(c0702m)) {
            throw new AssertionError();
        }
    }

    public final void a(C0684j2 c0684j2) {
        if (!i && !this.g.contains(c0684j2)) {
            throw new AssertionError();
        }
    }

    public static void a(C0702m c0702m) {
        Iterator it = c0702m.f().e().iterator();
        while (it.hasNext()) {
            for (W0 w0 : ((C0729q2) it.next()).M1()) {
                if (w0.q1()) {
                    w0.i1().y();
                }
            }
        }
    }
}
