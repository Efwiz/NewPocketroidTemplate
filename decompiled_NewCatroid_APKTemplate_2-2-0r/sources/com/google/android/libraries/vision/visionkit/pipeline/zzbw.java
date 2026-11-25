package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbw extends zzanm<zzby, zzbw> implements zzapg {
    private zzbw() {
        super(zzby.zzb);
    }

    public final zzbw zza(float f) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzby.zzd((zzby) this.zza, 0.0f);
        return this;
    }

    public final zzbw zzb(int i) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzby.zzf((zzby) this.zza, i);
        return this;
    }

    public final zzbw zzc(boolean z) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzby.zze((zzby) this.zza, false);
        return this;
    }

    public final zzbw zzd(float f) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzby.zzg((zzby) this.zza, 0.2f);
        return this;
    }

    public final zzbw zze(int i) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzby.zzh((zzby) this.zza, 3);
        return this;
    }

    /* synthetic */ zzbw(zzbv zzbvVar) {
        super(zzby.zzb);
    }
}
