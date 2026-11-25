package org.catrobat.catroid.io.asynctask;

import android.util.Log;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectRenamerKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectRenamer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0001\u001a\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0001\u001a\u0016\u0010\t\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004\"\u0016\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "renameProject", "Ljava/io/File;", "projectDirectory", "destinationName", "tryRenameProject", "", "tryRenameTo", "destinationDirectory", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProjectRenamerKt {
    private static final String TAG = "ProjectRenamer";

    public static final File renameProject(File projectDirectory, String destinationName) {
        Intrinsics.checkNotNullParameter(projectDirectory, "projectDirectory");
        Intrinsics.checkNotNullParameter(destinationName, "destinationName");
        File file = new File(projectDirectory.getParent(), FileMetaDataExtractor.encodeSpecialCharsForFileSystem(destinationName));
        File file2 = new File(file, Constants.CODE_XML_FILE_NAME);
        if (!tryRenameTo(projectDirectory, file) || !tryRenameProject(file2, destinationName)) {
            return null;
        }
        ProjectManager.getInstance().moveChangedFlag(projectDirectory.getName(), destinationName);
        return file;
    }

    public static final boolean tryRenameTo(File projectDirectory, File destinationDirectory) {
        Intrinsics.checkNotNullParameter(projectDirectory, "projectDirectory");
        Intrinsics.checkNotNullParameter(destinationDirectory, "destinationDirectory");
        try {
            projectDirectory.renameTo(destinationDirectory);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Cannot rename project directory " + projectDirectory.getAbsolutePath() + " to " + destinationDirectory.getName(), e);
            return false;
        }
    }

    public static final boolean tryRenameProject(File projectDirectory, String destinationName) throws Throwable {
        Intrinsics.checkNotNullParameter(projectDirectory, "projectDirectory");
        Intrinsics.checkNotNullParameter(destinationName, "destinationName");
        try {
            XstreamSerializer.renameProject(projectDirectory, destinationName);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Cannot rename project directory " + projectDirectory.getAbsolutePath() + " to " + destinationName, e);
            return false;
        }
    }
}
