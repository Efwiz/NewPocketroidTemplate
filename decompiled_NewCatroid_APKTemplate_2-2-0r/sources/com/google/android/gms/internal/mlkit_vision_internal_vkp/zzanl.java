package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzanl implements zzapd {
    private static final zzanl zza = new zzanl();

    private zzanl() {
    }

    public static zzanl zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapd
    public final zzapc zzb(Class<?> cls) {
        if (!zzans.class.isAssignableFrom(cls)) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported message type: ".concat(strValueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzapc) zzans.zzB(cls.asSubclass(zzans.class)).zzb(3, null, null);
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(strValueOf2.length() != 0 ? "Unable to get message info for ".concat(strValueOf2) : new String("Unable to get message info for "), e);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapd
    public final boolean zzc(Class<?> cls) {
        return zzans.class.isAssignableFrom(cls);
    }
}
