package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfc extends zzanm<zzfd, zzfc> implements zzapg {
    private zzfc() {
        super(zzfd.zzb);
    }

    public final zzfc zza(boolean z) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzfd.zzd((zzfd) this.zza, true);
        return this;
    }

    public final zzfc zzb(int i) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzfd.zze((zzfd) this.zza, i);
        return this;
    }

    /* synthetic */ zzfc(zzfb zzfbVar) {
        super(zzfd.zzb);
    }
}
