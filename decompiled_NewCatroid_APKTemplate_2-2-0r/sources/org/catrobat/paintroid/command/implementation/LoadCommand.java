package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.model.Layer;

/* JADX INFO: compiled from: LoadCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/LoadCommand;", "Lorg/catrobat/paintroid/command/Command;", "loadedBitmap", "Landroid/graphics/Bitmap;", "(Landroid/graphics/Bitmap;)V", "<set-?>", "getLoadedBitmap", "()Landroid/graphics/Bitmap;", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class LoadCommand implements Command {
    private Bitmap loadedBitmap;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public LoadCommand(Bitmap loadedBitmap) {
        Intrinsics.checkNotNullParameter(loadedBitmap, "loadedBitmap");
        this.loadedBitmap = loadedBitmap;
    }

    public final Bitmap getLoadedBitmap() {
        return this.loadedBitmap;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Bitmap bitmapCopy = this.loadedBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Intrinsics.checkNotNullExpressionValue(bitmapCopy, "loadedBitmap.copy(Bitmap.Config.ARGB_8888, true)");
        Layer layer = new Layer(bitmapCopy);
        layerModel.addLayerAt(0, layer);
        layerModel.setCurrentLayer(layer);
    }
}
