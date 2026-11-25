package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/MoveNStepsAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MoveNStepsAction extends TemporalAction {
    private Scope scope;
    private Formula steps;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        try {
            Formula formula = this.steps;
            Double dValueOf = formula == null ? Double.valueOf(0.0d) : formula.interpretDouble(this.scope);
            double radians = Math.toRadians(this.scope.getSprite().look.getMotionDirectionInUserInterfaceDimensionUnit());
            this.scope.getSprite().look.changePositionInInterfaceDimensionUnit((float) (dValueOf.doubleValue() * Math.sin(radians)), (float) (dValueOf.doubleValue() * Math.cos(radians)));
            this.scope.getSprite().movedByStepsBrick = true;
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setSteps(Formula steps) {
        this.steps = steps;
    }
}
