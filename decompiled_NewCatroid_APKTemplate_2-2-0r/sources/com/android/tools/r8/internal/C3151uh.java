package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3151uh extends AbstractC3078th {
    static final /* synthetic */ boolean b = true;
    private final C0767v2 a;

    public C3151uh(C0767v2 c0767v2) {
        this.a = c0767v2;
    }

    public static List a(C0684j2 c0684j2, C0684j2 c0684j3) {
        int iF0 = c0684j3.F0() - c0684j2.F0();
        if (iF0 == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(iF0);
        for (int iF1 = c0684j2.F0(); iF1 < c0684j3.G0().size(); iF1++) {
            arrayList.add(new C3151uh(c0684j3.k(iF1)));
        }
        return arrayList;
    }

    public final boolean equals(Object obj) {
        return obj != null && C3151uh.class == obj.getClass();
    }

    public final int hashCode() {
        return 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC3078th
    public final C0767v2 a(C0722p1 c0722p1) {
        if (b || this.a != null) {
            return this.a;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3078th
    public final AbstractC3472z00 a(C0702m c0702m, C0767v2 c0767v2) {
        return AbstractC3472z00.a(c0767v2, WH.i(), (C0702m<?>) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC3078th
    public final C2853qV a(C0702m c0702m) {
        return c0702m.b().a();
    }
}
