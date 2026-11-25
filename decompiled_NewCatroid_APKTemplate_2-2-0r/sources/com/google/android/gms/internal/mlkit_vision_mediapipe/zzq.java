package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzq extends zzo {
    private final String zzb;
    private final String zzc;
    private final int zzd;
    private final String zze;
    private int zzf;

    private zzq(String str, String str2, int i, String str3) {
        this.zzf = 0;
        this.zzb = (String) zzcj.zza(str, "class name");
        this.zzc = (String) zzcj.zza(str2, "method name");
        this.zzd = i;
        this.zze = str3;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzo
    public final String zza() {
        return this.zzb.replace('/', '.');
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzo
    public final String zzb() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzo
    public final int zzc() {
        return this.zzd & 65535;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzo
    public final String zzd() {
        return this.zze;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzq) {
            zzq zzqVar = (zzq) obj;
            if (this.zzb.equals(zzqVar.zzb) && this.zzc.equals(zzqVar.zzc) && this.zzd == zzqVar.zzd) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        if (this.zzf == 0) {
            this.zzf = ((((this.zzb.hashCode() + 4867) * 31) + this.zzc.hashCode()) * 31) + this.zzd;
        }
        return this.zzf;
    }

    /* synthetic */ zzq(String str, String str2, int i, String str3, zzn zznVar) {
        this(str, str2, i, str3);
    }
}
