package org.catrobat.catroid.camera;

import android.media.Image;
import android.os.Handler;
import android.util.Log;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzer;
import com.huawei.hms.mlsdk.skeleton.MLSkeletonAnalyzer;
import com.huawei.hms.mlsdk.skeleton.MLSkeletonAnalyzerFactory;
import com.huawei.hms.mlsdk.text.MLText;
import com.huawei.hms.mlsdk.text.MLTextAnalyzer;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.TextBlockUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/FaceTextPoseDetectorHuawei.class
  classes12.dex
 */
/* JADX INFO: compiled from: FaceTextPoseDetectorHuawei.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n \t*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n \t*\u0004\u0018\u00010\u00100\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/camera/FaceTextPoseDetectorHuawei;", "Landroidx/camera/core/ImageAnalysis$Analyzer;", "()V", "DETECTION_PROCESS_ERROR_MESSAGE", "", "QUADRANT_DEGREES", "", "analyzer", "Lcom/huawei/hms/mlsdk/face/MLFaceAnalyzer;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "faceDetected", "", "poseAnalyzer", "Lcom/huawei/hms/mlsdk/skeleton/MLSkeletonAnalyzer;", "poseDetected", "textAnalyzer", "Lcom/huawei/hms/mlsdk/text/MLTextAnalyzer;", "textDetected", "analyze", "", "imageProxy", "Landroidx/camera/core/ImageProxy;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FaceTextPoseDetectorHuawei implements ImageAnalysis.Analyzer {
    private static final String DETECTION_PROCESS_ERROR_MESSAGE = "Could not analyze image.";
    private static final int QUADRANT_DEGREES = 90;
    private static boolean faceDetected;
    private static boolean poseDetected;
    private static boolean textDetected;
    public static final FaceTextPoseDetectorHuawei INSTANCE = new FaceTextPoseDetectorHuawei();
    private static final MLFaceAnalyzer analyzer = MLAnalyzerFactory.getInstance().getFaceAnalyzer();
    private static final MLTextAnalyzer textAnalyzer = MLAnalyzerFactory.getInstance().getLocalTextAnalyzer();
    private static final MLSkeletonAnalyzer poseAnalyzer = MLSkeletonAnalyzerFactory.getInstance().getSkeletonAnalyzer();

    private FaceTextPoseDetectorHuawei() {
    }

    @Override // androidx.camera.core.ImageAnalysis.Analyzer
    public void analyze(final ImageProxy imageProxy) {
        Intrinsics.checkNotNullParameter(imageProxy, "imageProxy");
        final Image image = imageProxy.getImage();
        if (image != null) {
            MLFrame mLFrameFromMediaImage = MLFrame.fromMediaImage(image, imageProxy.getImageInfo().getRotationDegrees() / 90);
            textDetected = false;
            faceDetected = false;
            poseDetected = false;
            textAnalyzer.asyncAnalyseFrame(mLFrameFromMediaImage).addOnSuccessListener(new OnSuccessListener() { // from class: org.catrobat.catroid.camera.FaceTextPoseDetectorHuawei$$ExternalSyntheticLambda0
                @Override // com.huawei.hmf.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    FaceTextPoseDetectorHuawei.analyze$lambda$6$lambda$0(image, imageProxy, (MLText) obj);
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.camera.FaceTextPoseDetectorHuawei$$ExternalSyntheticLambda1
                @Override // com.huawei.hmf.tasks.OnFailureListener
                public final void onFailure(Exception exc) {
                    FaceTextPoseDetectorHuawei.analyze$lambda$6$lambda$1(exc);
                }
            });
            analyzer.asyncAnalyseFrame(mLFrameFromMediaImage).addOnSuccessListener(new OnSuccessListener() { // from class: org.catrobat.catroid.camera.FaceTextPoseDetectorHuawei$$ExternalSyntheticLambda2
                @Override // com.huawei.hmf.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    FaceTextPoseDetectorHuawei.analyze$lambda$6$lambda$2(image, imageProxy, (List) obj);
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.camera.FaceTextPoseDetectorHuawei$$ExternalSyntheticLambda3
                @Override // com.huawei.hmf.tasks.OnFailureListener
                public final void onFailure(Exception exc) {
                    FaceTextPoseDetectorHuawei.analyze$lambda$6$lambda$3(exc);
                }
            });
            poseAnalyzer.asyncAnalyseFrame(mLFrameFromMediaImage).addOnSuccessListener(new OnSuccessListener() { // from class: org.catrobat.catroid.camera.FaceTextPoseDetectorHuawei$$ExternalSyntheticLambda4
                @Override // com.huawei.hmf.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    FaceTextPoseDetectorHuawei.analyze$lambda$6$lambda$4(image, imageProxy, (List) obj);
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.camera.FaceTextPoseDetectorHuawei$$ExternalSyntheticLambda5
                @Override // com.huawei.hmf.tasks.OnFailureListener
                public final void onFailure(Exception exc) {
                    FaceTextPoseDetectorHuawei.analyze$lambda$6$lambda$5(exc);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void analyze$lambda$6$lambda$0(Image mediaImage, ImageProxy imageProxy, MLText mLText) {
        Intrinsics.checkNotNullParameter(mediaImage, "$mediaImage");
        Intrinsics.checkNotNullParameter(imageProxy, "$imageProxy");
        VisualDetectionHandler visualDetectionHandler = VisualDetectionHandler.INSTANCE;
        String stringValue = mLText.getStringValue();
        Intrinsics.checkNotNullExpressionValue(stringValue, "getStringValue(...)");
        visualDetectionHandler.updateTextSensorValues(stringValue, mLText.getBlocks().size());
        TextBlockUtil textBlockUtil = TextBlockUtil.INSTANCE;
        List<MLText.Block> blocks = mLText.getBlocks();
        Intrinsics.checkNotNullExpressionValue(blocks, "getBlocks(...)");
        textBlockUtil.setTextBlocksHuawei(blocks, mediaImage.getWidth(), mediaImage.getHeight());
        textDetected = true;
        if (faceDetected && poseDetected) {
            imageProxy.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void analyze$lambda$6$lambda$1(Exception exc) {
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        Handler handler = StageActivity.messageHandler;
        String[] strArr = new String[1];
        strArr[0] = stageActivity != null ? stageActivity.getString(R.string.camera_error_text_detection) : null;
        handler.obtainMessage(3, CollectionsKt.arrayListOf(strArr)).sendToTarget();
        Log.e(INSTANCE.getClass().getSimpleName(), "Could not analyze image.", exc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void analyze$lambda$6$lambda$2(Image mediaImage, ImageProxy imageProxy, List list) {
        Intrinsics.checkNotNullParameter(mediaImage, "$mediaImage");
        Intrinsics.checkNotNullParameter(imageProxy, "$imageProxy");
        VisualDetectionHandler visualDetectionHandler = VisualDetectionHandler.INSTANCE;
        Intrinsics.checkNotNull(list);
        List<VisualDetectionHandlerFace> listTranslateHuaweiFaceToVisualDetectionFace = visualDetectionHandler.translateHuaweiFaceToVisualDetectionFace(list);
        VisualDetectionHandler.INSTANCE.handleAlreadyExistingFaces(listTranslateHuaweiFaceToVisualDetectionFace);
        VisualDetectionHandler.INSTANCE.handleNewFaces(listTranslateHuaweiFaceToVisualDetectionFace);
        VisualDetectionHandler.INSTANCE.updateAllFaceSensorValues(mediaImage.getWidth(), mediaImage.getHeight());
        faceDetected = true;
        if (textDetected && poseDetected) {
            imageProxy.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void analyze$lambda$6$lambda$3(Exception exc) {
        StageActivity.messageHandler.obtainMessage(3, CollectionsKt.arrayListOf(CatroidApplication.getAppContext().getString(R.string.camera_error_face_detection))).sendToTarget();
        Log.e(INSTANCE.getClass().getSimpleName(), "Could not analyze image.", exc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void analyze$lambda$6$lambda$4(Image mediaImage, ImageProxy imageProxy, List list) {
        Intrinsics.checkNotNullParameter(mediaImage, "$mediaImage");
        Intrinsics.checkNotNullParameter(imageProxy, "$imageProxy");
        VisualDetectionHandler visualDetectionHandler = VisualDetectionHandler.INSTANCE;
        Intrinsics.checkNotNull(list);
        visualDetectionHandler.updateAllPoseSensorValuesHuawei(list, mediaImage.getWidth(), mediaImage.getHeight());
        poseDetected = true;
        if (faceDetected && textDetected) {
            imageProxy.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void analyze$lambda$6$lambda$5(Exception exc) {
        StageActivity.messageHandler.obtainMessage(3, CollectionsKt.arrayListOf(CatroidApplication.getAppContext().getString(R.string.camera_error_face_detection))).sendToTarget();
        Log.e(INSTANCE.getClass().getSimpleName(), "Could not analyze image.", exc);
    }
}
