package org.catrobat.catroid.web;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.scratchconverter.Client;
import org.catrobat.catroid.transfers.project.ProjectDownloadService;
import org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.notifications.NotificationData;
import org.catrobat.catroid.utils.notifications.StatusBarNotificationManager;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ProjectDownloader.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectDownloader.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00142\u00020\u0001:\u0003\u0014\u0015\u0016B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0005J\u0018\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/web/ProjectDownloader;", "Ljava/io/Serializable;", "queue", "Lorg/catrobat/catroid/web/ProjectDownloader$ProjectDownloadQueue;", "url", "", "callback", "Lorg/catrobat/catroid/scratchconverter/Client$ProjectDownloadCallback;", "(Lorg/catrobat/catroid/web/ProjectDownloader$ProjectDownloadQueue;Ljava/lang/String;Lorg/catrobat/catroid/scratchconverter/Client$ProjectDownloadCallback;)V", "callbackWeakReference", "Ljava/lang/ref/WeakReference;", "download", "", "activity", "Landroidx/appcompat/app/AppCompatActivity;", "downloadOverwriteExistingProject", "context", "Landroid/content/Context;", "projectName", "startService", "Companion", "ProjectDownloadQueue", "Receiver", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectDownloader implements Serializable {
    private static final String FILENAME_QUERY_PARAM = "fname=";
    private static final long serialVersionUID = 42;
    private final WeakReference<Client.ProjectDownloadCallback> callbackWeakReference;
    private final ProjectDownloadQueue queue;
    private final String url;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "ProjectDownloader";

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ProjectDownloader$ProjectDownloadQueue.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectDownloader.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/web/ProjectDownloader$ProjectDownloadQueue;", "", "alreadyInQueue", "", "projectName", "", "enqueue", "", "finished", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ProjectDownloadQueue {
        boolean alreadyInQueue(String projectName);

        void enqueue(String projectName);

        void finished(String projectName);
    }

    public ProjectDownloader(ProjectDownloadQueue queue, String url, Client.ProjectDownloadCallback projectDownloadCallback) {
        Intrinsics.checkNotNullParameter(queue, "queue");
        Intrinsics.checkNotNullParameter(url, "url");
        this.queue = queue;
        this.url = url;
        this.callbackWeakReference = new WeakReference<>(projectDownloadCallback);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ProjectDownloader$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectDownloader.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0006*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/web/ProjectDownloader$Companion;", "", "()V", "FILENAME_QUERY_PARAM", "", "TAG", JvmProtoBufUtil.PLATFORM_TYPE_ID, CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "getProjectNameFromUrl", "url", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getProjectNameFromUrl(String url) {
            Intrinsics.checkNotNullParameter(url, "url");
            String strSubstring = url.substring(StringsKt.lastIndexOf$default((CharSequence) url, ProjectDownloader.FILENAME_QUERY_PARAM, 0, false, 6, (Object) null) + 6);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            try {
                return URLDecoder.decode(strSubstring, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log.e(ProjectDownloader.TAG, "Could not decode project name: " + strSubstring, e);
                return null;
            }
        }
    }

    public final void download(AppCompatActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        String projectNameFromUrl = INSTANCE.getProjectNameFromUrl(this.url);
        if (projectNameFromUrl == null) {
            ToastUtil.showError(activity, R.string.error_could_not_decode_project_name_from_url);
            return;
        }
        if (!this.queue.alreadyInQueue(projectNameFromUrl)) {
            Boolean boolProjectExistsInDirectory = ReplaceExistingProjectDialogFragment.projectExistsInDirectory(projectNameFromUrl);
            Intrinsics.checkNotNullExpressionValue(boolProjectExistsInDirectory, "projectExistsInDirectory(...)");
            if (!boolProjectExistsInDirectory.booleanValue()) {
                downloadOverwriteExistingProject(activity, projectNameFromUrl);
                return;
            }
        }
        ReplaceExistingProjectDialogFragment.newInstance(projectNameFromUrl, this).show(activity.getSupportFragmentManager(), ReplaceExistingProjectDialogFragment.TAG);
    }

    public final void downloadOverwriteExistingProject(Context context, String projectName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(projectName, "projectName");
        synchronized (this.queue) {
            if (this.queue.alreadyInQueue(projectName)) {
                String string = context.getString(R.string.error_project_already_in_queue, projectName);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                Log.i(TAG, string);
                ToastUtil.showError(context, string);
                return;
            }
            this.queue.enqueue(projectName);
            startService(projectName, context);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void startService(String projectName, Context context) {
        Intrinsics.checkNotNullParameter(projectName, "projectName");
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent(context, (Class<?>) ProjectDownloadService.class);
        intent.putExtra(ProjectDownloadService.EXTRA_DOWNLOAD_NAME, projectName);
        intent.putExtra("url", this.url);
        intent.putExtra("receiver", new Receiver(this, projectName, new Handler()));
        StatusBarNotificationManager statusBarNotificationManager = new StatusBarNotificationManager(context);
        NotificationData notificationDataCreateProjectDownloadNotification = statusBarNotificationManager.createProjectDownloadNotification(context, projectName);
        intent.putExtra(ProjectDownloadService.EXTRA_NOTIFICATION_DATA, notificationDataCreateProjectDownloadNotification);
        statusBarNotificationManager.showOrUpdateNotification(context, notificationDataCreateProjectDownloadNotification, 0, null);
        Client.ProjectDownloadCallback projectDownloadCallback = this.callbackWeakReference.get();
        if (projectDownloadCallback != null) {
            projectDownloadCallback.onDownloadStarted(this.url);
        }
        context.startForegroundService(intent);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ProjectDownloader$Receiver.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectDownloader.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0083\u0004\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/web/ProjectDownloader$Receiver;", "Landroid/os/ResultReceiver;", "projectName", "", "handler", "Landroid/os/Handler;", "(Lorg/catrobat/catroid/web/ProjectDownloader;Ljava/lang/String;Landroid/os/Handler;)V", "getProjectName", "()Ljava/lang/String;", "onReceiveResult", "", "resultCode", "", "resultData", "Landroid/os/Bundle;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Receiver extends ResultReceiver {
        private final String projectName;
        final /* synthetic */ ProjectDownloader this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Receiver(ProjectDownloader projectDownloader, String projectName, Handler handler) {
            super(handler);
            Intrinsics.checkNotNullParameter(projectName, "projectName");
            Intrinsics.checkNotNullParameter(handler, "handler");
            this.this$0 = projectDownloader;
            this.projectName = projectName;
        }

        public final String getProjectName() {
            return this.projectName;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Intrinsics.checkNotNullParameter(resultData, "resultData");
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == 1) {
                int i = resultData.getInt("progress");
                Client.ProjectDownloadCallback projectDownloadCallback = (Client.ProjectDownloadCallback) this.this$0.callbackWeakReference.get();
                if (projectDownloadCallback != null) {
                    projectDownloadCallback.onDownloadProgress(i, this.this$0.url);
                    return;
                }
                return;
            }
            if (resultCode == 2) {
                this.this$0.queue.finished(this.projectName);
                return;
            }
            if (resultCode != 3) {
                return;
            }
            Client.ProjectDownloadCallback projectDownloadCallback2 = (Client.ProjectDownloadCallback) this.this$0.callbackWeakReference.get();
            if (projectDownloadCallback2 != null) {
                projectDownloadCallback2.onDownloadFinished(this.projectName, this.this$0.url);
            }
            ProjectManager.getInstance().addNewDownloadedProject(this.projectName);
            this.this$0.queue.finished(this.projectName);
        }
    }
}
