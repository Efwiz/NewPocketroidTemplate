package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgb implements zzhm {
    private static final zzgb zza = new zzgb();

    private zzgb() {
    }

    public static zzgb zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhm
    public final boolean zza(Class<?> cls) {
        return zzgd.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhm
    public final zzhj zzb(Class<?> cls) {
        if (!zzgd.class.isAssignableFrom(cls)) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported message type: ".concat(strValueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzhj) zzgd.zza(cls.asSubclass(zzgd.class)).zza(zzgd.zzf.zzc, (Object) null, (Object) null);
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(strValueOf2.length() != 0 ? "Unable to get message info for ".concat(strValueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
