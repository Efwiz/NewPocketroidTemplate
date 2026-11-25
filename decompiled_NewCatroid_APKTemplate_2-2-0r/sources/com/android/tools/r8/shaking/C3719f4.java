package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.InterfaceC2866qg;
import com.android.tools.r8.internal.InterfaceC2938rg;
import com.android.tools.r8.internal.InterfaceC3077tg;
import com.android.tools.r8.internal.OU;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.f4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3719f4 implements InterfaceC3077tg, InterfaceC2866qg, InterfaceC2938rg {
    private final AbstractC1476Wj a;
    private final C0722p1 b;
    private final Set c = OU.c();
    private final Set d = OU.c();
    private final Set e = OU.c();

    public C3719f4(C0702m c0702m) {
        this.a = c0702m.E();
        this.b = c0702m.a();
    }

    public final g4 a(AbstractC1476Wj abstractC1476Wj) {
        g4 g4Var = new g4(this.c, this.d, this.e);
        return (!abstractC1476Wj.i() || abstractC1476Wj == this.a) ? g4Var : g4Var.a(abstractC1476Wj.d());
    }

    public final void b(C0767v2 c0767v2) {
        Set set = this.e;
        C0767v2 c0767v2B = c0767v2.b(this.b);
        if (c0767v2B.O0()) {
            set.add(c0767v2B);
        }
    }

    public final void c(C0767v2 c0767v2) {
        Set set = this.c;
        C0767v2 c0767v2B = c0767v2.b(this.b);
        if (c0767v2B.O0()) {
            set.add(c0767v2B);
        }
    }

    public final void a(C0767v2 c0767v2) {
        Set set = this.d;
        C0767v2 c0767v2B = c0767v2.b(this.b);
        if (c0767v2B.O0()) {
            set.add(c0767v2B);
        }
    }

    public final void a(L l) {
        l.a((InterfaceC3077tg) this).a((InterfaceC2866qg) this).a((InterfaceC2938rg) this);
    }
}
