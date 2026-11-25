package org.catrobat.catroid.utils.notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.transfers.project.ProjectUploadService;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/notifications/StatusBarNotificationManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class StatusBarNotificationManager {
    private static final String ACTION_CANCEL_UPLOAD = "cancel_upload";
    private static final String ACTION_RETRY_UPLOAD = "retry_upload";
    private static final String ACTION_UPDATE_POCKET_CODE_VERSION = "update_pocket_code_version";
    public static final String CHANNEL_ID = "pocket_code_notification_channel_id";
    private static final int NOTIFICATION_PENDING_INTENT_REQUEST_CODE = 1;
    private static final String TAG = "StatusBarNotificationManager";
    public static final int UPLOAD_PENDING_INTENT_REQUEST_CODE = 65535;
    private static int notificationId = 1;
    private NotificationManager notificationManager;

    public StatusBarNotificationManager(Context context) {
        this.notificationManager = (NotificationManager) context.getSystemService("notification");
        createNotificationChannel(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public NotificationData createAndShowUploadNotification(Context context, String programName) {
        PendingIntent activity;
        if (context == null || programName == null) {
            return null;
        }
        Intent intent = new Intent(context, (Class<?>) MainMenuActivity.class);
        intent.setAction("android.intent.action.MAIN");
        Intent flags = intent.setFlags(2097152);
        if (Build.VERSION.SDK_INT >= 31) {
            activity = PendingIntent.getActivity(context, notificationId, flags, 335544320);
        } else {
            activity = PendingIntent.getActivity(context, notificationId, flags, 335544320);
        }
        NotificationData notificationData = new NotificationData(R.drawable.ic_stat, programName, context.getString(R.string.notification_upload_title_pending), context.getString(R.string.notification_upload_title_finished), context.getString(R.string.notification_upload_pending), context.getString(R.string.notification_upload_finished), 0, 100, true, false, getNextNotificationID());
        showOrUpdateNotification(context, notificationData, 0, activity);
        return notificationData;
    }

    public NotificationData createSaveProjectToExternalMemoryNotification(Context context, Uri projectDestination, String programName) {
        if (context == null || programName == null) {
            return null;
        }
        NotificationData notificationData = new NotificationData(R.drawable.ic_stat, programName, context.getString(R.string.notification_save_project_to_external_storage_title_pending), context.getString(R.string.notification_save_project_to_external_storage_title_open), context.getString(R.string.notification_save_project_to_external_storage_pending), context.getString(R.string.notification_save_project_to_external_storage_open, getPathOrFileName(context, projectDestination)), 0, 100, false, false, getNextNotificationID());
        showOrUpdateNotification(context, notificationData, 0, null);
        return notificationData;
    }

    public NotificationData createBuildProjectToExternalMemoryNotification(Context context, Uri projectDestination, String programName) {
        if (context == null || programName == null) {
            return null;
        }
        getPathOrFileName(context, projectDestination);
        NotificationData notificationData = new NotificationData(R.drawable.ic_stat, programName, context.getString(R.string.notification_save_project_to_external_storage_title_pending), context.getString(R.string.notification_save_project_to_external_storage_title_open), "Building APK...", "APK Saved!", 0, 100, false, false, getNextNotificationID());
        showOrUpdateNotification(context, notificationData, 0, null);
        return notificationData;
    }

    private String getPathOrFileName(Context context, Uri uri) {
        String string;
        String path = uri.getPath();
        if (uri.getScheme().equals(FirebaseAnalytics.Param.CONTENT)) {
            if (Build.VERSION.SDK_INT >= 29) {
                Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_display_name"}, null, null);
                if (cursorQuery != null) {
                    if (cursorQuery.moveToFirst() && (string = cursorQuery.getString(cursorQuery.getColumnIndex("_display_name"))) != null) {
                        path = string;
                    }
                    cursorQuery.close();
                }
            } else {
                Log.e(TAG, "Error while resolving file name.");
            }
        }
        return path;
    }

    public NotificationData createProjectDownloadNotification(Context context, String programName) {
        return new NotificationData(R.drawable.ic_stat, programName, context.getString(R.string.notification_download_title_pending), context.getString(R.string.notification_title_open), context.getString(R.string.notification_download_pending), context.getString(R.string.notification_download_finished), 0, 100, true, false, getNextNotificationID());
    }

    public static int getNextNotificationID() {
        int i = notificationId;
        notificationId = i + 1;
        return i;
    }

    public void showOrUpdateNotification(Context context, NotificationData notificationData, int progressInPercent, PendingIntent contentIntent) {
        int notificationID = notificationData.getNotificationID();
        notificationData.setProgressInPercent(progressInPercent);
        if (progressInPercent < 100) {
            ToastUtil.showSuccess(context, notificationData.getTextWorking());
        } else {
            notificationData.setProgressInPercent(0);
            notificationData.setMaxProgress(0);
            notificationData.setAutoCancel(true);
            notificationData.setOngoing(false);
            ToastUtil.showSuccess(context, notificationData.getTextDone());
        }
        this.notificationManager.notify(notificationID, notificationData.toNotification(context, CHANNEL_ID, contentIntent));
    }

    public void abortProgressNotificationWithMessage(Context context, NotificationData notificationData, int changeDoneText) {
        if (notificationData == null) {
            return;
        }
        notificationData.setTextDone(context.getString(changeDoneText));
        showOrUpdateNotification(context, notificationData, 100, null);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:21:0x00d0  */
    public Notification createUploadRejectedNotification(Context context, int statusCode, String serverAnswer, Bundle bundle) {
        PendingIntent activity;
        NotificationCompat.Builder ongoing = new NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle(context.getResources().getString(R.string.notification_upload_rejected)).setContentText(serverAnswer).setTicker(context.getResources().getString(R.string.notification_upload_rejected)).setSound(RingtoneManager.getDefaultUri(2)).setStyle(new NotificationCompat.BigTextStyle().bigText(serverAnswer)).setProgress(0, 0, false).setAutoCancel(true).setPriority(2).setOngoing(false);
        if (statusCode != 500 && statusCode != 501 && statusCode != 513 && statusCode != 514) {
            if (statusCode == 518 || statusCode == 519) {
                ongoing.addAction(new NotificationCompat.Action(R.drawable.pc_toolbar_icon, context.getResources().getString(R.string.notification_open_play_store), PendingIntent.getService(context, 1, new Intent(context, (Class<?>) NotificationActionService.class).setAction(ACTION_UPDATE_POCKET_CODE_VERSION).putExtra("notificationId", 1), 1107296256)));
            } else {
                switch (statusCode) {
                    case 503:
                        ongoing.addAction(new NotificationCompat.Action(R.drawable.pc_toolbar_icon, context.getResources().getString(R.string.notification_open_play_store), PendingIntent.getService(context, 1, new Intent(context, (Class<?>) NotificationActionService.class).setAction(ACTION_UPDATE_POCKET_CODE_VERSION).putExtra("notificationId", 1), 1107296256)));
                        break;
                    case 504:
                    case 505:
                    case 506:
                    case 507:
                        Intent action = new Intent(context, (Class<?>) NotificationActionService.class).setAction(ACTION_RETRY_UPLOAD);
                        action.putExtra("bundle", bundle);
                        ongoing.addAction(new NotificationCompat.Action(android.R.drawable.ic_popup_sync, context.getResources().getString(R.string.notification_upload_retry), PendingIntent.getService(context, 1, action, 301989888)));
                        Intent action2 = new Intent(context, (Class<?>) NotificationActionService.class).setAction(ACTION_CANCEL_UPLOAD);
                        action2.putExtra("bundle", bundle);
                        ongoing.addAction(new NotificationCompat.Action(android.R.drawable.ic_menu_close_clear_cancel, context.getResources().getString(R.string.cancel), PendingIntent.getService(context, 1, action2, 1107296256)));
                        break;
                    default:
                        Intent intent = new Intent(context, (Class<?>) MainMenuActivity.class);
                        intent.setAction("android.intent.action.MAIN").setFlags(67108864).putExtra("projectName", bundle.getString("projectName"));
                        if (Build.VERSION.SDK_INT >= 31) {
                            activity = PendingIntent.getActivity(context, notificationId, intent, 335544320);
                        } else {
                            activity = PendingIntent.getActivity(context, notificationId, intent, 335544320);
                        }
                        ongoing.setContentIntent(activity);
                        break;
                }
            }
        } else {
            Intent action3 = new Intent(context, (Class<?>) NotificationActionService.class).setAction(ACTION_RETRY_UPLOAD);
            action3.putExtra("bundle", bundle);
            ongoing.addAction(new NotificationCompat.Action(android.R.drawable.ic_popup_sync, context.getResources().getString(R.string.notification_upload_retry), PendingIntent.getService(context, 1, action3, 301989888)));
            Intent action4 = new Intent(context, (Class<?>) NotificationActionService.class).setAction(ACTION_CANCEL_UPLOAD);
            action4.putExtra("bundle", bundle);
            ongoing.addAction(new NotificationCompat.Action(android.R.drawable.ic_menu_close_clear_cancel, context.getResources().getString(R.string.cancel), PendingIntent.getService(context, 1, action4, 1107296256)));
        }
        return ongoing.build();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/notifications/StatusBarNotificationManager$NotificationActionService.class
      classes14.dex
     */
    public static class NotificationActionService extends IntentService {
        public NotificationActionService() {
            super(NotificationActionService.class.getSimpleName());
        }

        @Override // android.app.IntentService
        protected void onHandleIntent(Intent intent) {
            String action = intent.getAction();
            Log.d(StatusBarNotificationManager.TAG, "Received notification, action is: " + action);
            if (StatusBarNotificationManager.ACTION_UPDATE_POCKET_CODE_VERSION.equals(action)) {
                String packageName = getPackageName();
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)).addFlags(268435456));
                } catch (ActivityNotFoundException unused) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Constants.PLAY_STORE_PAGE_LINK + packageName)));
                }
                closeNotificationBar();
            }
            if (StatusBarNotificationManager.ACTION_RETRY_UPLOAD.equals(action)) {
                Intent intentPrepareReuploadIntent = prepareReuploadIntent(intent);
                NotificationData notificationDataCreateAndShowUploadNotification = new StatusBarNotificationManager(getApplicationContext()).createAndShowUploadNotification(getApplicationContext(), intent.getBundleExtra("bundle").getString("projectName"));
                intentPrepareReuploadIntent.putExtra("notificationId", notificationDataCreateAndShowUploadNotification == null ? -1 : notificationDataCreateAndShowUploadNotification.getNotificationID());
                getApplicationContext().startService(intentPrepareReuploadIntent);
            }
            if (StatusBarNotificationManager.ACTION_CANCEL_UPLOAD.equals(action)) {
                closeNotificationBar();
            }
        }

        private Intent prepareReuploadIntent(Intent intent) {
            String string = intent.getBundleExtra("bundle").getString("projectName");
            String string2 = intent.getBundleExtra("bundle").getString(Constants.EXTRA_PROJECT_DESCRIPTION);
            String string3 = intent.getBundleExtra("bundle").getString(Constants.EXTRA_PROJECT_PATH);
            String[] stringArray = intent.getBundleExtra("bundle").getStringArray(Constants.EXTRA_SCENE_NAMES);
            String string4 = intent.getBundleExtra("bundle").getString("token");
            String string5 = intent.getBundleExtra("bundle").getString("username");
            ResultReceiver resultReceiver = (ResultReceiver) intent.getBundleExtra("bundle").getParcelable("receiver");
            Intent intent2 = new Intent(getApplicationContext(), (Class<?>) ProjectUploadService.class);
            intent2.putExtra("receiver", resultReceiver);
            intent2.putExtra(Constants.EXTRA_UPLOAD_NAME, string);
            intent2.putExtra(Constants.EXTRA_PROJECT_DESCRIPTION, string2);
            intent2.putExtra(Constants.EXTRA_PROJECT_PATH, string3);
            intent2.putExtra("username", string5);
            intent2.putExtra("token", string4);
            intent2.putExtra(Constants.EXTRA_SCENE_NAMES, stringArray);
            return intent2;
        }

        private void closeNotificationBar() {
            sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        }
    }

    public void createNotificationChannel(Context context) {
        String string = context.getResources().getString(R.string.app_name);
        String string2 = context.getResources().getString(R.string.channel_description, string);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        if (notificationManager == null || notificationManager.getNotificationChannel(CHANNEL_ID) != null) {
            return;
        }
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, string, 3);
        notificationChannel.setDescription(string2);
        notificationChannel.enableVibration(false);
        notificationChannel.enableLights(false);
        notificationChannel.setSound(null, null);
        notificationManager.createNotificationChannel(notificationChannel);
    }
}
