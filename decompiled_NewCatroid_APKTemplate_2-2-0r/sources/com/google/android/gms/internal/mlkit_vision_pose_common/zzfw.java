package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfw {
    private static final zzfu<?> zza = new zzft();
    private static final zzfu<?> zzb = zzc();

    private static zzfu<?> zzc() {
        try {
            return (zzfu) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    static zzfu<?> zza() {
        return zza;
    }

    static zzfu<?> zzb() {
        zzfu<?> zzfuVar = zzb;
        if (zzfuVar != null) {
            return zzfuVar;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
