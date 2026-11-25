package org.catrobat.catroid.camera;

import android.content.Context;
import android.media.Image;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import com.google.mlkit.vision.common.InputImage;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.camera.mlkitdetectors.Detector;
import org.catrobat.catroid.camera.mlkitdetectors.FaceDetector;
import org.catrobat.catroid.camera.mlkitdetectors.ObjectDetector;
import org.catrobat.catroid.camera.mlkitdetectors.PoseDetector;
import org.catrobat.catroid.camera.mlkitdetectors.TextDetector;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/CatdroidImageAnalyzer.class
  classes12.dex
 */
/* JADX INFO: compiled from: CatdroidImageAnalyzer.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0017J\u0010\u0010\r\u001a\u00020\n2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/camera/CatdroidImageAnalyzer;", "Landroidx/camera/core/ImageAnalysis$Analyzer;", "()V", "DETECTION_PROCESS_ERROR_MESSAGE", "", "activeDetectors", "Ljava/util/ArrayList;", "Lorg/catrobat/catroid/camera/mlkitdetectors/Detector;", "Lkotlin/collections/ArrayList;", "analyze", "", "imageProxy", "Landroidx/camera/core/ImageProxy;", "setActiveDetectorsWithContext", "context", "Landroid/content/Context;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CatdroidImageAnalyzer implements ImageAnalysis.Analyzer {
    public static final String DETECTION_PROCESS_ERROR_MESSAGE = "Could not analyze image.";
    public static final CatdroidImageAnalyzer INSTANCE = new CatdroidImageAnalyzer();
    private static final ArrayList<Detector> activeDetectors = new ArrayList<>();

    private CatdroidImageAnalyzer() {
    }

    @Override // androidx.camera.core.ImageAnalysis.Analyzer
    public void analyze(ImageProxy imageProxy) {
        Intrinsics.checkNotNullParameter(imageProxy, "imageProxy");
        Image image = imageProxy.getImage();
        if (image != null) {
            ArrayList<Detector> arrayList = activeDetectors;
            DetectorsCompleteListener detectorsCompleteListener = new DetectorsCompleteListener(arrayList.size(), imageProxy);
            InputImage inputImageFromMediaImage = InputImage.fromMediaImage(image, imageProxy.getImageInfo().getRotationDegrees());
            Intrinsics.checkNotNullExpressionValue(inputImageFromMediaImage, "fromMediaImage(...)");
            Iterator<Detector> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().processImage(image, inputImageFromMediaImage, detectorsCompleteListener);
            }
        }
    }

    public final void setActiveDetectorsWithContext(Context context) {
        ArrayList<Detector> arrayList = activeDetectors;
        arrayList.clear();
        if (context != null) {
            if (SettingsFragment.isAIFaceDetectionSharedPreferenceEnabled(context)) {
                arrayList.add(FaceDetector.INSTANCE);
            }
            if (SettingsFragment.isAIPoseDetectionSharedPreferenceEnabled(context)) {
                arrayList.add(PoseDetector.INSTANCE);
            }
            if (SettingsFragment.isAITextRecognitionSharedPreferenceEnabled(context)) {
                arrayList.add(TextDetector.INSTANCE);
            }
            if (SettingsFragment.isAIObjectDetectionSharedPreferenceEnabled(context)) {
                arrayList.add(ObjectDetector.INSTANCE);
            }
        }
    }
}
