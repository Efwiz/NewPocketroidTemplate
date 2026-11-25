package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.g8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2051g8 extends B7 {
    private final M7 b;
    private final AbstractC1915eM c;

    public C2051g8(M7 m7, AbstractC1915eM abstractC1915eM) {
        this.b = m7;
        this.c = abstractC1915eM;
    }

    public final M7 T() {
        return this.b;
    }

    public AbstractC1915eM U() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7;
    }

    @Override // com.android.tools.r8.internal.B7
    public final C2051g8 s() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean y() {
        return false;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return 210;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, final com.android.tools.r8.graph.C c) {
        return aVar.a(this, (C2051g8) b7, (com.android.tools.r8.utils.structural.y<C2051g8>) new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.g8$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C2051g8.a(c, zVar);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C2051g8 c2051g8) {
        return c2051g8.c.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.C c, com.android.tools.r8.utils.structural.z zVar) {
        com.android.tools.r8.utils.structural.z zVarA = zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.internal.g8$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return C2051g8.a((C2051g8) obj);
            }
        });
        Function function = new Function() { // from class: com.android.tools.r8.internal.g8$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C2051g8) obj).b;
            }
        };
        com.android.tools.r8.utils.structural.u uVarA = c.a();
        zVarA.a(function, uVarA, uVarA);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this.c.e());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.b(this.c.e(), this.b.T());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        AbstractC1915eM abstractC1915eMA = a8.a(this.c);
        n8.b(abstractC1915eMA);
        c2102gl.a(abstractC1915eMA);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
