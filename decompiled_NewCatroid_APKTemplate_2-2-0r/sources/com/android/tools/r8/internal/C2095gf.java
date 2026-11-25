package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2095gf extends AbstractC1939ef {
    private final com.android.tools.r8.dex.code.B1[] e;
    private final int f;

    public C2095gf(AbstractC2412ko abstractC2412ko, com.android.tools.r8.dex.code.B1[] b1Arr) {
        super(abstractC2412ko);
        this.e = b1Arr;
        int iZ = 0;
        for (com.android.tools.r8.dex.code.B1 b1 : b1Arr) {
            iZ += b1.z();
        }
        this.f = iZ;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int a(C2322jf c2322jf) {
        return this.f;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int d() {
        return this.f;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int e() {
        return this.f;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int f() {
        return this.f;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final void a(C2322jf c2322jf, ArrayList arrayList) {
        int iC = c();
        for (com.android.tools.r8.dex.code.B1 b1 : this.e) {
            arrayList.add(b1);
            b1.g(iC);
            iC += b1.z();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final boolean a(AbstractC1939ef abstractC1939ef, C2322jf c2322jf) {
        return (abstractC1939ef instanceof C2095gf) && Arrays.equals(this.e, ((C2095gf) abstractC1939ef).e);
    }
}
