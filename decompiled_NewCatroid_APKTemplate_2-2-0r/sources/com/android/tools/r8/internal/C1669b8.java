package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.b8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1669b8 implements InterfaceC3107u6 {
    final /* synthetic */ com.android.tools.r8.graph.o5 a;
    final /* synthetic */ C0753u b;
    final /* synthetic */ C1892e8 c;

    C1669b8(C1892e8 c1892e8, com.android.tools.r8.graph.o5 o5Var, C0753u c0753u) {
        this.c = c1892e8;
        this.a = o5Var;
        this.b = c0753u;
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
        return this.c.b;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3107u6
    public final boolean d() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3107u6
    public final C0684j2 e() {
        return this.a.getReference();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3107u6
    public final boolean a(C0767v2 c0767v2) {
        return c0767v2 == this.a.a().n1();
    }
}
