package com.android.tools.r8.ir.optimize.info;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.U0;
import com.android.tools.r8.internal.InterfaceC1448Vh;
import com.android.tools.r8.internal.NE;
import com.android.tools.r8.utils.C3879c3;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class n implements InterfaceC1448Vh, NE {
    public void a(Consumer consumer) {
    }

    public static r a() {
        return r.b();
    }

    public static void a(Collection collection, ExecutorService executorService, final m mVar) {
        C3879c3.a(collection, new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.n$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                n.a(mVar, (C0729q2) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final m mVar, C0729q2 c0729q2) {
        Iterable iterableL1 = c0729q2.L1();
        Objects.requireNonNull(mVar);
        iterableL1.forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.n$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                mVar.a((U0) obj);
            }
        });
    }
}
