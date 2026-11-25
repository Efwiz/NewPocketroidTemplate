package com.google.mlkit.vision.pose;

import com.google.android.gms.common.internal.Preconditions;
import com.google.mlkit.vision.pose.internal.PoseDetectorImpl;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class PoseDetection {
    public static PoseDetector getClient(PoseDetectorOptionsBase poseDetectorOptionsBase) {
        Preconditions.checkNotNull(poseDetectorOptionsBase, "PoseDetectorOptionsBase can not be null.");
        return PoseDetectorImpl.newInstance(poseDetectorOptionsBase);
    }

    private PoseDetection() {
    }
}
