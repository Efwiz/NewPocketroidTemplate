package com.google.mlkit.vision.objects;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import com.google.android.gms.tasks.Task;
import com.google.android.odml.image.MlImage;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.Detector;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:object-detection-common@@17.1.0 */
/* JADX INFO: loaded from: classes10.dex */
public interface ObjectDetector extends Detector<List<DetectedObject>> {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void close();

    Task<List<DetectedObject>> process(MlImage mlImage);

    @Override // com.google.mlkit.vision.common.Detector
    Task<List<DetectedObject>> process(InputImage inputImage);
}
