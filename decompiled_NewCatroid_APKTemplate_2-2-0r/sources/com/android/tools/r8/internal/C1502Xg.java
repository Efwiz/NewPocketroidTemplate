package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1502Xg {
    static final /* synthetic */ boolean c = true;
    private final AbstractC2178hm a;
    private final SU b;

    private C1502Xg(SU su, AbstractC2178hm abstractC2178hm) {
        this.b = su;
        this.a = abstractC2178hm;
    }

    public final void a(Consumer consumer) {
        this.a.values().forEach(consumer);
        consumer.accept(this.b);
    }

    public final C3487zC a(C0767v2 c0767v2) {
        C3487zC c3487zC = (C3487zC) this.a.get(c0767v2);
        if (c || c3487zC != null) {
            return c3487zC;
        }
        throw new AssertionError();
    }

    public final SU a() {
        return this.b;
    }
}
