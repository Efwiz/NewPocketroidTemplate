package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Yb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1523Yb extends AbstractC2359k7 {
    static final /* synthetic */ boolean e = true;
    private final C2413kp b;
    private final ArrayDeque c;
    private int d;

    public C1523Yb() {
        this(new C2413kp(), new ArrayDeque(), 0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, C2284j7 c2284j7) {
        C2284j7 c2284j7A = C2284j7.T().a(this.b).a(this.c).a();
        H6 h6C = interfaceC3107u6.c();
        h6C.getClass();
        E6 e6A = h6C.a(c2284j7A.W(), c2284j7.W());
        if (e6A instanceof G6) {
            e6A = h6C.a(c2284j7A.Z(), c2284j7.Z());
        }
        if (e6A instanceof F6) {
            return new C2325jh(e6A.a().c());
        }
        if (interfaceC3107u6.d()) {
            return this;
        }
        C2284j7 c2284j7A0 = c2284j7.a0();
        return new C1523Yb(c2284j7A0.X(), c2284j7A0.Y(), this.d);
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 b(InterfaceC3107u6 interfaceC3107u6, C2284j7 c2284j7) {
        E6 e6A = interfaceC3107u6.c().a(this.b, c2284j7.W());
        return e6A instanceof F6 ? new C2325jh(e6A.a().c()) : this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 c(InterfaceC3107u6 interfaceC3107u6, C2284j7 c2284j7) {
        E6 e6A = interfaceC3107u6.c().a(this.c, c2284j7.Z());
        return e6A instanceof F6 ? new C2325jh(e6A.a().c()) : this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3327x1
    public final Object clone() {
        return new C1523Yb(this.b.clone(), this.c.clone(), this.d);
    }

    @Override // com.android.tools.r8.internal.AbstractC3327x1
    /* JADX INFO: renamed from: d */
    public final AbstractC3327x1 clone() {
        return new C1523Yb(this.b.clone(), this.c.clone(), this.d);
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final C1523Yb e() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3327x1
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1523Yb.class != obj.getClass()) {
            return false;
        }
        C1523Yb c1523Yb = (C1523Yb) obj;
        return this.b.equals(c1523Yb.b) && AbstractC2734ot.a((Iterable) this.c, (Iterable) c1523Yb.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 h() {
        return C3258w5.k();
    }

    public final int hashCode() {
        return Objects.hash(this.b, this.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 i() {
        return a(new BiFunction() { // from class: com.android.tools.r8.internal.Yb$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C1523Yb.b((AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    public C1523Yb(C2413kp c2413kp, ArrayDeque arrayDeque, int i) {
        if (!e) {
            AbstractC2440l7.a(c2413kp);
        }
        this.b = c2413kp;
        this.c = arrayDeque;
        this.d = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 b(AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        boolean zM0;
        if (!interfaceC3054tM.M()) {
            zM0 = false;
        } else if (interfaceC3054tM.l()) {
            zM0 = true;
        } else if (interfaceC3054tM.G()) {
            zM0 = interfaceC3054tM.f().T().r();
        } else {
            if (!e && !interfaceC3054tM.N()) {
                throw new AssertionError();
            }
            zM0 = interfaceC3054tM.j().T().M0();
        }
        return zM0 ? abstractC2359k7 : AbstractC2359k7.a(interfaceC3054tM);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0047  */
    /* JADX WARN: Code duplicated, block: B:18:0x004b  */
    /* JADX WARN: Code duplicated, block: B:34:0x0097  */
    /* JADX WARN: Code duplicated, block: B:36:0x009b  */
    /* JADX WARN: Code duplicated, block: B:53:0x00b0 A[SYNTHETIC] */
    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(AbstractC1579a10 abstractC1579a10, C0767v2 c0767v2) {
        abstractC1579a10.getClass();
        if (abstractC1579a10 instanceof TH) {
            return new C2325jh("Unexpected attempt to initialize already initialized type");
        }
        YJ it = this.b.b().iterator();
        while (it.hasNext()) {
            InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) it.next();
            InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) interfaceC2806pp.getValue();
            if (interfaceC1348Si.r()) {
                AbstractC2826q4 abstractC2826q4K = interfaceC1348Si.K();
                C1298Ql c1298Ql = C2284j7.d;
                if (abstractC1579a10 instanceof C1883e10) {
                    abstractC2826q4K.getClass();
                    if (abstractC2826q4K instanceof C1883e10) {
                        abstractC2826q4K = InterfaceC1348Si.b(c0767v2);
                    } else if (abstractC1579a10 instanceof C1733c10) {
                        abstractC2826q4K.getClass();
                        if ((abstractC2826q4K instanceof C1733c10) && abstractC1579a10.R() == abstractC2826q4K.R()) {
                            abstractC2826q4K = InterfaceC1348Si.b(c0767v2);
                        }
                    }
                } else if (abstractC1579a10 instanceof C1733c10) {
                    abstractC2826q4K.getClass();
                    if (abstractC2826q4K instanceof C1733c10) {
                        abstractC2826q4K = InterfaceC1348Si.b(c0767v2);
                    }
                }
                interfaceC2806pp.setValue(abstractC2826q4K);
            }
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        for (InterfaceC1348Si interfaceC1348SiK : this.c) {
            if (interfaceC1348SiK.r()) {
                interfaceC1348SiK = interfaceC1348SiK.K();
                C1298Ql c1298Ql2 = C2284j7.d;
                if (abstractC1579a10 instanceof C1883e10) {
                    interfaceC1348SiK.getClass();
                    if (interfaceC1348SiK instanceof C1883e10) {
                        interfaceC1348SiK = InterfaceC1348Si.b(c0767v2);
                    } else if (abstractC1579a10 instanceof C1733c10) {
                        interfaceC1348SiK.getClass();
                        if (!(interfaceC1348SiK instanceof C1733c10) && abstractC1579a10.R() == interfaceC1348SiK.R()) {
                            interfaceC1348SiK = InterfaceC1348Si.b(c0767v2);
                        }
                    }
                } else if (abstractC1579a10 instanceof C1733c10) {
                    interfaceC1348SiK.getClass();
                    if (!(interfaceC1348SiK instanceof C1733c10)) {
                    }
                }
            }
            arrayDeque.addLast(interfaceC1348SiK);
        }
        return new C1523Yb(this.b, arrayDeque, this.d);
    }

    private C2325jh b(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM) {
        return AbstractC2359k7.a("The max stack height of " + interfaceC3107u6.a() + " is violated when pushing " + C2325jh.c(interfaceC3054tM) + " to existing stack of size " + this.d);
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 b(InterfaceC3107u6 interfaceC3107u6, C0767v2 c0767v2) {
        return new C1523Yb(new C2413kp(this.b), new ArrayDeque(), 0).a(interfaceC3107u6, InterfaceC1348Si.b(c0767v2));
    }

    public final void a(Consumer consumer, com.android.tools.r8.utils.A1 a1) {
        if (!this.c.isEmpty()) {
            consumer.accept((InterfaceC3054tM) this.c.peekLast());
        } else if (!e && !a1.I0().C) {
            throw new AssertionError();
        }
    }

    public final void a(int i, Consumer consumer, com.android.tools.r8.utils.A1 a1) {
        if (this.c.size() >= i) {
            ArrayDeque arrayDeque = new ArrayDeque(i);
            Iterator itDescendingIterator = this.c.descendingIterator();
            while (itDescendingIterator.hasNext() && i > 0) {
                arrayDeque.addFirst((InterfaceC3054tM) itDescendingIterator.next());
                i--;
            }
            consumer.accept(arrayDeque);
            return;
        }
        if (!e && !a1.I0().C) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(BiFunction biFunction) {
        if (this.c.isEmpty()) {
            return new C2325jh("Unexpected pop from empty stack");
        }
        InterfaceC3054tM interfaceC3054tM = (InterfaceC3054tM) this.c.removeLast();
        this.d -= interfaceC3054tM.t();
        return (AbstractC2359k7) biFunction.apply(this, interfaceC3054tM);
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(final C0684j2 c0684j2, final InterfaceC3107u6 interfaceC3107u6) {
        return a(new BiFunction() { // from class: com.android.tools.r8.internal.Yb$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(c0684j2, interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC2359k7 a(C0684j2 c0684j2, InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        C0767v2 c0767v2D;
        if (interfaceC3054tM.r()) {
            if (interfaceC3054tM.A()) {
                if (c0684j2.B0() == interfaceC3107u6.e().B0() || interfaceC3107u6.a(c0684j2.B0())) {
                    return abstractC2359k7.a(interfaceC3054tM.o(), interfaceC3107u6.e().B0());
                }
            } else if (interfaceC3054tM.y() && c0684j2.B0() == (c0767v2D = interfaceC3054tM.D())) {
                return abstractC2359k7.a(interfaceC3054tM.z(), c0767v2D);
            }
            boolean z = e;
            if (!z && !interfaceC3054tM.r()) {
                throw new AssertionError();
            }
            StringBuilder sb = new StringBuilder("Constructor mismatch, expected constructor from ");
            if (interfaceC3054tM.y()) {
                sb.append(interfaceC3054tM.D().L0());
            } else {
                if (!z && !interfaceC3054tM.A()) {
                    throw new AssertionError();
                }
                sb.append(interfaceC3107u6.e().B0().L0()).append(" or its superclass");
            }
            sb.append(", but was ").append(c0684j2.M0());
            return new C2325jh(sb.toString());
        }
        return a(interfaceC3054tM);
    }

    private static C2325jh a(InterfaceC3054tM interfaceC3054tM) {
        return AbstractC2359k7.a("Unexpected attempt to initialize " + C2325jh.c(interfaceC3054tM));
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(final C0702m c0702m, final C0767v2 c0767v2, InterfaceC3107u6 interfaceC3107u6, final BiFunction biFunction) {
        final H6 h6C = interfaceC3107u6.c();
        return a(new BiFunction() { // from class: com.android.tools.r8.internal.Yb$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C1523Yb.a(c0702m, h6C, c0767v2, biFunction, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 a(C0702m c0702m, H6 h6, C0767v2 c0767v2, BiFunction biFunction, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        if (interfaceC3054tM.a() && h6.b(interfaceC3054tM.a(c0702m.a()), c0767v2)) {
            return (AbstractC2359k7) biFunction.apply(abstractC2359k7, interfaceC3054tM);
        }
        return AbstractC2359k7.a(interfaceC3054tM, InterfaceC1348Si.a(c0767v2));
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(C0702m c0702m, InterfaceC3107u6 interfaceC3107u6, C0767v2... c0767v2Arr) {
        AbstractC2359k7 abstractC2359k7A = this;
        for (int length = c0767v2Arr.length - 1; length >= 0; length--) {
            abstractC2359k7A = abstractC2359k7A.a(c0702m, interfaceC3107u6, c0767v2Arr[length]);
        }
        return abstractC2359k7A;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, C0767v2 c0767v2) {
        return a(interfaceC3107u6, InterfaceC1348Si.a(c0767v2));
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, AbstractC3472z00 abstractC3472z00) {
        return a(interfaceC3107u6, InterfaceC1348Si.a(abstractC3472z00));
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM) {
        int iT = interfaceC3054tM.t() + this.d;
        if (iT > interfaceC3107u6.a()) {
            return b(interfaceC3107u6, interfaceC3054tM);
        }
        this.c.addLast(interfaceC3054tM);
        this.d = iT;
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(C0702m c0702m, InterfaceC3107u6 interfaceC3107u6, int i, EnumC1812d20 enumC1812d20, BiFunction biFunction) {
        InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) this.b.get(i);
        if (interfaceC1348Si == null) {
            return AbstractC2359k7.a("Unexpected read of missing local at index " + i);
        }
        if (interfaceC1348Si.a()) {
            if (interfaceC3107u6.c().a(interfaceC1348Si.a(c0702m.a()), enumC1812d20)) {
                return (AbstractC2359k7) biFunction.apply(this, interfaceC1348Si);
            }
        } else if (interfaceC1348Si.r() && enumC1812d20.a()) {
            return (AbstractC2359k7) biFunction.apply(this, interfaceC1348Si);
        }
        return AbstractC2359k7.a(interfaceC1348Si, enumC1812d20, i);
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(int i, InterfaceC1348Si interfaceC1348Si, InterfaceC3107u6 interfaceC3107u6) {
        if (com.android.tools.r8.utils.M.a(interfaceC1348Si.P()) + i >= interfaceC3107u6.b()) {
            StringBuilder sbAppend = new StringBuilder("The max locals of ").append(interfaceC3107u6.b()).append(" is violated when storing ").append(C2325jh.c(interfaceC1348Si)).append(" at local index ").append(i);
            if (interfaceC1348Si.P()) {
                sbAppend.append(" and ").append(i + 1);
            }
            return new C2325jh(sbAppend.toString());
        }
        AbstractC2440l7.a(i, interfaceC1348Si, this.b);
        return this;
    }

    public final AbstractC2359k7 a(C0702m c0702m, C1523Yb c1523Yb, UnaryOperator unaryOperator) {
        C2284j7.a aVarT = C2284j7.T();
        C2413kp c2413kp = c1523Yb.b;
        YJ it = this.b.b().iterator();
        YJ it2 = c2413kp.b().iterator();
        while (it.hasNext() && it2.hasNext()) {
            InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) it.next();
            int iA = interfaceC2806pp.a();
            InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) interfaceC2806pp.getValue();
            InterfaceC2806pp interfaceC2806pp2 = (InterfaceC2806pp) it2.next();
            int iA2 = interfaceC2806pp2.a();
            InterfaceC1348Si interfaceC1348Si2 = (InterfaceC1348Si) interfaceC2806pp2.getValue();
            if (iA < iA2) {
                if (!e && iA >= iA2) {
                    throw new AssertionError();
                }
                a(iA, aVarT);
                it2.previous();
            } else if (iA2 < iA) {
                if (!e && iA2 >= iA) {
                    throw new AssertionError();
                }
                a(iA2, aVarT);
                it.previous();
            } else if (interfaceC1348Si.e()) {
                if (interfaceC1348Si2.e()) {
                    aVarT.a(iA, interfaceC1348Si.v().a(c0702m, interfaceC1348Si2.v()));
                } else {
                    a(iA, aVarT);
                }
            } else if (interfaceC1348Si2.P()) {
                D20 d20P = interfaceC1348Si.p();
                D20 d20P2 = interfaceC1348Si2.p();
                if (d20P.w() != d20P2.w()) {
                    a(iA, aVarT);
                } else {
                    if (d20P == d20P2) {
                        aVarT.a(iA, d20P);
                    } else {
                        boolean z = e;
                        if (!z && aVarT.b(iA)) {
                            throw new AssertionError();
                        }
                        if (!z && aVarT.b(iA + 1)) {
                            throw new AssertionError();
                        }
                        a(iA, aVarT);
                        a(iA + 1, aVarT);
                    }
                    a(iA, d20P, it);
                    a(iA, d20P2, it2);
                }
            } else {
                a(iA, aVarT);
            }
        }
        if (it.hasNext()) {
            InterfaceC2806pp interfaceC2806pp3 = (InterfaceC2806pp) it.next();
            if (((InterfaceC1348Si) interfaceC2806pp3.getValue()).n()) {
                a(interfaceC2806pp3.a(), aVarT);
            } else {
                a(it, interfaceC2806pp3, aVarT, unaryOperator);
            }
            while (it.hasNext()) {
                a(it, (InterfaceC2806pp) it.next(), aVarT, unaryOperator);
            }
        }
        if (it2.hasNext()) {
            InterfaceC2806pp interfaceC2806pp4 = (InterfaceC2806pp) it2.next();
            if (((InterfaceC1348Si) interfaceC2806pp4.getValue()).n()) {
                a(interfaceC2806pp4.a(), aVarT);
            } else {
                a(it2, interfaceC2806pp4, aVarT, unaryOperator);
            }
            while (it2.hasNext()) {
                a(it2, (InterfaceC2806pp) it2.next(), aVarT, unaryOperator);
            }
        }
        C2325jh c2325jhA = a(c0702m, c1523Yb.c, aVarT);
        if (c2325jhA != null) {
            return c2325jhA;
        }
        C2284j7 c2284j7B = aVarT.b();
        return new C1523Yb(c2284j7B.X(), c2284j7B.Y(), this.d);
    }

    private static void a(int i, D20 d20, YJ yj) {
        boolean z = e;
        if (!z && !yj.hasNext()) {
            throw new AssertionError();
        }
        InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) yj.next();
        int iA = interfaceC2806pp.a();
        if (!z && iA != i + 1) {
            throw new AssertionError();
        }
        InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) interfaceC2806pp.getValue();
        if (!z && interfaceC1348Si != d20.T()) {
            throw new AssertionError();
        }
    }

    private static void a(YJ yj, InterfaceC2806pp interfaceC2806pp, C2284j7.a aVar, UnaryOperator unaryOperator) {
        int iA = interfaceC2806pp.a();
        InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) interfaceC2806pp.getValue();
        boolean z = e;
        if (!z && interfaceC1348Si.n()) {
            throw new AssertionError();
        }
        if (interfaceC1348Si.w()) {
            a(iA, interfaceC1348Si.p(), yj);
        }
        InterfaceC1348Si interfaceC1348Si2 = (InterfaceC1348Si) unaryOperator.apply(interfaceC1348Si);
        if (!z && interfaceC1348Si2.e() != interfaceC1348Si.e()) {
            throw new AssertionError();
        }
        if (!interfaceC1348Si2.q() && !interfaceC1348Si2.k()) {
            aVar.a(iA, interfaceC1348Si2);
            return;
        }
        if (interfaceC1348Si2.e()) {
            a(iA, aVar);
            return;
        }
        if (!z && aVar.b(iA)) {
            throw new AssertionError();
        }
        if (!z && aVar.b(iA + 1)) {
            throw new AssertionError();
        }
        a(iA, aVar);
        a(iA + 1, aVar);
    }

    private static void a(int i, C2284j7.a aVar) {
        if (!e && aVar.b(i)) {
            throw new AssertionError();
        }
        aVar.a(i, InterfaceC1348Si.B());
    }

    private C2325jh a(C0702m c0702m, ArrayDeque arrayDeque, C2284j7.a aVar) {
        InterfaceC3054tM interfaceC3054tMF;
        Iterator it = this.c.iterator();
        Iterator it2 = arrayDeque.iterator();
        int i = 0;
        while (it.hasNext() && it2.hasNext()) {
            InterfaceC3054tM interfaceC3054tM = (InterfaceC3054tM) it.next();
            InterfaceC3054tM interfaceC3054tM2 = (InterfaceC3054tM) it2.next();
            if (interfaceC3054tM.e() != interfaceC3054tM2.e()) {
                return AbstractC2359k7.a("Cannot join stacks, expected frame types at stack index " + i + " to have the same width, but was: " + C2325jh.c(interfaceC3054tM) + " and " + C2325jh.c(interfaceC3054tM2));
            }
            if (interfaceC3054tM.e()) {
                InterfaceC1348Si interfaceC1348SiA = interfaceC3054tM.v().a(c0702m, interfaceC3054tM2.v());
                if (((AbstractC2826q4) interfaceC1348SiA).q()) {
                    return a(i, interfaceC3054tM, interfaceC3054tM2);
                }
                if (!e && !interfaceC1348SiA.J()) {
                    throw new AssertionError();
                }
                interfaceC3054tMF = interfaceC1348SiA.F();
            } else {
                InterfaceC1348Si interfaceC1348SiA2 = interfaceC3054tM.s().a(interfaceC3054tM2.s());
                if (((AbstractC2826q4) interfaceC1348SiA2).k()) {
                    return a(i, interfaceC3054tM, interfaceC3054tM2);
                }
                if (!e && !interfaceC1348SiA2.J()) {
                    throw new AssertionError();
                }
                interfaceC3054tMF = interfaceC1348SiA2.F();
            }
            aVar.a(interfaceC3054tMF);
            i++;
        }
        if (it.hasNext() || it2.hasNext()) {
            return AbstractC2359k7.a("Cannot join stacks of different size");
        }
        return null;
    }

    private static C2325jh a(int i, InterfaceC3054tM interfaceC3054tM, InterfaceC3054tM interfaceC3054tM2) {
        return AbstractC2359k7.a("Cannot join stacks, expected frame types at stack index " + i + " to join to a precise (non-top) type, but types " + C2325jh.c(interfaceC3054tM) + " and " + C2325jh.c(interfaceC3054tM2) + " do not");
    }
}
