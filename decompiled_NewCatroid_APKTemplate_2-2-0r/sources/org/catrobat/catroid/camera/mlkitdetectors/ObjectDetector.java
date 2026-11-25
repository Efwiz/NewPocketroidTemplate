package org.catrobat.catroid.camera.mlkitdetectors;

import android.media.Image;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.camera.CatdroidImageAnalyzer;
import org.catrobat.catroid.camera.DetectorsCompleteListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/mlkitdetectors/ObjectDetector.class
  classes12.dex
 */
/* JADX INFO: compiled from: ObjectDetector.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/camera/mlkitdetectors/ObjectDetector;", "Lorg/catrobat/catroid/camera/mlkitdetectors/Detector;", "()V", "processImage", "", "mediaImage", "Landroid/media/Image;", "inputImage", "Lcom/google/mlkit/vision/common/InputImage;", "onCompleteListener", "Lorg/catrobat/catroid/camera/DetectorsCompleteListener;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ObjectDetector implements Detector {
    public static final ObjectDetector INSTANCE = new ObjectDetector();

    private ObjectDetector() {
    }

    @Override // org.catrobat.catroid.camera.mlkitdetectors.Detector
    public void processImage(Image mediaImage, InputImage inputImage, final DetectorsCompleteListener onCompleteListener) {
        Intrinsics.checkNotNullParameter(mediaImage, "mediaImage");
        Intrinsics.checkNotNullParameter(inputImage, "inputImage");
        Intrinsics.checkNotNullParameter(onCompleteListener, "onCompleteListener");
        ObjectDetectorKt.getObjectDetectionClient().process(inputImage).addOnSuccessListener(new ObjectDetectorOnSuccessListener()).addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.camera.mlkitdetectors.ObjectDetector$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                ObjectDetector.processImage$lambda$0(exc);
            }
        }).addOnCompleteListener(new OnCompleteListener() { // from class: org.catrobat.catroid.camera.mlkitdetectors.ObjectDetector$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                ObjectDetector.processImage$lambda$1(onCompleteListener, task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processImage$lambda$0(Exception exception) {
        Intrinsics.checkNotNullParameter(exception, "exception");
        Log.e(INSTANCE.getClass().getSimpleName(), CatdroidImageAnalyzer.DETECTION_PROCESS_ERROR_MESSAGE, exception);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processImage$lambda$1(DetectorsCompleteListener onCompleteListener, Task it) {
        Intrinsics.checkNotNullParameter(onCompleteListener, "$onCompleteListener");
        Intrinsics.checkNotNullParameter(it, "it");
        onCompleteListener.onComplete();
    }
}
