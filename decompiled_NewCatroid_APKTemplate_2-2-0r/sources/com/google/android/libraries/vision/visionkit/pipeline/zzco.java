package com.google.android.libraries.vision.visionkit.pipeline;

import androidx.work.PeriodicWorkRequest;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzco extends zzanm<zzcp, zzco> implements zzapg {
    private zzco() {
        super(zzcp.zzb);
    }

    public final zzco zza(String str) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzcp.zzd((zzcp) this.zza, "MobileObjectLocalizerV3_1TfLiteClient");
        return this;
    }

    public final zzco zzb(long j) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzcp.zze((zzcp) this.zza, PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS);
        return this;
    }

    /* synthetic */ zzco(zzcm zzcmVar) {
        super(zzcp.zzb);
    }
}
