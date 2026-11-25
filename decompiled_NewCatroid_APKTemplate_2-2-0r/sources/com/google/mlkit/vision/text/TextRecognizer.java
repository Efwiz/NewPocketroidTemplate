package com.google.mlkit.vision.text;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.Detector;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
public interface TextRecognizer extends Detector<Text> {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void close();

    @Override // com.google.mlkit.vision.common.Detector
    Task<Text> process(InputImage inputImage);
}
