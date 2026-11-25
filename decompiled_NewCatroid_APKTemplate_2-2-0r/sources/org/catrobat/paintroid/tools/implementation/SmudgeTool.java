package org.catrobat.paintroid.tools.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.test.espresso.idling.CountingIdlingResource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.common.CommonBrushChangedListener;
import org.catrobat.paintroid.tools.common.CommonBrushPreviewListener;
import org.catrobat.paintroid.tools.options.SmudgeToolOptionsView;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: SmudgeTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\b\u0010:\u001a\u00020;H\u0002J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J\u0010\u0010@\u001a\u00020\u00122\u0006\u0010A\u001a\u00020\u0012H\u0002J\u0012\u0010B\u001a\u00020;2\b\u0010C\u001a\u0004\u0018\u00010-H\u0016J\u0012\u0010D\u001a\u00020=2\b\u0010C\u001a\u0004\u0018\u00010-H\u0016J\u001a\u0010E\u001a\u00020;2\b\u0010C\u001a\u0004\u0018\u00010-2\u0006\u0010F\u001a\u00020;H\u0016J\u0012\u0010G\u001a\u00020;2\b\u0010C\u001a\u0004\u0018\u00010-H\u0016J\u0012\u0010H\u001a\u00020=2\b\u0010C\u001a\u0004\u0018\u00010-H\u0016J\u0010\u0010I\u001a\u00020-2\u0006\u0010C\u001a\u00020-H\u0016J\u000e\u0010J\u001a\u00020=2\u0006\u0010K\u001a\u00020*J\u000e\u0010L\u001a\u00020=2\u0006\u0010M\u001a\u00020*R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R$\u0010\u0019\u001a\u00020\u001a8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R$\u0010!\u001a\u00020\u001a8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\"\u0010\u001c\u001a\u0004\b#\u0010\u001e\"\u0004\b$\u0010 R$\u0010%\u001a\u00020\u001a8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b&\u0010\u001c\u001a\u0004\b'\u0010\u001e\"\u0004\b(\u0010 R\u000e\u0010)\u001a\u00020*X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,8\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b.\u0010\u001c\u001a\u0004\b/\u00100R$\u00101\u001a\u00020\u001a8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b2\u0010\u001c\u001a\u0004\b3\u0010\u001e\"\u0004\b4\u0010 R\u0010\u00105\u001a\u0004\u0018\u00010-X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00106\u001a\u0002078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u00109¨\u0006N"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/SmudgeTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseTool;", "smudgeToolOptionsView", "Lorg/catrobat/paintroid/tools/options/SmudgeToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "(Lorg/catrobat/paintroid/tools/options/SmudgeToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;)V", "currentBitmap", "Landroid/graphics/Bitmap;", "drawTime", "", "getDrawTime", "()J", "setDrawTime", "(J)V", "maxPressure", "", "getMaxPressure$annotations", "()V", "getMaxPressure", "()F", "setMaxPressure", "(F)V", "maxSmudgeSize", "getMaxSmudgeSize$annotations", "getMaxSmudgeSize", "setMaxSmudgeSize", "minSmudgeSize", "getMinSmudgeSize$annotations", "getMinSmudgeSize", "setMinSmudgeSize", "numOfPointsOnPath", "", "pointArray", "", "Landroid/graphics/PointF;", "getPointArray$annotations", "getPointArray", "()Ljava/util/List;", "pressure", "getPressure$annotations", "getPressure", "setPressure", "prevPoint", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "currentBitmapHasColor", "", "draw", "", "canvas", "Landroid/graphics/Canvas;", "getBitmapClippedCircle", "bitmap", "handleDown", "coordinate", "handleDownAnimations", "handleMove", "shouldAnimate", "handleUp", "handleUpAnimations", "toolPositionCoordinates", "updateDrag", "dragInPercent", "updatePressure", "pressureInPercent", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SmudgeTool extends BaseTool {
    private Bitmap currentBitmap;
    private long drawTime;
    private float maxPressure;
    private float maxSmudgeSize;
    private float minSmudgeSize;
    private int numOfPointsOnPath;
    private final List<PointF> pointArray;
    private float pressure;
    private PointF prevPoint;

    public static /* synthetic */ void getMaxPressure$annotations() {
    }

    public static /* synthetic */ void getMaxSmudgeSize$annotations() {
    }

    public static /* synthetic */ void getMinSmudgeSize$annotations() {
    }

    public static /* synthetic */ void getPointArray$annotations() {
    }

    public static /* synthetic */ void getPressure$annotations() {
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public PointF toolPositionCoordinates(PointF coordinate) {
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        return coordinate;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmudgeTool(SmudgeToolOptionsView smudgeToolOptionsView, ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager) {
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(smudgeToolOptionsView, "smudgeToolOptionsView");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.numOfPointsOnPath = -1;
        this.pressure = this.maxPressure;
        this.maxSmudgeSize = toolPaint.getStrokeWidth();
        this.pointArray = new ArrayList();
        smudgeToolOptionsView.setBrushChangedListener(new CommonBrushChangedListener(this));
        smudgeToolOptionsView.setBrushPreviewListener(new CommonBrushPreviewListener(toolPaint, getToolType()));
        smudgeToolOptionsView.setCurrentPaint(toolPaint.getBitmapPaint());
        smudgeToolOptionsView.setStrokeCapButtonChecked(toolPaint.getStrokeCap());
        smudgeToolOptionsView.setCallback(new SmudgeToolOptionsView.Callback() { // from class: org.catrobat.paintroid.tools.implementation.SmudgeTool.1
            @Override // org.catrobat.paintroid.tools.options.SmudgeToolOptionsView.Callback
            public void onPressureChanged(int pressure) {
                SmudgeTool.this.updatePressure(pressure);
            }

            @Override // org.catrobat.paintroid.tools.options.SmudgeToolOptionsView.Callback
            public void onDragChanged(int drag) {
                SmudgeTool.this.updateDrag(drag);
            }
        });
        updatePressure(50);
        updateDrag(50);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public long getDrawTime() {
        return this.drawTime;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void setDrawTime(long j) {
        this.drawTime = j;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleUpAnimations(PointF coordinate) {
        super.handleUp(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleDownAnimations(PointF coordinate) {
        super.handleDown(coordinate);
    }

    public final float getMaxPressure() {
        return this.maxPressure;
    }

    public final void setMaxPressure(float f) {
        this.maxPressure = f;
    }

    public final float getPressure() {
        return this.pressure;
    }

    public final void setPressure(float f) {
        this.pressure = f;
    }

    public final float getMaxSmudgeSize() {
        return this.maxSmudgeSize;
    }

    public final void setMaxSmudgeSize(float f) {
        this.maxSmudgeSize = f;
    }

    public final float getMinSmudgeSize() {
        return this.minSmudgeSize;
    }

    public final void setMinSmudgeSize(float f) {
        this.minSmudgeSize = f;
    }

    public final List<PointF> getPointArray() {
        return this.pointArray;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return ToolType.SMUDGE;
    }

    public final void updatePressure(int pressureInPercent) {
        float f = (pressureInPercent * 0.0014999998f) + 0.85f;
        this.maxPressure = f;
        this.pressure = f;
    }

    public final void updateDrag(int dragInPercent) {
        this.minSmudgeSize = (this.maxSmudgeSize / 100.0f) * dragInPercent;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleDown(PointF coordinate) {
        if (coordinate == null) {
            return false;
        }
        if (this.maxSmudgeSize != this.toolPaint.getStrokeWidth()) {
            float f = this.minSmudgeSize / this.maxSmudgeSize;
            float strokeWidth = this.toolPaint.getStrokeWidth();
            this.maxSmudgeSize = strokeWidth;
            this.minSmudgeSize = strokeWidth * f;
        }
        Bitmap bitmapOfCurrentLayer = this.workspace.getBitmapOfCurrentLayer();
        float f2 = this.maxSmudgeSize;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) f2, (int) f2, Bitmap.Config.ARGB_8888);
        this.currentBitmap = bitmapCreateBitmap;
        if (bitmapCreateBitmap != null) {
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.translate((-coordinate.x) + (getMaxSmudgeSize() / 2.0f), (-coordinate.y) + (getMaxSmudgeSize() / 2.0f));
            canvas.rotate(-0.0f, coordinate.x, coordinate.y);
            if (bitmapOfCurrentLayer != null) {
                canvas.drawBitmap(bitmapOfCurrentLayer, 0.0f, 0.0f, (Paint) null);
            }
            if (this.toolPaint.getStrokeCap() == Paint.Cap.ROUND) {
                this.currentBitmap = getBitmapClippedCircle(bitmapCreateBitmap);
            }
        }
        if (!currentBitmapHasColor()) {
            Bitmap bitmap = this.currentBitmap;
            if (bitmap != null) {
                bitmap.recycle();
            }
            this.currentBitmap = null;
            return false;
        }
        PointF pointF = new PointF(coordinate.x, coordinate.y);
        this.prevPoint = pointF;
        getPointArray().add(new PointF(pointF.x, pointF.y));
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleMove(PointF coordinate, boolean shouldAnimate) {
        if (coordinate == null || this.currentBitmap == null || this.pressure < 0.8f) {
            return false;
        }
        PointF pointF = this.prevPoint;
        if (pointF == null) {
            return true;
        }
        float f = coordinate.x - pointF.x;
        float f2 = coordinate.y - pointF.y;
        int iSqrt = (int) (((float) Math.sqrt((f * f) + (f2 * f2))) / 3.0f);
        float f3 = iSqrt;
        float f4 = f / f3;
        float f5 = f2 / f3;
        for (int i = 0; i < iSqrt; i++) {
            pointF.x += f4;
            pointF.y += f5;
            setPressure(getPressure() - 0.004f);
            getPointArray().add(new PointF(pointF.x, pointF.y));
        }
        return true;
    }

    private final Bitmap getBitmapClippedCircle(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Path path = new Path();
        int i = height / 2;
        path.addCircle(width / 2, i, Math.min(width, i), Path.Direction.CCW);
        Canvas canvas = new Canvas(outputBitmap);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        Intrinsics.checkNotNullExpressionValue(outputBitmap, "outputBitmap");
        return outputBitmap;
    }

    private final boolean currentBitmapHasColor() {
        int width;
        Bitmap bitmap = this.currentBitmap;
        if (bitmap != null && (width = bitmap.getWidth()) > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                int height = bitmap.getHeight();
                if (height > 0) {
                    int i3 = 0;
                    while (true) {
                        int i4 = i3 + 1;
                        if (bitmap.getPixel(i, i3) != 0) {
                            return true;
                        }
                        if (i4 >= height) {
                            break;
                        }
                        i3 = i4;
                    }
                }
                if (i2 >= width) {
                    break;
                }
                i = i2;
            }
        }
        return false;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleUp(PointF coordinate) {
        Bitmap bitmap;
        int size;
        if (coordinate == null || this.pointArray.isEmpty() || (bitmap = this.currentBitmap) == null) {
            return false;
        }
        if (bitmap != null) {
            this.commandManager.addCommand(this.commandFactory.createSmudgePathCommand(bitmap, getPointArray(), getMaxPressure(), getMaxSmudgeSize(), getMinSmudgeSize()));
        }
        int i = this.numOfPointsOnPath;
        if (i < 0) {
            size = this.pointArray.size();
        } else {
            size = (i + this.pointArray.size()) / 2;
        }
        this.numOfPointsOnPath = size;
        this.pressure = this.maxPressure;
        this.pointArray.clear();
        Bitmap bitmap2 = this.currentBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
        }
        this.currentBitmap = null;
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void draw(Canvas canvas) {
        float size;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.pointArray.isEmpty()) {
            return;
        }
        List<PointF> mutableList = CollectionsKt.toMutableList((Collection) this.pointArray);
        int i = this.numOfPointsOnPath;
        if (i < 0) {
            size = (this.maxSmudgeSize - this.minSmudgeSize) / mutableList.size();
        } else {
            size = (this.maxSmudgeSize - this.minSmudgeSize) / i;
        }
        float f = this.maxSmudgeSize;
        float f2 = this.maxPressure;
        ColorMatrix colorMatrix = new ColorMatrix();
        Paint paint = new Paint();
        Bitmap bitmap = this.currentBitmap;
        Bitmap bitmapCopy = bitmap == null ? null : bitmap.copy(Bitmap.Config.ARGB_8888, false);
        for (PointF pointF : mutableList) {
            colorMatrix.setScale(1.0f, 1.0f, 1.0f, f2);
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) getMaxSmudgeSize(), (int) getMaxSmudgeSize(), Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(bitmapCreateBitmap);
            if (bitmapCopy != null) {
                canvas2.drawBitmap(bitmapCopy, 0.0f, 0.0f, paint);
            }
            if (bitmapCopy != null) {
                bitmapCopy.recycle();
            }
            float f3 = (-f) / 2.0f;
            float f4 = f / 2.0f;
            RectF rectF = new RectF(f3, f3, f4, f4);
            canvas.save();
            canvas.clipRect(0, 0, this.workspace.getWidth(), this.workspace.getHeight());
            canvas.translate(pointF.x, pointF.y);
            if (bitmapCreateBitmap != null) {
                canvas.drawBitmap(bitmapCreateBitmap, (Rect) null, rectF, new Paint(4));
            }
            canvas.restore();
            f -= size;
            f2 -= 0.004f;
            bitmapCopy = bitmapCreateBitmap;
        }
        if (bitmapCopy == null) {
            return;
        }
        bitmapCopy.recycle();
    }
}
