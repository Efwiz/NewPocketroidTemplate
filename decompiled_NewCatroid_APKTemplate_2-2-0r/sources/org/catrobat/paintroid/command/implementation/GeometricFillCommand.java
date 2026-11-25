package org.catrobat.paintroid.command.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.tools.drawable.ShapeDrawable;

/* JADX INFO: compiled from: GeometricFillCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016R\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006!"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/GeometricFillCommand;", "Lorg/catrobat/paintroid/command/Command;", "shapeDrawable", "Lorg/catrobat/paintroid/tools/drawable/ShapeDrawable;", "pointX", "", "pointY", "boxRect", "Landroid/graphics/RectF;", "boxRotation", "", "paint", "Landroid/graphics/Paint;", "(Lorg/catrobat/paintroid/tools/drawable/ShapeDrawable;IILandroid/graphics/RectF;FLandroid/graphics/Paint;)V", "<set-?>", "getBoxRect", "()Landroid/graphics/RectF;", "getBoxRotation", "()F", "getPaint", "()Landroid/graphics/Paint;", "getPointX", "()I", "getPointY", "getShapeDrawable", "()Lorg/catrobat/paintroid/tools/drawable/ShapeDrawable;", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class GeometricFillCommand implements Command {
    private RectF boxRect;
    private float boxRotation;
    private Paint paint;
    private int pointX;
    private int pointY;
    private ShapeDrawable shapeDrawable;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public GeometricFillCommand(ShapeDrawable shapeDrawable, int i, int i2, RectF boxRect, float f, Paint paint) {
        Intrinsics.checkNotNullParameter(shapeDrawable, "shapeDrawable");
        Intrinsics.checkNotNullParameter(boxRect, "boxRect");
        Intrinsics.checkNotNullParameter(paint, "paint");
        this.shapeDrawable = shapeDrawable;
        this.pointX = i;
        this.pointY = i2;
        this.boxRect = boxRect;
        this.boxRotation = f;
        this.paint = paint;
    }

    public final ShapeDrawable getShapeDrawable() {
        return this.shapeDrawable;
    }

    public final int getPointX() {
        return this.pointX;
    }

    public final int getPointY() {
        return this.pointY;
    }

    public final RectF getBoxRect() {
        return this.boxRect;
    }

    public final float getBoxRotation() {
        return this.boxRotation;
    }

    public final Paint getPaint() {
        return this.paint;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        canvas.save();
        canvas.translate(getPointX(), getPointY());
        canvas.rotate(getBoxRotation());
        getShapeDrawable().draw(canvas, getBoxRect(), getPaint());
        canvas.restore();
    }
}
