package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhe implements zzhm {
    private zzhm[] zza;

    zzhe(zzhm... zzhmVarArr) {
        this.zza = zzhmVarArr;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhm
    public final boolean zza(Class<?> cls) {
        for (zzhm zzhmVar : this.zza) {
            if (zzhmVar.zza(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhm
    public final zzhj zzb(Class<?> cls) {
        for (zzhm zzhmVar : this.zza) {
            if (zzhmVar.zza(cls)) {
                return zzhmVar.zzb(cls);
            }
        }
        String strValueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(strValueOf.length() != 0 ? "No factory is available for message type: ".concat(strValueOf) : new String("No factory is available for message type: "));
    }
}
