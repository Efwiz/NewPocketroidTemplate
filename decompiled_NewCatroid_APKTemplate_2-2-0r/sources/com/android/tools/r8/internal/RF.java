package com.android.tools.r8.internal;

import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class RF extends AbstractC2839qH {
    static final /* synthetic */ boolean c = true;
    private final AtomicInteger b;

    public RF(com.android.tools.r8.graph.o5 o5Var) {
        super(o5Var);
        this.b = new AtomicInteger();
    }

    @Override // com.android.tools.r8.internal.AbstractC2839qH
    public final void a(AbstractC2839qH abstractC2839qH, boolean z) {
        if (!c && a().u1()) {
            throw new AssertionError();
        }
        this.b.incrementAndGet();
    }

    public final int c() {
        return this.b.get();
    }

    @Override // com.android.tools.r8.internal.AbstractC2839qH
    public final void a(AbstractC2839qH abstractC2839qH) {
        throw new C3405y10();
    }
}
