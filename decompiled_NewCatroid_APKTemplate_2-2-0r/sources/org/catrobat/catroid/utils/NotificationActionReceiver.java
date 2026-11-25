package org.catrobat.catroid.utils;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.RemoteInput;
import com.huawei.hms.support.api.entity.core.CommonCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NotificationActionReceiver.class
  classes14.dex
 */
/* JADX INFO: compiled from: NotificationActionReceiver.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/utils/NotificationActionReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotificationActionReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        CharSequence charSequence;
        String string;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        String stringExtra = intent.getStringExtra("ACTION_ID");
        if (stringExtra == null) {
            return;
        }
        int intExtra = intent.getIntExtra("NOTIF_ID", -1);
        String stringExtra2 = intent.getStringExtra("NOTIF_DRAFT_ID");
        String str = "";
        if (stringExtra2 == null) {
            stringExtra2 = "";
        }
        boolean booleanExtra = intent.getBooleanExtra("AUTO_CLOSE", false);
        Bundle resultsFromIntent = RemoteInput.getResultsFromIntent(intent);
        if (resultsFromIntent != null && (charSequence = resultsFromIntent.getCharSequence("KEY_REPLY")) != null && (string = charSequence.toString()) != null) {
            str = string;
        }
        NewCatroidNotificationManager.INSTANCE.getSavedReplies().put(stringExtra, str);
        Object systemService = context.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        if (!booleanExtra) {
            NewCatroidNotificationManager.NotificationState notificationState = NewCatroidNotificationManager.INSTANCE.getActiveDrafts().get(stringExtra2);
            if (notificationState != null) {
                NewCatroidNotificationManager.INSTANCE.buildAndShow(context, notificationState);
            } else if (intExtra != -1) {
                notificationManager.cancel(intExtra);
            }
        } else if (intExtra != -1) {
            notificationManager.cancel(intExtra);
        }
        NewCatroidNotificationManager.INSTANCE.getPendingActions().add(stringExtra);
    }
}
