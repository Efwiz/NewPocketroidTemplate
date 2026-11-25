package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzco {
    private final int zza;
    private final int zzb;

    public zzco(int i, int i2) {
        zzkk.zzd(i < 32767 && i >= 0);
        zzkk.zzd(i2 < 32767 && i2 >= 0);
        this.zza = i;
        this.zzb = i2;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzco) {
            zzco zzcoVar = (zzco) obj;
            if (this.zza == zzcoVar.zza && this.zzb == zzcoVar.zzb) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.zzb | (this.zza << 16);
    }

    public final String toString() {
        int i = this.zza;
        int i2 = this.zzb;
        StringBuilder sb = new StringBuilder(23);
        sb.append(i);
        sb.append("x");
        sb.append(i2);
        return sb.toString();
    }

    public final int zza() {
        return this.zzb;
    }

    public final int zzb() {
        return this.zza;
    }
}
