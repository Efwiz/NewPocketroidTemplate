package org.catrobat.catroid.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.huawei.hms.support.api.entity.core.CommonCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NotificationPublisherReceiver.class
  classes14.dex
 */
/* JADX INFO: compiled from: NotificationPublisherReceiver.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/utils/NotificationPublisherReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotificationPublisherReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        String stringExtra = intent.getStringExtra("id");
        if (stringExtra == null) {
            return;
        }
        NewCatroidNotificationManager.NotificationState notificationState = new NewCatroidNotificationManager.NotificationState(stringExtra);
        String stringExtra2 = intent.getStringExtra("channelName");
        if (stringExtra2 == null) {
            stringExtra2 = "App Notifications";
        }
        notificationState.setChannelName(stringExtra2);
        String stringExtra3 = intent.getStringExtra("title");
        if (stringExtra3 == null) {
            stringExtra3 = "";
        }
        notificationState.setTitle(stringExtra3);
        String stringExtra4 = intent.getStringExtra("text");
        if (stringExtra4 == null) {
            stringExtra4 = "";
        }
        notificationState.setText(stringExtra4);
        String stringExtra5 = intent.getStringExtra("largeIconPath");
        notificationState.setLargeIconPath(stringExtra5 != null ? stringExtra5 : "");
        notificationState.setImportance(intent.getIntExtra("importance", 1));
        notificationState.setOngoing(intent.getBooleanExtra("isOngoing", false));
        NewCatroidNotificationManager.INSTANCE.buildAndShow(context, notificationState);
    }
}
