package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class RY {
    private final C0702m a;
    private final InterfaceC2802pl b;
    private final EnumC0928Dk c;
    private final ArrayList d;
    private final Set e;

    private RY(C0702m c0702m, InterfaceC2802pl interfaceC2802pl, EnumC0928Dk enumC0928Dk) {
        this.d = new ArrayList();
        this.e = OU.c();
        this.a = c0702m;
        this.b = interfaceC2802pl;
        this.c = enumC0928Dk;
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var) {
        this.d.add(o5Var);
    }

    public final void b(com.android.tools.r8.graph.o5 o5Var) {
        this.e.add(o5Var.a());
    }

    public final SY a() {
        return new SY(this.a, this.b, this.c, this.d, this.e);
    }
}
