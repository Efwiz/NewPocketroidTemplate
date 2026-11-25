package org.catrobat.paintroid.command.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.visualplacement.VisualPlacementActivity;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.command.serialization.SerializableTypeface;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: TextToolCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BK\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\b\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\b\u0010 \u001a\u00020!H\u0016J\u0018\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0016R\u001e\u0010\n\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R,\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003@BX\u0086\u000e¢\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0016\u0010\u0017R\u001e\u0010\r\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u001e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006'"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/TextToolCommand;", "Lorg/catrobat/paintroid/command/Command;", "multilineText", "", "", "textPaint", "Landroid/graphics/Paint;", "boxOffset", "", "boxWidth", "boxHeight", "toolPosition", "Landroid/graphics/PointF;", VisualPlacementActivity.ROTATION_ANGLE_BUNDLE_ARGUMENT, "typeFaceInfo", "Lorg/catrobat/paintroid/command/serialization/SerializableTypeface;", "([Ljava/lang/String;Landroid/graphics/Paint;FFFLandroid/graphics/PointF;FLorg/catrobat/paintroid/command/serialization/SerializableTypeface;)V", "<set-?>", "getBoxHeight", "()F", "getBoxOffset", "getBoxWidth", "getMultilineText", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getRotationAngle", "getTextPaint", "()Landroid/graphics/Paint;", "getToolPosition", "()Landroid/graphics/PointF;", "getTypeFaceInfo", "()Lorg/catrobat/paintroid/command/serialization/SerializableTypeface;", "freeResources", "", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class TextToolCommand implements Command {
    private float boxHeight;
    private float boxOffset;
    private float boxWidth;
    private String[] multilineText;
    private float rotationAngle;
    private Paint textPaint;
    private PointF toolPosition;
    private SerializableTypeface typeFaceInfo;

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
    }

    public TextToolCommand(String[] multilineText, Paint textPaint, float f, float f2, float f3, PointF toolPosition, float f4, SerializableTypeface typeFaceInfo) {
        Intrinsics.checkNotNullParameter(multilineText, "multilineText");
        Intrinsics.checkNotNullParameter(textPaint, "textPaint");
        Intrinsics.checkNotNullParameter(toolPosition, "toolPosition");
        Intrinsics.checkNotNullParameter(typeFaceInfo, "typeFaceInfo");
        this.multilineText = (String[]) multilineText.clone();
        this.textPaint = textPaint;
        this.boxOffset = f;
        this.boxWidth = f2;
        this.boxHeight = f3;
        this.toolPosition = toolPosition;
        this.rotationAngle = f4;
        this.typeFaceInfo = typeFaceInfo;
    }

    public final String[] getMultilineText() {
        return this.multilineText;
    }

    public final Paint getTextPaint() {
        return this.textPaint;
    }

    public final float getBoxOffset() {
        return this.boxOffset;
    }

    public final float getBoxWidth() {
        return this.boxWidth;
    }

    public final float getBoxHeight() {
        return this.boxHeight;
    }

    public final PointF getToolPosition() {
        return this.toolPosition;
    }

    public final float getRotationAngle() {
        return this.rotationAngle;
    }

    public final SerializableTypeface getTypeFaceInfo() {
        return this.typeFaceInfo;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        float fAscent = this.textPaint.ascent();
        float fDescent = this.textPaint.descent() - fAscent;
        String[] strArr = this.multilineText;
        float length = fDescent * strArr.length;
        float length2 = length / strArr.length;
        if (strArr.length == 0) {
            throw new NoSuchElementException();
        }
        int i = 0;
        float fMeasureText = getTextPaint().measureText(strArr[0]);
        int lastIndex = ArraysKt.getLastIndex(strArr);
        int i2 = 1;
        if (1 <= lastIndex) {
            while (true) {
                fMeasureText = Math.max(fMeasureText, getTextPaint().measureText(strArr[i2]));
                if (i2 == lastIndex) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        float f = 1.2f;
        if (this.typeFaceInfo.getItalic()) {
            fMeasureText *= 1.2f;
        }
        canvas.save();
        canvas.translate(getToolPosition().x, getToolPosition().y);
        canvas.rotate(getRotationAngle());
        float f2 = 2;
        float boxWidth = (getBoxWidth() - (getBoxOffset() * f2)) / fMeasureText;
        float boxHeight = (getBoxHeight() - (getBoxOffset() * f2)) / length;
        canvas.scale(boxWidth, boxHeight);
        float boxOffset = getBoxOffset() / boxHeight;
        float boxOffset2 = getBoxOffset() / boxWidth;
        float boxWidth2 = getBoxWidth() / boxWidth;
        float boxHeight2 = getBoxHeight() / boxHeight;
        String[] multilineText = getMultilineText();
        int length3 = multilineText.length;
        int i3 = 0;
        while (i < length3) {
            int i4 = i3 + 1;
            canvas.drawText(multilineText[i], boxOffset2 - ((boxWidth2 / f2) / (getTypeFaceInfo().getItalic() ? f : 1.0f)), (((-(boxHeight2 / f2)) + boxOffset) - fAscent) + (i3 * length2), getTextPaint());
            i++;
            f = 1.2f;
            i3 = i4;
        }
        canvas.restore();
    }
}
