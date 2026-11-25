package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: MergeLayersCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u001e\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0012"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/MergeLayersCommand;", "Lorg/catrobat/paintroid/command/Command;", "position", "", "mergeWith", "(II)V", "<set-?>", "getMergeWith", "()I", "getPosition", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class MergeLayersCommand implements Command {
    private static final String TAG = "MergeLayersCommand";
    private int mergeWith;
    private int position;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public MergeLayersCommand(int i, int i2) {
        this.position = i;
        this.mergeWith = i2;
    }

    public final int getPosition() {
        return this.position;
    }

    public final int getMergeWith() {
        return this.mergeWith;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        LayerContracts.Layer layerAt = layerModel.getLayerAt(this.position);
        LayerContracts.Layer layerAt2 = layerModel.getLayerAt(this.mergeWith);
        if (layerAt != null && layerAt2 != null) {
            Bitmap bitmap = layerAt2.getBitmap();
            if (bitmap == null) {
                return;
            }
            Bitmap copyBitmap = bitmap.copy(bitmap.getConfig(), true);
            Canvas canvas2 = new Canvas(copyBitmap);
            Bitmap bitmap2 = layerAt.getBitmap();
            if (bitmap2 == null) {
                return;
            }
            canvas2.drawBitmap(bitmap2, 0.0f, 0.0f, (Paint) null);
            if (layerModel.removeLayerAt(this.position)) {
                Intrinsics.checkNotNullExpressionValue(copyBitmap, "copyBitmap");
                layerAt2.setBitmap(copyBitmap);
                if (Intrinsics.areEqual(layerAt, layerModel.getCurrentLayer())) {
                    layerModel.setCurrentLayer(layerAt2);
                    return;
                }
                return;
            }
        }
        Log.e(TAG, "Could not merge layers!");
    }
}
