package io.netty.util.internal;

import java.util.concurrent.atomic.LongAdder;

/* JADX INFO: loaded from: classes11.dex */
final class LongAdderCounter extends LongAdder implements LongCounter {
    LongAdderCounter() {
    }

    @Override // io.netty.util.internal.LongCounter
    public long value() {
        return longValue();
    }
}
