package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzafv extends zzaft {
    private final zzbfi zza;
    private final zzbfr zzb;

    zzafv(zzbfi zzbfiVar, zzbfr zzbfrVar) {
        if (zzbfiVar == null) {
            throw new NullPointerException("Null deviceInfo");
        }
        this.zza = zzbfiVar;
        if (zzbfrVar == null) {
            throw new NullPointerException("Null NNAPIInfo");
        }
        this.zzb = zzbfrVar;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzaft) {
            zzaft zzaftVar = (zzaft) obj;
            if (this.zza.equals(zzaftVar.zza()) && this.zzb.equals(zzaftVar.zzb())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb.hashCode();
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        String strValueOf2 = String.valueOf(this.zzb);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 42 + String.valueOf(strValueOf2).length());
        sb.append("AndroidSystemInfo{deviceInfo=");
        sb.append(strValueOf);
        sb.append(", NNAPIInfo=");
        sb.append(strValueOf2);
        sb.append("}");
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaft
    final zzbfi zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaft
    final zzbfr zzb() {
        return this.zzb;
    }
}
