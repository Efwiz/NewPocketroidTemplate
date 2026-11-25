package com.google.mlkit.vision.objects;

import com.google.android.gms.common.internal.Preconditions;
import com.google.mlkit.vision.objects.internal.ObjectDetectorImpl;

/* JADX INFO: compiled from: com.google.mlkit:object-detection-common@@17.1.0 */
/* JADX INFO: loaded from: classes10.dex */
public class ObjectDetection {
    private ObjectDetection() {
    }

    public static ObjectDetector getClient(ObjectDetectorOptionsBase objectDetectorOptionsBase) {
        Preconditions.checkNotNull(objectDetectorOptionsBase, "options cannot be null");
        return ObjectDetectorImpl.zza(objectDetectorOptionsBase);
    }
}
