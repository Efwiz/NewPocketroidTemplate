package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0719o4;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class DM {
    private final C0725p4 a;
    private final boolean b;
    private final AbstractC3938n2 c;
    private final boolean d;

    private DM(com.android.tools.r8.graph.W0 w0, boolean z, boolean z2) {
        this.a = (C0725p4) ((C0719o4) C0725p4.O().a(w0.getAccessFlags().j()).b(w0.getAccessFlags().l()).c(w0.getAccessFlags().m())).d(w0.getAccessFlags().a0()).e(w0.getAccessFlags().c0()).a();
        this.b = z;
        this.c = w0.D1();
        this.d = z2;
    }

    public final boolean equals(Object obj) {
        if (obj == null || DM.class != obj.getClass()) {
            return false;
        }
        DM dm = (DM) obj;
        return this.a.equals(dm.a) && this.b == dm.b && this.c == dm.c && this.d == dm.d;
    }

    public final int hashCode() {
        return Objects.hash(this.a, Boolean.valueOf(this.b), Integer.valueOf(this.c.f()), Boolean.valueOf(this.d));
    }
}
