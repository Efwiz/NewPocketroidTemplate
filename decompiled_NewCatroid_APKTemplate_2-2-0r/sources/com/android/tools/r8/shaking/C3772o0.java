package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.EnumC0608b0;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.utils.InterfaceC3880d;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.o0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3772o0 extends F0 {
    private C3772o0(C3777p0 c3777p0) {
        super(c3777p0.a, c3777p0.b);
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(AbstractC3709e0 abstractC3709e0) {
        a("EnqueuerAction " + abstractC3709e0);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    final C3772o0 b() {
        return this;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void c(C0684j2 c0684j2, o5 o5Var) {
        a("TraceInvokeStaticAction " + c0684j2 + " from " + o5Var);
        throw null;
    }

    private static void a(String str) {
        throw new C3405y10("Attempt to enqueue an action in a non pushable enqueuer work list (" + str + Constant.AFTER_QUTO);
    }

    @Override // com.android.tools.r8.shaking.F0
    final void b(C0729q2 c0729q2, L0 l0) {
        a("MarkInterfaceInstantiatedAction " + c0729q2);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void c(m5 m5Var, o5 o5Var) {
        a("TraceReflectiveFieldWriteAction " + m5Var + " from " + o5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(InterfaceC3880d interfaceC3880d) {
        interfaceC3880d.a();
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void b(C0684j2 c0684j2, o5 o5Var) {
        a("TraceInvokeDirectAction " + c0684j2 + " from " + o5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void c(C0729q2 c0729q2, L0 l0) {
        a("TraceTypeReferenceAction " + c0729q2);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(C0684j2 c0684j2, o5 o5Var, AbstractC3837z1 abstractC3837z1) {
        a("MarkReachableDirectAction " + c0684j2 + " from " + o5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void b(C0767v2 c0767v2, o5 o5Var) {
        a("TraceNewInstanceAction " + c0767v2 + " from " + o5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(C0684j2 c0684j2, o5 o5Var) {
        a("MarkReachableSuperAction " + c0684j2 + " from " + o5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void b(m5 m5Var, o5 o5Var) {
        a("TraceReflectiveFieldReadAction " + m5Var + " from " + o5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(m5 m5Var, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1) {
        a("MarkFieldAsReachableAction " + m5Var + " from " + interfaceC0694k5);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(C0729q2 c0729q2, o5 o5Var, V0 v0, AbstractC3837z1 abstractC3837z1) {
        a("MarkInstantiatedAction " + c0729q2 + " from " + o5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(C0729q2 c0729q2, L0 l0) {
        a("MarkAnnotationInstantiatedAction " + c0729q2);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    final boolean a(o5 o5Var, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1) {
        if (!this.a.a(abstractC3837z1, o5Var)) {
            return false;
        }
        a("MarkMethodLiveAction " + o5Var + " from " + interfaceC0694k5);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(AbstractC3837z1 abstractC3837z1, o5 o5Var) {
        a("MarkMethodKeptAction " + o5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(L0 l0, m5 m5Var) {
        a("MarkFieldKeptAction " + m5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    final void a(InterfaceC0694k5 interfaceC0694k5, C0633d0 c0633d0, EnumC0608b0 enumC0608b0) {
        a("TraceAnnotationAction " + c0633d0 + " from " + interfaceC0694k5);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(o5 o5Var) {
        a("TraceCodeAction " + o5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(C0767v2 c0767v2, o5 o5Var) {
        a("TraceConstClassAction " + c0767v2 + " from " + o5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(C0729q2 c0729q2) {
        a("TraceDirectAndIndirectClassInitializers " + c0729q2);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(m5 m5Var, o5 o5Var) {
        a("TraceReflectiveFieldAccessAction " + m5Var + " from " + o5Var);
        throw null;
    }

    @Override // com.android.tools.r8.shaking.F0
    public final void a(com.android.tools.r8.graph.Y0 y0, o5 o5Var) {
        a("TraceStaticFieldRead " + y0 + " from " + o5Var);
        throw null;
    }
}
