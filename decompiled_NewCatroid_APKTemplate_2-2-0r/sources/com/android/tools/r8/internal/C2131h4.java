package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.h4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2131h4 {
    private final C0722p1 a;
    private final C0767v2 b;
    private final com.android.tools.r8.graph.Y0 c;
    private final C0684j2 d;
    private final C0684j2 e;

    C2131h4(C0702m c0702m, com.android.tools.r8.synthesis.P p) {
        C0722p1 c0722p1A = c0702m.a();
        this.a = c0722p1A;
        C0767v2 c0767v2E = p.e();
        this.b = c0767v2E;
        this.c = c0722p1A.a(p.e(), c0722p1A.R2, c0722p1A.c("initialValueSupplier"));
        this.d = c0722p1A.a(c0767v2E, c0722p1A.b(c0722p1A.D1, c0722p1A.R2), c0722p1A.Y0);
        this.e = c0722p1A.a(c0767v2E, c0722p1A.b(c0722p1A.Z1, new C0767v2[0]), c0722p1A.c("initialValue"));
        p.a(c0722p1A.a3);
        b(p);
        a(p);
        c(p);
    }

    private void a(com.android.tools.r8.synthesis.P p) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new O7(EnumC1812d20.OBJECT, 0));
        arrayList.add(new D8(D8.a.Dup));
        C0722p1 c0722p1 = this.a;
        arrayList.add(new I7(183, c0722p1.a(c0722p1.a3, c0722p1.b(c0722p1.D1, new C0767v2[0]), this.a.Y0), false));
        arrayList.add(new O7(EnumC1812d20.a(this.a.R2), 1));
        com.android.tools.r8.graph.Y0 y0 = this.c;
        arrayList.add(new C3482z7(y0, y0));
        arrayList.add(new C3186v8());
        p.a((AbstractCollection) AbstractC1639am.c(com.android.tools.r8.graph.W0.Y1().a(this.d).a(C0725p4.b(4097, true)).a(new C0753u(this.b, 2, 2, arrayList)).c().a()));
    }

    private void b(com.android.tools.r8.synthesis.P p) {
        p.a((List) AbstractC1639am.c(com.android.tools.r8.graph.T0.d1().a(this.c).a(com.android.tools.r8.graph.Q2.k(4113)).d().a()));
    }

    private void c(com.android.tools.r8.synthesis.P p) {
        ArrayList arrayList = new ArrayList();
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        arrayList.add(new O7(enumC1812d20, 0));
        com.android.tools.r8.graph.Y0 y0 = this.c;
        arrayList.add(new C3413y7(y0, y0));
        arrayList.add(new I7(185, this.a.l4.a, true));
        arrayList.add(new C3109u8(enumC1812d20));
        p.b((AbstractCollection) AbstractC1639am.c(com.android.tools.r8.graph.W0.Y1().a(this.e).a(C0725p4.b(4100, false)).a(new C0753u(this.b, 1, 1, arrayList)).c().a()));
    }
}
