package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.model.Layer;

/* JADX INFO: compiled from: LoadLayerListCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R*\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/LoadLayerListCommand;", "Lorg/catrobat/paintroid/command/Command;", "loadedLayers", "", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "(Ljava/util/List;)V", "<set-?>", "getLoadedLayers", "()Ljava/util/List;", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class LoadLayerListCommand implements Command {
    private List<? extends LayerContracts.Layer> loadedLayers;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public LoadLayerListCommand(List<? extends LayerContracts.Layer> loadedLayers) {
        Intrinsics.checkNotNullParameter(loadedLayers, "loadedLayers");
        this.loadedLayers = loadedLayers;
    }

    public final List<LayerContracts.Layer> getLoadedLayers() {
        return this.loadedLayers;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        int i = 0;
        for (Object obj : this.loadedLayers) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            LayerContracts.Layer layer = (LayerContracts.Layer) obj;
            Bitmap bitmapCopy = layer.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
            Intrinsics.checkNotNullExpressionValue(bitmapCopy, "layer.bitmap.copy(Bitmap.Config.ARGB_8888, true)");
            Layer layer2 = new Layer(bitmapCopy);
            layer2.setOpacityPercentage(layer.getOpacityPercentage());
            layerModel.addLayerAt(i, layer2);
            i = i2;
        }
        layerModel.setCurrentLayer(layerModel.getLayerAt(0));
    }
}
