package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O20 extends QY {
    private final com.android.tools.r8.graph.Y0 c;
    private final C0767v2 d;

    public O20(C0702m c0702m, com.android.tools.r8.graph.Y0 y0, C0767v2 c0767v2) {
        super(c0702m, y0.e);
        this.c = y0;
        this.d = c0767v2;
    }

    public final C0753u b() {
        C0722p1 c0722p1A = this.a.a();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new O7(EnumC1812d20.a(this.c.e), 0));
        arrayList.add(new I7(183, c0722p1A.a(this.d, c0722p1A.b(c0722p1A.D1, new C0767v2[0]), c0722p1A.Y0), false));
        arrayList.add(new O7(EnumC1812d20.a(this.c.e), 0));
        arrayList.add(new O7(EnumC1812d20.a(this.c.h), 1));
        com.android.tools.r8.graph.Y0 y0 = this.c;
        arrayList.add(new C3482z7(y0, y0));
        arrayList.add(new C3186v8());
        return a(arrayList);
    }
}
