package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.AbstractC3896g;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class WG extends UF {
    static final /* synthetic */ boolean f = true;
    private final com.android.tools.r8.androidapi.b b;
    private final boolean c;
    private final boolean d;
    private final com.android.tools.r8.androidapi.h e;

    public WG(C0702m c0702m) {
        this.b = c0702m.d();
        this.c = c0702m.Y().a().d();
        this.d = c0702m.q();
        this.e = c0702m.o();
    }

    @Override // com.android.tools.r8.internal.UF
    public final Object a(C0729q2 c0729q2) {
        com.android.tools.r8.androidapi.h hVarA;
        if (!f && !this.c) {
            throw new AssertionError();
        }
        if (this.d) {
            com.android.tools.r8.androidapi.b bVar = this.b;
            Iterable iterableH0 = c0729q2.H0();
            bVar.getClass();
            hVarA = AbstractC3896g.a(c0729q2, bVar.a(iterableH0, com.android.tools.r8.androidapi.h.f()));
        } else {
            hVarA = AbstractC3896g.a(c0729q2, this.e);
        }
        if (hVarA.H()) {
            return null;
        }
        return hVarA;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoDifferentApiReferenceLevel";
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean j() {
        return !this.c;
    }
}
