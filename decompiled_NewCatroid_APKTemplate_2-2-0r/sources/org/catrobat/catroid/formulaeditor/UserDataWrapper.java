package org.catrobat.catroid.formulaeditor;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import java.util.Iterator;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.UserDefinedScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/UserDataWrapper.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class UserDataWrapper {
    public static UserVariable getUserVariable(String name2, Scope scope) {
        UserVariable userVariable;
        if (scope == null) {
            return null;
        }
        Sprite sprite = scope.getSprite();
        if (sprite != null && (userVariable = sprite.getUserVariable(name2)) != null) {
            return userVariable;
        }
        Project project = scope.getProject();
        if (project == null) {
            return null;
        }
        UserVariable userVariable2 = project.getUserVariable(name2);
        return userVariable2 != null ? userVariable2 : project.getMultiplayerVariable(name2);
    }

    public static UserList getUserList(String name2, Scope scope) {
        UserList userList;
        if (scope == null) {
            return null;
        }
        Sprite sprite = scope.getSprite();
        if (sprite != null && (userList = sprite.getUserList(name2)) != null) {
            return userList;
        }
        Project project = scope.getProject();
        if (project != null) {
            return project.getUserList(name2);
        }
        return null;
    }

    public static UserData getUserDefinedBrickInput(String value, SequenceAction sequence) {
        if (!(sequence instanceof ScriptSequenceAction)) {
            return null;
        }
        Script script = ((ScriptSequenceAction) sequence).getScript();
        if (script instanceof UserDefinedScript) {
            return ((UserDefinedScript) script).getUserDefinedBrickInput(value);
        }
        return null;
    }

    public static void resetAllUserData(Project project) {
        project.resetUserData();
        Iterator<Scene> it = project.getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                it2.next().resetUserData();
            }
        }
    }

    private UserDataWrapper() {
        throw new AssertionError("No.");
    }
}
