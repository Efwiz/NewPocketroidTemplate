package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1052Hq;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C1021Gq;
import com.android.tools.r8.internal.C1303Qq;
import com.android.tools.r8.internal.C1316Rd;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2807pq;
import com.android.tools.r8.internal.C3406y2;
import com.android.tools.r8.internal.InterfaceC0934Dq;
import com.android.tools.r8.internal.InterfaceC0992Fq;
import com.android.tools.r8.internal.InterfaceC1912eK;
import com.android.tools.r8.internal.YJ;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class H0 {
    static final /* synthetic */ boolean m = true;
    private final W0 a;
    private final C0722p1 b;
    private final com.android.tools.r8.utils.A1 c;
    private ArrayList d;
    private com.android.tools.r8.internal.E e;
    private com.android.tools.r8.internal.E j;
    private com.android.tools.r8.internal.E f = null;
    private boolean g = false;
    private int h = -1;
    private AbstractC1915eM i = AbstractC1915eM.u();
    private final ArrayList k = new ArrayList();
    private int l = -1;

    public H0(C2409kl c2409kl, com.android.tools.r8.utils.A1 a1) {
        this.a = c2409kl.u();
        this.b = a1.a;
        this.c = a1;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    private boolean b() {
        if (!this.g) {
            return false;
        }
        boolean z = !X.a((InterfaceC0992Fq) this.j, (InterfaceC0992Fq) this.f);
        this.g = z;
        return z;
    }

    /* JADX WARN: Code duplicated, block: B:124:0x01ce  */
    /* JADX WARN: Code duplicated, block: B:127:0x01e5  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r2v11, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r2v19, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r2v20, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r2v24, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    /* JADX WARN: Type inference failed for: r4v12, types: [com.android.tools.r8.internal.E, com.android.tools.r8.internal.Fq] */
    public final void a(int i, int i2, AbstractC2412ko abstractC2412ko) {
        int i3;
        boolean z = abstractC2412ko.h().k() == abstractC2412ko;
        boolean z2 = abstractC2412ko.h().m() == abstractC2412ko;
        if (z) {
            boolean z3 = m;
            if (!z3 && this.f != null) {
                throw new AssertionError();
            }
            if (!z3 && this.g) {
                throw new AssertionError();
            }
            InterfaceC0992Fq interfaceC0992FqQ = abstractC2412ko.h().q();
            if (interfaceC0992FqQ == null) {
                this.f = AbstractC1052Hq.a;
            } else {
                C1303Qq c1303Qq = new C1303Qq(interfaceC0992FqQ.size());
                c1303Qq.putAll(interfaceC0992FqQ);
                this.f = c1303Qq;
                this.g = true;
            }
            com.android.tools.r8.internal.E e = this.j;
            if (e == null) {
                if (!z3 && this.d != null) {
                    throw new AssertionError();
                }
                if (!z3 && e != null) {
                    throw new AssertionError();
                }
                if (!z3 && this.e != null) {
                    throw new AssertionError();
                }
                if (!z3 && this.l != -1) {
                    throw new AssertionError();
                }
                if (interfaceC0992FqQ == null) {
                    C1021Gq c1021Gq = AbstractC1052Hq.a;
                    this.j = c1021Gq;
                    this.e = c1021Gq;
                } else {
                    this.j = new C1303Qq(16);
                    InterfaceC1912eK it = interfaceC0992FqQ.c().iterator();
                    while (it.hasNext()) {
                        InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
                        if (((X) interfaceC0934Dq.getValue()).c == null) {
                            this.j.a(interfaceC0934Dq.a(), (X) interfaceC0934Dq.getValue());
                        }
                    }
                    this.e = new C1303Qq((InterfaceC0992Fq) this.j);
                }
            }
        }
        boolean z4 = m;
        if (!z4 && this.f == null) {
            throw new AssertionError();
        }
        AbstractC1915eM abstractC1915eMY0 = abstractC2412ko.Y0();
        boolean z5 = i != i2;
        if (!z4) {
            abstractC2412ko.a(this.c.W0);
        }
        if (abstractC2412ko.m1()) {
            C3406y2 c3406y2Q = abstractC2412ko.q();
            if (this.d == null) {
                this.d = new ArrayList(this.a.getReference().F0());
            }
            if (!c3406y2Q.b().Q()) {
                this.d.add(c3406y2Q.j());
            }
        } else if (abstractC2412ko instanceof C1316Rd) {
            C1316Rd c1316RdH = abstractC2412ko.H();
            this.g = true;
            c1316RdH.a((InterfaceC0992Fq) this.f);
        } else if (z5) {
            if (!abstractC1915eMY0.o() && !abstractC1915eMY0.equals(this.i) && (this.c.W0 || abstractC2412ko.i1())) {
                if (!z4 && abstractC1915eMY0.equals(this.i)) {
                    throw new AssertionError();
                }
                if (this.l != -1) {
                    if (z4 && this.h == i) {
                        throw new AssertionError();
                    }
                    i3 = this.h;
                    if (i3 == -1) {
                        i3 = 0;
                    }
                    a(i3, this.i, i, abstractC1915eMY0, this.k, this.b, false);
                    this.h = i;
                    this.i = abstractC1915eMY0;
                    if (b()) {
                        a(this.j, this.f, this.e, this.k, this.b);
                        if (!z4 && !X.a((InterfaceC0992Fq) this.j, (InterfaceC0992Fq) this.f)) {
                            throw new AssertionError();
                        }
                    }
                    this.g = false;
                } else {
                    if (!z4 && !this.i.o()) {
                        throw new AssertionError();
                    }
                    if (!(abstractC1915eMY0 instanceof AbstractC1915eM.c) || abstractC1915eMY0.k()) {
                        this.l = abstractC1915eMY0.e();
                        this.i = AbstractC1915eM.b.x().a(abstractC1915eMY0.e()).a(abstractC1915eMY0.h().g()).a();
                        if (z4) {
                        }
                        i3 = this.h;
                        if (i3 == -1) {
                            i3 = 0;
                        }
                        a(i3, this.i, i, abstractC1915eMY0, this.k, this.b, false);
                        this.h = i;
                        this.i = abstractC1915eMY0;
                        if (b()) {
                            a(this.j, this.f, this.e, this.k, this.b);
                            if (!z4) {
                                throw new AssertionError();
                            }
                        }
                        this.g = false;
                    }
                }
            }
            if (this.h != i && b()) {
                if (!z4 && this.h == i) {
                    throw new AssertionError();
                }
                int i4 = this.h;
                int i5 = i4 == -1 ? i : i - i4;
                if (!z4 && i5 <= 0 && i4 != -1) {
                    throw new AssertionError();
                }
                if (i5 > 0) {
                    this.k.add(this.b.b(i5));
                }
                this.h = i;
                a(this.j, this.f, this.e, this.k, this.b);
                this.g = false;
                if (!z4 && !X.a((InterfaceC0992Fq) this.j, (InterfaceC0992Fq) this.f)) {
                    throw new AssertionError();
                }
            }
        }
        if (z2) {
            this.f = null;
            this.g = false;
        }
    }

    public final J0.a a() {
        boolean z = m;
        if (!z && this.f != null) {
            throw new AssertionError();
        }
        if (!z && this.g) {
            throw new AssertionError();
        }
        if (this.l == -1) {
            return null;
        }
        int iF0 = this.a.getReference().F0();
        C0759u2[] c0759u2Arr = new C0759u2[iF0];
        ArrayList arrayList = this.d;
        if (arrayList != null) {
            if (!z && iF0 != arrayList.size()) {
                throw new AssertionError();
            }
            for (int i = 0; i < this.d.size(); i++) {
                X x = (X) this.d.get(i);
                c0759u2Arr[i] = (x == null || x.c != null) ? null : x.a;
            }
        }
        if (!m && !com.android.tools.r8.utils.E0.a((B0[]) this.k.toArray(B0.a))) {
            throw new AssertionError();
        }
        return new J0.a(this.l, c0759u2Arr, (B0[]) this.k.toArray(B0.a));
    }

    public static void a(int i, AbstractC1915eM abstractC1915eM, int i2, AbstractC1915eM abstractC1915eM2, List<B0> list, C0722p1 c0722p1, boolean z) {
        boolean z2 = m;
        if (!z2 && i < 0) {
            throw new AssertionError();
        }
        int i3 = i2 - i;
        if (!z2 && abstractC1915eM.o() && !abstractC1915eM2.o()) {
            throw new AssertionError();
        }
        if (!z2 && !abstractC1915eM2.o() && abstractC1915eM2.e() < 0) {
            throw new AssertionError();
        }
        boolean z3 = false;
        int iE = abstractC1915eM2.o() ? 0 : abstractC1915eM2.e() - abstractC1915eM.e();
        if (!z2 && i3 < 0) {
            throw new AssertionError();
        }
        if (abstractC1915eM2.d() != abstractC1915eM.d()) {
            list.add(c0722p1.a(abstractC1915eM2.d()));
        }
        if (!z2 && !abstractC1915eM2.k() && !abstractC1915eM.k() && abstractC1915eM2.g() != abstractC1915eM.g() && !z) {
            throw new AssertionError();
        }
        boolean z4 = (abstractC1915eM2.b() == abstractC1915eM.b() && abstractC1915eM2.g() == abstractC1915eM.g()) ? false : true;
        boolean zQ = abstractC1915eM2.q();
        if (abstractC1915eM2.i() != null && !abstractC1915eM2.j().b()) {
            z3 = true;
        }
        if (z4 || zQ || z3) {
            list.add(c0722p1.a(abstractC1915eM2));
        }
        a(iE, i3, list, c0722p1);
    }

    public static void a(int i, int i2, List list, C0722p1 c0722p1) {
        if (i < -4 || i + 4 >= 15) {
            list.add(c0722p1.a(i));
            i = 0;
        }
        boolean z = B0.b.d;
        int i3 = i + 14;
        int i4 = (i2 * 15) + i3;
        if (i4 > 255) {
            list.add(c0722p1.b(i2));
        } else {
            i3 = i4;
        }
        boolean z2 = m;
        if (!z2 && i3 < 10) {
            throw new AssertionError();
        }
        if (!z2 && i3 > 255) {
            throw new AssertionError();
        }
        list.add(c0722p1.c(i3));
    }

    private static void a(InterfaceC0992Fq interfaceC0992Fq, InterfaceC0992Fq interfaceC0992Fq2, InterfaceC0992Fq interfaceC0992Fq3, ArrayList arrayList, C0722p1 c0722p1) {
        int i = X.d;
        C2807pq c2807pq = new C2807pq();
        InterfaceC1912eK it = interfaceC0992Fq.c().iterator();
        while (it.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
            int iA = interfaceC0934Dq.a();
            X x = (X) interfaceC0934Dq.getValue();
            if (interfaceC0992Fq2.get(iA) != x) {
                c2807pq.a(iA, x);
            }
        }
        C2807pq c2807pq2 = new C2807pq();
        InterfaceC1912eK it2 = interfaceC0992Fq2.c().iterator();
        while (it2.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq2 = (InterfaceC0934Dq) it2.next();
            int iA2 = interfaceC0934Dq2.a();
            Object obj = (X) interfaceC0934Dq2.getValue();
            if (interfaceC0992Fq.get(iA2) != obj) {
                c2807pq2.a(iA2, obj);
            }
        }
        if (!m && c2807pq.isEmpty() && c2807pq2.isEmpty()) {
            throw new AssertionError();
        }
        YJ it3 = c2807pq.c().iterator();
        while (it3.hasNext()) {
            int iA3 = ((InterfaceC0934Dq) it3.next()).a();
            if (!c2807pq2.a(iA3)) {
                interfaceC0992Fq.remove(iA3);
                arrayList.add(c0722p1.d(iA3));
            }
        }
        YJ it4 = c2807pq2.c().iterator();
        while (it4.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq3 = (InterfaceC0934Dq) it4.next();
            int iA4 = interfaceC0934Dq3.a();
            X x2 = (X) interfaceC0934Dq3.getValue();
            interfaceC0992Fq.a(iA4, x2);
            if (interfaceC0992Fq3.get(iA4) == x2) {
                arrayList.add(c0722p1.e(iA4));
            } else {
                arrayList.add(new B0.d(iA4, x2.a, x2.b, x2.c));
                interfaceC0992Fq3.a(iA4, x2);
            }
        }
    }
}
