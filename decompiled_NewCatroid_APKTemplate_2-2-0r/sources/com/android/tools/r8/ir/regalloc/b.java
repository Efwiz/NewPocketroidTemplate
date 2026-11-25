package com.android.tools.r8.ir.regalloc;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.X;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AF;
import com.android.tools.r8.internal.AbstractC1084Is;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC1987fG;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.C1232Od;
import com.android.tools.r8.internal.C1303Qq;
import com.android.tools.r8.internal.C1316Rd;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1831dI;
import com.android.tools.r8.internal.C2318jb;
import com.android.tools.r8.internal.C2329jl;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2561ml;
import com.android.tools.r8.internal.C2617nW;
import com.android.tools.r8.internal.C2708oW;
import com.android.tools.r8.internal.C2723oi;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.internal.C2778pR;
import com.android.tools.r8.internal.C2799pi;
import com.android.tools.r8.internal.C2879qr;
import com.android.tools.r8.internal.C2972s9;
import com.android.tools.r8.internal.C3218vY;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3454yk;
import com.android.tools.r8.internal.C3479z4;
import com.android.tools.r8.internal.C3492zF;
import com.android.tools.r8.internal.EnumC2527mI;
import com.android.tools.r8.internal.H1;
import com.android.tools.r8.internal.InterfaceC0841Ar;
import com.android.tools.r8.internal.InterfaceC0934Dq;
import com.android.tools.r8.internal.InterfaceC1912eK;
import com.android.tools.r8.internal.InterfaceC2488lo;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.ML;
import com.android.tools.r8.internal.NK;
import com.android.tools.r8.internal.P30;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.U2;
import com.android.tools.r8.internal.X1;
import com.android.tools.r8.internal.ZF;
import com.android.tools.r8.internal.kl$$ExternalSyntheticLambda10;
import com.android.tools.r8.internal.xC$$ExternalSyntheticLambda0;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.T2;
import com.android.tools.r8.utils.Y2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BiPredicate;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class b implements f {
    static final /* synthetic */ boolean r = true;
    private final C0702m a;
    private final C2409kl b;
    protected final int c;
    private IdentityHashMap d;
    protected S10 e;
    private S10 f;
    private int g = 1;
    private TreeSet h = new TreeSet();
    private int i = -1;
    private ArrayList j = new ArrayList();
    private LinkedList k = new LinkedList();
    protected List<c> l = new LinkedList();
    protected PriorityQueue<c> m = new PriorityQueue<>();
    private C2732or n = new C2732or();
    private ArrayList o = new ArrayList();
    private int p = Integer.MIN_VALUE;
    private int[] q = null;

    public b(C0702m<?> c0702m, C2409kl c2409kl) {
        this.a = c0702m;
        this.b = c2409kl;
        int iE0 = 0;
        for (AbstractC2412ko abstractC2412ko : c2409kl.j().p()) {
            if (abstractC2412ko.m1()) {
                iE0 += abstractC2412ko.b().e0();
            }
        }
        this.c = iE0;
    }

    private void f() {
        this.b.d.forEach(new xC$$ExternalSyntheticLambda0());
    }

    private boolean g() {
        if (e() == 0) {
            return false;
        }
        HashSet hashSet = new HashSet();
        for (c cVar : this.j) {
            if (!cVar.z()) {
                hashSet.add(Integer.valueOf(b(cVar.i())));
                if (cVar.o().b()) {
                    hashSet.add(Integer.valueOf(b(cVar.i() + 1)));
                }
            }
            for (c cVar2 : (ArrayList) cVar.k()) {
                if (!cVar2.z()) {
                    hashSet.add(Integer.valueOf(b(cVar2.i())));
                    if (cVar2.o().b()) {
                        hashSet.add(Integer.valueOf(b(cVar2.i() + 1)));
                    }
                }
            }
        }
        for (int i = this.p; i < this.i + 1; i++) {
            hashSet.add(Integer.valueOf(b(i)));
        }
        int[] iArr = new int[e()];
        int i2 = 0;
        for (int i3 = 0; i3 < e(); i3++) {
            if (!hashSet.contains(Integer.valueOf(i3))) {
                i2++;
            }
            iArr[i3] = i2;
        }
        this.q = iArr;
        return i2 > 0;
    }

    private int h() {
        if (r || i()) {
            return this.c;
        }
        throw new AssertionError();
    }

    private boolean i() {
        return !this.o.isEmpty();
    }

    private void j() {
        Iterator it = this.j.iterator();
        while (it.hasNext()) {
            ((c) it.next()).a(this);
        }
        n nVar = new n(this.a, this.b, this);
        for (c cVar : this.j) {
            if (cVar.s()) {
                PriorityQueue priorityQueue = new PriorityQueue();
                priorityQueue.addAll(cVar.k());
                Object objPoll = priorityQueue.poll();
                while (true) {
                    c cVar2 = (c) objPoll;
                    c cVar3 = cVar;
                    cVar = cVar2;
                    if (cVar != null) {
                        int iM = cVar.m();
                        if (iM % 2 != 1) {
                            iM--;
                        }
                        nVar.f(iM, cVar, cVar3);
                        objPoll = priorityQueue.poll();
                    }
                }
            }
        }
        for (C3332x4 c3332x4 : this.b.d) {
            for (C3332x4 c3332x5 : c3332x4.z()) {
                int iW0 = c3332x4.m().W0();
                boolean zC = c3332x4.c(c3332x5);
                if (zC) {
                    for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                        if (abstractC2412ko.g()) {
                            iW0 = abstractC2412ko.W0();
                            break;
                        }
                    }
                }
                int iW1 = c3332x5.k().W0();
                Iterator it2 = ((C2329jl) this.d.get(c3332x5)).a.iterator();
                while (it2.hasNext()) {
                    c cVarR = ((S10) it2.next()).r();
                    c cVarD = cVarR.d(iW0);
                    c cVarD2 = cVarR.d(iW1);
                    if (cVarD != cVarD2) {
                        if (!c3332x4.m().M1() || zC) {
                            AbstractC2412ko abstractC2412koK = c3332x5.k();
                            abstractC2412koK.getClass();
                            if (abstractC2412koK instanceof AF) {
                                nVar.b(iW1 + 1, cVarD2, cVarD);
                            } else {
                                nVar.b(iW1 - 1, cVarD2, cVarD);
                            }
                        } else {
                            nVar.d(iW0 - 1, cVarD2, cVarD);
                        }
                    }
                }
                int iIndexOf = c3332x5.y().indexOf(c3332x4);
                for (ML ml : c3332x5.x()) {
                    c cVarD3 = ml.r().d(iW1);
                    c cVarD4 = ml.c(iIndexOf).r().d(iW0);
                    if (cVarD4 != cVarD3 && !cVarD3.u()) {
                        if (!r && c3332x4.z().size() != 1) {
                            throw new AssertionError();
                        }
                        nVar.e(iW0 - 1, cVarD3, cVarD4);
                    }
                }
            }
        }
        int i = this.i;
        int i2 = i + 1;
        this.p = i2;
        this.i = i + nVar.a(i2);
    }

    private void k() {
        Iterator<C3332x4> it = this.b.d.iterator();
        while (it.hasNext()) {
            InterfaceC2641no interfaceC2641noA = it.next().a(this.b);
            while (interfaceC2641noA.hasNext()) {
                if (a(interfaceC2641noA.next())) {
                    interfaceC2641noA.remove();
                }
            }
        }
    }

    private boolean l() {
        boolean z;
        boolean z2 = false;
        for (S10 s10T = this.e; s10T != null; s10T = s10T.t()) {
            c cVarR = s10T.r();
            if (!r && cVarR.j() != 65535) {
                throw new AssertionError();
            }
            Iterator it = ((ArrayList) cVarR.k()).iterator();
            boolean z3 = true;
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                int iJ = ((c) it.next()).j();
                if (iJ < 65535) {
                    if (iJ < e() - 1) {
                        z = false;
                        z3 = false;
                        break;
                    }
                    z3 = false;
                }
            }
            if (z && !z3) {
                for (c cVar : (ArrayList) cVarR.k()) {
                    cVar.a();
                    cVar.j(cVarR.i());
                    cVar.a(false);
                }
                z2 = true;
            }
        }
        return z2;
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final void a(C3332x4 c3332x4, int i, List list) {
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final void b(C3332x4 c3332x4, C3332x4 c3332x5) {
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final C0702m c() {
        return this.a;
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final o5 d() {
        return this.b.h();
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final int e() {
        int i = this.i + 1;
        int[] iArr = this.q;
        return iArr != null ? i - iArr[iArr.length - 1] : i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Live ranges:\n");
        for (c cVar : this.j) {
            sb.append(cVar.r());
            sb.append(" ");
            sb.append(cVar);
        }
        sb.append("\nLive range ascii art: \n");
        for (c cVar2 : this.j) {
            S10 s10R = cVar2.r();
            if (cVar2.i() == Integer.MIN_VALUE) {
                Y2.b(sb, s10R + " (no reg): ", 20);
            } else {
                Y2.b(sb, s10R + " r" + cVar2.i() + ": ", 20);
            }
            sb.append("|");
            sb.append(cVar2.E());
            sb.append("\n");
        }
        return sb.toString();
    }

    private int a(int i, c cVar, i iVar, j jVar, boolean z) {
        int i2 = -1;
        int i3 = -1;
        for (int i4 = 0; i4 <= i; i4++) {
            if (!jVar.b(i4) && ((!z || !jVar.b(i4 + 1)) && jVar.a(i4, iVar))) {
                int iA = jVar.a(i4);
                if (z) {
                    if (i4 == this.c - 1) {
                        continue;
                    } else {
                        if (i4 >= i) {
                            break;
                        }
                        iA = Math.min(iA, jVar.a(i4 + 1));
                    }
                }
                if ((!cVar.t() || iA != cVar.d()) && iA > i2) {
                    i3 = i4;
                    if (iA == Integer.MAX_VALUE) {
                        break;
                    }
                    i2 = iA;
                }
            }
        }
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return ((abstractC2412ko instanceof AF) || a(abstractC2412ko)) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:258:0x04b8  */
    /* JADX WARN: Code duplicated, block: B:260:0x04c6  */
    /* JADX WARN: Code duplicated, block: B:439:0x07a5  */
    private int c(int i, boolean z) {
        boolean z2;
        int iC;
        S10 s10;
        boolean z3;
        int iA;
        int i2;
        int i3;
        int iC2;
        int iH;
        d dVarB;
        this.g = i;
        int i4 = 3;
        int i5 = -1;
        boolean z4 = false;
        if (z) {
            this.h.clear();
            this.i = -1;
            this.k.clear();
            this.l.clear();
            this.m.clear();
            this.o.clear();
            for (c cVar : this.j) {
                if (i == 3) {
                    cVar.F();
                    cVar.a(false);
                }
                cVar.a();
            }
            k();
        }
        if (this.e != null) {
            b(this.c - 1, true);
            int iE0 = 0;
            for (S10 s10T = this.e; s10T != null; s10T = s10T.t()) {
                a(s10T.r(), iE0);
                iE0 += s10T.e0();
            }
        }
        this.m.addAll(this.j);
        S10 s10T2 = this.e;
        while (true) {
            int i6 = Integer.MIN_VALUE;
            if (s10T2 == null) {
                int i7 = 2;
                if (i == 2 || i == 3) {
                    Iterator<C3332x4> it = this.b.d.iterator();
                    boolean z5 = false;
                    while (it.hasNext()) {
                        AbstractC2412ko abstractC2412koK = it.next().k();
                        abstractC2412koK.getClass();
                        if (abstractC2412koK instanceof AF) {
                            c cVarR = abstractC2412koK.b().r();
                            this.m.remove(cVarR);
                            this.o.add(cVarR);
                            cVarR.j(h());
                            if (!z5) {
                                Iterator it2 = this.o.iterator();
                                z5 = z5;
                                while (it2.hasNext()) {
                                    z5 |= ((c) it2.next()).d(cVarR) != -1;
                                }
                            }
                        }
                    }
                    if (i()) {
                        int iH2 = h();
                        if (!r && iH2 != this.i + 1) {
                            throw new AssertionError();
                        }
                        b(iH2, true);
                    }
                    if (z5) {
                        for (c cVar2 : this.o) {
                            if (cVar2.q().size() > 1) {
                                this.m.add(cVar2.k(cVar2.d() + 2));
                            }
                        }
                    }
                }
                while (true) {
                    if (this.m.isEmpty()) {
                        z2 = true;
                        break;
                    }
                    boolean z6 = r;
                    if (!z6) {
                        a(i);
                    }
                    this.n.clear();
                    c cVarPoll = this.m.poll();
                    if (cVarPoll.e() == null && (cVarPoll.r().b instanceof C2972s9)) {
                        S10 s11 = (S10) ((ArrayList) cVarPoll.r().b.y().h1()).get(z4 ? 1 : 0);
                        if (!z6 && s11 == null) {
                            throw new AssertionError();
                        }
                        if (s11.r() != null && s11.r().d(cVarPoll) == i5 && s11.s() == cVarPoll.r().b.j()) {
                            cVarPoll.a(s11.r(), this.m);
                        }
                    }
                    if (cVarPoll.e() == null && (cVarPoll.r().b instanceof U2)) {
                        U2 u2R = cVarPoll.r().b.r();
                        S10 s10L2 = u2R.L2();
                        if (!z6 && s10L2 == null) {
                            throw new AssertionError();
                        }
                        if (s10L2.r() == null || s10L2.r().d(cVarPoll) != i5) {
                            S10 s10N2 = u2R.N2();
                            if (!z6 && s10N2 == null) {
                                throw new AssertionError();
                            }
                            if (u2R.K2() && s10N2.r() != null && s10N2.r().d(cVarPoll) == i5) {
                                cVarPoll.a(s10N2.r(), this.m);
                            }
                        } else {
                            cVarPoll.a(s10L2.r(), this.m);
                        }
                    }
                    for (AbstractC2412ko abstractC2412ko : cVarPoll.r().h0()) {
                        if (abstractC2412ko.l2() && abstractC2412ko.n0().J2().O()) {
                            c cVarR2 = abstractC2412ko.n0().J2().r();
                            if (cVarR2.i() == i6) {
                                TreeSet treeSet = new TreeSet((SortedSet) this.h);
                                int i8 = this.i;
                                LinkedList linkedList = new LinkedList(this.l);
                                for (c cVar3 : this.k) {
                                    if (cVar3.u()) {
                                        a(cVar3);
                                    }
                                    this.l.add(cVar3);
                                }
                                this.m.remove(cVarR2);
                                boolean z7 = (i == i7 || i == i4) ? true : z4 ? 1 : 0;
                                this.m.add(cVarPoll);
                                a(cVarR2, z7);
                                this.k.remove(cVarR2);
                                this.m.remove(cVarPoll);
                                this.h = treeSet;
                                for (int i9 = i8 + 1; i9 <= this.i; i9++) {
                                    this.h.add(Integer.valueOf(i9));
                                }
                                this.l = linkedList;
                                for (c cVarN = cVarR2.n(); cVarN != null; cVarN = cVarN.g()) {
                                    boolean z8 = r;
                                    if (!z8 && this.l.contains(cVarN)) {
                                        throw new AssertionError();
                                    }
                                    if (!z8 && this.k.contains(cVarN)) {
                                        throw new AssertionError();
                                    }
                                    if (!z8 && this.m.contains(cVarN)) {
                                        throw new AssertionError();
                                    }
                                    this.l.add(cVarN);
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (cVarPoll.i() == i6) {
                        int iM = cVarPoll.m();
                        Iterator it3 = this.k.iterator();
                        while (it3.hasNext()) {
                            c cVar4 = (c) it3.next();
                            if (iM >= cVar4.c()) {
                                it3.remove();
                                a(cVar4);
                                if (iM == cVar4.c()) {
                                    this.n.a(cVar4.i());
                                    if (cVar4.o().b()) {
                                        this.n.a(cVar4.i() + 1);
                                    }
                                }
                            } else if (cVar4.f(iM)) {
                                continue;
                            } else {
                                it3.remove();
                                if (!r && cVar4.i() == i6) {
                                    throw new AssertionError();
                                }
                                this.l.add(cVar4);
                                a(cVar4);
                            }
                        }
                        Iterator<c> it4 = this.l.iterator();
                        while (it4.hasNext()) {
                            c next = it4.next();
                            if (iM >= next.c()) {
                                it4.remove();
                                if (iM == next.c()) {
                                    this.n.a(next.i());
                                    if (next.o().b()) {
                                        this.n.a(next.i() + 1);
                                    }
                                }
                            } else if (next.f(iM)) {
                                it4.remove();
                                if (!r && next.i() == i6) {
                                    throw new AssertionError();
                                }
                                this.k.add(next);
                                g(next);
                            } else {
                                continue;
                            }
                        }
                        if (!cVarPoll.w() || cVarPoll.u()) {
                            int iJ = cVarPoll.j();
                            boolean z9 = r;
                            if (!z9 && iJ > 65535) {
                                throw new AssertionError();
                            }
                            if (!z9 && cVarPoll.C() > i7) {
                                throw new AssertionError();
                            }
                            boolean z10 = cVarPoll.C() == i7 ? true : z4 ? 1 : 0;
                            if (cVarPoll.u() && (iJ == 65535 || (i == i7 && iJ == 255))) {
                                a(cVarPoll, cVarPoll.l().i());
                                g(cVarPoll);
                                this.k.add(cVarPoll);
                                z3 = z4 ? 1 : 0;
                            } else {
                                if (iJ < 65535 && (i == i7 || i == i4)) {
                                    iJ += this.c;
                                }
                                int i10 = iJ;
                                k kVar = new k(i10 + 1);
                                if (!b().W0) {
                                    if (this.b.h().a().f(this.a)) {
                                        if (!this.b.u().f.o()) {
                                            if (z9 && this.c <= 0) {
                                                throw new AssertionError();
                                            }
                                            if (z9 && ((s10 = this.e) == null || s10.e0() != 1)) {
                                                throw new AssertionError();
                                            }
                                            kVar.c(z4 ? 1 : 0);
                                        }
                                    }
                                } else if (!this.b.u().f.o()) {
                                    if (z9) {
                                    }
                                    if (z9) {
                                    }
                                    kVar.c(z4 ? 1 : 0);
                                }
                                if (i == i7 || i == i4) {
                                    for (int i11 = z4 ? 1 : 0; i11 < this.c && i11 <= i10; i11++) {
                                        kVar.c(i11);
                                    }
                                }
                                if (e(cVarPoll) && (iH = h()) <= i10) {
                                    kVar.c(iH);
                                }
                                for (c cVar5 : this.k) {
                                    int i12 = cVar5.i();
                                    if (i12 <= i10) {
                                        for (int i13 = z4 ? 1 : 0; i13 < cVar5.C(); i13++) {
                                            int i14 = i12 + i13;
                                            if (i14 <= i10) {
                                                kVar.c(i14);
                                            }
                                        }
                                    }
                                }
                                for (c cVar6 : this.l) {
                                    int i15 = cVar6.i();
                                    if (i15 <= i10 && cVarPoll.d(cVar6) != -1) {
                                        int iD = cVarPoll.d(cVar6);
                                        int i16 = z4 ? 1 : 0;
                                        while (i16 < cVar6.C()) {
                                            int i17 = i15 + i16;
                                            if (i17 <= i10 && !kVar.b(i17)) {
                                                int iM2 = cVarPoll.m();
                                                if (iM2 % 2 != 0) {
                                                    iM2++;
                                                }
                                                if (iD == iM2) {
                                                    kVar.c(i17);
                                                } else if (iD < kVar.a(i17)) {
                                                    kVar.a(i17, iD, cVar6);
                                                }
                                            }
                                            i16++;
                                            z4 = false;
                                        }
                                    }
                                }
                                if (!r) {
                                    int iMin = Math.min(this.i, i10);
                                    for (int i18 = 0; i18 <= iMin; i18++) {
                                        if (!kVar.b(i18)) {
                                            boolean z11 = r;
                                            if (!z11 && kVar.a(i18) <= 0) {
                                                throw new AssertionError();
                                            }
                                            if ((!i() || i18 != h()) && !z11 && !this.h.contains(Integer.valueOf(i18))) {
                                                throw new AssertionError();
                                            }
                                        }
                                    }
                                }
                                Integer numE = cVarPoll.e();
                                if (numE != null) {
                                    if (!a(cVarPoll, i10, kVar, z10, numE.intValue())) {
                                    }
                                    z3 = false;
                                }
                                S10 s10R = cVarPoll.r();
                                if (s10R.i()) {
                                    ML mlL = s10R.l();
                                    C3454yk c3454ykF = C3454yk.f();
                                    List<S10> listK0 = mlL.k0();
                                    for (int i19 = 0; i19 < listK0.size(); i19++) {
                                        c cVarR3 = listK0.get(i19).r();
                                        if (cVarR3.s()) {
                                            cVarR3 = cVarR3.d(mlL.h().y().get(i19).m().W0());
                                        }
                                        int i20 = cVarR3.i();
                                        if (i20 != i6) {
                                            c3454ykF.add(Integer.valueOf(i20));
                                        }
                                    }
                                    AbstractC2963s10 it5 = AbstractC1987fG.a(c3454ykF).entrySet().iterator();
                                    while (true) {
                                        if (it5.hasNext()) {
                                            if (a(cVarPoll, i10, kVar, z10, ((Integer) ((ZF) it5.next()).b()).intValue())) {
                                                z3 = false;
                                            }
                                        }
                                    }
                                }
                                int iA2 = a(i10, cVarPoll, i.d, kVar, z10);
                                if (iA2 != -1) {
                                    iA = kVar.a(iA2);
                                    if (z10) {
                                        iA = Math.min(iA, kVar.a(iA2 + 1));
                                    }
                                } else {
                                    iA = 0;
                                }
                                if (iA != 0) {
                                    i2 = -1;
                                    int iC3 = (cVarPoll.C() + iA2) - 1;
                                    if (iC3 > this.i) {
                                        z3 = false;
                                        b(iC3, false);
                                    } else {
                                        z3 = false;
                                    }
                                    if (iA >= cVarPoll.c()) {
                                        a(cVarPoll, iA2);
                                        g(cVarPoll);
                                        this.k.add(cVarPoll);
                                    } else {
                                        if (i == 1) {
                                            z2 = z3;
                                            break;
                                        }
                                        c cVarK = cVarPoll.k(iA);
                                        if (!r && cVarK == cVarPoll) {
                                            throw new AssertionError();
                                        }
                                        a(cVarPoll, iA2);
                                        g(cVarPoll);
                                        this.k.add(cVarPoll);
                                        this.m.add(cVarK);
                                    }
                                    z4 = z3;
                                    i5 = i2;
                                    i4 = 3;
                                    i6 = Integer.MIN_VALUE;
                                    i7 = 2;
                                } else {
                                    if (i == 1) {
                                        z3 = false;
                                        z2 = z3;
                                        break;
                                    }
                                    if (cVarPoll.t() && ((d) cVarPoll.q().first()).c()) {
                                        int iJ2 = cVarPoll.j();
                                        if (iJ2 < 65535) {
                                            iJ2 += this.c;
                                        }
                                        int i21 = iJ2;
                                        int i22 = i21 + 1;
                                        k kVar2 = new k(i22);
                                        k kVar3 = new k(i22);
                                        for (c cVar7 : this.k) {
                                            int i23 = cVar7.i();
                                            if (i23 <= i21) {
                                                for (int i24 = 0; i24 < cVar7.C(); i24++) {
                                                    int i25 = i23 + i24;
                                                    if (i25 <= i21) {
                                                        kVar2.a(i25, cVar7.c(cVarPoll.m()), cVar7);
                                                    }
                                                }
                                            }
                                        }
                                        for (c cVar8 : this.l) {
                                            int i26 = cVar8.i();
                                            if (i26 <= i21 && cVar8.d(cVarPoll) != -1) {
                                                for (int i27 = 0; i27 < cVar8.C(); i27++) {
                                                    int i28 = i26 + i27;
                                                    if (i28 <= i21 && (iC2 = cVar8.c(cVarPoll.m())) < kVar2.a(i28)) {
                                                        kVar2.a(i28, iC2, cVar8);
                                                    }
                                                }
                                            }
                                        }
                                        for (int i29 = 0; i29 < this.c; i29++) {
                                            kVar2.c(i29);
                                        }
                                        if (e(cVarPoll)) {
                                            kVar2.c(h());
                                        }
                                        a(this.k, cVarPoll, i21, kVar2, kVar3);
                                        a(this.l, cVarPoll, i21, kVar2, kVar3);
                                        boolean zB = cVarPoll.o().b();
                                        int iA3 = a(i21, cVarPoll, i.b, kVar2, zB);
                                        if (iA3 != Integer.MAX_VALUE) {
                                            int iA4 = a(i21, cVarPoll, i.c, kVar2, zB);
                                            if (iA4 != -1) {
                                                if (iA4 == Integer.MAX_VALUE || iA3 == -1) {
                                                    iA3 = iA4;
                                                } else {
                                                    int iA5 = kVar2.a(iA3);
                                                    if (zB) {
                                                        iA5 = Math.min(iA5, kVar2.a(iA3 + 1));
                                                    }
                                                    if (iA5 - 5 < cVarPoll.m()) {
                                                        iA3 = iA4;
                                                    }
                                                }
                                            }
                                            i2 = -1;
                                            if (iA3 == -1) {
                                                iA3 = a(i21, cVarPoll, i.a, kVar2, zB);
                                            }
                                        } else {
                                            i2 = -1;
                                        }
                                        int iA6 = kVar2.a(iA3);
                                        if (zB) {
                                            iA6 = Math.min(iA6, kVar2.a(iA3 + 1));
                                        }
                                        int iA7 = kVar3.a(iA3);
                                        if (zB) {
                                            iA7 = Math.min(iA7, kVar3.a(iA3 + 1));
                                        }
                                        if (iA6 < cVarPoll.d()) {
                                            c cVarK2 = cVarPoll.k(cVarPoll.d());
                                            if (!r && cVarK2 == cVarPoll) {
                                                throw new AssertionError();
                                            }
                                            if (cVarPoll.u()) {
                                                i3 = cVarPoll.l().i();
                                            } else {
                                                int i30 = this.i;
                                                b(cVarPoll.C() + i30, false);
                                                i3 = i30 + 1;
                                            }
                                            a(cVarPoll, i3);
                                            g(cVarPoll);
                                            this.k.add(cVarPoll);
                                            cVarPoll.a(true);
                                            this.m.add(cVarK2);
                                        } else {
                                            int iC4 = (cVarPoll.C() + iA3) - 1;
                                            if (iC4 > this.i) {
                                                b(iC4, false);
                                            }
                                            if (iA7 > cVarPoll.c()) {
                                                a(cVarPoll, iA3, zB);
                                                a(cVarPoll, iA3);
                                                g(cVarPoll);
                                                this.k.add(cVarPoll);
                                                b(cVarPoll, iA3, zB);
                                            } else {
                                                this.m.add(cVarPoll.k(iA7));
                                                a(cVarPoll, iA3, zB);
                                                a(cVarPoll, iA3);
                                                g(cVarPoll);
                                                this.k.add(cVarPoll);
                                                b(cVarPoll, iA3, zB);
                                            }
                                        }
                                    } else {
                                        i2 = -1;
                                        int iB = cVarPoll.b().b();
                                        int iA8 = a(cVarPoll, (C2732or) null);
                                        c cVarK3 = cVarPoll.k(iB);
                                        a(cVarPoll, iA8);
                                        g(cVarPoll);
                                        this.k.add(cVarPoll);
                                        this.m.add(cVarK3);
                                    }
                                    z3 = false;
                                    z4 = z3;
                                    i5 = i2;
                                    i4 = 3;
                                    i6 = Integer.MIN_VALUE;
                                    i7 = 2;
                                }
                            }
                            i2 = -1;
                            z4 = z3;
                            i5 = i2;
                            i4 = 3;
                            i6 = Integer.MIN_VALUE;
                            i7 = 2;
                        } else {
                            a(cVarPoll, z4);
                        }
                    }
                    i5 = -1;
                }
                if (z2) {
                    j();
                }
                if (i == 0) {
                    throw null;
                }
                int i31 = i - 1;
                if (i31 != 0) {
                    if (i31 == 1) {
                        if (!r && !z2) {
                            throw new AssertionError();
                        }
                        if (l()) {
                            k();
                            j();
                        }
                        g();
                        if (e() - 1 <= 255) {
                            b().n1.getClass();
                        } else {
                            this.q = null;
                            iC = c(3, true);
                        }
                    } else if (i31 == 2) {
                        if (!r && !z2) {
                            throw new AssertionError();
                        }
                        if (l()) {
                            k();
                            j();
                        }
                        g();
                    }
                    iC = i;
                } else if (!z2 || e() - 1 > 15) {
                    iC = c(2, true);
                } else {
                    b().n1.getClass();
                    if (!r && g()) {
                        throw new AssertionError();
                    }
                    iC = i;
                }
                boolean z12 = r;
                if (!z12 && iC == 1 && e() - 1 > 15) {
                    throw new AssertionError();
                }
                if (z12 || iC != 2 || e() - 1 <= 255) {
                    return iC;
                }
                throw new AssertionError();
            }
            c cVarR4 = s10T2.r();
            if (!r && cVarR4.i() == Integer.MIN_VALUE) {
                throw new AssertionError();
            }
            this.m.remove(cVarR4);
            if (i == 1) {
                this.k.add(cVarR4);
            } else {
                this.l.add(cVarR4);
                if (cVarR4.q().size() > 1 && (dVarB = cVarR4.b()) != null) {
                    this.m.add(cVarR4.B() == 1 ? cVarR4.k(dVarB.b()) : cVarR4.k(cVarR4.r().b.W0() + 1));
                }
                a(cVarR4);
            }
            s10T2 = s10T2.t();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d(c cVar) {
        if ((!b().i() && !b().u()) || cVar.C() == 2 || cVar.r().i() || cVar.l() != cVar) {
            return false;
        }
        AbstractC2412ko abstractC2412ko = cVar.r().b;
        abstractC2412ko.getClass();
        if (abstractC2412ko instanceof C2318jb) {
            return ((S10) abstractC2412ko.h1().get(0)).d0().b();
        }
        if (!abstractC2412ko.q2()) {
            return false;
        }
        C1831dI c1831dIW0 = abstractC2412ko.w0();
        return c1831dIW0.h == EnumC2527mI.LONG && c1831dIW0.i == EnumC2527mI.INT;
    }

    private boolean f(c cVar) {
        if (!r && cVar.i() == Integer.MIN_VALUE) {
            throw new AssertionError();
        }
        int i = cVar.i();
        return (this.h.contains(Integer.valueOf(i)) || (cVar.o().b() && this.h.contains(Integer.valueOf(i + 1)))) ? false : true;
    }

    private boolean e(c cVar) {
        if (!i()) {
            return false;
        }
        if (this.o.size() > 500) {
            return true;
        }
        Iterator it = this.o.iterator();
        while (it.hasNext()) {
            if (cVar.a((c) it.next())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final int b(S10 s10, int i) {
        if (s10.H()) {
            return b(s10.r().i());
        }
        return a(s10, i);
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final A1 b() {
        return this.a.Y();
    }

    final int b(int i) {
        int iC = c(i);
        int[] iArr = this.q;
        return iArr != null ? iC - iArr[iC] : iC;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(TreeSet treeSet, int i) {
        if (!r && !treeSet.contains(Integer.valueOf(i))) {
            throw new AssertionError();
        }
        treeSet.remove(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(c cVar) {
        if (b().Z() || cVar.C() == 1 || cVar.r().i() || cVar.l() != cVar) {
            return false;
        }
        AbstractC2412ko abstractC2412ko = cVar.r().b;
        return abstractC2412ko.p1() && abstractC2412ko.t().H2().b();
    }

    private boolean a(c cVar, int i, k kVar, boolean z, int i2) {
        if (i2 == Integer.MIN_VALUE || i2 + (z ? 1 : 0) > i || kVar.b(i2) || (z && kVar.b(i2 + 1))) {
            return false;
        }
        int iA = kVar.a(i2);
        if (z) {
            iA = Math.min(iA, kVar.a(i2 + 1));
        }
        if (iA >= cVar.c()) {
            if (c(cVar) && c(cVar, i2)) {
                return false;
            }
            if (b(cVar) && b(cVar, i2)) {
                return false;
            }
            a(cVar, i2);
            g(cVar);
            this.k.add(cVar);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d(c cVar, int i) {
        boolean z = r;
        if (!z && !d(cVar)) {
            throw new AssertionError();
        }
        AbstractC2412ko abstractC2412ko = cVar.r().b;
        abstractC2412ko.getClass();
        if (abstractC2412ko instanceof C2318jb) {
            S10 s10L2 = cVar.r().b.z().L2();
            S10 s10N2 = cVar.r().b.z().N2();
            int i2 = s10L2.r().d(cVar.m()).i();
            int i3 = s10N2.r().d(cVar.m()).i();
            if (!z && i2 == Integer.MIN_VALUE) {
                throw new AssertionError();
            }
            if (z || i3 != Integer.MIN_VALUE) {
                return i == i2 || i == i2 + 1 || i == i3 || i == i3 + 1;
            }
            throw new AssertionError();
        }
        if (z || cVar.r().b.q2()) {
            return i == ((S10) ((ArrayList) cVar.r().b.w0().h1()).get(0)).r().d(cVar.m()).i();
        }
        throw new AssertionError();
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0042, code lost:
    
        if (r2 != h()) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void g(com.android.tools.r8.ir.regalloc.c r6) {
        /*
            r5 = this;
            int r0 = r6.i()
            com.android.tools.r8.internal.d20 r1 = r6.o()
            boolean r1 = r1.b()
            boolean r2 = com.android.tools.r8.ir.regalloc.b.r
            if (r2 != 0) goto L4b
            java.util.TreeSet r2 = r5.h
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)
            boolean r2 = r2.contains(r3)
            if (r2 != 0) goto L28
            boolean r2 = r5.i()
            if (r2 == 0) goto L45
            int r2 = r5.h()
            if (r0 != r2) goto L45
        L28:
            if (r1 == 0) goto L4b
            int r2 = r0 + 1
            java.util.TreeSet r3 = r5.h
            java.lang.Integer r4 = java.lang.Integer.valueOf(r2)
            boolean r3 = r3.contains(r4)
            if (r3 != 0) goto L4b
            boolean r3 = r5.i()
            if (r3 == 0) goto L45
            int r3 = r5.h()
            if (r2 != r3) goto L45
            goto L4b
        L45:
            java.lang.AssertionError r6 = new java.lang.AssertionError
            r6.<init>()
            throw r6
        L4b:
            java.util.TreeSet r2 = r5.h
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)
            r2.remove(r3)
            if (r1 == 0) goto L61
            java.util.TreeSet r1 = r5.h
            int r0 = r0 + 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1.remove(r0)
        L61:
            boolean r0 = r6.u()
            if (r0 == 0) goto L7e
            com.android.tools.r8.ir.regalloc.c r0 = r6.l()
            if (r6 == r0) goto L7e
            com.android.tools.r8.ir.regalloc.c r0 = r6.l()
            int r1 = r0.i()
            int r6 = r6.i()
            if (r1 == r6) goto L7e
            r5.g(r0)
        L7e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.ir.regalloc.b.g(com.android.tools.r8.ir.regalloc.c):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(c cVar, int i) {
        boolean z = r;
        if (!z && !b(cVar)) {
            throw new AssertionError();
        }
        int i2 = cVar.r().b.t().J2().r().d(cVar.m()).i();
        if (z || i2 != Integer.MIN_VALUE) {
            return i2 == i;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final void a() {
        if (!r) {
            for (C3332x4 c3332x4 : this.b.d) {
                for (ML ml : c3332x4.x()) {
                    if (!r && ml.t() != null) {
                        throw new AssertionError();
                    }
                }
                for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                    for (S10 s10 : (ArrayList) abstractC2412ko.h1()) {
                        if (!r && s10.t() != null) {
                            throw new AssertionError();
                        }
                    }
                    if (!r && abstractC2412ko.b() != null && abstractC2412ko.b().t() != null) {
                        throw new AssertionError();
                    }
                }
            }
        }
        boolean z = r;
        if (!z && !this.b.b(this.a)) {
            throw new AssertionError();
        }
        if (this.b.u().f.X() && a(this.b)) {
            if (!z && !a(this.b)) {
                throw new AssertionError();
            }
            InterfaceC2488lo interfaceC2488loR = this.b.j().R();
            C2778pR c2778pR = new C2778pR();
            while (interfaceC2488loR.o().m1()) {
                c2778pR.a(c2778pR.size(), interfaceC2488loR.next().q().b());
            }
            while (!interfaceC2488loR.o().U1()) {
                interfaceC2488loR.next();
            }
            AbstractC1084Is abstractC1084IsX = interfaceC2488loR.o().X();
            int iE0 = this.c;
            if (abstractC1084IsX.b() != null) {
                iE0 += abstractC1084IsX.b().e0();
            }
            if (iE0 - 1 <= 255) {
                ArrayList arrayList = (ArrayList) abstractC1084IsX.J2();
                if (arrayList.size() >= 1) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 < arrayList.size()) {
                            S10 s10E = (S10) arrayList.get(i2);
                            if (!s10E.H()) {
                                s10E = s10E.b.y().e();
                            }
                            if (!r && !s10E.H()) {
                                throw new AssertionError();
                            }
                            int iA = c2778pR.a(s10E);
                            if (i >= 0 && iA != i + 1) {
                                break;
                            }
                            i2++;
                            i = iA;
                        } else {
                            while (interfaceC2488loR.j().w1()) {
                                C2972s9 c2972s9Y = interfaceC2488loR.previous().y();
                                c2972s9Y.b().g(c2972s9Y.e());
                                c2972s9Y.f(null);
                            }
                            break;
                        }
                    }
                }
            }
        }
        AbstractC2963s10 it = this.b.D().iterator();
        while (it.hasNext()) {
            for (AbstractC2412ko abstractC2412ko2 : ((C3332x4) it.next()).p()) {
                if (abstractC2412ko2.b() != null) {
                    abstractC2412ko2.b().f();
                }
            }
        }
        List<S10> listC = this.b.c();
        Iterator<S10> it2 = listC.iterator();
        int i3 = 0;
        while (it2.hasNext()) {
            c cVar = new c(it2.next());
            cVar.a(new e(0, i3));
            this.j.add(cVar);
            i3 += 2;
        }
        if (!listC.isEmpty()) {
            S10 s11 = listC.get(0);
            this.e = s11;
            int i4 = 1;
            while (i4 < listC.size()) {
                S10 s12 = listC.get(i4);
                s11.f(s12);
                s11.r().c(s12.r());
                i4++;
                s11 = s12;
            }
            this.f = s11;
        }
        Iterator<C3332x4> it3 = this.b.d.iterator();
        while (it3.hasNext()) {
            InterfaceC2641no interfaceC2641noA = it3.next().a(this.b);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next = interfaceC2641noA.next();
                if (next.U1()) {
                    interfaceC2641noA.previous();
                    a(next.X(), interfaceC2641noA);
                    interfaceC2641noA.next();
                }
            }
        }
        AbstractC1639am abstractC1639amV = this.b.v();
        IdentityHashMap identityHashMapD = this.b.d();
        this.d = identityHashMapD;
        a(this.a, this.b, identityHashMapD, this.j);
        if ((b().C() || b().B()) && !this.b.u().f.o()) {
            for (AbstractC2412ko abstractC2412ko3 : this.b.j().p()) {
                if (abstractC2412ko3.m1() && abstractC2412ko3.b().Q()) {
                    S10 s10B = abstractC2412ko3.b();
                    c cVarR = s10B.r();
                    cVarR.h().clear();
                    cVarR.a(new e(0, this.b.n()));
                    Iterator it4 = this.d.values().iterator();
                    while (it4.hasNext()) {
                        ((C2329jl) it4.next()).a.add(s10B);
                    }
                    break;
                }
            }
        }
        c(1, false);
        boolean z2 = r;
        if (!z2) {
            this.b.b(this.a, false);
        }
        if (!z2 && e() != 0 && this.q == null) {
            throw new AssertionError();
        }
        if (b().W0) {
            a(this.b, abstractC1639amV, this.j, this, this.d);
        } else {
            if (this.b.h().a().f(this.a)) {
                C2409kl c2409kl = this.b;
                c2409kl.getClass();
                C2561ml c2561ml = new C2561ml(c2409kl);
                while (c2561ml.hasNext()) {
                    AbstractC2412ko abstractC2412ko4 = (AbstractC2412ko) c2561ml.next();
                    abstractC2412ko4.getClass();
                    if (abstractC2412ko4 instanceof C1232Od) {
                        abstractC2412ko4.P0();
                        c2561ml.remove();
                    }
                }
            }
        }
        f();
        this.d = null;
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.h = null;
    }

    protected void b(c cVar, int i, boolean z) {
        int iC;
        ArrayList arrayList = new ArrayList();
        Iterator<c> it = this.l.iterator();
        while (it.hasNext()) {
            c next = it.next();
            if (next.a(i, z) && next.d(cVar) != -1) {
                if (next.w() && !next.u() && (iC = next.c(cVar.m())) != Integer.MAX_VALUE) {
                    c cVarK = next.k(iC);
                    cVarK.j(next.i());
                    arrayList.add(cVarK);
                }
                if (next.m() > cVar.m()) {
                    next.a();
                    it.remove();
                    this.m.add(next);
                } else {
                    this.m.add(next.k(cVar.m()));
                }
            }
        }
        this.l.addAll(arrayList);
    }

    private void b(int i, boolean z) {
        if (!z) {
            int i2 = this.i;
            while (true) {
                i2++;
                if (i2 > i) {
                    break;
                } else {
                    this.h.add(Integer.valueOf(i2));
                }
            }
        }
        this.i = i;
    }

    public static void a(C2409kl c2409kl, AbstractC1639am abstractC1639am, ArrayList arrayList, f fVar, IdentityHashMap identityHashMap) {
        C1303Qq c1303Qq;
        C1303Qq c1303Qq2;
        a aVar;
        int iW0;
        S10 s10C;
        f fVar2 = fVar;
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            c cVar = (c) it.next();
            S10 s10R = cVar.r();
            if (s10R.A()) {
                ArrayList<e> arrayList3 = new ArrayList(cVar.h());
                for (c cVar2 : (ArrayList) cVar.k()) {
                    boolean z = r;
                    if (!z && cVar2.r() != s10R) {
                        throw new AssertionError();
                    }
                    if (!z && cVar2.k() != null && !((ArrayList) cVar2.k()).isEmpty()) {
                        throw new AssertionError();
                    }
                    arrayList3.addAll(cVar2.h());
                }
                arrayList3.sort(Comparator.comparingInt(new ToIntFunction() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda4
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        return ((e) obj).a;
                    }
                }));
                for (e eVar : arrayList3) {
                    int i = eVar.a;
                    arrayList2.add(new a(s10R, fVar2.b(s10R, i), i, eVar.b));
                }
            }
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        arrayList2.sort(new Comparator() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((a) obj).compareTo((a) obj2);
            }
        });
        LinkedList<a> linkedList = new LinkedList();
        Iterator it2 = arrayList2.iterator();
        a aVar2 = (a) it2.next();
        C1303Qq c1303Qq3 = new C1303Qq(16);
        C1303Qq c1303Qq4 = new C1303Qq(16);
        AbstractC2963s10 it3 = abstractC1639am.iterator();
        boolean z2 = true;
        while (it3.hasNext()) {
            C3332x4 c3332x4 = (C3332x4) it3.next();
            InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
            final Set setA = T2.a((Iterable) ((C2329jl) identityHashMap.get(c3332x4)).b);
            if (z2) {
                if (!r && !c3332x4.x().isEmpty()) {
                    throw new AssertionError();
                }
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next = interfaceC2641noA.next();
                    if (!next.m1()) {
                        break;
                    } else if (next.b().A()) {
                        setA.add(next.b());
                    }
                }
                interfaceC2641noA.previous();
                z2 = false;
            } else {
                for (ML ml : c3332x4.x()) {
                    if (ml.A()) {
                        setA.add(ml);
                    }
                }
            }
            interfaceC2641noA.a(new Predicate() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return b.b((AbstractC2412ko) obj);
                }
            });
            final AbstractC2412ko abstractC2412koPrevious = interfaceC2641noA.previous();
            int iW1 = abstractC2412koPrevious.W0();
            linkedList.removeIf(new Predicate() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return b.a(setA, abstractC2412koPrevious, (a) obj);
                }
            });
            while (aVar2 != null && aVar2.d < iW1) {
                if (setA.contains(aVar2.a) && a(abstractC2412koPrevious, aVar2)) {
                    linkedList.add(aVar2);
                }
                aVar2 = it2.hasNext() ? (a) it2.next() : null;
            }
            C1303Qq c1303Qq5 = new C1303Qq(linkedList.size());
            for (a aVar3 : linkedList) {
                a aVar4 = aVar2;
                if (setA.contains(aVar3.a)) {
                    c1303Qq5.a(aVar3.c, aVar3.b);
                }
                aVar2 = aVar4;
            }
            a aVar5 = aVar2;
            if (!c3332x4.y().isEmpty() && c3332x4.k() != interfaceC2641noA.o()) {
                C1303Qq c1303Qq6 = new C1303Qq(linkedList.size());
                AbstractC2412ko abstractC2412koK = c3332x4.k();
                abstractC2412koK.getClass();
                if (abstractC2412koK instanceof AF) {
                    iW0 = c3332x4.y().get(0).l().W0();
                } else {
                    iW0 = c3332x4.y().get(0).m().W0();
                }
                for (a aVar6 : linkedList) {
                    C1303Qq c1303Qq7 = c1303Qq3;
                    if (aVar6.a.i() && aVar6.a.h() == c3332x4) {
                        s10C = aVar6.a.l().c(0);
                    } else {
                        s10C = aVar6.a;
                    }
                    c1303Qq6.a(fVar2.b(s10C, iW0), aVar6.b);
                    c1303Qq3 = c1303Qq7;
                    c1303Qq4 = c1303Qq4;
                }
                c1303Qq = c1303Qq3;
                c1303Qq2 = c1303Qq4;
                c3332x4.a(c1303Qq6);
                C1303Qq c1303Qq8 = new C1303Qq(16);
                C1303Qq c1303Qq9 = new C1303Qq(16);
                InterfaceC1912eK it4 = c1303Qq6.c().iterator();
                while (it4.hasNext()) {
                    InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it4.next();
                    if (c1303Qq5.get(interfaceC0934Dq.a()) != interfaceC0934Dq.getValue()) {
                        c1303Qq8.a(interfaceC0934Dq.a(), (X) interfaceC0934Dq.getValue());
                    }
                }
                InterfaceC1912eK it5 = c1303Qq5.c().iterator();
                while (it5.hasNext()) {
                    InterfaceC0934Dq interfaceC0934Dq2 = (InterfaceC0934Dq) it5.next();
                    if (c1303Qq6.get(interfaceC0934Dq2.a()) != interfaceC0934Dq2.getValue()) {
                        c1303Qq9.a(interfaceC0934Dq2.a(), (X) interfaceC0934Dq2.getValue());
                    }
                }
                C1316Rd c1316RdA = a(c1303Qq8, c1303Qq9, c3332x4.k().Y0());
                if (c1316RdA != null) {
                    interfaceC2641noA.add(c1316RdA);
                }
            } else {
                c1303Qq = c1303Qq3;
                c1303Qq2 = c1303Qq4;
                boolean z3 = r;
                if (!z3) {
                    AbstractC2412ko abstractC2412koK2 = c3332x4.k();
                    abstractC2412koK2.getClass();
                    if (abstractC2412koK2 instanceof AF) {
                        throw new AssertionError();
                    }
                }
                if (!z3 && a(c3332x4.k())) {
                    throw new AssertionError();
                }
                C1303Qq c1303Qq10 = new C1303Qq(c1303Qq5.size());
                c1303Qq10.putAll(c1303Qq5);
                c3332x4.a(c1303Qq10);
            }
            a aVar7 = aVar5;
            c1303Qq3 = c1303Qq;
            c1303Qq4 = c1303Qq2;
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next2 = interfaceC2641noA.next();
                if (!interfaceC2641noA.hasNext()) {
                    next2.P0();
                    break;
                }
                if (next2.S0().isEmpty()) {
                    aVar = aVar7;
                } else {
                    for (S10 s10 : next2.S0()) {
                        ListIterator listIterator = linkedList.listIterator();
                        while (listIterator.hasNext()) {
                            a aVar8 = (a) listIterator.next();
                            a aVar9 = aVar7;
                            if (aVar8.a == s10) {
                                if (!r && c1303Qq5.get(aVar8.c) != aVar8.b) {
                                    throw new AssertionError();
                                }
                                c1303Qq5.remove(aVar8.c);
                                c1303Qq3.a(aVar8.c, aVar8.b);
                                aVar7 = aVar9;
                                break;
                            }
                            aVar7 = aVar9;
                        }
                    }
                    aVar = aVar7;
                    next2.P0();
                }
                if (next2 instanceof C1232Od) {
                    AbstractC2412ko abstractC2412koPrevious2 = interfaceC2641noA.previous();
                    if (!r && abstractC2412koPrevious2 != next2) {
                        throw new AssertionError();
                    }
                    interfaceC2641noA.remove();
                }
                AbstractC2412ko abstractC2412koO = interfaceC2641noA.o();
                if (a(abstractC2412koO)) {
                    aVar7 = aVar;
                } else {
                    int iW2 = abstractC2412koO.W0();
                    ListIterator listIterator2 = linkedList.listIterator();
                    while (listIterator2.hasNext()) {
                        a aVar10 = (a) listIterator2.next();
                        if (!a(abstractC2412koO, aVar10)) {
                            listIterator2.remove();
                            if (c1303Qq5.remove(aVar10.c) != null) {
                                c1303Qq3.a(aVar10.c, aVar10.b);
                            }
                        }
                    }
                    a aVar11 = aVar;
                    while (aVar11 != null && aVar11.d < iW2) {
                        if (a(abstractC2412koO, aVar11)) {
                            linkedList.add(aVar11);
                            if (!r && c1303Qq5.a(aVar11.c)) {
                                throw new AssertionError();
                            }
                            c1303Qq5.a(aVar11.c, aVar11.b);
                            c1303Qq4.a(aVar11.c, aVar11.b);
                        }
                        aVar11 = it2.hasNext() ? (a) it2.next() : null;
                    }
                    if (!c1303Qq3.isEmpty() || !c1303Qq4.isEmpty()) {
                        C1316Rd c1316RdA2 = a(c1303Qq3, c1303Qq4, next2.Y0());
                        if (c1316RdA2 != null) {
                            interfaceC2641noA.add(c1316RdA2);
                        }
                        c1303Qq3 = new C1303Qq(16);
                        c1303Qq4 = new C1303Qq(16);
                    }
                    aVar7 = aVar11;
                }
            }
            aVar2 = aVar7;
            fVar2 = fVar;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, AbstractC2412ko abstractC2412ko, a aVar) {
        return (set.contains(aVar.a) && a(abstractC2412ko, aVar)) ? false : true;
    }

    private static boolean a(AbstractC2412ko abstractC2412ko, a aVar) {
        int i = aVar.d;
        int i2 = aVar.e;
        S10 s10 = aVar.a;
        int iW0 = abstractC2412ko.W0();
        if (!r && i >= iW0) {
            throw new AssertionError();
        }
        if (iW0 >= i2) {
            if (iW0 == i2) {
                for (S10 s11 : (ArrayList) abstractC2412ko.h1()) {
                    if (s10 != s11 && (!s10.i() || !(s11 instanceof C2723oi) || ((C2723oi) s11).j0() != s10)) {
                    }
                }
                for (S10 s12 : abstractC2412ko.S0()) {
                    if (s10 != s12 && (!s10.i() || !(s12 instanceof C2723oi) || ((C2723oi) s12).j0() != s10)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    private static C1316Rd a(C1303Qq c1303Qq, C1303Qq c1303Qq2, AbstractC1915eM abstractC1915eM) {
        C1316Rd c1316Rd;
        if (!r && !abstractC1915eM.s()) {
            throw new AssertionError();
        }
        if (c1303Qq.isEmpty() && c1303Qq2.isEmpty()) {
            return null;
        }
        if (!c1303Qq.isEmpty() && !c1303Qq2.isEmpty()) {
            C2879qr c2879qr = new C2879qr(Math.min(c1303Qq.size(), c1303Qq2.size()));
            InterfaceC1912eK it = c1303Qq.c().iterator();
            while (it.hasNext()) {
                InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
                if (c1303Qq2.get(interfaceC0934Dq.a()) == interfaceC0934Dq.getValue()) {
                    c2879qr.a(interfaceC0934Dq.a());
                }
            }
            if (c2879qr.size() == c1303Qq.size() && c2879qr.size() == c1303Qq2.size()) {
                return null;
            }
            InterfaceC0841Ar it2 = c2879qr.iterator();
            while (it2.hasNext()) {
                int iT = it2.t();
                c1303Qq.remove(iT);
                c1303Qq2.remove(iT);
            }
            c1316Rd = new C1316Rd(c1303Qq, c1303Qq2);
        } else {
            c1316Rd = new C1316Rd(c1303Qq, c1303Qq2);
        }
        c1316Rd.b(abstractC1915eM);
        return c1316Rd;
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public int a(S10 s10, int i) {
        if (s10.N()) {
            return b(s10.b().j0());
        }
        c cVarR = s10.r();
        if (cVarR != null) {
            if (cVarR.s()) {
                cVarR = cVarR.d(i);
            }
            return b(cVarR.i());
        }
        throw new C1367Tb("Unexpected attempt to get register for a value without a register in method `" + this.b.u().getReference().r0() + "`.", this.b.k);
    }

    private static boolean a(AbstractC2412ko abstractC2412ko) {
        S10 s10B = abstractC2412ko.b();
        if (s10B == null || !(s10B instanceof C2799pi)) {
            return false;
        }
        boolean z = r;
        if (!z && abstractC2412ko.W0() != -1) {
            throw new AssertionError();
        }
        if (!z && !abstractC2412ko.l2() && !abstractC2412ko.B1()) {
            throw new AssertionError();
        }
        if (z || !abstractC2412ko.F1()) {
            return true;
        }
        throw new AssertionError();
    }

    private void a(int i) {
        final TreeSet treeSet = new TreeSet();
        for (int i2 = 0; i2 <= this.i; i2++) {
            treeSet.add(Integer.valueOf(i2));
        }
        for (c cVar : this.k) {
            if (!r && !f(cVar)) {
                throw new AssertionError();
            }
            cVar.a(new IntConsumer() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda1
                @Override // java.util.function.IntConsumer
                public final void accept(int i3) {
                    b.a(treeSet, i3);
                }
            });
        }
        if (i == 2 || i == 3) {
            for (c cVar2 : this.k) {
                if (cVar2.u() && cVar2 != cVar2.l() && cVar2.l().i() != cVar2.i()) {
                    cVar2.l().a(new IntConsumer() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda2
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            b.b(treeSet, i3);
                        }
                    });
                }
            }
        }
        if (i()) {
            this.h.remove(Integer.valueOf(h()));
            treeSet.remove(Integer.valueOf(h()));
        }
        if (!r && !this.h.equals(treeSet)) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(TreeSet treeSet, int i) {
        if (!r && !treeSet.contains(Integer.valueOf(i))) {
            throw new AssertionError();
        }
        treeSet.remove(Integer.valueOf(i));
    }

    private void a(c cVar, boolean z) {
        S10 s10;
        c cVarN = cVar.n();
        C2879qr c2879qr = new C2879qr();
        for (c cVarG = cVarN; cVarG != null; cVarG = cVarG.g()) {
            for (c cVar2 : this.l) {
                if (cVar2.d(cVarG) != -1) {
                    a(cVar2, c2879qr);
                }
            }
        }
        if (z && (s10 = this.e) != null) {
            for (c cVarR = s10.r(); cVarR != null; cVarR = cVarR.g()) {
                boolean z2 = r;
                if (!z2 && cVarR != cVarR.l()) {
                    throw new AssertionError();
                }
                if (!z2 && cVarN != cVarN.n()) {
                    throw new AssertionError();
                }
                for (c cVar3 : (ArrayList) cVarR.k()) {
                    if (this.m.contains(cVar3)) {
                        for (c cVarG2 = cVarN; cVarG2 != null; cVarG2 = cVarG2.g()) {
                            if (cVar3.d(cVarG2) != -1) {
                                a(cVarR, c2879qr);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (e(cVarN) && this.h.remove(Integer.valueOf(h()))) {
            c2879qr.a(h());
        }
        int iA = a(cVarN.A(), false);
        while (cVarN != null) {
            cVarN.j(iA);
            boolean z3 = r;
            if (!z3) {
                if (!z3 && cVarN.i() == Integer.MIN_VALUE) {
                    throw new AssertionError();
                }
                for (S10 s10T = this.e; s10T != null; s10T = s10T.t()) {
                    if (!r && cVarN.b(s10T.r()) && s10T.r().a(cVarN)) {
                        throw new AssertionError();
                    }
                }
            }
            S10 s10R = cVarN.r();
            if (!s10R.i() && s10R.b.l2()) {
                s10R.b.n0().K2().r().a(cVarN, this.m);
            }
            if (cVarN != cVar) {
                this.m.remove(cVarN);
                this.l.add(cVarN);
            }
            iA += cVarN.C();
            cVarN = cVarN.g();
        }
        if (!r && cVar.i() == Integer.MIN_VALUE) {
            throw new AssertionError();
        }
        g(cVar);
        this.k.add(cVar);
        this.h.addAll(c2879qr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00fe, code lost:
    
        if (r9 != h()) goto L57;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int a(com.android.tools.r8.ir.regalloc.c r9, com.android.tools.r8.internal.C2732or r10) {
        /*
            Method dump skipped, instruction units count: 264
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.ir.regalloc.b.a(com.android.tools.r8.ir.regalloc.c, com.android.tools.r8.internal.or):int");
    }

    private boolean a(int i, int i2, c cVar) {
        c next;
        if (i > i2) {
            return true;
        }
        if (i < this.c) {
            c cVarR = this.e.r();
            while (!cVarR.a(i, cVar.o().b())) {
                cVarR = cVarR.g();
                if (!r && cVarR == null) {
                    throw new AssertionError();
                }
            }
            do {
                if (cVarR.a(cVar)) {
                    this.h.remove(Integer.valueOf(i));
                    if (i == cVarR.i() && cVarR.o().b()) {
                        this.h.remove(Integer.valueOf(i + 1));
                    }
                    return false;
                }
                cVarR = cVarR.g();
                if (cVarR == null) {
                    break;
                }
            } while (cVarR.a(i, cVar.o().b()));
        }
        Iterator<c> it = this.l.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (next.a(i, cVar.o().b()) && cVar.d(next) != -1) {
                break;
            }
        }
        if (next != null) {
            this.h.remove(Integer.valueOf(i));
            if (i == next.i() && next.o().b()) {
                this.h.remove(Integer.valueOf(i + 1));
            }
            return false;
        }
        if (!i() || ((i != h() && (!cVar.o().b() || i + 1 != h())) || !e(cVar))) {
            return true;
        }
        this.h.remove(Integer.valueOf(i));
        return false;
    }

    final int c(int i) {
        boolean z = r;
        if (!z && i == Integer.MIN_VALUE) {
            throw new AssertionError();
        }
        if (!z && i < 0) {
            throw new AssertionError();
        }
        int i2 = this.c;
        return i < i2 ? this.i - ((i2 - i) - 1) : i - i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(c cVar) {
        if (!b().y() || cVar.C() == 1 || cVar.r().i() || cVar.l() != cVar) {
            return false;
        }
        AbstractC2412ko abstractC2412ko = cVar.r().b;
        if (abstractC2412ko.n1() && abstractC2412ko.r().J2() == EnumC2527mI.LONG) {
            return (abstractC2412ko instanceof H1) || (abstractC2412ko instanceof C3218vY);
        }
        if (abstractC2412ko.i2() && abstractC2412ko.l0().J2() == EnumC2527mI.LONG) {
            return (abstractC2412ko instanceof NK) || (abstractC2412ko instanceof P30) || (abstractC2412ko instanceof X1);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(c cVar, int i) {
        boolean z = r;
        if (!z && !c(cVar)) {
            throw new AssertionError();
        }
        S10 s10L2 = cVar.r().b.x().L2();
        S10 s10N2 = cVar.r().b.x().N2();
        int i2 = s10L2.r().d(cVar.m()).i();
        int i3 = s10N2.r().d(cVar.m()).i();
        if (z || !(i2 == Integer.MIN_VALUE || i3 == Integer.MIN_VALUE)) {
            return i == i2 + 1 || i + 1 == i2 || i == i3 + 1 || i + 1 == i3;
        }
        throw new AssertionError();
    }

    private void a(c cVar, int i) {
        int i2;
        if (!r && (cVar.C() + i) - 1 > this.i) {
            throw new AssertionError();
        }
        cVar.j(i);
        S10 s10R = cVar.r();
        Iterator it = s10R.g0().iterator();
        while (true) {
            i2 = 0;
            if (!it.hasNext()) {
                break;
            }
            ML ml = (ML) it.next();
            c cVarR = ml.r();
            if (cVarR.e() == null) {
                cVarR.a(cVar, this.m);
                while (i2 < ml.k0().size()) {
                    c cVarD = ml.c(i2).r().d(ml.h().y().get(i2).m().W0());
                    if (cVarD.e() == null) {
                        cVarD.a(cVar, this.m);
                    }
                    i2++;
                }
            }
        }
        if (s10R.i() && cVar.l() == cVar) {
            ML mlL = s10R.l();
            C3332x4 c3332x4H = mlL.h();
            while (i2 < mlL.k0().size()) {
                mlL.c(i2).r().d(c3332x4H.y().get(i2).m().W0()).a(cVar, this.m);
                i2++;
            }
        }
    }

    private int a(Predicate predicate, BiPredicate biPredicate, int i, c cVar, int i2, boolean z, l lVar, i iVar) {
        if (predicate.test(cVar)) {
            while (biPredicate.test(cVar, Integer.valueOf(i))) {
                lVar.c(i);
                int iA = a(i2, cVar, iVar, lVar, z);
                if (i == iA) {
                    if (r) {
                        return -1;
                    }
                    throw new AssertionError("Unexpected attempt to take blocked register " + iA + " in " + this.b.h().w());
                }
                if (iA == -1) {
                    return iA;
                }
                i = iA;
            }
        }
        return i;
    }

    private int a(int i, c cVar, i iVar, k kVar, boolean z) {
        int iA = a(i, cVar, iVar, (j) kVar, z);
        if (iA == -1) {
            return iA;
        }
        l lVar = new l(kVar);
        return a(new Predicate() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((c) obj);
            }
        }, new BiPredicate() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda13
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return this.f$0.b((c) obj, ((Integer) obj2).intValue());
            }
        }, a(new Predicate() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.d((c) obj);
            }
        }, new BiPredicate() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda11
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return this.f$0.d((c) obj, ((Integer) obj2).intValue());
            }
        }, a(new Predicate() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.c((c) obj);
            }
        }, new BiPredicate() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda9
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return this.f$0.c((c) obj, ((Integer) obj2).intValue());
            }
        }, iA, cVar, i, z, lVar, iVar), cVar, i, z, lVar, iVar), cVar, i, z, lVar, iVar);
    }

    private void a(c cVar, int i, boolean z) {
        d dVar;
        boolean z2 = r;
        int i2 = Integer.MIN_VALUE;
        if (!z2 && cVar.i() != Integer.MIN_VALUE) {
            throw new AssertionError();
        }
        if (!z2 && this.h.contains(Integer.valueOf(i)) && (!z || this.h.contains(Integer.valueOf(i + 1)))) {
            throw new AssertionError();
        }
        final C2732or c2732or = new C2732or(z ? 2 : 1);
        c2732or.a(i);
        if (z) {
            c2732or.a(i + 1);
        }
        if (cVar.u() && cVar != cVar.l()) {
            cVar.l().a(new IntConsumer() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda0
                @Override // java.util.function.IntConsumer
                public final void accept(int i3) {
                    c2732or.a(i3);
                }
            });
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = this.k.iterator();
        while (it.hasNext()) {
            c cVar2 = (c) it.next();
            boolean z3 = r;
            if (!z3 && !f(cVar2)) {
                throw new AssertionError();
            }
            if (cVar2.a(i, z)) {
                it.remove();
                int iA = a(cVar2, c2732or);
                a(cVar2);
                c cVarK = cVar2.k(cVar.m());
                a(cVarK, iA);
                cVarK.a(true);
                g(cVarK);
                if (!z3 && cVarK.i() == i2) {
                    throw new AssertionError();
                }
                if (!z3 && cVar2.i() == i2) {
                    throw new AssertionError();
                }
                arrayList.add(cVarK);
                if (cVar2.r().J() && cVar2.m() == cVar2.r().b.W0() && cVar2.q().size() == 1) {
                    cVar2.a(true);
                }
                if (cVarK.q().size() <= 0) {
                    continue;
                } else if (cVarK.w() && !cVarK.u()) {
                    c cVarK2 = cVarK.k(cVarK.d());
                    cVarK2.j(cVar2.i());
                    this.l.add(cVarK2);
                } else if (cVar2.r().J()) {
                    if (!z3 && !cVarK.y()) {
                        throw new AssertionError();
                    }
                    if (!z3 && !cVarK.r().J()) {
                        throw new AssertionError();
                    }
                    if (!z3 && cVarK.w() && !cVarK.u()) {
                        throw new AssertionError();
                    }
                    if (!cVarK.q().isEmpty()) {
                        c cVarK3 = cVarK.k(cVarK.d());
                        this.m.add(cVarK3);
                        boolean z4 = true;
                        while (z4) {
                            int iM = cVarK3.m();
                            Iterator it2 = cVarK3.q().iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    z4 = false;
                                    break;
                                }
                                d dVar2 = (d) it2.next();
                                if (dVar2.b() - iM > 22) {
                                    c cVarK4 = cVarK3.k(iM + 2);
                                    int iB = dVar2.b();
                                    if (iB % 2 != 1) {
                                        iB--;
                                    }
                                    if (iB > cVarK4.m()) {
                                        a(cVarK4, iA);
                                        cVarK4.a(true);
                                        this.l.add(cVarK4);
                                        cVarK4 = cVarK4.k(dVar2.b());
                                    }
                                    cVarK3 = cVarK4;
                                    this.m.add(cVarK3);
                                    z4 = true;
                                    i2 = Integer.MIN_VALUE;
                                    break;
                                }
                                iM = dVar2.b();
                                i2 = Integer.MIN_VALUE;
                            }
                        }
                    }
                } else {
                    if (cVar2.u()) {
                        if (!z3 && !cVarK.y()) {
                            throw new AssertionError();
                        }
                        if (!z3 && !cVarK.u()) {
                            throw new AssertionError();
                        }
                        if (!cVarK.q().isEmpty()) {
                            this.m.add(cVarK.k(((d) cVarK.q().first()).b()));
                        }
                    } else {
                        if (!z3 && !cVarK.y()) {
                            throw new AssertionError();
                        }
                        if (!z3 && cVarK.r().J()) {
                            throw new AssertionError();
                        }
                        if (!z3 && cVarK.w() && !cVarK.u()) {
                            throw new AssertionError();
                        }
                        if (cVarK.u() || iA < this.c) {
                            iA = this.g == 2 ? 255 : 65535;
                        }
                        int iC = (cVarK.C() + iA) - 1;
                        Iterator it3 = cVarK.q().iterator();
                        boolean z5 = false;
                        while (true) {
                            if (!it3.hasNext()) {
                                dVar = null;
                                break;
                            }
                            dVar = (d) it3.next();
                            if (iC > dVar.a()) {
                                break;
                            } else {
                                z5 = true;
                            }
                        }
                        if (z5) {
                            cVarK.a(false);
                        }
                        if (dVar != null) {
                            this.m.add(cVarK.k(dVar.b()));
                        }
                    }
                    i2 = Integer.MIN_VALUE;
                }
            }
        }
        this.k.addAll(arrayList);
        if (r) {
            return;
        }
        if (this.h.contains(Integer.valueOf(i)) || (i() && i == h())) {
            if (!z) {
                return;
            }
            int i3 = i + 1;
            if (this.h.contains(Integer.valueOf(i3))) {
                return;
            }
            if (i() && i3 == h()) {
                return;
            }
        }
        throw new AssertionError();
    }

    private static void a(List list, c cVar, int i, k kVar, k kVar2) {
        int i2;
        int iC;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            c cVar2 = (c) it.next();
            if (cVar2.w() && (i2 = cVar2.i()) <= i && cVar2.d(cVar) != -1) {
                for (int i3 = 0; i3 < cVar2.C(); i3++) {
                    int i4 = i2 + i3;
                    if (i4 <= i && (iC = cVar2.c(cVar.m())) < kVar2.a(i4)) {
                        kVar2.a(i4, iC, cVar2);
                        if (!r && !kVar.b(i4) && kVar.a(i4) > kVar2.a(i4)) {
                            throw new AssertionError();
                        }
                    }
                }
            }
        }
    }

    private static void a(S10 s10, C3332x4 c3332x4, int i, ArrayList arrayList, C2409kl c2409kl) {
        int iW0 = c3332x4.k().W0();
        int size = ((c3332x4.p().size() * 2) + iW0) - 2;
        int iW1 = s10.i() ? iW0 : s10.b.W0();
        if (s10.r() == null) {
            S10 s10V = s10.v();
            c cVar = new c(s10V);
            while (true) {
                arrayList.add(cVar);
                s10V = s10V.t();
                if (s10V == null) {
                    break;
                }
                c cVar2 = new c(s10V);
                cVar.c(cVar2);
                cVar = cVar2;
            }
        }
        c cVarR = s10.r();
        if (iW0 <= iW1 && iW1 <= size) {
            if (s10.i()) {
                iW1--;
            }
            cVarR.a(new e(iW1, i));
            if (!r) {
                int iJ = cVarR.j();
                if (c2409kl.k().a() && iJ != 65535) {
                    throw new AssertionError();
                }
            }
            if (c2409kl.k().a() || s10.i()) {
                return;
            }
            cVarR.a(new d(iW1, s10.b.F2()));
            return;
        }
        cVarR.a(new e(iW0 - 1, i));
    }

    public static void a(C0702m c0702m, C2409kl c2409kl, Map map, ArrayList arrayList) {
        AbstractC2963s10 it = c2409kl.D().iterator();
        while (it.hasNext()) {
            C3332x4 c3332x4 = (C3332x4) it.next();
            LinkedHashSet<S10> linkedHashSet = new LinkedHashSet();
            LinkedHashSet linkedHashSet2 = new LinkedHashSet();
            LinkedHashSet<S10> linkedHashSet3 = new LinkedHashSet();
            Set setC = c3332x4.n().c();
            for (C3332x4 c3332x5 : c3332x4.z()) {
                boolean zContains = setC.contains(c3332x5);
                if (zContains) {
                    linkedHashSet3.addAll(((C2329jl) map.get(c3332x5)).a);
                } else {
                    linkedHashSet.addAll(((C2329jl) map.get(c3332x5)).a);
                }
                if (!r && zContains && !c3332x5.x().isEmpty()) {
                    throw new AssertionError();
                }
                for (ML ml : c3332x5.x()) {
                    linkedHashSet.remove(ml);
                    linkedHashSet2.add(ml.c(c3332x5.y().indexOf(c3332x4)));
                }
            }
            linkedHashSet.addAll(linkedHashSet2);
            LinkedList<AbstractC2412ko> linkedListP = c3332x4.p();
            for (S10 s10 : linkedHashSet) {
                int size = (linkedListP.size() * 2) + c3332x4.k().W0();
                if (linkedHashSet2.contains(s10)) {
                    size--;
                }
                a(s10, c3332x4, size, arrayList, c2409kl);
            }
            C3479z4 c3479z4D = c3332x4.d(c3332x4.p().size());
            while (c3479z4D.hasPrevious()) {
                AbstractC2412ko abstractC2412koPrevious = c3479z4D.previous();
                S10 s10B = abstractC2412koPrevious.b();
                if (s10B != null) {
                    if (s10B instanceof C2708oW) {
                        for (C2617nW c2617nW : ((C2708oW) s10B).j0()) {
                            linkedHashSet.remove(c2617nW);
                        }
                    } else if (!s10B.S()) {
                        a(s10B, c3332x4, abstractC2412koPrevious.W0() + 1, arrayList, c2409kl);
                        if (!r && c2409kl.k().a() && !abstractC2412koPrevious.m1()) {
                            throw new AssertionError("Arguments should be the only potentially unused local in CF");
                        }
                    }
                    linkedHashSet.remove(s10B);
                }
                for (S10 s11 : (ArrayList) abstractC2412koPrevious.h1()) {
                    if (s11.Y()) {
                        if (!r) {
                            int iE2 = abstractC2412koPrevious.E2();
                            if (c2409kl.k().a() && iE2 != 65535) {
                                throw new AssertionError();
                            }
                        }
                        if (!linkedHashSet.contains(s11)) {
                            linkedHashSet.add(s11);
                            a(s11, c3332x4, abstractC2412koPrevious.W0(), arrayList, c2409kl);
                        }
                        if (!c2409kl.k().a()) {
                            int iE3 = abstractC2412koPrevious.E2();
                            c cVarR = s11.r();
                            if (!s11.H() || iE3 != 65535) {
                                cVarR.a(new d(abstractC2412koPrevious.W0(), iE3));
                            }
                        }
                    }
                }
                if (abstractC2412koPrevious.g()) {
                    for (S10 s12 : linkedHashSet3) {
                        if (s12.Y() && !linkedHashSet.contains(s12)) {
                            linkedHashSet.add(s12);
                            int iW0 = abstractC2412koPrevious.W0();
                            if (abstractC2412koPrevious.w1() && s12 != abstractC2412koPrevious.y().e()) {
                                iW0 += 2;
                            }
                            a(s12, c3332x4, iW0, arrayList, c2409kl);
                        }
                    }
                }
                if (c0702m.Y().W0 || c2409kl.h().a().f(c0702m)) {
                    int iW1 = abstractC2412koPrevious.W0();
                    ArrayList<S10> arrayList2 = new ArrayList(abstractC2412koPrevious.S0());
                    arrayList2.sort(new kl$$ExternalSyntheticLambda10());
                    for (S10 s13 : arrayList2) {
                        if (!r && !s13.Y()) {
                            throw new AssertionError();
                        }
                        if (!linkedHashSet.contains(s13)) {
                            linkedHashSet.add(s13);
                            a(s13, c3332x4, iW1, arrayList, c2409kl);
                        }
                    }
                }
            }
        }
    }

    private static boolean a(C2409kl c2409kl) {
        if (c2409kl.d.size() > 1) {
            return false;
        }
        InterfaceC2488lo interfaceC2488loR = c2409kl.j().R();
        while (interfaceC2488loR.hasNext() && interfaceC2488loR.o().m1()) {
            interfaceC2488loR.next();
        }
        while (interfaceC2488loR.hasNext() && interfaceC2488loR.o().w1() && interfaceC2488loR.o().y().e().H()) {
            interfaceC2488loR.next();
        }
        if (interfaceC2488loR.hasNext() && interfaceC2488loR.next().U1()) {
            if (interfaceC2488loR.hasNext() && interfaceC2488loR.o().w1()) {
                interfaceC2488loR.next();
            }
            if (interfaceC2488loR.hasNext() && interfaceC2488loR.next().u2()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:51:0x010f  */
    private void a(AbstractC1084Is abstractC1084Is, InterfaceC2641no interfaceC2641no) {
        PriorityQueue priorityQueue;
        C3492zF c3492zF;
        if (abstractC1084Is.P2() > 5) {
            Iterator it = ((ArrayList) abstractC1084Is.J2()).iterator();
            S10 s10 = (S10) it.next();
            while (it.hasNext()) {
                S10 s11 = (S10) it.next();
                if (s10.O() && s10.t() == s11) {
                    s10 = s11;
                } else {
                    List listJ2 = abstractC1084Is.J2();
                    if (abstractC1084Is.P2() > 16) {
                        priorityQueue = new PriorityQueue(new Comparator() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda14
                            @Override // java.util.Comparator
                            public final int compare(Object obj, Object obj2) {
                                return b.a((C3492zF) obj, (C3492zF) obj2);
                            }
                        });
                        C3332x4 c3332x4H = abstractC1084Is.h();
                        if (c3332x4H.k().W0() == -1) {
                            c3332x4H.f(0);
                        }
                    } else {
                        priorityQueue = null;
                    }
                    int i = 0;
                    S10 s12 = null;
                    while (true) {
                        ArrayList arrayList = (ArrayList) listJ2;
                        if (i < arrayList.size()) {
                            S10 s13 = (S10) arrayList.get(i);
                            AbstractC2412ko abstractC2412ko = s13.b;
                            if (abstractC2412ko != null && abstractC2412ko.l2() && !s13.O() && s13 != s12) {
                                Iterator<AbstractC2412ko> it2 = s13.h0().iterator();
                                while (true) {
                                    if (it2.hasNext()) {
                                        if (it2.next().E2() != 65535) {
                                            S10 s10A = this.b.a((X) null, s13.w());
                                            s10A.a(true);
                                            c3492zF = new C3492zF(s10A, s13);
                                            c3492zF.a(abstractC1084Is.h());
                                            S10 s14 = (S10) ((ArrayList) abstractC1084Is.J2()).get(i);
                                            ((ArrayList) abstractC1084Is.J2()).set(i, s10A);
                                            s14.e(abstractC1084Is);
                                            s10A.b(abstractC1084Is);
                                            AbstractC2412ko abstractC2412ko2 = s13.b;
                                            if (abstractC2412ko2 == null) {
                                            }
                                            if (abstractC1084Is.P2() <= 16) {
                                                interfaceC2641no.add(c3492zF);
                                                c3492zF.b(abstractC1084Is.Y0());
                                            } else {
                                                interfaceC2641no.add(c3492zF);
                                                c3492zF.b(abstractC1084Is.Y0());
                                            }
                                            s13 = s10A;
                                        }
                                    }
                                }
                            } else {
                                S10 s10A2 = this.b.a((X) null, s13.w());
                                s10A2.a(true);
                                c3492zF = new C3492zF(s10A2, s13);
                                c3492zF.a(abstractC1084Is.h());
                                S10 s15 = (S10) ((ArrayList) abstractC1084Is.J2()).get(i);
                                ((ArrayList) abstractC1084Is.J2()).set(i, s10A2);
                                s15.e(abstractC1084Is);
                                s10A2.b(abstractC1084Is);
                                AbstractC2412ko abstractC2412ko3 = s13.b;
                                boolean z = abstractC2412ko3 == null && abstractC2412ko3.h() == abstractC1084Is.h();
                                if (abstractC1084Is.P2() <= 16 && z) {
                                    if (!r && c3492zF.K2().b.W0() < 0) {
                                        throw new AssertionError();
                                    }
                                    priorityQueue.add(c3492zF);
                                    c3492zF.b(s13.b.Y0());
                                } else {
                                    interfaceC2641no.add(c3492zF);
                                    c3492zF.b(abstractC1084Is.Y0());
                                }
                                s13 = s10A2;
                            }
                            if (s12 != null) {
                                s12.f(s13);
                            }
                            i++;
                            s12 = s13;
                        } else {
                            if (priorityQueue == null || priorityQueue.isEmpty()) {
                                return;
                            }
                            C3492zF c3492zF2 = (C3492zF) priorityQueue.poll();
                            AbstractC2412ko abstractC2412ko4 = (c3492zF2.K2().H() ? this.f : c3492zF2.K2()).b;
                            while (interfaceC2641no.j() != abstractC2412ko4) {
                                interfaceC2641no.previous();
                            }
                            interfaceC2641no.add(c3492zF2);
                            while (!priorityQueue.isEmpty()) {
                                C3492zF c3492zF3 = (C3492zF) priorityQueue.poll();
                                AbstractC2412ko abstractC2412ko5 = (c3492zF3.K2().H() ? this.f : c3492zF3.K2()).b;
                                if (!r && abstractC2412ko5.W0() < abstractC2412ko4.W0()) {
                                    throw new AssertionError();
                                }
                                if (abstractC2412ko5.W0() > abstractC2412ko4.W0()) {
                                    while (interfaceC2641no.j() != abstractC2412ko5) {
                                        interfaceC2641no.next();
                                    }
                                }
                                interfaceC2641no.add(c3492zF3);
                                abstractC2412ko4 = abstractC2412ko5;
                            }
                            while (interfaceC2641no.o() != abstractC1084Is) {
                                interfaceC2641no.next();
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C3492zF c3492zF, C3492zF c3492zF2) {
        return c3492zF.K2().b.W0() - c3492zF2.K2().b.W0();
    }

    private int a(int i, boolean z) {
        int iIntValue;
        int i2;
        int i3;
        int iIntValue2;
        int i4 = this.i;
        TreeSet treeSet = this.h;
        if (z) {
            treeSet = new TreeSet(new Comparator() { // from class: com.android.tools.r8.ir.regalloc.b$$ExternalSyntheticLambda3
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return this.f$0.a((Integer) obj, (Integer) obj2);
                }
            });
            treeSet.addAll(this.h);
        }
        Iterator it = treeSet.iterator();
        if (it.hasNext()) {
            iIntValue = ((Integer) it.next()).intValue();
        } else {
            iIntValue = this.i + 1;
            this.i = iIntValue;
        }
        while (true) {
            i2 = iIntValue;
            while ((iIntValue - i2) + 1 != i) {
                for (int i5 = 0; i5 < i - 1; i5++) {
                    if (it.hasNext()) {
                        iIntValue2 = ((Integer) it.next()).intValue();
                    } else {
                        iIntValue2 = this.i + 1;
                        this.i = iIntValue2;
                    }
                    iIntValue++;
                    if (iIntValue2 != iIntValue || iIntValue2 == this.c) {
                        iIntValue = iIntValue2;
                    }
                }
            }
            break;
        }
        while (true) {
            i4++;
            if (i4 <= this.i) {
                boolean zAdd = this.h.add(Integer.valueOf(i4));
                if (!r && !zAdd) {
                    throw new AssertionError();
                }
            } else {
                if (r || ((i2 < (i3 = this.c) && (i2 + i) - 1 < i3) || (i2 >= i3 && (i + i2) - 1 >= i3))) {
                    return i2;
                }
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int a(Integer num, Integer num2) {
        boolean z = num.intValue() < this.c;
        boolean z2 = num2.intValue() < this.c;
        if (z && !z2) {
            return 1;
        }
        if (z || !z2) {
            return num.intValue() - num2.intValue();
        }
        return -1;
    }

    private void a(c cVar, C2879qr c2879qr) {
        int i = cVar.i();
        if (!r && i == Integer.MIN_VALUE) {
            throw new AssertionError();
        }
        for (int i2 = 0; i2 < cVar.C(); i2++) {
            int i3 = i + i2;
            if (this.h.remove(Integer.valueOf(i3))) {
                c2879qr.a(i3);
            }
        }
        if (!cVar.u() || cVar == cVar.l()) {
            return;
        }
        c cVarL = cVar.l();
        if (cVarL.i() != i) {
            a(cVarL, c2879qr);
        }
    }

    private void a(c cVar) {
        boolean z = r;
        if (!z && !f(cVar)) {
            throw new AssertionError();
        }
        int i = cVar.i();
        if (!z && (cVar.C() + i) - 1 > this.i) {
            throw new AssertionError();
        }
        this.h.add(Integer.valueOf(i));
        if (cVar.o().b()) {
            this.h.add(Integer.valueOf(i + 1));
        }
        if (!cVar.u() || cVar == cVar.l() || cVar.l().i() == cVar.i()) {
            return;
        }
        a(cVar.l());
    }

    @Override // com.android.tools.r8.ir.regalloc.f
    public final boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        return Objects.equals(c3332x4.q(), c3332x5.q());
    }
}
