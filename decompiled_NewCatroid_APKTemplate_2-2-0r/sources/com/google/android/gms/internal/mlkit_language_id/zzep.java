package com.google.android.gms.internal.mlkit_language_id;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzep implements zzfw {
    private static final zzep zza = new zzep();

    private zzep() {
    }

    public static zzep zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfw
    public final boolean zza(Class<?> cls) {
        return zzeo.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfw
    public final zzfx zzb(Class<?> cls) {
        if (!zzeo.class.isAssignableFrom(cls)) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported message type: ".concat(strValueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzfx) zzeo.zza(cls.asSubclass(zzeo.class)).zza(zzeo.zze.zzc, (Object) null, (Object) null);
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(strValueOf2.length() != 0 ? "Unable to get message info for ".concat(strValueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
