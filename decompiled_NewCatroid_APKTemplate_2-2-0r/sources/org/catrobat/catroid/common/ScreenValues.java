package org.catrobat.catroid.common;

import com.badlogic.gdx.graphics.GL20;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.utils.Resolution;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/ScreenValues.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public final class ScreenValues {
    public static Resolution currentScreenResolution;
    public static final Resolution DEFAULT_SCREEN_RESOLUTION = new Resolution(GL20.GL_INVALID_ENUM, 768);
    public static final Resolution CAST_SCREEN_RESOLUTION = new Resolution(GL20.GL_INVALID_ENUM, 720);

    private ScreenValues() {
        throw new AssertionError();
    }

    public static void setToDefaultScreenSize() {
        currentScreenResolution = DEFAULT_SCREEN_RESOLUTION;
    }

    public static Resolution getResolutionForProject(Project project) {
        if (project.isCastProject()) {
            return CAST_SCREEN_RESOLUTION;
        }
        return currentScreenResolution;
    }
}
