package org.catrobat.catroid.camera.mlkitdetectors;

import android.media.Image;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.camera.CatdroidImageAnalyzer;
import org.catrobat.catroid.camera.DetectorsCompleteListener;
import org.catrobat.catroid.camera.VisualDetectionHandler;
import org.catrobat.catroid.camera.VisualDetectionHandlerFace;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/mlkitdetectors/FaceDetector.class
  classes12.dex
 */
/* JADX INFO: compiled from: FaceDetector.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/camera/mlkitdetectors/FaceDetector;", "Lorg/catrobat/catroid/camera/mlkitdetectors/Detector;", "()V", "processImage", "", "mediaImage", "Landroid/media/Image;", "inputImage", "Lcom/google/mlkit/vision/common/InputImage;", "onCompleteListener", "Lorg/catrobat/catroid/camera/DetectorsCompleteListener;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FaceDetector implements Detector {
    public static final FaceDetector INSTANCE = new FaceDetector();

    private FaceDetector() {
    }

    @Override // org.catrobat.catroid.camera.mlkitdetectors.Detector
    public void processImage(final Image mediaImage, InputImage inputImage, final DetectorsCompleteListener onCompleteListener) {
        Intrinsics.checkNotNullParameter(mediaImage, "mediaImage");
        Intrinsics.checkNotNullParameter(inputImage, "inputImage");
        Intrinsics.checkNotNullParameter(onCompleteListener, "onCompleteListener");
        Task<List<Face>> taskProcess = FaceDetectorKt.getFaceDetectionClient().process(inputImage);
        final Function1<List<Face>, Unit> function1 = new Function1<List<Face>, Unit>() { // from class: org.catrobat.catroid.camera.mlkitdetectors.FaceDetector.processImage.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<Face> list) {
                invoke2(list);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<Face> list) {
                VisualDetectionHandler visualDetectionHandler = VisualDetectionHandler.INSTANCE;
                Intrinsics.checkNotNull(list);
                List<VisualDetectionHandlerFace> listTranslateGoogleFaceToVisualDetectionFace = visualDetectionHandler.translateGoogleFaceToVisualDetectionFace(list);
                VisualDetectionHandler.INSTANCE.handleAlreadyExistingFaces(listTranslateGoogleFaceToVisualDetectionFace);
                VisualDetectionHandler.INSTANCE.handleNewFaces(listTranslateGoogleFaceToVisualDetectionFace);
                VisualDetectionHandler.INSTANCE.updateAllFaceSensorValues(mediaImage.getWidth(), mediaImage.getHeight());
            }
        };
        taskProcess.addOnSuccessListener(new OnSuccessListener() { // from class: org.catrobat.catroid.camera.mlkitdetectors.FaceDetector$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                FaceDetector.processImage$lambda$0(function1, obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.camera.mlkitdetectors.FaceDetector$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                FaceDetector.processImage$lambda$1(exc);
            }
        }).addOnCompleteListener(new OnCompleteListener() { // from class: org.catrobat.catroid.camera.mlkitdetectors.FaceDetector$$ExternalSyntheticLambda2
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                FaceDetector.processImage$lambda$2(onCompleteListener, task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processImage$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processImage$lambda$1(Exception e) {
        Intrinsics.checkNotNullParameter(e, "e");
        VisualDetectionHandler.INSTANCE.updateFaceDetectionStatusSensorValues();
        StageActivity.messageHandler.obtainMessage(3, CollectionsKt.arrayListOf(CatroidApplication.getAppContext().getString(R.string.camera_error_face_detection))).sendToTarget();
        Log.e(INSTANCE.getClass().getSimpleName(), CatdroidImageAnalyzer.DETECTION_PROCESS_ERROR_MESSAGE, e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processImage$lambda$2(DetectorsCompleteListener onCompleteListener, Task it) {
        Intrinsics.checkNotNullParameter(onCompleteListener, "$onCompleteListener");
        Intrinsics.checkNotNullParameter(it, "it");
        onCompleteListener.onComplete();
    }
}
