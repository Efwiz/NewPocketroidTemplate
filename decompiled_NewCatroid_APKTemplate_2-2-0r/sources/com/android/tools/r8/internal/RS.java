package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class RS extends AbstractC0836Am {
    static final AbstractC0836Am j = a((Collection) MS.d);
    private final transient C1829dG[] e;
    private final transient C1829dG[] f;
    private final transient int g;
    private final transient int h;
    private transient AbstractC1048Hm i;

    private RS(C1829dG[] c1829dGArr, C1829dG[] c1829dGArr2, int i, int i2, AbstractC1048Hm abstractC1048Hm) {
        this.e = c1829dGArr;
        this.f = c1829dGArr2;
        this.g = i;
        this.h = i2;
        this.i = abstractC1048Hm;
    }

    static AbstractC0836Am a(Collection collection) {
        int size = collection.size();
        C1829dG[] c1829dGArr = new C1829dG[size];
        if (size == 0) {
            return new RS(c1829dGArr, null, 0, 0, SS.h);
        }
        int iA = AbstractC0900Ck.a(size, 1.0d);
        int i = iA - 1;
        C1829dG[] c1829dGArr2 = new C1829dG[iA];
        Iterator it = collection.iterator();
        long j2 = 0;
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            ZF zf = (ZF) it.next();
            Object objB = zf.b();
            int i4 = AbstractC3123uM.a;
            objB.getClass();
            int iA2 = zf.a();
            int iHashCode = objB.hashCode();
            int iA3 = AbstractC0900Ck.a(iHashCode) & i;
            C1829dG c1829dG = c1829dGArr2[iA3];
            C1829dG c1829dG2 = c1829dG == null ? (!(zf instanceof C1829dG) || (zf instanceof QS)) ? new C1829dG(iA2, objB) : (C1829dG) zf : new QS(objB, iA2, c1829dG);
            i2 += iHashCode ^ iA2;
            c1829dGArr[i3] = c1829dG2;
            c1829dGArr2[iA3] = c1829dG2;
            j2 += (long) iA2;
            i3++;
        }
        for (int i5 = 0; i5 < iA; i5++) {
            int i6 = 0;
            for (C1829dG c1829dGC = c1829dGArr2[i5]; c1829dGC != null; c1829dGC = c1829dGC.c()) {
                i6++;
                if (i6 > 9) {
                    return C1055Ht.a(AbstractC1639am.b(size, c1829dGArr));
                }
            }
        }
        return new RS(c1829dGArr, c1829dGArr2, AbstractC0968Es.a(j2), i2, null);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public final int b(Object obj) {
        C1829dG[] c1829dGArr = this.f;
        if (obj != null && c1829dGArr != null) {
            for (C1829dG c1829dGC = c1829dGArr[AbstractC0900Ck.a(obj) & (c1829dGArr.length - 1)]; c1829dGC != null; c1829dGC = c1829dGC.c()) {
                if (AbstractC3428yK.a(obj, c1829dGC.b())) {
                    return c1829dGC.a();
                }
            }
        }
        return 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC0836Am, java.util.Collection, com.android.tools.r8.internal.InterfaceC1600aG
    public final int hashCode() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC0836Am
    /* JADX INFO: renamed from: i */
    public final AbstractC1048Hm I() {
        AbstractC1048Hm abstractC1048Hm = this.i;
        if (abstractC1048Hm != null) {
            return abstractC1048Hm;
        }
        C3456ym c3456ym = new C3456ym(Arrays.asList(this.e), this);
        this.i = c3456ym;
        return c3456ym;
    }

    @Override // com.android.tools.r8.internal.AbstractC0836Am
    final ZF j(int i) {
        return this.e[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.g;
    }
}
