package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1899eF;
import com.android.tools.r8.internal.AbstractC2509m2;
import com.android.tools.r8.internal.C00;
import com.android.tools.r8.internal.C2044g20;
import com.android.tools.r8.internal.C3263wA;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.EQ;
import com.android.tools.r8.utils.C3894f2;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Q3 extends AbstractC1899eF {
    static final /* synthetic */ boolean x = true;
    private final String j;
    final O3 k;
    private final int l;
    private ArrayList m;
    private B2 n;
    private int o;
    private ArrayList p;
    private ArrayList q;
    private ArrayList r;
    private final C0706m3.g s;
    final C0684j2 t;
    final C0725p4 u;
    final boolean v;
    Z3 w;

    public Q3(int i, String str, String str2, String str3, String[] strArr, O3 o3) {
        super(589824);
        this.m = null;
        this.n = null;
        this.o = -1;
        this.p = null;
        this.q = null;
        this.r = null;
        this.w = null;
        this.j = str;
        this.k = o3;
        this.t = o3.e.b(o3.j, str, str2);
        this.u = T3.a(i, str);
        this.v = com.android.tools.r8.utils.C.a(i);
        this.l = C3970u0.d(str2);
        if (strArr != null && strArr.length > 0) {
            B2[] b2Arr = new B2[strArr.length];
            for (int i2 = 0; i2 < strArr.length; i2++) {
                b2Arr[i2] = new B2.k(o3.e.f(strArr[i2]));
            }
            a(C0633d0.a(b2Arr, o3.e.a()));
        }
        this.s = o3.e.a.u1() ? C0706m3.c(str, str3, o3.c, o3.e.a(), o3.e.a.e) : C0706m3.g.d();
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, C00 c00, C3263wA[] c3263wAArr, C3263wA[] c3263wAArr2, int[] iArr, String str, boolean z) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 b(int i, C00 c00, String str, boolean z) {
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x00af  */
    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c() {
        C0663g5 c0663g5A;
        O3 o3 = this.k;
        M3 m3 = o3.e;
        com.android.tools.r8.utils.A1 a1 = m3.a;
        C0684j2 c0684j2 = this.t;
        I i = o3.d;
        if (a1.C1() && EQ.a(m3.a.a, c0684j2)) {
            m3.b(c0684j2.B0(), i);
        }
        O3 o4 = this.k;
        M3 m4 = o4.e;
        C0684j2 c0684j3 = this.t;
        I i2 = o4.d;
        if (m4.a.D1() && C2044g20.a(m4.a.a, c0684j3)) {
            m4.a(c0684j3.B0(), i2);
        }
        O3 o5 = this.k;
        M3 m5 = o5.e;
        C0684j2 c0684j4 = this.t;
        I i3 = o5.d;
        if (m5.a.D1() && C2044g20.b(m5.a.a, c0684j4)) {
            m5.c(c0684j4.B0(), i3);
        }
        if (!this.u.W() && !this.u.Z()) {
            O3 o6 = this.k;
            if (o6.d == I.c) {
                O3 o7 = this.k;
                this.w = new Z3(o7.c, o7.g, o7.e);
            } else if (!o6.e.a.V()) {
                O3 o8 = this.k;
                if (o8.d == I.d && (!o8.p.isEmpty() || o8.o != null)) {
                    O3 o9 = this.k;
                    this.w = new Z3(o9.c, o9.g, o9.e);
                }
            }
        }
        ArrayList arrayList = this.p;
        boolean z = false;
        if (arrayList == null) {
            c0663g5A = C0663g5.t0();
        } else {
            C0658g0[] c0658g0Arr = new C0658g0[arrayList.size()];
            for (int i4 = 0; i4 < this.p.size(); i4++) {
                c0658g0Arr[i4] = T3.a((List) this.p.get(i4), a1);
            }
            c0663g5A = C0663g5.a(c0658g0Arr, 0);
        }
        ArrayList arrayList2 = this.q;
        if (arrayList2 != null) {
            if (!x && this.r == null) {
                throw new AssertionError();
            }
            int size = arrayList2.size();
            int i5 = this.l;
            if (size != i5) {
                a1.a(this.t, this.k.c, i5, this.q.size());
            }
            if (this.m == null) {
                this.m = new ArrayList();
            }
            ArrayList arrayList3 = this.m;
            ArrayList arrayList4 = this.q;
            B2[] b2Arr = B2.a;
            arrayList3.add(C0633d0.a((B2[]) arrayList4.toArray(b2Arr), (B2[]) this.r.toArray(b2Arr), this.k.e.a.a));
        }
        W0 w0A = W0.S0().a(this.t).a(this.u).a(this.s).a(T3.a(this.m, a1)).a(c0663g5A).a(this.w).a(this.k.h).a(this.v).e().c().a();
        if (this.k.C.add(C3894f2.a().c(this.t))) {
            O3 o10 = this.k;
            boolean z2 = o10.D;
            C0767v2 c0767v2 = o10.e.a.a.l5;
            if (this.m == null) {
                this.m = new ArrayList();
            }
            Iterator it = this.m.iterator();
            while (it.hasNext()) {
                if (((C0633d0) it.next()).b.a == c0767v2) {
                    z = true;
                    break;
                }
            }
            o10.D = z | z2;
            if (this.u.o() || this.u.Y() || this.u.j()) {
                this.k.A.add(w0A);
            } else {
                this.k.B.add(w0A);
            }
        } else {
            a1.e.warning(new StringDiagnostic(String.format("Ignoring an implementation of the method `%s` because it has multiple definitions", this.t.r0())));
        }
        B2 b2 = this.n;
        if (b2 != null) {
            O3 o11 = this.k;
            String str = this.j;
            if (o11.w == null) {
                o11.w = new ArrayList();
            }
            o11.w.add(new C0649f0(o11.e.d(str), b2));
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, String str) {
        if (this.q == null) {
            if (!x && this.r != null) {
                throw new AssertionError();
            }
            this.q = new ArrayList(this.l);
            this.r = new ArrayList(this.l);
        }
        if (str == null) {
            this.q.add(I2.b);
        } else {
            this.q.add(new B2.j(this.k.e.a.a.c(str)));
        }
        this.r.add(B2.g.j(i));
        super.b(i, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a() {
        return new N3(this.k.e, new BiConsumer() { // from class: com.android.tools.r8.graph.Q3$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((List) obj, (List) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, List list2) {
        if (!x && list2.size() != 1) {
            throw new AssertionError();
        }
        this.n = (B2) list2.get(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, boolean z) {
        int i2 = this.o;
        if (i2 != -1 && !x && i2 != i) {
            throw new AssertionError();
        }
        this.o = i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, String str, boolean z) {
        if (this.p == null) {
            if (this.o == -1) {
                this.o = this.l;
            }
            this.p = new ArrayList(this.o);
            for (int i2 = 0; i2 < this.o; i2++) {
                this.p.add(new ArrayList());
            }
        }
        if (x || this.i == null) {
            return T3.a(str, z, (List) this.p.get(i), this.k.e, new O3$$ExternalSyntheticLambda0());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b() {
        throw new C3405y10("visitCode() should not be called when SKIP_CODE is set");
    }

    private void a(C0633d0 c0633d0) {
        if (this.m == null) {
            this.m = new ArrayList();
        }
        this.m.add(c0633d0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(String str, boolean z) {
        if (this.m == null) {
            this.m = new ArrayList();
        }
        return T3.a(str, z, this.m, this.k.e, new O3$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 c(int i, C00 c00, String str, boolean z) {
        if (this.m == null) {
            this.m = new ArrayList();
        }
        return T3.a(str, z, this.m, this.k.e, i, c00);
    }
}
