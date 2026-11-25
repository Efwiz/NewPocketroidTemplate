package org.catrobat.paintroid.command.implementation;

import android.graphics.Canvas;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: LayerOpacityCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0010"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/LayerOpacityCommand;", "Lorg/catrobat/paintroid/command/Command;", "position", "", "opacityPercentage", "(II)V", "getOpacityPercentage", "()I", "getPosition", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class LayerOpacityCommand implements Command {
    private final int opacityPercentage;
    private final int position;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public LayerOpacityCommand(int i, int i2) {
        this.position = i;
        this.opacityPercentage = i2;
    }

    public final int getPosition() {
        return this.position;
    }

    public final int getOpacityPercentage() {
        return this.opacityPercentage;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        LayerContracts.Layer layerAt = layerModel.getLayerAt(this.position);
        if (layerAt == null) {
            return;
        }
        layerAt.setOpacityPercentage(this.opacityPercentage);
    }
}
