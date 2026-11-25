package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.C1031Ha;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C2145hI;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.internal.InterfaceC0992Fq;
import com.android.tools.r8.internal.Z00;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.shaking.C3718f3;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Z3 extends W {
    static final /* synthetic */ boolean i = true;
    private final Origin d;
    private M3 e;
    private C0753u f;
    private S3 g;
    private boolean h = false;

    public Z3(Origin origin, S3 s3, M3 m3) {
        this.d = origin;
        this.g = s3;
        this.e = m3;
        s3.c.add(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void H0() {
        S3 s3 = this.g;
        M3 m3 = this.e;
        boolean z = i;
        if (!z && m3 == null) {
            throw new AssertionError();
        }
        if (!z && s3 == null) {
            throw new AssertionError();
        }
        try {
            a(s3, false);
        } catch (X3 unused) {
            for (W w : s3.c) {
                w.v0().f = null;
                w.v0().g = s3;
                w.v0().e = m3;
            }
            try {
                a(s3, true);
            } catch (X3 e) {
                throw new C3405y10(e);
            }
        } catch (Exception e2) {
            throw new C1367Tb("Could not parse code", e2, this.d, Position.UNKNOWN);
        }
        if (i) {
            return;
        }
        AbstractC0727q0 abstractC0727q0 = s3.b;
        Iterator<W0> it = abstractC0727q0.P1().iterator();
        while (it.hasNext()) {
            W wA1 = it.next().a1();
            if (!i && wA1 != null) {
                wA1.G0();
            }
        }
        Iterator<W0> it2 = abstractC0727q0.V0().iterator();
        while (it2.hasNext()) {
            W wA2 = it2.next().a1();
            if (!i && wA2 != null) {
                wA2.G0();
            }
        }
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean A0() {
        return true;
    }

    @Override // com.android.tools.r8.graph.W
    public final void G0() {
        if (i) {
            return;
        }
        if (this.g != null || this.e != null) {
            throw new AssertionError();
        }
    }

    public final void I0() {
        if (!i && this.f != null) {
            throw new AssertionError();
        }
        this.h = true;
    }

    @Override // com.android.tools.r8.graph.W
    public final C0753u K() {
        if (this.f == null) {
            com.android.tools.r8.utils.R0.a(this.d, Position.UNKNOWN, new Runnable() { // from class: com.android.tools.r8.graph.Z3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.H0();
                }
            });
        }
        if (i || this.f != null) {
            return this.f;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.W
    public final W a(C0684j2 c0684j2, W0 w0, C0722p1 c0722p1) {
        C0753u c0753uK = K();
        c0753uK.getClass();
        return c0753uK.a(c0722p1, c0684j2, w0.getReference(), w0.N0());
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final boolean c(Object obj) {
        throw new Z00();
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean k(int i2) {
        return K().k(i2);
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final int s0() {
        throw new Z00();
    }

    @Override // com.android.tools.r8.graph.W
    public final D t0() {
        return K();
    }

    @Override // com.android.tools.r8.graph.W
    public final String toString() {
        return K().toString();
    }

    @Override // com.android.tools.r8.graph.W
    public final Z3 v0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W
    public final int w0() {
        return K().w0();
    }

    @Override // com.android.tools.r8.graph.W
    public final int x0() {
        return K().x0();
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean y0() {
        return K().y0();
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean z0() {
        return true;
    }

    public final void a(S3 s3, boolean z) {
        V3 v3;
        M3 m3 = this.e;
        boolean z2 = this.h;
        int i2 = m3.a.N() ? 8 : 4;
        com.android.tools.r8.shaking.L2 l2D0 = m3.a.D0();
        boolean z3 = true;
        if (l2D0 == null) {
            v3 = new V3(true, true, i2);
        } else {
            C3718f3 c3718f3H = m3.a.D0().h();
            if (!l2D0.D() && !c3718f3H.h && !c3718f3H.i && !z2) {
                z3 = false;
            }
            boolean z4 = c3718f3H.g;
            boolean z5 = c3718f3H.j;
            if (!z3 && !z4 && !z5) {
                i2 |= 2;
            }
            v3 = new V3(z4, z3, i2);
        }
        new C1031Ha(s3.a).a(v3.c, new U3(s3.b, new W3(s3), this.e, z, this.d, v3));
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar) {
        return K().a(o5Var, c0702m, origin, aVar);
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, o5 o5Var2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, C2145hI c2145hI, AbstractC1915eM abstractC1915eM, Origin origin, com.android.tools.r8.graph.proto.k kVar) {
        return K().a(o5Var, o5Var2, c0702m, abstractC1476Wj, c2145hI, abstractC1915eM, origin, kVar);
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(o5 o5Var, J5 j5) {
        K().a(o5Var, j5);
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(S s, J5 j5) {
        K().a(s, j5);
    }

    @Override // com.android.tools.r8.graph.W
    public final String a(W0 w0, com.android.tools.r8.utils.P2 p2) {
        return K().a(w0, p2);
    }

    @Override // com.android.tools.r8.graph.W
    public final InterfaceC0992Fq a(C0702m c0702m, W0 w0) {
        return K().a(c0702m, w0);
    }
}
