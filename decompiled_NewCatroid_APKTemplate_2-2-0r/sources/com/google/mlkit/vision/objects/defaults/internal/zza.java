package com.google.mlkit.vision.objects.defaults.internal;

import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzkn;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzko;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzkp;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzkq;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzlp;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznw;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznz;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzoh;
import com.google.mlkit.common.sdkinternal.ExecutorSelector;
import com.google.mlkit.vision.common.internal.MobileVisionBase;
import com.google.mlkit.vision.common.internal.MultiFlavorDetectorCreator;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes10.dex */
final class zza implements MultiFlavorDetectorCreator.DetectorCreator<List<DetectedObject>, ObjectDetectorOptions> {
    private final zzb zza;
    private final zznw zzb;
    private final ExecutorSelector zzc;

    zza(zzb zzbVar, ExecutorSelector executorSelector) {
        zznw zznwVarZzb = zzoh.zzb("object-detection");
        this.zza = zzbVar;
        this.zzb = zznwVarZzb;
        this.zzc = executorSelector;
    }

    @Override // com.google.mlkit.vision.common.internal.MultiFlavorDetectorCreator.DetectorCreator
    public final /* bridge */ /* synthetic */ MobileVisionBase<List<DetectedObject>> create(MultiFlavorDetectorCreator.DetectorOptions detectorOptions) {
        ObjectDetectorOptions objectDetectorOptions = (ObjectDetectorOptions) detectorOptions;
        zznw zznwVar = this.zzb;
        zzkq zzkqVar = new zzkq();
        zzkqVar.zze(zzkn.TYPE_THICK);
        zzlp zzlpVar = new zzlp();
        zzlpVar.zzb(zzi.zzb(objectDetectorOptions));
        zzlpVar.zzc(zzko.NO_ERROR);
        zzkqVar.zzg(zzlpVar.zzd());
        zznwVar.zzc(zznz.zze(zzkqVar, 1), zzkp.ON_DEVICE_OBJECT_CREATE);
        return new MobileVisionBase<>(this.zza.get(objectDetectorOptions), this.zzc.getExecutorToUse(objectDetectorOptions.getExecutor()));
    }
}
