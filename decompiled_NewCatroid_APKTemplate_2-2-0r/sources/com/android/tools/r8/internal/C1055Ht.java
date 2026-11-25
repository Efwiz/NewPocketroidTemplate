package com.android.tools.r8.internal;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ht, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1055Ht extends AbstractC0836Am {
    private final Map e;
    private final AbstractC1639am f;
    private final long g;
    private transient AbstractC1048Hm h;

    private C1055Ht(HashMap map, AbstractC1639am abstractC1639am, long j) {
        this.e = map;
        this.f = abstractC1639am;
        this.g = j;
    }

    static AbstractC0836Am a(AbstractC1639am abstractC1639am) {
        ZF[] zfArr = (ZF[]) abstractC1639am.toArray(new ZF[0]);
        HashMap mapB = RD.b(zfArr.length);
        long j = 0;
        for (int i = 0; i < zfArr.length; i++) {
            ZF zf = zfArr[i];
            int iA = zf.a();
            j += (long) iA;
            Object objB = zf.b();
            int i2 = AbstractC3123uM.a;
            objB.getClass();
            mapB.put(objB, Integer.valueOf(iA));
            if (!(zf instanceof C1829dG)) {
                zfArr[i] = new C1829dG(iA, objB);
            }
        }
        return new C1055Ht(mapB, AbstractC1639am.b(zfArr.length, zfArr), j);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public final int b(Object obj) {
        return ((Integer) this.e.getOrDefault(obj, 0)).intValue();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC0836Am
    /* JADX INFO: renamed from: i */
    public final AbstractC1048Hm I() {
        AbstractC1048Hm abstractC1048Hm = this.h;
        if (abstractC1048Hm != null) {
            return abstractC1048Hm;
        }
        C3456ym c3456ym = new C3456ym(this.f, this);
        this.h = c3456ym;
        return c3456ym;
    }

    @Override // com.android.tools.r8.internal.AbstractC0836Am
    final ZF j(int i) {
        return (ZF) this.f.get(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return AbstractC0968Es.a(this.g);
    }
}
