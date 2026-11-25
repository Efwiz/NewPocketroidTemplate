package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaup;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzc extends zzanm<zzd, zzc> implements zzapg {
    private zzc() {
        super(zzd.zzb);
    }

    public final zzc zza(Iterable<? extends zzaup> iterable) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzd.zzg((zzd) this.zza, iterable);
        return this;
    }

    /* synthetic */ zzc(zzb zzbVar) {
        super(zzd.zzb);
    }
}
