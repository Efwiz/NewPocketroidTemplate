package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.io.IOException;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzft extends zzfu<zzgd.zzd> {
    zzft() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfu
    final boolean zza(zzhl zzhlVar) {
        return zzhlVar instanceof zzgd.zze;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfu
    final zzfv<zzgd.zzd> zza(Object obj) {
        return ((zzgd.zze) obj).zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfu
    final zzfv<zzgd.zzd> zzb(Object obj) {
        zzgd.zze zzeVar = (zzgd.zze) obj;
        if (zzeVar.zzc.zzc()) {
            zzeVar.zzc = (zzfv) zzeVar.zzc.clone();
        }
        return zzeVar.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfu
    final void zzc(Object obj) {
        zza(obj).zzb();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfu
    final int zza(Map.Entry<?, ?> entry) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfu
    final void zza(zzjn zzjnVar, Map.Entry<?, ?> entry) throws IOException {
        throw new NoSuchMethodError();
    }
}
