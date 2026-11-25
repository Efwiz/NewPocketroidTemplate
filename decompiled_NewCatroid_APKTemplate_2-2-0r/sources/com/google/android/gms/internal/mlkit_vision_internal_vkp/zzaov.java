package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaov implements zzapd {
    private final zzapd[] zza;

    zzaov(zzapd... zzapdVarArr) {
        this.zza = zzapdVarArr;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapd
    public final zzapc zzb(Class<?> cls) {
        zzapd[] zzapdVarArr = this.zza;
        for (int i = 0; i < 2; i++) {
            zzapd zzapdVar = zzapdVarArr[i];
            if (zzapdVar.zzc(cls)) {
                return zzapdVar.zzb(cls);
            }
        }
        String strValueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(strValueOf.length() != 0 ? "No factory is available for message type: ".concat(strValueOf) : new String("No factory is available for message type: "));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapd
    public final boolean zzc(Class<?> cls) {
        zzapd[] zzapdVarArr = this.zza;
        for (int i = 0; i < 2; i++) {
            if (zzapdVarArr[i].zzc(cls)) {
                return true;
            }
        }
        return false;
    }
}
