package com.android.tools.r8.ir.regalloc;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.AF;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C1229Oa;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.WH;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class n {
    static final /* synthetic */ boolean i = true;
    private final C2409kl d;
    private final b e;
    private final C1229Oa f;
    private final HashMap a = new HashMap();
    private final HashMap b = new HashMap();
    private final HashMap c = new HashMap();
    private final HashMap g = new HashMap();
    private int h = 0;

    public n(C0702m c0702m, C2409kl c2409kl, b bVar) {
        this.e = bVar;
        this.d = c2409kl;
        this.f = AbstractC3472z00.a(c0702m, WH.i());
        for (C3332x4 c3332x4 : c2409kl.d) {
            this.g.put(Integer.valueOf(c3332x4.k().W0()), c3332x4);
        }
    }

    private void a(int i2, c cVar, c cVar2) {
        if (!i && i2 % 2 != 1) {
            throw new AssertionError();
        }
        ((Set) this.a.computeIfAbsent(Integer.valueOf(i2), new Function() { // from class: com.android.tools.r8.ir.regalloc.n$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return n.a((Integer) obj);
            }
        })).add(new m(a(cVar, cVar2), cVar, cVar2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set c(Integer num) {
        return new LinkedHashSet();
    }

    public final void b(int i2, c cVar, c cVar2) {
        if (!i && cVar.l() != cVar2.l()) {
            throw new AssertionError();
        }
        a(i2, cVar, cVar2);
    }

    public final void d(int i2, c cVar, c cVar2) {
        if (!i && cVar.l() != cVar2.l()) {
            throw new AssertionError();
        }
        c(i2, cVar, cVar2);
    }

    public final void e(int i2, c cVar, c cVar2) {
        if (!i && i2 % 2 != 1) {
            throw new AssertionError();
        }
        m mVar = new m(a(cVar, cVar2), cVar, cVar2);
        int iF = mVar.b.f();
        int iF2 = cVar.f();
        if (iF > iF2) {
            cVar.g(iF);
        } else {
            mVar.b.g(iF2);
        }
        ((Set) this.c.computeIfAbsent(Integer.valueOf(i2), new Function() { // from class: com.android.tools.r8.ir.regalloc.n$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return n.c((Integer) obj);
            }
        })).add(mVar);
    }

    public final void f(int i2, c cVar, c cVar2) {
        boolean z = i;
        if (!z && i2 % 2 != 1) {
            throw new AssertionError();
        }
        if (!z && cVar.l() != cVar2.l()) {
            throw new AssertionError();
        }
        if (((C3332x4) this.g.get(Integer.valueOf(i2 + 1))) == null) {
            S10 s10R = cVar2.r();
            AbstractC2412ko abstractC2412ko = s10R.b;
            if (abstractC2412ko != null && (abstractC2412ko instanceof AF) && cVar.m() == s10R.b.o0().W0() + 1) {
                c(i2, cVar, cVar2);
            } else {
                a(i2, cVar, cVar2);
            }
        }
    }

    private void c(int i2, c cVar, c cVar2) {
        if (!i && i2 % 2 != 1) {
            throw new AssertionError();
        }
        ((Set) this.b.computeIfAbsent(Integer.valueOf(i2), new Function() { // from class: com.android.tools.r8.ir.regalloc.n$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return n.b((Integer) obj);
            }
        })).add(new m(a(cVar, cVar2), cVar, cVar2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set b(Integer num) {
        return new LinkedHashSet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set d(Integer num) {
        return new LinkedHashSet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(Integer num) {
        return new LinkedHashSet();
    }

    public final int a(int i2) {
        for (C3332x4 c3332x4 : this.d.d) {
            InterfaceC2641no interfaceC2641noA = c3332x4.a(this.d);
            if (c3332x4 == this.d.j()) {
                while (interfaceC2641noA.hasNext() && interfaceC2641noA.o().m1()) {
                    interfaceC2641noA.next();
                }
                for (S10 s10T = this.e.e; s10T != null; s10T = s10T.t()) {
                    AbstractC2412ko abstractC2412ko = s10T.b;
                    int iW0 = abstractC2412ko.W0() - 1;
                    if (this.b.containsKey(Integer.valueOf(iW0)) || this.a.containsKey(Integer.valueOf(iW0)) || this.c.containsKey(Integer.valueOf(iW0))) {
                        a(i2, abstractC2412ko, interfaceC2641noA);
                    }
                }
            }
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko abstractC2412koO = interfaceC2641noA.o();
                if (!i && abstractC2412koO.m1()) {
                    throw new AssertionError();
                }
                int iW1 = abstractC2412koO.W0() - 1;
                if (this.b.containsKey(Integer.valueOf(iW1)) || this.a.containsKey(Integer.valueOf(iW1)) || this.c.containsKey(Integer.valueOf(iW1))) {
                    a(i2, abstractC2412koO, interfaceC2641noA);
                }
                interfaceC2641noA.next();
            }
        }
        return this.h;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set e(Integer num) {
        return new LinkedHashSet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set f(Integer num) {
        return new LinkedHashSet();
    }

    private AbstractC3472z00 a(c cVar, c cVar2) {
        AbstractC3472z00 abstractC3472z00W = cVar.r().w();
        AbstractC3472z00 abstractC3472z00W2 = cVar2.r().w();
        if (!abstractC3472z00W.I() && !abstractC3472z00W2.I()) {
            if (i || abstractC3472z00W == abstractC3472z00W2) {
                return abstractC3472z00W;
            }
            throw new AssertionError();
        }
        boolean z = i;
        if (!z && !abstractC3472z00W2.I() && !abstractC3472z00W2.K()) {
            throw new AssertionError();
        }
        if (z || abstractC3472z00W.I() || abstractC3472z00W.K()) {
            return this.f;
        }
        throw new AssertionError();
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0060  */
    /* JADX WARN: Code duplicated, block: B:7:0x0024  */
    private void a(int i2, AbstractC2412ko abstractC2412ko, InterfaceC2641no interfaceC2641no) {
        AbstractC2412ko abstractC2412koO;
        AbstractC1915eM abstractC1915eMY0;
        AbstractC1915eM abstractC1915eMY1;
        m mVar;
        m mVar2;
        m mVar3;
        int iW0 = abstractC2412ko.W0();
        if (interfaceC2641no.hasPrevious()) {
            AbstractC2412ko abstractC2412koJ = interfaceC2641no.j();
            abstractC2412koJ.getClass();
            if (abstractC2412koJ instanceof AF) {
                abstractC1915eMY1 = interfaceC2641no.j().Y0();
            } else {
                abstractC2412koO = interfaceC2641no.o();
                if (i && abstractC2412koO.W0() != iW0 && !abstractC2412ko.m1()) {
                    throw new AssertionError();
                }
                abstractC1915eMY0 = abstractC2412koO.Y0();
                if (abstractC1915eMY0.o() || !abstractC2412koO.M1()) {
                    abstractC1915eMY1 = abstractC1915eMY0;
                } else {
                    abstractC1915eMY1 = abstractC2412koO.P().K2().k().Y0();
                }
            }
        } else {
            abstractC2412koO = interfaceC2641no.o();
            if (i) {
            }
            abstractC1915eMY0 = abstractC2412koO.Y0();
            if (abstractC1915eMY0.o()) {
                abstractC1915eMY1 = abstractC1915eMY0;
            } else {
                abstractC1915eMY1 = abstractC1915eMY0;
            }
        }
        int i3 = iW0 - 1;
        Set set = (Set) this.a.computeIfAbsent(Integer.valueOf(i3), new Function() { // from class: com.android.tools.r8.ir.regalloc.n$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return n.d((Integer) obj);
            }
        });
        Iterator it = set.iterator();
        while (it.hasNext()) {
            m mVar4 = (m) it.next();
            if (mVar4.c.i() < this.e.c && mVar4.c.u()) {
                it.remove();
            }
        }
        Set set2 = (Set) this.b.computeIfAbsent(Integer.valueOf(i3), new Function() { // from class: com.android.tools.r8.ir.regalloc.n$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return n.e((Integer) obj);
            }
        });
        Iterator it2 = set2.iterator();
        while (it2.hasNext()) {
            m mVar5 = (m) it2.next();
            if (mVar5.c.i() < this.e.c && mVar5.c.u()) {
                it2.remove();
            }
        }
        Set set3 = (Set) this.c.computeIfAbsent(Integer.valueOf(i3), new Function() { // from class: com.android.tools.r8.ir.regalloc.n$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return n.f((Integer) obj);
            }
        });
        Iterator it3 = set.iterator();
        while (it3.hasNext()) {
            m mVar6 = (m) it3.next();
            c cVar = mVar6.c;
            Iterator it4 = set2.iterator();
            do {
                if (!it4.hasNext()) {
                    mVar = null;
                    break;
                }
                mVar = (m) it4.next();
            } while (mVar.b != cVar);
            int i4 = mVar6.b.i();
            int iO = mVar6.a.O();
            Iterator it5 = set.iterator();
            while (true) {
                if (!it5.hasNext()) {
                    mVar2 = null;
                    break;
                }
                m mVar7 = (m) it5.next();
                int i5 = mVar7.c.i();
                int iO2 = mVar7.a.O();
                Iterator it6 = it5;
                int i6 = 0;
                while (i6 < iO) {
                    int i7 = iO;
                    int i8 = 0;
                    while (true) {
                        int i9 = iO2;
                        if (i8 < iO2) {
                            m mVar8 = mVar7;
                            if (i5 + i8 == i4 + i6) {
                                mVar2 = mVar8;
                                break;
                            } else {
                                i8++;
                                iO2 = i9;
                                mVar7 = mVar8;
                            }
                        }
                    }
                    i6++;
                    iO = i7;
                }
                it5 = it6;
            }
            c cVar2 = mVar6.c;
            Iterator it7 = set3.iterator();
            do {
                if (!it7.hasNext()) {
                    mVar3 = null;
                    break;
                }
                mVar3 = (m) it7.next();
            } while (mVar3.b != cVar2);
            if (mVar != null && mVar2 == null && mVar3 == null) {
                it3.remove();
                mVar.b = mVar6.b;
            }
        }
        set2.addAll(set3);
        a(i2, set, interfaceC2641no, abstractC1915eMY1);
        a(i2, set2, interfaceC2641no, abstractC1915eMY1);
    }

    private void a(int i2, Set set, InterfaceC2641no interfaceC2641no, AbstractC1915eM abstractC1915eM) {
        h hVar = new h(interfaceC2641no, i2, abstractC1915eM);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            m mVar = (m) it.next();
            if (!mVar.c.z()) {
                if (mVar.b.z()) {
                    boolean z = i;
                    if (!z && this.e.c(mVar.c.i()) >= 256) {
                        throw new AssertionError();
                    }
                    AbstractC2412ko abstractC2412ko = mVar.b.r().b;
                    if (abstractC2412ko.r2()) {
                        hVar.a(new g(mVar.c.i(), mVar.a, abstractC2412ko));
                    } else if (!z && !abstractC2412ko.m1()) {
                        throw new AssertionError();
                    }
                }
                if (mVar.c.i() != mVar.b.i()) {
                    if (this.e.b().g() && mVar.b.r().J() && mVar.a.K() && this.e.c(mVar.c.i()) < 256) {
                        hVar.a(new g(mVar.c.i(), mVar.a, mVar.b.r().b));
                    } else {
                        hVar.a(new g(mVar.c.i(), mVar.b.i(), mVar.a));
                    }
                }
            }
        }
        hVar.b();
        this.h = Math.max(this.h, hVar.a());
    }
}
