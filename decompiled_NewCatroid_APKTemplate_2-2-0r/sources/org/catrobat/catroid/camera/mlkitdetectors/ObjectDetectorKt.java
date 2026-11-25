package org.catrobat.catroid.camera.mlkitdetectors;

import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/mlkitdetectors/ObjectDetectorKt.class
  classes12.dex
 */
/* JADX INFO: compiled from: ObjectDetector.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001b\u0010\u0000\u001a\u00020\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0006"}, d2 = {"objectDetectionClient", "Lcom/google/mlkit/vision/objects/ObjectDetector;", "getObjectDetectionClient", "()Lcom/google/mlkit/vision/objects/ObjectDetector;", "objectDetectionClient$delegate", "Lkotlin/Lazy;", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ObjectDetectorKt {
    private static final Lazy objectDetectionClient$delegate = LazyKt.lazy(new Function0<com.google.mlkit.vision.objects.ObjectDetector>() { // from class: org.catrobat.catroid.camera.mlkitdetectors.ObjectDetectorKt$objectDetectionClient$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final com.google.mlkit.vision.objects.ObjectDetector invoke() {
            return ObjectDetection.getClient(new ObjectDetectorOptions.Builder().enableMultipleObjects().enableClassification().build());
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final com.google.mlkit.vision.objects.ObjectDetector getObjectDetectionClient() {
        return (com.google.mlkit.vision.objects.ObjectDetector) objectDetectionClient$delegate.getValue();
    }
}
