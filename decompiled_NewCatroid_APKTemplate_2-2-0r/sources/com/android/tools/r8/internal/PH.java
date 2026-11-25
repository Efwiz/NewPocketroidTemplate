package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class PH extends R3 {
    private final C0702m b;

    public PH(C0702m c0702m) {
        this.b = c0702m;
    }

    @Override // com.android.tools.r8.internal.R3
    public final boolean a(C0729q2 c0729q2) {
        Iterator it = c0729q2.h2().iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.W0 w0E = ((com.android.tools.r8.graph.o5) it.next()).e();
            if (w0E.f.c0() || w0E.a1().y0()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NotTwoInitsWithMonitors";
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean j() {
        return !this.b.Y().t();
    }
}
