package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgs {
    private static final zzgq zza = zzc();
    private static final zzgq zzb = new zzgp();

    static zzgq zza() {
        return zza;
    }

    static zzgq zzb() {
        return zzb;
    }

    private static zzgq zzc() {
        try {
            return (zzgq) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
