package com.google.mlkit.vision.face.internal;

import com.google.android.gms.internal.mlkit_vision_face.zzhy;
import com.google.android.gms.internal.mlkit_vision_face.zzhz;
import com.google.android.gms.internal.mlkit_vision_face.zzia;
import com.google.android.gms.internal.mlkit_vision_face.zzib;
import com.google.android.gms.internal.mlkit_vision_face.zzic;
import com.google.android.gms.internal.mlkit_vision_face.zzid;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes10.dex */
public final class zzi {
    static final AtomicReference<String> zza = new AtomicReference<>();

    public static zzid zza(FaceDetectorOptions faceDetectorOptions) {
        zzib zzibVar;
        zzhz zzhzVar;
        zzic zzicVar;
        zzia zziaVar;
        zzhy zzhyVar = new zzhy();
        int iZza = faceDetectorOptions.zza();
        if (iZza != 1) {
            zzibVar = iZza != 2 ? zzib.UNKNOWN_LANDMARKS : zzib.ALL_LANDMARKS;
        } else {
            zzibVar = zzib.NO_LANDMARKS;
        }
        zzhyVar.zza(zzibVar);
        int iZzc = faceDetectorOptions.zzc();
        if (iZzc != 1) {
            zzhzVar = iZzc != 2 ? zzhz.UNKNOWN_CLASSIFICATIONS : zzhz.ALL_CLASSIFICATIONS;
        } else {
            zzhzVar = zzhz.NO_CLASSIFICATIONS;
        }
        zzhyVar.zzb(zzhzVar);
        int iZzd = faceDetectorOptions.zzd();
        if (iZzd != 1) {
            zzicVar = iZzd != 2 ? zzic.UNKNOWN_PERFORMANCE : zzic.ACCURATE;
        } else {
            zzicVar = zzic.FAST;
        }
        zzhyVar.zzc(zzicVar);
        int iZzb = faceDetectorOptions.zzb();
        if (iZzb != 1) {
            zziaVar = iZzb != 2 ? zzia.UNKNOWN_CONTOURS : zzia.ALL_CONTOURS;
        } else {
            zziaVar = zzia.NO_CONTOURS;
        }
        zzhyVar.zzd(zziaVar);
        zzhyVar.zze(Boolean.valueOf(faceDetectorOptions.zze()));
        zzhyVar.zzf(Float.valueOf(faceDetectorOptions.zzf()));
        return zzhyVar.zzg();
    }

    public static String zzb() {
        AtomicReference<String> atomicReference = zza;
        if (atomicReference.get() != null) {
            return atomicReference.get();
        }
        String str = true != zza.zzd(MlKitContext.getInstance().getApplicationContext()) ? "play-services-mlkit-face-detection" : "face-detection";
        atomicReference.set(str);
        return str;
    }
}
