package org.catrobat.catroid.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ShowTextUtils.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ShowTextUtils {
    public static final int ALIGNMENT_STYLE_CENTERED = 1;
    public static final int ALIGNMENT_STYLE_LEFT = 0;
    public static final int ALIGNMENT_STYLE_RIGHT = 2;
    public static final int DEFAULT_TEXT_SIZE = 45;
    public static final float DEFAULT_X_OFFSET = 3.0f;

    public static float sanitizeTextSize(float textSize) {
        if (textSize > 4500.0f) {
            return 1125.0f;
        }
        if (textSize <= 0.0f || textSize >= 2.25f) {
            return textSize;
        }
        return 11.25f;
    }

    private ShowTextUtils() {
    }

    public static int[] calculateColorRGBs(String color) {
        int i = Integer.parseInt(color.substring(1), 16);
        return new int[]{(16711680 & i) >> 16, (65280 & i) >> 8, i & 255};
    }

    public static boolean isValidColorString(String color) {
        if (color == null || !color.matches("#[A-F0-9a-f]+")) {
            return false;
        }
        return color.length() == 7 || color.length() == 9;
    }

    public static int calculateAlignmentValuesForText(Paint paint, int bitmapWidth, int alignment) {
        if (alignment == 0) {
            paint.setTextAlign(Paint.Align.LEFT);
            return 0;
        }
        if (alignment == 2) {
            paint.setTextAlign(Paint.Align.RIGHT);
            return bitmapWidth;
        }
        paint.setTextAlign(Paint.Align.CENTER);
        return bitmapWidth / 2;
    }

    private static String convertToEnglishDigits(String value) {
        return value.replace("١", SchemaSymbols.ATTVAL_TRUE_1).replace("٢", "2").replace("٣", "3").replace("٤", "4").replace("٥", "5").replace("٦", "6").replace("٧", "7").replace("٨", "8").replace("٩", "9").replace("٠", "0").replace("۱", SchemaSymbols.ATTVAL_TRUE_1).replace("۲", "2").replace("۳", "3").replace("۴", "4").replace("۵", "5").replace("۶", "6").replace("۷", "7").replace("۸", "8").replace("۹", "9").replace("۰", "0").replace("१", SchemaSymbols.ATTVAL_TRUE_1).replace("२", "2").replace("३", "3").replace("४", "4").replace("५", "5").replace("६", "6").replace("७", "7").replace("८", "8").replace("९", "9").replace("०", "0").replace("১", SchemaSymbols.ATTVAL_TRUE_1).replace("২", "2").replace("৩", "3").replace("৪", "4").replace("৫", "5").replace("৬", "6").replace("৭", "7").replace("৮", "8").replace("৯", "9").replace("০", "0").replace("௧", SchemaSymbols.ATTVAL_TRUE_1).replace("௦", "0").replace("௨", "2").replace("௩", "3").replace("௪", "4").replace("௫", "5").replace("௬", "6").replace("௭", "7").replace("௮", "8").replace("௯", "9").replace("૧", SchemaSymbols.ATTVAL_TRUE_1).replace("૨", "2").replace("૩", "3").replace("૪", "4").replace("૫", "5").replace("૬", "6").replace("૭", "7").replace("૮", "8").replace("૯", "9").replace("૦", "0");
    }

    public static boolean isNumberAndInteger(String variableValue) {
        if (!variableValue.matches("-?\\d+(\\.\\d+)?")) {
            return false;
        }
        double d = Double.parseDouble(convertToEnglishDigits(variableValue));
        return ((double) ((int) d)) - d == 0.0d;
    }

    public static String getStringAsInteger(String variableValue) {
        return Integer.toString((int) Double.parseDouble(convertToEnglishDigits(variableValue)));
    }

    public static String convertColorToString(int color) {
        return String.format("#%02X%02X%02X", Integer.valueOf(Color.red(color)), Integer.valueOf(Color.green(color)), Integer.valueOf(Color.blue(color)));
    }

    public static String convertStringToMetricRepresentation(String value) {
        try {
            return NumberFormats.toMetricUnitRepresentation(Integer.parseInt(value));
        } catch (NumberFormatException unused) {
            return value;
        }
    }

    public static String convertObjectToString(Object object) {
        if (object instanceof Boolean) {
            return new AndroidStringProvider(CatroidApplication.getAppContext()).getTrueOrFalse((Boolean) object);
        }
        return convertStringToMetricRepresentation(NumberFormats.trimTrailingCharacters(object.toString()));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ShowTextUtils$AndroidStringProvider.class
      classes14.dex
     */
    public static class AndroidStringProvider implements Formula.StringProvider {
        private final String falseString;
        private final String trueString;

        public AndroidStringProvider(Context context) {
            this.trueString = context.getString(R.string.formula_editor_true);
            this.falseString = context.getString(R.string.formula_editor_false);
        }

        @Override // org.catrobat.catroid.formulaeditor.Formula.StringProvider
        public String getTrueOrFalse(Boolean value) {
            if (value.booleanValue()) {
                return this.trueString;
            }
            return this.falseString;
        }
    }
}
