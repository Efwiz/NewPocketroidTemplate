package org.catrobat.catroid.sensing;

import androidx.core.graphics.ColorUtils;
import com.badlogic.gdx.graphics.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.formulaeditor.common.Conversions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/ColorEqualsColor.class
  classes14.dex
 */
/* JADX INFO: compiled from: ColorEqualsColor.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0007H\u0002J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0007H\u0002J$\u0010\r\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u00012\b\u0010\u000f\u001a\u0004\u0018\u00010\u00012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/sensing/ColorEqualsColor;", "", "()V", "addHashtagIfMissing", "", "parameter", "calculateEuclideanDistanceInPercent", "", "firstColorHex", "secondColorHex", "isToleranceParameterInvalid", "", "isToleranceParameterOverAHundred", "tryInterpretFunctionColorEqualsColor", "firstColor", "secondColor", "tolerance", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ColorEqualsColor {
    private final boolean isToleranceParameterOverAHundred(double parameter) {
        return parameter >= 100.0d;
    }

    public final boolean tryInterpretFunctionColorEqualsColor(Object firstColor, Object secondColor, Object tolerance) {
        Double dConvertArgumentToDouble;
        if ((firstColor instanceof String) && (secondColor instanceof String)) {
            String strAddHashtagIfMissing = addHashtagIfMissing((String) firstColor);
            String strAddHashtagIfMissing2 = addHashtagIfMissing((String) secondColor);
            if (Conversions.INSTANCE.isValidHexColor(strAddHashtagIfMissing) && Conversions.INSTANCE.isValidHexColor(strAddHashtagIfMissing2) && (dConvertArgumentToDouble = Conversions.convertArgumentToDouble(tolerance)) != null) {
                double dDoubleValue = dConvertArgumentToDouble.doubleValue();
                if (isToleranceParameterInvalid(dDoubleValue)) {
                    return false;
                }
                return isToleranceParameterOverAHundred(dDoubleValue) || calculateEuclideanDistanceInPercent(strAddHashtagIfMissing, strAddHashtagIfMissing2) <= dDoubleValue;
            }
        }
        return false;
    }

    private final double calculateEuclideanDistanceInPercent(String firstColorHex, String secondColorHex) {
        double[] dArr = new double[3];
        double[] dArr2 = new double[3];
        Intrinsics.checkNotNull(firstColorHex, "null cannot be cast to non-null type kotlin.String");
        Color colorValueOf = Color.valueOf(firstColorHex);
        Intrinsics.checkNotNull(secondColorHex, "null cannot be cast to non-null type kotlin.String");
        Color colorValueOf2 = Color.valueOf(secondColorHex);
        ColorUtils.RGBToLAB((int) (colorValueOf.r * 255.0f), (int) (colorValueOf.g * 255.0f), (int) (colorValueOf.b * 255.0f), dArr);
        ColorUtils.RGBToLAB((int) (colorValueOf2.r * 255.0f), (int) (colorValueOf2.g * 255.0f), (int) (colorValueOf2.b * 255.0f), dArr2);
        return (ColorUtils.distanceEuclidean(dArr, dArr2) / ((double) 258.693f)) * ((double) 100.0f);
    }

    private final String addHashtagIfMissing(String parameter) {
        return parameter.length() == 6 ? "#" + parameter : parameter;
    }

    private final boolean isToleranceParameterInvalid(double parameter) {
        return Double.isNaN(parameter) || parameter < 0.0d;
    }
}
