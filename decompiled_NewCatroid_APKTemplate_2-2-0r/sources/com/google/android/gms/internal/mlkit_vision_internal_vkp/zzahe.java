package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzahe implements zzagv {
    private final zzqn zza;

    private zzahe(zzqn zzqnVar, int i) {
        this.zza = zzqnVar;
        zzahs.zza();
    }

    public static zzagv zzf(zzqn zzqnVar) {
        return new zzahe(zzqnVar, 0);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagv
    public final int zza() {
        return 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagv
    public final zzagv zzb(zzadr zzadrVar) {
        this.zza.zzc(zzqe.zza(zzadrVar.zza()));
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagv
    public final zzagv zzc(zzafg zzafgVar) {
        zzafi zzafiVarZzl = zzafgVar.zzl();
        zzuv zzuvVarZza = zzuw.zza();
        zzuvVarZza.zzc(zzkq.zzb(zzafiVarZzl.zzf()));
        zzuvVarZza.zzd(zzkq.zzb(zzafiVarZzl.zzg()));
        zzuvVarZza.zzf(zzkq.zzb(null));
        zzuvVarZza.zzk(zzkq.zzb(zzafiVarZzl.zzi()));
        zzuvVarZza.zzl(zzkq.zzb(zzafiVarZzl.zzj()));
        zzuvVarZza.zzg(zzkq.zzb(null));
        zzuvVarZza.zzb(zzkq.zzb(null));
        zzuvVarZza.zzj(zzkq.zzb(zzafiVarZzl.zzh()));
        zzuvVarZza.zzh(Boolean.TRUE.equals(zzafiVarZzl.zzb()));
        zzuvVarZza.zzi(Boolean.TRUE.equals(zzafiVarZzl.zzd()));
        Integer numZze = zzafiVarZzl.zze();
        zzuvVarZza.zze(numZze == null ? 0 : numZze.intValue());
        zzkz<String> zzkzVarZza = zzafiVarZzl.zza();
        if (zzkzVarZza != null) {
            zzuvVarZza.zza(zzkzVarZza);
        }
        this.zza.zzb(zzuvVarZza);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagv
    public final String zzd() {
        zzuw zzuwVarZzd = ((zzqo) this.zza.zzt()).zzd();
        return !zzuwVarZzd.zze().isEmpty() ? zzuwVarZzd.zze() : "NA";
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagv
    public final byte[] zze(int i, boolean z) {
        return ((zzqo) this.zza.zzt()).zzw();
    }
}
