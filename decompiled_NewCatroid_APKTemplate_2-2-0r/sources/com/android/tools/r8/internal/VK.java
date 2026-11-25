package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class VK {
    public static VK a(C0702m c0702m) {
        return c0702m.Y().T.a ? new C2225iL(c0702m) : new UK();
    }

    public abstract void a(com.android.tools.r8.graph.o5 o5Var);

    public abstract void a(AbstractC1476Wj abstractC1476Wj);

    public abstract void a(C2409kl c2409kl, com.android.tools.r8.utils.y3 y3Var);

    public abstract void a(C3012sl c3012sl, com.android.tools.r8.ir.optimize.info.o oVar, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var);

    public abstract void b();

    public abstract void b(com.android.tools.r8.graph.o5 o5Var);

    public static VK a() {
        return new UK();
    }
}
