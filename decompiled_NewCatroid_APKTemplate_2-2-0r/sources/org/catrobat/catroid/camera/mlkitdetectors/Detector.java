package org.catrobat.catroid.camera.mlkitdetectors;

import android.media.Image;
import com.google.mlkit.vision.common.InputImage;
import kotlin.Metadata;
import org.catrobat.catroid.camera.DetectorsCompleteListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/mlkitdetectors/Detector.class
  classes12.dex
 */
/* JADX INFO: compiled from: Detector.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/camera/mlkitdetectors/Detector;", "", "processImage", "", "mediaImage", "Landroid/media/Image;", "inputImage", "Lcom/google/mlkit/vision/common/InputImage;", "onCompleteListener", "Lorg/catrobat/catroid/camera/DetectorsCompleteListener;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Detector {
    void processImage(Image mediaImage, InputImage inputImage, DetectorsCompleteListener onCompleteListener);
}
