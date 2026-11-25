package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collections;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2441l8 extends AbstractC2591n8 {
    static final /* synthetic */ boolean d = true;
    private final C0825Ad a;
    private final CN b;
    private final D7 c;

    private C2441l8(C0825Ad c0825Ad, D7 d7) {
        this.b = CN.c();
        this.a = c0825Ad;
        this.c = d7;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1153Le
    public final void a(C0729q2 c0729q2, AbstractC0727q0 abstractC0727q0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2262is
    public final void a(C0729q2 c0729q2, C0754u0 c0754u0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1098Je
    public final void a(C0754u0 c0754u0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void b(C0754u0 c0754u0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void c(C0754u0 c0754u0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1398Ue
    public final void e(C0754u0 c0754u0) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1098Je
    public final void m(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        a(o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1471We
    public final void v(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        a(o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2591n8
    public final Set b() {
        return Collections.emptySet();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1344Se
    public final void b(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        a(o5Var);
    }

    private void a(com.android.tools.r8.graph.o5 o5Var) {
        boolean z = d;
        if (!z && this.c.a(o5Var)) {
            throw new AssertionError();
        }
        if (!z && !o5Var.e().a1().z0()) {
            throw new AssertionError();
        }
        this.b.add(o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2262is
    public final void a(C0729q2 c0729q2, C0767v2 c0767v2, C1566Zr c1566Zr) {
        c1566Zr.a(c0729q2, c0729q2, c0767v2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1153Le
    public final void a(com.android.tools.r8.graph.o5 o5Var, C2404kg c2404kg) {
        a(o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2262is
    public final void a(AbstractC0748t0 abstractC0748t0, com.android.tools.r8.graph.o5 o5Var) {
        a(o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2262is
    public final void a(com.android.tools.r8.graph.o5 o5Var, C0767v2 c0767v2, com.android.tools.r8.graph.I4.a aVar) {
        a(o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2591n8
    public final void a() {
        if (!d) {
            this.a.g();
        }
        this.a.f();
        this.a.a(this.b);
        this.a.d();
    }
}
