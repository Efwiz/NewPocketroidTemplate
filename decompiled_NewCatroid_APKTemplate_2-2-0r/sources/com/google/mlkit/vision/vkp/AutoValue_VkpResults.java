package com.google.mlkit.vision.vkp;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes10.dex */
final class AutoValue_VkpResults extends VkpResults {
    private final VkpStatus zza;
    private final List<VkpDetectedObject> zzb;
    private final List<VkpImageLabel> zzc;
    private final boolean zzd;
    private final Boolean zze;

    AutoValue_VkpResults(VkpStatus vkpStatus, List<VkpDetectedObject> list, List<VkpImageLabel> list2, boolean z, Boolean bool) {
        if (vkpStatus == null) {
            throw new NullPointerException("Null getStatus");
        }
        this.zza = vkpStatus;
        if (list == null) {
            throw new NullPointerException("Null getDetectedObjects");
        }
        this.zzb = list;
        if (list2 == null) {
            throw new NullPointerException("Null getImageLabels");
        }
        this.zzc = list2;
        this.zzd = z;
        this.zze = bool;
    }

    public final boolean equals(Object obj) {
        Boolean bool;
        if (obj == this) {
            return true;
        }
        if (obj instanceof VkpResults) {
            VkpResults vkpResults = (VkpResults) obj;
            if (this.zza.equals(vkpResults.getStatus()) && this.zzb.equals(vkpResults.getDetectedObjects()) && this.zzc.equals(vkpResults.getImageLabels()) && this.zzd == vkpResults.isFromColdCall() && ((bool = this.zze) != null ? bool.equals(vkpResults.isAccelerated()) : vkpResults.isAccelerated() == null)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.mlkit.vision.vkp.VkpResults
    public List<VkpDetectedObject> getDetectedObjects() {
        return this.zzb;
    }

    @Override // com.google.mlkit.vision.vkp.VkpResults
    public List<VkpImageLabel> getImageLabels() {
        return this.zzc;
    }

    @Override // com.google.mlkit.vision.vkp.VkpResults
    public VkpStatus getStatus() {
        return this.zza;
    }

    public final int hashCode() {
        int iHashCode = (((((((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb.hashCode()) * 1000003) ^ this.zzc.hashCode()) * 1000003) ^ (true != this.zzd ? XKeySym.XK_kana_YU : XKeySym.XK_kana_MA)) * 1000003;
        Boolean bool = this.zze;
        return iHashCode ^ (bool == null ? 0 : bool.hashCode());
    }

    @Override // com.google.mlkit.vision.vkp.VkpResults
    public Boolean isAccelerated() {
        return this.zze;
    }

    @Override // com.google.mlkit.vision.vkp.VkpResults
    public boolean isFromColdCall() {
        return this.zzd;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        String strValueOf2 = String.valueOf(this.zzb);
        String strValueOf3 = String.valueOf(this.zzc);
        boolean z = this.zzd;
        String strValueOf4 = String.valueOf(this.zze);
        int length = String.valueOf(strValueOf).length();
        int length2 = String.valueOf(strValueOf2).length();
        StringBuilder sb = new StringBuilder(length + 98 + length2 + String.valueOf(strValueOf3).length() + String.valueOf(strValueOf4).length());
        sb.append("VkpResults{getStatus=");
        sb.append(strValueOf);
        sb.append(", getDetectedObjects=");
        sb.append(strValueOf2);
        sb.append(", getImageLabels=");
        sb.append(strValueOf3);
        sb.append(", isFromColdCall=");
        sb.append(z);
        sb.append(", isAccelerated=");
        sb.append(strValueOf4);
        sb.append("}");
        return sb.toString();
    }
}
