package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzac<K, V> extends zzah<Map.Entry<K, V>> {
    private final /* synthetic */ zzaa zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzac(zzaa zzaaVar) {
        super(zzaaVar, null);
        this.zza = zzaaVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzah
    final /* synthetic */ Object zza(int i) {
        return new zzaj(this.zza, i);
    }
}
