package org.catrobat.catroid.utils;

import com.beust.jcommander.Parameters;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlin.time.DurationKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NumberFormats.class
  classes14.dex
 */
/* JADX INFO: compiled from: NumberFormats.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/catrobat/catroid/utils/NumberFormats;", "", "()V", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NumberFormats {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final String toMetricUnitRepresentation(int i) {
        return INSTANCE.toMetricUnitRepresentation(i);
    }

    @JvmStatic
    public static final String trimTrailingCharacters(String str) {
        return INSTANCE.trimTrailingCharacters(str);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NumberFormats$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: NumberFormats.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\u0004H\u0007¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/utils/NumberFormats$Companion;", "", "()V", "toMetricUnitRepresentation", "", "number", "", "trimTrailingCharacters", "value", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final String trimTrailingCharacters(String value) {
            if (value == null) {
                return "";
            }
            String str = value;
            if (!StringsKt.contains$default((CharSequence) str, (CharSequence) ".", false, 2, (Object) null) || !new Regex("(-?[1-9]\\d*|0)\\.(0|\\d*[1-9]0)").matches(str)) {
                return value;
            }
            return new Regex("\\.$").replace(new Regex("0$").replace(str, ""), "");
        }

        @JvmStatic
        public final String toMetricUnitRepresentation(int number) {
            String str;
            int iAbs;
            int i;
            if (number >= 0) {
                str = "";
                iAbs = number;
            } else {
                iAbs = Math.abs(number);
                str = Parameters.DEFAULT_OPTION_PREFIXES;
            }
            if (iAbs >= 1000000) {
                return str + (iAbs / DurationKt.NANOS_IN_MILLIS) + "M";
            }
            if (iAbs >= 10000 && (i = iAbs % 1000) > 100) {
                return str + (iAbs / 1000) + "." + (i / 100) + JvmAnnotationNames.KIND_FIELD_NAME;
            }
            if (iAbs >= 1000) {
                return str + (iAbs / 1000) + JvmAnnotationNames.KIND_FIELD_NAME;
            }
            return String.valueOf(number);
        }
    }

    private NumberFormats() {
    }
}
