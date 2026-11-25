package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class R6 extends S6 {
    private final Set a;

    private R6() {
        this.a = OU.a();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2128h20
    public final void a(C0729q2 c0729q2, InterfaceC0694k5 interfaceC0694k5) {
    }

    @Override // com.android.tools.r8.internal.FQ
    public final void a(C0729q2 c0729q2, C0729q2 c0729q3) {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2128h20
    public final void b(C0729q2 c0729q2) {
        this.a.add(c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1126Ke
    public final void c(C0729q2 c0729q2) {
        this.a.add(c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2721og
    public final void d(C0729q2 c0729q2) {
        this.a.add(c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1445Ve
    public final void e(C0729q2 c0729q2) {
        this.a.add(c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1445Ve
    public final void f(C0729q2 c0729q2) {
        this.a.add(c0729q2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1471We
    public final void v(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.a.add(o5Var.a());
    }

    @Override // com.android.tools.r8.internal.HQ
    public final void a(C0729q2 c0729q2) {
        this.a.add(c0729q2);
    }

    @Override // com.android.tools.r8.internal.S6
    public final Set a() {
        return this.a;
    }
}
