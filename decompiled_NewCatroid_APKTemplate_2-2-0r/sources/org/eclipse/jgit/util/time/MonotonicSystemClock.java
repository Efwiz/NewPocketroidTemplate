package org.eclipse.jgit.util.time;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes14.dex */
public class MonotonicSystemClock implements MonotonicClock {
    private static final AtomicLong before = new AtomicLong();

    private static long nowMicros() {
        AtomicLong atomicLong;
        long j;
        long jMax;
        long micros = TimeUnit.MILLISECONDS.toMicros(System.currentTimeMillis());
        do {
            atomicLong = before;
            j = atomicLong.get();
            jMax = Math.max(1 + j, micros);
        } while (!atomicLong.compareAndSet(j, jMax));
        return jMax;
    }

    @Override // org.eclipse.jgit.util.time.MonotonicClock
    public ProposedTimestamp propose() {
        final long jNowMicros = nowMicros();
        return new ProposedTimestamp() { // from class: org.eclipse.jgit.util.time.MonotonicSystemClock.1
            @Override // org.eclipse.jgit.util.time.ProposedTimestamp
            public void blockUntil(Duration duration) {
            }

            @Override // org.eclipse.jgit.util.time.ProposedTimestamp
            public long read(TimeUnit timeUnit) {
                return timeUnit.convert(jNowMicros, TimeUnit.MICROSECONDS);
            }
        };
    }
}
