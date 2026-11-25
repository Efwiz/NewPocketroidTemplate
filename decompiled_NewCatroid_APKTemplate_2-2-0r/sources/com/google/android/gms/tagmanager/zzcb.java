package com.google.android.gms.tagmanager;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcb implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ zzcc zzc;
    final /* synthetic */ zzcc zzd;

    zzcb(zzcc zzccVar, zzcc zzccVar2, long j, String str, byte[] bArr) {
        this.zzc = zzccVar;
        this.zzd = zzccVar2;
        this.zza = j;
        this.zzb = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzc.zze == null) {
            zzff zzffVarZzg = zzff.zzg();
            zzffVarZzg.zzl(this.zzc.zzf, this.zzd);
            this.zzc.zze = zzffVarZzg.zzf();
        }
        this.zzc.zze.zzb(this.zza, this.zzb);
    }
}
