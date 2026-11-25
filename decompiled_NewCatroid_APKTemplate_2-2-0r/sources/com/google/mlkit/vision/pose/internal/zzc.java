package com.google.mlkit.vision.pose.internal;

import android.os.SystemClock;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzbm;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzbx;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzcb;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzej;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzep;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzgd;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.sdkinternal.MLTask;
import com.google.mlkit.common.sdkinternal.MLTaskInput;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.ImageConvertUtils;
import com.google.mlkit.vision.common.internal.ImageUtils;
import com.google.mlkit.vision.mediapipe.MediaPipeGraphRunner;
import com.google.mlkit.vision.mediapipe.MediaPipeGraphRunnerConfig;
import com.google.mlkit.vision.mediapipe.MediaPipeInput;
import com.google.mlkit.vision.mediapipe.MediaPipeInputFactory;
import com.google.mlkit.vision.mediapipe.pose.PoseHolder;
import com.google.mlkit.vision.mediapipe.pose.PoseHolderConverter;
import com.google.mlkit.vision.mediapipe.utils.ImageConvertNativeUtils;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public final class zzc extends MLTask<Pose, InputImage> {
    private static boolean zzh = true;
    private static final ImageUtils zzi = ImageUtils.getInstance();
    private String zza;
    private String zzb;
    private MediaPipeGraphRunner zzc;
    private final zzej zzd;
    private final MlKitContext zze;
    private final PoseDetectorOptionsBase zzf;
    private final zzbm.zzba zzg;

    public zzc(MlKitContext mlKitContext, PoseDetectorOptionsBase poseDetectorOptionsBase) {
        Preconditions.checkNotNull(mlKitContext, "MlKitContext can not be null");
        Preconditions.checkNotNull(poseDetectorOptionsBase, "PoseDetectorOptionsBase can not be null");
        this.zzd = (zzej) mlKitContext.get(zzej.class);
        this.zze = mlKitContext;
        this.zzf = poseDetectorOptionsBase;
        this.zzg = poseDetectorOptionsBase.zzd();
        this.zza = poseDetectorOptionsBase.zzb();
        this.zzb = poseDetectorOptionsBase.zzc();
    }

    @Override // com.google.mlkit.common.sdkinternal.ModelResource
    public final void load() throws MlKitException {
        String str;
        if (this.zzc == null) {
            zza(zzcb.ON_DEVICE_POSE_LOAD);
            HashMap map = new HashMap();
            map.put("$POSE_DETECTOR_MODEL", this.zza);
            map.put("$POSE_LANDMARKS_MODEL", this.zzb);
            MlKitContext mlKitContext = this.zze;
            if (this.zzf.zza() == 1) {
                str = "mlkit_pose/pose_tracking_graph.binarypb";
            } else {
                str = "mlkit_pose/pose_non_tracking_graph.binarypb";
            }
            MediaPipeGraphRunner mediaPipeGraphRunner = new MediaPipeGraphRunner(MediaPipeGraphRunnerConfig.create(mlKitContext, str, "input_frames", "output_pose_landmarks", map, MediaPipeGraphRunnerConfig.ResultType.PROTO));
            this.zzc = mediaPipeGraphRunner;
            ((MediaPipeGraphRunner) Preconditions.checkNotNull(mediaPipeGraphRunner)).load();
        }
    }

    @Override // com.google.mlkit.common.sdkinternal.ModelResource
    public final void release() {
        MediaPipeGraphRunner mediaPipeGraphRunner = this.zzc;
        if (mediaPipeGraphRunner != null) {
            mediaPipeGraphRunner.close();
            this.zzc = null;
        }
        zza(zzcb.ON_DEVICE_POSE_CLOSE);
        zzh = true;
    }

    private final void zza(zzcb zzcbVar) {
        this.zzd.zza(zzbm.zzad.zzb().zza(true).zza((zzbm.zzaz) ((zzgd) zzbm.zzaz.zza().zza(this.zzg).zzh())), zzcbVar);
    }

    @Override // com.google.mlkit.common.sdkinternal.MLTask
    public final /* synthetic */ Pose run(MLTaskInput mLTaskInput) throws MlKitException {
        MediaPipeInput mediaPipeInput;
        zzbx zzbxVar;
        final InputImage inputImage = (InputImage) mLTaskInput;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        byte[] rgbBuffer = ImageConvertNativeUtils.getRgbBuffer(inputImage);
        if (rgbBuffer == null) {
            mediaPipeInput = MediaPipeInputFactory.getMediaPipeInput(ImageConvertUtils.getInstance().getUpRightBitmap(inputImage), TimeUnit.MICROSECONDS.convert(jElapsedRealtime, TimeUnit.MILLISECONDS));
        } else {
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();
            if ((inputImage.getRotationDegrees() / 90) % 2 == 1) {
                width = inputImage.getHeight();
                height = inputImage.getWidth();
            }
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(rgbBuffer.length);
            byteBufferAllocateDirect.put(rgbBuffer);
            byteBufferAllocateDirect.rewind();
            mediaPipeInput = MediaPipeInputFactory.getMediaPipeInput(byteBufferAllocateDirect, width, height, TimeUnit.MICROSECONDS.convert(jElapsedRealtime, TimeUnit.MILLISECONDS));
        }
        PoseHolder poseHolder = (PoseHolder) ((MediaPipeGraphRunner) Preconditions.checkNotNull(this.zzc)).run(mediaPipeInput, new PoseHolderConverter());
        if (poseHolder.getPoseLandmarkHolders().isEmpty()) {
            zzbxVar = zzbx.MEDIAPIPE_ERROR;
        } else {
            zzbxVar = zzbx.NO_ERROR;
        }
        final zzbx zzbxVar2 = zzbxVar;
        final long jElapsedRealtime2 = SystemClock.elapsedRealtime() - jElapsedRealtime;
        this.zzd.zza(new zzej.zza(this, jElapsedRealtime2, zzbxVar2, inputImage) { // from class: com.google.mlkit.vision.pose.internal.zzb
            private final zzc zza;
            private final long zzb;
            private final zzbx zzc;
            private final InputImage zzd;

            {
                this.zza = this;
                this.zzb = jElapsedRealtime2;
                this.zzc = zzbxVar2;
                this.zzd = inputImage;
            }

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzej.zza
            public final zzbm.zzad.zza zza() {
                return this.zza.zza(this.zzb, this.zzc, this.zzd);
            }
        }, zzcb.ON_DEVICE_POSE_INFERENCE);
        zzbm.zzg.zza.C0066zza c0066zzaZza = zzbm.zzg.zza.zza().zza(this.zzg).zza(zzbxVar2).zza(zzh);
        ImageUtils imageUtils = zzi;
        this.zzd.zza((zzbm.zzg.zza) ((zzgd) c0066zzaZza.zza(zzep.zza(imageUtils.getMobileVisionImageFormat(inputImage), imageUtils.getMobileVisionImageSize(inputImage))).zzh()), jElapsedRealtime2, zzcb.AGGREGATED_ON_DEVICE_POSE_DETECTION, zzd.zza);
        zzh = false;
        return new Pose(poseHolder);
    }

    final /* synthetic */ zzbm.zzad.zza zza(long j, zzbx zzbxVar, InputImage inputImage) {
        zzbm.zzad.zza zzaVarZza = zzbm.zzad.zzb().zza(true);
        zzbm.zzaz.zza zzaVarZza2 = zzbm.zzaz.zza().zza(zzbm.zzaf.zza().zza(j).zza(zzbxVar).zza(zzh).zzb(true).zzc(true));
        ImageUtils imageUtils = zzi;
        return zzaVarZza.zza(zzaVarZza2.zza(zzep.zza(imageUtils.getMobileVisionImageFormat(inputImage), imageUtils.getMobileVisionImageSize(inputImage))).zza(this.zzg));
    }
}
