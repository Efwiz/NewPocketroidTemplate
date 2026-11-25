package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0722p1;
import java.util.Collections;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class RQ {
    private final com.android.tools.r8.utils.A1 a;
    private boolean b = false;
    private Set c;

    public RQ(com.android.tools.r8.utils.A1 a1) {
        this.a = a1;
    }

    public void a() {
        this.c = Collections.emptySet();
    }

    public final RQ b() {
        if (this.c == null) {
            C0722p1 c0722p1J0 = this.a.j0();
            this.c = com.android.tools.r8.utils.T2.b(c0722p1J0.G3, c0722p1J0.H3);
        }
        return this;
    }

    public final boolean c() {
        return this.b;
    }

    public final boolean a(C0635d2 c0635d2) {
        return this.c.contains(c0635d2.getType());
    }

    public void a(boolean z) {
        this.b = z;
    }
}
