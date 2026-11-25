package org.catrobat.catroid.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.LunoScriptBrick;
import org.catrobat.catroid.content.bricks.RunPythonScriptBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ProjectSecurityChecker.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ProjectSecurityChecker {
    public static boolean projectContainsDangerousBricks(Project project) {
        if (project != null && project.getLibsDir() != null) {
            File[] fileArrListFiles = project.getLibsDir().listFiles();
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                return true;
            }
            Iterator<Scene> it = project.getSceneList().iterator();
            while (it.hasNext()) {
                Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
                while (it2.hasNext()) {
                    Iterator<Script> it3 = it2.next().getScriptList().iterator();
                    while (it3.hasNext()) {
                        if (checkBrickRecursively(it3.next())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkBrickRecursively(Brick brick) {
        if (brick == null) {
            return false;
        }
        return (brick instanceof LunoScriptBrick) || (brick instanceof RunPythonScriptBrick);
    }

    private static boolean checkBrickRecursively(Script brick) {
        List<Brick> brickList;
        if (brick != null && (brickList = brick.getBrickList()) != null) {
            Iterator<Brick> it = brickList.iterator();
            while (it.hasNext()) {
                if (checkBrickRecursively(it.next())) {
                    return true;
                }
            }
        }
        return false;
    }
}
