package com.google.mlkit.vision.vkp;

import android.graphics.Rect;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes10.dex */
final class AutoValue_VkpDetectedObject extends VkpDetectedObject {
    private final Rect zza;
    private final Integer zzb;
    private final List<VkpImageLabel> zzc;

    AutoValue_VkpDetectedObject(Rect rect, Integer num, List<VkpImageLabel> list) {
        this.zza = rect;
        this.zzb = num;
        if (list == null) {
            throw new NullPointerException("Null labels");
        }
        this.zzc = list;
    }

    public final boolean equals(Object obj) {
        Integer num;
        if (obj == this) {
            return true;
        }
        if (obj instanceof VkpDetectedObject) {
            VkpDetectedObject vkpDetectedObject = (VkpDetectedObject) obj;
            if (this.zza.equals(vkpDetectedObject.getBoundingBox()) && ((num = this.zzb) != null ? num.equals(vkpDetectedObject.getTrackingId()) : vkpDetectedObject.getTrackingId() == null) && this.zzc.equals(vkpDetectedObject.getLabels())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.mlkit.vision.vkp.VkpDetectedObject
    public Rect getBoundingBox() {
        return this.zza;
    }

    @Override // com.google.mlkit.vision.vkp.VkpDetectedObject
    public List<VkpImageLabel> getLabels() {
        return this.zzc;
    }

    @Override // com.google.mlkit.vision.vkp.VkpDetectedObject
    public Integer getTrackingId() {
        return this.zzb;
    }

    public final int hashCode() {
        int iHashCode = (this.zza.hashCode() ^ 1000003) * 1000003;
        Integer num = this.zzb;
        return ((iHashCode ^ (num == null ? 0 : num.hashCode())) * 1000003) ^ this.zzc.hashCode();
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        String strValueOf2 = String.valueOf(this.zzb);
        String strValueOf3 = String.valueOf(this.zzc);
        int length = String.valueOf(strValueOf).length();
        StringBuilder sb = new StringBuilder(length + 53 + String.valueOf(strValueOf2).length() + String.valueOf(strValueOf3).length());
        sb.append("VkpDetectedObject{boundingBox=");
        sb.append(strValueOf);
        sb.append(", trackingId=");
        sb.append(strValueOf2);
        sb.append(", labels=");
        sb.append(strValueOf3);
        sb.append("}");
        return sb.toString();
    }
}
