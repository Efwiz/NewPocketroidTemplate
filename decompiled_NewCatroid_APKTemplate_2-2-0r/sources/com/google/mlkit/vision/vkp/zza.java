package com.google.mlkit.vision.vkp;

import com.google.mlkit.common.model.LocalModel;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes10.dex */
final class zza extends VkpImageLabelerOptions {
    private final float zza;
    private final int zzb;
    private final LocalModel zzc;

    zza(float f, int i, LocalModel localModel) {
        this.zza = f;
        this.zzb = i;
        this.zzc = localModel;
    }

    public final boolean equals(Object obj) {
        LocalModel localModel;
        if (obj == this) {
            return true;
        }
        if (obj instanceof VkpImageLabelerOptions) {
            VkpImageLabelerOptions vkpImageLabelerOptions = (VkpImageLabelerOptions) obj;
            if (Float.floatToIntBits(this.zza) == Float.floatToIntBits(vkpImageLabelerOptions.zza()) && this.zzb == vkpImageLabelerOptions.zzb() && ((localModel = this.zzc) != null ? localModel.equals(vkpImageLabelerOptions.zzc()) : vkpImageLabelerOptions.zzc() == null)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iFloatToIntBits = (((Float.floatToIntBits(this.zza) ^ 1000003) * 1000003) ^ this.zzb) * 1000003;
        LocalModel localModel = this.zzc;
        return iFloatToIntBits ^ (localModel == null ? 0 : localModel.hashCode());
    }

    public final String toString() {
        float f = this.zza;
        int i = this.zzb;
        String strValueOf = String.valueOf(this.zzc);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 116);
        sb.append("VkpImageLabelerOptions{confidenceThreshold=");
        sb.append(f);
        sb.append(", maxResultCount=");
        sb.append(i);
        sb.append(", customClassifierLocalModel=");
        sb.append(strValueOf);
        sb.append("}");
        return sb.toString();
    }

    @Override // com.google.mlkit.vision.vkp.VkpImageLabelerOptions
    final float zza() {
        return this.zza;
    }

    @Override // com.google.mlkit.vision.vkp.VkpImageLabelerOptions
    final int zzb() {
        return this.zzb;
    }

    @Override // com.google.mlkit.vision.vkp.VkpImageLabelerOptions
    final LocalModel zzc() {
        return this.zzc;
    }
}
