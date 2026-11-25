package org.catrobat.paintroid.tools.common;

import android.graphics.Point;
import kotlin.Metadata;

/* JADX INFO: compiled from: ScrollBehavior.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH&¨\u0006\n"}, d2 = {"Lorg/catrobat/paintroid/tools/common/ScrollBehavior;", "", "getScrollDirection", "Landroid/graphics/Point;", "pointX", "", "pointY", "viewWidth", "", "viewHeight", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface ScrollBehavior {
    Point getScrollDirection(float pointX, float pointY, int viewWidth, int viewHeight);
}
