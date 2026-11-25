package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzcc<T> {
    private final zzaz zza;
    private int zzb = 0;
    private int zzc = -1;

    public zzcc(zzaz zzazVar) {
        this.zza = (zzaz) zzcj.zza(zzazVar, "context");
    }

    protected abstract void zza(int i, int i2, zzbz zzbzVar);

    protected abstract T zzc();

    public final zzcf zzd() {
        return this.zza.zza();
    }

    public final String zze() {
        return this.zza.zzb();
    }

    public final int zzf() {
        return this.zzc + 1;
    }

    public final void zzb(int i, int i2, zzbz zzbzVar) {
        if (zzbzVar.zza() < 32) {
            this.zzb |= 1 << zzbzVar.zza();
        }
        this.zzc = Math.max(this.zzc, zzbzVar.zza());
        zza(i, i2, zzbzVar);
    }

    public final T zzg() {
        this.zza.zza().zza(this);
        int i = this.zzb;
        if (((i + 1) & i) != 0 || (this.zzc > 31 && i != -1)) {
            throw zzce.zza(String.format("unreferenced arguments [first missing index=%d]", Integer.valueOf(Integer.numberOfTrailingZeros(~i))), this.zza.zzb());
        }
        return zzc();
    }
}
