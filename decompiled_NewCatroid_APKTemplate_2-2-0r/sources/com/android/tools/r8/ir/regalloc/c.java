package com.android.tools.r8.ir.regalloc;

import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.internal.EnumC1812d20;
import com.android.tools.r8.internal.ML;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.ZL$$ExternalSyntheticLambda0;
import com.beust.jcommander.Parameters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class c implements Comparable<c> {
    static final /* synthetic */ boolean r = true;
    private final S10 a;
    private c b;
    private c c;
    private final c d;
    private final ArrayList e;
    private final C2732or f;
    private boolean g;
    private List h;
    private final TreeSet i;
    private int j;
    private int k;
    private Integer l;
    private boolean m;
    private boolean n;
    private int o;
    private int p;
    private boolean q;

    public c(S10 s10) {
        this.e = new ArrayList();
        this.f = new C2732or();
        this.g = false;
        this.h = new ArrayList();
        this.i = new TreeSet();
        this.j = -1;
        this.k = Integer.MIN_VALUE;
        this.m = false;
        this.n = false;
        this.o = 65535;
        this.p = Integer.MIN_VALUE;
        this.q = false;
        this.a = s10;
        this.n = s10.i0();
        this.d = this;
        s10.a(this);
    }

    private void D() {
        if (this.g) {
            return;
        }
        this.e.sort(Comparator.comparingInt(new ToIntFunction() { // from class: com.android.tools.r8.ir.regalloc.c$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((c) obj).c();
            }
        }));
        this.f.clear();
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            this.f.a(((c) it.next()).c());
        }
        if (!r) {
            for (int i = 0; i < this.e.size(); i++) {
                boolean z = r;
                if (!z && ((c) this.e.get(i)).c() != this.f.i(i)) {
                    throw new AssertionError();
                }
                if (!z && i != 0 && this.f.i(i - 1) > this.f.i(i)) {
                    throw new AssertionError();
                }
            }
        }
        this.g = true;
    }

    public final int A() {
        c cVarN = n();
        int i = cVarN.j;
        int iC = 0;
        if (i == -1) {
            c cVarN2 = n();
            for (c cVar = cVarN2; cVar != null; cVar = cVar.b) {
                iC += cVar.C();
            }
            cVarN2.j = iC;
            return iC;
        }
        if (!r) {
            c cVarN3 = n();
            for (c cVar2 = cVarN3; cVar2 != null; cVar2 = cVar2.b) {
                iC += cVar2.C();
            }
            cVarN3.j = iC;
            if (i != iC) {
                throw new AssertionError();
            }
        }
        return cVarN.j;
    }

    public final int B() {
        Iterator it = this.i.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((d) it.next()).c()) {
                i++;
            }
        }
        return i;
    }

    public final int C() {
        return this.a.d0().c();
    }

    public final String E() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (e eVar : this.h) {
            eVar.getClass();
            if (eVar == e.c) {
                sb.append("--- infinite ---...");
                break;
            }
            while (i < eVar.a) {
                sb.append(" ");
                i++;
            }
            while (i < eVar.b) {
                sb.append(Parameters.DEFAULT_OPTION_PREFIXES);
                i++;
            }
        }
        return sb.toString();
    }

    public final void F() {
        ArrayList arrayList = new ArrayList(this.h);
        for (c cVar : this.e) {
            arrayList.addAll(cVar.h);
            Iterator it = cVar.i.iterator();
            while (it.hasNext()) {
                a((d) it.next());
            }
        }
        Collections.sort(arrayList);
        this.h.clear();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            a((e) it2.next());
        }
        this.e.clear();
        this.o = 65535;
        Iterator it3 = this.i.iterator();
        while (it3.hasNext()) {
            this.o = Math.min(this.o, ((d) it3.next()).a());
        }
    }

    public final boolean G() {
        return this.n;
    }

    public final void a(boolean z) {
        boolean z2 = r;
        if (!z2 && this.k == Integer.MIN_VALUE) {
            throw new AssertionError();
        }
        if (!z2 && z && u() && this.k != this.d.k) {
            throw new AssertionError();
        }
        this.m = z;
    }

    public final boolean b(c cVar) {
        return cVar.a(this.k, this.a.d0().b());
    }

    public void c(c cVar) {
        if (!r && this.j != -1) {
            throw new AssertionError();
        }
        this.b = cVar;
        cVar.c = this;
    }

    @Override // java.lang.Comparable
    public final int compareTo(c cVar) {
        int iIntValue;
        c cVar2 = cVar;
        int iM = m() - cVar2.m();
        if (iM != 0) {
            return iM;
        }
        Integer num = this.l;
        if (num != null && cVar2.l != null && (iIntValue = num.intValue() - cVar2.l.intValue()) != 0) {
            return iIntValue;
        }
        Integer num2 = this.l;
        if (num2 != null && cVar2.l == null) {
            return -1;
        }
        if (num2 == null && cVar2.l != null) {
            return 1;
        }
        int iU = this.a.u() - cVar2.a.u();
        if (r || iU != 0) {
            return iU;
        }
        throw new AssertionError();
    }

    public final int d(c cVar) {
        int i;
        e eVar;
        Iterator it = cVar.h.iterator();
        e eVar2 = (e) it.next();
        Iterator it2 = this.h.iterator();
        do {
            i = -1;
            if (!it2.hasNext()) {
                break;
            }
            eVar = (e) it2.next();
            while (eVar2.b <= eVar.a) {
                if (!it.hasNext()) {
                    return -1;
                }
                eVar2 = (e) it.next();
            }
            i = eVar2.a;
        } while (i >= eVar.b);
        return i;
    }

    public final Integer e() {
        return this.l;
    }

    public final int f() {
        c cVar = this.d;
        int i = cVar.p;
        if (i != Integer.MIN_VALUE) {
            return i;
        }
        boolean z = r;
        if (!z && cVar.d != cVar) {
            throw new AssertionError();
        }
        if (!z && i != Integer.MIN_VALUE) {
            throw new AssertionError();
        }
        if (!cVar.m) {
            cVar.p = cVar.k;
        }
        for (c cVar2 : cVar.e) {
            if (!cVar2.m) {
                cVar.p = Math.max(cVar.p, cVar2.k);
            }
        }
        return cVar.p;
    }

    public final c g() {
        return this.b;
    }

    public final List h() {
        return this.h;
    }

    public final int i() {
        return this.k;
    }

    public final int j() {
        return this.o;
    }

    public final c k(int i) {
        e eVar;
        int i2;
        List list;
        int i3 = i % 2;
        int i4 = i3 == 0 ? i : i + 1;
        int iM = m();
        if (iM % 2 != 0) {
            iM++;
        }
        if (i4 == iM) {
            if (!r && this.i.size() != 0 && d() == i) {
                throw new AssertionError();
            }
            this.k = Integer.MIN_VALUE;
            return this;
        }
        if (!r && this.a.b(new ZL$$ExternalSyntheticLambda0())) {
            throw new AssertionError();
        }
        if (i3 != 1) {
            i--;
        }
        c cVar = new c(this.d);
        this.d.e.add(cVar);
        this.d.g = false;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (i == c()) {
            List list2 = this.h;
            arrayList2.add(new e(i, i));
            list = list2;
        } else {
            int i5 = 0;
            while (i5 < this.h.size() && (((i2 = (eVar = (e) this.h.get(i5)).a) > i || eVar.b <= i) && i2 <= i)) {
                i5++;
            }
            e eVar2 = (e) this.h.get(i5);
            arrayList.addAll(this.h.subList(0, i5));
            if (eVar2.a < i) {
                arrayList.add(new e(eVar2.a, i));
                arrayList2.add(new e(i, eVar2.b));
            } else {
                arrayList2.add(eVar2);
            }
            List list3 = this.h;
            arrayList2.addAll(list3.subList(i5 + 1, list3.size()));
            list = arrayList;
        }
        cVar.h = arrayList2;
        this.h = list;
        while (!this.i.isEmpty() && ((d) this.i.last()).b() >= i) {
            cVar.a((d) this.i.pollLast());
        }
        this.o = 65535;
        Iterator it = this.i.iterator();
        while (it.hasNext()) {
            this.o = Math.min(this.o, ((d) it.next()).a());
        }
        boolean z = r;
        if (!z && this.h.isEmpty()) {
            throw new AssertionError();
        }
        if (z || !cVar.h.isEmpty()) {
            return cVar;
        }
        throw new AssertionError();
    }

    public final c l() {
        return this.d;
    }

    public int m() {
        if (r || !this.h.isEmpty()) {
            return ((e) this.h.get(0)).a;
        }
        throw new AssertionError();
    }

    public final c n() {
        c cVar = this;
        while (true) {
            c cVar2 = cVar.c;
            if (cVar2 == null) {
                return cVar;
            }
            cVar = cVar2;
        }
    }

    public final EnumC1812d20 o() {
        return this.a.d0();
    }

    public final TreeSet q() {
        return this.i;
    }

    public final S10 r() {
        return this.a;
    }

    public final boolean s() {
        return this.e.size() != 0;
    }

    public final boolean t() {
        return !this.i.isEmpty();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("(cons ");
        sb.append(this.j);
        sb.append("): ");
        Iterator it = this.h.iterator();
        while (it.hasNext()) {
            sb.append((e) it.next());
            sb.append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }

    public final boolean u() {
        AbstractC2412ko abstractC2412ko = this.d.a.b;
        return abstractC2412ko != null && abstractC2412ko.m1();
    }

    public final boolean v() {
        S10 s10 = this.a;
        return s10.b != null && s10.J();
    }

    public final boolean w() {
        c cVar = this.d;
        return (cVar.c == null && cVar.b == null) ? false : true;
    }

    public final boolean x() {
        AbstractC2412ko abstractC2412ko = this.a.b;
        return (abstractC2412ko == null || !abstractC2412ko.p2() || this.a.b.t0().K2()) ? false : true;
    }

    public final boolean y() {
        return this.m;
    }

    public final boolean z() {
        if (this.m) {
            c cVar = this.d;
            if (!r && cVar.d != cVar) {
                throw new AssertionError();
            }
            if (cVar.q) {
                return true;
            }
        }
        return false;
    }

    public final void g(int i) {
        if (!r && i < this.d.p) {
            throw new AssertionError();
        }
        this.d.p = i;
    }

    public void j(int i) {
        int i2;
        if (!r && (i2 = this.k) != Integer.MIN_VALUE && i2 != i) {
            throw new AssertionError();
        }
        this.k = i;
    }

    public final d b() {
        for (d dVar : this.i) {
            if (dVar.c()) {
                return dVar;
            }
        }
        return null;
    }

    public int c() {
        if (!r && this.h.isEmpty()) {
            throw new AssertionError();
        }
        List list = this.h;
        return ((e) list.get(list.size() - 1)).b;
    }

    public final int c(int i) {
        for (d dVar : this.i) {
            if (dVar.b() >= i) {
                return dVar.b();
            }
        }
        return Integer.MAX_VALUE;
    }

    public final boolean a(c cVar) {
        c cVar2 = this.d;
        if (cVar2.d(cVar) != -1) {
            return true;
        }
        Iterator it = cVar2.e.iterator();
        while (it.hasNext()) {
            if (((c) it.next()).d(cVar) != -1) {
                return true;
            }
        }
        return false;
    }

    public final int d() {
        return ((d) this.i.first()).b();
    }

    public c d(int i) {
        int iBinarySearch;
        if (!r && this.d != this) {
            throw new AssertionError();
        }
        if (m() <= i && c() > i) {
            return this;
        }
        c cVar = c() == i ? this : null;
        if (this.e.size() > 100) {
            D();
            iBinarySearch = Collections.binarySearch(this.f, Integer.valueOf(i));
            if (iBinarySearch < 0) {
                iBinarySearch = -(iBinarySearch + 1);
            }
        } else {
            iBinarySearch = 0;
        }
        while (iBinarySearch < this.e.size()) {
            c cVar2 = (c) this.e.get(iBinarySearch);
            if (cVar2.m() <= i && cVar2.c() > i) {
                return cVar2;
            }
            if (cVar2.c() == i) {
                cVar = cVar2;
            }
            iBinarySearch++;
        }
        if (cVar != null) {
            return cVar;
        }
        if (r) {
            return null;
        }
        throw new AssertionError("Couldn't find split covering instruction position.");
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0032  */
    /* JADX WARN: Code duplicated, block: B:19:0x003b  */
    public void a(e eVar) {
        boolean z = true;
        if (this.h.size() > 0) {
            List list = this.h;
            e eVar2 = (e) list.get(list.size() - 1);
            eVar2.getClass();
            if (eVar2 == e.c) {
                z = false;
            } else {
                int i = eVar.a;
                if (i % 2 != 0) {
                    i++;
                }
                int i2 = eVar2.b;
                if (i2 % 2 != 0) {
                    i2++;
                }
                if (i2 > i) {
                    z = false;
                } else if (i2 == i) {
                    eVar2.b = eVar.b;
                } else {
                    this.h.add(eVar);
                }
            }
        } else {
            this.h.add(eVar);
        }
        if (!r && !z) {
            throw new AssertionError();
        }
    }

    public final boolean f(int i) {
        for (e eVar : this.h) {
            if (eVar.a > i) {
                return false;
            }
            if (i < eVar.b) {
                return true;
            }
        }
        return false;
    }

    public c(c cVar) {
        this.e = new ArrayList();
        this.f = new C2732or();
        this.g = false;
        this.h = new ArrayList();
        this.i = new TreeSet();
        this.j = -1;
        this.k = Integer.MIN_VALUE;
        this.m = false;
        this.n = false;
        this.o = 65535;
        this.p = Integer.MIN_VALUE;
        this.q = false;
        this.d = cVar;
        this.a = cVar.a;
        this.n = cVar.n;
    }

    public void a(d dVar) {
        this.i.add(dVar);
        this.o = Math.min(this.o, dVar.a());
    }

    public final boolean a(int i, boolean z) {
        if (this.k == i) {
            return true;
        }
        if (this.a.d0().b() && this.k + 1 == i) {
            return true;
        }
        return z && this.k == i + 1;
    }

    public final void a() {
        this.k = Integer.MIN_VALUE;
        this.l = null;
    }

    public final void a(c cVar, PriorityQueue priorityQueue) {
        if (d(cVar) != -1) {
            return;
        }
        boolean zRemove = priorityQueue.remove(this);
        this.l = Integer.valueOf(cVar.k);
        if (zRemove) {
            priorityQueue.add(this);
        }
    }

    public final void a(IntConsumer intConsumer) {
        if (!r && this.k == Integer.MIN_VALUE) {
            throw new AssertionError();
        }
        intConsumer.accept(this.k);
        if (this.a.d0().b()) {
            intConsumer.accept(this.k + 1);
        }
    }

    public final void a(b bVar) {
        if (!r && this.d != this) {
            throw new AssertionError();
        }
        if (this.a.H()) {
            this.q = true;
            return;
        }
        if (!this.a.J()) {
            return;
        }
        Iterator it = this.a.g0().iterator();
        while (true) {
            if (it.hasNext()) {
                ML ml = (ML) it.next();
                if (bVar.c(ml.r().k) >= 255) {
                    for (int i = 0; i < ml.k0().size(); i++) {
                        if (ml.c(i) == this.a && d(ml.h().y().get(i).m().W0()).m) {
                            return;
                        }
                    }
                }
            } else {
                if (f() == Integer.MIN_VALUE) {
                    boolean z = r;
                    if (!z) {
                        if (!z && !this.m) {
                            throw new AssertionError();
                        }
                        for (c cVar : this.e) {
                            if (!r && !cVar.m) {
                                throw new AssertionError();
                            }
                        }
                    }
                    this.q = true;
                    return;
                }
                this.q = bVar.c(f()) < 255;
                return;
            }
        }
    }

    public final List k() {
        return this.e;
    }
}
