package org.catrobat.catroid.transfers.project;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import java.io.File;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.io.ZipArchiver;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment;
import org.catrobat.catroid.utils.DeviceSettingsProvider;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.utils.notifications.StatusBarNotificationManager;
import org.catrobat.catroid.web.CatrobatWebClient;
import org.catrobat.catroid.web.ServerCalls;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/project/ProjectUploadService.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectUploadService.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\b\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\u0006H\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006H\u0002J\b\u0010\r\u001a\u00020\u000bH\u0016J\u0012\u0010\u000e\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/transfers/project/ProjectUploadService;", "Landroid/app/IntentService;", "()V", "createUploadFinishedNotification", "Landroid/app/Notification;", ReplaceExistingProjectDialogFragment.BUNDLE_KEY_PROGRAM_NAME, "", "createUploadNotification", "getUserEmail", Constants.EXTRA_PROVIDER, "logWarning", "", "warningMessage", "onDestroy", "onHandleIntent", "projectUploadIntent", "Landroid/content/Intent;", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectUploadService extends IntentService {
    private static final String TAG = "ProjectUploadService";

    public ProjectUploadService() {
        super("ProjectUploadService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent projectUploadIntent) {
        if (projectUploadIntent == null) {
            logWarning("Called ProjectUploadService with null intent!");
            return;
        }
        String stringExtra = projectUploadIntent.getStringExtra(Constants.EXTRA_PROJECT_PATH);
        if (stringExtra == null) {
            logWarning("Called ProjectUploadService without project path!");
            return;
        }
        File file = new File(stringExtra);
        File[] fileArrListFiles = file.listFiles();
        Intrinsics.checkNotNullExpressionValue(fileArrListFiles, "listFiles(...)");
        if (fileArrListFiles.length == 0) {
            logWarning("Called ProjectUploadService with empty project directory!");
            return;
        }
        Parcelable parcelableExtra = projectUploadIntent.getParcelableExtra(Constants.EXTRA_RESULT_RECEIVER);
        final ResultReceiver resultReceiver = parcelableExtra instanceof ResultReceiver ? (ResultReceiver) parcelableExtra : null;
        if (resultReceiver == null) {
            logWarning("Called ProjectUploadService without resultReceiver!");
            return;
        }
        final String stringExtra2 = projectUploadIntent.getStringExtra(Constants.EXTRA_UPLOAD_NAME);
        if (stringExtra2 == null) {
            logWarning("Called ProjectUploadService with empty project name!");
            return;
        }
        final int nextNotificationID = StatusBarNotificationManager.getNextNotificationID();
        startForeground(nextNotificationID, createUploadNotification(stringExtra2));
        final Bundle bundle = new Bundle();
        bundle.putString("projectName", stringExtra2);
        bundle.putString(Constants.EXTRA_PROJECT_DESCRIPTION, projectUploadIntent.getStringExtra(Constants.EXTRA_PROJECT_DESCRIPTION));
        bundle.putString(Constants.EXTRA_PROJECT_PATH, stringExtra);
        bundle.putStringArray(Constants.EXTRA_SCENE_NAMES, projectUploadIntent.getStringArrayExtra(Constants.EXTRA_SCENE_NAMES));
        bundle.putString(Constants.EXTRA_USER_EMAIL, getUserEmail(projectUploadIntent.getStringExtra(Constants.EXTRA_PROVIDER)));
        bundle.putString("language", Locale.getDefault().getLanguage());
        String stringExtra3 = projectUploadIntent.getStringExtra(Constants.EXTRA_PROJECT_DESCRIPTION);
        if (stringExtra3 == null) {
            stringExtra3 = "";
        }
        String userEmail = getUserEmail(projectUploadIntent.getStringExtra(Constants.EXTRA_PROVIDER));
        String[] stringArrayExtra = projectUploadIntent.getStringArrayExtra(Constants.EXTRA_SCENE_NAMES);
        File file2 = new File(getCacheDir(), ProjectUploadServiceKt.UPLOAD_FILE_NAME);
        ZipArchiver zipArchiver = new ZipArchiver();
        ProjectAndSceneScreenshotLoader projectAndSceneScreenshotLoader = new ProjectAndSceneScreenshotLoader(getApplicationContext().getResources().getDimensionPixelSize(R.dimen.project_thumbnail_width), getApplicationContext().getResources().getDimensionPixelSize(R.dimen.project_thumbnail_height));
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intrinsics.checkNotNullExpressionValue(defaultSharedPreferences, "getDefaultSharedPreferences(...)");
        new ProjectUpload(file, stringExtra2, stringExtra3, userEmail, stringArrayExtra, file2, zipArchiver, projectAndSceneScreenshotLoader, defaultSharedPreferences, new ServerCalls(CatrobatWebClient.INSTANCE.getClient())).start(new Function1<String, Unit>() { // from class: org.catrobat.catroid.transfers.project.ProjectUploadService.onHandleIntent.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String projectId) {
                Intrinsics.checkNotNullParameter(projectId, "projectId");
                Log.v(ProjectUploadService.TAG, "Upload successful");
                ProjectUploadService.this.stopForeground(true);
                Object systemService = ProjectUploadService.this.getSystemService("notification");
                NotificationManager notificationManager = systemService instanceof NotificationManager ? (NotificationManager) systemService : null;
                if (notificationManager != null) {
                    notificationManager.notify(nextNotificationID, ProjectUploadService.this.createUploadFinishedNotification(stringExtra2));
                }
                ToastUtil.showSuccess(ProjectUploadService.this, R.string.notification_upload_finished);
                Bundle bundle2 = new Bundle();
                bundle2.putString(Constants.EXTRA_PROJECT_ID, projectId);
                resultReceiver.send(1, bundle2);
            }
        }, new Function2<Integer, String, Unit>() { // from class: org.catrobat.catroid.transfers.project.ProjectUploadService.onHandleIntent.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str) {
                invoke(num.intValue(), str);
                return Unit.INSTANCE;
            }

            public final void invoke(int i, String errorMessage) {
                Intrinsics.checkNotNullParameter(errorMessage, "errorMessage");
                Log.e(ProjectUploadService.TAG, errorMessage);
                ProjectUploadService.this.stopForeground(true);
                ProjectUploadService projectUploadService = ProjectUploadService.this;
                ToastUtil.showError(projectUploadService, projectUploadService.getResources().getString(R.string.error_project_upload) + " " + errorMessage);
                new StatusBarNotificationManager(ProjectUploadService.this.getApplicationContext()).createUploadRejectedNotification(ProjectUploadService.this.getApplicationContext(), i, errorMessage, bundle);
                resultReceiver.send(0, null);
            }
        });
    }

    @Override // android.app.IntentService, android.app.Service
    public void onDestroy() {
        Utils.invalidateLoginTokenIfUserRestricted(getApplicationContext());
        super.onDestroy();
    }

    private final Notification createUploadNotification(String programName) {
        PendingIntent activity;
        new StatusBarNotificationManager(getApplicationContext()).createNotificationChannel(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), (Class<?>) MainMenuActivity.class);
        intent.setAction("android.intent.action.MAIN");
        Intent flags = intent.setFlags(2097152);
        Intrinsics.checkNotNullExpressionValue(flags, "setFlags(...)");
        if (Build.VERSION.SDK_INT >= 31) {
            activity = PendingIntent.getActivity(getApplicationContext(), 65535, flags, 335544320);
        } else {
            activity = PendingIntent.getActivity(getApplicationContext(), 65535, flags, 335544320);
        }
        Notification notificationBuild = new NotificationCompat.Builder(getApplicationContext(), StatusBarNotificationManager.CHANNEL_ID).setContentIntent(activity).setSmallIcon(R.drawable.ic_stat).setContentTitle(getApplicationContext().getString(R.string.notification_upload_title_pending) + " " + programName).setContentText(getApplicationContext().getString(R.string.notification_upload_pending)).setOngoing(true).setProgress(100, 0, true).build();
        Intrinsics.checkNotNullExpressionValue(notificationBuild, "build(...)");
        return notificationBuild;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Notification createUploadFinishedNotification(String programName) {
        PendingIntent activity;
        Intent intent = new Intent(getApplicationContext(), (Class<?>) MainMenuActivity.class);
        intent.setAction("android.intent.action.MAIN");
        Intent flags = intent.setFlags(2097152);
        Intrinsics.checkNotNullExpressionValue(flags, "setFlags(...)");
        if (Build.VERSION.SDK_INT >= 31) {
            activity = PendingIntent.getActivity(getApplicationContext(), 65535, flags, 335544320);
        } else {
            activity = PendingIntent.getActivity(getApplicationContext(), 65535, flags, 335544320);
        }
        Notification notificationBuild = new NotificationCompat.Builder(getApplicationContext(), StatusBarNotificationManager.CHANNEL_ID).setContentIntent(activity).setSmallIcon(R.drawable.ic_stat).setContentTitle(getApplicationContext().getString(R.string.notification_upload_title_finished) + " " + programName).setContentText(getApplicationContext().getString(R.string.notification_upload_finished)).setAutoCancel(true).setOngoing(false).setProgress(0, 0, false).build();
        Intrinsics.checkNotNullExpressionValue(notificationBuild, "build(...)");
        return notificationBuild;
    }

    private final String getUserEmail(String provider) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String string = Intrinsics.areEqual(provider, Constants.GOOGLE_PLUS) ? defaultSharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL) : defaultSharedPreferences.getString("email", Constants.NO_EMAIL);
        if (Intrinsics.areEqual(string, Constants.NO_EMAIL)) {
            string = DeviceSettingsProvider.getUserEmail(this);
        }
        return string == null ? "" : string;
    }

    private final void logWarning(String warningMessage) {
        Log.w(TAG, warningMessage);
    }
}
