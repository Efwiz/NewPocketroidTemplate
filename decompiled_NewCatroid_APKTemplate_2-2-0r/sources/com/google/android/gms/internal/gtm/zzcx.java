package com.google.android.gms.internal.gtm;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcx extends zzbs {
    zzcx(zzbv zzbvVar) {
        super(zzbvVar);
    }

    public final zzba zza() {
        zzW();
        return zzq().zzd();
    }

    public final String zzb() {
        zzW();
        zzba zzbaVarZza = zza();
        int i = zzbaVarZza.zza;
        int i2 = zzbaVarZza.zzb;
        StringBuilder sb = new StringBuilder(23);
        sb.append(i);
        sb.append("x");
        sb.append(i2);
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
    }
}
