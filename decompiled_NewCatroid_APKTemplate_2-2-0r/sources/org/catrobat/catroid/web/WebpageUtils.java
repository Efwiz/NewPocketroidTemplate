package org.catrobat.catroid.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.ContextCompat;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/WebpageUtils.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class WebpageUtils {
    private static final String REGEX_WIKI = "https://catrob.at/regex";

    private WebpageUtils() {
    }

    public static void openWikiPage(Context context) {
        ContextCompat.startActivity(context, new Intent("android.intent.action.VIEW", Uri.parse(REGEX_WIKI)), null);
    }

    public static void openWebPage(Context context, String url) {
        ContextCompat.startActivity(context, new Intent("android.intent.action.VIEW", Uri.parse(url)), null);
    }
}
