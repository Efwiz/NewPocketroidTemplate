package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.logging.Level;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbj implements zzai {
    private final zzai zza;

    zzbj(zzai zzaiVar) {
        this.zza = zzaiVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final zzaz zzg() {
        return null;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final Object[] zzh() {
        return null;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final Level zzd() {
        return this.zza.zzd();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final long zze() {
        return this.zza.zze();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final zzo zzf() {
        return this.zza.zzf();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final zzan zzk() {
        return this.zza.zzk();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final boolean zzj() {
        return this.zza.zzj();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final Object zzi() {
        zzaz zzazVarZzg = this.zza.zzg();
        if (zzazVarZzg != null) {
            String strValueOf = String.valueOf(zzazVarZzg.zzb());
            return strValueOf.length() != 0 ? "(REDACTED) ".concat(strValueOf) : new String("(REDACTED) ");
        }
        return this.zza.zzi();
    }
}
