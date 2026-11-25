package org.catrobat.catroid.formulaeditor;

import android.content.Context;
import android.text.SpannableStringBuilder;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/FormulaSpannableStringBuilder.class
  classes14.dex
 */
/* JADX INFO: compiled from: FormulaSpannableStringBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0002J \u0010\u000e\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0004H\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/FormulaSpannableStringBuilder;", "", "()V", "BITMAP_SIZE_MULTIPLIER", "", "addColoredSquareToColorString", "", "context", "Landroid/content/Context;", "colorString", "", "bitmapSize", "stringBuilder", "Landroid/text/SpannableStringBuilder;", "buildSpannableFormulaString", "formulaString", StageActivity.STYLE_TEXT_SIZE, "isColorString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FormulaSpannableStringBuilder {
    private static final float BITMAP_SIZE_MULTIPLIER = 1.25f;
    public static final FormulaSpannableStringBuilder INSTANCE = new FormulaSpannableStringBuilder();

    private FormulaSpannableStringBuilder() {
    }

    @JvmStatic
    public static final SpannableStringBuilder buildSpannableFormulaString(Context context, String formulaString, float textSize) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(formulaString, "formulaString");
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        for (String str : StringsKt.split$default((CharSequence) formulaString, new String[]{" "}, false, 0, 6, (Object) null)) {
            FormulaSpannableStringBuilder formulaSpannableStringBuilder = INSTANCE;
            if (formulaSpannableStringBuilder.isColorString(str)) {
                formulaSpannableStringBuilder.addColoredSquareToColorString(context, str, BITMAP_SIZE_MULTIPLIER * textSize, spannableStringBuilder);
            } else if (str.length() != 0) {
                spannableStringBuilder.append((CharSequence) (str + " "));
            }
        }
        return spannableStringBuilder;
    }

    private final boolean isColorString(String colorString) {
        return new Regex("^'#.{6}'$").matches(colorString);
    }

    private final void addColoredSquareToColorString(Context context, String colorString, float bitmapSize, SpannableStringBuilder stringBuilder) {
        String strSubstring = colorString.substring(0, colorString.length() - 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        stringBuilder.append((CharSequence) (strSubstring + " "));
        stringBuilder.setSpan(new VisualizeColorString(context, colorString, bitmapSize).getImageSpan(), stringBuilder.length() - 1, stringBuilder.length(), 33);
        stringBuilder.append("' ");
    }
}
