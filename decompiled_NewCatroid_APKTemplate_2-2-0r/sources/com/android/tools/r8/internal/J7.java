package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class J7 extends B7 {
    private final C0721p0 b;

    public J7(C0721p0 c0721p0) {
        this.b = c0721p0;
    }

    public final C0721p0 T() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        return this.b.a(((J7) b7).b, aVar);
    }

    @Override // com.android.tools.r8.internal.B7
    public final J7 n() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean x() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return 186;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        this.b.a(oVar);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        C0721p0 c0721p0A = c2363kB.a(this.b, o5Var);
        C0699l2 c0699l2 = c0721p0A.f;
        List list = c0721p0A.g;
        Object[] objArr = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            objArr[i] = a((com.android.tools.r8.graph.B2) list.get(i), abstractC3649o0);
        }
        abstractC1899eF.a(abstractC3649o0.a(c0721p0A, c0702m).toString(), c0721p0A.e.a(abstractC3649o0), c0699l2.a(abstractC3649o0), objArr);
    }

    public static Object a(com.android.tools.r8.graph.B2 b2, AbstractC3649o0 abstractC3649o0) {
        switch (b2.N0().ordinal()) {
            case 3:
                return Integer.valueOf(b2.D0().k1());
            case 4:
                return Long.valueOf(b2.E0().k1());
            case 5:
                return Float.valueOf(b2.C0().k1());
            case 6:
                return Double.valueOf(b2.z0().k1());
            case 7:
                return CZ.c(((C0737r2) b2.H0().j1()).a(abstractC3649o0));
            case 8:
                return ((C0699l2) b2.G0().j1()).a(abstractC3649o0);
            case 9:
                C0759u2 c0759u2 = (C0759u2) b2.K0().j1();
                if (c0759u2 == null) {
                    return null;
                }
                return c0759u2.toString();
            case 10:
                return CZ.f(abstractC3649o0.c((C0767v2) b2.L0().b).toString());
            default:
                throw new C3405y10("Unsupported bootstrap argument of type " + b2.getClass().getSimpleName());
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    final void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        j5.a(this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        C0767v2[] c0767v2Arr = this.b.e.f.a;
        ArrayList arrayList = new ArrayList(c0767v2Arr.length);
        int length = c0767v2Arr.length;
        n8.getClass();
        int[] iArr = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            iArr[i] = n8.e().a;
        }
        for (int i2 = 0; i2 < length; i2++) {
            arrayList.add(Integer.valueOf(iArr[i2]));
        }
        ArrayList arrayList2 = new ArrayList(c0767v2Arr.length);
        for (C0767v2 c0767v2 : c0767v2Arr) {
            arrayList2.add(EnumC1812d20.a(c0767v2));
        }
        c2102gl.a(this.b, arrayList2, arrayList);
        if (this.b.e.e.Y0()) {
            return;
        }
        c2102gl.d(n8.a(this.b.e.e).a);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.c;
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        AbstractC2359k7 abstractC2359k7A = abstractC2359k7.a(c0702m, interfaceC3107u6, this.b.e.u0().a);
        C0767v2 c0767v2V0 = this.b.e.v0();
        return c0767v2V0.Y0() ? abstractC2359k7A : abstractC2359k7A.a(interfaceC3107u6, c0767v2V0);
    }
}
