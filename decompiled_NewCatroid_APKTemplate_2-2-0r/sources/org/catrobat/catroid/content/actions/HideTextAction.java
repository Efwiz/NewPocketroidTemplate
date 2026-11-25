package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.ShowTextActor;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/HideTextAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HideTextAction extends Action {
    private ShowTextUtils.AndroidStringProvider androidStringProvider;
    private Sprite sprite;
    private UserVariable variableToHide;

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (StageActivity.getActiveStageListener() != null && this.variableToHide != null) {
            Array.ArrayIterator<Actor> it = StageActivity.getActiveStageListener().getStage().getActors().iterator();
            while (it.hasNext()) {
                Actor next = it.next();
                if (next instanceof ShowTextActor) {
                    ShowTextActor showTextActor = (ShowTextActor) next;
                    if (showTextActor.getVariableNameToCompare().equals(this.variableToHide.getName()) && showTextActor.getSprite().equals(this.sprite)) {
                        showTextActor.remove();
                        break;
                    }
                }
            }
        }
        UserVariable userVariable = this.variableToHide;
        if (userVariable == null) {
            return true;
        }
        userVariable.setVisible(false);
        return true;
    }

    public void setVariableToHide(UserVariable userVariable) {
        this.variableToHide = userVariable;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setAndroidStringProvider(ShowTextUtils.AndroidStringProvider androidStringProvider) {
        this.androidStringProvider = androidStringProvider;
    }
}
