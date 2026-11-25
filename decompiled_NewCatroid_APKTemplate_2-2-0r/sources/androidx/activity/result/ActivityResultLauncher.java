package androidx.activity.result;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityOptionsCompat;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/activity-1.7.2.aar:classes.jar:androidx/activity/result/ActivityResultLauncher.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/activity.jar:androidx/activity/result/ActivityResultLauncher.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityResultLauncher<I> {
    public abstract ActivityResultContract<I, ?> getContract();

    public abstract void launch(I i, ActivityOptionsCompat activityOptionsCompat);

    public abstract void unregister();

    public void launch(I i) {
        launch(i, null);
    }
}
