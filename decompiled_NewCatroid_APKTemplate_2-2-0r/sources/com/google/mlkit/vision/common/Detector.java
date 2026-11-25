package com.google.mlkit.vision.common;

import androidx.lifecycle.LifecycleObserver;
import com.google.android.gms.tasks.Task;
import java.io.Closeable;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes10.dex */
public interface Detector<DetectionResultT> extends Closeable, LifecycleObserver {
    Task<DetectionResultT> process(InputImage inputImage);
}
