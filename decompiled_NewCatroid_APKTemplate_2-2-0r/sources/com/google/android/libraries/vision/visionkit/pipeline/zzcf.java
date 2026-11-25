package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcf extends zzanm<zzcg, zzcf> implements zzapg {
    private zzcf() {
        super(zzcg.zzb);
    }

    public final zzcf zza(zzf zzfVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzcg.zzf((zzcg) this.zza, zzfVar.zzt());
        return this;
    }

    public final zzcf zzb(zzi zziVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzcg.zzh((zzcg) this.zza, zziVar.zzt());
        return this;
    }

    public final zzcf zzc(zzdm zzdmVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzcg.zzg((zzcg) this.zza, zzdmVar.zzt());
        return this;
    }

    public final zzcf zzd(zzfc zzfcVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzcg.zze((zzcg) this.zza, zzfcVar.zzt());
        return this;
    }

    /* synthetic */ zzcf(zzce zzceVar) {
        super(zzcg.zzb);
    }
}
