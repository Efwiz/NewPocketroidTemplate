package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0922Dg {
    public static AbstractC0922Dg a(C0702m c0702m) {
        return c0702m.Y().w ? new C0982Fg(c0702m) : C1472Wf.e();
    }

    public abstract Set a(C2409kl c2409kl, TE te, com.android.tools.r8.graph.proto.k kVar);

    public abstract void a(C0702m c0702m, C3012sl c3012sl, C2844qM c2844qM, ExecutorService executorService, com.android.tools.r8.ir.optimize.info.o oVar);

    public abstract void a(com.android.tools.r8.graph.o5 o5Var);

    public abstract void a(C0729q2 c0729q2, OW ow);

    public abstract void a(AbstractC1476Wj abstractC1476Wj);

    public abstract void a(C2409kl c2409kl, TE te);

    public abstract void b();

    public abstract void b(com.android.tools.r8.graph.o5 o5Var);

    public abstract void c();

    public abstract void d();

    public static C1472Wf a() {
        return C1472Wf.e();
    }
}
