package org.catrobat.paintroid.command.implementation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.command.CommandFactory;
import org.catrobat.paintroid.command.serialization.SerializablePath;
import org.catrobat.paintroid.command.serialization.SerializableTypeface;
import org.catrobat.paintroid.common.CommonFactory;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.tools.ToolReference;
import org.catrobat.paintroid.tools.drawable.ShapeDrawable;
import org.catrobat.paintroid.tools.helper.ConversionKt;
import org.catrobat.paintroid.tools.helper.JavaFillAlgorithmFactory;

/* JADX INFO: compiled from: DefaultCommandFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J0\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0016J\u0018\u0010\u0010\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\tH\u0016J \u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J0\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u0018H\u0016J(\u0010\u001f\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0016J(\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020\u00182\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\rH\u0016J\u0010\u0010&\u001a\u00020\u00062\u0006\u0010'\u001a\u00020(H\u0016J0\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u00100\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u00182\u0006\u00102\u001a\u00020\u0018H\u0016J\u0016\u00100\u001a\u00020\u00062\f\u00103\u001a\b\u0012\u0004\u0012\u00020504H\u0016J\u0018\u00106\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\u00182\u0006\u00107\u001a\u00020\u0018H\u0016J\u0018\u00108\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\u00182\u0006\u00109\u001a\u00020\u0018H\u0016J\u0018\u0010:\u001a\u00020\u00062\u0006\u0010#\u001a\u00020$2\u0006\u0010;\u001a\u00020<H\u0016J\u0018\u0010=\u001a\u00020\u00062\u0006\u0010#\u001a\u00020$2\u0006\u0010>\u001a\u00020\u000bH\u0016J\u0010\u0010?\u001a\u00020\u00062\u0006\u0010@\u001a\u00020\u0018H\u0016J\u0018\u0010A\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\u00182\u0006\u0010B\u001a\u00020\u0018H\u0016J\b\u0010C\u001a\u00020\u0006H\u0016J\u0018\u0010D\u001a\u00020\u00062\u0006\u0010E\u001a\u00020\u00182\u0006\u0010F\u001a\u00020\u0018H\u0016J\u0010\u0010G\u001a\u00020\u00062\u0006\u0010H\u001a\u00020IH\u0016J\u0010\u0010J\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\u0018H\u0016J6\u0010K\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\f\u0010L\u001a\b\u0012\u0004\u0012\u00020\u000b0M2\u0006\u0010N\u001a\u00020\r2\u0006\u0010O\u001a\u00020\r2\u0006\u0010P\u001a\u00020\rH\u0016J\u0018\u0010Q\u001a\u00020\u00062\u0006\u0010R\u001a\u00020S2\u0006\u0010#\u001a\u00020$H\u0016JS\u0010T\u001a\u00020\u00062\f\u0010U\u001a\b\u0012\u0004\u0012\u00020W0V2\u0006\u0010X\u001a\u00020$2\u0006\u0010Y\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010Z\u001a\u00020[H\u0016¢\u0006\u0002\u0010\\R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006]"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/DefaultCommandFactory;", "Lorg/catrobat/paintroid/command/CommandFactory;", "()V", "commonFactory", "Lorg/catrobat/paintroid/common/CommonFactory;", "createAddEmptyLayerCommand", "Lorg/catrobat/paintroid/command/Command;", "createClipboardCommand", "bitmap", "Landroid/graphics/Bitmap;", "toolPosition", "Landroid/graphics/PointF;", "boxWidth", "", "boxHeight", "boxRotation", "createClippingCommand", "pathBitmap", "createColorChangedCommand", "toolReference", "Lorg/catrobat/paintroid/tools/ToolReference;", "context", "Landroid/content/Context;", TypedValues.Custom.S_COLOR, "", "createCropCommand", "resizeCoordinateXLeft", "resizeCoordinateYTop", "resizeCoordinateXRight", "resizeCoordinateYBottom", "maximumBitmapResolution", "createCutCommand", "createFillCommand", "x", "y", "paint", "Landroid/graphics/Paint;", "colorTolerance", "createFlipCommand", "flipDirection", "Lorg/catrobat/paintroid/command/implementation/FlipCommand$FlipDirection;", "createGeometricFillCommand", "shapeDrawable", "Lorg/catrobat/paintroid/tools/drawable/ShapeDrawable;", "position", "Landroid/graphics/Point;", "box", "Landroid/graphics/RectF;", "createInitCommand", "width", "height", "layers", "", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "createLayerOpacityCommand", "opacityPercentage", "createMergeLayersCommand", "mergeWith", "createPathCommand", "path", "Lorg/catrobat/paintroid/command/serialization/SerializablePath;", "createPointCommand", "coordinate", "createRemoveLayerCommand", "index", "createReorderLayersCommand", "swapWith", "createResetCommand", "createResizeCommand", "newWidth", "newHeight", "createRotateCommand", "rotateDirection", "Lorg/catrobat/paintroid/command/implementation/RotateCommand$RotateDirection;", "createSelectLayerCommand", "createSmudgePathCommand", "pointPath", "", "maxPressure", "maxSize", "minSize", "createSprayCommand", "sprayedPoints", "", "createTextToolCommand", "multilineText", "", "", "textPaint", "boxOffset", "typefaceInfo", "Lorg/catrobat/paintroid/command/serialization/SerializableTypeface;", "([Ljava/lang/String;Landroid/graphics/Paint;IFFLandroid/graphics/PointF;FLorg/catrobat/paintroid/command/serialization/SerializableTypeface;)Lorg/catrobat/paintroid/command/Command;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultCommandFactory implements CommandFactory {
    private final CommonFactory commonFactory = new CommonFactory();

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createInitCommand(int width, int height) {
        CompositeCommand compositeCommand = new CompositeCommand();
        compositeCommand.addCommand(new SetDimensionCommand(width, height));
        compositeCommand.addCommand(new AddEmptyLayerCommand(this.commonFactory));
        return compositeCommand;
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createInitCommand(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        CompositeCommand compositeCommand = new CompositeCommand();
        compositeCommand.addCommand(new SetDimensionCommand(bitmap.getWidth(), bitmap.getHeight()));
        compositeCommand.addCommand(new LoadCommand(bitmap));
        return compositeCommand;
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createInitCommand(List<? extends LayerContracts.Layer> layers) {
        Intrinsics.checkNotNullParameter(layers, "layers");
        CompositeCommand compositeCommand = new CompositeCommand();
        Bitmap bitmap = layers.get(0).getBitmap();
        compositeCommand.addCommand(new SetDimensionCommand(bitmap.getWidth(), bitmap.getHeight()));
        compositeCommand.addCommand(new LoadLayerListCommand(layers));
        return compositeCommand;
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createResetCommand() {
        CompositeCommand compositeCommand = new CompositeCommand();
        compositeCommand.addCommand(new ResetCommand());
        compositeCommand.addCommand(new AddEmptyLayerCommand(this.commonFactory));
        return compositeCommand;
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createAddEmptyLayerCommand() {
        return new AddEmptyLayerCommand(this.commonFactory);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createSelectLayerCommand(int position) {
        return new SelectLayerCommand(position);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createLayerOpacityCommand(int position, int opacityPercentage) {
        return new LayerOpacityCommand(position, opacityPercentage);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createRemoveLayerCommand(int index) {
        return new RemoveLayerCommand(index);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createReorderLayersCommand(int position, int swapWith) {
        return new ReorderLayersCommand(position, swapWith);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createMergeLayersCommand(int position, int mergeWith) {
        return new MergeLayersCommand(position, mergeWith);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createRotateCommand(RotateCommand.RotateDirection rotateDirection) {
        Intrinsics.checkNotNullParameter(rotateDirection, "rotateDirection");
        return new RotateCommand(rotateDirection);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createFlipCommand(FlipCommand.FlipDirection flipDirection) {
        Intrinsics.checkNotNullParameter(flipDirection, "flipDirection");
        return new FlipCommand(flipDirection);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createCropCommand(int resizeCoordinateXLeft, int resizeCoordinateYTop, int resizeCoordinateXRight, int resizeCoordinateYBottom, int maximumBitmapResolution) {
        return new CropCommand(resizeCoordinateXLeft, resizeCoordinateYTop, resizeCoordinateXRight, resizeCoordinateYBottom, maximumBitmapResolution);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createPointCommand(Paint paint, PointF coordinate) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        return new PointCommand(this.commonFactory.createPaint(paint), this.commonFactory.createPointF(coordinate));
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createFillCommand(int x, int y, Paint paint, float colorTolerance) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        return new FillCommand(new JavaFillAlgorithmFactory(), this.commonFactory.createPoint(x, y), this.commonFactory.createPaint(paint), colorTolerance);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createGeometricFillCommand(ShapeDrawable shapeDrawable, Point position, RectF box, float boxRotation, Paint paint) {
        Intrinsics.checkNotNullParameter(shapeDrawable, "shapeDrawable");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(box, "box");
        Intrinsics.checkNotNullParameter(paint, "paint");
        return new GeometricFillCommand(shapeDrawable, position.x, position.y, this.commonFactory.createRectF(box), boxRotation, this.commonFactory.createPaint(paint));
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createPathCommand(Paint paint, SerializablePath path) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(path, "path");
        return new PathCommand(this.commonFactory.createPaint(paint), this.commonFactory.createSerializablePath(path));
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createSmudgePathCommand(Bitmap bitmap, List<PointF> pointPath, float maxPressure, float maxSize, float minSize) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(pointPath, "pointPath");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = pointPath.iterator();
        while (it.hasNext()) {
            arrayList.add(this.commonFactory.createPointF((PointF) it.next()));
        }
        Bitmap bitmapCopy = bitmap.copy(Bitmap.Config.ARGB_8888, false);
        Intrinsics.checkNotNullExpressionValue(bitmapCopy, "bitmap.copy(Bitmap.Config.ARGB_8888, false)");
        return new SmudgePathCommand(bitmapCopy, arrayList, maxPressure, maxSize, minSize);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createTextToolCommand(String[] multilineText, Paint textPaint, int boxOffset, float boxWidth, float boxHeight, PointF toolPosition, float boxRotation, SerializableTypeface typefaceInfo) {
        Intrinsics.checkNotNullParameter(multilineText, "multilineText");
        Intrinsics.checkNotNullParameter(textPaint, "textPaint");
        Intrinsics.checkNotNullParameter(toolPosition, "toolPosition");
        Intrinsics.checkNotNullParameter(typefaceInfo, "typefaceInfo");
        return new TextToolCommand(multilineText, this.commonFactory.createPaint(textPaint), boxOffset, boxWidth, boxHeight, this.commonFactory.createPointF(toolPosition), boxRotation, typefaceInfo);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createResizeCommand(int newWidth, int newHeight) {
        return new ResizeCommand(newWidth, newHeight);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createClipboardCommand(Bitmap bitmap, PointF toolPosition, float boxWidth, float boxHeight, float boxRotation) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(toolPosition, "toolPosition");
        return new ClipboardCommand(bitmap, ConversionKt.toPoint(toolPosition), boxWidth, boxHeight, boxRotation);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createClippingCommand(Bitmap bitmap, Bitmap pathBitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(pathBitmap, "pathBitmap");
        return new ClippingCommand(bitmap, pathBitmap);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createSprayCommand(float[] sprayedPoints, Paint paint) {
        Intrinsics.checkNotNullParameter(sprayedPoints, "sprayedPoints");
        Intrinsics.checkNotNullParameter(paint, "paint");
        return new SprayCommand(sprayedPoints, paint);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createCutCommand(PointF toolPosition, float boxWidth, float boxHeight, float boxRotation) {
        Intrinsics.checkNotNullParameter(toolPosition, "toolPosition");
        return new CutCommand(ConversionKt.toPoint(toolPosition), boxWidth, boxHeight, boxRotation);
    }

    @Override // org.catrobat.paintroid.command.CommandFactory
    public Command createColorChangedCommand(ToolReference toolReference, Context context, int color) {
        Intrinsics.checkNotNullParameter(toolReference, "toolReference");
        Intrinsics.checkNotNullParameter(context, "context");
        return new ColorChangedCommand(toolReference, context, color);
    }
}
