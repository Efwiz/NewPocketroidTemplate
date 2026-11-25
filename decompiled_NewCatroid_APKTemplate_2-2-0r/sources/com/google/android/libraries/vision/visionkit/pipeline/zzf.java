package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzf extends zzanm<zzg, zzf> implements zzapg {
    private zzf() {
        super(zzg.zzb);
    }

    public final zzf zza(boolean z) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzg.zzd((zzg) this.zza, true);
        return this;
    }

    public final zzf zzb(String str) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzg.zze((zzg) this.zza, str);
        return this;
    }

    /* synthetic */ zzf(zze zzeVar) {
        super(zzg.zzb);
    }
}
