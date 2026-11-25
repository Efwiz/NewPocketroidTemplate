package org.catrobat.catroid.camera;

import androidx.camera.core.ImageProxy;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/DetectorsCompleteListener.class
  classes12.dex
 */
/* JADX INFO: compiled from: CatdroidImageAnalyzer.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/camera/DetectorsCompleteListener;", "", "numActiveDetectors", "", "imageProxy", "Landroidx/camera/core/ImageProxy;", "(ILandroidx/camera/core/ImageProxy;)V", "finishedDetectors", "onComplete", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DetectorsCompleteListener {
    private int finishedDetectors;
    private final ImageProxy imageProxy;
    private final int numActiveDetectors;

    public DetectorsCompleteListener(int i, ImageProxy imageProxy) {
        Intrinsics.checkNotNullParameter(imageProxy, "imageProxy");
        this.numActiveDetectors = i;
        this.imageProxy = imageProxy;
    }

    public final void onComplete() {
        int i = this.finishedDetectors + 1;
        this.finishedDetectors = i;
        if (i >= this.numActiveDetectors) {
            this.imageProxy.close();
        }
    }
}
