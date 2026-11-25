package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0572k$$ExternalSyntheticLambda8;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class PF extends AbstractC2438l6 {
    PF(C0702m c0702m) {
        super(c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(com.android.tools.r8.graph.o5 o5Var) {
        o5Var.a(new QF(this.a, (RF) b(o5Var), new Function() { // from class: com.android.tools.r8.internal.PF$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (RF) this.f$0.b((com.android.tools.r8.graph.o5) obj);
            }
        }, this.c));
    }

    @Override // com.android.tools.r8.internal.AbstractC2438l6
    protected final AbstractC2839qH a(com.android.tools.r8.graph.o5 o5Var) {
        return new RF(o5Var);
    }

    public final OF a(ExecutorService executorService) {
        C3879c3.a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.internal.PF$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
        return new OF(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.PF$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((com.android.tools.r8.graph.o5) obj);
            }
        }, new C0572k$$ExternalSyntheticLambda8());
    }
}
