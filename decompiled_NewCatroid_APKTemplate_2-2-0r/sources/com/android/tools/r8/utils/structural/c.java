package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class c extends a {
    static final /* synthetic */ boolean a = true;

    @Override // com.android.tools.r8.utils.structural.a
    public final int a(boolean z, boolean z2) {
        return Boolean.compare(z, z2);
    }

    @Override // com.android.tools.r8.utils.structural.a
    public final int a(int i, int i2) {
        return Integer.compare(i, i2);
    }

    @Override // com.android.tools.r8.utils.structural.a
    public final int a(long j, long j2) {
        return Long.compare(j, j2);
    }

    @Override // com.android.tools.r8.utils.structural.a
    public final int a(float f, float f2) {
        return Float.compare(f, f2);
    }

    @Override // com.android.tools.r8.utils.structural.a
    public final int a(double d, double d2) {
        return Double.compare(d, d2);
    }

    @Override // com.android.tools.r8.utils.structural.a
    public final int a(Iterator it, Iterator it2, v vVar) {
        int iA = 0;
        while (iA == 0 && it.hasNext() && it2.hasNext()) {
            iA = vVar.a(it.next(), it2.next(), this);
        }
        return iA == 0 ? Boolean.compare(it.hasNext(), it2.hasNext()) : iA;
    }

    @Override // com.android.tools.r8.utils.structural.a
    public int a(C0759u2 c0759u2, C0759u2 c0759u3) {
        return c0759u2.a(c0759u3);
    }

    @Override // com.android.tools.r8.utils.structural.a
    public final int a(AbstractC0744s2 abstractC0744s2, AbstractC0744s2 abstractC0744s3) {
        int iCompare = Integer.compare(abstractC0744s2.A0(), abstractC0744s3.A0());
        if (iCompare != 0) {
            return iCompare;
        }
        if (!a && abstractC0744s2.getClass() != abstractC0744s3.getClass()) {
            throw new AssertionError();
        }
        if (abstractC0744s2 instanceof C0767v2) {
            return a(abstractC0744s2.w0(), abstractC0744s3.w0());
        }
        if (abstractC0744s2.x0()) {
            return a(abstractC0744s2.t0(), abstractC0744s3.t0());
        }
        return a(abstractC0744s2.v0(), abstractC0744s3.v0());
    }

    @Override // com.android.tools.r8.utils.structural.a
    public final int a(Object obj, Object obj2, y yVar) {
        b bVar = new b(obj, obj2, this);
        yVar.a(bVar);
        return bVar.d;
    }
}
