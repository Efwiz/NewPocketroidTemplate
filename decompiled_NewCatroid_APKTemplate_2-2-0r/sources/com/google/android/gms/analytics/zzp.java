package com.google.android.gms.analytics;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzp implements ThreadFactory {
    private static final AtomicInteger zza = new AtomicInteger();

    private zzp() {
    }

    /* synthetic */ zzp(zzo zzoVar) {
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        int iIncrementAndGet = zza.incrementAndGet();
        StringBuilder sb = new StringBuilder(23);
        sb.append("measurement-");
        sb.append(iIncrementAndGet);
        return new zzq(runnable, sb.toString());
    }
}
