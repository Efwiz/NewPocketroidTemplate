package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzer {
    private static final zzeq<?> zza = new zzes();
    private static final zzeq<?> zzb = zzc();

    private static zzeq<?> zzc() {
        try {
            return (zzeq) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    static zzeq<?> zza() {
        return zza;
    }

    static zzeq<?> zzb() {
        zzeq<?> zzeqVar = zzb;
        if (zzeqVar != null) {
            return zzeqVar;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
