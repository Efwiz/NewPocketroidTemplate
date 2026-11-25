package com.google.android.gms.tagmanager;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdu implements zzfi {
    final /* synthetic */ zzdw zza;

    zzdu(zzdw zzdwVar) {
        this.zza = zzdwVar;
    }

    @Override // com.google.android.gms.tagmanager.zzfi
    public final void zza(zzca zzcaVar) {
        long jZza = zzcaVar.zza();
        if (jZza == 0) {
            zzdw.zzi(this.zza, zzcaVar.zzb(), this.zza.zzg.currentTimeMillis());
            return;
        }
        if (jZza + 14400000 < this.zza.zzg.currentTimeMillis()) {
            this.zza.zzl(zzcaVar.zzb());
            long jZzb = zzcaVar.zzb();
            StringBuilder sb = new StringBuilder(47);
            sb.append("Giving up on failed hitId: ");
            sb.append(jZzb);
            zzdh.zzb.zzd(sb.toString());
        }
    }
}
