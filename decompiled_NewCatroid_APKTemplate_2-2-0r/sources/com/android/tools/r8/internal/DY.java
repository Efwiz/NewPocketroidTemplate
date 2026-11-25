package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class DY {
    private static final DY e = new DY();
    private final boolean a;
    private final boolean b;
    private final List c;
    private final AbstractC1639am d;

    public DY(ArrayList arrayList, ArrayList arrayList2, boolean z) {
        this.a = false;
        this.b = z;
        arrayList.sort(Comparator.naturalOrder());
        this.c = arrayList;
        arrayList2.sort(Comparator.naturalOrder());
        this.d = AbstractC1639am.a(arrayList2);
    }

    public static DY a() {
        return e;
    }

    public final List b() {
        return this.c;
    }

    public final List c() {
        return this.d;
    }

    public final boolean d() {
        return this.a;
    }

    public final boolean e() {
        return this.b;
    }

    private DY() {
        this.a = true;
        this.b = false;
        this.c = AbstractC1639am.i();
        this.d = AbstractC1639am.i();
    }
}
