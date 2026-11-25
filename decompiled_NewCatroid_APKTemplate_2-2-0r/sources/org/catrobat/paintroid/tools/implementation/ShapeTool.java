package org.catrobat.paintroid.tools.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.test.espresso.idling.CountingIdlingResource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.drawable.DrawableFactory;
import org.catrobat.paintroid.tools.drawable.DrawableShape;
import org.catrobat.paintroid.tools.drawable.DrawableStyle;
import org.catrobat.paintroid.tools.drawable.ShapeDrawable;
import org.catrobat.paintroid.tools.helper.ConversionKt;
import org.catrobat.paintroid.tools.options.ShapeToolOptionsView;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: ShapeTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0018\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020$2\u0006\u0010/\u001a\u000200H\u0016J \u00101\u001a\u00020-2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u000205H\u0014J\u0006\u00107\u001a\u00020\u0014J\u0012\u00108\u001a\u00020-2\b\u00109\u001a\u0004\u0018\u00010:H\u0016J\u0012\u0010;\u001a\u00020-2\b\u00109\u001a\u0004\u0018\u00010:H\u0016J\b\u0010<\u001a\u00020-H\u0016J\u0012\u0010=\u001a\u00020-2\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J\u0012\u0010@\u001a\u00020-2\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J\u0010\u0010A\u001a\u00020-2\u0006\u0010B\u001a\u00020\u001cH\u0002J \u0010C\u001a\u00020-2\u0006\u0010D\u001a\u00020'2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u000205H\u0002J\u000e\u0010E\u001a\u00020-2\u0006\u0010F\u001a\u00020\u0014J\u0010\u0010G\u001a\u00020:2\u0006\u00109\u001a\u00020:H\u0016R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001b\u001a\u00020\u001c¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010(\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+¨\u0006H"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/ShapeTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseToolWithRectangleShape;", "shapeToolOptionsView", "Lorg/catrobat/paintroid/tools/options/ShapeToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "(Lorg/catrobat/paintroid/tools/options/ShapeToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;J)V", "baseShape", "Lorg/catrobat/paintroid/tools/drawable/DrawableShape;", "getDrawTime", "()J", "setDrawTime", "(J)V", "drawableFactory", "Lorg/catrobat/paintroid/tools/drawable/DrawableFactory;", "shapeBitmapPaint", "Landroid/graphics/Paint;", "getShapeBitmapPaint", "()Landroid/graphics/Paint;", "shapeDrawType", "Lorg/catrobat/paintroid/tools/drawable/DrawableStyle;", "shapeDrawable", "Lorg/catrobat/paintroid/tools/drawable/ShapeDrawable;", "shapeOutlineWidth", "", "shapePreviewPaint", "shapePreviewRect", "Landroid/graphics/RectF;", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "changePaintColor", "", TypedValues.Custom.S_COLOR, "invalidate", "", "drawBitmap", "canvas", "Landroid/graphics/Canvas;", "boxWidth", "", "boxHeight", "getBaseShape", "handleDownAnimations", "coordinate", "Landroid/graphics/PointF;", "handleUpAnimations", "onClickOnButton", "onRestoreInstanceState", "bundle", "Landroid/os/Bundle;", "onSaveInstanceState", "preparePaint", "paint", "prepareShapeRectangle", "shapeRect", "setBaseShape", "shape", "toolPositionCoordinates", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ShapeTool extends BaseToolWithRectangleShape {
    private DrawableShape baseShape;
    private long drawTime;
    private final DrawableFactory drawableFactory;
    private final Paint shapeBitmapPaint;
    private DrawableStyle shapeDrawType;
    private ShapeDrawable shapeDrawable;
    private int shapeOutlineWidth;
    private final Paint shapePreviewPaint;
    private final RectF shapePreviewRect;
    private final ShapeToolOptionsView shapeToolOptionsView;

    /* JADX INFO: compiled from: ShapeTool.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DrawableStyle.values().length];
            iArr[DrawableStyle.FILL.ordinal()] = 1;
            iArr[DrawableStyle.STROKE.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public PointF toolPositionCoordinates(PointF coordinate) {
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        return coordinate;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public long getDrawTime() {
        return this.drawTime;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void setDrawTime(long j) {
        this.drawTime = j;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShapeTool(ShapeToolOptionsView shapeToolOptionsView, ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, final Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j) {
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(shapeToolOptionsView, "shapeToolOptionsView");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.drawTime = j;
        this.shapePreviewPaint = new Paint();
        this.shapePreviewRect = new RectF();
        DrawableFactory drawableFactory = new DrawableFactory();
        this.drawableFactory = drawableFactory;
        this.baseShape = DrawableShape.RECTANGLE;
        this.shapeDrawType = DrawableStyle.FILL;
        this.shapeDrawable = drawableFactory.createDrawable(this.baseShape);
        this.shapeOutlineWidth = 25;
        this.shapeBitmapPaint = new Paint();
        this.rotationEnabled = true;
        this.shapeToolOptionsView = shapeToolOptionsView;
        shapeToolOptionsView.setCallback(new ShapeToolOptionsView.Callback() { // from class: org.catrobat.paintroid.tools.implementation.ShapeTool.1
            @Override // org.catrobat.paintroid.tools.options.ShapeToolOptionsView.Callback
            public void setToolType(DrawableShape shape) {
                Intrinsics.checkNotNullParameter(shape, "shape");
                ShapeTool.this.setBaseShape(shape);
                workspace.invalidate();
            }

            @Override // org.catrobat.paintroid.tools.options.ShapeToolOptionsView.Callback
            public void setDrawType(DrawableStyle drawType) {
                Intrinsics.checkNotNullParameter(drawType, "drawType");
                ShapeTool.this.shapeDrawType = drawType;
                workspace.invalidate();
            }

            @Override // org.catrobat.paintroid.tools.options.ShapeToolOptionsView.Callback
            public void setOutlineWidth(int outlineWidth) {
                ShapeTool.this.shapeOutlineWidth = outlineWidth;
                workspace.invalidate();
            }
        });
        toolOptionsViewController.showDelayed();
    }

    public final Paint getShapeBitmapPaint() {
        return this.shapeBitmapPaint;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return ToolType.SHAPE;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleUpAnimations(PointF coordinate) {
        super.handleUp(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleDownAnimations(PointF coordinate) {
        super.handleDown(coordinate);
    }

    public final DrawableShape getBaseShape() {
        return this.baseShape;
    }

    public final void setBaseShape(DrawableShape shape) {
        Intrinsics.checkNotNullParameter(shape, "shape");
        this.baseShape = shape;
        this.shapeDrawable = this.drawableFactory.createDrawable(shape);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void changePaintColor(int color, boolean invalidate) {
        super.changePaintColor(color, invalidate);
        if (invalidate) {
            this.workspace.invalidate();
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseToolWithShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        bundle.putSerializable("BASE_SHAPE", this.baseShape);
        bundle.putSerializable("SHAPE_DRAW_TYPE", this.shapeDrawType);
        bundle.putInt("OUTLINE_WIDTH", this.shapeOutlineWidth);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseToolWithShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        DrawableShape drawableShape = (DrawableShape) (bundle == null ? null : bundle.getSerializable("BASE_SHAPE"));
        if (drawableShape == null) {
            return;
        }
        DrawableStyle drawableStyle = (DrawableStyle) (bundle == null ? null : bundle.getSerializable("SHAPE_DRAW_TYPE"));
        if (drawableStyle == null) {
            return;
        }
        Integer numValueOf = bundle != null ? Integer.valueOf(bundle.getInt("OUTLINE_WIDTH")) : null;
        if (numValueOf == null) {
            return;
        }
        int iIntValue = numValueOf.intValue();
        if (this.baseShape == drawableShape && this.shapeDrawType == drawableStyle) {
            return;
        }
        this.baseShape = drawableShape;
        this.shapeDrawType = drawableStyle;
        this.shapeOutlineWidth = iIntValue;
        this.shapeDrawable = this.drawableFactory.createDrawable(drawableShape);
        this.shapeToolOptionsView.setShapeActivated(drawableShape);
        this.shapeToolOptionsView.setDrawTypeActivated(drawableStyle);
        this.shapeToolOptionsView.setShapeOutlineWidth(iIntValue);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape
    protected void drawBitmap(Canvas canvas, float boxWidth, float boxHeight) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        this.shapePreviewPaint.set(this.toolPaint.getPreviewPaint());
        preparePaint(this.shapePreviewPaint);
        prepareShapeRectangle(this.shapePreviewRect, boxWidth, boxHeight);
        this.shapeDrawable.draw(canvas, this.shapePreviewRect, this.shapePreviewPaint);
    }

    private final void prepareShapeRectangle(RectF shapeRect, float boxWidth, float boxHeight) {
        shapeRect.setEmpty();
        float scale = 10.0f / (this.workspace.getScale() > 1.0f ? this.workspace.getScale() : 1.0f);
        if (scale <= 0.75f) {
            scale = 0.75f;
        }
        float f = 2;
        shapeRect.inset(scale - (boxWidth / f), scale - (boxHeight / f));
        if (this.shapePreviewPaint.getStyle() == Paint.Style.STROKE) {
            int i = this.shapeOutlineWidth;
            shapeRect.inset(i / 2.0f, i / 2.0f);
        }
    }

    private final void preparePaint(Paint paint) {
        int i = WhenMappings.$EnumSwitchMapping$0[this.shapeDrawType.ordinal()];
        if (i == 1) {
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeJoin(Paint.Join.MITER);
        } else {
            if (i != 2) {
                return;
            }
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(this.shapeOutlineWidth);
            paint.setStrokeCap(Paint.Cap.BUTT);
            paint.setStrokeJoin(Paint.Join.MITER);
            paint.setStrokeMiter(paint.getStrokeMiter() * 4);
            paint.setAntiAlias(this.shapeOutlineWidth > 1);
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    public void onClickOnButton() {
        float f = 2;
        float f2 = (-this.boxWidth) / f;
        float width = this.workspace.getWidth() + (this.boxWidth / f);
        float f3 = this.toolPosition.x;
        if (f2 > f3 || f3 > width) {
            return;
        }
        float f4 = (-this.boxHeight) / f;
        float height = this.workspace.getHeight() + (this.boxHeight / f);
        float f5 = this.toolPosition.y;
        if (f4 > f5 || f5 > height) {
            return;
        }
        this.shapeBitmapPaint.set(this.toolPaint.getBitmapPaint());
        RectF rectF = new RectF();
        preparePaint(this.shapeBitmapPaint);
        prepareShapeRectangle(rectF, this.boxWidth, this.boxHeight);
        this.commandManager.addCommand(this.commandFactory.createGeometricFillCommand(this.shapeDrawable, ConversionKt.toPoint(this.toolPosition), rectF, this.boxRotation, this.shapeBitmapPaint));
        highlightBox();
    }
}
