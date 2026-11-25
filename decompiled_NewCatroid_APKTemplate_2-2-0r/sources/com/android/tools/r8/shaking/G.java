package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.l5;
import java.util.Objects;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class G {
    private final L a;

    G(L l) {
        this.a = l;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(L l, C0767v2 c0767v2, l5 l5Var) {
        l.I.b(c0767v2);
    }

    public final AbstractC0727q0 a(C0767v2 c0767v2, InterfaceC0694k5 interfaceC0694k5) {
        final L l = this.a;
        Objects.requireNonNull(l);
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.tools.r8.shaking.G$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                L.m2148$$Nest$mb(l, (AbstractC0727q0) obj, (l5) obj2);
            }
        };
        final L l2 = this.a;
        Objects.requireNonNull(l2);
        return l.a(c0767v2, interfaceC0694k5, biConsumer, new BiConsumer() { // from class: com.android.tools.r8.shaking.G$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                G.a(l2, (C0767v2) obj, (l5) obj2);
            }
        });
    }
}
