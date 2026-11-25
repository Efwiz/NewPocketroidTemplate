package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: RotateCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u000f2\u00020\u0001:\u0002\u000f\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/RotateCommand;", "Lorg/catrobat/paintroid/command/Command;", "rotateDirection", "Lorg/catrobat/paintroid/command/implementation/RotateCommand$RotateDirection;", "(Lorg/catrobat/paintroid/command/implementation/RotateCommand$RotateDirection;)V", "<set-?>", "getRotateDirection", "()Lorg/catrobat/paintroid/command/implementation/RotateCommand$RotateDirection;", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Companion", "RotateDirection", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class RotateCommand implements Command {
    private static final float ANGLE = 90.0f;
    private RotateDirection rotateDirection;

    /* JADX INFO: compiled from: RotateCommand.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/RotateCommand$RotateDirection;", "", "(Ljava/lang/String;I)V", "ROTATE_LEFT", "ROTATE_RIGHT", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum RotateDirection {
        ROTATE_LEFT,
        ROTATE_RIGHT
    }

    /* JADX INFO: compiled from: RotateCommand.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[RotateDirection.values().length];
            iArr[RotateDirection.ROTATE_RIGHT.ordinal()] = 1;
            iArr[RotateDirection.ROTATE_LEFT.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public RotateCommand(RotateDirection rotateDirection) {
        Intrinsics.checkNotNullParameter(rotateDirection, "rotateDirection");
        this.rotateDirection = rotateDirection;
    }

    public final RotateDirection getRotateDirection() {
        return this.rotateDirection;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Matrix matrix = new Matrix();
        int i = WhenMappings.$EnumSwitchMapping$0[getRotateDirection().ordinal()];
        if (i == 1) {
            matrix.postRotate(90.0f);
        } else if (i == 2) {
            matrix.postRotate(-90.0f);
        }
        ListIterator<LayerContracts.Layer> listIterator = layerModel.listIterator(0);
        while (listIterator.hasNext()) {
            LayerContracts.Layer next = listIterator.next();
            Bitmap rotatedBitmap = Bitmap.createBitmap(next.getBitmap(), 0, 0, layerModel.getWidth(), layerModel.getHeight(), matrix, true);
            Intrinsics.checkNotNullExpressionValue(rotatedBitmap, "rotatedBitmap");
            next.setBitmap(rotatedBitmap);
        }
        int width = layerModel.getWidth();
        layerModel.setWidth(layerModel.getHeight());
        layerModel.setHeight(width);
    }
}
