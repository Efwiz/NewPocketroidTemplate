package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.utils.A1;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class r {
    static r a(C0702m c0702m) {
        A1 a1Y = c0702m.Y();
        if ((!c0702m.z().b() && a1Y.C1.r().isEmpty() && !a1Y.C1.B()) || a1Y.C1.d()) {
            return new C0580q();
        }
        a1Y.n1.getClass();
        return new C0578p(c0702m);
    }

    public abstract void a(Y0 y0);

    public abstract void a(C0684j2 c0684j2);

    abstract void a(C0729q2 c0729q2);

    public abstract void a(C0767v2 c0767v2);

    abstract void a(A1 a1);

    abstract boolean a();
}
