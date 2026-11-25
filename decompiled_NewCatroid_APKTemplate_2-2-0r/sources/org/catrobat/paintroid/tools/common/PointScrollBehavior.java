package org.catrobat.paintroid.tools.common;

import android.graphics.Point;
import kotlin.Metadata;

/* JADX INFO: compiled from: PointScrollBehavior.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/paintroid/tools/common/PointScrollBehavior;", "Lorg/catrobat/paintroid/tools/common/ScrollBehavior;", "scrollTolerance", "", "(I)V", "getScrollDirection", "Landroid/graphics/Point;", "pointX", "", "pointY", "viewWidth", "viewHeight", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PointScrollBehavior implements ScrollBehavior {
    private final int scrollTolerance;

    public PointScrollBehavior(int i) {
        this.scrollTolerance = i;
    }

    @Override // org.catrobat.paintroid.tools.common.ScrollBehavior
    public Point getScrollDirection(float pointX, float pointY, int viewWidth, int viewHeight) {
        int i = this.scrollTolerance;
        int i2 = pointX < ((float) i) ? 1 : 0;
        if (pointX > viewWidth - i) {
            i2 = -1;
        }
        return new Point(i2, pointY <= ((float) (viewHeight - i)) ? pointY >= ((float) i) ? 0 : 1 : -1);
    }
}
