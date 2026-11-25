package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class I0 {
    private final Map a;

    private I0(IdentityHashMap identityHashMap) {
        this.a = identityHashMap;
    }

    public static G0 a() {
        return new G0();
    }

    public final void a(C0702m c0702m) {
        final com.android.tools.r8.graph.T2 t2Y = ((C3733i) c0702m.f()).y();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.I0$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                I0.a(t2Y, (com.android.tools.r8.graph.Y0) obj, (H0) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.T2 t2, com.android.tools.r8.graph.Y0 y0, H0 h0) {
        com.android.tools.r8.graph.U2 u2 = new com.android.tools.r8.graph.U2(y0);
        u2.a(h0.a);
        u2.b(h0.b);
        t2.a(y0, u2);
    }
}
