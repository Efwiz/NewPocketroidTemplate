package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.u4;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.G3;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.d0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3550d0 {
    static final /* synthetic */ boolean d = true;
    private C0702m a;
    private boolean b = true;
    private AbstractC1476Wj c;

    public C3550d0(C0702m c0702m, AbstractC1476Wj abstractC1476Wj) {
        this.a = c0702m;
        this.c = abstractC1476Wj;
    }

    public final void a() {
        this.b = false;
    }

    public final C0702m b() {
        return this.a;
    }

    public final AbstractC1476Wj c() {
        return this.c;
    }

    public final V d(C0767v2 c0767v2, o5 o5Var) {
        return V.a(this.a, c0767v2, o5Var);
    }

    public final V e(C0767v2 c0767v2, o5 o5Var) {
        return V.a(this.a, c0767v2, o5Var);
    }

    public final V f(C0767v2 c0767v2, o5 o5Var) {
        return V.a(this.a, c0767v2, o5Var);
    }

    public final V g(C0767v2 c0767v2, o5 o5Var) {
        return V.a(this.a, c0767v2, o5Var);
    }

    public final V h(C0767v2 c0767v2, o5 o5Var) {
        return V.a(this.a, c0767v2, o5Var);
    }

    public final V a(C0767v2 c0767v2, o5 o5Var) {
        return V.a(this.a, c0767v2, o5Var);
    }

    public final V b(C0767v2 c0767v2, o5 o5Var) {
        return V.a(this.a, c0767v2, o5Var);
    }

    public final V c(C0767v2 c0767v2, o5 o5Var) {
        return V.a(this.a, c0767v2, o5Var);
    }

    public final V d(C0684j2 c0684j2, o5 o5Var) {
        C0684j2 c0684j3 = (C0684j2) this.c.a(c0684j2, o5Var.getReference(), EnumC1953et.g, (AbstractC1476Wj) null).b();
        if (c0684j3.e.M0()) {
            return V.d;
        }
        I4 i4B = ((C3733i) this.a.f()).b(c0684j3, false);
        return !i4B.y() ? V.c : a(i4B.a(), o5Var, i4B.t());
    }

    public final V e(Y0 y0, o5 o5Var) {
        return a(y0, o5Var);
    }

    public final V a(C0684j2 c0684j2, o5 o5Var, EnumC1953et enumC1953et) {
        int iOrdinal = enumC1953et.ordinal();
        if (iOrdinal == 0) {
            return a(c0684j2, o5Var);
        }
        if (iOrdinal == 1) {
            return b(c0684j2, o5Var);
        }
        if (iOrdinal == 2) {
            return c(c0684j2, o5Var);
        }
        if (iOrdinal == 3) {
            return a(o5Var);
        }
        if (iOrdinal == 4) {
            return d(c0684j2, o5Var);
        }
        if (iOrdinal == 7) {
            return V.c;
        }
        if (iOrdinal != 8) {
            throw new C3405y10("Unexpected type: " + enumC1953et);
        }
        return V.c;
    }

    public final V b(Y0 y0, o5 o5Var) {
        return a(y0, o5Var);
    }

    public final V c(Y0 y0, o5 o5Var) {
        return a(y0, o5Var);
    }

    public final V b(C0684j2 c0684j2, o5 o5Var) {
        C0684j2 c0684j3 = (C0684j2) this.c.a(c0684j2, o5Var.getReference(), EnumC1953et.d, (AbstractC1476Wj) null).b();
        if (c0684j3.e.M0()) {
            return V.d;
        }
        I4 i4B = ((C3733i) this.a.f()).b(c0684j3, true);
        if (!i4B.y()) {
            return V.c;
        }
        return a(i4B.a(), o5Var, i4B.t());
    }

    public final V c(C0684j2 c0684j2, o5 o5Var) {
        C0684j2 c0684j3 = (C0684j2) this.c.a(c0684j2, o5Var.getReference(), EnumC1953et.e, (AbstractC1476Wj) null).b();
        if (c0684j3.e.M0()) {
            return V.d;
        }
        I4 i4H = ((C3733i) this.a.f()).h(c0684j3);
        W0 w0A = a(i4H, o5Var, new G3() { // from class: com.android.tools.r8.ir.optimize.d0$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return ((I4) obj).c((C0729q2) obj2, (C0702m) obj3);
            }
        });
        if (!this.b && w0A != null) {
            C0702m c0702m = this.a;
            AbstractC1476Wj abstractC1476Wj = this.c;
            C0767v2 c0767v2J0 = w0A.J0();
            abstractC1476Wj.getClass();
            AbstractC0727q0 abstractC0727q0C = c0702m.c(abstractC1476Wj.a(AbstractC1476Wj.e(), c0767v2J0));
            if (abstractC0727q0C != null && abstractC0727q0C.isInterface() && w0A.q1()) {
                return V.c;
            }
        }
        return a(i4H.a(), o5Var, w0A);
    }

    public final V d(Y0 y0, o5 o5Var) {
        return a(y0, o5Var);
    }

    public final V a(C0684j2 c0684j2, o5 o5Var) {
        C0684j2 c0684j3 = (C0684j2) this.c.a(c0684j2, o5Var.getReference(), EnumC1953et.c, (AbstractC1476Wj) null).b();
        if (c0684j3.e.M0()) {
            return V.d;
        }
        I4 i4H = ((C3733i) this.a.f()).h(c0684j3);
        return a(i4H.a(), o5Var, a(i4H, o5Var, new G3() { // from class: com.android.tools.r8.ir.optimize.d0$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return ((I4) obj).a((C0729q2) obj2, (C0702m) obj3);
            }
        }));
    }

    private W0 a(I4 i4, o5 o5Var, G3 g3) {
        W0 w0;
        if (!i4.x()) {
            return null;
        }
        W0 w1 = (W0) g3.a(i4, o5Var.a(), this.a);
        AbstractC1476Wj abstractC1476Wj = this.c;
        boolean z = abstractC1476Wj instanceof u4;
        if (!z || w1 != null) {
            return w1;
        }
        boolean z2 = d;
        if (!z2 && !z) {
            throw new AssertionError();
        }
        if (!z2 && AbstractC2964s2.a(abstractC1476Wj, o5Var.a().f) != o5Var.t()) {
            throw new AssertionError();
        }
        C0702m c0702m = this.a;
        C0767v2 c0767v2 = o5Var.a().f;
        c0702m.getClass();
        C0729q2 c0729q2A = c0702m.a(o5Var.a(), c0767v2);
        if (c0729q2A == null || (w0 = (W0) g3.a(i4, c0729q2A, this.a)) == null || w0.J0() != c0729q2A.d) {
            return null;
        }
        return w0;
    }

    public static V a(o5 o5Var) {
        return new V(U.SAMECLASS, o5Var.t());
    }

    private V a(Y0 y0, o5 o5Var) {
        AbstractC0677i3 abstractC0677i3C = ((C3733i) this.a.f()).c(this.c.c((AbstractC1476Wj) null, y0));
        if (abstractC0677i3C.z()) {
            return V.c;
        }
        return a(abstractC0677i3C.a(), o5Var, abstractC0677i3C.s());
    }

    private V a(AbstractC0727q0 abstractC0727q0, o5 o5Var, com.android.tools.r8.graph.U0 u0) {
        V v;
        if (u0 == null) {
            return V.c;
        }
        C0702m c0702m = this.a;
        C1121Ka c1121KaP = ((C0680j) c0702m.f()).p();
        C0767v2 c0767v2J0 = u0.J0();
        c1121KaP.getClass();
        A1 a1Y = c0702m.Y();
        AbstractC3135uW abstractC3135uWC = c0702m.C();
        com.android.tools.r8.synthesis.D D = c0702m.D();
        FeatureSplit featureSplitA = c1121KaP.a(c0767v2J0, a1Y, abstractC3135uWC, D);
        if (featureSplitA.isBase() || featureSplitA == c1121KaP.a(o5Var.C(), a1Y, abstractC3135uWC, D)) {
            v = V.d;
        } else {
            v = V.c;
        }
        boolean z = d;
        if (!z && v != V.d && v != V.c) {
            throw new AssertionError();
        }
        if (v == V.c) {
            return v;
        }
        C0767v2 c0767v2A = AbstractC2964s2.a(this.c, u0.J0());
        if (!z && abstractC0727q0 == null) {
            throw new AssertionError();
        }
        return V.a(V.a(o5Var, abstractC0727q0.d, abstractC0727q0.e, this.a), V.a(o5Var, c0767v2A, u0.getAccessFlags(), this.a), (C0702m<?>) this.a);
    }
}
