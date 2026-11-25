package com.google.mlkit.vision.face.internal;

import com.google.android.gms.internal.mlkit_vision_face.zziq;
import com.google.android.gms.internal.mlkit_vision_face.zzis;
import com.google.android.gms.internal.mlkit_vision_face.zzjb;
import com.google.android.gms.internal.mlkit_vision_face.zzkq;
import com.google.android.gms.internal.mlkit_vision_face.zzks;
import com.google.android.gms.internal.mlkit_vision_face.zzlc;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.common.sdkinternal.ExecutorSelector;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.MobileVisionBase;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import java.util.List;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes10.dex */
public class FaceDetectorImpl extends MobileVisionBase<List<Face>> implements FaceDetector {
    static final FaceDetectorOptions zzb = new FaceDetectorOptions.Builder().build();

    /* JADX WARN: Illegal instructions before constructor call */
    /* synthetic */ FaceDetectorImpl(zzh zzhVar, ExecutorSelector executorSelector, FaceDetectorOptions faceDetectorOptions, zzc zzcVar) {
        Executor executorToUse = executorSelector.getExecutorToUse(faceDetectorOptions.zzg());
        zzkq zzkqVarZza = zzlc.zza(zzi.zzb());
        super(zzhVar, executorToUse);
        zzis zzisVar = new zzis();
        zzjb zzjbVar = new zzjb();
        zzjbVar.zzc(zzi.zza(faceDetectorOptions));
        zzisVar.zzd(zzjbVar.zzf());
        zzkqVarZza.zzd(zzks.zzc(zzisVar), zziq.ON_DEVICE_FACE_CREATE);
    }

    @Override // com.google.mlkit.vision.face.FaceDetector, com.google.mlkit.vision.common.Detector
    public final Task<List<Face>> process(InputImage inputImage) {
        return super.processBase(inputImage);
    }
}
