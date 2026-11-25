package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F10 implements C7 {
    private final AbstractC1639am a;

    public F10(C0702m c0702m) {
        C1507Xl c1507XlG = AbstractC1639am.g();
        D10.a(c0702m, c1507XlG);
        E10.a(c0702m, c1507XlG);
        A10.a(c0702m, c1507XlG);
        B10.a(c0702m, c1507XlG);
        C3474z10.a(c0702m, c1507XlG);
        this.a = c1507XlG.a();
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            C3224ve c3224veA = ((C10) it.next()).a(b7);
            if (c3224veA != null) {
                return c3224veA;
            }
        }
        return C3224ve.c();
    }
}
