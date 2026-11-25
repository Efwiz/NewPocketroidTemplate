package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Status;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzag implements zzdg<com.google.android.gms.internal.gtm.zzai> {
    final /* synthetic */ zzal zza;

    /* synthetic */ zzag(zzal zzalVar, zzaf zzafVar) {
        this.zza = zzalVar;
    }

    @Override // com.google.android.gms.tagmanager.zzdg
    public final void zza(int i) {
        if (i == 4) {
            this.zza.zzi.zzc();
        }
        synchronized (this.zza) {
            if (!this.zza.isReady()) {
                if (this.zza.zzl != null) {
                    zzal zzalVar = this.zza;
                    zzalVar.setResult(zzalVar.zzl);
                } else {
                    zzal zzalVar2 = this.zza;
                    zzalVar2.setResult(zzalVar2.createFailedResult(Status.RESULT_TIMEOUT));
                }
            }
        }
        this.zza.zzr(this.zza.zzi.zzb());
    }
}
