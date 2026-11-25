package com.android.tools.r8.shaking;

import com.android.tools.r8.experimental.graphinfo.GraphConsumer;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.AbstractC2151hN;
import com.android.tools.r8.internal.WP;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class Z {
    public static L a(C0702m<? extends C0680j> c0702m, AbstractC2151hN abstractC2151hN, ExecutorService executorService, B5 b5) {
        return new L(c0702m, abstractC2151hN, executorService, b5, null, L.a.a);
    }

    public static L b(C0702m c0702m, ExecutorService executorService, B5 b5, GraphConsumer graphConsumer) {
        return new L(c0702m, AbstractC2151hN.b(c0702m), executorService, b5, graphConsumer, L.a.e);
    }

    public static L a(C0702m c0702m, ExecutorService executorService, B5 b5, GraphConsumer graphConsumer, Set set) throws Throwable {
        final L l = new L(c0702m, AbstractC2151hN.b(c0702m), executorService, b5, graphConsumer, L.a.b);
        c0702m.e(new InterfaceC3905h3() { // from class: com.android.tools.r8.shaking.Z$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                Z.a(l, (WP) obj);
            }
        });
        l.c(set);
        return l;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(L l, WP wp) {
        l.b(wp.a());
    }

    public static L a(C0702m c0702m, ExecutorService executorService, B5 b5) {
        return new L(c0702m, AbstractC2151hN.b(c0702m), executorService, b5, null, L.a.c);
    }

    public static L a(C0702m c0702m, ExecutorService executorService, B5 b5, GraphConsumer graphConsumer) {
        return new L(c0702m, AbstractC2151hN.b(c0702m), executorService, b5, graphConsumer, L.a.d);
    }

    public static L a(C0702m c0702m, ExecutorService executorService, B5 b5, D4 d4) {
        return new L(c0702m, AbstractC2151hN.b(c0702m), executorService, b5, d4, L.a.f);
    }
}
