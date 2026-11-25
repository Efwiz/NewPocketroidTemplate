package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zznk {
    private final zzmz zza;
    private final String zzb;
    private final String zzc;
    private final long zzd;

    public zznk(zzmz zzmzVar, String str, String str2, long j) {
        this.zza = zzmzVar;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = j;
    }

    public final long zza() {
        return this.zzd;
    }

    public final zzmz zzb() {
        return this.zza;
    }

    public final String zzc() {
        return this.zzb;
    }

    public final String zzd() {
        return this.zzc;
    }

    public final boolean zze() {
        return System.currentTimeMillis() >= this.zzd;
    }
}
