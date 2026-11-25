package com.google.mlkit.vision.pose.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzbm;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzcb;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzej;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzgd;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.common.sdkinternal.ExecutorSelector;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.MobileVisionBase;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class PoseDetectorImpl extends MobileVisionBase<Pose> implements PoseDetector {
    public static PoseDetectorImpl newInstance(PoseDetectorOptionsBase poseDetectorOptionsBase) {
        Preconditions.checkNotNull(poseDetectorOptionsBase, "PoseDetectorOptionsBase can not be null.");
        return new PoseDetectorImpl(MlKitContext.getInstance(), poseDetectorOptionsBase);
    }

    private PoseDetectorImpl(MlKitContext mlKitContext, PoseDetectorOptionsBase poseDetectorOptionsBase) {
        this(((zza) mlKitContext.get(zza.class)).get(poseDetectorOptionsBase), ((ExecutorSelector) mlKitContext.get(ExecutorSelector.class)).getExecutorToUse(poseDetectorOptionsBase.getExecutor()));
        ((zzej) mlKitContext.get(zzej.class)).zza(zzbm.zzad.zzb().zza(true).zza((zzbm.zzaz) ((zzgd) zzbm.zzaz.zza().zza(poseDetectorOptionsBase.zzd()).zzh())), zzcb.ON_DEVICE_POSE_CREATE);
    }

    private PoseDetectorImpl(zzc zzcVar, Executor executor) {
        super(zzcVar, executor);
    }

    @Override // com.google.mlkit.vision.pose.PoseDetector
    public Task<Pose> process(InputImage inputImage) {
        return super.processBase(inputImage);
    }
}
