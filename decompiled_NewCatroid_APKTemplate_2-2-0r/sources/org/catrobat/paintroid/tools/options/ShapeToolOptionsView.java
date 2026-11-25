package org.catrobat.paintroid.tools.options;

import kotlin.Metadata;
import org.catrobat.paintroid.tools.drawable.DrawableShape;
import org.catrobat.paintroid.tools.drawable.DrawableStyle;

/* JADX INFO: compiled from: ShapeToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u000fJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH&¨\u0006\u0010"}, d2 = {"Lorg/catrobat/paintroid/tools/options/ShapeToolOptionsView;", "", "setCallback", "", "callback", "Lorg/catrobat/paintroid/tools/options/ShapeToolOptionsView$Callback;", "setDrawTypeActivated", "drawType", "Lorg/catrobat/paintroid/tools/drawable/DrawableStyle;", "setShapeActivated", "shape", "Lorg/catrobat/paintroid/tools/drawable/DrawableShape;", "setShapeOutlineWidth", "outlineWidth", "", "Callback", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface ShapeToolOptionsView {

    /* JADX INFO: compiled from: ShapeToolOptionsView.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&¨\u0006\f"}, d2 = {"Lorg/catrobat/paintroid/tools/options/ShapeToolOptionsView$Callback;", "", "setDrawType", "", "drawType", "Lorg/catrobat/paintroid/tools/drawable/DrawableStyle;", "setOutlineWidth", "outlineWidth", "", "setToolType", "shape", "Lorg/catrobat/paintroid/tools/drawable/DrawableShape;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Callback {
        void setDrawType(DrawableStyle drawType);

        void setOutlineWidth(int outlineWidth);

        void setToolType(DrawableShape shape);
    }

    void setCallback(Callback callback);

    void setDrawTypeActivated(DrawableStyle drawType);

    void setShapeActivated(DrawableShape shape);

    void setShapeOutlineWidth(int outlineWidth);
}
