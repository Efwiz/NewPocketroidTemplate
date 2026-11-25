package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class C0 extends AbstractC3402y0 {
    protected C0(C0722p1 c0722p1) {
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
            throw new C1367Tb("Desugared library: cannot parse method " + str);
        }
        b();
        int iA = a(strArrSplit);
        c(a(strArrSplit[iA]));
        b(a(strArrSplit[iA + 1]));
        a(this.a.c(strArrSplit[iA + 2]));
        for (int i = iA + 3; i < strArrSplit.length; i++) {
            a(a(strArrSplit[i]));
        }
        a();
    }

    protected abstract void c(C0767v2 c0767v2);
}
