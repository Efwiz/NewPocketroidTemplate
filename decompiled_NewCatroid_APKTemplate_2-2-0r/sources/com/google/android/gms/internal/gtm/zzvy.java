package com.google.android.gms.internal.gtm;

import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzvy {
    private static final zzvy zza = new zzvu(null);
    private static final zzvy zzb = new zzvw(0 == true ? 1 : 0);

    /* synthetic */ zzvy(zzvx zzvxVar) {
    }

    static zzvy zzd() {
        return zza;
    }

    static zzvy zze() {
        return zzb;
    }

    abstract <L> List<L> zza(Object obj, long j);

    abstract void zzb(Object obj, long j);

    abstract <L> void zzc(Object obj, Object obj2, long j);
}
