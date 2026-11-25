package androidx.activity.result;

import androidx.activity.result.contract.ActivityResultContract;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/activity-1.7.2.aar:classes.jar:androidx/activity/result/ActivityResultCaller.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/activity.jar:androidx/activity/result/ActivityResultCaller.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public interface ActivityResultCaller {
    <I, O> ActivityResultLauncher<I> registerForActivityResult(ActivityResultContract<I, O> activityResultContract, ActivityResultCallback<O> activityResultCallback);

    <I, O> ActivityResultLauncher<I> registerForActivityResult(ActivityResultContract<I, O> activityResultContract, ActivityResultRegistry activityResultRegistry, ActivityResultCallback<O> activityResultCallback);
}
