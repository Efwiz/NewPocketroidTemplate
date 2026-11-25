package org.catrobat.paintroid.command.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: SprayCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0012"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/SprayCommand;", "Lorg/catrobat/paintroid/command/Command;", "sprayedPoints", "", "paint", "Landroid/graphics/Paint;", "([FLandroid/graphics/Paint;)V", "getPaint", "()Landroid/graphics/Paint;", "getSprayedPoints", "()[F", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SprayCommand implements Command {
    private final Paint paint;
    private final float[] sprayedPoints;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public SprayCommand(float[] sprayedPoints, Paint paint) {
        Intrinsics.checkNotNullParameter(sprayedPoints, "sprayedPoints");
        Intrinsics.checkNotNullParameter(paint, "paint");
        this.sprayedPoints = sprayedPoints;
        this.paint = paint;
    }

    public final float[] getSprayedPoints() {
        return this.sprayedPoints;
    }

    public final Paint getPaint() {
        return this.paint;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        canvas.drawPoints(this.sprayedPoints, this.paint);
    }
}
