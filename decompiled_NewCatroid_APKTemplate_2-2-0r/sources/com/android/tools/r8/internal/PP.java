package com.android.tools.r8.internal;

import java.util.function.Consumer;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class PP {
    private final AbstractC3433yP a;
    private final AbstractC3433yP b;

    public PP(AbstractC3433yP abstractC3433yP, AbstractC3433yP abstractC3433yP2) {
        this.a = abstractC3433yP;
        this.b = abstractC3433yP2;
    }

    public final AbstractC3433yP a() {
        return this.b;
    }

    public final AbstractC3433yP b() {
        return this.a;
    }

    public final Stream c() {
        return Stream.of((Object[]) new AbstractC3433yP[]{this.a, this.b});
    }

    public final void a(Consumer consumer) {
        consumer.accept(this.a);
        consumer.accept(this.b);
    }
}
