package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzat<E> extends zzj<E> {
    private final zzar<E> zza;

    zzat(zzar<E> zzarVar, int i) {
        super(zzarVar.size(), i);
        this.zza = zzarVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzj
    protected final E zza(int i) {
        return this.zza.get(i);
    }
}
