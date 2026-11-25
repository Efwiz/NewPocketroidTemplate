package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.InterfaceC3949p3;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ha, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2164ha {
    public static InterfaceC2090ga a(final C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, final C2409kl c2409kl, final com.android.tools.r8.utils.y3 y3Var) {
        if (o5Var.e().u1() || o5Var.e().h1() == 0) {
            return InterfaceC2090ga.a();
        }
        final C0868Bs c0868Bs = new C0868Bs(c0702m, D5.h(), c2409kl, new C2930rZ(c0702m, o5Var, c2409kl), C0842As.a());
        final C1097Jd c1097Jd = (C1097Jd) y3Var.a("Data flow analysis", new InterfaceC3949p3() { // from class: com.android.tools.r8.internal.ha$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return AbstractC2164ha.a(c0868Bs, c2409kl, y3Var);
            }
        });
        if (c1097Jd == null) {
            return InterfaceC2090ga.a();
        }
        AbstractC3350xL abstractC3350xL = (AbstractC3350xL) y3Var.a("Externalize", new InterfaceC3949p3() { // from class: com.android.tools.r8.internal.ha$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return AbstractC2164ha.a(c1097Jd, c0702m);
            }
        });
        abstractC3350xL.getClass();
        if (abstractC3350xL instanceof D5) {
            return InterfaceC2090ga.b();
        }
        return abstractC3350xL instanceof C2821q10 ? InterfaceC2090ga.a() : new C3299wc(abstractC3350xL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1097Jd a(C0868Bs c0868Bs, C2409kl c2409kl, com.android.tools.r8.utils.y3 y3Var) {
        return c0868Bs.a(c2409kl.j(), y3Var).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3350xL a(C1097Jd c1097Jd, C0702m c0702m) {
        return ((AbstractC3350xL) c1097Jd.a(c0702m)).f();
    }
}
