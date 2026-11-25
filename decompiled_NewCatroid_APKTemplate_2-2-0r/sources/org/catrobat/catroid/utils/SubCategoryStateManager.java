package org.catrobat.catroid.utils;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/SubCategoryStateManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: SubCategoryStateManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\b\b\u0002\u0010\n\u001a\u00020\u0006J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/utils/SubCategoryStateManager;", "", "()V", "PREFS_NAME", "", "isExpanded", "", "context", "Landroid/content/Context;", "title", "default", "setExpanded", "", "expanded", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SubCategoryStateManager {
    public static final SubCategoryStateManager INSTANCE = new SubCategoryStateManager();
    private static final String PREFS_NAME = "subcategory_states_prefs";

    private SubCategoryStateManager() {
    }

    public static /* synthetic */ boolean isExpanded$default(SubCategoryStateManager subCategoryStateManager, Context context, String str, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return subCategoryStateManager.isExpanded(context, str, z);
    }

    public final boolean isExpanded(Context context, String title, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(title, "title");
        return context.getSharedPreferences(PREFS_NAME, 0).getBoolean(title, z);
    }

    public final void setExpanded(Context context, String title, boolean expanded) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(title, "title");
        context.getSharedPreferences(PREFS_NAME, 0).edit().putBoolean(title, expanded).apply();
    }
}
