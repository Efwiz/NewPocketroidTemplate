package com.google.mlkit.vision.objects.defaults.internal;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.common.internal.LibraryVersion;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzar;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzau;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzfe;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzka;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzkb;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzkf;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzkn;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzko;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzkp;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzkq;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzkz;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzla;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzlv;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzlw;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzlz;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznw;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzny;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznz;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzoh;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzos;
import com.google.android.gms.tasks.Tasks;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.sdkinternal.MLTask;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.CommonConvertUtils;
import com.google.mlkit.vision.common.internal.ImageConvertUtils;
import com.google.mlkit.vision.common.internal.ImageUtils;
import com.google.mlkit.vision.common.internal.VisionImageMetadataParcel;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import com.google.mlkit.vision.objects.defaults.PredefinedCategory;
import com.google.mlkit.vision.vkp.PipelineManager;
import com.google.mlkit.vision.vkp.VkpDetectedObject;
import com.google.mlkit.vision.vkp.VkpObjectDetectorOptions;
import com.google.mlkit.vision.vkp.VkpResults;
import com.google.mlkit.vision.vkp.VkpStatus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes10.dex */
public final class zze extends MLTask<List<DetectedObject>, InputImage> {
    private static final ImageUtils zzb = ImageUtils.getInstance();
    final zzlv zza;
    private final ObjectDetectorOptions zzc;
    private final MlKitContext zzd;
    private final zznw zze;
    private final zzny zzf;
    private final zzos zzg;
    private PipelineManager zzh;
    private boolean zzi;

    zze(MlKitContext mlKitContext, ObjectDetectorOptions objectDetectorOptions) {
        zznw zznwVarZzb = zzoh.zzb("object-detection");
        Preconditions.checkNotNull(mlKitContext, "Context can not be null");
        Preconditions.checkNotNull(objectDetectorOptions, "ObjectDetectorOptions can not be null");
        this.zze = zznwVarZzb;
        this.zzf = zzny.zza(mlKitContext.getApplicationContext());
        this.zzc = objectDetectorOptions;
        this.zzd = mlKitContext;
        this.zza = zzi.zzb(objectDetectorOptions);
        this.zzg = zzos.zzf();
    }

    private final void zze(zzko zzkoVar, VkpStatus vkpStatus, InputImage inputImage, List<DetectedObject> list, boolean z, Boolean bool, long j) {
        long jElapsedRealtime = SystemClock.elapsedRealtime() - j;
        this.zze.zze(new zzd(this, list, jElapsedRealtime, zzkoVar, vkpStatus, z, bool, inputImage), zzkp.ON_DEVICE_OBJECT_INFERENCE);
        zzfe zzfeVar = new zzfe();
        zzfeVar.zza(this.zza);
        zzfeVar.zzb(zzkoVar);
        zzfeVar.zzd(Boolean.valueOf(z));
        zzfeVar.zzc(Boolean.valueOf(!list.isEmpty()));
        this.zze.zzf(zzfeVar.zze(), jElapsedRealtime, zzkp.AGGREGATED_ON_DEVICE_OBJECT_INFERENCE, zzc.zza);
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.zzf.zzc(24310, zzkoVar.zza(), jCurrentTimeMillis - jElapsedRealtime, jCurrentTimeMillis);
    }

    private final void zzf(zzko zzkoVar, VkpStatus vkpStatus, long j) {
        zznw zznwVar = this.zze;
        zzkq zzkqVar = new zzkq();
        zzkqVar.zze(zzkn.TYPE_THICK);
        zzlz zzlzVar = new zzlz();
        zzlzVar.zzd(this.zza);
        zzlzVar.zzg(Long.valueOf(j));
        zzlzVar.zze(zzkoVar);
        zzlzVar.zzf(zzi.zza(vkpStatus));
        zzkqVar.zzi(zzlzVar.zzh());
        zznwVar.zzc(zznz.zzd(zzkqVar), zzkp.ON_DEVICE_OBJECT_LOAD);
    }

    @Override // com.google.mlkit.common.sdkinternal.ModelResource
    public final synchronized void load() throws MlKitException {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        zzos zzosVar = this.zzg;
        String strValueOf = String.valueOf(LibraryVersion.getInstance().getVersion("object-detection"));
        zzosVar.zzk(strValueOf.length() != 0 ? "object-detection:".concat(strValueOf) : new String("object-detection:"));
        try {
            Tasks.await(this.zzg.zzb());
        } catch (InterruptedException | ExecutionException e) {
            String strValueOf2 = String.valueOf(e.getLocalizedMessage());
            Log.e("BundledODTTask", strValueOf2.length() != 0 ? "remoteConfig.loadAndActivate failed: ".concat(strValueOf2) : new String("remoteConfig.loadAndActivate failed: "));
        }
        this.zzi = Boolean.parseBoolean(this.zzg.zzh("vision_object_detection_enable_acceleration"));
        if (Log.isLoggable("BundledODTTask", 4)) {
            boolean z = this.zzi;
            StringBuilder sb = new StringBuilder(41);
            sb.append("isRemoteConfigAccelerationEnabled = ");
            sb.append(z);
            Log.i("BundledODTTask", sb.toString());
        }
        this.zzg.zza(zzos.zza);
        if (this.zzh == null) {
            Context applicationContext = this.zzd.getApplicationContext();
            boolean z2 = true;
            if (this.zzc.getDetectorMode() != 1) {
                z2 = false;
            }
            this.zzh = PipelineManager.newInstanceForObjectDetection(applicationContext, VkpObjectDetectorOptions.from(z2, this.zzc.isMultipleObjectsEnabled(), this.zzc.isClassificationEnabled(), this.zzi, 0.0f, 1, null));
        }
        VkpStatus vkpStatusStart = this.zzh.start();
        if (vkpStatusStart.isSuccess()) {
            zzf(zzko.NO_ERROR, vkpStatusStart, SystemClock.elapsedRealtime() - jElapsedRealtime);
        } else {
            zzf(zzko.NO_VALID_MODEL, vkpStatusStart, SystemClock.elapsedRealtime() - jElapsedRealtime);
            vkpStatusStart.throwsMlKitExceptionIfPresent();
        }
    }

    @Override // com.google.mlkit.common.sdkinternal.ModelResource
    public final synchronized void release() {
        PipelineManager pipelineManager = this.zzh;
        if (pipelineManager != null) {
            pipelineManager.stop();
            this.zzh = null;
        }
        zznw zznwVar = this.zze;
        zzkq zzkqVar = new zzkq();
        zzkqVar.zze(zzkn.TYPE_THICK);
        zznwVar.zzc(zznz.zzd(zzkqVar), zzkp.ON_DEVICE_OBJECT_CLOSE);
    }

    /* JADX WARN: Code duplicated, block: B:44:0x0118  */
    /* JADX WARN: Code duplicated, block: B:75:0x017e  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.google.mlkit.common.sdkinternal.MLTask
    /* JADX INFO: renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public final synchronized List<DetectedObject> run(InputImage inputImage) throws MlKitException {
        ArrayList arrayList;
        byte b;
        String str;
        byte b2;
        Preconditions.checkNotNull(inputImage, "Mobile vision input can not be null");
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (this.zzh == null) {
            Log.e("BundledODTTask", "Object detector is not initialized.");
            return new ArrayList();
        }
        VkpResults vkpResultsProcess = ((PipelineManager) Preconditions.checkNotNull(this.zzh)).process(inputImage.getFormat() == 35 ? InputImage.fromByteBuffer(ImageConvertUtils.getInstance().convertToNv21Buffer(inputImage, true), inputImage.getWidth(), inputImage.getHeight(), inputImage.getRotationDegrees(), 17) : inputImage, new VisionImageMetadataParcel(inputImage.getWidth(), inputImage.getHeight(), 0, SystemClock.elapsedRealtime(), CommonConvertUtils.convertToMVRotation(inputImage.getRotationDegrees())));
        VkpStatus status = vkpResultsProcess.getStatus();
        if (!status.isSuccess()) {
            zze(zzko.UNKNOWN_ERROR, status, inputImage, zzau.zzi(), vkpResultsProcess.isFromColdCall(), vkpResultsProcess.isAccelerated(), jElapsedRealtime);
            status.throwsMlKitExceptionIfPresent();
            return new ArrayList();
        }
        List<VkpDetectedObject> detectedObjects = vkpResultsProcess.getDetectedObjects();
        if (detectedObjects.isEmpty()) {
            arrayList = new ArrayList();
        } else {
            ArrayList arrayList2 = new ArrayList();
            for (VkpDetectedObject vkpDetectedObject : detectedObjects) {
                ArrayList arrayList3 = new ArrayList();
                if (!vkpDetectedObject.getLabels().isEmpty()) {
                    String className = vkpDetectedObject.getLabels().get(0).getClassName();
                    int i = 2;
                    switch (className.hashCode()) {
                        case -584479206:
                            if (className.equals("/g/11g0srqwrg")) {
                                b = 0;
                            } else {
                                b = -1;
                            }
                            break;
                        case -584453279:
                            if (className.equals("/g/11g0srrsqr")) {
                                b = 1;
                            } else {
                                b = -1;
                            }
                            break;
                        case -347133297:
                            if (className.equals("/m/02wbm")) {
                                b = 2;
                            } else {
                                b = -1;
                            }
                            break;
                        case -347049250:
                            if (className.equals("/m/05s2s")) {
                                b = 4;
                            } else {
                                b = -1;
                            }
                            break;
                        case 286374479:
                            if (className.equals("/g/11fhycwtxg")) {
                                b = 3;
                            } else {
                                b = -1;
                            }
                            break;
                        default:
                            b = -1;
                            break;
                    }
                    if (b == 0) {
                        str = PredefinedCategory.HOME_GOOD;
                    } else if (b == 1) {
                        str = PredefinedCategory.FASHION_GOOD;
                    } else if (b == 2) {
                        str = PredefinedCategory.FOOD;
                    } else if (b != 3) {
                        str = b != 4 ? null : PredefinedCategory.PLANT;
                    } else {
                        str = PredefinedCategory.PLACE;
                    }
                    if (str != null) {
                        float score = vkpDetectedObject.getLabels().get(0).getScore();
                        switch (str.hashCode()) {
                            case -958563771:
                                if (str.equals(PredefinedCategory.FASHION_GOOD)) {
                                    b2 = 0;
                                } else {
                                    b2 = -1;
                                }
                                break;
                            case -231354562:
                                if (str.equals(PredefinedCategory.HOME_GOOD)) {
                                    b2 = 1;
                                } else {
                                    b2 = -1;
                                }
                                break;
                            case 2195582:
                                if (str.equals(PredefinedCategory.FOOD)) {
                                    b2 = 2;
                                } else {
                                    b2 = -1;
                                }
                                break;
                            case 77195495:
                                if (str.equals(PredefinedCategory.PLACE)) {
                                    b2 = 3;
                                } else {
                                    b2 = -1;
                                }
                                break;
                            case 77195851:
                                if (str.equals(PredefinedCategory.PLANT)) {
                                    b2 = 4;
                                } else {
                                    b2 = -1;
                                }
                                break;
                            default:
                                b2 = -1;
                                break;
                        }
                        if (b2 == 0) {
                            i = 0;
                        } else if (b2 == 1) {
                            i = 1;
                        } else if (b2 != 2) {
                            if (b2 != 3) {
                                i = 4;
                                if (b2 != 4) {
                                    i = -1;
                                }
                            } else {
                                i = 3;
                            }
                        }
                        arrayList3.add(new DetectedObject.Label(str, score, i));
                    }
                }
                arrayList2.add(new DetectedObject(vkpDetectedObject.getBoundingBox(), vkpDetectedObject.getTrackingId(), arrayList3));
            }
            arrayList = arrayList2;
        }
        zze(zzko.NO_ERROR, status, inputImage, arrayList, vkpResultsProcess.isFromColdCall(), vkpResultsProcess.isAccelerated(), jElapsedRealtime);
        return arrayList;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    final /* synthetic */ zznz zzd(List list, long j, zzko zzkoVar, VkpStatus vkpStatus, boolean z, Boolean bool, InputImage inputImage) {
        zzkb zzkbVar;
        zzla zzlaVar;
        zzar zzarVar = new zzar();
        Iterator it = list.iterator();
        while (true) {
            byte b = -1;
            if (!it.hasNext()) {
                zzkf zzkfVar = new zzkf();
                zzkfVar.zzd(Long.valueOf(j));
                zzkfVar.zze(zzkoVar);
                zzkfVar.zzh(zzi.zza(vkpStatus));
                zzkfVar.zzg(Boolean.valueOf(z));
                zzkfVar.zzb(true);
                zzkfVar.zzc(true);
                zzkfVar.zzi(Boolean.valueOf(this.zzi));
                if (bool != null) {
                    zzkfVar.zzf(bool);
                }
                zzkq zzkqVar = new zzkq();
                zzkqVar.zze(zzkn.TYPE_THICK);
                zzlw zzlwVar = new zzlw();
                zzlwVar.zzg(zzkfVar.zzj());
                ImageUtils imageUtils = zzb;
                int mobileVisionImageFormat = imageUtils.getMobileVisionImageFormat(inputImage);
                int mobileVisionImageSize = imageUtils.getMobileVisionImageSize(inputImage);
                zzka zzkaVar = new zzka();
                if (mobileVisionImageFormat == -1) {
                    zzkbVar = zzkb.BITMAP;
                } else if (mobileVisionImageFormat == 35) {
                    zzkbVar = zzkb.YUV_420_888;
                } else if (mobileVisionImageFormat == 842094169) {
                    zzkbVar = zzkb.YV12;
                } else if (mobileVisionImageFormat != 16) {
                    zzkbVar = mobileVisionImageFormat != 17 ? zzkb.UNKNOWN_FORMAT : zzkb.NV21;
                } else {
                    zzkbVar = zzkb.NV16;
                }
                zzkaVar.zza(zzkbVar);
                zzkaVar.zzb(Integer.valueOf(mobileVisionImageSize));
                zzlwVar.zzf(zzkaVar.zzd());
                zzlwVar.zze(this.zza);
                zzlwVar.zzh(zzarVar.zzd());
                zzkqVar.zzh(zzlwVar.zzi());
                return zznz.zzd(zzkqVar);
            }
            DetectedObject detectedObject = (DetectedObject) it.next();
            zzkz zzkzVar = new zzkz();
            if (!detectedObject.getLabels().isEmpty()) {
                DetectedObject.Label label = detectedObject.getLabels().get(0);
                String text = label.getText();
                switch (text.hashCode()) {
                    case -958563771:
                        if (text.equals(PredefinedCategory.FASHION_GOOD)) {
                            b = 1;
                        }
                        break;
                    case -231354562:
                        if (text.equals(PredefinedCategory.HOME_GOOD)) {
                            b = 0;
                        }
                        break;
                    case 2195582:
                        if (text.equals(PredefinedCategory.FOOD)) {
                            b = 2;
                        }
                        break;
                    case 77195495:
                        if (text.equals(PredefinedCategory.PLACE)) {
                            b = 3;
                        }
                        break;
                    case 77195851:
                        if (text.equals(PredefinedCategory.PLANT)) {
                            b = 4;
                        }
                        break;
                }
                if (b == 0) {
                    zzlaVar = zzla.CATEGORY_HOME_GOOD;
                } else if (b == 1) {
                    zzlaVar = zzla.CATEGORY_FASHION_GOOD;
                } else if (b == 2) {
                    zzlaVar = zzla.CATEGORY_FOOD;
                } else if (b == 3) {
                    zzlaVar = zzla.CATEGORY_PLACE;
                } else if (b != 4) {
                    String strValueOf = String.valueOf(text);
                    Log.e("ObjectsLoggingUtils", strValueOf.length() != 0 ? "Unexpected category: ".concat(strValueOf) : new String("Unexpected category: "));
                    zzlaVar = zzla.CATEGORY_UNKNOWN;
                } else {
                    zzlaVar = zzla.CATEGORY_PLANT;
                }
                zzkzVar.zza(zzlaVar);
                zzkzVar.zzb(Float.valueOf(label.getConfidence()));
            }
            Integer trackingId = detectedObject.getTrackingId();
            if (trackingId != null) {
                zzkzVar.zzc(trackingId);
            }
            zzarVar.zzc(zzkzVar.zze());
        }
    }
}
