package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Script;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StopThisScriptAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StopThisScriptAction extends Action {
    private Script currentScript;

    public void setCurrentScript(Script currentScript) {
        this.currentScript = currentScript;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!(this.actor instanceof Look)) {
            return true;
        }
        ((Look) this.actor).stopThreadWithScript(this.currentScript);
        return true;
    }
}
