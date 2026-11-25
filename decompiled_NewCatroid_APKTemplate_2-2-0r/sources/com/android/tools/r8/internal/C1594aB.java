package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1594aB implements InterfaceC1206Ne {
    private final boolean a;
    private final C1982fB b;
    private final C1747cB c;

    public C1594aB(C1982fB c1982fB, C1747cB c1747cB, boolean z) {
        this.a = z;
        this.b = c1982fB;
        this.c = c1747cB;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final Set a() {
        return AbstractC1048Hm.k();
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
    public boolean d() {
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

    public Map<C0767v2, C0767v2> g() {
        return this.c.b();
    }

    public final String h() {
        return this.b.c();
    }

    public final C1747cB i() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final boolean isEmpty() {
        return this.c.i();
    }

    public final C1982fB j() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final C2215iD a(AbstractC0681j0 abstractC0681j0, com.android.tools.r8.utils.y3 y3Var) {
        return new C1822dB(y3Var).a(this, abstractC0681j0).a(abstractC0681j0, y3Var);
    }
}
