package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhk {
    private static final zzhi zza = zzc();
    private static final zzhi zzb = new zzhh();

    static zzhi zza() {
        return zza;
    }

    static zzhi zzb() {
        return zzb;
    }

    private static zzhi zzc() {
        try {
            return (zzhi) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
