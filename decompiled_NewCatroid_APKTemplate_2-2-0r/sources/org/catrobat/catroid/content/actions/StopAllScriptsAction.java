package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StopAllScriptsAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StopAllScriptsAction extends Action {
    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        Array.ArrayIterator<Actor> it = StageActivity.getActiveStageListener().getStage().getActors().iterator();
        while (it.hasNext()) {
            Actor next = it.next();
            if (next instanceof Look) {
                Look look = (Look) next;
                look.stopThreads(look.getActions());
            }
        }
        return true;
    }
}
