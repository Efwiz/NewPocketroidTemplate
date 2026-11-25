package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhw {
    private static final zzhu zza = zzc();
    private static final zzhu zzb = new zzht();

    static zzhu zza() {
        return zza;
    }

    static zzhu zzb() {
        return zzb;
    }

    private static zzhu zzc() {
        try {
            return (zzhu) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
