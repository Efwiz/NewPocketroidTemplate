package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EG extends QY {
    private final C0767v2 c;

    private EG(C0702m c0702m, C0767v2 c0767v2, C0767v2 c0767v3) {
        super(c0702m, c0767v2);
        this.c = c0767v3;
    }

    public static EG a(C0702m c0702m, C0767v2 c0767v2, C0767v2 c0767v3) {
        return new EG(c0702m, c0767v2, c0767v3);
    }

    public final C0753u b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new V7(null, this.c));
        arrayList.add(new C3186v8());
        return a(arrayList);
    }
}
