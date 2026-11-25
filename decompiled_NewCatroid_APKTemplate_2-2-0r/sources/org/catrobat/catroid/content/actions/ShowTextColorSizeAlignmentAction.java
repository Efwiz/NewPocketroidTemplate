package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.ShowTextActor;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ShowTextColorSizeAlignmentAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShowTextColorSizeAlignmentAction extends Action {
    private int alignment;
    private ShowTextUtils.AndroidStringProvider androidStringProvider;
    private Formula color;
    private Formula relativeTextSize;
    private Scope scope;
    private UserVariable variableToShow;
    private Formula xPosition;
    private Formula yPosition;

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        ShowTextActor showTextActor;
        try {
            float fFloatValue = this.xPosition.interpretFloat(this.scope).floatValue();
            float fFloatValue2 = this.yPosition.interpretFloat(this.scope).floatValue();
            float fFloatValue3 = this.relativeTextSize.interpretFloat(this.scope).floatValue() / 100.0f;
            String strInterpretString = this.color.interpretString(this.scope);
            if (StageActivity.getActiveStageListener() == null) {
                return true;
            }
            Array.ArrayIterator<Actor> it = StageActivity.getActiveStageListener().getStage().getActors().iterator();
            while (true) {
                if (!it.hasNext()) {
                    showTextActor = null;
                    break;
                }
                Actor next = it.next();
                if (next instanceof ShowTextActor) {
                    ShowTextActor showTextActor2 = (ShowTextActor) next;
                    if (showTextActor2.getVariableNameToCompare().equals(this.variableToShow.getName()) && showTextActor2.getSprite().equals(this.scope.getSprite())) {
                        showTextActor = showTextActor2;
                        break;
                    }
                }
            }
            if (showTextActor == null) {
                StageActivity.getActiveStageListener().addActor(new ShowTextActor(false, this.variableToShow, "", fFloatValue, fFloatValue2, fFloatValue3, strInterpretString, this.scope.getSprite(), this.alignment, this.androidStringProvider));
                this.variableToShow.setVisible(true);
            } else {
                showTextActor.updateProperties(showTextActor.getVariableNameToCompare(), fFloatValue, fFloatValue2, fFloatValue3, strInterpretString, null);
            }
            return true;
        } catch (InterpretationException e) {
            Log.d("ShowTextColorSize", "InterpretationException: " + e);
        }
    }

    public void setPosition(Formula xPosition, Formula yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void setRelativeTextSize(Formula relativeTextSize) {
        this.relativeTextSize = relativeTextSize;
    }

    public void setColor(Formula color) {
        this.color = color;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setVariableToShow(UserVariable userVariable) {
        this.variableToShow = userVariable;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public void setAndroidStringProvider(ShowTextUtils.AndroidStringProvider androidStringProvider) {
        this.androidStringProvider = androidStringProvider;
    }
}
