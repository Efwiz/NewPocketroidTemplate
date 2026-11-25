package org.catrobat.catroid.formulaeditor.common;

import android.graphics.Color;
import androidx.core.view.ViewCompat;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/common/Conversions.class
  classes14.dex
 */
/* JADX INFO: compiled from: Conversions.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0019\u0010\f\u001a\u0004\u0018\u00010\u00042\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0010H\u0007J\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u0012H\u0002¢\u0006\u0002\u0010\u0015J\f\u0010\u0016\u001a\u00020\u000b*\u0004\u0018\u00010\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/common/Conversions;", "", "()V", "FALSE", "", "TRUE", "colorPattern", "Ljava/util/regex/Pattern;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "booleanToDouble", "value", "", "convertArgumentToDouble", "argument", "(Ljava/lang/Object;)Ljava/lang/Double;", "tryParseColor", "", "string", "", "defaultValue", "tryParseDouble", "(Ljava/lang/String;)Ljava/lang/Double;", "isValidHexColor", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Conversions {
    public static final double FALSE = 0.0d;
    public static final double TRUE = 1.0d;
    public static final Conversions INSTANCE = new Conversions();
    private static final Pattern colorPattern = Pattern.compile("#\\p{XDigit}{6}");

    @JvmStatic
    public static final double booleanToDouble(boolean value) {
        return value ? 1.0d : 0.0d;
    }

    @JvmStatic
    public static final int tryParseColor(String str) {
        return tryParseColor$default(str, 0, 2, null);
    }

    private Conversions() {
    }

    private final Double tryParseDouble(String argument) {
        if (StringUtils.containsAny(argument, "fFdD")) {
            return null;
        }
        try {
            return Double.valueOf(Double.parseDouble(argument));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public static /* synthetic */ int tryParseColor$default(String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = ViewCompat.MEASURED_STATE_MASK;
        }
        return tryParseColor(str, i);
    }

    @JvmStatic
    public static final int tryParseColor(String string, int defaultValue) {
        return INSTANCE.isValidHexColor(string) ? Color.parseColor(string) : defaultValue;
    }

    public final boolean isValidHexColor(String str) {
        return str != null && colorPattern.matcher(str).matches();
    }

    @JvmStatic
    public static final Double convertArgumentToDouble(Object argument) {
        if (argument != null) {
            return argument instanceof String ? INSTANCE.tryParseDouble((String) argument) : (Double) argument;
        }
        return null;
    }
}
