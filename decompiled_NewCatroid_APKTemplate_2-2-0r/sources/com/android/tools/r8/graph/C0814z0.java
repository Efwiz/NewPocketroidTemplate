package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.internal.C3017sq;
import com.android.tools.r8.internal.EnumC1812d20;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.graph.z0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0814z0 implements I0 {
    static final /* synthetic */ boolean i = true;
    private boolean a = false;
    private boolean b = false;
    private final HashMap c = new HashMap();
    private final C3017sq d = new C3017sq();
    private C0797x0 e = null;
    private final HashMap f = new HashMap();
    private ArrayList g = new ArrayList();
    private final N0 h;

    public C0814z0(int i2, C0684j2 c0684j2) {
        if (!i && c0684j2 == null) {
            throw new AssertionError();
        }
        this.h = new N0(i2, c0684j2);
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(B0.a aVar) {
        this.h.a(aVar);
        a(false);
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(A0 a0) {
        this.h.a(a0);
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(F0 f0) {
        this.h.a(f0);
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(B0.b bVar) {
        this.h.a(bVar);
        a(true);
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(B0.c cVar) {
        this.h.a(cVar);
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(G0 g0) {
        this.a = true;
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(E0 e0) {
        this.b = true;
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(B0.d dVar) {
        int i2 = dVar.c;
        C0806y0 c0806y0 = (C0806y0) this.c.get(Integer.valueOf(i2));
        if (c0806y0 == null) {
            c0806y0 = new C0806y0();
            this.c.put(Integer.valueOf(i2), c0806y0);
        }
        X x = new X(dVar.d, dVar.f, dVar.e);
        X x2 = (X) this.f.putIfAbsent(x, x);
        if (x2 != null) {
            x = x2;
        }
        c0806y0.a = x;
        c0806y0.b = x;
    }

    public C0814z0(W0 w0, C0722p1 c0722p1) {
        C0759u2 c0759u2;
        boolean z = i;
        if (!z && (w0 == null || w0.getReference() == null)) {
            throw new AssertionError();
        }
        C0772w0 c0772w0P = w0.a1().P();
        J0.a aVarT0 = c0772w0P.H0().t0();
        if (!z && aVarT0 == null) {
            throw new AssertionError();
        }
        this.h = new N0(aVarT0.e, w0.getReference());
        int iC = c0772w0P.d - c0772w0P.e;
        if (!w0.f.o()) {
            C0759u2 c0759u3 = c0722p1.c1;
            C0767v2 c0767v2J0 = w0.J0();
            a(iC, c0759u3, c0767v2J0);
            iC += EnumC1812d20.a(c0767v2J0).c();
        }
        C0767v2[] c0767v2Arr = w0.getReference().h.f.a;
        C0759u2[] c0759u2Arr = aVarT0.f;
        for (int i2 = 0; i2 < c0767v2Arr.length; i2++) {
            if (i2 < c0759u2Arr.length && (c0759u2 = c0759u2Arr[i2]) != null) {
                a(iC, c0759u2, c0767v2Arr[i2]);
            }
            iC += EnumC1812d20.a(c0767v2Arr[i2]).c();
        }
        for (B0 b0 : aVarT0.g) {
            b0.a(this);
        }
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(C0 c0) {
        int i2 = c0.c;
        C0806y0 c0806y0 = (C0806y0) this.c.get(Integer.valueOf(i2));
        if (c0806y0 == null) {
            c0806y0 = new C0806y0();
            this.c.put(Integer.valueOf(i2), c0806y0);
        }
        c0806y0.a = null;
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(D0 d0) {
        int i2 = d0.c;
        C0806y0 c0806y0 = (C0806y0) this.c.get(Integer.valueOf(i2));
        if (c0806y0 == null) {
            c0806y0 = new C0806y0();
            this.c.put(Integer.valueOf(i2), c0806y0);
        }
        c0806y0.a = c0806y0.b;
    }

    private void a(boolean z) {
        if (this.e != null) {
            ArrayList arrayList = this.g;
            C0797x0 c0797x0 = this.e;
            boolean z2 = c0797x0.a;
            int i2 = c0797x0.b;
            C0759u2 c0759u2 = c0797x0.c;
            boolean z3 = c0797x0.d;
            boolean z4 = c0797x0.e;
            C1946em c1946emE = AbstractC2178hm.e();
            for (Map.Entry entry : this.c.entrySet()) {
                Integer num = (Integer) entry.getKey();
                X x = ((C0806y0) entry.getValue()).a;
                if (x != null) {
                    c1946emE.a(num, x);
                }
            }
            arrayList.add(new C0797x0(z2, i2, c0759u2, z3, z4, c1946emE.a(), this.e.b()));
        }
        this.e = new C0797x0(z, this.h.b(), null, this.a, this.b, null, this.h.c());
        this.a = false;
        this.b = false;
    }

    public List<C0797x0> a() {
        if (this.e != null) {
            a(false);
            this.e = null;
        }
        ArrayList arrayList = this.g;
        this.g = null;
        return arrayList;
    }

    public final void a(int i2, C0759u2 c0759u2, C0767v2 c0767v2) {
        X x = new X(c0759u2, null, c0767v2);
        X x2 = (X) this.f.putIfAbsent(x, x);
        if (x2 != null) {
            x = x2;
        }
        this.d.a(i2, x);
        C0806y0 c0806y0 = (C0806y0) this.c.get(Integer.valueOf(i2));
        if (c0806y0 == null) {
            c0806y0 = new C0806y0();
            this.c.put(Integer.valueOf(i2), c0806y0);
        }
        c0806y0.a = x;
        c0806y0.b = x;
    }
}
