package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0572k$$ExternalSyntheticLambda8;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.k6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2358k6 extends AbstractC3231vl {
    static final /* synthetic */ boolean e = true;

    public C2358k6(C0702m<C3733i> c0702m) {
        super(c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(com.android.tools.r8.graph.o5 o5Var) {
        o5Var.a(new C3308wl(this.a, (C2768pH) b(o5Var), new Function() { // from class: com.android.tools.r8.internal.k6$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (C2768pH) this.f$0.b((com.android.tools.r8.graph.o5) obj);
            }
        }, this.c, CM.b()));
    }

    @Override // com.android.tools.r8.internal.AbstractC3231vl
    public /* bridge */ /* synthetic */ C2205i6 a(ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) throws ExecutionException {
        return super.a(executorService, y3Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC3231vl
    final void a(ExecutorService executorService) {
        C3879c3.a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.internal.k6$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.k6$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((com.android.tools.r8.graph.o5) obj);
            }
        }, new C0572k$$ExternalSyntheticLambda8());
    }

    @Override // com.android.tools.r8.internal.AbstractC3231vl
    final void a() {
        Iterator<C0729q2> it = ((C3733i) this.a.f()).d().iterator();
        while (it.hasNext()) {
            for (com.android.tools.r8.graph.W0 w0 : it.next().M1()) {
                if (!e) {
                    if (w0.q1() != (this.b.get(w0.getReference()) != null)) {
                        throw new AssertionError();
                    }
                }
            }
        }
    }
}
