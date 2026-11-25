package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzapb {
    private static final zzapa zza;
    private static final zzapa zzb;

    static {
        zzapa zzapaVar;
        try {
            zzapaVar = (zzapa) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzapaVar = null;
        }
        zza = zzapaVar;
        zzb = new zzapa();
    }

    static zzapa zza() {
        return zza;
    }

    static zzapa zzb() {
        return zzb;
    }
}
