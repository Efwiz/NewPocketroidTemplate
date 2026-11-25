package org.catrobat.catroid.common;

import android.content.Context;
import java.io.IOException;
import org.catrobat.catroid.common.defaultprojectcreators.ChromeCastProjectCreator;
import org.catrobat.catroid.common.defaultprojectcreators.DefaultExampleProject;
import org.catrobat.catroid.common.defaultprojectcreators.ProjectCreator;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.io.XstreamSerializer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/DefaultProjectHandler.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public final class DefaultProjectHandler {
    private static DefaultProjectHandler instance;
    private ProjectCreator defaultProjectCreator;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/DefaultProjectHandler$ProjectCreatorType.class
      classes12.dex
     */
    public enum ProjectCreatorType {
        PROJECT_CREATOR_DEFAULT,
        PROJECT_CREATOR_CAST
    }

    public static DefaultProjectHandler getInstance() {
        if (instance == null) {
            instance = new DefaultProjectHandler();
        }
        return instance;
    }

    private DefaultProjectHandler() {
        setDefaultProjectCreator(ProjectCreatorType.PROJECT_CREATOR_DEFAULT);
    }

    public static Project createAndSaveDefaultProject(Context context) throws IOException {
        return createAndSaveDefaultProject(context.getString(getInstance().defaultProjectCreator.getDefaultProjectNameID()), context, false);
    }

    public static Project createAndSaveDefaultProject(String name2, Context context, boolean landscapeMode) throws IOException {
        return getInstance().defaultProjectCreator.createDefaultProject(name2, context, landscapeMode);
    }

    public static Project createAndSaveEmptyProject(String name2, Context context, boolean landscapeMode, boolean isCastEnabled) throws IOException {
        Project project = new Project(context, name2, landscapeMode, isCastEnabled);
        if (project.getDirectory().exists()) {
            throw new IOException("Cannot create new project at " + project.getDirectory().getAbsolutePath() + ", directory already exists.");
        }
        XstreamSerializer.getInstance().saveProject(project);
        return project;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/DefaultProjectHandler$1.class
      classes12.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.common.DefaultProjectHandler$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$common$DefaultProjectHandler$ProjectCreatorType;

        static {
            int[] iArr = new int[ProjectCreatorType.values().length];
            $SwitchMap$org$catrobat$catroid$common$DefaultProjectHandler$ProjectCreatorType = iArr;
            try {
                iArr[ProjectCreatorType.PROJECT_CREATOR_DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$common$DefaultProjectHandler$ProjectCreatorType[ProjectCreatorType.PROJECT_CREATOR_CAST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public void setDefaultProjectCreator(ProjectCreatorType type) {
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$common$DefaultProjectHandler$ProjectCreatorType[type.ordinal()];
        if (i == 1) {
            this.defaultProjectCreator = new DefaultExampleProject();
        } else {
            if (i != 2) {
                return;
            }
            this.defaultProjectCreator = new ChromeCastProjectCreator();
        }
    }
}
