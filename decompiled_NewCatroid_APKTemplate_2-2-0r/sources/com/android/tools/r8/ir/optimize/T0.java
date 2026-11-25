package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.TE;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class T0 {
    static final /* synthetic */ boolean c = true;
    private final o5 a;
    private boolean b;

    private T0(o5 o5Var) {
        this.a = o5Var;
    }

    public final o5 a() {
        if (c || this.b) {
            return this.a;
        }
        throw new AssertionError();
    }

    public final o5 b() {
        return this.a;
    }

    public final void a(TE te) {
        te.c(this.a);
        this.b = true;
    }
}
