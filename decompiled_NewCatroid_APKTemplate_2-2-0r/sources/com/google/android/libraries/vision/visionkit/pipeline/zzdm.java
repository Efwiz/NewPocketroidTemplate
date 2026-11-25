package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarq;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzir;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzjv;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdm extends zzanm<zzdp, zzdm> implements zzapg {
    private zzdm() {
        super(zzdp.zzb);
    }

    public final zzdm zza(zzir zzirVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzdp.zzg((zzdp) this.zza, zzirVar.zzt());
        return this;
    }

    public final zzdm zzb(zzao zzaoVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzdp.zzh((zzdp) this.zza, zzaoVar.zzt());
        return this;
    }

    public final zzdm zzc(zzarq zzarqVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzdp.zzd((zzdp) this.zza, zzarqVar);
        return this;
    }

    public final zzdm zzd(String str) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzdp.zze((zzdp) this.zza, str);
        return this;
    }

    public final zzdm zze(String str) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzdp.zzf((zzdp) this.zza, str);
        return this;
    }

    public final zzdm zzf(boolean z) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzdp.zzi((zzdp) this.zza, true);
        return this;
    }

    public final zzdm zzg(zzbw zzbwVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzdp.zzk((zzdp) this.zza, zzbwVar.zzt());
        return this;
    }

    public final zzdm zzh(zzdx zzdxVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzdp.zzl((zzdp) this.zza, zzdxVar);
        return this;
    }

    public final zzdm zzi(zzjv zzjvVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzdp.zzj((zzdp) this.zza, zzjvVar);
        return this;
    }

    public final zzdm zzj(int i) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzdp.zzm((zzdp) this.zza, i);
        return this;
    }

    /* synthetic */ zzdm(zzdl zzdlVar) {
        super(zzdp.zzb);
    }
}
