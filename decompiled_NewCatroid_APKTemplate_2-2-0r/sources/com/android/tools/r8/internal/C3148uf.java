package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0487p2;
import com.android.tools.r8.dex.code.C0511u2;
import com.android.tools.r8.dex.code.C0516v2;
import com.android.tools.r8.dex.code.C0521w2;
import com.android.tools.r8.dex.code.C0524x0;
import com.android.tools.r8.dex.code.C0529y0;
import com.android.tools.r8.dex.code.C0537z0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0772w0;
import com.android.tools.r8.graph.C0797x0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3148uf implements KV {
    static final /* synthetic */ boolean n = true;
    private final C0772w0 a;
    private final com.android.tools.r8.graph.o5 b;
    private boolean i;
    private final C2828q6 k;
    private List l;
    private final C0684j2 m;
    private final HashMap c = new HashMap();
    private final LY d = new LY();
    private final X2 e = new X2();
    private C0772w0.a f = null;
    private C3038t6 g = null;
    private com.android.tools.r8.dex.code.B1 h = null;
    private AbstractC1915eM j = null;

    public C3148uf(C0772w0 c0772w0, com.android.tools.r8.graph.o5 o5Var, C0684j2 c0684j2, AbstractC1915eM abstractC1915eM, C0722p1 c0722p1) {
        this.l = null;
        this.a = c0772w0;
        this.b = o5Var;
        this.m = c0684j2;
        com.android.tools.r8.graph.J0.a aVarA = com.android.tools.r8.graph.J0.a(c0772w0, c0722p1);
        if (aVarA != null) {
            this.l = aVarA.a(c0684j2);
        }
        List list = this.l;
        this.k = new C2828q6(abstractC1915eM, list == null ? 0 : list.size(), c0684j2, o5Var.e().N0(), com.android.tools.r8.utils.E0.a(c0684j2, aVarA).a());
    }

    public static void a(Integer num, C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X a(int i) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X a(int i, int i2) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl) {
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl, int i, int i2, boolean z) {
    }

    @Override // com.android.tools.r8.internal.KV
    public final void b() {
        int i = 0;
        while (true) {
            com.android.tools.r8.dex.code.B1[] b1Arr = this.a.i;
            if (i >= b1Arr.length) {
                return;
            }
            com.android.tools.r8.dex.code.B1 b1 = b1Arr[i];
            this.c.put(Integer.valueOf(b1.u()), Integer.valueOf(i));
            if (b1.K()) {
                if (b1.M()) {
                    this.d.a((com.android.tools.r8.dex.code.d4) b1);
                } else {
                    this.e.a((C0529y0) b1);
                }
            }
            i++;
        }
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X c(int i) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void clear() {
        this.d.a();
        this.e.a();
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean d() {
        return true;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int e() {
        return this.a.i.length;
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean f(int i) {
        return i < this.a.d;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int g(int i) {
        return ((Integer) this.c.get(Integer.valueOf(i))).intValue();
    }

    @Override // com.android.tools.r8.internal.KV
    public final void c(C2102gl c2102gl) {
        if (!n && this.i) {
            throw new AssertionError();
        }
        this.i = true;
        this.j = this.k.a();
        C0772w0 c0772w0 = this.a;
        int i = c0772w0.e;
        if (i > 0) {
            c2102gl.a(c0772w0.d - i, this.b.e(), new VY$$ExternalSyntheticLambda2());
        }
        this.i = false;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int d(int i) {
        com.android.tools.r8.dex.code.B1 b1 = this.a.i[i];
        if (b1 instanceof C0487p2) {
            return ((C0487p2) b1).e;
        }
        return -1;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int e(int i) {
        return this.a.i[i].u();
    }

    @Override // com.android.tools.r8.internal.KV
    public final AbstractC1915eM f() {
        return this.j;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl, int i, boolean z) {
        C0797x0 c0797x0;
        C0772w0.a aVar;
        C0722p1 c0722p1A = c2102gl.o.a();
        int iE = e(i);
        C0772w0.a[] aVarArr = this.a.g;
        int length = aVarArr.length;
        int i2 = 0;
        while (true) {
            c0797x0 = null;
            if (i2 >= length) {
                aVar = null;
                break;
            }
            aVar = aVarArr[i2];
            int i3 = aVar.b;
            if (i3 <= iE && iE < i3 + aVar.c) {
                break;
            } else {
                i2++;
            }
        }
        if (aVar != this.f) {
            this.f = aVar;
            if (aVar == null) {
                this.g = null;
            } else {
                this.g = a(aVar, c0722p1A);
            }
        }
        List list = this.l;
        if (list != null && !list.isEmpty()) {
            int iE2 = e(i);
            List<C0797x0> list2 = this.l;
            if (list2 != null) {
                for (C0797x0 c0797x1 : list2) {
                    if (c0797x1.b > iE2) {
                        break;
                    } else {
                        c0797x0 = c0797x1;
                    }
                }
            }
            if (c0797x0 == null) {
                this.j = this.k.a();
            } else {
                AbstractC1915eM abstractC1915eMB = c0797x0.b();
                if (!n && abstractC1915eMB.k() && !abstractC1915eMB.a(this.m)) {
                    throw new AssertionError();
                }
                AbstractC1915eM abstractC1915eMB2 = this.k.b(abstractC1915eMB.a().a(this.k.a(abstractC1915eMB.b())).a());
                this.j = abstractC1915eMB2;
                if (c0797x0.a && c0797x0.b == iE2) {
                    c2102gl.a(abstractC1915eMB2);
                }
            }
        }
        com.android.tools.r8.dex.code.B1 b1 = this.a.i[i];
        this.h = b1;
        b1.a(c2102gl);
    }

    @Override // com.android.tools.r8.internal.KV
    public final C3038t6 b(C2102gl c2102gl) {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.KV
    public final AbstractC1915eM b(int i) {
        List<C0797x0> list = this.l;
        C0797x0 c0797x0 = null;
        if (list != null) {
            for (C0797x0 c0797x1 : list) {
                if (c0797x1.b > i) {
                    break;
                }
                c0797x0 = c0797x1;
            }
        }
        if (c0797x0 == null) {
            return this.k.a();
        }
        AbstractC1915eM abstractC1915eMB = c0797x0.b();
        if (n || !abstractC1915eMB.k() || abstractC1915eMB.a(this.m)) {
            return this.k.b(abstractC1915eMB.a().a(this.k.a(abstractC1915eMB.b())).a());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean c() {
        return this.i || this.h.l();
    }

    private ArrayList b(C0772w0.a aVar, C0722p1 c0722p1) {
        final ArrayList arrayList = new ArrayList();
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.tools.r8.internal.uf$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                arrayList.add((Integer) obj2);
            }
        };
        C0772w0.b bVar = this.a.h[aVar.d];
        for (C0772w0.b.a aVar2 : bVar.a) {
            biConsumer.accept(aVar2.getType(), Integer.valueOf(aVar2.b));
            if (aVar2.getType() == c0722p1.m3) {
                return arrayList;
            }
        }
        int i = bVar.b;
        if (i != -1) {
            biConsumer.accept(c0722p1.m3, Integer.valueOf(i));
        }
        return arrayList;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(int i, int i2, int i3, C2102gl c2102gl) {
        c2102gl.a(i, this.d.b(i3), i2, this.d.a(i3));
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(int i, int i2, C2102gl c2102gl) {
        c2102gl.a(i, this.e.b(i2), this.e.c(i2), this.e.a(i2));
    }

    @Override // com.android.tools.r8.internal.KV
    public final int a(int i, C2102gl c2102gl) {
        C0772w0.a aVar;
        com.android.tools.r8.dex.code.B1 b1 = this.a.i[i];
        int iU = b1.u();
        boolean z = n;
        if (!z && b1.K()) {
            throw new AssertionError();
        }
        int[] iArrB = b1.B();
        com.android.tools.r8.dex.code.B1[] b1Arr = com.android.tools.r8.dex.code.B1.b;
        int i2 = 0;
        if (iArrB != null) {
            if (!z && b1.l()) {
                throw new AssertionError();
            }
            int length = iArrB.length;
            while (i2 < length) {
                c2102gl.d(iU, iArrB[i2] + iU);
                i2++;
            }
            return i;
        }
        if (b1.l()) {
            if (b1.D()) {
                this.e.a((C0524x0) b1);
            }
            C0772w0.a[] aVarArr = this.a.g;
            int length2 = aVarArr.length;
            while (true) {
                if (i2 < length2) {
                    aVar = aVarArr[i2];
                    int i3 = aVar.b;
                    if (i3 <= iU && iU < i3 + aVar.c) {
                        break;
                    }
                    i2++;
                } else {
                    aVar = null;
                    break;
                }
            }
            if (aVar != null) {
                int i4 = aVar.b;
                com.android.tools.r8.dex.code.B1 b2 = this.a.i[((Integer) this.c.get(Integer.valueOf(i4))).intValue()];
                if ((b2 instanceof C0511u2) || (b2 instanceof C0516v2) || (b2 instanceof C0521w2)) {
                    i4++;
                }
                c2102gl.h(i4);
                Iterator it = new HashSet(b(aVar, c2102gl.o.a())).iterator();
                while (it.hasNext()) {
                    c2102gl.c(iU, ((Integer) it.next()).intValue());
                }
                int i5 = i + 1;
                com.android.tools.r8.dex.code.B1[] b1Arr2 = this.a.i;
                if (i5 < b1Arr2.length) {
                    com.android.tools.r8.dex.code.B1 b3 = b1Arr2[i5];
                    if ((b3 instanceof C0511u2) || (b3 instanceof C0516v2) || (b3 instanceof C0521w2)) {
                        if (!n && !(b1 instanceof com.android.tools.r8.dex.code.K1) && !(b1 instanceof com.android.tools.r8.dex.code.L1) && !(b1 instanceof com.android.tools.r8.dex.code.M1) && !(b1 instanceof com.android.tools.r8.dex.code.N1) && !(b1 instanceof com.android.tools.r8.dex.code.Y1) && !(b1 instanceof com.android.tools.r8.dex.code.Z1) && !(b1 instanceof com.android.tools.r8.dex.code.O1) && !(b1 instanceof com.android.tools.r8.dex.code.P1) && !(b1 instanceof com.android.tools.r8.dex.code.U1) && !(b1 instanceof com.android.tools.r8.dex.code.V1) && !(b1 instanceof com.android.tools.r8.dex.code.W1) && !(b1 instanceof com.android.tools.r8.dex.code.X1) && !(b1 instanceof com.android.tools.r8.dex.code.S1) && !(b1 instanceof com.android.tools.r8.dex.code.T1) && !(b1 instanceof C0537z0) && !(b1 instanceof com.android.tools.r8.dex.code.A0)) {
                            throw new AssertionError();
                        }
                        i = i5;
                        b1 = b3;
                    }
                }
                if (!(b1 instanceof com.android.tools.r8.dex.code.e4)) {
                    c2102gl.d(iU, b1.z() + b1.u());
                }
                return i;
            }
            if (b1 instanceof com.android.tools.r8.dex.code.e4) {
                return i;
            }
            return -1;
        }
        if (!b1.I()) {
            return -1;
        }
        this.d.a(b1);
        LY ly = this.d;
        ly.getClass();
        if (!LY.c && !b1.I()) {
            throw new AssertionError();
        }
        int[] iArrA = ly.a(b1.x() + b1.u());
        int length3 = iArrA.length;
        while (i2 < length3) {
            c2102gl.d(iU, iArrA[i2]);
            i2++;
        }
        c2102gl.d(iU, b1.z() + iU);
        return i;
    }

    private C3038t6 a(C0772w0.a aVar, C0722p1 c0722p1) {
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.tools.r8.internal.uf$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3148uf.a(arrayList, arrayList2, (C0767v2) obj, (Integer) obj2);
            }
        };
        C0772w0.b bVar = this.a.h[aVar.d];
        for (C0772w0.b.a aVar2 : bVar.a) {
            biConsumer.accept(aVar2.getType(), Integer.valueOf(aVar2.b));
            if (aVar2.getType() == c0722p1.m3) {
                return new C3038t6(arrayList, arrayList2);
            }
        }
        int i = bVar.b;
        if (i != -1) {
            biConsumer.accept(c0722p1.m3, Integer.valueOf(i));
        }
        return new C3038t6(arrayList, arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, List list2, C0767v2 c0767v2, Integer num) {
        list.add(c0767v2);
        list2.add(num);
    }
}
