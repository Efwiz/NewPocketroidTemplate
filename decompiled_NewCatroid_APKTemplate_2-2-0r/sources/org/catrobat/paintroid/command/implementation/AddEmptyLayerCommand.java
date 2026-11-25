package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.common.CommonFactory;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.model.Layer;

/* JADX INFO: compiled from: AddEmptyLayerCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/AddEmptyLayerCommand;", "Lorg/catrobat/paintroid/command/Command;", "commonFactory", "Lorg/catrobat/paintroid/common/CommonFactory;", "(Lorg/catrobat/paintroid/common/CommonFactory;)V", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class AddEmptyLayerCommand implements Command {
    private final CommonFactory commonFactory;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public AddEmptyLayerCommand(CommonFactory commonFactory) {
        Intrinsics.checkNotNullParameter(commonFactory, "commonFactory");
        this.commonFactory = commonFactory;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Layer layer = new Layer(this.commonFactory.createBitmap(layerModel.getWidth(), layerModel.getHeight(), Bitmap.Config.ARGB_8888));
        layerModel.addLayerAt(0, layer);
        layerModel.setCurrentLayer(layer);
    }
}
