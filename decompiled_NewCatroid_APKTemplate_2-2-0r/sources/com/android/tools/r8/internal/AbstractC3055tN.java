package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3055tN {
    static void a(final C0729q2 c0729q2, InterfaceC0694k5 interfaceC0694k5, C2626nc c2626nc, D3 d3) {
        if (d3.j()) {
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.tN$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AbstractC3055tN.a(c0729q2, (InterfaceC1997fN) obj);
                }
            };
            c2626nc.getClass();
            if (interfaceC0694k5.e0()) {
                c2626nc.a(consumer, interfaceC0694k5.a0());
            } else {
                if (!C2626nc.d && !interfaceC0694k5.k()) {
                    throw new AssertionError();
                }
                c2626nc.a(interfaceC0694k5.g0().getReference(), consumer);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0729q2 c0729q2, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        interfaceC1997fN.a(c0729q2.getReference());
        c0729q2.l(new lN$$ExternalSyntheticLambda1(interfaceC1997fN));
    }
}
