package com.google.mlkit.vision.vkp;

import com.gaurav.avnc.vnc.XKeySym;
import com.google.mlkit.common.MlKitException;
import java.util.Set;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes10.dex */
final class AutoValue_VkpStatus extends VkpStatus {
    private final boolean zza;
    private final MlKitException zzb;
    private final Set<VkpStatus.VkpError> zzc;

    AutoValue_VkpStatus(boolean z, MlKitException mlKitException, Set<VkpStatus.VkpError> set) {
        this.zza = z;
        this.zzb = mlKitException;
        if (set == null) {
            throw new NullPointerException("Null errors");
        }
        this.zzc = set;
    }

    public final boolean equals(Object obj) {
        MlKitException mlKitException;
        if (obj == this) {
            return true;
        }
        if (obj instanceof VkpStatus) {
            VkpStatus vkpStatus = (VkpStatus) obj;
            if (this.zza == vkpStatus.isSuccess() && ((mlKitException = this.zzb) != null ? mlKitException.equals(vkpStatus.getMlKitException()) : vkpStatus.getMlKitException() == null) && this.zzc.equals(vkpStatus.getErrors())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.mlkit.vision.vkp.VkpStatus
    public Set<VkpStatus.VkpError> getErrors() {
        return this.zzc;
    }

    @Override // com.google.mlkit.vision.vkp.VkpStatus
    public MlKitException getMlKitException() {
        return this.zzb;
    }

    public final int hashCode() {
        int i = ((true != this.zza ? XKeySym.XK_kana_YU : XKeySym.XK_kana_MA) ^ 1000003) * 1000003;
        MlKitException mlKitException = this.zzb;
        return ((i ^ (mlKitException == null ? 0 : mlKitException.hashCode())) * 1000003) ^ this.zzc.hashCode();
    }

    @Override // com.google.mlkit.vision.vkp.VkpStatus
    public boolean isSuccess() {
        return this.zza;
    }

    public final String toString() {
        boolean z = this.zza;
        String strValueOf = String.valueOf(this.zzb);
        String strValueOf2 = String.valueOf(this.zzc);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 50 + String.valueOf(strValueOf2).length());
        sb.append("VkpStatus{success=");
        sb.append(z);
        sb.append(", mlKitException=");
        sb.append(strValueOf);
        sb.append(", errors=");
        sb.append(strValueOf2);
        sb.append("}");
        return sb.toString();
    }
}
