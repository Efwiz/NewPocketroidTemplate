package org.catrobat.catroid.camera.mlkitdetectors;

import android.media.Image;
import android.os.Handler;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.camera.CatdroidImageAnalyzer;
import org.catrobat.catroid.camera.DetectorsCompleteListener;
import org.catrobat.catroid.camera.VisualDetectionHandler;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.TextBlockUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/mlkitdetectors/TextDetector.class
  classes12.dex
 */
/* JADX INFO: compiled from: TextDetector.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/camera/mlkitdetectors/TextDetector;", "Lorg/catrobat/catroid/camera/mlkitdetectors/Detector;", "()V", "processImage", "", "mediaImage", "Landroid/media/Image;", "inputImage", "Lcom/google/mlkit/vision/common/InputImage;", "onCompleteListener", "Lorg/catrobat/catroid/camera/DetectorsCompleteListener;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TextDetector implements Detector {
    public static final TextDetector INSTANCE = new TextDetector();

    private TextDetector() {
    }

    @Override // org.catrobat.catroid.camera.mlkitdetectors.Detector
    public void processImage(final Image mediaImage, InputImage inputImage, final DetectorsCompleteListener onCompleteListener) {
        Intrinsics.checkNotNullParameter(mediaImage, "mediaImage");
        Intrinsics.checkNotNullParameter(inputImage, "inputImage");
        Intrinsics.checkNotNullParameter(onCompleteListener, "onCompleteListener");
        Task<Text> taskProcess = TextDetectorKt.getTextDetectionClient().process(inputImage);
        final Function1<Text, Unit> function1 = new Function1<Text, Unit>() { // from class: org.catrobat.catroid.camera.mlkitdetectors.TextDetector.processImage.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Text text) {
                invoke2(text);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Text text) {
                VisualDetectionHandler visualDetectionHandler = VisualDetectionHandler.INSTANCE;
                String text2 = text.getText();
                Intrinsics.checkNotNullExpressionValue(text2, "getText(...)");
                visualDetectionHandler.updateTextSensorValues(text2, text.getTextBlocks().size());
                TextBlockUtil textBlockUtil = TextBlockUtil.INSTANCE;
                List<Text.TextBlock> textBlocks = text.getTextBlocks();
                Intrinsics.checkNotNullExpressionValue(textBlocks, "getTextBlocks(...)");
                textBlockUtil.setTextBlocksGoogle(textBlocks, mediaImage.getWidth(), mediaImage.getHeight());
            }
        };
        taskProcess.addOnSuccessListener(new OnSuccessListener() { // from class: org.catrobat.catroid.camera.mlkitdetectors.TextDetector$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                TextDetector.processImage$lambda$0(function1, obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.camera.mlkitdetectors.TextDetector$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                TextDetector.processImage$lambda$1(exc);
            }
        }).addOnCompleteListener(new OnCompleteListener() { // from class: org.catrobat.catroid.camera.mlkitdetectors.TextDetector$$ExternalSyntheticLambda2
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                TextDetector.processImage$lambda$2(onCompleteListener, task);
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
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        Handler handler = StageActivity.messageHandler;
        String[] strArr = new String[1];
        strArr[0] = stageActivity != null ? stageActivity.getString(R.string.camera_error_text_detection) : null;
        handler.obtainMessage(3, CollectionsKt.arrayListOf(strArr)).sendToTarget();
        Log.e(INSTANCE.getClass().getSimpleName(), CatdroidImageAnalyzer.DETECTION_PROCESS_ERROR_MESSAGE, e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processImage$lambda$2(DetectorsCompleteListener onCompleteListener, Task it) {
        Intrinsics.checkNotNullParameter(onCompleteListener, "$onCompleteListener");
        Intrinsics.checkNotNullParameter(it, "it");
        onCompleteListener.onComplete();
    }
}
