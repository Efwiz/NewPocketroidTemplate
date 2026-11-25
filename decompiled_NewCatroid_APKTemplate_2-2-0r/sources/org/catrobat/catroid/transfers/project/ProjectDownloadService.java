package org.catrobat.catroid.transfers.project;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import com.huawei.hms.support.api.entity.core.CommonCode;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.io.ZipArchiver;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.notifications.NotificationData;
import org.catrobat.catroid.utils.notifications.StatusBarNotificationManager;
import org.catrobat.catroid.web.CatrobatServerCalls;
import org.catrobat.catroid.web.CatrobatWebClient;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/project/ProjectDownloadService.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectDownloadService.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J(\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J(\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\nH\u0002J\u0012\u0010\u0015\u001a\u00020\u00042\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/transfers/project/ProjectDownloadService;", "Landroid/app/IntentService;", "()V", "downloadErrorCallback", "", "context", "Landroid/content/Context;", Constants.EXTRA_RESULT_RECEIVER, "Landroid/os/ResultReceiver;", "projectName", "", "downloadProgressCallback", ProjectDownloadService.EXTRA_NOTIFICATION_DATA, "Lorg/catrobat/catroid/utils/notifications/NotificationData;", "progress", "", "downloadSuccessCallback", "destinationFile", "Ljava/io/File;", "logWarning", "warningMessage", "onHandleIntent", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectDownloadService extends IntentService {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String DOWNLOAD_FILE_NAME = "down.newtrobat";
    public static final int ERROR_CODE = 2;
    public static final String EXTRA_DOWNLOAD_NAME = "downloadName";
    public static final String EXTRA_NOTIFICATION_DATA = "notificationData";
    public static final String EXTRA_RESULT_RECEIVER = "receiver";
    public static final String EXTRA_URL = "url";
    public static final int SUCCESS_CODE = 3;
    private static final String TAG;
    public static final int UPDATE_PROGRESS_CODE = 1;
    public static final String UPDATE_PROGRESS_EXTRA = "progress";

    public ProjectDownloadService() {
        super("ProjectDownloadService");
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/project/ProjectDownloadService$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectDownloadService.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/transfers/project/ProjectDownloadService$Companion;", "", "()V", "DOWNLOAD_FILE_NAME", "", "ERROR_CODE", "", "EXTRA_DOWNLOAD_NAME", "EXTRA_NOTIFICATION_DATA", "EXTRA_RESULT_RECEIVER", "EXTRA_URL", "SUCCESS_CODE", "TAG", "getTAG", "()Ljava/lang/String;", "UPDATE_PROGRESS_CODE", "UPDATE_PROGRESS_EXTRA", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return ProjectDownloadService.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ProjectDownloadService", "getSimpleName(...)");
        TAG = "ProjectDownloadService";
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            logWarning("Called ProjectDownloadService with null intent - aborting");
            return;
        }
        final String stringExtra = intent.getStringExtra(EXTRA_DOWNLOAD_NAME);
        if (stringExtra == null) {
            logWarning("Called ProjectDownloadService with null projectName -  aborting");
            return;
        }
        String stringExtra2 = intent.getStringExtra("url");
        if (stringExtra2 == null) {
            logWarning("Called ProjectDownloadService without url - aborting");
            return;
        }
        final ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        if (resultReceiver == null) {
            logWarning("Called ProjectDownloadService without url - aborting");
            return;
        }
        final File file = new File(new File(new File(Constants.CACHE_DIRECTORY, Constants.TMP_DIRECTORY_NAME), DOWNLOAD_FILE_NAME).getAbsolutePath());
        File parentFile = file.getParentFile();
        boolean zIsDirectory = parentFile != null ? parentFile.isDirectory() : false;
        File parentFile2 = file.getParentFile();
        if (!(zIsDirectory | (parentFile2 != null ? parentFile2.mkdirs() : false))) {
            ToastUtil.showError(this, R.string.error_project_download);
            return;
        }
        Serializable serializableExtra = intent.getSerializableExtra(EXTRA_NOTIFICATION_DATA);
        Intrinsics.checkNotNull(serializableExtra, "null cannot be cast to non-null type org.catrobat.catroid.utils.notifications.NotificationData");
        final NotificationData notificationData = (NotificationData) serializableExtra;
        startForeground(notificationData.getNotificationID(), notificationData.toNotification(this, StatusBarNotificationManager.CHANNEL_ID, null));
        new CatrobatServerCalls(CatrobatWebClient.INSTANCE.getClient()).downloadProject(stringExtra2, file, new CatrobatServerCalls.DownloadSuccessCallback() { // from class: org.catrobat.catroid.transfers.project.ProjectDownloadService.onHandleIntent.1
            @Override // org.catrobat.catroid.web.CatrobatServerCalls.DownloadSuccessCallback
            public void onSuccess() throws Throwable {
                ProjectDownloadService projectDownloadService = ProjectDownloadService.this;
                projectDownloadService.downloadSuccessCallback(projectDownloadService, stringExtra, file, resultReceiver);
            }
        }, new CatrobatServerCalls.DownloadErrorCallback() { // from class: org.catrobat.catroid.transfers.project.ProjectDownloadService.onHandleIntent.2
            @Override // org.catrobat.catroid.web.CatrobatServerCalls.DownloadErrorCallback
            public void onError(int code, String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                ProjectDownloadService projectDownloadService = ProjectDownloadService.this;
                projectDownloadService.downloadErrorCallback(projectDownloadService, resultReceiver, stringExtra);
            }
        }, new CatrobatServerCalls.DownloadProgressCallback() { // from class: org.catrobat.catroid.transfers.project.ProjectDownloadService.onHandleIntent.3
            @Override // org.catrobat.catroid.web.CatrobatServerCalls.DownloadProgressCallback
            public void onProgress(long progress) {
                ProjectDownloadService projectDownloadService = ProjectDownloadService.this;
                projectDownloadService.downloadProgressCallback(projectDownloadService, resultReceiver, notificationData, progress);
            }
        });
        stopForeground(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void downloadSuccessCallback(Context context, String projectName, File destinationFile, ResultReceiver resultReceiver) throws Throwable {
        PendingIntent activity;
        StatusBarNotificationManager statusBarNotificationManager = new StatusBarNotificationManager(context);
        NotificationData notificationDataCreateProjectDownloadNotification = statusBarNotificationManager.createProjectDownloadNotification(this, projectName);
        try {
            File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(projectName));
            new ZipArchiver().unzip(destinationFile, file);
            XstreamSerializer.renameProject(new File(file, Constants.CODE_XML_FILE_NAME), projectName);
            ProjectManager.getInstance().addNewDownloadedProject(projectName);
            Intent intent = new Intent(context, (Class<?>) MainMenuActivity.class);
            intent.setAction("android.intent.action.MAIN").setFlags(67108864).putExtra("projectName", projectName);
            if (Build.VERSION.SDK_INT >= 31) {
                activity = PendingIntent.getActivity(context, notificationDataCreateProjectDownloadNotification.getNotificationID(), intent, 335544320);
                Intrinsics.checkNotNull(activity);
            } else {
                activity = PendingIntent.getActivity(context, notificationDataCreateProjectDownloadNotification.getNotificationID(), intent, 335544320);
                Intrinsics.checkNotNull(activity);
            }
            statusBarNotificationManager.showOrUpdateNotification(context, notificationDataCreateProjectDownloadNotification, 100, activity);
            resultReceiver.send(3, new Bundle());
        } catch (IOException e) {
            String message = e.getMessage();
            if (message != null) {
                Log.i(TAG, message);
            }
            statusBarNotificationManager.abortProgressNotificationWithMessage(context, notificationDataCreateProjectDownloadNotification, R.string.error_project_download);
            resultReceiver.send(2, new Bundle());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void downloadErrorCallback(Context context, ResultReceiver resultReceiver, String projectName) {
        StatusBarNotificationManager statusBarNotificationManager = new StatusBarNotificationManager(context);
        statusBarNotificationManager.abortProgressNotificationWithMessage(context, statusBarNotificationManager.createProjectDownloadNotification(this, projectName), R.string.error_project_download);
        resultReceiver.send(2, new Bundle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void downloadProgressCallback(Context context, ResultReceiver resultReceiver, NotificationData notificationData, long progress) {
        int i = (int) progress;
        new StatusBarNotificationManager(context).showOrUpdateNotification(context, notificationData, i, null);
        Bundle bundle = new Bundle();
        bundle.putInt("progress", i);
        resultReceiver.send(1, bundle);
    }

    private final void logWarning(String warningMessage) {
        Log.w(TAG, warningMessage);
    }
}
