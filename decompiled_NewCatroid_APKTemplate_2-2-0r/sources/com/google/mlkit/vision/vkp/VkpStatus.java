package com.google.mlkit.vision.vkp;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb;
import com.google.mlkit.common.MlKitException;
import java.util.Set;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes10.dex */
public abstract class VkpStatus {

    /* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
    public static abstract class VkpError {
        public abstract int getErrorCode();

        public abstract int getErrorSpaceNumber();
    }

    static VkpStatus zza(MlKitException mlKitException) {
        return new AutoValue_VkpStatus(false, mlKitException, zzlb.zzj());
    }

    public static VkpStatus zzb() {
        return new AutoValue_VkpStatus(true, null, zzlb.zzj());
    }

    public abstract Set<VkpError> getErrors();

    public abstract MlKitException getMlKitException();

    public abstract boolean isSuccess();

    public void throwsMlKitExceptionIfPresent() throws MlKitException {
        MlKitException mlKitException = getMlKitException();
        if (mlKitException != null) {
            throw mlKitException;
        }
    }
}
