package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2252ii;
import com.android.tools.r8.internal.AbstractC2509m2;
import com.android.tools.r8.internal.C00;
import com.android.tools.r8.internal.C2044g20;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.EQ;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class P3 extends AbstractC2252ii {
    static final /* synthetic */ boolean j = true;
    private final O3 c;
    private final int d;
    private final String e;
    private final String f;
    private final Object g;
    private final C0706m3.e h;
    private ArrayList i;

    public P3(O3 o3, int i, String str, String str2, String str3, Object obj) {
        super(589824);
        this.i = null;
        this.c = o3;
        this.d = i;
        this.e = str;
        this.f = str2;
        this.g = obj;
        this.h = o3.e.a.u1() ? C0706m3.b(str, str3, o3.c, o3.e.a(), o3.e.a.e) : C0706m3.e.p();
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final void a() {
        B2 b2A;
        Q2 q2G = Q2.g(T3.a(this.d));
        O3 o3 = this.c;
        Y0 y0A = o3.e.a(o3.j, this.e, this.f);
        O3 o4 = this.c;
        M3 m3 = o4.e;
        I i = o4.d;
        if (m3.a.C1() && EQ.a(y0A, m3.a.a)) {
            m3.b(y0A.B0(), i);
        }
        O3 o5 = this.c;
        M3 m4 = o5.e;
        I i2 = o5.d;
        if (m4.a.D1()) {
            C0722p1 c0722p1 = m4.a.a;
            if (C2044g20.a(c0722p1, y0A.e) ? true : C2044g20.a(c0722p1, y0A.h)) {
                m4.a(y0A.B0(), i2);
            }
        }
        O3 o6 = this.c;
        M3 m5 = o6.e;
        I i3 = o6.d;
        if (m5.a.D1() && C2044g20.a(y0A, m5.a.a)) {
            m5.c(y0A.B0(), i3);
        }
        if (!this.c.z.add(com.android.tools.r8.utils.W0.a().c(y0A))) {
            this.c.e.a.e.warning(new StringDiagnostic(String.format("Field `%s` has multiple definitions", y0A.r0())));
            return;
        }
        C0658g0 c0658g0A = T3.a(this.i, this.c.e.a);
        if (q2G.o()) {
            b2A = a(y0A.h, this.g);
        } else {
            b2A = null;
        }
        T0 t0A = T0.Q0().a(y0A).a(q2G).a(this.h).a(c0658g0A).a(b2A).b(com.android.tools.r8.utils.C.a(this.d)).d().a();
        if (q2G.o()) {
            this.c.x.add(t0A);
        } else {
            this.c.y.add(t0A);
        }
    }

    private B2 a(C0767v2 c0767v2, Object obj) {
        if (obj == null) {
            return null;
        }
        C0722p1 c0722p1A = this.c.e.a();
        if (c0767v2 == c0722p1A.v1) {
            int iIntValue = ((Integer) obj).intValue();
            if (j || (iIntValue >= 0 && iIntValue <= 1)) {
                return B2.b.a(iIntValue == 1);
            }
            throw new AssertionError();
        }
        if (c0767v2 == c0722p1A.w1) {
            return B2.c.a(((Integer) obj).byteValue());
        }
        if (c0767v2 == c0722p1A.C1) {
            return B2.i.a(((Integer) obj).shortValue());
        }
        if (c0767v2 == c0722p1A.x1) {
            return B2.d.a((char) ((Integer) obj).intValue());
        }
        if (c0767v2 == c0722p1A.A1) {
            return B2.g.j(((Integer) obj).intValue());
        }
        if (c0767v2 == c0722p1A.z1) {
            return B2.f.a(((Float) obj).floatValue());
        }
        if (c0767v2 == c0722p1A.B1) {
            return B2.h.a(((Long) obj).longValue());
        }
        if (c0767v2 == c0722p1A.y1) {
            return B2.e.a(((Double) obj).doubleValue());
        }
        if (c0767v2 == c0722p1A.X1) {
            return new B2.j(c0722p1A.c((String) obj));
        }
        throw new C3405y10("Unexpected static-value type " + c0767v2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final AbstractC2509m2 a(String str, boolean z) {
        if (this.i == null) {
            this.i = new ArrayList();
        }
        return T3.a(str, z, this.i, this.c.e, new O3$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        if (this.i == null) {
            this.i = new ArrayList();
        }
        return T3.a(str, z, this.i, this.c.e, i, c00);
    }
}
