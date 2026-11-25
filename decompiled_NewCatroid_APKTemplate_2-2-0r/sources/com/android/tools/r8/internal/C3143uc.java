package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3143uc extends G0 {
    final /* synthetic */ C3222vc b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C3143uc(C3222vc c3222vc) {
        super(c3222vc);
        this.b = c3222vc;
    }

    @Override // com.android.tools.r8.internal.G0
    final InterfaceC1600aG a() {
        return this.b;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final Object[] toArray() {
        int iC = this.a.c();
        AbstractC2625nb.a(iC, "arraySize");
        ArrayList arrayList = new ArrayList(AbstractC0968Es.a(((long) iC) + 5 + ((long) (iC / 10))));
        AbstractC3533zt.a(arrayList, this.a.e());
        return arrayList.toArray();
    }

    /* synthetic */ C3143uc(C3222vc c3222vc, int i) {
        this(c3222vc);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final Object[] toArray(Object[] objArr) {
        int iC = this.a.c();
        AbstractC2625nb.a(iC, "arraySize");
        ArrayList arrayList = new ArrayList(AbstractC0968Es.a(((long) iC) + 5 + ((long) (iC / 10))));
        AbstractC3533zt.a(arrayList, this.a.e());
        return arrayList.toArray(objArr);
    }
}
