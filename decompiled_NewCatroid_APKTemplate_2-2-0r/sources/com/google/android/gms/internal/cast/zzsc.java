package com.google.android.gms.internal.cast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzsc {
    public static zzrx zza(ExecutorService executorService) {
        if (executorService instanceof zzrx) {
            return (zzrx) executorService;
        }
        return executorService instanceof ScheduledExecutorService ? new zzsb((ScheduledExecutorService) executorService) : new zzry(executorService);
    }
}
