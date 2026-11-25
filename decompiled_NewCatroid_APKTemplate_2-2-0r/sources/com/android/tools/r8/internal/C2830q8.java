package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ArrayList;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.q8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2830q8 extends B7 {
    private final com.android.tools.r8.graph.Y0[] b;

    public C2830q8(com.android.tools.r8.graph.Y0[] y0Arr) {
        this.b = y0Arr;
    }

    public final com.android.tools.r8.graph.Y0[] T() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.B7
    public final C2830q8 t() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return 211;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.d(new Function() { // from class: com.android.tools.r8.internal.q8$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C2830q8) obj).b;
            }
        });
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        return aVar.a(this, b7.t(), new q8$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new q8$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        int length = this.b.length;
        int[] iArr = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            iArr[i] = n8.e().a;
        }
        com.android.tools.r8.graph.Y0[] y0Arr = this.b;
        C2732or c2732orB = C2732or.b(iArr);
        int i2 = n8.a(c2102gl.o.a().c2).a;
        ArrayList arrayList = new ArrayList();
        InterfaceC0841Ar interfaceC0841ArListIterator = c2732orB.listIterator(0);
        while (interfaceC0841ArListIterator.hasNext()) {
            arrayList.add(c2102gl.b(Integer.valueOf(((K) interfaceC0841ArListIterator).t()).intValue(), EnumC1968f20.OBJECT));
        }
        c2102gl.a(new LQ(y0Arr, c2102gl.a(i2, AbstractC3472z00.a(c2102gl.o.a().c2, WH.b(), (C0702m<?>) c2102gl.o), 2), arrayList));
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        C0722p1 c0722p1A = c0702m.a();
        for (com.android.tools.r8.graph.Y0 y0 : this.b) {
            abstractC2359k7 = abstractC2359k7.a(c0702m, interfaceC3107u6, c0722p1A.Z1);
        }
        return abstractC2359k7.a(interfaceC3107u6, c0722p1A.c2);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
