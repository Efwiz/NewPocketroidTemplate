package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.AbstractC3578p0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class A4 implements InterfaceC2641no {
    static final /* synthetic */ boolean f = true;
    protected final C3332x4 a;
    protected final ListIterator b;
    protected AbstractC2412ko c;
    protected AbstractC1915eM d;
    private final C3155ul e;

    A4(C3155ul c3155ul, C3332x4 c3332x4) {
        this.d = null;
        this.a = c3332x4;
        this.b = c3332x4.p().listIterator();
        this.e = c3155ul;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        return c3332x5 == c3332x4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2) {
        return abstractC2412ko2 == abstractC2412ko;
    }

    @Override // java.util.ListIterator
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public final void add(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.a(this.a);
        if (!f && abstractC2412ko.h() != this.a) {
            throw new AssertionError();
        }
        if (this.d != null && !abstractC2412ko.e1()) {
            abstractC2412ko.b(this.d);
        }
        this.b.add(abstractC2412ko);
        this.e.a(abstractC2412ko);
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.b.hasNext();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2488lo, java.util.ListIterator
    public final boolean hasPrevious() {
        return this.b.hasPrevious();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.b.nextIndex();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.b.previousIndex();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void r() {
        this.d = null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no, com.android.tools.r8.internal.InterfaceC2488lo, java.util.Iterator, java.util.ListIterator
    public final void remove() {
        AbstractC2412ko abstractC2412ko = this.c;
        if (abstractC2412ko == null) {
            throw new IllegalStateException();
        }
        boolean z = f;
        if (!z && abstractC2412ko.b() != null && this.c.b().S()) {
            throw new AssertionError();
        }
        if (!z && !this.c.S0().isEmpty()) {
            throw new AssertionError();
        }
        for (int i = 0; i < this.c.b.size(); i++) {
            ((S10) this.c.b.get(i)).e(this.c);
        }
        Iterator it = this.c.S0().iterator();
        while (it.hasNext()) {
            ((S10) it.next()).d(this.c);
        }
        if (this.c.j() != null) {
            Iterator it2 = this.c.b().g().iterator();
            while (it2.hasNext()) {
                ((AbstractC2412ko) it2.next()).e(this.c.b());
            }
        }
        this.b.remove();
        this.c = null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void s() {
        AbstractC2412ko abstractC2412ko = this.c;
        if (abstractC2412ko == null) {
            throw new IllegalStateException();
        }
        if (abstractC2412ko.S0().isEmpty()) {
            remove();
        } else {
            a(new C1232Od(), (Set) null);
        }
    }

    @Override // java.util.ListIterator
    public final void set(AbstractC2412ko abstractC2412ko) {
        AbstractC2412ko abstractC2412ko2 = abstractC2412ko;
        abstractC2412ko2.a(this.a);
        if (!f && abstractC2412ko2.h() != this.a) {
            throw new AssertionError();
        }
        this.b.set(abstractC2412ko2);
        this.e.a(abstractC2412ko2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void v() {
        if (this.c == null) {
            throw new IllegalStateException();
        }
        this.b.remove();
        this.c = null;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final AbstractC2412ko next() {
        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) this.b.next();
        this.c = abstractC2412ko;
        return abstractC2412ko;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void b(C0702m c0702m, C2409kl c2409kl, C0759u2 c0759u2) {
        AbstractC2412ko abstractC2412ko = this.c;
        if (abstractC2412ko == null) {
            throw new IllegalStateException();
        }
        com.android.tools.r8.graph.X xJ = abstractC2412ko.j();
        c2409kl.getClass();
        a(new C1033Hc(c2409kl.a(xJ, AbstractC3472z00.b((C0702m<?>) c0702m, WH.b())), c0759u2), (Set) null);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.ListIterator
    public final AbstractC2412ko previous() {
        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) this.b.previous();
        this.c = abstractC2412ko;
        return abstractC2412ko;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(AbstractC1915eM abstractC1915eM) {
        this.d = abstractC1915eM;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, ListIterator listIterator, AbstractC2412ko abstractC2412ko, com.android.tools.r8.utils.A1 a1) {
        if (this.a.D()) {
            C3332x4 c3332x4A = a(c2409kl, listIterator, false);
            c3332x4A.a(c2409kl).add(abstractC2412ko);
            boolean z = f;
            if (!z && this.a.D()) {
                throw new AssertionError();
            }
            if (!z && !c3332x4A.D()) {
                throw new AssertionError();
            }
            this.a.a(c2409kl, listIterator, c3332x4A, a1);
            if (listIterator != null) {
                while (com.android.tools.r8.utils.L1.b(listIterator) != c3332x4A) {
                    listIterator.previous();
                }
            }
            return c3332x4A;
        }
        add(abstractC2412ko);
        return null;
    }

    A4(C3155ul c3155ul, C3332x4 c3332x4, int i) {
        this.d = null;
        this.a = c3332x4;
        this.b = c3332x4.p().listIterator(i);
        this.e = c3155ul;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(AbstractC2412ko abstractC2412ko, Set set) {
        AbstractC2412ko abstractC2412ko2 = this.c;
        if (abstractC2412ko2 != null) {
            Iterator it = abstractC2412ko2.b.iterator();
            while (it.hasNext()) {
                ((S10) it.next()).e(this.c);
            }
            if (this.c.g1()) {
                if (!f && abstractC2412ko.b() == null) {
                    throw new AssertionError();
                }
                if (set != null && abstractC2412ko.a() != this.c.a()) {
                    this.c.b().a(set);
                }
                this.c.b().g(abstractC2412ko.b());
            }
            this.c.c(abstractC2412ko);
            abstractC2412ko.a(this.a);
            if (!abstractC2412ko.e1()) {
                abstractC2412ko.b(this.c.Y0());
            }
            this.b.remove();
            this.b.add(abstractC2412ko);
            this.c.O0();
            this.e.a(abstractC2412ko);
            this.c = abstractC2412ko;
            return;
        }
        throw new IllegalStateException();
    }

    A4(C3155ul c3155ul, C3332x4 c3332x4, final AbstractC2412ko abstractC2412ko) {
        this(c3155ul, c3332x4);
        a(new Predicate() { // from class: com.android.tools.r8.internal.A4$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return A4.b(abstractC2412ko, (AbstractC2412ko) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final AbstractC1305Qs a(C0702m c0702m, C2409kl c2409kl, B4 b4, S10 s10, AbstractC1915eM abstractC1915eM) {
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        C0684j2 c0684j2 = c0702m.a().g4.e;
        int i = C2115gt.m;
        C2030ft c2030ft = (C2030ft) new C2030ft().a(c0684j2);
        c2030ft.getClass();
        C2030ft c2030ft2 = (C2030ft) c2030ft.a(AbstractC1639am.c(s10));
        c2030ft2.b = abstractC1915eM;
        C2115gt c2115gtB = ((C2030ft) c2030ft2.a()).b();
        add(c2115gtB);
        if (this.a.D()) {
            a(c2409kl, b4, a1Y, (UnaryOperator) null);
        }
        return c2115gtB;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x006c, code lost:
    
        if (r7.E().a((com.android.tools.r8.internal.AbstractC1476Wj) null, r3).b(r1) != false) goto L29;
     */
    @Override // com.android.tools.r8.internal.InterfaceC2641no
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean a(com.android.tools.r8.graph.C0702m r7, com.android.tools.r8.graph.o5 r8) {
        /*
            r6 = this;
            com.android.tools.r8.internal.ko r0 = r6.c
            boolean r1 = com.android.tools.r8.internal.A4.f
            if (r1 != 0) goto Lf
            if (r0 == 0) goto L9
            goto Lf
        L9:
            java.lang.AssertionError r7 = new java.lang.AssertionError
            r7.<init>()
            throw r7
        Lf:
            if (r1 != 0) goto L24
            boolean r1 = r0.P1()
            if (r1 != 0) goto L24
            boolean r1 = r0.Z1()
            if (r1 == 0) goto L1e
            goto L24
        L1e:
            java.lang.AssertionError r7 = new java.lang.AssertionError
            r7.<init>()
            throw r7
        L24:
            boolean r1 = r0.g1()
            r2 = 0
            if (r1 == 0) goto L2c
            return r2
        L2c:
            boolean r1 = r0.W1()
            if (r1 == 0) goto L6f
            com.android.tools.r8.graph.p1 r1 = r7.a()
            com.android.tools.r8.internal.Ls r3 = r0.Z()
            com.android.tools.r8.graph.j2 r3 = r3.S2()
            boolean r1 = r3.b(r1)
            if (r1 != 0) goto L6e
            com.android.tools.r8.graph.p1 r1 = r7.a()
            com.android.tools.r8.graph.u2 r4 = r3.C0()
            com.android.tools.r8.graph.u2 r5 = r1.a1
            r4.getClass()
            byte[] r5 = r5.e
            boolean r4 = r4.a(r5)
            if (r4 == 0) goto L6f
            com.android.tools.r8.graph.t0 r4 = r7.b(r3)
            if (r4 == 0) goto L6f
            com.android.tools.r8.internal.Wj r4 = r7.E()
            r5 = 0
            com.android.tools.r8.graph.j2 r3 = r4.a(r5, r3)
            boolean r1 = r3.b(r1)
            if (r1 == 0) goto L6f
        L6e:
            return r2
        L6f:
            com.android.tools.r8.internal.jo r1 = com.android.tools.r8.internal.C2332jo.e
            boolean r8 = r0.a(r7, r8, r1)
            if (r8 == 0) goto L78
            return r2
        L78:
            boolean r8 = r0.P1()
            if (r8 == 0) goto L87
            com.android.tools.r8.internal.Jn r8 = r0.S()
            com.android.tools.r8.internal.S10 r8 = r8.e()
            goto L8f
        L87:
            com.android.tools.r8.internal.Rs r8 = r0.c0()
            com.android.tools.r8.internal.S10 r8 = r8.U2()
        L8f:
            boolean r0 = r8.P()
            r1 = 1
            if (r0 == 0) goto L9a
            r6.s()
            return r1
        L9a:
            r6.a(r7, r8)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.A4.a(com.android.tools.r8.graph.m, com.android.tools.r8.graph.o5):boolean");
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final boolean a(C0702m c0702m, C2409kl c2409kl, C0767v2 c0767v2, Consumer consumer) {
        AbstractC2412ko abstractC2412ko = this.c;
        boolean z = f;
        if (!z && abstractC2412ko == null) {
            throw new AssertionError();
        }
        if (!z && !abstractC2412ko.v2() && !(abstractC2412ko instanceof C1721bt)) {
            throw new AssertionError();
        }
        if (abstractC2412ko.g1()) {
            return false;
        }
        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        if (!abstractC2412ko.c(c0702m, o5VarH)) {
            s();
            return true;
        }
        if (abstractC2412ko.a(c0702m, o5VarH, C2332jo.b)) {
            return false;
        }
        if (!c0767v2.a(o5VarH, c0702m)) {
            s();
            return true;
        }
        if (!c0702m.k()) {
            return false;
        }
        C0729q2 c0729q2A = C0729q2.a(c0702m.c(c0767v2));
        if (c0729q2A != null) {
            C2179hn c2179hn = new C2179hn(c0729q2A.d, c2409kl.a((AbstractC3472z00) AbstractC3472z00.k()));
            a(c2179hn, (Set) null);
            consumer.accept(c2179hn);
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, C2409kl c2409kl, C0767v2 c0767v2, com.android.tools.r8.graph.X x) {
        if (this.c != null) {
            WH whB = WH.b();
            int i = AbstractC3472z00.b;
            a(new C3446yc(c2409kl.a(x, AbstractC3472z00.a(c0702m.a().m2, whB, (C0702m<?>) c0702m).b()), c0767v2), (Set) null);
            return;
        }
        throw new IllegalStateException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C2409kl c2409kl, int i) {
        AbstractC2412ko abstractC2412ko = this.c;
        if (abstractC2412ko != null) {
            if (!f && abstractC2412ko.d1()) {
                AbstractC3472z00 abstractC3472z00A = this.c.a();
                abstractC3472z00A.getClass();
                if (!(abstractC3472z00A instanceof C1484Wr)) {
                    throw new AssertionError();
                }
            }
            a(c2409kl.a(i, this.c.j()), (Set) null);
            return;
        }
        throw new IllegalStateException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, S10 s10) {
        AbstractC2412ko abstractC2412ko = this.c;
        if (abstractC2412ko != null) {
            boolean z = f;
            if (!z && !abstractC2412ko.f1()) {
                throw new AssertionError();
            }
            if (!z && this.a.D() && !this.c.g()) {
                throw new AssertionError();
            }
            C0684j2 c0684j2 = c0702m.a().g4.e;
            int i = C2115gt.m;
            C2030ft c2030ft = (C2030ft) new C2030ft().a(c0684j2);
            c2030ft.getClass();
            a(((C2030ft) c2030ft.a(AbstractC1639am.c(s10))).b(), (Set) null);
            return;
        }
        throw new IllegalStateException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, C2409kl c2409kl, com.android.tools.r8.graph.Y0 y0, Set set) {
        if (this.c != null) {
            AbstractC3472z00 abstractC3472z00A = AbstractC3472z00.a(y0.h, WH.i(), (C0702m<?>) c0702m);
            AbstractC3472z00 abstractC3472z00A2 = this.c.a();
            S10 s10A = c2409kl.a(this.c.j(), abstractC3472z00A);
            a(new QW(y0, s10A), (Set) null);
            if (!s10A.y() || abstractC3472z00A.equals(abstractC3472z00A2)) {
                return;
            }
            set.addAll(s10A.a());
            return;
        }
        throw new IllegalStateException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(C0702m c0702m, C2409kl c2409kl, B4 b4, S10 s10, Set set, Set set2) {
        C3332x4 c3332x4A;
        InterfaceC2641no interfaceC2641noA;
        AbstractC2412ko abstractC2412ko = this.c;
        if (abstractC2412ko != null) {
            com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
            C3332x4 c3332x4H = abstractC2412ko.h();
            boolean z = f;
            if (!z && set.contains(c3332x4H)) {
                throw new AssertionError();
            }
            if (!z && set2 == null) {
                throw new AssertionError();
            }
            previous();
            if (c3332x4H.D() && !abstractC2412ko.g()) {
                c3332x4A = a(c2409kl, b4, a1Y, (UnaryOperator) null);
                c3332x4A.a(c2409kl).a(c2409kl, (ListIterator<C3332x4>) b4, true);
            } else {
                a(c2409kl, b4, a1Y, (UnaryOperator) null);
                c3332x4A = c3332x4H;
            }
            set.addAll(c3332x4A.a(c3332x4A.A(), new C0827Af(c2409kl, 2), set2));
            if (c3332x4A == c3332x4H) {
                previous();
                next();
                interfaceC2641noA = this;
            } else {
                interfaceC2641noA = c3332x4A.a(c2409kl, 1);
            }
            if (!z && interfaceC2641noA.hasNext()) {
                throw new AssertionError();
            }
            C1621aZ c1621aZ = new C1621aZ(s10);
            AbstractC1915eM abstractC1915eM = this.d;
            if (abstractC1915eM != null) {
                c1621aZ.b(abstractC1915eM);
            } else if (abstractC2412ko.Y0().s()) {
                c1621aZ.b(abstractC2412ko.Y0());
            } else {
                if (!z && abstractC2412ko.g()) {
                    throw new AssertionError();
                }
                c1621aZ.b(AbstractC1915eM.v());
            }
            interfaceC2641noA.d(c1621aZ);
            return;
        }
        throw new IllegalStateException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final void a(final C0702m c0702m, final C2409kl c2409kl, ListIterator listIterator, final Set set, final Set set2) {
        C3332x4 c3332x4A;
        InterfaceC2641no interfaceC2641noA;
        AbstractC2412ko abstractC2412ko = this.c;
        if (abstractC2412ko != null) {
            final C3332x4 c3332x4H = abstractC2412ko.h();
            boolean z = f;
            if (!z && set.contains(c3332x4H)) {
                throw new AssertionError();
            }
            if (!z && set2 == null) {
                throw new AssertionError();
            }
            previous();
            if (c3332x4H.D() && !abstractC2412ko.g()) {
                c3332x4A = a(c2409kl, listIterator, true);
                c3332x4A.a(c2409kl).a(c2409kl, (ListIterator<C3332x4>) listIterator);
            } else {
                a(c2409kl, listIterator, true);
                c3332x4A = c3332x4H;
            }
            if (!z && this.b.hasNext()) {
                throw new AssertionError();
            }
            previous();
            set.addAll(c3332x4A.a(c3332x4A.A(), new C0827Af(c2409kl, 2), set2));
            if (c3332x4A == c3332x4H) {
                interfaceC2641noA = this;
            } else {
                interfaceC2641noA = c3332x4A.a(c2409kl);
                interfaceC2641noA.a(this.d);
            }
            S10 s10A = interfaceC2641noA.a(c2409kl, c0702m.Y());
            interfaceC2641noA.next();
            if (!z && interfaceC2641noA.hasNext()) {
                throw new AssertionError();
            }
            C1621aZ c1621aZ = new C1621aZ(s10A);
            AbstractC1915eM abstractC1915eM = this.d;
            if (abstractC1915eM != null) {
                c1621aZ.b(abstractC1915eM);
            } else if (abstractC2412ko.Y0().s()) {
                c1621aZ.b(abstractC2412ko.Y0());
            } else {
                if (!z && abstractC2412ko.g()) {
                    throw new AssertionError();
                }
                c1621aZ.b(AbstractC1915eM.v());
            }
            interfaceC2641noA.d(c1621aZ);
            if (c3332x4H.D()) {
                if (c3332x4H == c3332x4A) {
                    c3332x4H.n().a(new BiConsumer() { // from class: com.android.tools.r8.internal.A4$$ExternalSyntheticLambda2
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            A4.a(set, c0702m, c2409kl, c3332x4H, set2, (C0767v2) obj, (C3332x4) obj2);
                        }
                    });
                    return;
                } else {
                    c3332x4A.a(c2409kl, listIterator, c3332x4H, c0702m.Y());
                    return;
                }
            }
            return;
        }
        throw new IllegalStateException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, C0702m c0702m, C2409kl c2409kl, C3332x4 c3332x4, Set set2, C0767v2 c0767v2, C3332x4 c3332x5) {
        if (!set.contains(c3332x5) && c0702m.a(c0702m.a().s3, c0767v2).a()) {
            set.addAll(c3332x4.a(c3332x5, new C0827Af(c2409kl, 2), set2));
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, ListIterator listIterator, boolean z) {
        LinkedList<C3332x4> linkedList = c2409kl.d;
        boolean z2 = f;
        if (!z2 && listIterator != null && com.android.tools.r8.utils.L1.b(listIterator) != this.a) {
            throw new AssertionError();
        }
        if (!z2 && !this.b.hasNext()) {
            throw new AssertionError();
        }
        AbstractC2412ko abstractC2412ko = this.c;
        AbstractC1915eM abstractC1915eMY0 = abstractC2412ko != null ? abstractC2412ko.Y0() : this.a.k().Y0();
        C3332x4 c3332x4A = this.a.a(c2409kl.f.a(), z);
        C3332x4 c3332x4 = this.a;
        C1296Qj c1296Qj = new C1296Qj();
        c1296Qj.a(c3332x4);
        this.b.add(c1296Qj);
        c1296Qj.b(abstractC1915eMY0);
        while (this.b.hasNext()) {
            AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) this.b.next();
            c3332x4A.p().addLast(abstractC2412ko2);
            abstractC2412ko2.a(c3332x4A);
            this.b.remove();
        }
        if (listIterator == null) {
            linkedList.add(linkedList.indexOf(this.a) + 1, c3332x4A);
        } else {
            listIterator.add(c3332x4A);
            listIterator.previous();
            listIterator.next();
        }
        return c3332x4A;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, int i, ListIterator listIterator) {
        C3332x4 c3332x4A = a(c2409kl, (ListIterator<C3332x4>) listIterator);
        if (!f && listIterator != null && com.android.tools.r8.utils.L1.b(listIterator) != c3332x4A) {
            throw new AssertionError();
        }
        InterfaceC2641no interfaceC2641noA = c3332x4A.a(c2409kl);
        for (int i2 = 0; i2 < i; i2++) {
            interfaceC2641noA.next();
        }
        interfaceC2641noA.a(c2409kl, (ListIterator<C3332x4>) listIterator);
        return c3332x4A;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C2409kl c2409kl, B4 b4, com.android.tools.r8.utils.A1 a1, UnaryOperator unaryOperator) {
        C3332x4 c3332x4A = a(c2409kl, (ListIterator) b4, false);
        if (!f && this.a.D()) {
            throw new AssertionError();
        }
        if (c3332x4A.D()) {
            this.a.a(c2409kl, b4, c3332x4A, a1);
        }
        if (unaryOperator != null) {
            b4.a((C3332x4) unaryOperator.apply(c3332x4A));
        }
        return c3332x4A;
    }

    private static void a(C0702m c0702m, C2409kl c2409kl, C3332x4 c3332x4, C3332x4 c3332x5, ListIterator listIterator) {
        C3332x4 c3332x4A;
        InterfaceC2641no interfaceC2641noA;
        InterfaceC2641no interfaceC2641noA2 = c3332x5.a(c2409kl);
        while (c3332x5 != null && interfaceC2641noA2.hasNext()) {
            boolean z = f;
            if (!z && c3332x5.D()) {
                throw new AssertionError();
            }
            if (((AbstractC2412ko) interfaceC2641noA2.a(new A4$$ExternalSyntheticLambda5())) != null) {
                if (interfaceC2641noA2.hasNext()) {
                    c3332x4A = interfaceC2641noA2.a(c2409kl, (ListIterator<C3332x4>) listIterator);
                    if (!z && c3332x4A.y().size() != 1) {
                        throw new AssertionError();
                    }
                    if (!z && c3332x5 != c3332x4A.y().get(0)) {
                        throw new AssertionError();
                    }
                    C3332x4 c3332x6 = (C3332x4) listIterator.previous();
                    if (!z && c3332x6 != c3332x4A) {
                        throw new AssertionError();
                    }
                } else {
                    c3332x4A = null;
                }
                c3332x5.a(c2409kl, listIterator, c3332x4, c0702m.Y());
                if (c3332x4A != null) {
                    C3332x4 c3332x7 = (C3332x4) listIterator.next();
                    if (!z && c3332x7 != c3332x4A) {
                        throw new AssertionError();
                    }
                    interfaceC2641noA = c3332x4A.a(c2409kl);
                } else {
                    interfaceC2641noA = null;
                }
                C3332x4 c3332x8 = c3332x4A;
                interfaceC2641noA2 = interfaceC2641noA;
                c3332x5 = c3332x8;
            } else {
                if (!z && interfaceC2641noA2.hasNext()) {
                    throw new AssertionError();
                }
                c3332x5 = null;
                interfaceC2641noA2 = null;
            }
        }
    }

    private static void a(InterfaceC2641no interfaceC2641no, S10 s10) {
        boolean z = f;
        if (!z && !interfaceC2641no.hasNext()) {
            throw new AssertionError();
        }
        AbstractC2412ko next = interfaceC2641no.next();
        if (!z && !next.m1()) {
            throw new AssertionError();
        }
        if (!z && next.b().S()) {
            throw new AssertionError();
        }
        if (!z && next.b() != s10) {
            throw new AssertionError();
        }
        interfaceC2641no.remove();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final C3332x4 a(C0702m c0702m, C2409kl c2409kl, C2409kl c2409kl2, ListIterator listIterator, Set set, C0729q2 c0729q2) {
        boolean z;
        int i;
        InterfaceC2641no interfaceC2641noA;
        int i2;
        C3332x4 c3332x4D0;
        final C3332x4 c3332x4;
        ListIterator b4 = listIterator;
        boolean z2 = f;
        if (!z2 && set == null) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        com.android.tools.r8.graph.o5 o5VarH2 = c2409kl2.h();
        if (o5VarH.a() != o5VarH2.a() && o5VarH2.e().K1()) {
            if (!z2 && !AbstractC3578p0.a(o5VarH.t(), o5VarH2.t(), c0702m)) {
                throw new AssertionError();
            }
            AbstractC3578p0.a(c0702m, o5VarH, c2409kl2);
        }
        InterfaceC2488lo interfaceC2488loQ = c2409kl2.q();
        while (true) {
            if (!interfaceC2488loQ.hasNext()) {
                z = false;
                break;
            }
            if (interfaceC2488loQ.next().g()) {
                z = true;
                break;
            }
        }
        C3332x4 c3332x4A = a(c2409kl, 1, b4);
        boolean z3 = f;
        if (!z3 && c3332x4A.p().size() != 2) {
            throw new AssertionError();
        }
        if (!z3 && !c3332x4A.p().getFirst().U1()) {
            throw new AssertionError();
        }
        AbstractC1084Is abstractC1084IsX = c3332x4A.p().getFirst().X();
        C3332x4 c3332x5 = c3332x4A.y().get(0);
        C3332x4 c3332x6 = c3332x4A.z().get(0);
        Set setC = OU.c();
        List<S10> listC = c2409kl2.c();
        if (!z3 && abstractC1084IsX.b.size() != listC.size()) {
            throw new AssertionError();
        }
        C3332x4 c3332x4J = c2409kl2.j();
        if (z3 || c0729q2 == null) {
            i = 0;
        } else {
            i = 0;
            if (!listC.get(0).Q()) {
                throw new AssertionError();
            }
        }
        if (c0729q2 != null && listC.get(i).S()) {
            S10 s10 = (S10) abstractC1084IsX.b.get(i);
            C3133uU c3133uU = new C3133uU(c2409kl.a(AbstractC3472z00.a(c0729q2.getType(), s10.w().N(), (C0702m<?>) c0702m)), s10, c0729q2.getType());
            c3133uU.b(abstractC1084IsX.Y0());
            if (c3332x4J.a()) {
                C3332x4 c3332x4A2 = c3332x4J.a(c2409kl).a(c2409kl2);
                interfaceC2641noA = c3332x4A2.a(c2409kl);
                c3332x4J.a(c2409kl).add(c3133uU);
                c3133uU.a(c3332x4J);
                if (!z3 && c3133uU.h().p().size() != 2) {
                    throw new AssertionError();
                }
                c3332x4J = c3332x4A2;
            } else {
                c3133uU.a(c3332x4J);
                interfaceC2641noA = c3332x4J.a(c2409kl);
                interfaceC2641noA.add(c3133uU);
            }
            S10 s11 = listC.get(0);
            setC.addAll(s11.a());
            s11.g(c3133uU.a);
            a(interfaceC2641noA, s11);
            i2 = 1;
        } else {
            interfaceC2641noA = c3332x4J.a(c2409kl);
            i2 = 0;
        }
        while (i2 < abstractC1084IsX.b.size()) {
            if (!f && listC.get(i2).A()) {
                throw new AssertionError();
            }
            S10 s12 = listC.get(i2);
            setC.addAll(s12.a());
            s12.g((S10) abstractC1084IsX.b.get(i2));
            a(interfaceC2641noA, s12);
            i2++;
        }
        boolean z4 = f;
        if (!z4 && !c3332x4J.p().stream().noneMatch(new A4$$ExternalSyntheticLambda3())) {
            throw new AssertionError();
        }
        new C3030t00(c0702m, false).a((Iterable) setC);
        C3332x4 c3332x4J2 = c2409kl2.j();
        List<C3332x4> listE = c2409kl2.e();
        if (listE.isEmpty()) {
            c3332x4D0 = null;
            c3332x4 = c3332x6;
        } else {
            InterfaceC2641no interfaceC2641noA2 = a(c0702m, c2409kl2, listE);
            if (!z4 && !interfaceC2641noA2.o().u2()) {
                throw new AssertionError();
            }
            if (abstractC1084IsX.b() != null) {
                AbstractC1048Hm abstractC1048HmA = abstractC1084IsX.b().a();
                C1616aU c1616aUB0 = interfaceC2641noA2.o().B0();
                abstractC1084IsX.b().g(c1616aUB0.L2());
                new C3030t00(c0702m, false).a(AbstractC0984Fi.a(AbstractC1639am.c(c1616aUB0.L2()), abstractC1048HmA));
            }
            C3332x4 c3332x4A3 = interfaceC2641noA2.a(c2409kl2);
            c3332x4D0 = c3332x4A3.d0();
            InterfaceC2641no interfaceC2641noA3 = c3332x4A3.a(c2409kl);
            interfaceC2641noA3.next();
            interfaceC2641noA3.remove();
            if (!z4 && interfaceC2641noA3.hasNext()) {
                throw new AssertionError();
            }
            c2409kl2.d.remove(c3332x4A3);
            c3332x4A.d0();
            InterfaceC2641no interfaceC2641noA4 = c3332x4A.a(c2409kl);
            interfaceC2641noA4.next();
            interfaceC2641noA4.remove();
            if (!z4 && !c3332x4A.p().getFirst().M1()) {
                throw new AssertionError();
            }
            c3332x4 = c3332x4A;
        }
        c3332x5.h(c3332x4J2);
        if (c3332x4D0 != null) {
            c3332x4D0.h(c3332x4);
        }
        if (b4 == null) {
            b4 = new B4(c2409kl, c2409kl.d.indexOf(c3332x4A));
        } else {
            listIterator.previous();
            listIterator.previous();
        }
        if (!z4 && com.android.tools.r8.utils.L1.a(b4) != c3332x4A) {
            throw new AssertionError();
        }
        for (C3332x4 c3332x7 : c2409kl2.d) {
            c3332x7.g(c2409kl.f.a());
            b4.add(c3332x7);
        }
        if (c3332x4A.D()) {
            for (int i3 = 0; i3 < c2409kl2.d.size(); i3++) {
                b4.previous();
            }
            if (!f && com.android.tools.r8.utils.L1.a(b4) != c2409kl2.j()) {
                throw new AssertionError();
            }
            for (C3332x4 c3332x8 : c2409kl2.d) {
                C3332x4 c3332x9 = (C3332x4) b4.next();
                if (!f && c3332x8 != c3332x9) {
                    throw new AssertionError();
                }
                if (c3332x8.D()) {
                    c3332x8.a(c2409kl, b4, c3332x4A, c0702m.Y());
                } else {
                    a(c0702m, c2409kl, c3332x4A, c3332x8, b4);
                }
            }
        }
        if (listE.isEmpty()) {
            if (!f && !z) {
                throw new AssertionError();
            }
            C0827Af c0827Af = new C0827Af(c2409kl, 2);
            Set setC2 = OU.c();
            set.addAll(c3332x5.a(c3332x4A, c0827Af, setC2));
            new C3030t00(c0702m, false).a((Iterable) setC2);
        }
        b4.next();
        boolean z5 = f;
        if (!z5 && com.android.tools.r8.utils.L1.b(b4) != c3332x4A) {
            throw new AssertionError();
        }
        if (!z5 && c3332x4 != c3332x4A && !com.android.tools.r8.utils.L1.a(b4, new Predicate() { // from class: com.android.tools.r8.internal.A4$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return A4.a(c3332x4, (C3332x4) obj);
            }
        })) {
            throw new AssertionError();
        }
        c2409kl.t().a(c2409kl2.t());
        return c3332x4;
    }

    private InterfaceC2641no a(C0702m c0702m, C2409kl c2409kl, List list) {
        boolean z;
        S10 s10;
        C1616aU c1616aU;
        if (list.size() == 1) {
            InterfaceC2641no interfaceC2641noA = ((C3332x4) list.get(0)).a(c2409kl);
            interfaceC2641noA.a(new A4$$ExternalSyntheticLambda1());
            interfaceC2641noA.previous();
            return interfaceC2641noA;
        }
        C3332x4 c3332x4 = new C3332x4();
        c3332x4.g(c2409kl.f.a());
        if (((C3332x4) list.get(0)).m().B0().K2()) {
            c1616aU = new C1616aU();
        } else {
            ArrayList arrayList = new ArrayList(list.size());
            Iterator it = list.iterator();
            loop1: while (true) {
                z = true;
                while (true) {
                    if (!it.hasNext()) {
                        break loop1;
                    }
                    S10 s10L2 = ((C3332x4) it.next()).m().B0().L2();
                    arrayList.add(s10L2);
                    if (!z || s10L2 != arrayList.get(0)) {
                        z = false;
                    }
                }
            }
            if (z) {
                s10 = (S10) arrayList.get(0);
            } else {
                ML ml = new ML(c2409kl.e.a(), c3332x4, AbstractC3472z00.f(), null, ML.a.a);
                ml.a((List<S10>) arrayList);
                new C3030t00(c0702m, false).a((Set) AbstractC1048Hm.c(ml));
                s10 = ml;
            }
            c1616aU = new C1616aU(s10);
        }
        c1616aU.b(AbstractC1915eM.u());
        c3332x4.a(c1616aU, this.e);
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            C3332x4 c3332x5 = (C3332x4) it2.next();
            InterfaceC2641no interfaceC2641noA2 = c3332x5.a(c2409kl, c3332x5.p().size());
            AbstractC2412ko abstractC2412koPrevious = interfaceC2641noA2.previous();
            if (!f && !abstractC2412koPrevious.u2()) {
                throw new AssertionError();
            }
            interfaceC2641noA2.d(new C1296Qj());
            c3332x5.h(c3332x4);
        }
        c3332x4.a((C2102gl) null);
        c2409kl.d.add(c3332x4);
        return c3332x4.a(c2409kl);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final S10 a(C2409kl c2409kl, com.android.tools.r8.utils.A1 a1, long j, AbstractC3472z00 abstractC3472z00) {
        AbstractC1915eM abstractC1915eMU;
        c2409kl.getClass();
        C0978Fc c0978Fc = new C0978Fc(c2409kl.a((com.android.tools.r8.graph.X) null, abstractC3472z00), j);
        if (this.d == null) {
            if (a1.W0) {
                AbstractC2412ko abstractC2412ko = this.c;
                abstractC1915eMU = abstractC2412ko != null ? abstractC2412ko.Y0() : this.a.k().Y0();
            } else {
                abstractC1915eMU = AbstractC1915eM.u();
            }
            c0978Fc.b(abstractC1915eMU);
        }
        add(c0978Fc);
        return c0978Fc.b();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2641no
    public final S10 a(C0702m c0702m, C2409kl c2409kl, C0759u2 c0759u2) {
        c2409kl.getClass();
        C1033Hc c1033Hc = new C1033Hc(c2409kl.a((com.android.tools.r8.graph.X) null, AbstractC3472z00.b((C0702m<?>) c0702m, WH.b())), c0759u2);
        c1033Hc.b(c0702m.Y().W0 ? this.c.Y0() : AbstractC1915eM.u());
        add(c1033Hc);
        return c1033Hc.b();
    }
}
