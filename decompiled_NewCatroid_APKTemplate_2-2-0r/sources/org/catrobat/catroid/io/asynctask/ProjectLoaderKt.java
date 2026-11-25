package org.catrobat.catroid.io.asynctask;

import android.content.Context;
import android.util.Log;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.exceptions.ProjectException;
import org.catrobat.catroid.io.DeviceListAccessor;
import org.catrobat.catroid.io.DeviceVariableAccessor;
import org.catrobat.catroid.io.LookFileGarbageCollector;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectLoaderKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectLoader.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"loadProject", "", ProjectUploadActivityKt.PROJECT_DIR, "Ljava/io/File;", "context", "Landroid/content/Context;", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProjectLoaderKt {
    public static final boolean loadProject(File file, Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            ProjectManager.getInstance().loadProject(file, context);
            Project currentProject = ProjectManager.getInstance().getCurrentProject();
            new DeviceVariableAccessor(file).cleanUpDeletedUserData(currentProject);
            new DeviceListAccessor(file).cleanUpDeletedUserData(currentProject);
            LookFileGarbageCollector lookFileGarbageCollector = new LookFileGarbageCollector();
            Intrinsics.checkNotNull(currentProject);
            lookFileGarbageCollector.cleanUpUnusedLookFiles(currentProject);
            return true;
        } catch (ProjectException e) {
            if (file == null) {
                return false;
            }
            Log.e(ProjectLoader.INSTANCE.getTAG(), "Cannot load project in " + file.getAbsolutePath(), e);
            return false;
        }
    }
}
