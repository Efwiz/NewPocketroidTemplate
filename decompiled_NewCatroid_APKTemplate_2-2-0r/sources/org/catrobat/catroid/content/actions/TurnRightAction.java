package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsLook;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/TurnRightAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TurnRightAction extends TemporalAction {
    private Formula degrees;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        try {
            Formula formula = this.degrees;
            Float fValueOf = formula == null ? Float.valueOf(0.0f) : formula.interpretFloat(this.scope);
            if (this.scope.getSprite().look instanceof PhysicsLook) {
                ((PhysicsLook) this.scope.getSprite().look).setFlippedByDegree(fValueOf.floatValue());
            }
            this.scope.getSprite().look.changeDirectionInUserInterfaceDimensionUnit(fValueOf.floatValue());
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setDegrees(Formula degrees) {
        this.degrees = degrees;
    }
}
