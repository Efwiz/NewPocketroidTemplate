package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: FlipCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/FlipCommand;", "Lorg/catrobat/paintroid/command/Command;", "flipDirection", "Lorg/catrobat/paintroid/command/implementation/FlipCommand$FlipDirection;", "(Lorg/catrobat/paintroid/command/implementation/FlipCommand$FlipDirection;)V", "<set-?>", "getFlipDirection", "()Lorg/catrobat/paintroid/command/implementation/FlipCommand$FlipDirection;", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "FlipDirection", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class FlipCommand implements Command {
    private FlipDirection flipDirection;

    /* JADX INFO: compiled from: FlipCommand.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/FlipCommand$FlipDirection;", "", "(Ljava/lang/String;I)V", "FLIP_HORIZONTAL", "FLIP_VERTICAL", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum FlipDirection {
        FLIP_HORIZONTAL,
        FLIP_VERTICAL
    }

    /* JADX INFO: compiled from: FlipCommand.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FlipDirection.values().length];
            iArr[FlipDirection.FLIP_HORIZONTAL.ordinal()] = 1;
            iArr[FlipDirection.FLIP_VERTICAL.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public FlipCommand(FlipDirection flipDirection) {
        Intrinsics.checkNotNullParameter(flipDirection, "flipDirection");
        this.flipDirection = flipDirection;
    }

    public final FlipDirection getFlipDirection() {
        return this.flipDirection;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Bitmap bitmap;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Matrix matrix = new Matrix();
        int i = WhenMappings.$EnumSwitchMapping$0[getFlipDirection().ordinal()];
        if (i == 1) {
            matrix.setScale(1.0f, -1.0f);
            matrix.postTranslate(0.0f, layerModel.getHeight());
        } else if (i == 2) {
            matrix.setScale(-1.0f, 1.0f);
            matrix.postTranslate(layerModel.getWidth(), 0.0f);
        }
        LayerContracts.Layer currentLayer = layerModel.getCurrentLayer();
        if (currentLayer == null || (bitmap = currentLayer.getBitmap()) == null) {
            return;
        }
        Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
        Canvas canvas2 = new Canvas(bitmap);
        bitmap.eraseColor(0);
        canvas2.drawBitmap(bitmapCopy, matrix, new Paint());
    }
}
