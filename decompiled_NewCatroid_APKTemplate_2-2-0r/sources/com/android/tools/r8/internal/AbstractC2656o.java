package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.o, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2656o extends AbstractC3402y0 {
    protected AbstractC2656o(C0722p1 c0722p1) {
        super(c0722p1);
    }

    protected abstract void a();

    protected abstract void a(C0759u2 c0759u2);

    protected abstract void a(C0767v2 c0767v2);

    protected abstract void b();

    protected abstract void b(C0767v2 c0767v2);

    public final void b(String str) {
        String[] strArrSplit = str.split("\\s+|,\\s+|#|\\(|\\)");
        if (strArrSplit.length < 3) {
            throw new C1367Tb("Desugared library: cannot parse field " + str);
        }
        b();
        int iA = a(strArrSplit);
        a(a(strArrSplit[iA]));
        b(a(strArrSplit[iA + 1]));
        a(this.a.c(strArrSplit[iA + 2]));
        a();
    }
}
