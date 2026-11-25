package org.catrobat.paintroid.tools.options;

import android.graphics.Paint;
import kotlin.Metadata;

/* JADX INFO: compiled from: SprayToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u000eJ\b\u0010\u0002\u001a\u00020\u0003H&J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH&¨\u0006\u000f"}, d2 = {"Lorg/catrobat/paintroid/tools/options/SprayToolOptionsView;", "", "getRadius", "", "setCallback", "", "callback", "Lorg/catrobat/paintroid/tools/options/SprayToolOptionsView$Callback;", "setCurrentPaint", "paint", "Landroid/graphics/Paint;", "setRadius", "radius", "", "Callback", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface SprayToolOptionsView {

    /* JADX INFO: compiled from: SprayToolOptionsView.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/paintroid/tools/options/SprayToolOptionsView$Callback;", "", "radiusChanged", "", "radius", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Callback {
        void radiusChanged(int radius);
    }

    float getRadius();

    void setCallback(Callback callback);

    void setCurrentPaint(Paint paint);

    void setRadius(int radius);
}
