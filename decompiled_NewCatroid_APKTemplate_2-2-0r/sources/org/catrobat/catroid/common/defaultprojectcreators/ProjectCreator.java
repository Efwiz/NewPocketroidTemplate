package org.catrobat.catroid.common.defaultprojectcreators;

import android.content.Context;
import java.io.IOException;
import org.catrobat.catroid.content.Project;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/defaultprojectcreators/ProjectCreator.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public abstract class ProjectCreator {
    static double backgroundImageScaleFactor = 1.0d;
    int defaultProjectNameResourceId;

    public abstract Project createDefaultProject(String name2, Context context, boolean landscapeMode) throws IOException;

    static int calculateValueRelativeToScaledBackground(int value) {
        int i = (int) (((double) value) * backgroundImageScaleFactor);
        return i - (i % 5);
    }

    public int getDefaultProjectNameID() {
        return this.defaultProjectNameResourceId;
    }
}
