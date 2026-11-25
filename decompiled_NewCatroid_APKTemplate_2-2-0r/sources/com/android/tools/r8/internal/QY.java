package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class QY {
    protected final C0702m a;
    private final C0767v2 b;

    protected QY(C0702m c0702m, C0767v2 c0767v2) {
        this.a = c0702m;
        this.b = c0767v2;
    }

    public final C0767v2 a() {
        return this.b;
    }

    protected final C0753u a(ArrayList arrayList) {
        return new C0753u(this.b, 16, 16, arrayList);
    }
}
