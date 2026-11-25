package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.li, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2482li extends AbstractC2331jn {
    private final Map a;

    C2482li(ConcurrentHashMap concurrentHashMap) {
        this.a = concurrentHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1476Wj abstractC1476Wj, C2257in c2257in, C0767v2 c0767v2, com.android.tools.r8.graph.Y0 y0) {
        c2257in.a(AbstractC2964s2.a(abstractC1476Wj, c0767v2), abstractC1476Wj.c((AbstractC1476Wj) null, y0));
    }

    @Override // com.android.tools.r8.internal.AbstractC2331jn
    public final AbstractC2331jn a(final AbstractC1476Wj abstractC1476Wj) {
        final C2257in c2257in = new C2257in();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.li$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2482li.a(abstractC1476Wj, c2257in, (C0767v2) obj, (com.android.tools.r8.graph.Y0) obj2);
            }
        });
        return c2257in.a();
    }

    @Override // com.android.tools.r8.internal.AbstractC2331jn
    public final com.android.tools.r8.graph.Y0 a(C0767v2 c0767v2) {
        com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) this.a.get(c0767v2);
        if (y0 != null) {
            return y0;
        }
        throw new C3405y10("Unexpected InitClass instruction for `" + c0767v2.r0() + "`");
    }
}
