package org.catrobat.paintroid.tools.helper;

import android.graphics.Bitmap;
import android.graphics.Point;
import kotlin.Metadata;

/* JADX INFO: compiled from: FillAlgorithm.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J0\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH&¨\u0006\u000e"}, d2 = {"Lorg/catrobat/paintroid/tools/helper/FillAlgorithm;", "", "performFilling", "", "setParameters", "bitmap", "Landroid/graphics/Bitmap;", "clickedPixel", "Landroid/graphics/Point;", "targetColor", "", "replacementColor", "colorToleranceThreshold", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface FillAlgorithm {
    void performFilling();

    void setParameters(Bitmap bitmap, Point clickedPixel, int targetColor, int replacementColor, float colorToleranceThreshold);
}
