package org.catrobat.paintroid.tools;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.arsc.value.ResConfigBase;
import kotlin.Metadata;

/* JADX INFO: compiled from: Tool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u00016J\u001a\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015H&J\u0010\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0018H&J\u0010\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0013H&J\u0010\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dH&J(\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020\u0013H&J\b\u0010%\u001a\u00020\u0015H&J\u0012\u0010&\u001a\u00020\u00152\b\u0010'\u001a\u0004\u0018\u00010(H&J\u0012\u0010)\u001a\u00020\u00112\b\u0010'\u001a\u0004\u0018\u00010(H&J\u001c\u0010*\u001a\u00020\u00152\b\u0010'\u001a\u0004\u0018\u00010(2\b\b\u0002\u0010+\u001a\u00020\u0015H&J\u0012\u0010,\u001a\u00020\u00152\b\u0010'\u001a\u0004\u0018\u00010(H&J\u0012\u0010-\u001a\u00020\u00112\b\u0010'\u001a\u0004\u0018\u00010(H&J\u0012\u0010.\u001a\u00020\u00112\b\u0010/\u001a\u0004\u0018\u000100H&J\u0012\u00101\u001a\u00020\u00112\b\u0010/\u001a\u0004\u0018\u000100H&J\u0010\u00102\u001a\u00020\u00112\u0006\u00103\u001a\u000204H&J\u0010\u00105\u001a\u00020(2\u0006\u0010'\u001a\u00020(H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\u00020\u0007X¦\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u00067"}, d2 = {"Lorg/catrobat/paintroid/tools/Tool;", "", "drawPaint", "Landroid/graphics/Paint;", "getDrawPaint", "()Landroid/graphics/Paint;", "drawTime", "", "getDrawTime", "()J", "setDrawTime", "(J)V", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "changePaintColor", "", TypedValues.Custom.S_COLOR, "", "invalidate", "", "changePaintStrokeCap", "cap", "Landroid/graphics/Paint$Cap;", "changePaintStrokeWidth", "strokeWidth", "draw", "canvas", "Landroid/graphics/Canvas;", "getAutoScrollDirection", "Landroid/graphics/Point;", "pointX", "", "pointY", ResConfigBase.NAME_screenWidth, ResConfigBase.NAME_screenHeight, "handToolMode", "handleDown", "coordinate", "Landroid/graphics/PointF;", "handleDownAnimations", "handleMove", "shouldAnimate", "handleUp", "handleUpAnimations", "onRestoreInstanceState", "bundle", "Landroid/os/Bundle;", "onSaveInstanceState", "resetInternalState", "stateChange", "Lorg/catrobat/paintroid/tools/Tool$StateChange;", "toolPositionCoordinates", "StateChange", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface Tool {

    /* JADX INFO: compiled from: Tool.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/tools/Tool$StateChange;", "", "(Ljava/lang/String;I)V", "ALL", "RESET_INTERNAL_STATE", "NEW_IMAGE_LOADED", "MOVE_CANCELED", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum StateChange {
        ALL,
        RESET_INTERNAL_STATE,
        NEW_IMAGE_LOADED,
        MOVE_CANCELED
    }

    void changePaintColor(int color, boolean invalidate);

    void changePaintStrokeCap(Paint.Cap cap);

    void changePaintStrokeWidth(int strokeWidth);

    void draw(Canvas canvas);

    Point getAutoScrollDirection(float pointX, float pointY, int screenWidth, int screenHeight);

    Paint getDrawPaint();

    long getDrawTime();

    ToolType getToolType();

    boolean handToolMode();

    boolean handleDown(PointF coordinate);

    void handleDownAnimations(PointF coordinate);

    boolean handleMove(PointF coordinate, boolean shouldAnimate);

    boolean handleUp(PointF coordinate);

    void handleUpAnimations(PointF coordinate);

    void onRestoreInstanceState(Bundle bundle);

    void onSaveInstanceState(Bundle bundle);

    void resetInternalState(StateChange stateChange);

    void setDrawTime(long j);

    PointF toolPositionCoordinates(PointF coordinate);

    /* JADX INFO: compiled from: Tool.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public static final class DefaultImpls {
        public static /* synthetic */ boolean handleMove$default(Tool tool, PointF pointF, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: handleMove");
            }
            if ((i & 2) != 0) {
                z = false;
            }
            return tool.handleMove(pointF, z);
        }

        public static /* synthetic */ void changePaintColor$default(Tool tool, int i, boolean z, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: changePaintColor");
            }
            if ((i2 & 2) != 0) {
                z = true;
            }
            tool.changePaintColor(i, z);
        }
    }
}
