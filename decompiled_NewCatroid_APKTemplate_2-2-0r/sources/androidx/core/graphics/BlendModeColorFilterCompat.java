package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/graphics/BlendModeColorFilterCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/graphics/BlendModeColorFilterCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public class BlendModeColorFilterCompat {
    public static ColorFilter createBlendModeColorFilterCompat(int i, BlendModeCompat blendModeCompat) {
        if (Build.VERSION.SDK_INT >= 29) {
            Object objObtainBlendModeFromCompat = BlendModeUtils.Api29Impl.obtainBlendModeFromCompat(blendModeCompat);
            if (objObtainBlendModeFromCompat != null) {
                return Api29Impl.createBlendModeColorFilter(i, objObtainBlendModeFromCompat);
            }
            return null;
        }
        PorterDuff.Mode modeObtainPorterDuffFromCompat = BlendModeUtils.obtainPorterDuffFromCompat(blendModeCompat);
        if (modeObtainPorterDuffFromCompat != null) {
            return new PorterDuffColorFilter(i, modeObtainPorterDuffFromCompat);
        }
        return null;
    }

    private BlendModeColorFilterCompat() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/graphics/BlendModeColorFilterCompat$Api29Impl.class
      classes.dex
     */
    static class Api29Impl {
        private Api29Impl() {
        }

        static ColorFilter createBlendModeColorFilter(int i, Object obj) {
            return new BlendModeColorFilter(i, (BlendMode) obj);
        }
    }
}
