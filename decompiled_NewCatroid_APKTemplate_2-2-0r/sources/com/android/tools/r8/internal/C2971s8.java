package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.s8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2971s8 implements com.android.tools.r8.ir.regalloc.f {
    static final /* synthetic */ boolean n = true;
    private final C0702m a;
    private final C2409kl b;
    private final Q00 c;
    private IdentityHashMap d;
    private final HashMap e = new HashMap();
    private final ArrayList f = new ArrayList();
    private final LinkedList g = new LinkedList();
    private final LinkedList h = new LinkedList();
    private final PriorityQueue i = new PriorityQueue();
    private TreeSet j = new TreeSet();
    private int k = 0;
    private int l = 0;
    private int m = -1;

    public C2971s8(C0702m c0702m, C2409kl c2409kl, Q00 q00) {
        this.a = c0702m;
        this.b = c2409kl;
        this.c = q00;
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final int a(S10 s10, int i) {
        return a(s10);
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final int b(S10 s10, int i) {
        return a(s10);
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final C0702m c() {
        return this.a;
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final com.android.tools.r8.graph.o5 d() {
        return this.b.h();
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final int e() {
        return this.l + 1;
    }

    public final int a(S10 s10) {
        if (s10 instanceof C2723oi) {
            return ((C2723oi) s10).a(this);
        }
        if (n || !s10.r().s()) {
            return s10.r().i();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final com.android.tools.r8.utils.A1 b() {
        return this.a.Y();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C2900r8 b(C3332x4 c3332x4) {
        LinkedHashSet<S10> linkedHashSet = ((C2329jl) this.d.get(c3332x4)).a;
        C1303Qq c1303Qq = new C1303Qq(linkedHashSet.size());
        for (S10 s10 : linkedHashSet) {
            c1303Qq.a(a(s10), this.c.b(s10));
        }
        Deque deque = ((C2329jl) this.d.get(c3332x4)).c;
        ArrayList arrayList = new ArrayList(deque.size());
        Iterator it = deque.iterator();
        while (it.hasNext()) {
            arrayList.add(this.c.b((S10) it.next()));
        }
        return new C2900r8(c1303Qq, arrayList);
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final void a() {
        int iA;
        Iterator<AbstractC2412ko> it = this.b.r().iterator();
        while (it.hasNext()) {
            S10 s10B = it.next().b();
            if (s10B != null) {
                s10B.a(true ^ ((s10B instanceof C2617nW) || (s10B instanceof C2708oW)));
            }
        }
        AbstractC1639am abstractC1639amV = this.b.v();
        IdentityHashMap identityHashMapD = this.b.d();
        this.d = identityHashMapD;
        com.android.tools.r8.ir.regalloc.b.a(this.a, this.b, identityHashMapD, this.f);
        this.i.addAll(this.f);
        while (!this.i.isEmpty() && ((com.android.tools.r8.ir.regalloc.c) this.i.peek()).r().H()) {
            com.android.tools.r8.ir.regalloc.c cVar = (com.android.tools.r8.ir.regalloc.c) this.i.poll();
            a(cVar, a(cVar.o().b()));
        }
        this.m = this.k - 1;
        while (!this.i.isEmpty()) {
            com.android.tools.r8.ir.regalloc.c cVar2 = (com.android.tools.r8.ir.regalloc.c) this.i.poll();
            if (!n && cVar2.r().H()) {
                throw new AssertionError();
            }
            int iM = cVar2.m();
            Iterator it2 = this.g.iterator();
            while (it2.hasNext()) {
                com.android.tools.r8.ir.regalloc.c cVar3 = (com.android.tools.r8.ir.regalloc.c) it2.next();
                if (iM >= cVar3.c()) {
                    it2.remove();
                    if (!this.a.Y().n1.b) {
                        int i = cVar3.i();
                        this.j.add(Integer.valueOf(i));
                        if (cVar3.o().b()) {
                            this.j.add(Integer.valueOf(i + 1));
                        }
                    }
                } else if (cVar3.f(iM)) {
                    continue;
                } else {
                    if (!n && cVar3.i() == Integer.MIN_VALUE) {
                        throw new AssertionError();
                    }
                    it2.remove();
                    this.h.add(cVar3);
                    if (!this.a.Y().n1.b) {
                        int i2 = cVar3.i();
                        this.j.add(Integer.valueOf(i2));
                        if (cVar3.o().b()) {
                            this.j.add(Integer.valueOf(i2 + 1));
                        }
                    }
                }
            }
            Iterator it3 = this.h.iterator();
            while (it3.hasNext()) {
                com.android.tools.r8.ir.regalloc.c cVar4 = (com.android.tools.r8.ir.regalloc.c) it3.next();
                if (iM >= cVar4.c()) {
                    it3.remove();
                } else if (cVar4.f(iM)) {
                    it3.remove();
                    if (!n && cVar4.i() == Integer.MIN_VALUE) {
                        throw new AssertionError();
                    }
                    this.g.add(cVar4);
                    int i3 = cVar4.i();
                    this.j.remove(Integer.valueOf(i3));
                    if (cVar4.o().b()) {
                        this.j.remove(Integer.valueOf(i3 + 1));
                    }
                } else {
                    continue;
                }
            }
            if (cVar2.e() != null) {
                boolean zB = cVar2.o().b();
                int iIntValue = cVar2.e().intValue();
                if (this.j.contains(Integer.valueOf(iIntValue)) && (!zB || this.j.contains(Integer.valueOf(iIntValue + 1)))) {
                    Iterator it4 = this.h.iterator();
                    while (true) {
                        if (it4.hasNext()) {
                            com.android.tools.r8.ir.regalloc.c cVar5 = (com.android.tools.r8.ir.regalloc.c) it4.next();
                            if (!cVar5.a(iIntValue, zB) || cVar5.d(cVar2) == -1) {
                            }
                        } else {
                            a(cVar2, cVar2.e().intValue());
                        }
                    }
                }
            }
            boolean zB2 = cVar2.o().b();
            TreeSet treeSet = new TreeSet((SortedSet) this.j);
            while (true) {
                iA = a(zB2);
                Iterator it5 = this.h.iterator();
                while (true) {
                    if (!it5.hasNext()) {
                        break;
                    }
                    com.android.tools.r8.ir.regalloc.c cVar6 = (com.android.tools.r8.ir.regalloc.c) it5.next();
                    if (cVar6.a(iA, zB2) && cVar2.d(cVar6) != -1) {
                        this.j.remove(Integer.valueOf(iA));
                    }
                }
            }
            this.j = treeSet;
            a(cVar2, iA);
        }
        if (this.a.Y().W0) {
            com.android.tools.r8.ir.regalloc.b.a(this.b, abstractC1639amV, this.f, this, this.d);
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x008e  */
    @Override // com.android.tools.r8.ir.regalloc.f
    public final void b(C3332x4 c3332x4, C3332x4 c3332x5) {
        P00 p00A;
        C2900r8 c2900r8A = a(c3332x4);
        C2900r8 c2900r8A2 = a(c3332x5);
        boolean z = n;
        if (!z && c2900r8A.a.size() != c2900r8A2.a.size()) {
            throw new AssertionError();
        }
        a(c2900r8A.a, c2900r8A2.a);
        if (!z && c2900r8A.b.size() != c2900r8A2.b.size()) {
            throw new AssertionError();
        }
        List list = c2900r8A.b;
        List list2 = c2900r8A2.b;
        if (list.isEmpty()) {
            list.addAll(list2);
            return;
        }
        if (!z && list.size() != list2.size()) {
            throw new AssertionError();
        }
        for (int i = 0; i < list.size(); i++) {
            P00 p00 = (P00) list.get(i);
            Q00 q00 = this.c;
            P00 p01 = (P00) list2.get(i);
            q00.getClass();
            if (p00 == p01) {
                p00A = p00;
            } else {
                C0767v2 c0767v2A = p00.a();
                C0767v2 c0767v2A2 = p01.a();
                if ((p00 instanceof M00) && (p01 instanceof M00) && c0767v2A == c0767v2A2) {
                    p00A = p00;
                } else {
                    if (!Q00.j && (c0767v2A.W0() || c0767v2A2.W0())) {
                        throw new AssertionError();
                    }
                    p00A = q00.a(q00.a(AbstractC1048Hm.a(c0767v2A, c0767v2A2)));
                }
            }
            if (p00A != p00) {
                list.set(i, p00A);
            }
        }
    }

    private int a(boolean z) {
        if (this.j.isEmpty()) {
            return this.k;
        }
        if (z) {
            for (Integer num : this.j) {
                if (this.j.contains(Integer.valueOf(num.intValue() + 1)) || this.k == num.intValue() + 1) {
                    if (num.intValue() != this.m) {
                        return num.intValue();
                    }
                }
            }
            return this.k;
        }
        return ((Integer) this.j.first()).intValue();
    }

    private void a(com.android.tools.r8.ir.regalloc.c cVar, int i) {
        cVar.j(i);
        int i2 = cVar.i();
        this.j.remove(Integer.valueOf(i2));
        if (cVar.o().b()) {
            this.j.remove(Integer.valueOf(i2 + 1));
        }
        int i3 = (cVar.o().b() ? 1 : 0) + i;
        if (i3 >= this.k) {
            this.k = i3 + 1;
        }
        this.l = Math.max(this.l, i3);
        for (ML ml : cVar.r().g0()) {
            if (!ml.T() && ml.r().e() == null) {
                ml.r().a(cVar, this.i);
                Iterator<S10> it = ml.k0().iterator();
                while (it.hasNext()) {
                    it.next().r().a(cVar, this.i);
                }
            }
        }
        this.g.add(cVar);
    }

    public final void a(C3332x4 c3332x4, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ML ml = (ML) it.next();
            if (ml.T()) {
                ((C2329jl) this.d.get(c3332x4)).c.addLast(ml);
            } else {
                ((C2329jl) this.d.get(c3332x4)).a.add(ml);
            }
        }
    }

    public final C2900r8 a(C3332x4 c3332x4) {
        return (C2900r8) this.e.computeIfAbsent(c3332x4, new Function() { // from class: com.android.tools.r8.internal.s8$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((C3332x4) obj);
            }
        });
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        if (!Objects.equals(c3332x4.q(), c3332x5.q())) {
            return false;
        }
        List list = a(c3332x4).b;
        List list2 = a(c3332x5).b;
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (((P00) list.get(i)).a() != ((P00) list2.get(i)).a()) {
                return false;
            }
        }
        AbstractC2412ko abstractC2412koK = c3332x4.k();
        abstractC2412koK.getClass();
        boolean z = abstractC2412koK instanceof AF;
        AbstractC2412ko abstractC2412koK2 = c3332x5.k();
        abstractC2412koK2.getClass();
        if (z != (abstractC2412koK2 instanceof AF)) {
            return false;
        }
        AbstractC2412ko abstractC2412koK3 = c3332x4.k();
        abstractC2412koK3.getClass();
        return !(abstractC2412koK3 instanceof AF) || this.c.b(c3332x4.k().b()).a() == this.c.b(c3332x5.k().b()).a();
    }

    /* JADX WARN: Code duplicated, block: B:138:0x02ba  */
    @Override // com.android.tools.r8.ir.regalloc.f
    public final void a(C3332x4 c3332x4, int i, List list) {
        int i2;
        InterfaceC0992Fq c1303Qq;
        P00 p00A;
        C1303Qq c1303Qq2 = new C1303Qq(16);
        ArrayList arrayList = new ArrayList();
        if (!n && list.isEmpty()) {
            throw new AssertionError();
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C3332x4 c3332x5 = (C3332x4) it.next();
            C2900r8 c2900r8A = a(c3332x5);
            C1303Qq c1303Qq3 = new C1303Qq(c2900r8A.a);
            ArrayDeque arrayDeque = new ArrayDeque(c2900r8A.b);
            int size = c3332x5.p().size() - i;
            InterfaceC2488lo interfaceC2488loR = c3332x5.R();
            while (true) {
                size--;
                if (size < 0 || !interfaceC2488loR.hasNext()) {
                    break;
                }
                AbstractC2412ko next = interfaceC2488loR.next();
                if (next.b() != null && !((ArrayList) next.h1()).isEmpty()) {
                    if (next instanceof C3044tC) {
                        int iA = a((S10) ((ArrayList) next.h1()).get(0));
                        if (!n && !c1303Qq3.a(iA)) {
                            throw new AssertionError();
                        }
                        arrayDeque.addLast((P00) c1303Qq3.get(iA));
                    } else if (next instanceof SW) {
                        c1303Qq3.a(a(next.b()), (P00) arrayDeque.removeLast());
                    } else if (next instanceof ZL) {
                        arrayDeque.removeLast();
                    } else if (next instanceof C1099Jf) {
                        arrayDeque.addLast((P00) arrayDeque.getLast());
                    } else if (next instanceof C1068If) {
                        P00 p00 = (P00) arrayDeque.removeLast();
                        P00 p01 = (P00) arrayDeque.getLast();
                        arrayDeque.addLast(p00);
                        arrayDeque.addLast(p01);
                        arrayDeque.addLast(p00);
                    } else if (next instanceof HY) {
                        P00 p02 = (P00) arrayDeque.removeLast();
                        P00 p03 = (P00) arrayDeque.removeLast();
                        arrayDeque.addLast(p02);
                        arrayDeque.addLast(p03);
                    } else if (next instanceof C1289Qd) {
                        c1303Qq3.a(a(next.b()), (P00) arrayDeque.removeLast());
                    }
                }
                for (int size2 = ((ArrayList) next.h1()).size() - 1; size2 >= 0; size2--) {
                    if (((S10) ((ArrayList) next.h1()).get(size2)).T()) {
                        arrayDeque.removeLast();
                    }
                }
                S10 s10B = next.b();
                if (s10B == null) {
                    continue;
                } else {
                    P00 p00B = this.c.b(s10B);
                    if (!n && p00B == null) {
                        throw new AssertionError();
                    }
                    if (s10B.Y()) {
                        c1303Qq3.a(a(s10B), p00B);
                    } else if (s10B.T()) {
                        arrayDeque.addLast(p00B);
                    } else {
                        throw new C3405y10();
                    }
                }
            }
            if (c3332x5.z().isEmpty()) {
                c1303Qq = new C1303Qq(16);
            } else {
                if (!n && c3332x5.z().size() != 1) {
                    throw new AssertionError();
                }
                c1303Qq = a(c3332x5.z().get(0)).a;
            }
            Lr lr = new Lr(c1303Qq.size() * 2);
            lr.a(c1303Qq.keySet());
            C3479z4 c3479z4D = c3332x5.d(c3332x5.p().size());
            int i3 = i;
            while (true) {
                i3--;
                if (i3 < 0 || !c3479z4D.hasPrevious()) {
                    break;
                }
                AbstractC2412ko abstractC2412koPrevious = c3479z4D.previous();
                S10 s10B2 = abstractC2412koPrevious.b();
                if (s10B2 != null && s10B2.Y()) {
                    int iA2 = a(s10B2);
                    if (!n && !lr.f(iA2)) {
                        throw new AssertionError();
                    }
                    lr.k(iA2);
                }
                for (S10 s10 : (ArrayList) abstractC2412koPrevious.h1()) {
                    if (s10.Y()) {
                        lr.a(a(s10));
                    }
                }
            }
            InterfaceC0992Fq c1303Qq4 = new C1303Qq(c1303Qq3.size());
            InterfaceC0841Ar it2 = lr.iterator();
            while (it2.hasNext()) {
                int iIntValue = Integer.valueOf(((K) it2).t()).intValue();
                if (!n && !c1303Qq3.a(iIntValue)) {
                    throw new AssertionError();
                }
                c1303Qq4.a(iIntValue, (P00) c1303Qq3.get(iIntValue));
            }
            a(c1303Qq2, c1303Qq4);
            List listAsList = Arrays.asList((P00[]) arrayDeque.toArray(new P00[0]));
            if (arrayList.isEmpty()) {
                arrayList.addAll(listAsList);
            } else {
                if (!n && arrayList.size() != listAsList.size()) {
                    throw new AssertionError();
                }
                for (i2 = 0; i2 < arrayList.size(); i2++) {
                    P00 p04 = (P00) arrayList.get(i2);
                    Q00 q00 = this.c;
                    P00 p05 = (P00) listAsList.get(i2);
                    q00.getClass();
                    if (p04 == p05) {
                        p00A = p04;
                    } else {
                        C0767v2 c0767v2A = p04.a();
                        C0767v2 c0767v2A2 = p05.a();
                        if ((p04 instanceof M00) && (p05 instanceof M00) && c0767v2A == c0767v2A2) {
                            p00A = p04;
                        } else {
                            if (!Q00.j && (c0767v2A.W0() || c0767v2A2.W0())) {
                                throw new AssertionError();
                            }
                            p00A = q00.a(q00.a(AbstractC1048Hm.a(c0767v2A, c0767v2A2)));
                        }
                    }
                    if (p00A != p04) {
                        arrayList.set(i2, p00A);
                    }
                }
            }
        }
        if (!n && this.e.containsKey(c3332x4)) {
            throw new AssertionError();
        }
        this.e.put(c3332x4, new C2900r8(c1303Qq2, arrayList));
    }

    /* JADX WARN: Code duplicated, block: B:31:0x006e  */
    private void a(InterfaceC0992Fq interfaceC0992Fq, InterfaceC0992Fq interfaceC0992Fq2) {
        Object objA;
        if (interfaceC0992Fq.isEmpty()) {
            interfaceC0992Fq.putAll(interfaceC0992Fq2);
            return;
        }
        if (!n && interfaceC0992Fq.size() != interfaceC0992Fq2.size()) {
            throw new AssertionError();
        }
        InterfaceC1912eK it = interfaceC0992Fq.c().iterator();
        while (it.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
            int iA = interfaceC0934Dq.a();
            P00 p00 = (P00) interfaceC0934Dq.getValue();
            if (!n && !interfaceC0992Fq2.a(iA)) {
                throw new AssertionError();
            }
            Q00 q00 = this.c;
            P00 p01 = (P00) interfaceC0992Fq2.get(iA);
            q00.getClass();
            if (p00 == p01) {
                objA = p00;
            } else {
                C0767v2 c0767v2A = p00.a();
                C0767v2 c0767v2A2 = p01.a();
                if ((p00 instanceof M00) && (p01 instanceof M00) && c0767v2A == c0767v2A2) {
                    objA = p00;
                } else {
                    if (!Q00.j && (c0767v2A.W0() || c0767v2A2.W0())) {
                        throw new AssertionError();
                    }
                    objA = q00.a(q00.a(AbstractC1048Hm.a(c0767v2A, c0767v2A2)));
                }
            }
            if (objA != p00) {
                interfaceC0992Fq.a(iA, objA);
            }
        }
    }
}
