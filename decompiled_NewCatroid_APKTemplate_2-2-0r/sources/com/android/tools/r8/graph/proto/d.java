package com.android.tools.r8.graph.proto;

import com.android.tools.r8.internal.AbstractC3324x;
import com.android.tools.r8.internal.C1301Qo;
import com.android.tools.r8.internal.C3085to;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class d {
    private final AbstractC3324x a;

    private d(int i) {
        AbstractC3324x c3085to = i <= 30 ? new C3085to() : new C1301Qo();
        c3085to.e(-1);
        this.a = c3085to;
    }

    public final e a() {
        return this.a.isEmpty() ? g.c() : new f(this.a);
    }

    public final void a(int i, int i2) {
        if (i != i2) {
            this.a.b(i, i2);
        } else {
            this.a.remove(i);
        }
    }
}
