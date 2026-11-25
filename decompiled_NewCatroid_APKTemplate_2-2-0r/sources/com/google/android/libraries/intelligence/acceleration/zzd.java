package com.google.android.libraries.intelligence.acceleration;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanc;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaoe;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzatw;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaux;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzd {
    public static zzatw zza() {
        byte[] bArrGetDeviceInfo = AndroidSystemDetectionJNI.GetDeviceInfo();
        if (bArrGetDeviceInfo == null) {
            return null;
        }
        try {
            return zzatw.zze(bArrGetDeviceInfo, zzanc.zza());
        } catch (zzaoe e) {
            throw new zzc(zzatw.class.getName(), e);
        }
    }

    public static zzaux zzb() {
        byte[] bArrGetNNAPIInfo = AndroidSystemDetectionJNI.GetNNAPIInfo();
        if (bArrGetNNAPIInfo == null) {
            return null;
        }
        try {
            return zzaux.zze(bArrGetNNAPIInfo, zzanc.zza());
        } catch (zzaoe e) {
            throw new zzc(zzaux.class.getName(), e);
        }
    }
}
