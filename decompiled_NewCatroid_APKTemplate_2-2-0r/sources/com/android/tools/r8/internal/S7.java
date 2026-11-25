package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S7 extends B7 implements X8 {
    static final /* synthetic */ boolean d = true;
    private final C0767v2 b;
    private final int c;

    public S7(int i, C0767v2 c0767v2) {
        this.b = c0767v2;
        this.c = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.internal.S7$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((S7) obj).T();
            }
        }).c(new Function() { // from class: com.android.tools.r8.internal.S7$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((S7) obj).getType();
            }
        });
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean S() {
        return true;
    }

    public final int T() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.X8
    public final C0767v2 getType() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.B7
    public final X8 w() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean x() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return 197;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        C0767v2 c0767v2 = this.b;
        abstractC1476Wj.getClass();
        abstractC1899eF.a(this.c, abstractC3649o0.d(abstractC1476Wj.a(AbstractC1476Wj.e(), c0767v2)));
    }

    @Override // com.android.tools.r8.internal.X8
    public final B7 a(C0767v2 c0767v2) {
        return new S7(this.c, c0767v2);
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        return aVar.a(this, (S7) b7, new S7$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new S7$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    final void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        j5.f(this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        com.android.tools.r8.utils.A1 a1Y = c2102gl.o.Y();
        if (!d && a1Y.Y0()) {
            throw new AssertionError();
        }
        int i = this.c;
        n8.getClass();
        int[] iArr = new int[i];
        for (int i2 = i - 1; i2 >= 0; i2--) {
            iArr[i2] = n8.e().a;
        }
        C0767v2 c0767v2 = this.b;
        c2102gl.b(c0767v2, n8.a(c0767v2).a, iArr);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.e(this.b, o5Var);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        C0722p1 c0722p1A = c0702m.a();
        for (int i = 0; i < this.c; i++) {
            abstractC2359k7 = abstractC2359k7.a(c0702m, interfaceC3107u6, c0722p1A.A1);
        }
        return abstractC2359k7.a(interfaceC3107u6, this.b);
    }
}
