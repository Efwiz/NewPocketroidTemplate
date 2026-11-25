package com.google.mlkit.vision.vkp;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import androidx.work.PeriodicWorkRequest;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzadr;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzafo;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahd;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahe;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahn;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarq;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaue;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaui;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzauo;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaup;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzco;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzft;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzfu;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzgl;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzgw;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzhk;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzhl;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzip;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zziu;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzjj;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzjm;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkw;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzla;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzn;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzqn;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzqo;
import com.google.android.libraries.intelligence.acceleration.ProcessStateObserver;
import com.google.android.libraries.vision.visionkit.pipeline.PipelineException;
import com.google.android.libraries.vision.visionkit.pipeline.zzag;
import com.google.android.libraries.vision.visionkit.pipeline.zzam;
import com.google.android.libraries.vision.visionkit.pipeline.zzbg;
import com.google.android.libraries.vision.visionkit.pipeline.zzcf;
import com.google.android.libraries.vision.visionkit.pipeline.zzcg;
import com.google.android.libraries.vision.visionkit.pipeline.zzda;
import com.google.android.libraries.vision.visionkit.pipeline.zzdm;
import com.google.android.libraries.vision.visionkit.pipeline.zzf;
import com.google.android.libraries.vision.visionkit.pipeline.zzg;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.internal.model.ModelUtils;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.common.sdkinternal.Constants;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.ImageConvertUtils;
import com.google.mlkit.vision.common.internal.VisionImageMetadataParcel;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes10.dex */
public class PipelineManager {
    private final Context zza;
    private final zzc zzb;
    private final boolean zzc;
    private final zzahd zzg;
    private final zzafo zzh;
    private zzd zzi;
    private boolean zzj;
    private final List<zzaup> zzd = new ArrayList();
    private final List<AssetFileDescriptor> zze = new ArrayList();
    private final com.google.android.libraries.intelligence.acceleration.zzb zzf = new com.google.android.libraries.intelligence.acceleration.zzb(10);
    private boolean zzk = true;
    private long zzl = -1;

    static {
        System.loadLibrary("mlkitcommonpipeline");
    }

    PipelineManager(Context context, zzc zzcVar, boolean z, zzahd zzahdVar, zzafo zzafoVar) {
        this.zza = context;
        this.zzb = zzcVar;
        this.zzc = z;
        this.zzg = zzahdVar;
        this.zzh = zzafoVar;
    }

    public static PipelineManager newInstanceForImageLabeling(Context context, VkpImageLabelerOptions vkpImageLabelerOptions) {
        return new PipelineManager(context, vkpImageLabelerOptions, false, zzahn.zzb("vision-internal-vkp"), (zzafo) Preconditions.checkNotNull(zzafo.zza()));
    }

    public static PipelineManager newInstanceForObjectDetection(Context context, VkpObjectDetectorOptions vkpObjectDetectorOptions) {
        return new PipelineManager(context, vkpObjectDetectorOptions, vkpObjectDetectorOptions.zzi(), zzahn.zzb("vision-internal-vkp"), (zzafo) Preconditions.checkNotNull(zzafo.zza()));
    }

    private final zzhl zzb(String str) throws IOException {
        AssetFileDescriptor assetFileDescriptorOpenFd = this.zza.getAssets().openFd(str);
        this.zze.add(assetFileDescriptorOpenFd);
        zzhk zzhkVarZze = zzhl.zze();
        zzhkVarZze.zza(((AssetFileDescriptor) Preconditions.checkNotNull(assetFileDescriptorOpenFd)).getParcelFileDescriptor().getFd());
        zzhkVarZze.zzc(((AssetFileDescriptor) Preconditions.checkNotNull(assetFileDescriptorOpenFd)).getStartOffset());
        zzhkVarZze.zzb(((AssetFileDescriptor) Preconditions.checkNotNull(assetFileDescriptorOpenFd)).getLength());
        return zzhkVarZze.zzt();
    }

    private final zzhl zzc(Uri uri) throws IOException {
        AssetFileDescriptor assetFileDescriptorZza = zzn.zza(this.zza, uri, "r");
        this.zze.add(assetFileDescriptorZza);
        if (assetFileDescriptorZza != null) {
            zzhk zzhkVarZze = zzhl.zze();
            zzhkVarZze.zza(((AssetFileDescriptor) Preconditions.checkNotNull(assetFileDescriptorZza)).getParcelFileDescriptor().getFd());
            zzhkVarZze.zzc(((AssetFileDescriptor) Preconditions.checkNotNull(assetFileDescriptorZza)).getStartOffset());
            zzhkVarZze.zzb(((AssetFileDescriptor) Preconditions.checkNotNull(assetFileDescriptorZza)).getLength());
            return zzhkVarZze.zzt();
        }
        String strValueOf = String.valueOf(uri);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 19);
        sb.append("Failed to open URI ");
        sb.append(strValueOf);
        throw new IOException(sb.toString());
    }

    private final String[] zzd(LocalModel localModel, boolean z) throws IOException {
        String string;
        String string2 = z ? (String) Preconditions.checkNotNull(localModel.getAssetFilePath()) : (String) Preconditions.checkNotNull(localModel.getAbsoluteFilePath());
        if (localModel.isManifestFile()) {
            ModelUtils.AutoMLManifest manifestFile = ModelUtils.parseManifestFile(string2, z, this.zza);
            if (manifestFile == null) {
                throw new IOException("Failed to parse manifest file.");
            }
            Preconditions.checkState(Constants.AUTOML_IMAGE_LABELING_MODEL_TYPE.equals(manifestFile.getModelType()), "Model type should be: %s.", Constants.AUTOML_IMAGE_LABELING_MODEL_TYPE);
            string2 = new File(new File(string2).getParent(), manifestFile.getModelFile()).toString();
            string = new File(new File(string2).getParent(), manifestFile.getLabelsFile()).toString();
        } else {
            string = "";
        }
        return new String[]{string2, string};
    }

    /* JADX WARN: Code duplicated, block: B:32:0x01cf  */
    public VkpResults process(InputImage inputImage, VisionImageMetadataParcel visionImageMetadataParcel) {
        VkpStatus vkpStatusZzb;
        zzkg<zzda> zzkgVarZzb;
        boolean z;
        Boolean bool;
        if (this.zzc) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            long j = this.zzl;
            if (j <= 0 || jElapsedRealtime - j <= 300) {
                this.zzl = jElapsedRealtime;
                vkpStatusZzb = VkpStatus.zzb();
            } else {
                Log.w("PipelineManager", "Pipeline is reset.");
                stop();
                vkpStatusZzb = start();
            }
        } else {
            vkpStatusZzb = VkpStatus.zzb();
        }
        if (!vkpStatusZzb.isSuccess()) {
            return VkpResults.zza(vkpStatusZzb);
        }
        try {
            if (inputImage.getFormat() == -1) {
                Bitmap bitmapCopy = (Bitmap) Preconditions.checkNotNull(inputImage.getBitmapInternal());
                if (bitmapCopy.getConfig() != Bitmap.Config.ARGB_8888) {
                    String strValueOf = String.valueOf(bitmapCopy.getConfig());
                    StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 70);
                    sb.append("Input bitmap is not ARGB_8888 config. Converting it to ARGB_8888 from ");
                    sb.append(strValueOf);
                    Log.d("PipelineManager", sb.toString());
                    bitmapCopy = bitmapCopy.copy(Bitmap.Config.ARGB_8888, bitmapCopy.isMutable());
                }
                zzkgVarZzb = ((zzd) Preconditions.checkNotNull(this.zzi)).zzh(SystemClock.elapsedRealtime() * 1000, bitmapCopy, zze.zza(visionImageMetadataParcel.rotation));
            } else if (inputImage.getFormat() == 35) {
                Image.Plane[] planeArr = (Image.Plane[]) Preconditions.checkNotNull(inputImage.getPlanes());
                zzkgVarZzb = ((zzd) Preconditions.checkNotNull(this.zzi)).zzi(SystemClock.elapsedRealtime() * 1000, ((Image.Plane) Preconditions.checkNotNull(planeArr[0])).getBuffer(), ((Image.Plane) Preconditions.checkNotNull(planeArr[1])).getBuffer(), ((Image.Plane) Preconditions.checkNotNull(planeArr[2])).getBuffer(), inputImage.getWidth(), inputImage.getHeight(), ((Image.Plane) Preconditions.checkNotNull(planeArr[0])).getRowStride(), ((Image.Plane) Preconditions.checkNotNull(planeArr[1])).getRowStride(), ((Image.Plane) Preconditions.checkNotNull(planeArr[1])).getPixelStride(), zze.zza(visionImageMetadataParcel.rotation));
            } else {
                ByteBuffer byteBufferConvertToNv21Buffer = ImageConvertUtils.getInstance().convertToNv21Buffer(inputImage, true);
                zzd zzdVar = (zzd) Preconditions.checkNotNull(this.zzi);
                zzbg zzbgVar = new zzbg();
                zzbgVar.zza(byteBufferConvertToNv21Buffer.array());
                zzbgVar.zzf(zze.zza(visionImageMetadataParcel.rotation));
                zzbgVar.zzb(new zzco(visionImageMetadataParcel.width, visionImageMetadataParcel.height));
                zzbgVar.zzc(visionImageMetadataParcel.timestampMillis * 1000);
                zzbgVar.zze(2);
                zzkgVarZzb = zzdVar.zzb(zzbgVar.zzd());
            }
            if (!zzkgVarZzb.zzc()) {
                return VkpResults.zza(new AutoValue_VkpStatus(false, null, zzlb.zzj()));
            }
            com.google.android.libraries.vision.visionkit.pipeline.zzc zzcVarZzA = ((zzd) Preconditions.checkNotNull(this.zzi)).zza().zzb(com.google.android.libraries.vision.visionkit.pipeline.zzd.zzd()).zzA();
            zzcVarZzA.zza(this.zzd);
            com.google.android.libraries.vision.visionkit.pipeline.zzd zzdVarZzt = zzcVarZzA.zzt();
            this.zzd.clear();
            List<zzaup> listZza = this.zzf.zza(zzdVarZzt.zzf());
            if (!listZza.isEmpty()) {
                zzahd zzahdVar = this.zzg;
                zzqn zzqnVarZza = zzqo.zza();
                zzqnVarZza.zza(listZza);
                zzahdVar.zzb(zzahe.zzf(zzqnVarZza), zzadr.ACCELERATION_ANALYTICS);
            }
            zzda zzdaVarZza = zzkgVarZzb.zza();
            Matrix uprightRotationMatrix = visionImageMetadataParcel.getUprightRotationMatrix();
            boolean z2 = this.zzk;
            if (zzdVarZzt.zza() == 0) {
                bool = null;
            } else {
                Iterator<zzaup> it = zzdVarZzt.zzf().iterator();
                boolean z3 = false;
                while (true) {
                    if (it.hasNext()) {
                        zzaui zzauiVarZze = it.next().zze();
                        zzaue zzaueVarZze = zzauiVarZze.zze();
                        if (zzauiVarZze.zzh() == 4) {
                            if (zzauiVarZze.zzi() != 2 || zzauiVarZze.zzc().zzf().zzf() != 2 || !zzaueVarZze.zzi() || zzaueVarZze.zzc().zzf() || zzaueVarZze.zzc().zzg() || zzaueVarZze.zzh() || zzaueVarZze.zzg()) {
                                z3 = true;
                            } else {
                                z = true;
                            }
                        }
                    } else if (z3) {
                        z = false;
                    } else {
                        bool = null;
                    }
                    bool = z;
                }
            }
            zzkw zzkwVar = new zzkw();
            zzjm zzjmVarZzf = zzdaVarZza.zzf();
            for (int i = 0; i < zzjmVarZzf.zza(); i++) {
                zzjj zzjjVarZzc = zzjmVarZzf.zzc(i);
                zzgl zzglVarZzd = zzjjVarZzc.zzd();
                RectF rectF = new RectF(zzglVarZzd.zzc(), zzglVarZzd.zzd(), zzglVarZzd.zzc() + zzglVarZzd.zze(), zzglVarZzd.zzd() + zzglVarZzd.zza());
                if (uprightRotationMatrix != null) {
                    uprightRotationMatrix.mapRect(rectF);
                }
                Rect rect = new Rect();
                rectF.round(rect);
                Integer numValueOf = zzjjVarZzc.zzg() ? Integer.valueOf((int) zzjjVarZzc.zzc()) : null;
                zzkw zzkwVar2 = new zzkw();
                for (int i2 = 0; i2 < zzjjVarZzc.zza(); i2++) {
                    zzkwVar2.zzb(VkpImageLabel.zza(zzjjVarZzc.zze(i2)));
                }
                zzkwVar.zzb(new AutoValue_VkpDetectedObject(rect, numValueOf, zzkwVar2.zzc()));
            }
            zzkw zzkwVar3 = new zzkw();
            Iterator<zzip> it2 = zzdaVarZza.zze().zzd().iterator();
            while (it2.hasNext()) {
                Iterator<zzgw> it3 = it2.next().zzc().iterator();
                while (it3.hasNext()) {
                    zzkwVar3.zzb(VkpImageLabel.zza(it3.next()));
                }
            }
            AutoValue_VkpResults autoValue_VkpResults = new AutoValue_VkpResults(VkpStatus.zzb(), zzkwVar.zzc(), zzkwVar3.zzc(), z2, bool);
            this.zzk = false;
            return autoValue_VkpResults;
        } catch (MlKitException e) {
            return VkpResults.zza(VkpStatus.zza(e));
        }
    }

    public VkpStatus start() {
        zziu zziuVarZzf;
        zzarq zzarqVar;
        zzcg zzcgVarZza;
        if (this.zzj) {
            return VkpStatus.zzb();
        }
        if (this.zzi == null) {
            try {
                zzc zzcVar = this.zzb;
                if (zzcVar instanceof VkpImageLabelerOptions) {
                    VkpImageLabelerOptions vkpImageLabelerOptions = (VkpImageLabelerOptions) zzcVar;
                    float fZza = vkpImageLabelerOptions.zza();
                    int iZzb = vkpImageLabelerOptions.zzb();
                    LocalModel localModelZzc = vkpImageLabelerOptions.zzc();
                    if (localModelZzc == null) {
                        zzcgVarZza = zzft.zzc(this.zza, fZza, iZzb);
                    } else if (localModelZzc.getAssetFilePath() != null) {
                        String[] strArrZzd = zzd(localModelZzc, true);
                        zzcgVarZza = zzft.zzb(zzb(strArrZzd[0]), fZza, iZzb, strArrZzd[1], this.zza);
                    } else if (localModelZzc.getAbsoluteFilePath() != null) {
                        String[] strArrZzd2 = zzd(localModelZzc, false);
                        zzcgVarZza = zzft.zza(strArrZzd2[0], fZza, iZzb, strArrZzd2[1]);
                    } else {
                        zzcgVarZza = zzft.zzb(zzc((Uri) Preconditions.checkNotNull(localModelZzc.getUri())), fZza, iZzb, "", this.zza);
                    }
                } else {
                    VkpObjectDetectorOptions vkpObjectDetectorOptions = (VkpObjectDetectorOptions) zzcVar;
                    float fZza2 = vkpObjectDetectorOptions.zza();
                    int iZzb2 = vkpObjectDetectorOptions.zzb();
                    LocalModel localModelZzc2 = vkpObjectDetectorOptions.zzc();
                    if (!vkpObjectDetectorOptions.zzg()) {
                        zziuVarZzf = zzfu.zza;
                    } else if (localModelZzc2 == null) {
                        zziuVarZzf = zzfu.zzg(zzb(zzfu.zzi()));
                    } else if (localModelZzc2.getAssetFilePath() != null) {
                        String[] strArrZzd3 = zzd(localModelZzc2, true);
                        zziuVarZzf = zzfu.zzf(this.zza, zzb(strArrZzd3[0]), strArrZzd3[1], fZza2, iZzb2);
                    } else if (localModelZzc2.getAbsoluteFilePath() != null) {
                        String[] strArrZzd4 = zzd(localModelZzc2, false);
                        zziuVarZzf = zzfu.zze(strArrZzd4[0], strArrZzd4[1], fZza2, iZzb2);
                    } else {
                        zziuVarZzf = zzfu.zzf(this.zza, zzc((Uri) Preconditions.checkNotNull(localModelZzc2.getUri())), "", fZza2, iZzb2);
                    }
                    zziu zziuVar = zziuVarZzf;
                    int i = true != vkpObjectDetectorOptions.zzf() ? 3 : 2;
                    zzhl zzhlVarZzb = zzb(zzfu.zzj());
                    if (!vkpObjectDetectorOptions.zzf() || Build.VERSION.SDK_INT < 29) {
                        zzarqVar = null;
                    } else {
                        zzarq zzarqVarZzc = this.zzh.zzc(vkpObjectDetectorOptions.zzd(), vkpObjectDetectorOptions.zze(), "com.google.perception", 2);
                        Log.i("PipelineManager", "Fetching acceleration allowlist");
                        this.zzh.zzb(vkpObjectDetectorOptions.zzd(), vkpObjectDetectorOptions.zze(), "com.google.perception", 2);
                        zzarqVar = zzarqVarZzc;
                    }
                    if (vkpObjectDetectorOptions.zzi()) {
                        zzdm zzdmVarZzd = zzfu.zzd(this.zza, vkpObjectDetectorOptions.zzh(), zzhlVarZzb, zziuVar, PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS, zzarqVar);
                        zzdmVarZzd.zzj(i);
                        zzcgVarZza = zzfu.zzb(zzdmVarZzd);
                    } else {
                        zzdm zzdmVarZzc = zzfu.zzc(this.zza, vkpObjectDetectorOptions.zzh(), zzhlVarZzb, zziuVar);
                        zzdmVarZzc.zzj(i);
                        if (zzarqVar != null) {
                            zzdmVarZzc.zzc(zzarqVar);
                        }
                        zzcgVarZza = zzfu.zza(zzdmVarZzc);
                    }
                }
                zzcf zzcfVarZzA = zzcgVarZza.zzA();
                zzf zzfVarZza = zzg.zza();
                zzfVarZza.zza(true);
                File file = new File(this.zza.getFilesDir(), "com.google.mlkit.acceleration");
                if (!file.exists() && !file.mkdir()) {
                    List<zzaup> list = this.zzd;
                    zzauo zzauoVarZzf = zzaup.zzf();
                    zzauoVarZzf.zza(3);
                    list.add(zzauoVarZzf.zzt());
                    Log.e("PipelineManager", "Failed to create acceleration storage dir");
                }
                zzfVarZza.zzb(file.getAbsolutePath());
                zzcfVarZzA.zza(zzfVarZza);
                this.zzi = new zzd(zzcfVarZzA.zzt());
            } catch (IOException e) {
                zza();
                return VkpStatus.zza(new MlKitException("Failed to initialize detector. ", 5, e));
            }
        }
        try {
            try {
                this.zzi.zzf();
                zza();
                ProcessStateObserver.zza().zzb();
                this.zzj = true;
                return VkpStatus.zzb();
            } catch (PipelineException e2) {
                String strZzb = e2.getRootCauseMessage().zzb("");
                MlKitException mlKitException = new MlKitException(strZzb.length() != 0 ? "Failed to initialize detector. ".concat(strZzb) : new String("Failed to initialize detector. "), 3);
                zzla zzlaVar = new zzla();
                zzlaVar.zzb(new AutoValue_VkpStatus_VkpError(1, e2.getStatusCode().ordinal()));
                Iterator<zzag> it = e2.getComponentStatuses().iterator();
                while (it.hasNext()) {
                    for (zzam zzamVar : it.next().zzc()) {
                        zzlaVar.zzb(new AutoValue_VkpStatus_VkpError(true != "tflite::support::TfLiteSupportStatus".equals(zzamVar.zzd()) ? 0 : 3, zzamVar.zza()));
                    }
                }
                AutoValue_VkpStatus autoValue_VkpStatus = new AutoValue_VkpStatus(false, mlKitException, zzlaVar.zzc());
                zza();
                return autoValue_VkpStatus;
            }
        } catch (Throwable th) {
            zza();
            throw th;
        }
    }

    public void stop() {
        zzd zzdVar = this.zzi;
        if (zzdVar != null) {
            if (this.zzj) {
                zzdVar.zzg();
            }
            this.zzi.zzc();
            this.zzi = null;
        }
        this.zzj = false;
        this.zzk = true;
        this.zzl = -1L;
        zza();
    }

    final void zza() {
        for (AssetFileDescriptor assetFileDescriptor : this.zze) {
            if (assetFileDescriptor != null) {
                try {
                    assetFileDescriptor.close();
                } catch (IOException e) {
                    Log.e("PipelineManager", "Failed to close asset model file.", e);
                }
            }
        }
        this.zze.clear();
    }
}
