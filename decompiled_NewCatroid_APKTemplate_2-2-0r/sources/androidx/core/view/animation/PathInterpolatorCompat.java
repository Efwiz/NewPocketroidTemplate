package androidx.core.view.animation;

import android.graphics.Path;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/view/animation/PathInterpolatorCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/view/animation/PathInterpolatorCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class PathInterpolatorCompat {
    private PathInterpolatorCompat() {
    }

    public static Interpolator create(Path path) {
        return Api21Impl.createPathInterpolator(path);
    }

    public static Interpolator create(float f, float f2) {
        return Api21Impl.createPathInterpolator(f, f2);
    }

    public static Interpolator create(float f, float f2, float f3, float f4) {
        return Api21Impl.createPathInterpolator(f, f2, f3, f4);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/view/animation/PathInterpolatorCompat$Api21Impl.class
      classes.dex
     */
    static class Api21Impl {
        private Api21Impl() {
        }

        static Interpolator createPathInterpolator(Path path) {
            return new PathInterpolator(path);
        }

        static Interpolator createPathInterpolator(float f, float f2) {
            return new PathInterpolator(f, f2);
        }

        static Interpolator createPathInterpolator(float f, float f2, float f3, float f4) {
            return new PathInterpolator(f, f2, f3, f4);
        }
    }
}
