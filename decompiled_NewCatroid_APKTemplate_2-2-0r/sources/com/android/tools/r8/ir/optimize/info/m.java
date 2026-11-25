package com.android.tools.r8.ir.optimize.info;

import com.android.tools.r8.graph.T0;
import com.android.tools.r8.graph.U0;
import com.android.tools.r8.graph.W0;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface m {
    default void a(U0 u0) {
        final g gVarL0 = u0.L0();
        if (gVarL0.d()) {
            u0.a(new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.m$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(gVarL0, (T0) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.m$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(gVarL0, (W0) obj);
                }
            });
        }
    }

    void a(W0 w0, l lVar);

    void a(k kVar);

    /* JADX INFO: Access modifiers changed from: private */
    /* synthetic */ default void a(g gVar, T0 t0) {
        a(gVar.c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* synthetic */ default void a(g gVar, W0 w0) {
        a(w0, gVar.a());
    }
}
