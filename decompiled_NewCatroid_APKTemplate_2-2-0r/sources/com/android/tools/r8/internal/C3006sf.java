package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.utils.C3932m1;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3006sf extends com.android.tools.r8.graph.N0 {
    static final /* synthetic */ boolean k = true;
    final /* synthetic */ C3932m1 f;
    final /* synthetic */ C3979v2 g;
    final /* synthetic */ InterfaceC2229iM h;
    final /* synthetic */ List i;
    final /* synthetic */ C3076tf j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3006sf(C3076tf c3076tf, int i, C0684j2 c0684j2, C3932m1 c3932m1, C3979v2 c3979v2, C2067gM c2067gM, ArrayList arrayList) {
        super(i, c0684j2);
        this.j = c3076tf;
        this.f = c3932m1;
        this.g = c3979v2;
        this.h = c2067gM;
        this.i = arrayList;
    }

    @Override // com.android.tools.r8.graph.N0, com.android.tools.r8.graph.I0
    public final void a(com.android.tools.r8.graph.B0.b bVar) {
        super.a(bVar);
        if (!k && a() < 0) {
            throw new AssertionError();
        }
        if (this.f.a() < 0) {
            this.f.e(b());
        }
        AbstractC1915eM abstractC1915eMC = c();
        if (this.g.b() != null) {
            InterfaceC2606nM interfaceC2606nM = this.j.b;
            int iB = b();
            AbstractC1915eM abstractC1915eM = (AbstractC1915eM) this.g.b();
            InterfaceC2229iM interfaceC2229iM = this.h;
            List list = this.i;
            AbstractC1915eM abstractC1915eM2 = (AbstractC1915eM) interfaceC2229iM.a(abstractC1915eM).a();
            for (int iIntValue = ((Integer) this.g.a()).intValue(); iIntValue < iB; iIntValue++) {
                list.add(new C3114uD(interfaceC2606nM.a(iIntValue), abstractC1915eM2));
            }
        }
        this.g.a(Integer.valueOf(b()));
        this.g.b(abstractC1915eMC);
    }
}
