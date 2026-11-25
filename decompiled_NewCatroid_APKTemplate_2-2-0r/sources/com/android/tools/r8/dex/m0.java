package com.android.tools.r8.dex;

import com.android.tools.r8.errors.DexFileOverflowDiagnostic;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.O2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class m0 extends h0 {
    m0(C0548k c0548k, Collection collection, A1 a1) {
        super(c0548k, collection, a1, AbstractC3135uW.b());
    }

    @Override // com.android.tools.r8.dex.g0
    public final ArrayList a() {
        Map mapB = b();
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            this.f.a((C0729q2) it.next());
        }
        this.f.c.a();
        r0 r0Var = this.f;
        O2 o2 = this.g.e;
        if (!r0Var.a(65536)) {
            if (this.g.k != null && !mapB.isEmpty()) {
                a(mapB, AbstractC3135uW.b());
            }
            return this.c;
        }
        l0 l0Var = r0Var.c;
        long size = l0Var.b.d.size() + l0Var.f.size();
        l0 l0Var2 = r0Var.c;
        o2.a(new DexFileOverflowDiagnostic(false, size, l0Var2.b.e.size() + l0Var2.e.size()));
        throw null;
    }
}
