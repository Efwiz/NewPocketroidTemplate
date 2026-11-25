package com.google.android.gms.internal.gtm;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzus implements zzwi {
    private static final zzus zza = new zzus();

    private zzus() {
    }

    public static zzus zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.gtm.zzwi
    public final zzwh zzb(Class<?> cls) {
        if (!zzuz.class.isAssignableFrom(cls)) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported message type: ".concat(strValueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzwh) zzuz.zzab(cls.asSubclass(zzuz.class)).zzb(3, null, null);
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(strValueOf2.length() != 0 ? "Unable to get message info for ".concat(strValueOf2) : new String("Unable to get message info for "), e);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzwi
    public final boolean zzc(Class<?> cls) {
        return zzuz.class.isAssignableFrom(cls);
    }
}
