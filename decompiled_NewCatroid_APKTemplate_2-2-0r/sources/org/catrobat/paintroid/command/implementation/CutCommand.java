package org.catrobat.paintroid.command.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: CutCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001a"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/CutCommand;", "Lorg/catrobat/paintroid/command/Command;", "toolPosition", "Landroid/graphics/Point;", "boxWidth", "", "boxHeight", "boxRotation", "(Landroid/graphics/Point;FFF)V", "getBoxHeight", "()F", "boxRect", "Landroid/graphics/RectF;", "getBoxRotation", "getBoxWidth", "paint", "Landroid/graphics/Paint;", "getToolPosition", "()Landroid/graphics/Point;", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CutCommand implements Command {
    private final float boxHeight;
    private final RectF boxRect;
    private final float boxRotation;
    private final float boxWidth;
    private final Paint paint;
    private final Point toolPosition;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public CutCommand(Point toolPosition, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(toolPosition, "toolPosition");
        this.toolPosition = toolPosition;
        this.boxWidth = f;
        this.boxHeight = f2;
        this.boxRotation = f3;
        this.boxRect = new RectF((-f) / 2.0f, (-f2) / 2.0f, f / 2.0f, f2 / 2.0f);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setAlpha(0);
        Unit unit = Unit.INSTANCE;
        this.paint = paint;
    }

    public final Point getToolPosition() {
        return this.toolPosition;
    }

    public final float getBoxWidth() {
        return this.boxWidth;
    }

    public final float getBoxHeight() {
        return this.boxHeight;
    }

    public final float getBoxRotation() {
        return this.boxRotation;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        canvas.save();
        canvas.translate(this.toolPosition.x, this.toolPosition.y);
        canvas.rotate(this.boxRotation);
        canvas.drawRect(this.boxRect, this.paint);
        canvas.restore();
    }
}
