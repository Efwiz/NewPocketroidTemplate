package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgd {
    private static final zzgb zza = zzc();
    private static final zzgb zzb = new zzge();

    static zzgb zza() {
        return zza;
    }

    static zzgb zzb() {
        return zzb;
    }

    private static zzgb zzc() {
        try {
            return (zzgb) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
