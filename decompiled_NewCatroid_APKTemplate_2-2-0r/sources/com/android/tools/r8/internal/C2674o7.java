package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0795x;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0771w;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.o7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2674o7 {
    static final /* synthetic */ boolean k = true;
    private final C0702m a;
    private final C0753u b;
    private final InterfaceC3107u6 c;
    private final InterfaceC2829q7 d;
    private final C0722p1 e;
    private final com.android.tools.r8.graph.o5 f;
    private final Optional g;
    private final boolean h;
    private final ArrayDeque i = new ArrayDeque();
    private final Set j;

    public C2674o7(C0702m c0702m, C0753u c0753u, InterfaceC3107u6 interfaceC3107u6, InterfaceC2829q7 interfaceC2829q7, com.android.tools.r8.graph.o5 o5Var, Optional optional, boolean z) {
        this.a = c0702m;
        this.b = c0753u;
        this.c = interfaceC3107u6;
        this.d = interfaceC2829q7;
        this.e = c0702m.a();
        this.f = o5Var;
        this.g = optional;
        this.h = z;
        this.j = c0753u.P0();
    }

    public static C2516m7 a(C0702m c0702m, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return new C2516m7(c0702m, c0753u, o5Var);
    }

    /* JADX WARN: Code duplicated, block: B:157:0x03a6  */
    /* JADX WARN: Code duplicated, block: B:159:0x03b0  */
    /* JADX WARN: Code duplicated, block: B:160:0x03b5  */
    /* JADX WARN: Code duplicated, block: B:162:0x03c3  */
    /* JADX WARN: Code duplicated, block: B:163:0x03c8  */
    /* JADX WARN: Code duplicated, block: B:165:0x03cc  */
    /* JADX WARN: Code duplicated, block: B:166:0x03d7  */
    /* JADX WARN: Code duplicated, block: B:168:0x03da  */
    /* JADX WARN: Code duplicated, block: B:169:0x03f4  */
    public final int a() {
        int i;
        com.android.tools.r8.utils.D3 d3B;
        C0771w c0771wA;
        int iJ0;
        int i2;
        B7 b7L;
        C2284j7 c2284j7I;
        com.android.tools.r8.utils.D3 d3A;
        if (!this.a.Y().N() || this.a.Y().n1.z0) {
            return 2;
        }
        com.android.tools.r8.graph.W0 w0E = this.f.e();
        if (w0E.p1() && w0E.Z0().e(Z8.g)) {
            return 2;
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        ArrayList<M7> arrayList = new ArrayList();
        boolean z = !this.b.Q0().isEmpty();
        Iterator<B7> it = this.b.I0().iterator();
        loop0: while (true) {
            if (!it.hasNext()) {
                if (!z || !identityHashMap.isEmpty()) {
                    d3B = com.android.tools.r8.utils.D3.b(identityHashMap);
                    break;
                }
                com.android.tools.r8.graph.o5 o5Var = this.f;
                C0702m c0702m = this.a;
                StringBuilder sb = new StringBuilder("Expected stack map table for method with non-linear control flow.");
                if (c0702m.q()) {
                    sb.append(" In later version of R8, the method may be assumed not reachable.");
                }
                d3B = com.android.tools.r8.utils.D3.a(new C0771w(o5Var.getOrigin(), c0702m.E().a((AbstractC1476Wj) null, o5Var.getReference()), sb.toString()));
                break;
            }
            B7 next = it.next();
            if (next.G()) {
                C2284j7 c2284j7I2 = next.i();
                if (arrayList.isEmpty()) {
                    if (next != this.b.l(0)) {
                        com.android.tools.r8.graph.o5 o5Var2 = this.f;
                        C0702m c0702m2 = this.a;
                        StringBuilder sb2 = new StringBuilder("Unexpected stack map frame without target");
                        if (c0702m2.q()) {
                            sb2.append(" In later version of R8, the method may be assumed not reachable.");
                        }
                        d3B = com.android.tools.r8.utils.D3.a(new C0771w(o5Var2.getOrigin(), c0702m2.E().a((AbstractC1476Wj) null, o5Var2.getReference()), sb2.toString()));
                        break;
                    }
                } else {
                    for (M7 m7 : arrayList) {
                        if (identityHashMap.containsKey(m7)) {
                            com.android.tools.r8.graph.o5 o5Var3 = this.f;
                            C0702m c0702m3 = this.a;
                            StringBuilder sb3 = new StringBuilder("Multiple frames for label");
                            if (c0702m3.q()) {
                                sb3.append(" In later version of R8, the method may be assumed not reachable.");
                            }
                            d3B = com.android.tools.r8.utils.D3.a(new C0771w(o5Var3.getOrigin(), c0702m3.E().a((AbstractC1476Wj) null, o5Var3.getReference()), sb3.toString()));
                            break loop0;
                        }
                        identityHashMap.put(m7, c2284j7I2);
                    }
                }
            }
            if (!(next instanceof C2051g8)) {
                if (next instanceof M7) {
                    arrayList.add(next.q());
                } else {
                    arrayList.clear();
                }
                if (!z) {
                    z = next.M() && !a(next);
                }
            }
        }
        if (d3B.f()) {
            if (!k && !d3B.f()) {
                throw new AssertionError();
            }
            this.d.a((C0771w) d3B.a().i());
            return 3;
        }
        final Map map = (Map) d3B.c().i();
        Iterator<W8> it2 = this.b.Q0().iterator();
        do {
            if (!it2.hasNext()) {
                c0771wA = null;
                break;
            }
            c0771wA = a(it2.next(), map);
        } while (c0771wA == null);
        if (c0771wA != null) {
            this.d.a(c0771wA);
            return 3;
        }
        this.a.a();
        AbstractC2359k7 c1523Yb = new C1523Yb();
        C0684j2 c0684j2 = (C0684j2) this.g.orElse(this.f.getReference());
        if (this.f.e().B1() || this.h) {
            c1523Yb = c1523Yb.a(0, c0684j2.b(this.a) ? InterfaceC1348Si.g() : InterfaceC1348Si.b(c0684j2.B0()), this.c);
            iJ0 = 1;
        } else {
            iJ0 = 0;
        }
        for (C0767v2 c0767v2 : c0684j2.G0()) {
            c1523Yb = c1523Yb.a(iJ0, InterfaceC1348Si.a(c0767v2), this.c);
            iJ0 += c0767v2.J0();
        }
        c1523Yb.getClass();
        com.android.tools.r8.utils.D3 d3A2 = c1523Yb instanceof C2325jh ? com.android.tools.r8.utils.D3.a(AbstractC0795x.a(this.f, 0, this.b.l(0), c1523Yb.f().k(), this.a)) : com.android.tools.r8.utils.D3.b(c1523Yb);
        if (d3A2.f()) {
            if (!k && !d3A2.f()) {
                throw new AssertionError();
            }
            this.d.a((C0771w) d3A2.a().i());
            return 3;
        }
        AbstractC2359k7 abstractC2359k7A = (AbstractC2359k7) d3A2.c().i();
        for (i = 0; i < this.b.I0().size(); i++) {
            B7 b7L2 = this.b.l(i);
            if (!k) {
                abstractC2359k7A.getClass();
                if (abstractC2359k7A instanceof C2325jh) {
                    throw new AssertionError();
                }
            }
            b7L2.getClass();
            if (b7L2 instanceof M7) {
                a(b7L2.q());
            } else if (this.a.Y().Y || b7L2.x()) {
                Iterator it3 = this.i.iterator();
                while (it3.hasNext()) {
                    Iterator<M7> it4 = ((W8) it3.next()).d.iterator();
                    while (it4.hasNext()) {
                        C2284j7 c2284j7 = (C2284j7) map.get(it4.next());
                        if (c2284j7 == null) {
                            abstractC2359k7A = AbstractC2359k7.a("No frame for target catch range target");
                            break;
                        }
                        abstractC2359k7A = abstractC2359k7A.b(this.c, c2284j7);
                    }
                }
                abstractC2359k7A.getClass();
                if (abstractC2359k7A instanceof C2325jh) {
                    this.d.a(AbstractC0795x.a(this.f, i, b7L2, abstractC2359k7A.f().k(), this.a));
                    return 3;
                }
            }
            this.d.a(b7L2, abstractC2359k7A);
            AbstractC2359k7 abstractC2359k7A2 = b7L2.a(abstractC2359k7A, this.a, this.c);
            if (b7L2.N()) {
                int i3 = i + 1;
                final B7 b7L3 = i3 < this.b.I0().size() ? this.b.l(i3) : null;
                abstractC2359k7A2 = (AbstractC2359k7) b7L2.a(new BiFunction() { // from class: com.android.tools.r8.internal.o7$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return this.f$0.a(b7L3, map, (B7) obj, (AbstractC2359k7) obj2);
                    }
                }, b7L3, abstractC2359k7A2).c().i();
            }
            if (!b7L2.M()) {
                d3A = com.android.tools.r8.utils.D3.b(abstractC2359k7A2);
            } else if (i == this.b.I0().size() - 1) {
                d3A = com.android.tools.r8.utils.D3.b(AbstractC2359k7.g());
            } else if (i == this.b.I0().size() - 2) {
                B7 b7L4 = this.b.l(i + 1);
                b7L4.getClass();
                if (b7L4 instanceof M7) {
                    d3A = com.android.tools.r8.utils.D3.b(AbstractC2359k7.g());
                } else if (b7L2.p().T()) {
                    d3A = com.android.tools.r8.utils.D3.b(abstractC2359k7A2);
                } else {
                    i2 = i + 1;
                    b7L = this.b.l(i2);
                    if (b7L.G()) {
                        c2284j7I = b7L.i();
                    } else if (b7L instanceof M7) {
                        c2284j7I = (C2284j7) map.get(b7L.q());
                    } else {
                        c2284j7I = null;
                    }
                    if (c2284j7I != null) {
                        C2284j7 c2284j7A0 = c2284j7I.a0();
                        d3A = com.android.tools.r8.utils.D3.b(new C1523Yb(c2284j7A0.X(), c2284j7A0.Y(), c2284j7A0.V()));
                    } else {
                        d3A = com.android.tools.r8.utils.D3.a(AbstractC0795x.a(this.f, i2, b7L, "Expected frame instruction", this.a));
                    }
                }
            } else if (b7L2.p().T()) {
                d3A = com.android.tools.r8.utils.D3.b(abstractC2359k7A2);
            } else {
                i2 = i + 1;
                b7L = this.b.l(i2);
                if (b7L.G()) {
                    c2284j7I = b7L.i();
                } else if (b7L instanceof M7) {
                    c2284j7I = (C2284j7) map.get(b7L.q());
                } else {
                    c2284j7I = null;
                }
                if (c2284j7I != null) {
                    C2284j7 c2284j7A1 = c2284j7I.a0();
                    d3A = com.android.tools.r8.utils.D3.b(new C1523Yb(c2284j7A1.X(), c2284j7A1.Y(), c2284j7A1.V()));
                } else {
                    d3A = com.android.tools.r8.utils.D3.a(AbstractC0795x.a(this.f, i2, b7L, "Expected frame instruction", this.a));
                }
            }
            if (!d3A.g()) {
                if (!k && !d3A.f()) {
                    throw new AssertionError();
                }
                this.d.a((C0771w) d3A.a().i());
                return 3;
            }
            abstractC2359k7A = (AbstractC2359k7) d3A.c().i();
            abstractC2359k7A.getClass();
            if (abstractC2359k7A instanceof C2325jh) {
                this.d.a(AbstractC0795x.a(this.f, i, b7L2, abstractC2359k7A.f().k(), this.a));
                return 3;
            }
        }
        return 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.utils.D3 a(B7 b7, Map map, B7 b8, AbstractC2359k7 abstractC2359k7) {
        if (b8 != b7) {
            if (!k) {
                b8.getClass();
                if (!(b8 instanceof M7)) {
                    throw new AssertionError();
                }
            }
            C2284j7 c2284j7 = (C2284j7) map.get(b8.q());
            if (c2284j7 != null) {
                abstractC2359k7 = abstractC2359k7.b(this.c, c2284j7).c(this.c, c2284j7);
            } else {
                abstractC2359k7 = AbstractC2359k7.a("No destination frame");
            }
        }
        return com.android.tools.r8.utils.D3.b(abstractC2359k7);
    }

    private void a(final M7 m7) {
        if (this.j.contains(m7)) {
            for (W8 w8 : this.b.Q0()) {
                if (w8.a == m7) {
                    this.i.add(w8);
                }
            }
            this.i.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.o7$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C2674o7.a(m7, (W8) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(M7 m7, W8 w8) {
        return w8.b == m7;
    }

    private C0771w a(W8 w8, Map map) {
        Iterator it = w8.a().iterator();
        while (it.hasNext()) {
            C2284j7 c2284j7 = (C2284j7) map.get((M7) it.next());
            if (c2284j7 == null) {
                return AbstractC0795x.a(this.f, w8, "No frame for target catch range target", this.a);
            }
            for (C0767v2 c0767v2 : w8.c) {
                if (!this.c.c().b(c0767v2, this.e.m3)) {
                    return AbstractC0795x.a(this.f, w8, "Could not assign " + c0767v2.L0() + " to java.lang.Throwable", this.a);
                }
                E6 e6A = this.c.c().a(C1298Ql.a(InterfaceC1348Si.b(c0767v2)), c2284j7.Z());
                if (e6A.b()) {
                    return AbstractC0795x.a(this.f, w8, e6A.a().c(), this.a);
                }
            }
        }
        return null;
    }

    private boolean a(B7 b7) {
        if (!b7.R() && !b7.Q()) {
            return false;
        }
        for (int size = this.b.I0().size() - 1; size >= 0; size--) {
            B7 b7L = this.b.l(size);
            if (b7L == b7) {
                return true;
            }
            if (!b7L.P() && !b7L.O()) {
                return false;
            }
        }
        throw new C3405y10("Instruction " + b7 + " should be in instructions");
    }
}
