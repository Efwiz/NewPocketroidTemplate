package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GoNStepsBackAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GoNStepsBackAction extends TemporalAction {
    private Scope scope;
    private Formula steps;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        try {
            Formula formula = this.steps;
            Float fValueOf = formula == null ? Float.valueOf(0.0f) : formula.interpretFloat(this.scope);
            int zIndex = this.scope.getSprite().look.getZIndex();
            if (fValueOf.intValue() > 0 && zIndex - fValueOf.intValue() < 3) {
                this.scope.getSprite().look.setZIndex(3);
            } else if (fValueOf.intValue() < 0 && zIndex - fValueOf.intValue() < zIndex) {
                toFront(delta);
            } else {
                goNStepsBack(fValueOf.intValue());
            }
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    private void toFront(float delta) {
        this.scope.getSprite().getActionFactory().createComeToFrontAction(this.scope.getSprite()).act(delta);
    }

    private void goNStepsBack(int steps) {
        this.scope.getSprite().look.setZIndex(Math.max(this.scope.getSprite().look.getZIndex() - steps, 3));
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setSteps(Formula steps) {
        this.steps = steps;
    }
}
