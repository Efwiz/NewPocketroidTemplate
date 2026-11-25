package com.google.mlkit.vision.objects.defaults.internal;

import android.util.Log;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzar;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzau;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzls;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzlt;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzlv;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzml;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzmm;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzmo;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import com.google.mlkit.vision.vkp.VkpStatus;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes10.dex */
public final class zzi {
    public static zzau<zzmo> zza(VkpStatus vkpStatus) {
        zzar zzarVar = new zzar();
        for (VkpStatus.VkpError vkpError : vkpStatus.getErrors()) {
            zzml zzmlVar = new zzml();
            zzmlVar.zzb(zzmm.zzb(vkpError.getErrorSpaceNumber()));
            zzmlVar.zza(Integer.valueOf(vkpError.getErrorCode()));
            zzarVar.zzc(zzmlVar.zzd());
        }
        return zzarVar.zzd();
    }

    public static zzlv zzb(ObjectDetectorOptions objectDetectorOptions) {
        zzlt zzltVar;
        zzls zzlsVar = new zzls();
        int detectorMode = objectDetectorOptions.getDetectorMode();
        if (detectorMode == 1) {
            zzltVar = zzlt.STREAM;
        } else if (detectorMode != 2) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Unexpected detector mode: ");
            sb.append(detectorMode);
            Log.e("ObjectsLoggingUtils", sb.toString());
            zzltVar = zzlt.MODE_UNSPECIFIED;
        } else {
            zzltVar = zzlt.SINGLE_IMAGE;
        }
        zzlsVar.zzb(zzltVar);
        zzlsVar.zzc(Boolean.valueOf(objectDetectorOptions.isMultipleObjectsEnabled()));
        zzlsVar.zza(Boolean.valueOf(objectDetectorOptions.isClassificationEnabled()));
        return zzlsVar.zze();
    }
}
