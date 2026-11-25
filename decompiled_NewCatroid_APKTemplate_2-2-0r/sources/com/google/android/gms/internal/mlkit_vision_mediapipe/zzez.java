package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzez implements zzgf {
    private static final zzez zza = new zzez();

    private zzez() {
    }

    public static zzez zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgf
    public final boolean zza(Class<?> cls) {
        return zzfb.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgf
    public final zzgg zzb(Class<?> cls) {
        if (!zzfb.class.isAssignableFrom(cls)) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported message type: ".concat(strValueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzgg) zzfb.zza(cls.asSubclass(zzfb.class)).zza(zzfb.zzf.zzc, (Object) null, (Object) null);
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(strValueOf2.length() != 0 ? "Unable to get message info for ".concat(strValueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
