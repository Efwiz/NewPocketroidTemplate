package org.catrobat.catroid.utils;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;
import org.catrobat.catroid.ui.UiUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ToastUtil.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ToastUtil {
    private static final int DEFAULT_COLOR = 0;

    private ToastUtil() {
    }

    public static void showError(Context context, String message) {
        createToast(context, message, 0, 0);
    }

    public static void showError(Context context, int messageId) {
        createToast(context, context.getResources().getString(messageId), 0, 0);
    }

    public static void showErrorWithColor(Context context, int messageId, int color) {
        createToast(context, context.getResources().getString(messageId), 1, color);
    }

    public static void showSuccess(Context context, String message) {
        createToast(context, message, 0, 0);
    }

    public static void showSuccess(Context context, int messageId) {
        createToast(context, context.getResources().getString(messageId), 0, 0);
    }

    public static void showInfoLong(Context context, String message) {
        createToast(context, message, 1, 0);
    }

    private static void createToast(final Context context, final String message, final int duration, final int color) {
        Activity activityFromContextWrapper;
        if (context instanceof Activity) {
            activityFromContextWrapper = (Activity) context;
        } else {
            activityFromContextWrapper = UiUtils.getActivityFromContextWrapper(context);
            if (activityFromContextWrapper == null) {
                return;
            }
        }
        activityFromContextWrapper.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.utils.ToastUtil$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ToastUtil.lambda$createToast$0(context, message, duration, color);
            }
        });
    }

    static /* synthetic */ void lambda$createToast$0(Context context, String str, int i, int i2) {
        Toast toastMakeText = Toast.makeText(context, str, i);
        if (i2 != 0) {
            ((TextView) toastMakeText.getView().findViewById(R.id.message)).setTextColor(i2);
        }
        toastMakeText.show();
    }
}
