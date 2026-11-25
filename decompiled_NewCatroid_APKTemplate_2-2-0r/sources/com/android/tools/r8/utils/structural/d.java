package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class d extends c {
    private final AbstractC3649o0 b;

    public d(AbstractC3649o0 abstractC3649o0) {
        this.b = abstractC3649o0;
    }

    @Override // com.android.tools.r8.utils.structural.a
    public int a(C0767v2 c0767v2, C0767v2 c0767v3) {
        if (c0767v2 == c0767v3) {
            return 0;
        }
        C0759u2 c0759u2C = this.b.c(c0767v2);
        C0759u2 c0759u2C2 = this.b.c(c0767v3);
        c0759u2C.getClass();
        return a(c0759u2C, c0759u2C2);
    }

    @Override // com.android.tools.r8.utils.structural.a
    public int a(Y0 y0, Y0 y1) {
        if (y0 == y1) {
            return 0;
        }
        C0767v2 c0767v2 = y0.e;
        C0767v2 c0767v3 = y1.e;
        c0767v2.getClass();
        int iA = a(c0767v2, c0767v3);
        if (iA != 0) {
            return iA;
        }
        C0759u2 c0759u2A = this.b.a(y0);
        C0759u2 c0759u2A2 = this.b.a(y1);
        c0759u2A.getClass();
        int iA2 = a(c0759u2A, c0759u2A2);
        if (iA2 != 0) {
            return iA2;
        }
        C0767v2 c0767v4 = y0.h;
        C0767v2 c0767v5 = y1.h;
        c0767v4.getClass();
        return a(c0767v4, c0767v5);
    }

    @Override // com.android.tools.r8.utils.structural.a
    public int a(C0684j2 c0684j2, C0684j2 c0684j3) {
        if (c0684j2 == c0684j3) {
            return 0;
        }
        C0767v2 c0767v2 = c0684j2.e;
        C0767v2 c0767v3 = c0684j3.e;
        c0767v2.getClass();
        int iA = a(c0767v2, c0767v3);
        if (iA != 0) {
            return iA;
        }
        C0759u2 c0759u2A = this.b.a(c0684j2);
        C0759u2 c0759u2A2 = this.b.a(c0684j3);
        c0759u2A.getClass();
        int iA2 = a(c0759u2A, c0759u2A2);
        return iA2 != 0 ? iA2 : c0684j2.h.a(c0684j3.h, (a) this);
    }
}
