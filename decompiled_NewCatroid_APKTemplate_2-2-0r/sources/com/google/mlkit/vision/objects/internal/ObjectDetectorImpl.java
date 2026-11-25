package com.google.mlkit.vision.objects.internal;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.odml.image.MlImage;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.ImageConvertUtils;
import com.google.mlkit.vision.common.internal.MobileVisionBase;
import com.google.mlkit.vision.common.internal.MultiFlavorDetectorCreator;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.ObjectDetectorOptionsBase;
import java.nio.ByteBuffer;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:object-detection-common@@17.1.0 */
/* JADX INFO: loaded from: classes10.dex */
public class ObjectDetectorImpl implements ObjectDetector {
    private final MobileVisionBase<List<DetectedObject>> zza;

    private ObjectDetectorImpl(ObjectDetectorOptionsBase objectDetectorOptionsBase) {
        this.zza = MultiFlavorDetectorCreator.getInstance().create(objectDetectorOptionsBase);
    }

    public static ObjectDetectorImpl zza(ObjectDetectorOptionsBase objectDetectorOptionsBase) {
        Preconditions.checkNotNull(objectDetectorOptionsBase, "options cannot be null");
        return new ObjectDetectorImpl(objectDetectorOptionsBase);
    }

    @Override // com.google.mlkit.vision.objects.ObjectDetector, java.io.Closeable, java.lang.AutoCloseable
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public final void close() {
        this.zza.close();
    }

    @Override // com.google.mlkit.vision.objects.ObjectDetector
    public final Task<List<DetectedObject>> process(MlImage mlImage) {
        return this.zza.processBase(mlImage);
    }

    @Override // com.google.mlkit.vision.objects.ObjectDetector, com.google.mlkit.vision.common.Detector
    public final Task<List<DetectedObject>> process(InputImage inputImage) {
        ByteBuffer byteBuffer = inputImage.getByteBuffer();
        if (byteBuffer != null) {
            inputImage = InputImage.fromByteBuffer(ImageConvertUtils.getInstance().cloneByteBuffer(byteBuffer), inputImage.getWidth(), inputImage.getHeight(), inputImage.getRotationDegrees(), inputImage.getFormat());
        }
        return this.zza.processBase(inputImage);
    }
}
