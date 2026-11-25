package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WaitTillIdleAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WaitTillIdleAction extends Action {
    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        return allActorsIdle();
    }

    private boolean allActorsIdle() {
        int i = StageActivity.getActiveStageListener().getStage().getActors().size;
        if (i == 0) {
            return false;
        }
        Array.ArrayIterator<Actor> it = StageActivity.getActiveStageListener().getStage().getActors().iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            Array<Action> actions = it.next().getActions();
            if (actions.size == 0) {
                i2++;
            }
            if (actions.size == 1 && ((ScriptSequenceAction) actions.get(0)).getActions().contains(this, true)) {
                i3++;
            }
        }
        return i == i2 + i3;
    }
}
