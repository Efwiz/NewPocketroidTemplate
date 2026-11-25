package org.catrobat.paintroid.tools.options;

import kotlin.Metadata;
import org.catrobat.paintroid.ui.tools.NumberRangeFilter;

/* JADX INFO: compiled from: TransformToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0001\u000fJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\bH&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&¨\u0006\u0010"}, d2 = {"Lorg/catrobat/paintroid/tools/options/TransformToolOptionsView;", "", "setCallback", "", "callback", "Lorg/catrobat/paintroid/tools/options/TransformToolOptionsView$Callback;", "setHeight", "height", "", "setHeightFilter", "numberRangeFilter", "Lorg/catrobat/paintroid/ui/tools/NumberRangeFilter;", "setWidth", "width", "setWidthFilter", "Callback", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface TransformToolOptionsView {

    /* JADX INFO: compiled from: TransformToolOptionsView.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\u0003H&J\b\u0010\b\u001a\u00020\u0003H&J\b\u0010\t\u001a\u00020\u0003H&J\b\u0010\n\u001a\u00020\u0003H&J\b\u0010\u000b\u001a\u00020\u0003H&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000eH&J\b\u0010\u0011\u001a\u00020\u0003H&¨\u0006\u0012"}, d2 = {"Lorg/catrobat/paintroid/tools/options/TransformToolOptionsView$Callback;", "", "applyResizeClicked", "", "resizePercentage", "", "autoCropClicked", "flipHorizontalClicked", "flipVerticalClicked", "hideToolOptions", "rotateClockwiseClicked", "rotateCounterClockwiseClicked", "setBoxHeight", "boxHeight", "", "setBoxWidth", "boxWidth", "setCenterClicked", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Callback {
        void applyResizeClicked(int resizePercentage);

        void autoCropClicked();

        void flipHorizontalClicked();

        void flipVerticalClicked();

        void hideToolOptions();

        void rotateClockwiseClicked();

        void rotateCounterClockwiseClicked();

        void setBoxHeight(float boxHeight);

        void setBoxWidth(float boxWidth);

        void setCenterClicked();
    }

    void setCallback(Callback callback);

    void setHeight(int height);

    void setHeightFilter(NumberRangeFilter numberRangeFilter);

    void setWidth(int width);

    void setWidthFilter(NumberRangeFilter numberRangeFilter);
}
