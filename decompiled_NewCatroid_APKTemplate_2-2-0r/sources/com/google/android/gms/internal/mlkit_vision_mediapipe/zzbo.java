package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbo implements zzbe {
    private final String zza;
    private final boolean zzb;
    private final boolean zzc;
    private final boolean zzd;
    private final boolean zze;
    private volatile zzbn zzf;

    private zzbo(String str, boolean z, boolean z2, boolean z3, boolean z4) {
        this.zza = str;
        this.zzb = z;
        this.zzc = z2;
        this.zzd = z3;
        this.zze = z4;
    }

    public zzbo() {
        this("", true, false, true, false);
    }

    public final zzbo zza(boolean z) {
        return new zzbo(this.zza, this.zzb, this.zzc, false, this.zze);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzbe
    public final zzal zza(String str) {
        if (this.zze && str.contains(".")) {
            return zza();
        }
        return new zzbm(this.zza, str, this.zzb, this.zzc, this.zzd, null);
    }

    private final zzbn zza() {
        zzbn zzbnVar = this.zzf;
        if (zzbnVar == null) {
            synchronized (this) {
                zzbnVar = this.zzf;
                if (zzbnVar == null) {
                    zzbnVar = new zzbn(this.zza, this.zzb, this.zzc, this.zzd, null);
                    this.zzf = zzbnVar;
                }
            }
        }
        return zzbnVar;
    }
}
