package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetVelocityAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetVelocityAction extends TemporalAction {
    private PhysicsObject physicsObject;
    private Scope scope;
    private Formula velocityX;
    private Formula velocityY;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        try {
            Formula formula = this.velocityX;
            Float fValueOf = formula == null ? Float.valueOf(0.0f) : formula.interpretFloat(this.scope);
            try {
                Formula formula2 = this.velocityY;
                this.physicsObject.setVelocity(fValueOf.floatValue(), (formula2 == null ? Float.valueOf(0.0f) : formula2.interpretFloat(this.scope)).floatValue());
            } catch (InterpretationException e) {
                Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            }
        } catch (InterpretationException e2) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e2);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setPhysicsObject(PhysicsObject physicsObject) {
        this.physicsObject = physicsObject;
    }

    public void setVelocity(Formula velocityX, Formula velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
}
