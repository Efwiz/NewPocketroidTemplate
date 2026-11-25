package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzanf {
    private static final zzand<?> zza = new zzane();
    private static final zzand<?> zzb;

    static {
        zzand<?> zzandVar;
        try {
            zzandVar = (zzand) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzandVar = null;
        }
        zzb = zzandVar;
    }

    static zzand<?> zza() {
        zzand<?> zzandVar = zzb;
        if (zzandVar != null) {
            return zzandVar;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }

    static zzand<?> zzb() {
        return zza;
    }
}
