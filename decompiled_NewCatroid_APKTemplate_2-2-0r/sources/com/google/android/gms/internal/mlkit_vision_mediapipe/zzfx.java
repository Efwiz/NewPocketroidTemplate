package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfx implements zzgf {
    private zzgf[] zza;

    zzfx(zzgf... zzgfVarArr) {
        this.zza = zzgfVarArr;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgf
    public final boolean zza(Class<?> cls) {
        for (zzgf zzgfVar : this.zza) {
            if (zzgfVar.zza(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgf
    public final zzgg zzb(Class<?> cls) {
        for (zzgf zzgfVar : this.zza) {
            if (zzgfVar.zza(cls)) {
                return zzgfVar.zzb(cls);
            }
        }
        String strValueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(strValueOf.length() != 0 ? "No factory is available for message type: ".concat(strValueOf) : new String("No factory is available for message type: "));
    }
}
