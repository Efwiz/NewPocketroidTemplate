package com.google.mlkit.vision.vkp;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkz;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes10.dex */
public abstract class VkpResults {
    public static VkpResults zza(VkpStatus vkpStatus) {
        return new AutoValue_VkpResults(vkpStatus, zzkz.zzi(), zzkz.zzi(), false, null);
    }

    public abstract List<VkpDetectedObject> getDetectedObjects();

    public abstract List<VkpImageLabel> getImageLabels();

    public abstract VkpStatus getStatus();

    public abstract Boolean isAccelerated();

    public abstract boolean isFromColdCall();
}
