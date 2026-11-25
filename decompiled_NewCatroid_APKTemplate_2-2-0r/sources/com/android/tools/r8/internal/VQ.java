package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class VQ {
    static final /* synthetic */ boolean c = true;
    private final ConcurrentHashMap a = new ConcurrentHashMap();
    final /* synthetic */ WQ b;

    VQ(WQ wq) {
        this.b = wq;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0729q2 c0729q2) {
        Boolean bool = (Boolean) this.a.get(c0729q2);
        if (bool != null) {
            return bool.booleanValue();
        }
        com.android.tools.r8.utils.O3.h(new SQ(this, c0729q2)).a(new BiConsumer() { // from class: com.android.tools.r8.internal.VQ$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((UQ) obj).accept((com.android.tools.r8.utils.O3) obj2);
            }
        });
        if (c || this.a.containsKey(c0729q2)) {
            return ((Boolean) this.a.get(c0729q2)).booleanValue();
        }
        throw new AssertionError();
    }
}
