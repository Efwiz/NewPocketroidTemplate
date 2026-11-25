package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1293Qg {
    static final /* synthetic */ boolean f = true;
    private final C0722p1 a;
    private final IdentityHashMap b = new IdentityHashMap();
    private final Q4 c = new Q4();
    private final P4 d = new P4();
    private IdentityHashMap e = new IdentityHashMap();

    C1293Qg(C0702m c0702m) {
        this.a = c0702m.a();
    }

    public final C1293Qg a(AbstractC1048Hm abstractC1048Hm) {
        Iterator it = abstractC1048Hm.iterator();
        while (it.hasNext()) {
            this.b.put((C0767v2) it.next(), this.a.A1);
        }
        return this;
    }

    public final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        if (y0 == y1) {
            return;
        }
        this.c.put(y0, y1);
    }

    public final void a(C0684j2 c0684j2, C0684j2 c0684j3, boolean z) {
        a(c0684j2, c0684j3, z, true, Collections.emptyList());
    }

    public final com.android.tools.r8.graph.proto.k a(C0684j2 c0684j2, C0684j2 c0684j3, boolean z, boolean z2, List list) {
        int i;
        boolean z3 = f;
        if (!z3 && c0684j2 == c0684j3) {
            throw new AssertionError();
        }
        this.d.a((Object) c0684j2, (Object) c0684j3);
        int iA = com.android.tools.r8.utils.M.a(!z2);
        com.android.tools.r8.graph.proto.c.a aVarA = com.android.tools.r8.graph.proto.c.a().a(c0684j2.a(z));
        if (z == z2) {
            i = 0;
        } else {
            if (!z3 && !z2) {
                throw new AssertionError();
            }
            aVarA.a(0, com.android.tools.r8.graph.proto.l.e().c(c0684j2.B0()).b(c0684j3.k(0)).a()).b();
            i = 1;
        }
        for (int i2 = 0; i2 < c0684j2.G0().size(); i2++) {
            C0767v2 c0767v2K = c0684j2.k(i2);
            int i3 = i2 + i;
            C0767v2 c0767v2K2 = c0684j3.k(i3);
            if (c0767v2K != c0767v2K2) {
                aVarA.a(i3 + iA, com.android.tools.r8.graph.proto.l.e().c(c0767v2K).b(c0767v2K2).a());
            }
        }
        com.android.tools.r8.graph.proto.l lVarA = c0684j2.I0() == c0684j3.I0() ? null : com.android.tools.r8.graph.proto.l.e().c(c0684j2.I0()).b(c0684j3.I0()).a();
        com.android.tools.r8.graph.proto.c cVarA = aVarA.a();
        int i4 = com.android.tools.r8.graph.proto.k.f;
        com.android.tools.r8.graph.proto.k kVarA = com.android.tools.r8.graph.proto.k.a(Collections.emptyList(), lVarA, cVarA).a(list);
        this.e.put(c0684j3, kVarA);
        return kVarA;
    }

    final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C0684j2 c0684j2 = (C0684j2) this.d.a(o5Var.getReference(), o5Var.getReference());
        this.d.a((Object) c0684j2, (Object) o5Var.getReference());
        this.d.a((Object) c0684j2, (Object) o5Var2.getReference());
        this.d.b(c0684j2, o5Var.getReference());
    }

    public final C1319Rg a(C0702m c0702m) {
        if (f || !this.b.isEmpty()) {
            return new C1319Rg(c0702m, this.c, this.d, this.b, AbstractC2178hm.a(this.e));
        }
        throw new AssertionError();
    }
}
