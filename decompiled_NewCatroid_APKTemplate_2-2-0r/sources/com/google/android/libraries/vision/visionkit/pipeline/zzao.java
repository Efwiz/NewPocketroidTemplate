package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbaj;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzhb;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zziu;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzao extends zzanm<zzap, zzao> implements zzapg {
    private zzao() {
        super(zzap.zzb);
    }

    public final zzao zza(zzhb zzhbVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzap.zze((zzap) this.zza, zzhbVar.zzt());
        return this;
    }

    public final zzao zzb(zziu zziuVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzap.zzj((zzap) this.zza, zziuVar);
        return this;
    }

    public final zzao zzc(zzbaj zzbajVar) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzap.zzd((zzap) this.zza, zzbajVar);
        return this;
    }

    public final zzao zzd(boolean z) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzap.zzk((zzap) this.zza, true);
        return this;
    }

    public final zzao zze(float f) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzap.zzh((zzap) this.zza, 0.6f);
        return this;
    }

    public final zzao zzf(int i) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzap.zzg((zzap) this.zza, i);
        return this;
    }

    public final zzao zzg(int i) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzap.zzi((zzap) this.zza, 0);
        return this;
    }

    public final zzao zzh(boolean z) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzap.zzf((zzap) this.zza, z);
        return this;
    }

    /* synthetic */ zzao(zzan zzanVar) {
        super(zzap.zzb);
    }
}
