package org.catrobat.catroid.ui.controller;

import android.content.Context;
import android.content.Intent;
import com.huawei.hms.support.api.entity.core.CommonCode;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.io.asynctask.ProjectSaverKt;
import org.catrobat.catroid.transfers.project.ProjectUploadService;
import org.catrobat.catroid.transfers.project.ResultReceiverWrapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/controller/ProjectUploadController.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectUploadController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001:\u0001\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0002J*\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\n2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/ui/controller/ProjectUploadController;", "", "projectUploadInterface", "Lorg/catrobat/catroid/ui/controller/ProjectUploadController$ProjectUploadInterface;", "(Lorg/catrobat/catroid/ui/controller/ProjectUploadController$ProjectUploadInterface;)V", "context", "Landroid/content/Context;", "createUploadIntent", "Landroid/content/Intent;", "projectName", "", Constants.EXTRA_PROJECT_DESCRIPTION, "project", "Lorg/catrobat/catroid/content/Project;", "startUpload", "", "projectNotesAndCredits", "ProjectUploadInterface", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ProjectUploadController {
    private final Context context;
    private final ProjectUploadInterface projectUploadInterface;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/controller/ProjectUploadController$ProjectUploadInterface.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectUploadController.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH&¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/ui/controller/ProjectUploadController$ProjectUploadInterface;", "", "getContext", "Landroid/content/Context;", "getResultReceiverWrapper", "Lorg/catrobat/catroid/transfers/project/ResultReceiverWrapper;", "startUploadService", "", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ProjectUploadInterface {
        Context getContext();

        ResultReceiverWrapper getResultReceiverWrapper();

        void startUploadService(Intent intent);
    }

    public ProjectUploadController(ProjectUploadInterface projectUploadInterface) {
        Intrinsics.checkNotNullParameter(projectUploadInterface, "projectUploadInterface");
        this.projectUploadInterface = projectUploadInterface;
        this.context = projectUploadInterface.getContext();
    }

    private final Intent createUploadIntent(String projectName, String projectDescription, Project project) {
        Intent intent = new Intent(this.context, (Class<?>) ProjectUploadService.class);
        List<String> sceneNames = project.getSceneNames();
        Intrinsics.checkNotNullExpressionValue(sceneNames, "getSceneNames(...)");
        String[] strArr = (String[]) sceneNames.toArray(new String[0]);
        intent.putExtra(Constants.EXTRA_RESULT_RECEIVER, this.projectUploadInterface.getResultReceiverWrapper());
        intent.putExtra(Constants.EXTRA_UPLOAD_NAME, projectName);
        intent.putExtra(Constants.EXTRA_PROJECT_DESCRIPTION, projectDescription);
        intent.putExtra(Constants.EXTRA_PROJECT_PATH, project.getDirectory().getAbsolutePath());
        intent.putExtra(Constants.EXTRA_PROVIDER, Constants.NO_OAUTH_PROVIDER);
        intent.putExtra(Constants.EXTRA_SCENE_NAMES, strArr);
        return intent;
    }

    public void startUpload(String projectName, String projectDescription, String projectNotesAndCredits, Project project) {
        Intrinsics.checkNotNullParameter(projectName, "projectName");
        Intrinsics.checkNotNullParameter(projectDescription, "projectDescription");
        Intrinsics.checkNotNullParameter(project, "project");
        project.setDescription(projectDescription);
        project.setNotesAndCredits(projectNotesAndCredits);
        project.setDeviceData(this.context);
        project.setListeningLanguageTag();
        ProjectSaverKt.saveProjectSerial(project, this.context);
        this.projectUploadInterface.startUploadService(createUploadIntent(projectName, projectDescription, project));
    }
}
