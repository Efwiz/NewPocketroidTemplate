package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3822x;
import com.android.tools.r8.shaking.InterfaceC3697c0;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3208vP extends C3822x {
    private static final InterfaceC3697c0 m = new InterfaceC3697c0() { // from class: com.android.tools.r8.internal.vP$$ExternalSyntheticLambda0
        @Override // com.android.tools.r8.shaking.InterfaceC3697c0
        public final C3822x a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.shaking.L l, com.android.tools.r8.androidapi.b bVar) {
            return new C3208vP(c0702m, o5Var, l, bVar);
        }
    };
    private final VP l;

    public C3208vP(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.shaking.L l, com.android.tools.r8.androidapi.b bVar) {
        super(c0702m, o5Var, l, bVar);
        this.l = c0702m.Z().h;
    }

    public static InterfaceC3697c0 g() {
        return m;
    }

    @Override // com.android.tools.r8.shaking.C3822x, com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void a(C0767v2 c0767v2, ListIterator listIterator, boolean z) {
        if (this.l.a(((com.android.tools.r8.graph.o5) d()).e())) {
            this.k.a(c0767v2);
        } else {
            super.a(c0767v2, listIterator, z);
        }
    }

    @Override // com.android.tools.r8.shaking.C3822x, com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        C0729q2 c0729q2A;
        if (this.l.a(((com.android.tools.r8.graph.o5) d()).e()) && y0.B0() != ((com.android.tools.r8.graph.o5) d()).a().getType() && (y0 == this.l.a(((com.android.tools.r8.graph.o5) d()).a()) || ((c0729q2A = C0729q2.a(this.j.c(y0.B0()))) != null && c0729q2A.f1().a(this.l.d) && y0 == this.l.b(c0729q2A)))) {
            this.k.a(y0.B0());
        } else {
            super.e(y0);
        }
    }
}
