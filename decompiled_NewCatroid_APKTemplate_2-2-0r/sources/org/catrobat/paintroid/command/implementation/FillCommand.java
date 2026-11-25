package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.tools.helper.FillAlgorithm;
import org.catrobat.paintroid.tools.helper.FillAlgorithmFactory;

/* JADX INFO: compiled from: FillCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0019"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/FillCommand;", "Lorg/catrobat/paintroid/command/Command;", "fillAlgorithmFactory", "Lorg/catrobat/paintroid/tools/helper/FillAlgorithmFactory;", "clickedPixel", "Landroid/graphics/Point;", "paint", "Landroid/graphics/Paint;", "colorTolerance", "", "(Lorg/catrobat/paintroid/tools/helper/FillAlgorithmFactory;Landroid/graphics/Point;Landroid/graphics/Paint;F)V", "<set-?>", "getClickedPixel", "()Landroid/graphics/Point;", "getColorTolerance", "()F", "getPaint", "()Landroid/graphics/Paint;", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class FillCommand implements Command {
    private Point clickedPixel;
    private float colorTolerance;
    private final FillAlgorithmFactory fillAlgorithmFactory;
    private Paint paint;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public FillCommand(FillAlgorithmFactory fillAlgorithmFactory, Point clickedPixel, Paint paint, float f) {
        Intrinsics.checkNotNullParameter(fillAlgorithmFactory, "fillAlgorithmFactory");
        Intrinsics.checkNotNullParameter(clickedPixel, "clickedPixel");
        Intrinsics.checkNotNullParameter(paint, "paint");
        this.fillAlgorithmFactory = fillAlgorithmFactory;
        this.clickedPixel = clickedPixel;
        this.paint = paint;
        this.colorTolerance = f;
    }

    public final Point getClickedPixel() {
        return this.clickedPixel;
    }

    public final Paint getPaint() {
        return this.paint;
    }

    public final float getColorTolerance() {
        return this.colorTolerance;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        LayerContracts.Layer currentLayer = layerModel.getCurrentLayer();
        if (currentLayer == null) {
            return;
        }
        Bitmap bitmap = currentLayer.getBitmap();
        int pixel = bitmap.getPixel(getClickedPixel().x, getClickedPixel().y);
        FillAlgorithm fillAlgorithmCreateFillAlgorithm = this.fillAlgorithmFactory.createFillAlgorithm();
        fillAlgorithmCreateFillAlgorithm.setParameters(bitmap, getClickedPixel(), getPaint().getColor(), pixel, getColorTolerance());
        fillAlgorithmCreateFillAlgorithm.performFilling();
    }
}
