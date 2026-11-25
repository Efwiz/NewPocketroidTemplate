package androidx.core.text;

import android.text.TextUtils;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import java.util.Locale;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/text/TextUtilsCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/text/TextUtilsCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class TextUtilsCompat {
    public static String htmlEncode(String str) {
        return TextUtils.htmlEncode(str);
    }

    public static int getLayoutDirectionFromLocale(Locale locale) {
        return TextUtils.getLayoutDirectionFromLocale(locale);
    }

    private TextUtilsCompat() {
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/text/TextUtilsCompat$Api17Impl.class */
    @RequiresApi(17)
    static class Api17Impl {
        private Api17Impl() {
        }

        @DoNotInline
        static int getLayoutDirectionFromLocale(Locale locale) {
            return TextUtils.getLayoutDirectionFromLocale(locale);
        }
    }
}
