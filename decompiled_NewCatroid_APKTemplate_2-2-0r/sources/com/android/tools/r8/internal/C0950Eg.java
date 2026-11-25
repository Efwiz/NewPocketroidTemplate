package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Collections;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Eg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0950Eg implements com.android.tools.r8.ir.optimize.info.m {
    final /* synthetic */ DH a;
    final /* synthetic */ AbstractC1476Wj b;
    final /* synthetic */ C1372Tg c;
    final /* synthetic */ C0982Fg d;

    C0950Eg(C0982Fg c0982Fg, DH dh, AbstractC1476Wj abstractC1476Wj, C1372Tg c1372Tg) {
        this.d = c0982Fg;
        this.a = dh;
        this.b = abstractC1476Wj;
        this.c = c1372Tg;
    }

    @Override // com.android.tools.r8.ir.optimize.info.m
    public final void a(com.android.tools.r8.ir.optimize.info.k kVar) {
        kVar.a(this.d.a, this.a, this.b).a(this.d.a, this.a, Collections.emptySet());
    }

    @Override // com.android.tools.r8.ir.optimize.info.m
    public final void a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.ir.optimize.info.l lVar) {
        C0702m c0702m = this.d.a;
        DH dh = this.a;
        lVar.getClass();
        lVar.a(c0702m, dh, Collections.emptySet()).a(this.d.a, this.a, this.b).a(this.d.a, this.a, this.b, this.c.c());
        if (this.c.a().containsValue(w0.getReference())) {
            return;
        }
        lVar.W();
    }
}
