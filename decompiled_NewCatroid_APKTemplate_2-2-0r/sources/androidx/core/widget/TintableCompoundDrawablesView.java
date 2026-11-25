package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/widget/TintableCompoundDrawablesView.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/widget/TintableCompoundDrawablesView.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public interface TintableCompoundDrawablesView {
    ColorStateList getSupportCompoundDrawablesTintList();

    PorterDuff.Mode getSupportCompoundDrawablesTintMode();

    void setSupportCompoundDrawablesTintList(ColorStateList colorStateList);

    void setSupportCompoundDrawablesTintMode(PorterDuff.Mode mode);
}
