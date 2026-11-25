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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ShowText3Action.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShowText3Action extends Action {
    private int alignment;
    private ShowTextUtils.AndroidStringProvider androidStringProvider;
    private Formula color;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private Formula f551name;
    private Formula relativeTextSize;
    private Scope scope;
    private Formula text;
    private Formula xPosition;
    private Formula yPosition;

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        ShowTextActor showTextActor;
        try {
            String strInterpretString = this.f551name.interpretString(this.scope) != null ? this.f551name.interpretString(this.scope) : "dummyActor";
            String strInterpretString2 = this.text.interpretString(this.scope) != null ? this.text.interpretString(this.scope) : "NaN";
            float fFloatValue = this.xPosition.interpretFloat(this.scope).floatValue();
            float fFloatValue2 = this.yPosition.interpretFloat(this.scope).floatValue();
            float fFloatValue3 = this.relativeTextSize.interpretFloat(this.scope).floatValue() / 100.0f;
            String strInterpretString3 = this.color.interpretString(this.scope);
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
                    if (showTextActor2.getVariableNameToCompare().equals(strInterpretString) && showTextActor2.getSprite().equals(this.scope.getSprite())) {
                        showTextActor = showTextActor2;
                        break;
                    }
                }
            }
            if (showTextActor == null) {
                UserVariable userVariable = new UserVariable(strInterpretString, strInterpretString2);
                StageActivity.getActiveStageListener().addActor(new ShowTextActor(true, userVariable, strInterpretString2, fFloatValue, fFloatValue2, fFloatValue3, strInterpretString3, this.scope.getSprite(), this.alignment, this.androidStringProvider));
                userVariable.setVisible(true);
            } else {
                showTextActor.updateProperties(strInterpretString2, fFloatValue, fFloatValue2, fFloatValue3, strInterpretString3, null);
            }
            return true;
        } catch (InterpretationException e) {
            Log.d("ShowText3Action", "InterpretationException: " + e);
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

    public void setText(Formula text) {
        this.text = text;
    }

    public void setName(Formula name2) {
        this.f551name = name2;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public void setAndroidStringProvider(ShowTextUtils.AndroidStringProvider androidStringProvider) {
        this.androidStringProvider = androidStringProvider;
    }
}
