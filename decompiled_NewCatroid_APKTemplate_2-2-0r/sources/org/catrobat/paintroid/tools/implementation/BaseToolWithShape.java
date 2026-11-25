package org.catrobat.paintroid.tools.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.reandroid.arsc.value.ResConfigBase;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;
import org.catrobat.paintroid.ui.Perspective;

/* JADX INFO: compiled from: BaseToolWithShape.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B7\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH&J \u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 H\u0014J(\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020 2\u0006\u0010%\u001a\u00020 2\u0006\u0010&\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u0014H\u0016J\u000e\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020 J\u001e\u0010*\u001a\u00020 2\u0006\u0010+\u001a\u00020 2\u0006\u0010,\u001a\u00020 2\u0006\u0010-\u001a\u00020 J\b\u0010.\u001a\u00020\u001bH&J\u0012\u0010/\u001a\u00020\u001b2\b\u00100\u001a\u0004\u0018\u000101H\u0016J\u0012\u00102\u001a\u00020\u001b2\b\u00100\u001a\u0004\u0018\u000101H\u0016R\u0010\u0010\u000f\u001a\u00020\u00108\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u00020\u00128\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0013\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0015\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0016\u001a\u00020\u00178\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0018\u0010\u0019¨\u00063"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/BaseToolWithShape;", "Lorg/catrobat/paintroid/tools/implementation/BaseTool;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "(Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;)V", "linePaint", "Landroid/graphics/Paint;", "metrics", "Landroid/util/DisplayMetrics;", "primaryShapeColor", "", "secondaryShapeColor", "toolPosition", "Landroid/graphics/PointF;", "getToolPosition$annotations", "()V", "drawShape", "", "canvas", "Landroid/graphics/Canvas;", "drawToolSpecifics", "boxWidth", "", "boxHeight", "getAutoScrollDirection", "Landroid/graphics/Point;", "pointX", "pointY", ResConfigBase.NAME_screenWidth, ResConfigBase.NAME_screenHeight, "getInverselyProportionalSizeForZoom", "defaultSize", "getStrokeWidthForZoom", "defaultStrokeWidth", "minStrokeWidth", "maxStrokeWidth", "onClickOnButton", "onRestoreInstanceState", "bundle", "Landroid/os/Bundle;", "onSaveInstanceState", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class BaseToolWithShape extends BaseTool {
    public final Paint linePaint;
    public final DisplayMetrics metrics;
    public int primaryShapeColor;
    public int secondaryShapeColor;
    public PointF toolPosition;

    public static /* synthetic */ void getToolPosition$annotations() {
    }

    public abstract void drawShape(Canvas canvas);

    protected void drawToolSpecifics(Canvas canvas, float boxWidth, float boxHeight) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    public abstract void onClickOnButton();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseToolWithShape(ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager) {
        PointF pointF;
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.primaryShapeColor = contextCallback.getColor(R.color.pocketpaint_main_rectangle_tool_primary_color);
        this.secondaryShapeColor = contextCallback.getColor(R.color.pocketpaint_colorAccentAlpha60);
        this.metrics = contextCallback.getDisplayMetrics();
        Perspective perspective = workspace.getPerspective();
        if (perspective.getSurfaceScale() > 1.0f) {
            pointF = new PointF(perspective.surfaceCenterX - perspective.surfaceTranslationX, perspective.surfaceCenterY - perspective.surfaceTranslationY);
        } else {
            pointF = new PointF(workspace.getWidth() / 2.0f, workspace.getHeight() / 2.0f);
        }
        this.toolPosition = pointF;
        Paint paint = new Paint();
        this.linePaint = paint;
        paint.setColor(this.primaryShapeColor);
        paint.setPathEffect(null);
    }

    public final float getStrokeWidthForZoom(float defaultStrokeWidth, float minStrokeWidth, float maxStrokeWidth) {
        return Math.min(maxStrokeWidth, Math.max(minStrokeWidth, (defaultStrokeWidth * this.metrics.density) / this.workspace.getScale()));
    }

    public final float getInverselyProportionalSizeForZoom(float defaultSize) {
        return (defaultSize * this.metrics.density) / this.workspace.getScale();
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        bundle.putFloat("TOOL_POSITION_X", this.toolPosition.x);
        bundle.putFloat("TOOL_POSITION_Y", this.toolPosition.y);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        PointF pointF = this.toolPosition;
        pointF.x = bundle.getFloat("TOOL_POSITION_X", pointF.x);
        PointF pointF2 = this.toolPosition;
        pointF2.y = bundle.getFloat("TOOL_POSITION_Y", pointF2.y);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public Point getAutoScrollDirection(float pointX, float pointY, int screenWidth, int screenHeight) {
        PointF surfacePointFromCanvasPoint = this.workspace.getSurfacePointFromCanvasPoint(this.toolPosition);
        return this.scrollBehavior.getScrollDirection(surfacePointFromCanvasPoint.x, surfacePointFromCanvasPoint.y, screenWidth, screenHeight);
    }
}
