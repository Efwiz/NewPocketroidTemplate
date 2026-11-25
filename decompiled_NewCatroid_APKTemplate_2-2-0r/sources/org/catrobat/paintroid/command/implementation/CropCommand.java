package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: CropCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u001e\u0010\u0007\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u001e\u0010\u0005\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u001e\u0010\u0006\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u001e\u0010\u0004\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/CropCommand;", "Lorg/catrobat/paintroid/command/Command;", "resizeCoordinateXLeft", "", "resizeCoordinateYTop", "resizeCoordinateXRight", "resizeCoordinateYBottom", "maximumBitmapResolution", "(IIIII)V", "<set-?>", "getMaximumBitmapResolution", "()I", "getResizeCoordinateXLeft", "getResizeCoordinateXRight", "getResizeCoordinateYBottom", "getResizeCoordinateYTop", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CropCommand implements Command {
    private int maximumBitmapResolution;
    private int resizeCoordinateXLeft;
    private int resizeCoordinateXRight;
    private int resizeCoordinateYBottom;
    private int resizeCoordinateYTop;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public CropCommand(int i, int i2, int i3, int i4, int i5) {
        this.resizeCoordinateXLeft = i;
        this.resizeCoordinateYTop = i2;
        this.resizeCoordinateXRight = i3;
        this.resizeCoordinateYBottom = i4;
        this.maximumBitmapResolution = i5;
    }

    public final int getResizeCoordinateXLeft() {
        return this.resizeCoordinateXLeft;
    }

    public final int getResizeCoordinateYTop() {
        return this.resizeCoordinateYTop;
    }

    public final int getResizeCoordinateXRight() {
        return this.resizeCoordinateXRight;
    }

    public final int getResizeCoordinateYBottom() {
        return this.resizeCoordinateYBottom;
    }

    public final int getMaximumBitmapResolution() {
        return this.maximumBitmapResolution;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        int i = this.resizeCoordinateXRight;
        int i2 = this.resizeCoordinateXLeft;
        if (i < i2 || this.resizeCoordinateYBottom < this.resizeCoordinateYTop || i2 >= layerModel.getWidth() || this.resizeCoordinateXRight < 0 || this.resizeCoordinateYTop >= layerModel.getHeight() || this.resizeCoordinateYBottom < 0) {
            return;
        }
        if (this.resizeCoordinateXLeft == 0 && this.resizeCoordinateXRight == layerModel.getWidth() - 1 && this.resizeCoordinateYBottom == layerModel.getHeight() - 1 && this.resizeCoordinateYTop == 0) {
            return;
        }
        int i3 = this.resizeCoordinateXRight;
        int i4 = this.resizeCoordinateXLeft;
        int i5 = this.resizeCoordinateYBottom;
        int i6 = this.resizeCoordinateYTop;
        if (((i3 + 1) - i4) * ((i5 + 1) - i6) > this.maximumBitmapResolution) {
            return;
        }
        int i7 = (i3 + 1) - i4;
        int i8 = (i5 + 1) - i6;
        ListIterator<LayerContracts.Layer> listIterator = layerModel.listIterator(0);
        while (listIterator.hasNext()) {
            LayerContracts.Layer next = listIterator.next();
            Bitmap bitmap = next.getBitmap();
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(i7, i8, Bitmap.Config.ARGB_8888);
            }
            Bitmap resizedBitmap = Bitmap.createBitmap(i7, i8, bitmap.getConfig());
            new Canvas(resizedBitmap).drawBitmap(bitmap, -this.resizeCoordinateXLeft, -this.resizeCoordinateYTop, (Paint) null);
            Intrinsics.checkNotNullExpressionValue(resizedBitmap, "resizedBitmap");
            next.setBitmap(resizedBitmap);
        }
        layerModel.setHeight(i8);
        layerModel.setWidth(i7);
    }
}
