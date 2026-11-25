package org.catrobat.catroid.ui.dialogs.regexassistant;

import android.content.Context;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/regexassistant/RegularExpressionFeature.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class RegularExpressionFeature {
    int titleId;

    public abstract void openDialog(Context context);

    public int getTitleId() {
        return this.titleId;
    }
}
