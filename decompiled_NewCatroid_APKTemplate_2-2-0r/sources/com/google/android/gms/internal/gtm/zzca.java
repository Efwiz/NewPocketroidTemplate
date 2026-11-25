package com.google.android.gms.internal.gtm;

import android.content.ComponentName;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzca implements Runnable {
    final /* synthetic */ ComponentName zza;
    final /* synthetic */ zzcb zzb;

    zzca(zzcb zzcbVar, ComponentName componentName) {
        this.zzb = zzcbVar;
        this.zza = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcc.zzb(this.zzb.zza, this.zza);
    }
}
