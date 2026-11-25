package com.google.android.gms.cast.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzt implements Runnable {
    final /* synthetic */ zzw zza;
    final /* synthetic */ zza zzb;

    zzt(zzv zzvVar, zzw zzwVar, zza zzaVar) {
        this.zza = zzwVar;
        this.zzb = zzaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzw.zzH(this.zza, this.zzb);
    }
}
