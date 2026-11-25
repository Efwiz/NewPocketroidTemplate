package androidx.core.content;

import android.content.ContentProvider;
import android.content.Context;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/content/ContentProviderCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/content/ContentProviderCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class ContentProviderCompat {
    private ContentProviderCompat() {
    }

    public static Context requireContext(ContentProvider contentProvider) {
        Context context = contentProvider.getContext();
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("Cannot find context from the provider.");
    }
}
