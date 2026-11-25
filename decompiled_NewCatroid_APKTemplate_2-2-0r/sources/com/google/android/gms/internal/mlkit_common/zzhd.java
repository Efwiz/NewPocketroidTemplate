package com.google.android.gms.internal.mlkit_common;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhd {
    private zzhp zza;
    private Long zzb;
    private zzgy zzc;
    private Long zzd;
    private zzhe zze;
    private Long zzf;

    public final zzhd zzb(Long l) {
        this.zzf = l;
        return this;
    }

    public final zzhd zzc(zzhe zzheVar) {
        this.zze = zzheVar;
        return this;
    }

    public final zzhd zzd(zzgy zzgyVar) {
        this.zzc = zzgyVar;
        return this;
    }

    public final zzhd zze(Long l) {
        this.zzd = Long.valueOf(l.longValue() & Long.MAX_VALUE);
        return this;
    }

    public final zzhd zzf(zzhp zzhpVar) {
        this.zza = zzhpVar;
        return this;
    }

    public final zzhd zzg(Long l) {
        this.zzb = Long.valueOf(l.longValue() & Long.MAX_VALUE);
        return this;
    }

    public final zzhg zzi() {
        return new zzhg(this, null);
    }
}
