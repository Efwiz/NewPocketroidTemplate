package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.List;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1133Kk implements InterfaceC1206Ne {
    private final boolean a;
    private final C1533Yk b;
    private final C1297Qk c;

    public C1133Kk(C1533Yk c1533Yk, C1297Qk c1297Qk, boolean z) {
        this.a = z;
        this.b = c1533Yk;
        this.c = c1297Qk;
    }

    public static C1133Kk g() {
        return new C1133Kk(new C1533Yk(EnumC3891f.B, "unused", null, null, true, AbstractC1639am.i()), new C1297Qk(AbstractC2178hm.s(), AbstractC1048Hm.k(), AbstractC1048Hm.k(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC1048Hm.k(), AbstractC1048Hm.k(), AbstractC2178hm.s(), AbstractC1048Hm.k(), AbstractC2178hm.s(), AbstractC2178hm.s()), false);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final Set a() {
        return this.c.k();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final String b() {
        return this.b.d();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final EnumC3891f c() {
        return this.b.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final boolean d() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final List e() {
        return this.b.b();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final String f() {
        return this.b.f();
    }

    public final String h() {
        return this.b.c();
    }

    public final C1297Qk i() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final boolean isEmpty() {
        return this.c.u();
    }

    public final C1533Yk j() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public C2215iD a(AbstractC0681j0 abstractC0681j0, com.android.tools.r8.utils.y3 y3Var) {
        return new C1425Uk(y3Var).a(this, abstractC0681j0);
    }
}
