package com.google.android.gms.internal.gtm;

import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbp implements Callable<Void> {
    final /* synthetic */ zzbq zza;

    zzbp(zzbq zzbqVar) {
        this.zza = zzbqVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Void call() throws Exception {
        this.zza.zza.zzac();
        return null;
    }
}
