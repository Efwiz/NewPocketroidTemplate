package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class a {
    public abstract int a(double d, double d2);

    public abstract int a(float f, float f2);

    public abstract int a(int i, int i2);

    public abstract int a(long j, long j2);

    public abstract int a(AbstractC0744s2 abstractC0744s2, AbstractC0744s2 abstractC0744s3);

    public abstract int a(C0759u2 c0759u2, C0759u2 c0759u3);

    public abstract int a(C0767v2 c0767v2, C0767v2 c0767v3);

    public abstract <S> int a(S s, S s2, y<S> yVar);

    public abstract int a(Iterator it, Iterator it2, v vVar);

    public final int a(List list, List list2) {
        return a(list.iterator(), list2.iterator(), new a$$ExternalSyntheticLambda0());
    }

    public abstract int a(boolean z, boolean z2);

    public int a(Y0 y0, Y0 y1) {
        return a(y0, y1, (y<Y0>) y0.p());
    }

    public int a(C0684j2 c0684j2, C0684j2 c0684j3) {
        return a(c0684j2, c0684j3, (y<C0684j2>) c0684j2.p());
    }
}
