package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.ShowTextActor;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/HideText3Action.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HideText3Action extends Action {
    private ShowTextUtils.AndroidStringProvider androidStringProvider;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f534name;
    private Scope scope;
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (this.scope == null) {
            return true;
        }
        UserVariable userVariable = new UserVariable(this.f534name);
        if (StageActivity.getActiveStageListener() != null) {
            Array.ArrayIterator<Actor> it = StageActivity.getActiveStageListener().getStage().getActors().iterator();
            while (it.hasNext()) {
                Actor next = it.next();
                if (next instanceof ShowTextActor) {
                    ShowTextActor showTextActor = (ShowTextActor) next;
                    if (showTextActor.getVariableNameToCompare().equals(this.f534name) && showTextActor.getSprite().equals(this.sprite)) {
                        showTextActor.remove();
                        break;
                    }
                }
            }
        }
        userVariable.setVisible(false);
        return true;
    }

    public void setName(Formula nameFormula) {
        try {
            this.f534name = nameFormula.interpretString(this.scope);
        } catch (InterpretationException unused) {
            this.f534name = "defaultName";
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setAndroidStringProvider(ShowTextUtils.AndroidStringProvider androidStringProvider) {
        this.androidStringProvider = androidStringProvider;
    }
}
