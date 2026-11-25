package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/graphics/drawable/TintAwareDrawable.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/graphics/drawable/TintAwareDrawable.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public interface TintAwareDrawable {
    void setTint(int i);

    void setTintList(ColorStateList colorStateList);

    void setTintMode(PorterDuff.Mode mode);
}
