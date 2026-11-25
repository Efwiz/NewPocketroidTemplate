package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ws, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1485Ws extends com.android.tools.r8.graph.J5 {
    static final /* synthetic */ boolean g = true;
    private EnumC1459Vs e;
    private C0684j2 f;

    public C1485Ws(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        super(c0702m, o5Var);
        this.e = EnumC1459Vs.f;
    }

    private void a(C0684j2 c0684j2, EnumC1459Vs enumC1459Vs) {
        if (this.e != EnumC1459Vs.f) {
            this.e = EnumC1459Vs.e;
            this.f = null;
        } else {
            if (!g && this.f != null) {
                throw new AssertionError();
            }
            this.f = c0684j2;
            this.e = enumC1459Vs;
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
        this.e = EnumC1459Vs.e;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        this.e = EnumC1459Vs.e;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
        this.e = EnumC1459Vs.e;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        this.e = EnumC1459Vs.e;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
        this.e = EnumC1459Vs.e;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        this.e = EnumC1459Vs.e;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
        a(c0684j2, EnumC1459Vs.b);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        this.e = EnumC1459Vs.e;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(C0767v2 c0767v2) {
        this.e = EnumC1459Vs.e;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        a(c0684j2, EnumC1459Vs.c);
    }

    public final EnumC1459Vs f() {
        return this.e;
    }

    public final C0684j2 g() {
        return this.f;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
        a(c0684j2, EnumC1459Vs.d);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        this.e = EnumC1459Vs.e;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        a(c0684j2, EnumC1459Vs.a);
    }
}
