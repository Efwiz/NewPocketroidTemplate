package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StopOtherScriptsAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StopOtherScriptsAction extends Action {
    private Script currentScript;
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if ((this.actor instanceof Look) && this.actor.getActions() != null) {
            this.sprite.releaseAllPointers();
            Look look = (Look) this.actor;
            look.stopThreads(getOtherThreads(look));
        }
        return true;
    }

    private Array<Action> getOtherThreads(Look look) {
        Array<Action> array = new Array<>(look.getActions());
        Array.ArrayIterator<Action> it = array.iterator();
        while (it.hasNext()) {
            Action next = it.next();
            if ((next instanceof ScriptSequenceAction) && ((ScriptSequenceAction) next).getScript() == this.currentScript) {
                it.remove();
            }
        }
        return array;
    }

    public void setCurrentScript(Script currentScript) {
        this.currentScript = currentScript;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
