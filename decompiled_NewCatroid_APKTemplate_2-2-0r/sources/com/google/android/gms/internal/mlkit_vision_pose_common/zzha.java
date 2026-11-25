package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzha extends zzgv {
    private zzha() {
        super();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgv
    final void zza(Object obj, long j) {
        zzb(obj, j).zzb();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgv
    final <E> void zza(Object obj, Object obj2, long j) {
        zzgm zzgmVarZzb = zzb(obj, j);
        zzgm zzgmVarZzb2 = zzb(obj2, j);
        int size = zzgmVarZzb.size();
        int size2 = zzgmVarZzb2.size();
        if (size > 0 && size2 > 0) {
            if (!zzgmVarZzb.zza()) {
                zzgmVarZzb = zzgmVarZzb.zzb(size2 + size);
            }
            zzgmVarZzb.addAll(zzgmVarZzb2);
        }
        if (size > 0) {
            zzgmVarZzb2 = zzgmVarZzb;
        }
        zziz.zza(obj, j, zzgmVarZzb2);
    }

    private static <E> zzgm<E> zzb(Object obj, long j) {
        return (zzgm) zziz.zzf(obj, j);
    }
}
