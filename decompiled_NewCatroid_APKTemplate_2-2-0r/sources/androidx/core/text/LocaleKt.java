package androidx.core.text;

import android.text.TextUtils;
import java.util.Locale;
import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-ktx-1.2.0.aar:classes.jar:androidx/core/text/LocaleKt.class
  classes.dex
 */
/* JADX INFO: compiled from: Locale.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"layoutDirection", "", "Ljava/util/Locale;", "getLayoutDirection", "(Ljava/util/Locale;)I", "core-ktx_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
public final class LocaleKt {
    public static final int getLayoutDirection(Locale locale) {
        return TextUtils.getLayoutDirectionFromLocale(locale);
    }
}
