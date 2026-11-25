package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.Optional;

/* JADX INFO: renamed from: com.android.tools.r8.internal.p7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2756p7 implements InterfaceC3107u6 {
    private final H6 a;
    private final C0753u b;
    private final com.android.tools.r8.graph.o5 c;
    private final Optional d;

    C2756p7(C0702m c0702m, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var, Optional optional) {
        this.a = new H6(c0702m);
        this.b = c0753u;
        this.c = o5Var;
        this.d = optional;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3107u6
    public final int a() {
        return this.b.L0();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3107u6
    public final int b() {
        return this.b.K0();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3107u6
    public final H6 c() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3107u6
    public final boolean d() {
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3107u6
    public final C0684j2 e() {
        return (C0684j2) this.d.orElse(this.c.getReference());
    }

    @Override // com.android.tools.r8.internal.InterfaceC3107u6
    public final boolean a(C0767v2 c0767v2) {
        return this.d.isPresent() || c0767v2 == this.c.a().n1();
    }
}
