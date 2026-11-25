package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: SmudgePathCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b¢\u0006\u0002\u0010\u000bJ\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u001e\u0010\n\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u001e\u0010\u0011\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R*\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001d"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/SmudgePathCommand;", "Lorg/catrobat/paintroid/command/Command;", "bitmap", "Landroid/graphics/Bitmap;", "pointPath", "", "Landroid/graphics/PointF;", "maxPressure", "", "maxSize", "minSize", "(Landroid/graphics/Bitmap;Ljava/util/List;FFF)V", "<set-?>", "getMaxPressure", "()F", "getMaxSize", "getMinSize", "originalBitmap", "getOriginalBitmap", "()Landroid/graphics/Bitmap;", "getPointPath", "()Ljava/util/List;", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SmudgePathCommand implements Command {
    private float maxPressure;
    private float maxSize;
    private float minSize;
    private Bitmap originalBitmap;
    private List<PointF> pointPath;

    public SmudgePathCommand(Bitmap bitmap, List<PointF> pointPath, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(pointPath, "pointPath");
        this.originalBitmap = bitmap;
        this.pointPath = pointPath;
        this.maxPressure = f;
        this.maxSize = f2;
        this.minSize = f3;
    }

    public final Bitmap getOriginalBitmap() {
        return this.originalBitmap;
    }

    public final List<PointF> getPointPath() {
        return this.pointPath;
    }

    public final float getMaxPressure() {
        return this.maxPressure;
    }

    public final float getMaxSize() {
        return this.maxSize;
    }

    public final float getMinSize() {
        return this.minSize;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        float size = (this.maxSize - this.minSize) / this.pointPath.size();
        float f = this.maxSize;
        float f2 = this.maxPressure;
        ColorMatrix colorMatrix = new ColorMatrix();
        Paint paint = new Paint();
        Bitmap bitmapCopy = this.originalBitmap.copy(Bitmap.Config.ARGB_8888, false);
        for (PointF pointF : this.pointPath) {
            colorMatrix.setScale(1.0f, 1.0f, 1.0f, f2);
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) getMaxSize(), (int) getMaxSize(), Bitmap.Config.ARGB_8888);
            new Canvas(bitmapCreateBitmap).drawBitmap(bitmapCopy, 0.0f, 0.0f, paint);
            bitmapCopy.recycle();
            float f3 = (-f) / 2.0f;
            float f4 = f / 2.0f;
            RectF rectF = new RectF(f3, f3, f4, f4);
            canvas.save();
            canvas.translate(pointF.x, pointF.y);
            canvas.drawBitmap(bitmapCreateBitmap, (Rect) null, rectF, new Paint(4));
            canvas.restore();
            f -= size;
            f2 -= 0.004f;
            bitmapCopy = bitmapCreateBitmap;
        }
    }

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
        this.originalBitmap.recycle();
    }
}
