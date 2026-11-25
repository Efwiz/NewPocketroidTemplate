package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import java.util.ListIterator;
import java.util.function.BiFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.z7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3482z7 extends AbstractC2206i7 {
    public C3482z7(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        super(y0, y1);
    }

    @Override // com.android.tools.r8.internal.AbstractC2206i7
    public final int U() {
        return 181;
    }

    @Override // com.android.tools.r8.internal.AbstractC2206i7
    public final AbstractC2206i7 a(com.android.tools.r8.graph.Y0 y0) {
        return new C3482z7(y0, y0);
    }

    @Override // com.android.tools.r8.internal.B7
    public final C3482z7 j() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    final void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        j5.c(getField());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        c2102gl.b(n8.e().a, n8.e().a, getField());
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.c(getField(), o5Var);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7.a(c0702m, interfaceC3107u6, getField().getType()).b(c0702m, getField().B0(), interfaceC3107u6, new BiFunction() { // from class: com.android.tools.r8.internal.z7$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a((AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        return interfaceC3054tM.y() ? a(interfaceC3054tM) : abstractC2359k7;
    }

    private C2325jh a(InterfaceC3054tM interfaceC3054tM) {
        return AbstractC2359k7.a("Frame type " + C2325jh.c(interfaceC3054tM) + " is not assignable to " + getField().B0().L0());
    }
}
