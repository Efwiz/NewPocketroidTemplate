package org.catrobat.catroid.stage.event;

import java.util.Iterator;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/event/EventManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EventManager {
    public static boolean projectHasScriptOfType(Project project, Class<? extends Script> scriptClass) {
        if (project != null && scriptClass != null) {
            Iterator<Scene> it = project.getSceneList().iterator();
            while (it.hasNext()) {
                Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
                while (it2.hasNext()) {
                    Iterator<Script> it3 = it2.next().getScriptList().iterator();
                    while (it3.hasNext()) {
                        if (scriptClass.isInstance(it3.next())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
