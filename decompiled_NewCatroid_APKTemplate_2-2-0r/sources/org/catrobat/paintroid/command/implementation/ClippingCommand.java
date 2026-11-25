package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: ClippingCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\"\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\"\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/ClippingCommand;", "Lorg/catrobat/paintroid/command/Command;", "bitmap", "Landroid/graphics/Bitmap;", "pathBitmap", "(Landroid/graphics/Bitmap;Landroid/graphics/Bitmap;)V", "<set-?>", "getBitmap", "()Landroid/graphics/Bitmap;", "getPathBitmap", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ClippingCommand implements Command {
    private Bitmap bitmap;
    private Bitmap pathBitmap;

    public ClippingCommand(Bitmap bitmap, Bitmap pathBitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(pathBitmap, "pathBitmap");
        this.bitmap = bitmap.copy(bitmap.getConfig(), true);
        this.pathBitmap = pathBitmap.copy(pathBitmap.getConfig(), true);
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final Bitmap getPathBitmap() {
        return this.pathBitmap;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Bitmap bitmap;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 == null) {
            return;
        }
        Rect rect = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        LayerContracts.Layer currentLayer = layerModel.getCurrentLayer();
        if (currentLayer != null && (bitmap = currentLayer.getBitmap()) != null) {
            bitmap.eraseColor(0);
        }
        Paint paint = new Paint();
        canvas.save();
        Bitmap pathBitmap = getPathBitmap();
        if (pathBitmap != null) {
            canvas.drawBitmap(pathBitmap, (Rect) null, rect, (Paint) null);
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Bitmap bitmap3 = getBitmap();
        if (bitmap3 != null) {
            canvas.drawBitmap(bitmap3, (Rect) null, rect, paint);
        }
        canvas.restore();
    }

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.recycle();
        }
        Bitmap bitmap2 = this.pathBitmap;
        if (bitmap2 == null) {
            return;
        }
        bitmap2.recycle();
    }
}
