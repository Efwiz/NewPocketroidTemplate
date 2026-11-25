package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.EnumC3891f;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Pj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1268Pj implements InterfaceC3007sg {
    private final C0722p1 a;
    private final com.android.tools.r8.shaking.L b;
    private final com.android.tools.r8.androidapi.b c;

    public C1268Pj(C0702m c0702m, com.android.tools.r8.shaking.L l) {
        this.a = c0702m.a();
        this.b = l;
        this.c = c0702m.d();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3007sg
    public final void a() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3007sg
    public final void b() {
    }

    public static void a(C0702m c0702m, com.android.tools.r8.shaking.L l) {
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        if (a1Y.Y0() && a1Y.B0().a(EnumC3891f.L)) {
            return;
        }
        l.a(new C1268Pj(c0702m, l));
    }

    @Override // com.android.tools.r8.internal.InterfaceC3007sg
    public final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.o5 o5Var) {
        C0767v2 type = y0.getType();
        if (type.M0()) {
            C0767v2 c0767v2B = type.b(this.a);
            if (c0767v2B.O0()) {
                com.android.tools.r8.androidapi.h hVarA = this.c.a(c0767v2B, com.android.tools.r8.androidapi.h.f());
                if (!hVarA.y() || hVarA.b0().a().a(EnumC3891f.L)) {
                    this.b.h().a(o5Var, new Pj$$ExternalSyntheticLambda0());
                }
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3007sg
    public final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.shaking.F0 f0) {
        C0767v2 type = y0.getType();
        if (type.M0()) {
            C0767v2 c0767v2B = type.b(this.a);
            if (c0767v2B.O0()) {
                com.android.tools.r8.androidapi.h hVarA = this.c.a(c0767v2B, com.android.tools.r8.androidapi.h.f());
                if (!hVarA.y() || hVarA.b0().a().a(EnumC3891f.L)) {
                    this.b.h().a(o5Var, new Pj$$ExternalSyntheticLambda0());
                }
            }
        }
    }
}
