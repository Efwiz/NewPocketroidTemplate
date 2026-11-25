package com.google.mlkit.vision.pose.defaults;

import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection@@17.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class PoseDetectorOptions extends PoseDetectorOptionsBase {

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection@@17.0.0 */
    public static class Builder extends PoseDetectorOptionsBase.Builder<Builder> {
        @Override // com.google.mlkit.vision.pose.PoseDetectorOptionsBase.Builder
        public Builder setDetectorMode(int i) {
            return (Builder) super.setDetectorMode(i);
        }

        @Override // com.google.mlkit.vision.pose.PoseDetectorOptionsBase.Builder
        public Builder setExecutor(Executor executor) {
            return (Builder) super.setExecutor(executor);
        }

        @Override // com.google.mlkit.vision.pose.PoseDetectorOptionsBase.Builder
        public PoseDetectorOptions build() {
            super.setModelInfo(true, "mlkit_pose/posedetector.tflite", "mlkit_pose/pose_39kp_lite.tflite");
            return new PoseDetectorOptions(this);
        }
    }

    private PoseDetectorOptions(Builder builder) {
        super(builder);
    }
}
