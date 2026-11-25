package org.catrobat.catroid.io.asynctask;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.ZipArchiver;
import org.catrobat.catroid.transfers.project.ProjectDownloadService;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;
import org.catrobat.catroid.utils.notifications.NotificationData;
import org.catrobat.catroid.utils.notifications.StatusBarNotificationManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectExportTask.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectExportTask.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\b\u0018\u0000 \u001b2\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001:\u0002\u001b\u001cB%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u0012\u001a\u00020\u0011H\u0002J'\u0010\u0013\u001a\u0004\u0018\u00010\u00022\u0016\u0010\u0014\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\u0015\"\u0004\u0018\u00010\u0002H\u0014¢\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0011H\u0007J\u000e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u000fJ\u0010\u0010\u001a\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\nH\u0002R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ProjectExportTask;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", ProjectUploadActivityKt.PROJECT_DIR, "Ljava/io/File;", "projectDestination", "Landroid/net/Uri;", ProjectDownloadService.EXTRA_NOTIFICATION_DATA, "Lorg/catrobat/catroid/utils/notifications/NotificationData;", "context", "Landroid/content/Context;", "(Ljava/io/File;Landroid/net/Uri;Lorg/catrobat/catroid/utils/notifications/NotificationData;Landroid/content/Context;)V", "contextWeakReference", "Ljava/lang/ref/WeakReference;", "finishedExportingCallback", "Lorg/catrobat/catroid/io/asynctask/ProjectExportTask$ProjectExportCallback;", "abortNotification", "", "deleteUndoFile", "doInBackground", "voids", "", "([Ljava/lang/Void;)Ljava/lang/Void;", "exportProjectToExternalStorage", "registerCallback", "callback", "updateNotification", "Companion", "ProjectExportCallback", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectExportTask extends AsyncTask<Void, Void, Void> {
    private static final int NOTIFICATION_PROGRESS_COMPLETE = 100;
    private final WeakReference<Context> contextWeakReference;
    private ProjectExportCallback finishedExportingCallback;
    private final NotificationData notificationData;
    private final Uri projectDestination;
    private final File projectDir;
    private static final String TAG = "ProjectExportTask";

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectExportTask$ProjectExportCallback.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectExportTask.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ProjectExportTask$ProjectExportCallback;", "", "onProjectExportFinished", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ProjectExportCallback {
        void onProjectExportFinished();
    }

    public ProjectExportTask(File projectDir, Uri projectDestination, NotificationData notificationData, Context context) {
        Intrinsics.checkNotNullParameter(projectDir, "projectDir");
        Intrinsics.checkNotNullParameter(projectDestination, "projectDestination");
        Intrinsics.checkNotNullParameter(notificationData, "notificationData");
        Intrinsics.checkNotNullParameter(context, "context");
        this.projectDir = projectDir;
        this.projectDestination = projectDestination;
        this.notificationData = notificationData;
        this.contextWeakReference = new WeakReference<>(context);
    }

    public final void exportProjectToExternalStorage() {
        Context context = this.contextWeakReference.get();
        if (context == null) {
            return;
        }
        deleteUndoFile();
        File file = new File(Constants.CACHE_DIRECTORY, this.projectDir.getName() + Constants.ZIP_EXTENSION);
        if (file.exists()) {
            file.delete();
        }
        try {
            new ZipArchiver().zip(file, this.projectDir.listFiles());
            StorageOperations.copyFileContentToUri(context.getContentResolver(), this.projectDestination, file);
            updateNotification(context);
            ProjectExportCallback projectExportCallback = this.finishedExportingCallback;
            if (projectExportCallback != null) {
                projectExportCallback.onProjectExportFinished();
            }
        } catch (IOException e) {
            Log.e(TAG, "Cannot create archive.", e);
            abortNotification(context);
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public final void registerCallback(ProjectExportCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.finishedExportingCallback = callback;
    }

    private final void updateNotification(Context context) {
        new StatusBarNotificationManager(context).showOrUpdateNotification(context, this.notificationData, 100, null);
    }

    private final void abortNotification(Context context) {
        new StatusBarNotificationManager(context).abortProgressNotificationWithMessage(context, this.notificationData, R.string.save_project_to_external_storage_io_exception_message);
    }

    private final void deleteUndoFile() {
        File file = new File(this.projectDir, Constants.UNDO_CODE_XML_FILE_NAME);
        if (file.exists()) {
            try {
                StorageOperations.deleteFile(file);
            } catch (IOException e) {
                Log.e(TAG, "Deleting undo file failed.", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Void doInBackground(Void... voids) {
        Intrinsics.checkNotNullParameter(voids, "voids");
        exportProjectToExternalStorage();
        return null;
    }
}
