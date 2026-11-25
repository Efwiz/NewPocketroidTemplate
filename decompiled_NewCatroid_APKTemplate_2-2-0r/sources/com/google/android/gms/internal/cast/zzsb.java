package com.google.android.gms.internal.cast;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzsb extends zzry implements ScheduledExecutorService, zzrx {
    final ScheduledExecutorService zza;

    zzsb(ScheduledExecutorService scheduledExecutorService) {
        super(scheduledExecutorService);
        scheduledExecutorService.getClass();
        this.zza = scheduledExecutorService;
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    public final /* bridge */ /* synthetic */ ScheduledFuture schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        ScheduledExecutorService scheduledExecutorService = this.zza;
        zzse zzseVarZzn = zzse.zzn(runnable, null);
        return new zzrz(zzseVarZzn, scheduledExecutorService.schedule(zzseVarZzn, j, timeUnit));
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    public final /* bridge */ /* synthetic */ ScheduledFuture scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        zzsa zzsaVar = new zzsa(runnable);
        return new zzrz(zzsaVar, this.zza.scheduleAtFixedRate(zzsaVar, j, j2, timeUnit));
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    public final /* bridge */ /* synthetic */ ScheduledFuture scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        zzsa zzsaVar = new zzsa(runnable);
        return new zzrz(zzsaVar, this.zza.scheduleWithFixedDelay(zzsaVar, j, j2, timeUnit));
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    public final /* bridge */ /* synthetic */ ScheduledFuture schedule(Callable callable, long j, TimeUnit timeUnit) {
        zzse zzseVar = new zzse(callable);
        return new zzrz(zzseVar, this.zza.schedule(zzseVar, j, timeUnit));
    }
}
