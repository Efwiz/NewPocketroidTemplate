package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: ResizeCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u001e\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/ResizeCommand;", "Lorg/catrobat/paintroid/command/Command;", "newWidth", "", "newHeight", "(II)V", "<set-?>", "getNewHeight", "()I", "getNewWidth", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ResizeCommand implements Command {
    private int newHeight;
    private int newWidth;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public ResizeCommand(int i, int i2) {
        this.newWidth = i;
        this.newHeight = i2;
    }

    public final int getNewWidth() {
        return this.newWidth;
    }

    public final int getNewHeight() {
        return this.newHeight;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        ListIterator<LayerContracts.Layer> listIterator = layerModel.listIterator(0);
        while (listIterator.hasNext()) {
            LayerContracts.Layer next = listIterator.next();
            Bitmap bitmap = next.getBitmap();
            if (bitmap != null) {
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, getNewWidth(), getNewHeight(), true);
                Intrinsics.checkNotNullExpressionValue(resizedBitmap, "resizedBitmap");
                next.setBitmap(resizedBitmap);
            }
        }
        layerModel.setHeight(this.newHeight);
        layerModel.setWidth(this.newWidth);
    }
}
