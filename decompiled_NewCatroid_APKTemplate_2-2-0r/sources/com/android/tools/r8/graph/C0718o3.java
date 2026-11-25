package com.android.tools.r8.graph;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.graph.o3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0718o3 {
    private final ArrayList a;
    private C0706m3.c b;
    private final ArrayList c;

    private C0718o3() {
        this.a = new ArrayList();
        this.b = null;
        this.c = new ArrayList();
    }

    public final void a(List list) {
        this.a.addAll(list);
    }

    public final void b(C0706m3.c cVar) {
        this.b = cVar;
    }

    public final void a(C0706m3.c cVar) {
        this.c.add(cVar);
    }

    public final C0706m3.b a() {
        return new C0706m3.b(this.a, this.b, this.c);
    }
}
