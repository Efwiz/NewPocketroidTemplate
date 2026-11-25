package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2796pf extends com.android.tools.r8.graph.N0 {
    static final /* synthetic */ boolean l = true;
    private final C2865qf f;
    private final List g;
    private final InterfaceC2229iM h;
    private final List i;
    private int j;
    private boolean k;

    public C2796pf(C2865qf c2865qf, ArrayList arrayList, C2067gM c2067gM, ArrayList arrayList2, int i, C0684j2 c0684j2) {
        super(i, c0684j2);
        this.j = 0;
        this.f = c2865qf;
        this.g = arrayList;
        this.h = c2067gM;
        this.i = arrayList2;
    }

    @Override // com.android.tools.r8.graph.N0, com.android.tools.r8.graph.I0
    public final void a(com.android.tools.r8.graph.C0 c0) {
        if (this.j != b()) {
            C2865qf c2865qf = this.f;
            int iB = b();
            c2865qf.f.add(new com.android.tools.r8.graph.B0.a(iB - c2865qf.d));
            c2865qf.d = iB;
            this.j = b();
        }
        this.i.add(c0);
    }

    @Override // com.android.tools.r8.graph.N0, com.android.tools.r8.graph.I0
    public final void a(com.android.tools.r8.graph.D0 d0) {
        if (this.j != b()) {
            C2865qf c2865qf = this.f;
            int iB = b();
            c2865qf.f.add(new com.android.tools.r8.graph.B0.a(iB - c2865qf.d));
            c2865qf.d = iB;
            this.j = b();
        }
        this.i.add(d0);
    }

    @Override // com.android.tools.r8.graph.N0, com.android.tools.r8.graph.I0
    public final void a(com.android.tools.r8.graph.B0.d dVar) {
        if (this.j != b()) {
            C2865qf c2865qf = this.f;
            int iB = b();
            c2865qf.f.add(new com.android.tools.r8.graph.B0.a(iB - c2865qf.d));
            c2865qf.d = iB;
            this.j = b();
        }
        this.i.add(dVar);
    }

    @Override // com.android.tools.r8.graph.N0, com.android.tools.r8.graph.I0
    public final void a(com.android.tools.r8.graph.B0.b bVar) {
        super.a(bVar);
        if (!l && a() < 0) {
            throw new AssertionError();
        }
        AbstractC1915eM abstractC1915eMC = c();
        InterfaceC2229iM interfaceC2229iM = this.h;
        List list = this.g;
        C3979v2 c3979v2A = interfaceC2229iM.a(abstractC1915eMC);
        AbstractC1915eM abstractC1915eM = (AbstractC1915eM) c3979v2A.a();
        AbstractC1915eM abstractC1915eM2 = (AbstractC1915eM) c3979v2A.b();
        list.add(new C3114uD(abstractC1915eM2.e(), abstractC1915eM));
        C2865qf c2865qf = this.f;
        int iB = b();
        if (c2865qf.e == null) {
            c2865qf.b = abstractC1915eM2.e();
            c2865qf.e = AbstractC1915eM.b.x().a(c2865qf.b).a(c2865qf.c).a();
        }
        com.android.tools.r8.graph.H0.a(c2865qf.d, c2865qf.e, iB, abstractC1915eM2, c2865qf.f, c2865qf.a, true);
        c2865qf.d = iB;
        c2865qf.e = abstractC1915eM2;
        if (abstractC1915eM2 != abstractC1915eMC) {
            this.k = true;
        }
        this.j = b();
    }

    @Override // com.android.tools.r8.graph.N0, com.android.tools.r8.graph.I0
    public final void a(com.android.tools.r8.graph.B0.c cVar) {
        this.i.add(cVar);
    }

    @Override // com.android.tools.r8.graph.N0, com.android.tools.r8.graph.I0
    public final void a(com.android.tools.r8.graph.G0 g0) {
        this.i.add(g0);
    }

    @Override // com.android.tools.r8.graph.N0, com.android.tools.r8.graph.I0
    public final void a(com.android.tools.r8.graph.E0 e0) {
        this.i.add(e0);
    }
}
