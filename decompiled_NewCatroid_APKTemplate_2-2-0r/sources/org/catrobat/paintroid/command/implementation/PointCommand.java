package org.catrobat.paintroid.command.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: PointCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/PointCommand;", "Lorg/catrobat/paintroid/command/Command;", "paint", "Landroid/graphics/Paint;", KHRLightsPunctual.GLTFLight.TYPE_POINT, "Landroid/graphics/PointF;", "(Landroid/graphics/Paint;Landroid/graphics/PointF;)V", "getPaint", "()Landroid/graphics/Paint;", "setPaint", "(Landroid/graphics/Paint;)V", "<set-?>", "getPoint", "()Landroid/graphics/PointF;", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PointCommand implements Command {
    private Paint paint;
    private PointF point;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public PointCommand(Paint paint, PointF point) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(point, "point");
        this.paint = paint;
        this.point = point;
    }

    public final Paint getPaint() {
        return this.paint;
    }

    public final void setPaint(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "<set-?>");
        this.paint = paint;
    }

    public final PointF getPoint() {
        return this.point;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        canvas.drawPoint(this.point.x, this.point.y, this.paint);
    }
}
