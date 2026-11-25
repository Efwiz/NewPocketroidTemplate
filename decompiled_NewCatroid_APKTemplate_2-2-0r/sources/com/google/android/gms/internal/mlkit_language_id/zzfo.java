package com.google.android.gms.internal.mlkit_language_id;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfo implements zzfw {
    private zzfw[] zza;

    zzfo(zzfw... zzfwVarArr) {
        this.zza = zzfwVarArr;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfw
    public final boolean zza(Class<?> cls) {
        for (zzfw zzfwVar : this.zza) {
            if (zzfwVar.zza(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfw
    public final zzfx zzb(Class<?> cls) {
        for (zzfw zzfwVar : this.zza) {
            if (zzfwVar.zza(cls)) {
                return zzfwVar.zzb(cls);
            }
        }
        String strValueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(strValueOf.length() != 0 ? "No factory is available for message type: ".concat(strValueOf) : new String("No factory is available for message type: "));
    }
}
