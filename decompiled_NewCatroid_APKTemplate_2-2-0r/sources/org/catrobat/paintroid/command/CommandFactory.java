package org.catrobat.paintroid.command;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.List;
import kotlin.Metadata;
import org.catrobat.paintroid.command.implementation.FlipCommand;
import org.catrobat.paintroid.command.implementation.RotateCommand;
import org.catrobat.paintroid.command.serialization.SerializablePath;
import org.catrobat.paintroid.command.serialization.SerializableTypeface;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.tools.ToolReference;
import org.catrobat.paintroid.tools.drawable.ShapeDrawable;

/* JADX INFO: compiled from: CommandFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J0\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH&J\u0018\u0010\r\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006H&J \u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H&J0\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0015H&J(\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH&J(\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\nH&J\u0010\u0010#\u001a\u00020\u00032\u0006\u0010$\u001a\u00020%H&J0\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010 \u001a\u00020!H&J\u0010\u0010-\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0018\u0010-\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u00152\u0006\u0010/\u001a\u00020\u0015H&J\u0016\u0010-\u001a\u00020\u00032\f\u00100\u001a\b\u0012\u0004\u0012\u00020201H&J\u0018\u00103\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u00152\u0006\u00104\u001a\u00020\u0015H&J\u0018\u00105\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u00152\u0006\u00106\u001a\u00020\u0015H&J\u0018\u00107\u001a\u00020\u00032\u0006\u0010 \u001a\u00020!2\u0006\u00108\u001a\u000209H&J\u0018\u0010:\u001a\u00020\u00032\u0006\u0010 \u001a\u00020!2\u0006\u0010;\u001a\u00020\bH&J\u0010\u0010<\u001a\u00020\u00032\u0006\u0010=\u001a\u00020\u0015H&J\u0018\u0010>\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u00152\u0006\u0010?\u001a\u00020\u0015H&J\b\u0010@\u001a\u00020\u0003H&J\u0018\u0010A\u001a\u00020\u00032\u0006\u0010B\u001a\u00020\u00152\u0006\u0010C\u001a\u00020\u0015H&J\u0010\u0010D\u001a\u00020\u00032\u0006\u0010E\u001a\u00020FH&J\u0010\u0010G\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u0015H&J6\u0010H\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010I\u001a\b\u0012\u0004\u0012\u00020\b0J2\u0006\u0010K\u001a\u00020\n2\u0006\u0010L\u001a\u00020\n2\u0006\u0010M\u001a\u00020\nH&J\u0018\u0010N\u001a\u00020\u00032\u0006\u0010O\u001a\u00020P2\u0006\u0010 \u001a\u00020!H&JS\u0010Q\u001a\u00020\u00032\f\u0010R\u001a\b\u0012\u0004\u0012\u00020T0S2\u0006\u0010U\u001a\u00020!2\u0006\u0010V\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010W\u001a\u00020XH&¢\u0006\u0002\u0010Y¨\u0006Z"}, d2 = {"Lorg/catrobat/paintroid/command/CommandFactory;", "", "createAddEmptyLayerCommand", "Lorg/catrobat/paintroid/command/Command;", "createClipboardCommand", "bitmap", "Landroid/graphics/Bitmap;", "toolPosition", "Landroid/graphics/PointF;", "boxWidth", "", "boxHeight", "boxRotation", "createClippingCommand", "pathBitmap", "createColorChangedCommand", "toolReference", "Lorg/catrobat/paintroid/tools/ToolReference;", "context", "Landroid/content/Context;", TypedValues.Custom.S_COLOR, "", "createCropCommand", "resizeCoordinateXLeft", "resizeCoordinateYTop", "resizeCoordinateXRight", "resizeCoordinateYBottom", "maximumBitmapResolution", "createCutCommand", "createFillCommand", "x", "y", "paint", "Landroid/graphics/Paint;", "colorTolerance", "createFlipCommand", "flipDirection", "Lorg/catrobat/paintroid/command/implementation/FlipCommand$FlipDirection;", "createGeometricFillCommand", "shapeDrawable", "Lorg/catrobat/paintroid/tools/drawable/ShapeDrawable;", "position", "Landroid/graphics/Point;", "box", "Landroid/graphics/RectF;", "createInitCommand", "width", "height", "layers", "", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "createLayerOpacityCommand", "opacityPercentage", "createMergeLayersCommand", "mergeWith", "createPathCommand", "path", "Lorg/catrobat/paintroid/command/serialization/SerializablePath;", "createPointCommand", "coordinate", "createRemoveLayerCommand", "index", "createReorderLayersCommand", "swapWith", "createResetCommand", "createResizeCommand", "newWidth", "newHeight", "createRotateCommand", "rotateDirection", "Lorg/catrobat/paintroid/command/implementation/RotateCommand$RotateDirection;", "createSelectLayerCommand", "createSmudgePathCommand", "pointPath", "", "maxPressure", "maxSize", "minSize", "createSprayCommand", "sprayedPoints", "", "createTextToolCommand", "multilineText", "", "", "textPaint", "boxOffset", "typefaceInfo", "Lorg/catrobat/paintroid/command/serialization/SerializableTypeface;", "([Ljava/lang/String;Landroid/graphics/Paint;IFFLandroid/graphics/PointF;FLorg/catrobat/paintroid/command/serialization/SerializableTypeface;)Lorg/catrobat/paintroid/command/Command;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface CommandFactory {
    Command createAddEmptyLayerCommand();

    Command createClipboardCommand(Bitmap bitmap, PointF toolPosition, float boxWidth, float boxHeight, float boxRotation);

    Command createClippingCommand(Bitmap bitmap, Bitmap pathBitmap);

    Command createColorChangedCommand(ToolReference toolReference, Context context, int color);

    Command createCropCommand(int resizeCoordinateXLeft, int resizeCoordinateYTop, int resizeCoordinateXRight, int resizeCoordinateYBottom, int maximumBitmapResolution);

    Command createCutCommand(PointF toolPosition, float boxWidth, float boxHeight, float boxRotation);

    Command createFillCommand(int x, int y, Paint paint, float colorTolerance);

    Command createFlipCommand(FlipCommand.FlipDirection flipDirection);

    Command createGeometricFillCommand(ShapeDrawable shapeDrawable, Point position, RectF box, float boxRotation, Paint paint);

    Command createInitCommand(int width, int height);

    Command createInitCommand(Bitmap bitmap);

    Command createInitCommand(List<? extends LayerContracts.Layer> layers);

    Command createLayerOpacityCommand(int position, int opacityPercentage);

    Command createMergeLayersCommand(int position, int mergeWith);

    Command createPathCommand(Paint paint, SerializablePath path);

    Command createPointCommand(Paint paint, PointF coordinate);

    Command createRemoveLayerCommand(int index);

    Command createReorderLayersCommand(int position, int swapWith);

    Command createResetCommand();

    Command createResizeCommand(int newWidth, int newHeight);

    Command createRotateCommand(RotateCommand.RotateDirection rotateDirection);

    Command createSelectLayerCommand(int position);

    Command createSmudgePathCommand(Bitmap bitmap, List<PointF> pointPath, float maxPressure, float maxSize, float minSize);

    Command createSprayCommand(float[] sprayedPoints, Paint paint);

    Command createTextToolCommand(String[] multilineText, Paint textPaint, int boxOffset, float boxWidth, float boxHeight, PointF toolPosition, float boxRotation, SerializableTypeface typefaceInfo);
}
