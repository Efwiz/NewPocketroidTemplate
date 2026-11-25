package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzahs {
    private static zzahs zza;

    private zzahs() {
    }

    public static synchronized zzahs zza() {
        if (zza == null) {
            zza = new zzahs();
        }
        return zza;
    }
}
